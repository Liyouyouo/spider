package com.jubensha.manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jubensha.manager.dao.DmPerformanceMapper;
import com.jubensha.manager.model.entity.DmPerformance;
import com.jubensha.manager.service.DmPerformanceService;
import org.springframework.stereotype.Service;

@Service
public class DmPerformanceServiceImpl extends ServiceImpl<DmPerformanceMapper, DmPerformance> implements DmPerformanceService {
}
