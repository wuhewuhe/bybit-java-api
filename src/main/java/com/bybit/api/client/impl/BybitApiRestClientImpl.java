package com.bybit.api.client.impl;

import com.bybit.api.client.domain.market.MarketInterval;
import com.bybit.api.client.domain.ProductType;
import com.bybit.api.client.domain.market.request.*;
import com.bybit.api.client.domain.position.request.PositionListRequest;
import com.bybit.api.client.domain.position.request.SetLeverageRequest;
import com.bybit.api.client.domain.trade.requests.*;
import com.bybit.api.client.domain.user.request.ApiKeyRequest;
import com.bybit.api.client.domain.user.request.FreezeSubUIDRquest;
import com.bybit.api.client.domain.user.request.SubUserRequest;
import com.bybit.api.client.service.BybitApiService;

import static com.bybit.api.client.service.BybitApiServiceGenerator.createService;
import static com.bybit.api.client.service.BybitApiServiceGenerator.executeSync;

/**
 * Implementation of Bybit's REST API using Retrofit with synchronous/blocking
 * method calls.
 */
public class BybitApiRestClientImpl implements BybitApiRestClient {
    private final BybitApiService bybitApiService;

    public BybitApiRestClientImpl(String apiKey, String secret) {
        bybitApiService = createService(BybitApiService.class, apiKey, secret);
    }

    // Market Data endpoints
    @Override
    public Object getServerTime() {
        return executeSync(
                bybitApiService.getServerTime());
    }

    @Override
    public Object getMarketLinesData(ProductType category, String symbol, MarketInterval interval, Integer limit, Long startTime, Long endTime) {
        return executeSync(
                bybitApiService.getMarketLinesData(category.getProductTypeId(), symbol, interval.getIntervalId(), limit, startTime, endTime));
    }

    @Override
    public Object getMarketLinesData(ProductType category, String symbol, MarketInterval interval) {
        return getMarketLinesData(category, symbol, interval, null, null, null);
    }

    @Override
    public Object getMarketPriceLinesData(ProductType category, String symbol, MarketInterval interval, Integer limit, Long startTime, Long endTime) {
        return executeSync(
                bybitApiService.getMarketPriceLinesData(category.getProductTypeId(), symbol, interval.getIntervalId(), limit, startTime, endTime));
    }

    @Override
    public Object getMarketPriceLinesData(ProductType category, String symbol, MarketInterval interval) {
        return getMarketPriceLinesData(category, symbol, interval, null, null, null);
    }

    @Override
    public Object getIndexPriceLinesData(ProductType category, String symbol, MarketInterval interval, Integer limit, Long startTime, Long endTime) {
        return executeSync(
                bybitApiService.getIndexPriceLinesData(category.getProductTypeId(), symbol, interval.getIntervalId(), limit, startTime, endTime));
    }

    @Override
    public Object getIndexPriceLinesData(ProductType category, String symbol, MarketInterval interval) {
        return getIndexPriceLinesData(category, symbol, interval, null, null, null);
    }

    @Override
    public Object getPremiumIndexPriceLinesData(ProductType category, String symbol, MarketInterval interval, Integer limit, Long startTime, Long endTime) {
        return executeSync(
                bybitApiService.getPremiumIndexPriceLinesData(category.getProductTypeId(), symbol, interval.getIntervalId(), limit, startTime, endTime));
    }


    @Override
    public Object getPremiumIndexPriceLinesData(ProductType category, String symbol, MarketInterval interval) {
        return getPremiumIndexPriceLinesData(category, symbol, interval, null, null, null);
    }

    @Override
    public Object getInstrumentsInfo(InstrumentInfoRequest instrumentInfoRequest) {
        return executeSync(bybitApiService.getInstrumentsInfo(
                instrumentInfoRequest.getCategory().getProductTypeId(),
                instrumentInfoRequest.getSymbol(),
                instrumentInfoRequest.getStatus(),
                instrumentInfoRequest.getBaseCoin(),
                instrumentInfoRequest.getLimit(),
                instrumentInfoRequest.getCursor()
        ));
    }

    @Override
    public Object getMarketOrderbook(ProductType category, String symbol) {
        return executeSync(bybitApiService.getMarketOrderbook(
                category.getProductTypeId(), symbol
        ));
    }

