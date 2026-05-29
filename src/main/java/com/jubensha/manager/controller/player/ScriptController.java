package com.jubensha.manager.controller.player;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jubensha.manager.common.Result;
import com.jubensha.manager.model.dto.ScriptQueryRequest;
import com.jubensha.manager.model.vo.ScriptVO;
import com.jubensha.manager.service.ScriptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/player/scripts")
@RequiredArgsConstructor
public class ScriptController {

    private final ScriptService scriptService;

    /** 剧本列表（支持多维度筛选） */
    @GetMapping
    public Result<Page<ScriptVO>> list(ScriptQueryRequest request) {
        return Result.success(scriptService.queryScripts(request));
    }

    /** 剧本详情 */
    @GetMapping("/{id}")
    public Result<ScriptVO> detail(@PathVariable Long id) {
        return Result.success(scriptService.getScriptDetail(id));
    }
}
