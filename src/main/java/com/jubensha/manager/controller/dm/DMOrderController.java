package com.jubensha.manager.controller.dm;

import com.jubensha.manager.common.Result;
import com.jubensha.manager.model.entity.Order;
import com.jubensha.manager.model.entity.OrderParticipant;
import com.jubensha.manager.service.OrderParticipantService;
import com.jubensha.manager.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dm/orders")
@RequiredArgsConstructor
public class DMOrderController {

    private final OrderService orderService;
    private final OrderParticipantService orderParticipantService;

    /** 订单详情 */
    @GetMapping("/{id}")
    public Result<Order> detail(@PathVariable Long id) {
        return Result.success(orderService.getById(id));
    }

    /** 查看本场玩家名单 */
    @GetMapping("/{id}/participants")
    public Result<List<OrderParticipant>> participants(@PathVariable Long id) {
        return Result.success(orderParticipantService.getByOrderId(id));
    }

    /** 玩家签到 */
    @PostMapping("/{orderId}/checkin/{participantId}")
    public Result<Void> checkIn(@PathVariable Long orderId, @PathVariable Long participantId) {
        orderService.checkInParticipant(orderId, participantId);
        return Result.success();
    }

    /** 开始开本 */
    @PostMapping("/{id}/start")
    public Result<Void> startSession(@PathVariable Long id) {
        orderService.startSession(id);
        return Result.success();
    }

    /** 结束开本/核销 */
    @PostMapping("/{id}/finish")
    public Result<Void> finishSession(@PathVariable Long id) {
        orderService.finishSession(id);
        return Result.success();
    }
}
