package com.jubensha.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jubensha.manager.model.entity.OrderParticipant;

import java.util.List;

public interface OrderParticipantService extends IService<OrderParticipant> {
    List<OrderParticipant> getByOrderId(Long orderId);
}
