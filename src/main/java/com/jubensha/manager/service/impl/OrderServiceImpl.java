package com.jubensha.manager.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jubensha.manager.common.exception.BusinessException;
import com.jubensha.manager.dao.OrderMapper;
import com.jubensha.manager.model.dto.OrderCreateRequest;
import com.jubensha.manager.model.entity.*;
import com.jubensha.manager.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    private final ScriptSessionService scriptSessionService;
    private final ScriptService scriptService;
    private final MemberService memberService;
    private final PaymentService paymentService;
    private final OrderParticipantService orderParticipantService;

    @Override
    @Transactional
    public Order createOrder(Long userId, OrderCreateRequest request) {
        ScriptSession session = scriptSessionService.getById(request.getScriptSessionId());
        if (session == null || !"OPEN".equals(session.getStatus())) {
            throw new BusinessException("该场次不可预约");
        }

        Script script = scriptService.getById(session.getScriptId());
        if (script == null || !"ONLINE".equals(script.getStatus())) {
            throw new BusinessException("剧本已下架，无法预约");
        }

        if ("CARPOOL".equals(request.getOrderType())) {
            if (session.getCurrentPlayers() >= session.getMaxPlayers()) {
                throw new BusinessException("该场次已满员");
            }
            if (session.getIsFullBooking()) {
                throw new BusinessException("该场次为整车包场，不支持拼车");
            }
        }

        BigDecimal amount = script.getPrice();
        boolean isDeposit = "DEPOSIT".equals(request.getPayType());
        BigDecimal depositAmount = script.getPrice().multiply(BigDecimal.valueOf(0.3));
        BigDecimal paidAmount = isDeposit ? depositAmount : amount;

        Order order = new Order();
        order.setOrderNo(IdUtil.getSnowflakeNextIdStr());
        order.setScriptSessionId(request.getScriptSessionId());
        order.setUserId(userId);
        order.setScriptId(script.getId());
        order.setScriptName(script.getName());
        order.setRoomName(session.getRoomId() != null ? session.getRoomId().toString() : "");
        order.setSessionTime(session.getScheduleDate().atTime(
                java.time.LocalTime.MIDNIGHT));
        order.setTotalAmount(amount);
        order.setPaidAmount(paidAmount);
        order.setDepositAmount(depositAmount);
        order.setPayMethod(request.getPayMethod());
        order.setOrderType(request.getOrderType());
        order.setStatus(isDeposit ? "WAITING_CARPOOL" : "CARPOOL_SUCCESS");
        order.setRemark(request.getRemark());
        order.setPayTime(LocalDateTime.now());

        save(order);

        if (request.getParticipants() != null && !request.getParticipants().isEmpty()) {
            for (OrderCreateRequest.ParticipantInfo p : request.getParticipants()) {
                OrderParticipant op = new OrderParticipant();
                op.setOrderId(order.getId());
                op.setUserId(userId);
                op.setPlayerName(p.getPlayerName());
                op.setPlayerPhone(p.getPlayerPhone());
                op.setCheckedIn(false);
                orderParticipantService.save(op);
            }
        }

        paymentService.createPayment(order.getId(), request.getPayMethod());
        scriptSessionService.addPlayer(session.getId());

        return order;
    }

    @Override
    @Transactional
    public Order payOrder(Long orderId) {
        Order order = getById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        order.setStatus("CARPOOL_SUCCESS");
        order.setPaidAmount(order.getTotalAmount());
        order.setPayTime(LocalDateTime.now());
        updateById(order);
        return order;
    }

    @Override
    @Transactional
    public Order cancelOrder(Long userId, Long orderId) {
        Order order = getById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        if (!order.getUserId().equals(userId)) {
            throw new BusinessException("无权操作该订单");
        }

        // 开场前2小时免费取消
        long hoursUntilSession = ChronoUnit.HOURS.between(LocalDateTime.now(), order.getSessionTime());
        if (hoursUntilSession < 2) {
            throw new BusinessException("距离开场不足2小时，无法取消订单");
        }

        order.setStatus("CANCELLED");
        updateById(order);
        scriptSessionService.removePlayer(order.getScriptSessionId());

        if (order.getPaidAmount().compareTo(BigDecimal.ZERO) > 0) {
            paymentService.processRefund(orderId);
        }

        return order;
    }

    @Override
    @Transactional
    public Order refundOrder(Long orderId) {
        Order order = getById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        order.setStatus("REFUNDED");
        order.setRefundTime(LocalDateTime.now());
        order.setRefundAmount(order.getPaidAmount());
        updateById(order);
        paymentService.processRefund(orderId);
        return order;
    }

    @Override
    public void checkInParticipant(Long orderId, Long participantId) {
        OrderParticipant op = orderParticipantService.getById(participantId);
        if (op == null || !op.getOrderId().equals(orderId)) {
            throw new BusinessException("参与者记录不存在");
        }
        op.setCheckedIn(true);
        op.setCheckInTime(LocalDateTime.now());
        orderParticipantService.updateById(op);
    }

    @Override
    @Transactional
    public void startSession(Long orderId) {
        Order order = getById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        order.setStatus("PLAYING");
        updateById(order);
        scriptSessionService.updateStatus(order.getScriptSessionId(), "IN_PROGRESS");
    }

    @Override
    @Transactional
    public void finishSession(Long orderId) {
        Order order = getById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        order.setStatus("FINISHED");
        updateById(order);

        scriptSessionService.updateStatus(order.getScriptSessionId(), "FINISHED");
        scriptService.incrementPlayCount(order.getScriptId());
        memberService.addPoints(order.getUserId(), order.getTotalAmount().intValue());
    }

    @Override
    public Page<Order> queryUserOrders(Long userId, String status, Integer page, Integer size) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getUserId, userId);
        if (status != null && !status.isEmpty()) {
            wrapper.eq(Order::getStatus, status);
        }
        wrapper.orderByDesc(Order::getCreateTime);
        return page(new Page<>(page, size), wrapper);
    }

    @Override
    public Page<Order> queryAllOrders(String status, String orderType, Integer page, Integer size) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        if (status != null && !status.isEmpty()) {
            wrapper.eq(Order::getStatus, status);
        }
        if (orderType != null && !orderType.isEmpty()) {
            wrapper.eq(Order::getOrderType, orderType);
        }
        wrapper.orderByDesc(Order::getCreateTime);
        return page(new Page<>(page, size), wrapper);
    }
}
