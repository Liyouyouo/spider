package com.jubensha.manager.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderCreateRequest {

    @NotNull(message = "剧本场次ID不能为空")
    private Long scriptSessionId;

    /**
     * 订单类型: CARPOOL-拼车, FULL_BOOKING-整车包场
     */
    @NotNull(message = "订单类型不能为空")
    private String orderType;

    /** 支付方式: WECHAT-微信, ALIPAY-支付宝 */
    private String payMethod;

    /** 支付类型: DEPOSIT-定金, FULL-全款 */
    private String payType;

    /** 使用优惠券ID */
    private Long couponId;

    /** 玩家备注 */
    private String remark;

    /** 参与者列表（整车包场时需要） */
    private List<ParticipantInfo> participants;

    @Data
    public static class ParticipantInfo {
        private String playerName;
        private String playerPhone;
    }
}
