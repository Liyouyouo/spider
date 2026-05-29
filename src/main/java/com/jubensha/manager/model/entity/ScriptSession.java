package com.jubensha.manager.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jubensha.manager.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_script_session")
public class ScriptSession extends BaseEntity {

    /** 剧本ID */
    private Long scriptId;

    /** 房间ID */
    private Long roomId;

    /** 场次ID（时段） */
    private Long sessionId;

    /** DM主持人用户ID */
    private Long dmUserId;

    /** 排期日期 */
    private LocalDate scheduleDate;

    /** 最大拼车人数 */
    private Integer maxPlayers;

    /** 当前已拼人数 */
    private Integer currentPlayers;

    /**
     * 状态: OPEN-开放预约, FULL-已满, CANCELLED-已取消, IN_PROGRESS-进行中, FINISHED-已完成
     */
    private String status;

    /** 是否整车包场 */
    private Boolean isFullBooking;
}
