package com.jubensha.manager.controller.admin;

import com.jubensha.manager.common.Result;
import com.jubensha.manager.model.entity.ScriptSession;
import com.jubensha.manager.service.ScriptSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/sessions")
@RequiredArgsConstructor
public class AdminSessionController {

    private final ScriptSessionService scriptSessionService;

    @GetMapping
    public Result<List<ScriptSession>> list(@RequestParam String date) {
        return Result.success(scriptSessionService.getSessionsByDate(java.time.LocalDate.parse(date)));
    }

    @PostMapping
    public Result<ScriptSession> create(@RequestBody ScriptSession session) {
        scriptSessionService.save(session);
        return Result.success(session);
    }

    @PutMapping("/{id}")
    public Result<ScriptSession> update(@PathVariable Long id, @RequestBody ScriptSession session) {
        session.setId(id);
        scriptSessionService.updateById(session);
        return Result.success(session);
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        scriptSessionService.removeById(id);
        return Result.success();
    }

    @PutMapping("/{id}/assign-dm")
    public Result<Void> assignDm(@PathVariable Long id, @RequestParam Long dmUserId) {
        scriptSessionService.assignDm(id, dmUserId);
        return Result.success();
    }
}
