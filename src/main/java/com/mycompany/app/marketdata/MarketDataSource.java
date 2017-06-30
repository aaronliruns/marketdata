package com.mycompany.app.marketdata;

/**
 * Created by admin on 30/6/17.
 */
public interface MarketDataSource {
    void subscribe(MarketDataSourceListener listener);
    void unsubscribe(MarketDataSourceListener listener);
    String getName();
}