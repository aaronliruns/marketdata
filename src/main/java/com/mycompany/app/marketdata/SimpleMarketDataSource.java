package com.mycompany.app.marketdata;

import com.mycompany.app.entity.Tick;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by admin on 30/6/17.
 */
public class SimpleMarketDataSource implements MarketDataSource {

    protected final List<MarketDataSourceListener> listeners = new ArrayList<MarketDataSourceListener>();
    //Thread-safe queue that holds a series of ticks
    protected final BlockingQueue<Tick> marketData = new LinkedBlockingQueue<Tick>();
    protected final String name;

    public SimpleMarketDataSource(String name) {
        this.name = name;
    }



    public void subscribe(MarketDataSourceListener listener) {

        listeners.add(listener);
        //spawn a new thread for listener
        Thread t = new Thread((Runnable) listener);
        t.start();

    }

    public void unsubscribe(MarketDataSourceListener listener) {

        listeners.clear();

    }

    public String getName() {
        return name;
    }

    public void publishMarketData(Tick tick) {
        try {
            marketData.put(tick);
            notifyAllListeners();
        } catch (InterruptedException e) {
            throw new RuntimeException("Failed to put tick data into the queue.");
        }
    }


    /**
     * Bid and ask prices are published to all listeners as soon as they arrive
     */
    protected void notifyAllListeners() {

        //Always get the latest tick as soon as new one arrives
        Tick latestTick = null;
        try {
            latestTick = marketData.take();
        } catch (InterruptedException e) {
            throw new RuntimeException("Failed to take tick data out of the queue. Skipping this tick...");
        }//end try

        //One thread (producer) push the latest tick to all listeners (consumers) who run on their own threads separately
        for (MarketDataSourceListener listener :  listeners) {
            listener.onUpdate(latestTick);
        }//end for

    }

}
