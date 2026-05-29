package com.jubensha.manager.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jubensha.manager.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_room")
public class Room extends BaseEntity {

    /** 包间名称 */
    private String name;

    /** 包间风格: HORROR-恐怖, ANCIENT-古风, MODERN-现代, IMMERSIVE-沉浸式 */
    private String style;

    /** 容纳人数上限 */
    private Integer capacity;

    /** 房间描述 */
    private String description;

    /**
     * 状态: IDLE-空闲, OCCUPIED-占用, MAINTENANCE-维护中
     */
    private String status;
}
