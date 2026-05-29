package com.jubensha.manager.controller.admin;

import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jubensha.manager.common.Result;
import com.jubensha.manager.model.entity.User;
import com.jubensha.manager.model.vo.DmInfoVO;
import com.jubensha.manager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;

    /** DM人员数量 */
    @GetMapping("/dm-count")
    public Result<Map<String, Long>> dmCount() {
        Map<String, Long> data = new LinkedHashMap<>();
        data.put("count", userService.countByRole("DM"));
        return Result.success(data);
    }

    /** 查看所有DM信息（含绩效） */
    @GetMapping("/dms")
    public Result<List<DmInfoVO>> listAllDms() {
        return Result.success(userService.listAllDmsInfo());
    }

    /** 员工/DM列表 */
    @GetMapping
    public Result<Page<User>> list(@RequestParam(required = false) String role,
                                    @RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "10") Integer size) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (role != null && !role.isEmpty()) {
            wrapper.eq(User::getRole, role);
        }
        wrapper.orderByDesc(User::getCreateTime);
        return Result.success(userService.page(new Page<>(page, size), wrapper));
    }

    /** 新增员工 */
    @PostMapping
    public Result<User> create(@RequestBody User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword()));
        userService.save(user);
        return Result.success(user);
    }

    /** 编辑员工 */
    @PutMapping("/{id}")
    public Result<User> update(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(BCrypt.hashpw(user.getPassword()));
        } else {
            user.setPassword(null);
        }
        userService.updateById(user);
        return Result.success(user);
    }

    /** 冻结/解冻 */
    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestParam String status) {
        User user = userService.getById(id);
        if (user != null) {
            user.setStatus(status);
            userService.updateById(user);
        }
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        userService.removeById(id);
        return Result.success();
    }
}
