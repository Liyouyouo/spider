package com.jubensha.manager.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jubensha.manager.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_member")
public class Member extends BaseEntity {

    /** 关联用户ID */
    private Long userId;

    /**
     * 会员等级: NORMAL-普通, SILVER-银卡, GOLD-金卡, DIAMOND-钻石
     */
    private String level;

    /** 账户积分 */
    private Integer points;

    /** 累计消费金额 */
    private BigDecimal totalSpent;

    /** 累计开本次数 */
    private Integer totalPlays;
}
