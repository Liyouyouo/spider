package com.jubensha.manager.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jubensha.manager.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_session")
public class Session extends BaseEntity {

    /** 场次名称: 如"下午场"、"晚场"、"通宵场" */
    private String name;

    /** 开始时间 */
    private LocalTime startTime;

    /** 结束时间 */
    private LocalTime endTime;

    /** 排序 */
    private Integer sortOrder;

    /** 是否启用 */
    private Boolean enabled;
}
