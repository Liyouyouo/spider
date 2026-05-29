package com.jubensha.manager.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DM信息响应VO，包含DM用户基本信息和绩效汇总
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DmInfoVO {

    /** ID */
    private Long id;
    /** 用户名 */
    private String username;
    /** 真实姓名 */
    private String realName;
    /** 手机号 */
    private String phone;
    /** 头像URL */
    private String avatar;
    /** 角色: DM */
    private String role;
    /** 状态: ACTIVE-正常, FROZEN-冻结 */
    private String status;
    /** 提成比例（百分比） */
    private Integer commissionRate;
    /** 个人简介 */
    private String introduction;
    /** 创建时间 */
    private LocalDateTime createTime;

    /** 累计开本数 */
    private Integer totalSessions;
    /** 平均评分 */
    private BigDecimal avgRating;
    /** 累计提成 */
    private BigDecimal totalCommission;
}
