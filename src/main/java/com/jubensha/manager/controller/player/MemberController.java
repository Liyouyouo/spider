package com.jubensha.manager.controller.player;

import com.jubensha.manager.common.Result;
import com.jubensha.manager.model.entity.Member;
import com.jubensha.manager.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/player/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /** 我的会员信息 */
    @GetMapping
    public Result<Member> myMember(@RequestAttribute Long userId) {
        return Result.success(memberService.getByUserId(userId));
    }
}
