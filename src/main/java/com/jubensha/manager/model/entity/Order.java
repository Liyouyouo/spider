package com.jubensha.manager.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jubensha.manager.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_order")
public class Order extends BaseEntity {

    /** 订单编号（唯一业务编号） */
    private String orderNo;

    /** 剧本场次ID */
    private Long scriptSessionId;

    /** 下单玩家ID */
    private Long userId;

    /** 剧本ID */
    private Long scriptId;

    /** 剧本名称（冗余） */
    private String scriptName;

    /** 房间名称（冗余） */
    private String roomName;

    /** 场次时间（冗余） */
    private LocalDateTime sessionTime;

    /** DM姓名（冗余） */
    private String dmName;

    /** 订单总金额 */
    private BigDecimal totalAmount;

    /** 实付金额 */
    private BigDecimal paidAmount;

    /** 定金金额 */
    private BigDecimal depositAmount;

    /** 支付方式: WECHAT-微信, ALIPAY-支付宝, OFFLINE-线下 */
    private String payMethod;

    /**
     * 订单类型: CARPOOL-拼车, FULL_BOOKING-整车包场
     */
    private String orderType;

    /**
     * 订单状态:
     * WAITING_CARPOOL-待拼车, CARPOOL_SUCCESS-拼车成功,
     * WAITING_PLAY-待开本, PLAYING-进行中,
     * FINISHED-已完成, CANCELLED-已取消, REFUNDED-已退款
     */
    private String status;

    /** 支付时间 */
    private LocalDateTime payTime;

    /** 退款时间 */
    private LocalDateTime refundTime;

    /** 退款金额 */
    private BigDecimal refundAmount;

    /** 玩家备注 */
    private String remark;
}
