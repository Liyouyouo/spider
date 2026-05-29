package com.jubensha.manager.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jubensha.manager.common.Result;
import com.jubensha.manager.model.entity.Session;
import com.jubensha.manager.model.entity.SystemConfig;
import com.jubensha.manager.service.SessionService;
import com.jubensha.manager.service.SystemConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/config")
@RequiredArgsConstructor
public class AdminConfigController {

    private final SystemConfigService systemConfigService;
    private final SessionService sessionService;

    // ===== 系统参数配置 =====

    @GetMapping("/system")
    public Result<List<SystemConfig>> listSystemConfig() {
        return Result.success(systemConfigService.list());
    }

    @PutMapping("/system/{id}")
    public Result<SystemConfig> updateSystemConfig(@PathVariable Long id,
                                                     @RequestBody SystemConfig config) {
        config.setId(id);
        systemConfigService.updateById(config);
        return Result.success(config);
    }

    // ===== 场次时段配置 =====

    @GetMapping("/sessions")
    public Result<List<Session>> listSessions() {
        return Result.success(sessionService.list());
    }

    @PostMapping("/sessions")
    public Result<Session> createSession(@RequestBody Session session) {
        sessionService.save(session);
        return Result.success(session);
    }

    @PutMapping("/sessions/{id}")
    public Result<Session> updateSession(@PathVariable Long id, @RequestBody Session session) {
        session.setId(id);
        sessionService.updateById(session);
        return Result.success(session);
    }

    @DeleteMapping("/sessions/{id}")
    public Result<Void> deleteSession(@PathVariable Long id) {
        sessionService.removeById(id);
        return Result.success();
    }
}
