package com.jubensha.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jubensha.manager.dao.OrderParticipantMapper;
import com.jubensha.manager.model.entity.OrderParticipant;
import com.jubensha.manager.service.OrderParticipantService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderParticipantServiceImpl extends ServiceImpl<OrderParticipantMapper, OrderParticipant> implements OrderParticipantService {

    @Override
    public List<OrderParticipant> getByOrderId(Long orderId) {
        return list(new LambdaQueryWrapper<OrderParticipant>()
                .eq(OrderParticipant::getOrderId, orderId));
    }
}
