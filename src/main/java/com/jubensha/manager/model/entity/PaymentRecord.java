package com.jubensha.manager.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jubensha.manager.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_payment_record")
public class PaymentRecord extends BaseEntity {

    /** 订单ID */
    private Long orderId;

    /** 交易流水号 */
    private String transactionNo;

    /** 支付金额 */
    private BigDecimal amount;

    /**
     * 类型: PAY-支付, REFUND-退款
     */
    private String type;

    /** 支付方式: WECHAT-微信, ALIPAY-支付宝, OFFLINE-线下 */
    private String payMethod;

    /** 支付状态: PENDING-待支付, SUCCESS-成功, FAILED-失败 */
    private String status;

    /** 支付完成时间 */
    private LocalDateTime payTime;
}
