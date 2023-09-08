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

public interface BybitApiRestClient {
    // Market Data

    /**
     * Get Bybit Server Time
     */
    Object getServerTime();

    /**
     * Kline/candlestick bars for a symbol. Klines are uniquely identified by their open time.
     *
     * @param category  product type. spot,linear, inverse (mandatory)
     * @param symbol    symbol to aggregate (mandatory)
     * @param interval  candlestick interval (mandatory)
     * @param limit     Default 500; max 1000 (optional)
     * @param startTime Timestamp in ms to get candlestick bars from INCLUSIVE (optional).
     * @param endTime   Timestamp in ms to get candlestick bars until INCLUSIVE (optional).
     * @return a candlestick bar for the given symbol and interval
     */
    Object getMarketLinesData(ProductType category, String symbol, MarketInterval interval, Integer limit, Long startTime, Long endTime);

    /**
     * Kline/candlestick bars for a symbol. Klines are uniquely identified by their open time.
     *
     * @see #getMarketLinesData(ProductType, String, MarketInterval, Integer, Long, Long)
     */
    Object getMarketLinesData(ProductType category, String symbol, MarketInterval interval);

    /**
     * Query for historical mark price klines. Charts are returned in groups based on the requested interval.
     * <p>
     * Covers: USDT perpetual / USDC contract / Inverse contract
     *
     * @param category  product type. spot,linear, inverse (mandatory)
     * @param symbol    symbol to aggregate (mandatory)
     * @param interval  candlestick interval (mandatory)
     * @param limit     Default 500; max 1000 (optional)
     * @param startTime Timestamp in ms to get candlestick bars from INCLUSIVE (optional).
     * @param endTime   Timestamp in ms to get candlestick bars until INCLUSIVE (optional).
     * @return a candlestick bar for the given symbol and interval
     */
    Object getMarketPriceLinesData(ProductType category, String symbol, MarketInterval interval, Integer limit, Long startTime, Long endTime);

    /**
     * Kline/candlestick bars for a symbol. Klines are uniquely identified by their open time.
     *
     * @see #getMarketLinesData(ProductType, String, MarketInterval, Integer, Long, Long)
     */
    Object getMarketPriceLinesData(ProductType category, String symbol, MarketInterval interval);


    /**
     * Query for historical index price klines. Charts are returned in groups based on the requested interval.
     * <p>
     * Covers: USDT perpetual / USDC contract / Inverse contract
     *
     * @param category  product type. spot,linear, inverse (mandatory)
     * @param symbol    symbol to aggregate (mandatory)
     * @param interval  candlestick interval (mandatory)
     * @param limit     Default 500; max 1000 (optional)
     * @param startTime Timestamp in ms to get candlestick bars from INCLUSIVE (optional).
     * @param endTime   Timestamp in ms to get candlestick bars until INCLUSIVE (optional).
     * @return a candlestick bar for the given symbol and interval
     */
    Object getIndexPriceLinesData(ProductType category, String symbol, MarketInterval interval, Integer limit, Long startTime, Long endTime);

    /**
     * Kline/candlestick bars for a symbol. Klines are uniquely identified by their open time.
     *
     * @see #getMarketLinesData(ProductType, String, MarketInterval, Integer, Long, Long)
     */
    Object getIndexPriceLinesData(ProductType category, String symbol, MarketInterval interval);

    /**
     * Query for historical index price klines. Charts are returned in groups based on the requested interval.
     * <p>
     * Covers: USDT perpetual / USDC contract / Inverse contract
     *
     * @param category  product type. spot,linear, inverse (mandatory)
     * @param symbol    symbol to aggregate (mandatory)
     * @param interval  candlestick interval (mandatory)
     * @param limit     Default 500; max 1000 (optional)
     * @param startTime Timestamp in ms to get candlestick bars from INCLUSIVE (optional).
     * @param endTime   Timestamp in ms to get candlestick bars until INCLUSIVE (optional).
     * @return a candlestick bar for the given symbol and interval
     */
    Object getPremiumIndexPriceLinesData(ProductType category, String symbol, MarketInterval interval, Integer limit, Long startTime, Long endTime);

    /**
     * Query for the instrument specification of online trading pairs.
     *
     * Covers: Spot / USDT perpetual / USDC contract / Inverse contract / Option
     *
     * CAUTION
     * Spot does not support pagination, so limit, cursor are invalid.
     * When query by baseCoin, regardless of category=linear or inverse, the result will have USDT perpetual, USDC contract and Inverse contract symbols.
     *
     * @see #getMarketLinesData(ProductType, String, MarketInterval, Integer, Long, Long)
     */
    Object getPremiumIndexPriceLinesData(ProductType category, String symbol, MarketInterval interval);

