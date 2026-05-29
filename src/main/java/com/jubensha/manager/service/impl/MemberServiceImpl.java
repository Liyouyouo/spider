package com.jubensha.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jubensha.manager.common.exception.BusinessException;
import com.jubensha.manager.dao.MemberMapper;
import com.jubensha.manager.model.entity.Member;
import com.jubensha.manager.service.MemberService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Override
    public Member getByUserId(Long userId) {
        return getOne(new LambdaQueryWrapper<Member>().eq(Member::getUserId, userId));
    }

    @Override
    public void addPoints(Long userId, Integer points) {
        Member member = getByUserId(userId);
        if (member != null) {
            member.setPoints(member.getPoints() + points);
            member.setTotalSpent(member.getTotalSpent().add(BigDecimal.valueOf(points)));
            member.setTotalPlays(member.getTotalPlays() + 1);
            updateById(member);
        }
    }

    @Override
    public void deductPoints(Long userId, Integer points) {
        Member member = getByUserId(userId);
        if (member == null) {
            throw new BusinessException("会员不存在");
        }
        if (member.getPoints() < points) {
            throw new BusinessException("积分不足");
        }
        member.setPoints(Math.max(0, member.getPoints() - points));
        updateById(member);
    }
}
