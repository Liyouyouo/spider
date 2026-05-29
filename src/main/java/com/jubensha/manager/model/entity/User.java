package com.jubensha.manager.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jubensha.manager.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_user")
public class User extends BaseEntity {

    /** 用户名 */
    private String username;

    /** 密码（加密存储） */
    private String password;

    /** 真实姓名 */
    private String realName;

    /** 手机号 */
    private String phone;

    /** 头像URL */
    private String avatar;

    /**
     * 角色: PLAYER-玩家, DM-主持人, ADMIN-管理员
     */
    private String role;

    /** 状态: ACTIVE-正常, FROZEN-冻结 */
    private String status;

    /** 微信openId（玩家端绑定） */
    private String openId;

    /** DM专属：提成比例（百分比） */
    private Integer commissionRate;

    /** DM专属：个人简介 */
    private String introduction;
}