    /**
     * Query for the instrument specification of online trading pairs.
     *
     * Covers: Spot / USDT perpetual / USDC contract / Inverse contract / Option
     *
     * CAUTION
     * Spot does not support pagination, so limit, cursor are invalid.
     * When query by baseCoin, regardless of category=linear or inverse, the result will have USDT perpetual, USDC contract and Inverse contract symbols.
     * @param instrumentInfoRequest
     * @return
     */
    Object getInstrumentsInfo(InstrumentInfoRequest instrumentInfoRequest);

    /**
     * Query for orderbook depth data.
     *
     * Covers: Spot / USDT perpetual / USDC contract / Inverse contract / Option
     *
     * future: 200-level of orderbook data
     * spot: 50-level of orderbook data
     * option: 25-level of orderbook data
     * TIP
     * The response is in the snapshot format.
     * @param category
     * @param symbol
     * @return
     */
    Object getMarketOrderbook(ProductType category, String symbol);
    Object getMarketOrderbook(ProductType category, String symbol, Integer limit);

    /**
     * Query for the latest price snapshot, best bid/ask price, and trading volume in the last 24 hours.
     *
     * Covers: Spot / USDT perpetual / USDC contract / Inverse contract / Option
     *
     * TIP
     * If category=option, symbol or baseCoin must be passed.
     * @param category
     * @param symbol
     * @return
     */
    Object getMarketTickers(ProductType category, String symbol);
    Object getMarketTickers(ProductType category, String symbol, String baseCoin, String expDate);

    /**
     * Query for historical funding rates. Each symbol has a different funding interval. For example, if the interval is 8 hours and the current time is UTC 12, then it returns the last funding rate, which settled at UTC 8.
     *
     * To query the funding rate interval, please refer to the instruments-info endpoint.
     *
     * Covers: USDT and USDC perpetual / Inverse perpetual
     *
     * TIP
     * Passing only startTime returns an error.
     * Passing only endTime returns 200 records up till endTime.
     * Passing neither returns 200 records up till the current time.
     * @param fundingHistoryRequest
     * @return
     */
    Object getFundingHistory(FundingHistoryRequest fundingHistoryRequest);

    /**
     * Query recent public trading data in Bybit.
     *
     * Covers: Spot / USDT perpetual / USDC contract / Inverse contract / Option
     *
     * You can download archived historical trades here:
     *
     * USDT Perpetual, Inverse Perpetual & Inverse Futures
     * Spot
     * @param recentTradeRequest
     * @return
     */
    Object getRecentTradeData(RecentTradeDataRequest recentTradeRequest);

    /**
     * Get the open interest of each symbol.
     *
     * Covers: USDT perpetual / USDC contract / Inverse contract
     *
     * INFO
     * Returns single side data
     * The upper limit time you can query is the launch time of the symbol.
     * @param openInterestRequest
     * @return
     */
    Object getOpenInterest(OpenInterestRequest openInterestRequest);

    /**
     * Query option historical volatility
     *
     * Covers: Option
     *
     * INFO
     * The data is hourly.
     * If both startTime and endTime are not specified, it will return the most recent 1 hours worth of data.
     * startTime and endTime are a pair of params. Either both are passed or they are not passed at all.
     * This endpoint can query the last 2 years worth of data, but make sure [endTime - startTime] <= 30 days.
     * @param HistoricalVolatilityRequest
     * @return
     */
    Object getHistoricalVolatility(HistoricalVolatilityRequest HistoricalVolatilityRequest);
    // Trade

    /**
     * Get all account orders; active, canceled, or filled.
     *
     * @param orderHistoryRequest order request parameters
     * @return a list of all history orders 2 years
     */
    Object getHistoryOrderResult(OrderHistoryRequest orderHistoryRequest);

    /**
     * Get Insurance
     * Query for Bybit insurance pool data (BTC/USDT/USDC etc). The data is updated every 24 hours.
     * @param coin
     * @return
     */
    Object getInsurance(String coin);
    Object getInsurance();

    /**
     * Get Risk Limit
     * Query for the risk limit.
     *
     * Covers: USDT perpetual / USDC contract / Inverse contract
     * @param category
     * @param symbol
     * @return
     */
    Object getRiskLimit(ProductType category, String symbol);
    Object getRiskLimit(ProductType category);

    /**
     * Get Delivery Price
     * Get the delivery price.
     *
     * Covers: USDC futures / Inverse futures / Option
     * @param deliveryPriceRequest
     * @return
     */
    Object getDeliveryPrice(DeliveryPriceRequest deliveryPriceRequest);

