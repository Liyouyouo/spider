package com.jubensha.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jubensha.manager.dao.SessionMapper;
import com.jubensha.manager.model.entity.Session;
import com.jubensha.manager.service.SessionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionServiceImpl extends ServiceImpl<SessionMapper, Session> implements SessionService {

    @Override
    public List<Session> getEnabledSessions() {
        return list(new LambdaQueryWrapper<Session>()
                .eq(Session::getEnabled, true)
                .orderByAsc(Session::getSortOrder));
    }
}
