package com.jubensha.manager.controller.player;

import com.jubensha.manager.common.Result;
import com.jubensha.manager.model.entity.User;
import com.jubensha.manager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/player/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /** 获取个人资料 */
    @GetMapping("/profile")
    public Result<User> profile(@RequestAttribute Long userId) {
        return Result.success(userService.getById(userId));
    }

    /** 修改密码 */
    @PutMapping("/password")
    public Result<Void> updatePassword(@RequestAttribute Long userId,
                                        @RequestParam String oldPwd,
                                        @RequestParam String newPwd) {
        userService.updatePassword(userId, oldPwd, newPwd);
        return Result.success();
    }
}
