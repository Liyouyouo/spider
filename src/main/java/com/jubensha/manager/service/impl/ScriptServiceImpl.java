package com.jubensha.manager.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jubensha.manager.common.exception.BusinessException;
import com.jubensha.manager.dao.ScriptMapper;
import com.jubensha.manager.model.dto.ScriptQueryRequest;
import com.jubensha.manager.model.entity.Script;
import com.jubensha.manager.model.vo.ScriptVO;
import com.jubensha.manager.service.ScriptService;
import org.springframework.stereotype.Service;

@Service
public class ScriptServiceImpl extends ServiceImpl<ScriptMapper, Script> implements ScriptService {

    @Override
    public Page<ScriptVO> queryScripts(ScriptQueryRequest request) {
        LambdaQueryWrapper<Script> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Script::getStatus, "ONLINE");

        if (request.getCategory() != null && !request.getCategory().isEmpty()) {
            wrapper.eq(Script::getCategory, request.getCategory());
        }
        if (request.getDifficulty() != null && !request.getDifficulty().isEmpty()) {
            wrapper.eq(Script::getDifficulty, request.getDifficulty());
        }
        if (request.getPlayerCount() != null) {
            wrapper.eq(Script::getPlayerCount, request.getPlayerCount());
        }
        if (request.getKeyword() != null && !request.getKeyword().isEmpty()) {
            wrapper.like(Script::getName, request.getKeyword());
        }

        String sortBy = request.getSortBy();
        if ("POPULAR".equals(sortBy)) {
            wrapper.orderByDesc(Script::getPlayCount);
        } else if ("RATING".equals(sortBy)) {
            wrapper.orderByDesc(Script::getRating);
        } else if ("PRICE_ASC".equals(sortBy)) {
            wrapper.orderByAsc(Script::getPrice);
        } else {
            wrapper.orderByDesc(Script::getSortOrder);
            wrapper.orderByDesc(Script::getCreateTime);
        }

        Page<Script> page = page(new Page<>(request.getPage(), request.getSize()), wrapper);

        Page<ScriptVO> voPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        voPage.setRecords(page.getRecords().stream()
                .map(s -> BeanUtil.copyProperties(s, ScriptVO.class))
                .toList());
        return voPage;
    }

    @Override
    public ScriptVO getScriptDetail(Long id) {
        Script script = getById(id);
        if (script == null || "OFFLINE".equals(script.getStatus())) {
            throw new BusinessException("剧本不存在或已下架");
        }
        return BeanUtil.copyProperties(script, ScriptVO.class);
    }

    @Override
    public void online(Long id) {
        Script script = getById(id);
        if (script == null) {
            throw new BusinessException("剧本不存在");
        }
        script.setStatus("ONLINE");
        updateById(script);
    }

    @Override
    public void offline(Long id) {
        Script script = getById(id);
        if (script == null) {
            throw new BusinessException("剧本不存在");
        }
        script.setStatus("OFFLINE");
        updateById(script);
    }

    @Override
    public void incrementPlayCount(Long id) {
        Script script = getById(id);
        if (script != null) {
            script.setPlayCount(script.getPlayCount() + 1);
            updateById(script);
        }
    }
}
