package com.mycompany.app.marketdata;

import com.mycompany.app.entity.Tick;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

/**
 * Created by admin on 30/6/17.
 */
public class SimpleMarketDataSourceListener implements MarketDataSourceListener,Runnable {

    private final static Logger LOGGER = Logger.getLogger(SimpleMarketDataSourceListener.class.getName());
    protected final MarketDataSource marketDataSource;
    protected final BlockingQueue<Tick> embededBlockingQueue = new LinkedBlockingQueue<Tick>();


    public SimpleMarketDataSourceListener(MarketDataSource marketDataSource) {
        this.marketDataSource = marketDataSource;
        marketDataSource.subscribe(this);

    }

    //Pass in immutable tick object
    public void onUpdate(final Tick tick) {

        try {
            embededBlockingQueue.put(tick);
        } catch (InterruptedException e) {
            new RuntimeException("Interrupted while putting on the queue");
        }

    }

    public void run() {

        StringBuilder startInfo = new StringBuilder();
        startInfo.append("[");
        startInfo.append(marketDataSource.getName());
        startInfo.append("]");
        startInfo.append("[");
        startInfo.append(Thread.currentThread().getName());
        startInfo.append("] ");
        startInfo.append(" started listening ........................................");
        LOGGER.info(startInfo.toString());


        //keep thread alive listening to the subscribed datasource
        while (true) {
            try {

                //Block waiting for next tick
                Tick tick = embededBlockingQueue.take();

                StringBuilder tickInfo = new StringBuilder();
                tickInfo.append("[");
                tickInfo.append(marketDataSource.getName());
                tickInfo.append("]");
                tickInfo.append("[");
                tickInfo.append(Thread.currentThread().getName());
                tickInfo.append("] ");
                tickInfo.append("Received price update  ");
                tickInfo.append(tick.toString());
                LOGGER.info(tickInfo.toString());

                Thread.sleep(100);
            } catch (InterruptedException e) {
                StringBuilder sb = new StringBuilder(Thread.currentThread().getName());
                sb.append(" : Listener interrupted while listening");
                new RuntimeException(sb.toString());
            }
        }//end while

    }
}
