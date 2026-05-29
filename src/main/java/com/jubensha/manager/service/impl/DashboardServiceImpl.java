package com.jubensha.manager.service.impl;

import com.jubensha.manager.service.DashboardService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Override
    public Map<String, Object> getTodaySummary() {
        Map<String, Object> summary = new LinkedHashMap<>();
        summary.put("todayRevenue", BigDecimal.valueOf(0));
        summary.put("todayOrderCount", 0);
        summary.put("todaySessionCount", 0);
        summary.put("newMemberCount", 0);
        summary.put("date", LocalDate.now().toString());
        return summary;
    }

    @Override
    public Map<String, Object> getWeeklyRevenue() {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("labels", Arrays.asList("周一", "周二", "周三", "周四", "周五", "周六", "周日"));
        data.put("values", Arrays.asList(0, 0, 0, 0, 0, 0, 0));
        return data;
    }

    @Override
    public Map<String, Object> getHotScripts() {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("items", Collections.emptyList());
        return data;
    }

    @Override
    public Map<String, Object> getDmRanking() {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("items", Collections.emptyList());
        return data;
    }
}
