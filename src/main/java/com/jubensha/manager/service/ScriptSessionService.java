package com.jubensha.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jubensha.manager.model.entity.ScriptSession;

import java.time.LocalDate;
import java.util.List;

public interface ScriptSessionService extends IService<ScriptSession> {

    List<ScriptSession> getByScriptIdAndDate(Long scriptId, LocalDate date);

    List<ScriptSession> getSessionsByDate(LocalDate date);

    void addPlayer(Long sessionId);

    void removePlayer(Long sessionId);

    void assignDm(Long sessionId, Long dmUserId);

    void updateStatus(Long sessionId, String status);
}
