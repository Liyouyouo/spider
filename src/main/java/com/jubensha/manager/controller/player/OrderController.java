package com.jubensha.manager.controller.player;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jubensha.manager.common.Result;
import com.jubensha.manager.model.dto.OrderCreateRequest;
import com.jubensha.manager.model.entity.Order;
import com.jubensha.manager.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/player/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /** 创建订单（拼车/整车） */
    @PostMapping
    public Result<Order> create(@RequestAttribute Long userId,
                                 @Valid @RequestBody OrderCreateRequest request) {
        return Result.success(orderService.createOrder(userId, request));
    }

    /** 支付尾款 */
    @PostMapping("/{id}/pay")
    public Result<Order> pay(@PathVariable Long id) {
        return Result.success(orderService.payOrder(id));
    }

    /** 取消订单 */
    @PostMapping("/{id}/cancel")
    public Result<Order> cancel(@RequestAttribute Long userId, @PathVariable Long id) {
        return Result.success(orderService.cancelOrder(userId, id));
    }

    /** 我的订单列表 */
    @GetMapping
    public Result<Page<Order>> myOrders(@RequestAttribute Long userId,
                                         @RequestParam(required = false) String status,
                                         @RequestParam(defaultValue = "1") Integer page,
                                         @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(orderService.queryUserOrders(userId, status, page, size));
    }

    /** 订单详情 */
    @GetMapping("/{id}")
    public Result<Order> detail(@PathVariable Long id) {
        return Result.success(orderService.getById(id));
    }
}
