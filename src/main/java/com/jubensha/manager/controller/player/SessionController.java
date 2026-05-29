package com.jubensha.manager.controller.player;

import com.jubensha.manager.common.Result;
import com.jubensha.manager.model.entity.ScriptSession;
import com.jubensha.manager.service.ScriptSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/player/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final ScriptSessionService scriptSessionService;

    /** 查看某剧本某天的场次安排 */
    @GetMapping
    public Result<List<ScriptSession>> list(@RequestParam Long scriptId,
                                             @RequestParam String date) {
        return Result.success(scriptSessionService.getByScriptIdAndDate(
                scriptId, LocalDate.parse(date)));
    }

    /** 场次拼车详情 */
    @GetMapping("/{id}")
    public Result<ScriptSession> detail(@PathVariable Long id) {
        return Result.success(scriptSessionService.getById(id));
    }
}
