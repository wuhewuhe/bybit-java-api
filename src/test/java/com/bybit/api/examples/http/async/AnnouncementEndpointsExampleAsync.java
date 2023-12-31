package com.bybit.api.examples.http.async;

import com.bybit.api.client.domain.announcement.LanguageSymbol;
import com.bybit.api.client.domain.market.request.MarketDataRequest;
import com.bybit.api.client.service.BybitApiClientFactory;

public class AnnouncementEndpointsExampleAsync {
    public static void main(String[] args) {
        var client = BybitApiClientFactory.newInstance().newAsyncMarketDataRestClient();

        // Get Announcement
        var announcementInfoRequest = MarketDataRequest.builder().locale(LanguageSymbol.EN_US).build();
        client.getAnnouncementInfo(announcementInfoRequest, System.out::println);
    }
}
