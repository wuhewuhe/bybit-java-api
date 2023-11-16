package com.bybit.api.client.domain.trade.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CancelAllOrdersRequest {
    private String symbol;
    private String category;
    private String baseCoin;
    private String settleCoin;
    private String orderFilter;
    private String stopOrderType;
}