    /**
     * This endpoint supports to create the order for spot, spot margin, USDT perpetual, USDC perpetual, USDC futures, inverse futures and options.
     * <p>
     * Unified account covers: Spot / USDT perpetual / USDC contract / Inverse contract / Options
     * Normal account covers: Spot / USDT perpetual / Inverse contract
     * <p>
     * INFO
     * Supported order type (orderType):
     * Limit order: orderType=Limit, it is necessary to specify order qty and price.
     * <p>
     * Market order: orderType=Market, execute at the best price in the Bybit market until the transaction is completed. When selecting a market order, the `price` is empty. In the futures trading system, in order to protect the serious slippage of the market order, the Bybit trading system will convert the market order into a limit order for matching. will be cancelled. The slippage threshold refers to the percentage that the order price deviates from the latest transaction price. The current threshold is set to 3% for BTC contracts and 5% for other contracts.
     * Supported timeInForce strategy:
     * GTC
     * IOC
     * FOK
     * PostOnly: If the order would be filled immediately when submitted, it will be cancelled. The purpose of this is to protect your order during the submission process. If the matching system cannot entrust the order to the order book due to price changes on the market, it will be cancelled. For the PostOnly order type, the quantity that can be submitted in a single order is more than other types of orders, please refer to the parameter lotSizeFilter > postOnlyMaxOrderQty in the instruments-info endpoint.
     * <p>
     * How to create conditional order:
     * When submitting an order, if triggerPrice is set, the order will be automatically converted into a conditional order. In addition, the conditional order does not occupy the margin. If the margin is insufficient after the conditional order is triggered, the order will be cancelled.
     * <p>
     * Take profit / Stop loss: You can set TP/SL while placing orders. Besides, you could modify the position's TP/SL.
     * <p>
     * Order quantity: The quantity of perpetual contracts you are going to buy/sell. For the order quantity, Bybit only supports positive number at present.
     * <p>
     * Order price: Place a limit order, this parameter is required. If you have position, the price should be higher than the liquidation price. For the minimum unit of the price change, please refer to the priceFilter > tickSize field in the instruments-info endpoint.
     * <p>
     * orderLinkId: You can customize the active order ID. We can link this ID to the order ID in the system. Once the active order is successfully created, we will send the unique order ID in the system to you. Then, you can use this order ID to cancel active orders, and if both orderId and orderLinkId are entered in the parameter input, Bybit will prioritize the orderId to process the corresponding order. Meanwhile, your customized order ID should be no Longer than 36 characters and should be unique.
     * <p>
     * Open orders up limit:
     * Future: Each account can hold a maximum of 500 active orders simultaneously. This is contract-specific, so the following situation is allowed: the same account can hold 300 BTCUSD active orders and 280 ETHUSD active orders at the same time. For conditional orders, each account can hold a maximum of 10 active orders simultaneously. When the upper limit of orders is reached, you can still place orders with parameters of reduceOnly or closeOnTrigger.
     * Spot: No limit for normal order but a maximum of 30 open TP/SL orders
     * Option: a maximum of 100 open orders
     * <p>
     * Rate limit:
     * Please refer to rate limit table. If you need to raise the rate limit, please contact your client manager or submit an application via here
     * <p>
     * TIP
     * To margin trade on spot on a normal account, you need to go here to borrow margin first.
     *
     * @param order the new order to submit.
     * @return a response containing details about the newly placed order.
     */
    Object newOrder(NewOrderRequest order);

    /**
     * Unified account covers: USDT perpetual / USDC contract / Inverse contract / Option
     * Normal account covers: USDT perpetual / Inverse contract
     * <p>
     * IMPORTANT
     * You can only modify unfilled or partially filled orders.
     *
     * @param order the existed order to amend.
     * @return a response containing details about the newly placed order.
     */
    Object amendOrder(AmendOrderRequest order);

    /**
     * Unified account covers: Spot / USDT perpetual / USDC contract / Inverse contract / Options
     * Normal account covers: Spot / USDT perpetual / Inverse contract
     * <p>
     * IMPORTANT
     * You must specify orderId or orderLinkId to cancel the order.
     * If orderId and orderLinkId do not match, the system will process orderId first.
     * You can only cancel unfilled or partially filled orders.
     *
     * @param order the existed order to cancel.
     * @return a response containing details about the newly placed order.
     */
    Object cancelOrder(CancelOrderRequest order);

