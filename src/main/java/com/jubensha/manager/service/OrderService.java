package com.jubensha.manager.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jubensha.manager.model.dto.OrderCreateRequest;
import com.jubensha.manager.model.entity.Order;

public interface OrderService extends IService<Order> {

    Order createOrder(Long userId, OrderCreateRequest request);

    Order payOrder(Long orderId);

    Order cancelOrder(Long userId, Long orderId);

    Order refundOrder(Long orderId);

    void checkInParticipant(Long orderId, Long participantId);

    void startSession(Long orderId);

    void finishSession(Long orderId);

    Page<Order> queryUserOrders(Long userId, String status, Integer page, Integer size);

    Page<Order> queryAllOrders(String status, String orderType, Integer page, Integer size);
}
