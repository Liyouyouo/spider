package com.jubensha.manager.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jubensha.manager.model.dto.ScriptQueryRequest;
import com.jubensha.manager.model.entity.Script;
import com.jubensha.manager.model.vo.ScriptVO;

public interface ScriptService extends IService<Script> {

    Page<ScriptVO> queryScripts(ScriptQueryRequest request);

    ScriptVO getScriptDetail(Long id);

    void online(Long id);
    void offline(Long id);
    void incrementPlayCount(Long id);
}
