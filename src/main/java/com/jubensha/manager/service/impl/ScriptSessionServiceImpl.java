package com.jubensha.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jubensha.manager.common.exception.BusinessException;
import com.jubensha.manager.dao.ScriptSessionMapper;
import com.jubensha.manager.model.entity.ScriptSession;
import com.jubensha.manager.service.ScriptSessionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScriptSessionServiceImpl extends ServiceImpl<ScriptSessionMapper, ScriptSession> implements ScriptSessionService {

    @Override
    public List<ScriptSession> getByScriptIdAndDate(Long scriptId, LocalDate date) {
        return list(new LambdaQueryWrapper<ScriptSession>()
                .eq(ScriptSession::getScriptId, scriptId)
                .eq(ScriptSession::getScheduleDate, date)
                .eq(ScriptSession::getStatus, "OPEN"));
    }

    @Override
    public List<ScriptSession> getSessionsByDate(LocalDate date) {
        return list(new LambdaQueryWrapper<ScriptSession>()
                .eq(ScriptSession::getScheduleDate, date));
    }

    @Override
    public void addPlayer(Long sessionId) {
        ScriptSession session = getById(sessionId);
        if (session != null) {
            session.setCurrentPlayers(session.getCurrentPlayers() + 1);
            if (session.getCurrentPlayers() >= session.getMaxPlayers()) {
                session.setStatus("FULL");
            }
            updateById(session);
        }
    }

    @Override
    public void removePlayer(Long sessionId) {
        ScriptSession session = getById(sessionId);
        if (session != null) {
            session.setCurrentPlayers(Math.max(0, session.getCurrentPlayers() - 1));
            if ("FULL".equals(session.getStatus())) {
                session.setStatus("OPEN");
            }
            updateById(session);
        }
    }

    @Override
    public void assignDm(Long sessionId, Long dmUserId) {
        ScriptSession session = getById(sessionId);
        if (session == null) {
            throw new BusinessException("场次不存在");
        }
        session.setDmUserId(dmUserId);
        updateById(session);
    }

    @Override
    public void updateStatus(Long sessionId, String status) {
        ScriptSession session = getById(sessionId);
        if (session != null) {
            session.setStatus(status);
            updateById(session);
        }
    }
}
