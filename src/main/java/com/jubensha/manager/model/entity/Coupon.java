package com.jubensha.manager.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jubensha.manager.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_coupon")
public class Coupon extends BaseEntity {

    /** 优惠券名称 */
    private String name;

    /** 类型: DISCOUNT-折扣券, CASH-现金抵扣券 */
    private String type;

    /** 面额/折扣值 */
    @TableField("coupon_value")
    private BigDecimal couponValue;

    /** 最低消费门槛 */
    private BigDecimal minAmount;

    /** 发放总量 */
    private Integer totalCount;

    /** 已领取数量 */
    private Integer claimedCount;

    /** 每人限领张数 */
    private Integer limitPerUser;

    /** 有效期开始 */
    private LocalDateTime validFrom;

    /** 有效期结束 */
    private LocalDateTime validTo;

    /** 是否启用 */
    private Boolean enabled;
}
