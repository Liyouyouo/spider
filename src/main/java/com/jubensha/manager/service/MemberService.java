package com.jubensha.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jubensha.manager.model.entity.Member;

public interface MemberService extends IService<Member> {
    Member getByUserId(Long userId);
    void addPoints(Long userId, Integer points);
    void deductPoints(Long userId, Integer points);
}
