package com.jubensha.manager.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jubensha.manager.common.Result;
import com.jubensha.manager.model.entity.Member;
import com.jubensha.manager.model.entity.User;
import com.jubensha.manager.service.MemberService;
import com.jubensha.manager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/members")
@RequiredArgsConstructor
public class AdminMemberController {

    private final MemberService memberService;

    @GetMapping
    public Result<Page<Member>> list(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(memberService.page(new Page<>(page, size)));
    }

    /** 手动充值积分 */
    @PostMapping("/{id}/add-points")
    public Result<Void> addPoints(@PathVariable Long id, @RequestParam Integer points) {
        Member member = memberService.getById(id);
        if (member != null) {
            member.setPoints(member.getPoints() + points);
            memberService.updateById(member);
        }
        return Result.success();
    }
}