    /**
     * Query unfilled or partially filled orders in real-time. To query older order records, please use the order history interface.
     * <p>
     * Unified account covers: Spot / USDT perpetual / USDC contract / Inverse contract / Options
     * Normal account covers: Spot / USDT perpetual / Inverse contract
     * <p>
     * TIP
     * It also supports querying filled, cancelled, and rejected orders which occurred in last 10 minutes (check the openOnly param). At most, 500 orders will be returned.
     * You can query by symbol, baseCoin, orderId and orderLinkId, and if you pass multiple params, the system will process them according to this priority: orderId > orderLinkId > symbol > baseCoin.
     * The records are sorted by the createdTime from newest to oldest.
     *
     * @param order get all real time open orders
     * @return
     */
    Object getOpenOrders(OpenOrderRequest order);

    // User

    /**
     * Get the information of the api key. Use the api key pending to be checked to call the endpoint. Both master and sub user's api key are applicable.
     * <p>
     * TIP
     * Any permission can access this endpoint.
     *
     * @return
     */
    Object getCurrentAPIKeyInfo();

    /**
     * Get all sub uid of master account. Use master user's api key only.
     * <p>
     * TIP
     * The API key must have one of the below permissions in order to call this endpoint..
     * <p>
     * master API key: "Account Transfer", "Subaccount Transfer", "Withdrawal"
     */
    Object getSubUIDList();

    /**
     * Create a new sub user id. Use master user's api key only.
     * <p>
     * TIP
     * The API key must have one of the below permissions in order to call this endpoint..
     * <p>
     * master API key: "Account Transfer", "Subaccount Transfer", "Withdrawal"
     */
    Object createSubMember(SubUserRequest subUserRequest);

    /**
     * To create new API key for those newly created sub UID. Use master user's api key only.
     * <p>
     * TIP
     * The API key must have one of the below permissions in order to call this endpoint..
     * <p>
     * master API key: "Account Transfer", "Subaccount Transfer", "Withdrawal"
     */
    Object createSubAPI(ApiKeyRequest apiKeyRequest);

    /**
     * Freeze Sub UID. Use master user's api key only.
     * <p>
     * TIP
     * The API key must have one of the below permissions in order to call this endpoint..
     * <p>
     * master API key: "Account Transfer", "Subaccount Transfer", "Withdrawal"
     */
    Object freezeSubMember(FreezeSubUIDRquest freezeSubUIDRquest);

    /**
     * Get available wallet types for the master account or sub account
     * <p>
     * TIP
     * Master api key: you can get master account and appointed sub account available wallet types, and support up to 200 sub uid in one request.
     * Sub api key: you can get its own available wallet types
     * PRACTICE
     * "FUND" - If you never deposit or transfer capital into it, this wallet type will not be shown in the array, but your account indeed has this wallet.
     * <p>
     * ["SPOT","OPTION","FUND","CONTRACT"] : Normal account and Funding wallet was operated before
     * ["SPOT","OPTION","CONTRACT"] : Normal account and Funding wallet is never operated
     * ["SPOT","UNIFIED","FUND","CONTRACT"] : UMA account and Funding wallet was operated before. (No UMA account after we forced upgrade to UTA)
     * ["SPOT","UNIFIED","CONTRACT"] : UMA account and Funding wallet is never operated. (No UMA account after we forced upgrade to UTA)
     * ["UNIFIED""FUND","CONTRACT"] : UTA account and Funding wallet was operated before.
     * ["UNIFIED","CONTRACT"] : UTA account and Funding wallet is never operated.
     */
    Object getUIDWalletType(String memberIds);

    Object getUIDWalletType();

    /**
     * Get Affiliate User Info
     * This API is used for affiliate to get their users information
     * <p>
     * TIP
     * Use master UID only
     * The api key can only have "Affiliate" permission
     * The transaction volume and deposit amount are the total amount of the user done on Bybit, and have nothing to do with commission settlement.
     * Any transaction volume data related to commission settlement is subject to the Affiliate Portal.
     */
    Object getAffiliateUserInfo(String uid);

    // Position Data

    /**
     * Get Position Info
     * Query real-time position data, such as position size, cumulative realizedPNL.
     *
     * Unified account covers: USDT perpetual / USDC contract / Inverse contract / Options
     * Normal account covers: USDT perpetual / Inverse contract
     * @param positionListRequest
     * @return
     */
    Object getPositionInfo(PositionListRequest positionListRequest);

    /**
     * Set Leverage
     * Set the leverage
     *
     * Unified account covers: USDT perpetual / USDC contract / Inverse contract
     * Normal account covers: USDT perpetual / Inverse contract
     * @param setLeverageRequest
     * @return
     */
    Object setPositionLeverage(SetLeverageRequest setLeverageRequest);
}
