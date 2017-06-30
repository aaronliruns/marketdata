package com.mycompany.app.marketdata;

import com.mycompany.app.entity.Tick;

/**
 * Created by admin on 30/6/17.
 */
public interface MarketDataSourceListener {
    void onUpdate(Tick tick);
}