    @Override
    public Object getMarketOrderbook(ProductType category, String symbol, Integer limit) {
        return executeSync(bybitApiService.getMarketOrderbook(
                category.getProductTypeId(), symbol, limit
        ));
    }

    @Override
    public Object getMarketTickers(ProductType category, String symbol) {
        return executeSync(bybitApiService.getMarketTickers(
                category.getProductTypeId(), symbol
        ));
    }

    @Override
    public Object getMarketTickers(ProductType category, String symbol, String baseCoin, String expDate) {
        return executeSync(bybitApiService.getMarketTickers(
                category.getProductTypeId(), symbol, baseCoin, expDate
        ));
    }

    @Override
    public Object getFundingHistory(FundingHistoryRequest fundingHistoryRequest) {
        return executeSync(bybitApiService.getFundingHistory(
                fundingHistoryRequest.getCategory().getProductTypeId(),
                fundingHistoryRequest.getSymbol(),
                fundingHistoryRequest.getStartTime(),
                fundingHistoryRequest.getEndTime(),
                fundingHistoryRequest.getLimit()
        ));
    }

    @Override
    public Object getRecentTradeData(RecentTradeDataRequest recentTradeRequest) {
        return executeSync(bybitApiService.getRecentTradeData(
                recentTradeRequest.getCategory().getProductTypeId(),
                recentTradeRequest.getBaseCoin(),
                recentTradeRequest.getOptionType(),
                recentTradeRequest.getSymbol(),
                recentTradeRequest.getLimit()
        ));
    }

    @Override
    public Object getOpenInterest(OpenInterestRequest openInterestRequest) {
        return executeSync(bybitApiService.getOpenInterest(
                openInterestRequest.getCategory().getProductTypeId(),
                openInterestRequest.getSymbol(),
                openInterestRequest.getIntervalTime(),
                openInterestRequest.getStartTime(),
                openInterestRequest.getEndTime(),
                openInterestRequest.getLimit(),
                openInterestRequest.getCursor()
        ));
    }

    @Override
    public Object getHistoricalVolatility(HistoricalVolatilityRequest historicalVolatilityRequest) {
        return executeSync(bybitApiService.getHistoricalVolatility(
                historicalVolatilityRequest.getCategory().getProductTypeId(),
                historicalVolatilityRequest.getBaseCoin(),
                historicalVolatilityRequest.getPeriod(),
                historicalVolatilityRequest.getStartTime(),
                historicalVolatilityRequest.getEndTime())
        );
    }

    @Override
    public Object getHistoryOrderResult(OrderHistoryRequest orderHistoryRequest) {
        return executeSync(bybitApiService.getHistoryOrderResult(
                orderHistoryRequest.getCategory().getProductTypeId(),
                orderHistoryRequest.getSymbol(),
                orderHistoryRequest.getBaseCoin(),
                orderHistoryRequest.getSettleCoin(),
                orderHistoryRequest.getOrderId(),
                orderHistoryRequest.getOrderLinkId(),
                orderHistoryRequest.getOrderFilter(),
                orderHistoryRequest.getOrderStatus(),
                orderHistoryRequest.getStartTime(),
                orderHistoryRequest.getEndTime(),
                orderHistoryRequest.getLimit(),
                orderHistoryRequest.getCursor()));
    }

    @Override
    public Object getInsurance(String coin) {
        return executeSync(bybitApiService.getInsurance(coin));
    }

    @Override
    public Object getInsurance() {
        return executeSync(bybitApiService.getInsurance());
    }

    @Override
    public Object getRiskLimit(ProductType category) {
        return executeSync(bybitApiService.getRiskLimit(category.getProductTypeId()));
    }

    @Override
    public Object getRiskLimit(ProductType category, String symbol) {
        return executeSync(bybitApiService.getRiskLimit(category.getProductTypeId(), symbol));
    }

    @Override
    public Object getDeliveryPrice(DeliveryPriceRequest deliveryPriceRequest) {
        return executeSync(bybitApiService.getDeliveryPrice(deliveryPriceRequest.getCategory().getProductTypeId(),
                deliveryPriceRequest.getSymbol(),
                deliveryPriceRequest.getBaseCoin(),
                deliveryPriceRequest.getLimit(),
                deliveryPriceRequest.getCursor()
        ));
    }

