package com.jubensha.manager.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jubensha.manager.common.Result;
import com.jubensha.manager.model.dto.OrderCreateRequest;
import com.jubensha.manager.model.entity.Order;
import com.jubensha.manager.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderService orderService;

    /** 全部订单列表 */
    @GetMapping
    public Result<Page<Order>> list(@RequestParam(required = false) String status,
                                     @RequestParam(required = false) String orderType,
                                     @RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(orderService.queryAllOrders(status, orderType, page, size));
    }

    /** 线下手动创建订单 */
    @PostMapping("/offline")
    public Result<Order> createOffline(@RequestAttribute Long userId,
                                        @Valid @RequestBody OrderCreateRequest request) {
        return Result.success(orderService.createOrder(userId, request));
    }

    /** 退款审核 */
    @PostMapping("/{id}/refund")
    public Result<Order> refund(@PathVariable Long id) {
        return Result.success(orderService.refundOrder(id));
    }

    /** 订单详情 */
    @GetMapping("/{id}")
    public Result<Order> detail(@PathVariable Long id) {
        return Result.success(orderService.getById(id));
    }
}
