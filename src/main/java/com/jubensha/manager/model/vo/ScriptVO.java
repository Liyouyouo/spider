package com.jubensha.manager.model.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ScriptVO {

    private Long id;
    private String name;
    private String coverImage;
    private String category;
    private String difficulty;
    private Integer playerCount;
    private Integer duration;
    private String description;
    private String characters;
    private BigDecimal price;
    private BigDecimal memberPrice;
    private String status;
    private Integer playCount;
    private BigDecimal rating;
    private LocalDateTime createTime;
}
