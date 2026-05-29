package com.jubensha.manager.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jubensha.manager.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_script")
public class Script extends BaseEntity {

    /** 剧本名称 */
    private String name;

    /** 封面图片URL */
    private String coverImage;

    /** 题材分类 */
    private String category;

    /** 难度: NOVICE-新手, ADVANCED-进阶, HARD-硬核 */
    private String difficulty;

    /** 所需人数 */
    private Integer playerCount;

    /** 游玩时长（分钟） */
    private Integer duration;

    /** 剧本简介 */
    private String description;

    /** 人物介绍（JSON数组） */
    private String characters;

    /** 标准价格 */
    private BigDecimal price;

    /** 会员价格 */
    private BigDecimal memberPrice;

    /** 节假日加价 */
    private BigDecimal holidaySurcharge;

    /** DM复盘资料URL */
    private String dmMaterialUrl;

    /** DM组织者手册URL */
    private String dmManualUrl;

    /** 状态: ONLINE-上架, OFFLINE-下架, DRAFT-草稿 */
    private String status;

    /** 累计游玩次数 */
    private Integer playCount;

    /** 综合评分 */
    private BigDecimal rating;

    /** 排序权重（越大越靠前） */
    private Integer sortOrder;
}
