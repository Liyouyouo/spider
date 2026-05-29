package com.jubensha.manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jubensha.manager.dao.PaymentRecordMapper;
import com.jubensha.manager.model.entity.PaymentRecord;
import com.jubensha.manager.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentRecordMapper, PaymentRecord> implements PaymentService {

    @Override
    public void createPayment(Long orderId, String payMethod) {
        // 模拟支付 - 实际对接微信/支付宝后替换
        System.out.println("创建支付: orderId=" + orderId + ", method=" + payMethod);
    }

    @Override
    public void processRefund(Long orderId) {
        // 模拟退款 - 实际对接微信/支付宝后替换
        System.out.println("处理退款: orderId=" + orderId);
    }
}
