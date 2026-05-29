package com.jubensha.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jubensha.manager.model.entity.SystemConfig;

public interface SystemConfigService extends IService<SystemConfig> {
    String getConfigValue(String key);
}
