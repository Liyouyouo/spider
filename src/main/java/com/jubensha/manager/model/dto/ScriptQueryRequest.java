package com.jubensha.manager.model.dto;

import lombok.Data;

@Data
public class ScriptQueryRequest {

    /** 题材分类 */
    private String category;

    /** 难度 */
    private String difficulty;

    /** 人数筛选 */
    private Integer playerCount;

    /** 关键词搜索 */
    private String keyword;

    /** 排序: NEWEST-最新, POPULAR-最多游玩, RATING-评分最高, PRICE_ASC-价格最低 */
    private String sortBy;

    /** 页码 */
    private Integer page = 1;

    /** 每页大小 */
    private Integer size = 10;
}
