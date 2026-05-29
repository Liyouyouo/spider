package com.jubensha.manager.controller.dm;

import com.jubensha.manager.common.Result;
import com.jubensha.manager.model.entity.ScriptSession;
import com.jubensha.manager.service.ScriptSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/dm/workbench")
@RequiredArgsConstructor
public class DMWorkbenchController {

    private final ScriptSessionService scriptSessionService;

    /** DM今日待开本列表 */
    @GetMapping("/today")
    public Result<List<ScriptSession>> todaySessions(@RequestAttribute Long userId) {
        return Result.success(scriptSessionService.getSessionsByDate(LocalDate.now()).stream()
                .filter(s -> userId.equals(s.getDmUserId()))
                .toList());
    }
}
