package com.jubensha.manager.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jubensha.manager.common.exception.BusinessException;
import com.jubensha.manager.dao.UserMapper;
import com.jubensha.manager.model.dto.LoginRequest;
import com.jubensha.manager.model.entity.DmPerformance;
import com.jubensha.manager.model.entity.User;
import com.jubensha.manager.model.vo.DmInfoVO;
import com.jubensha.manager.model.vo.LoginVO;
import com.jubensha.manager.service.DmPerformanceService;
import com.jubensha.manager.service.UserService;
import com.jubensha.manager.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final JwtUtil jwtUtil;
    private final DmPerformanceService dmPerformanceService;

    @Override
    public LoginVO login(LoginRequest request) {
        User user = getByUsername(request.getUsername());
        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }
        if (!BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }
        if ("FROZEN".equals(user.getStatus())) {
            throw new BusinessException("账号已被冻结，请联系管理员");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole());

        return LoginVO.builder()
                .token(token)
                .userId(user.getId())
                .username(user.getUsername())
                .realName(user.getRealName())
                .role(user.getRole())
                .avatar(user.getAvatar())
                .build();
    }

    @Override
    public User getByUsername(String username) {
        return getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }

    @Override
    public void updatePassword(Long userId, String oldPwd, String newPwd) {
        User user = getById(userId);
        if (!BCrypt.checkpw(oldPwd, user.getPassword())) {
            throw new BusinessException("原密码错误");
        }
        user.setPassword(BCrypt.hashpw(newPwd));
        updateById(user);
    }

    @Override
    public long countByRole(String role) {
        return count(new LambdaQueryWrapper<User>().eq(User::getRole, role));
    }

    @Override
    public List<DmInfoVO> listAllDmsInfo() {
        // 查询所有DM用户
        List<User> dms = list(new LambdaQueryWrapper<User>().eq(User::getRole, "DM"));
        if (dms.isEmpty()) {
            return List.of();
        }

        // 批量查询所有DM的绩效数据
        List<Long> dmUserIds = dms.stream().map(User::getId).toList();
        List<DmPerformance> performances = dmPerformanceService.list(
                new LambdaQueryWrapper<DmPerformance>().in(DmPerformance::getDmUserId, dmUserIds));

        // 按DM用户ID分组
        Map<Long, List<DmPerformance>> performanceMap = performances.stream()
                .collect(Collectors.groupingBy(DmPerformance::getDmUserId));

        // 组装每个DM的基本信息 + 绩效汇总
        List<DmInfoVO> result = new ArrayList<>();
        for (User dm : dms) {
            DmInfoVO vo = DmInfoVO.builder()
                    .id(dm.getId())
                    .username(dm.getUsername())
                    .realName(dm.getRealName())
                    .phone(dm.getPhone())
                    .avatar(dm.getAvatar())
                    .role(dm.getRole())
                    .status(dm.getStatus())
                    .commissionRate(dm.getCommissionRate())
                    .introduction(dm.getIntroduction())
                    .createTime(dm.getCreateTime())
                    .build();

            // 聚合绩效：累计开本、累计提成、平均评分
            List<DmPerformance> dmPerformances = performanceMap.get(dm.getId());
            if (dmPerformances != null && !dmPerformances.isEmpty()) {
                int totalSessions = dmPerformances.stream()
                        .mapToInt(p -> p.getDailySessionCount() != null ? p.getDailySessionCount() : 0)
                        .sum();
                BigDecimal totalCommission = dmPerformances.stream()
                        .map(p -> p.getMonthlyCommission() != null ? p.getMonthlyCommission() : BigDecimal.ZERO)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal avgRating = dmPerformances.stream()
                        .filter(p -> p.getAvgRating() != null)
                        .map(DmPerformance::getAvgRating)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                if (avgRating.compareTo(BigDecimal.ZERO) > 0) {
                    long countWithRating = dmPerformances.stream()
                            .filter(p -> p.getAvgRating() != null).count();
                    avgRating = avgRating.divide(BigDecimal.valueOf(countWithRating), 1, java.math.RoundingMode.HALF_UP);
                }
                vo.setTotalSessions(totalSessions);
                vo.setTotalCommission(totalCommission);
                vo.setAvgRating(avgRating);
            }
            result.add(vo);
        }
        return result;
    }
}
