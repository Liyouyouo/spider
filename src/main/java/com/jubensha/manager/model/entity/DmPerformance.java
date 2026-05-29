package com.jubensha.manager.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jubensha.manager.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_dm_performance")
public class DmPerformance extends BaseEntity {

    /** DM用户ID */
    private Long dmUserId;

    /** 统计日期 */
    private LocalDate statDate;

    /** 当天开本数量 */
    private Integer dailySessionCount;

    /** 当月开本数量 */
    private Integer monthlySessionCount;

    /** 当天提成金额 */
    private BigDecimal dailyCommission;

    /** 当月累计提成 */
    private BigDecimal monthlyCommission;

    /** 玩家评分均分 */
    private BigDecimal avgRating;
}
