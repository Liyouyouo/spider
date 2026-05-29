package com.jubensha.manager.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jubensha.manager.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_order_participant")
public class OrderParticipant extends BaseEntity {

    /** 订单ID */
    private Long orderId;

    /** 玩家用户ID */
    private Long userId;

    /** 玩家姓名 */
    private String playerName;

    /** 玩家手机号 */
    private String playerPhone;

    /** 是否已签到 */
    private Boolean checkedIn;

    /** 签到时间 */
    private java.time.LocalDateTime checkInTime;
}
