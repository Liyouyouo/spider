package com.jubensha.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jubensha.manager.model.entity.Session;

import java.util.List;

public interface SessionService extends IService<Session> {
    List<Session> getEnabledSessions();
}
