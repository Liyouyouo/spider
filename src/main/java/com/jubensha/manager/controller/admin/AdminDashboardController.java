package com.jubensha.manager.controller.admin;

import com.jubensha.manager.common.Result;
import com.jubensha.manager.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final DashboardService dashboardService;

    /** 今日数据总览 */
    @GetMapping("/today")
    public Result<Map<String, Object>> todaySummary() {
        return Result.success(dashboardService.getTodaySummary());
    }

    /** 近7日营收趋势 */
    @GetMapping("/weekly-revenue")
    public Result<Map<String, Object>> weeklyRevenue() {
        return Result.success(dashboardService.getWeeklyRevenue());
    }

    /** 热门剧本排行 */
    @GetMapping("/hot-scripts")
    public Result<Map<String, Object>> hotScripts() {
        return Result.success(dashboardService.getHotScripts());
    }

    /** DM开本排行 */
    @GetMapping("/dm-ranking")
    public Result<Map<String, Object>> dmRanking() {
        return Result.success(dashboardService.getDmRanking());
    }
}
