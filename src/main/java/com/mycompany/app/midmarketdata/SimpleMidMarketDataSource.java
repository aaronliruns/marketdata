package com.mycompany.app.midmarketdata;

import com.mycompany.app.entity.Tick;
import com.mycompany.app.marketdata.MarketDataSourceListener;
import com.mycompany.app.marketdata.SimpleMarketDataSource;

/**
 * Created by admin on 30/6/17.
 */
public class SimpleMidMarketDataSource extends SimpleMarketDataSource {

    protected long lastUpdateTime = 0L;
    private final static long FIVE_SECONDS_IN_MILLS = 5 * 1000;

    public SimpleMidMarketDataSource(String name) {
        super(name);
    }

    @Override
    protected void notifyAllListeners() {
        //Always get the latest tick as soon as new one arrives
        Tick latestTick = null;
        try {
            latestTick = marketData.take();
        } catch (InterruptedException e) {
            throw new RuntimeException("Failed to take tick data out of the queue. Skipping this tick...");
        }//end try


        //But conditionally publish the price
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - lastUpdateTime >= FIVE_SECONDS_IN_MILLS) {
            //One thread (producer) push the latest tick to all listeners (consumers) who run on their own threads separately
            for (MarketDataSourceListener listener : listeners) {
                listener.onUpdate(latestTick);
            }//end for
            lastUpdateTime = currentTimeMillis;
        }

    }
}
