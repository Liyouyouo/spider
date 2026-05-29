package com.jubensha.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jubensha.manager.model.entity.PaymentRecord;

public interface PaymentService extends IService<PaymentRecord> {
    void createPayment(Long orderId, String payMethod);
    void processRefund(Long orderId);
}