    // Trade Data endpoints
    @Override
    public Object newOrder(NewOrderRequest order) {
        return executeSync(bybitApiService.newOrder(
                order.getCategory().getProductTypeId(),
                order.getSymbol(),
                order.getIsLeverage(),
                order.getSide(),
                order.getOrderType(),
                order.getQty(),
                order.getPrice(),
                order.getTriggerDirection(),
                order.getOrderFilter(),
                order.getTriggerPrice(),
                order.getTriggerBy(),
                order.getOrderIv(),
                order.getTimeInForce(),
                order.getPositionIdx(),
                order.getOrderLinkId(),
                order.getTakeProfit(),
                order.getStopLoss(),
                order.getTpTriggerBy(),
                order.getSlTriggerBy(),
                order.getReduceOnly(),
                order.getCloseOnTrigger(),
                order.getSmpType(),
                order.getMmp(),
                order.getTpslMode(),
                order.getTpLimitPrice(),
                order.getSlLimitPrice(),
                order.getTpOrderType(),
                order.getSlOrderType()
        ));
    }

    @Override
    public Object amendOrder(AmendOrderRequest order) {
        return executeSync(bybitApiService.amendOrder(
                order.getCategory().getProductTypeId(),
                order.getSymbol(),
                order.getOrderId(),
                order.getOrderLinkId(),
                order.getOrderIv(),
                order.getTriggerPrice(),
                order.getQty(),
                order.getPrice(),
                order.getTakeProfit(),
                order.getStopLoss(),
                order.getTpTriggerBy(),
                order.getSlTriggerBy(),
                order.getTriggerBy(),
                order.getTpLimitPrice(),
                order.getSlLimitPrice()
        ));
    }

    @Override
    public Object cancelOrder(CancelOrderRequest order) {
        return executeSync(bybitApiService.cancelOrder(
                order.getCategory().getProductTypeId(),
                order.getSymbol(),
                order.getOrderId(),
                order.getOrderLinkId(),
                order.getOrderFilter()
        ));
    }

    @Override
    public Object getOpenOrders(OpenOrderRequest order) {
        return executeSync(bybitApiService.getOpenOrders(
                order.getCategory().getProductTypeId(),
                order.getSymbol(),
                order.getBaseCoin(),
                order.getSettleCoin(),
                order.getOrderId(),
                order.getOrderLinkId(),
                order.getOpenOnly(),
                order.getOrderFilter(),
                order.getLimit(),
                order.getCursor()
        ));
    }

    // User endpoints
    @Override
    public Object getCurrentAPIKeyInfo() {
        return executeSync(bybitApiService.getCurrentAPIKeyInfo());
    }

    @Override
    public Object getSubUIDList() {
        return executeSync(bybitApiService.getSubUIDList());
    }

    @Override
    public Object createSubMember(SubUserRequest subUserRequest) {
        return executeSync(bybitApiService.createSubMember(
                subUserRequest.getUsername(),
                subUserRequest.getPassword(),
                subUserRequest.getMemberType(),
                subUserRequest.getSwitchOption(),
                subUserRequest.getIsUta(),
                subUserRequest.getNote()
        ));
    }

    @Override
    public Object createSubAPI(ApiKeyRequest apiKeyRequest) {
        return executeSync(bybitApiService.createSubAPI(apiKeyRequest));
    }

    @Override
    public Object freezeSubMember(FreezeSubUIDRquest freezeSubUIDRquest) {
        return executeSync(bybitApiService.freezeSubMember(freezeSubUIDRquest));
    }

    @Override
    public Object getUIDWalletType(String memberIds) {
        return executeSync(bybitApiService.getUIDWalletType(memberIds));
    }

    @Override
    public Object getUIDWalletType() {
        return executeSync(bybitApiService.getUIDWalletType());
    }

    @Override
    public Object getAffiliateUserInfo(String uid) {
        return executeSync(bybitApiService.getAffiliateUserInfo(uid));
    }


    // Position endpoints

    @Override
    public Object getPositionInfo(PositionListRequest positionListRequest) {
        return executeSync(bybitApiService.getPositionInfo(
                positionListRequest.getCategory().getProductTypeId(),
                positionListRequest.getSymbol(),
                positionListRequest.getBaseCoin(),
                positionListRequest.getSettleCoin(),
                positionListRequest.getLimit(),
                positionListRequest.getCursor()
        ));
    }

    @Override
    public Object setPositionLeverage(SetLeverageRequest setLeverageRequest) {
        return executeSync(bybitApiService.setPositionLeverage(setLeverageRequest));
    }


}
