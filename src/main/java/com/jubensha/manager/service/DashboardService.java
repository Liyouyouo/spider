package com.jubensha.manager.service;

import java.util.Map;

public interface DashboardService {
    Map<String, Object> getTodaySummary();
    Map<String, Object> getWeeklyRevenue();
    Map<String, Object> getHotScripts();
    Map<String, Object> getDmRanking();
}
