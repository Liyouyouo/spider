package com.jubensha.manager.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jubensha.manager.common.Result;
import com.jubensha.manager.model.entity.Script;
import com.jubensha.manager.service.ScriptService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/scripts")
@RequiredArgsConstructor
public class AdminScriptController {

    private final ScriptService scriptService;

    /** 剧本列表（含下架） */
    @GetMapping
    public Result<Page<Script>> list(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(scriptService.page(new Page<>(page, size)));
    }

    /** 新增剧本 */
    @PostMapping
    public Result<Script> create(@Valid @RequestBody Script script) {
        scriptService.save(script);
        return Result.success(script);
    }

    /** 编辑剧本 */
    @PutMapping("/{id}")
    public Result<Script> update(@PathVariable Long id, @RequestBody Script script) {
        script.setId(id);
        scriptService.updateById(script);
        return Result.success(script);
    }

    /** 剧本详情 */
    @GetMapping("/{id}")
    public Result<Script> detail(@PathVariable Long id) {
        return Result.success(scriptService.getById(id));
    }

    /** 上架 */
    @PostMapping("/{id}/online")
    public Result<Void> online(@PathVariable Long id) {
        scriptService.online(id);
        return Result.success();
    }

    /** 下架 */
    @PostMapping("/{id}/offline")
    public Result<Void> offline(@PathVariable Long id) {
        scriptService.offline(id);
        return Result.success();
    }

    /** 删除剧本 */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        scriptService.removeById(id);
        return Result.success();
    }
}
