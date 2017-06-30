package com.mycompany.app;

import com.mycompany.app.marketdata.MarketDataSource;
import com.mycompany.app.marketdata.SimpleMarketDataSourceListener;
import com.mycompany.app.midmarketdata.SimpleMidMarketDataSource;
import com.mycompany.app.util.RandomTestObjectFactory;

/**
 * Created by admin on 30/6/17.
 */
public class MarketMidPrice5sTickingDemo {


    private void demo() {



        MarketDataSource[]  marketDataSources = new MarketDataSource[2];
        marketDataSources[0] = new SimpleMidMarketDataSource(RandomTestObjectFactory.getInstance().getRandomMarketName());
        marketDataSources[1] = new SimpleMidMarketDataSource(RandomTestObjectFactory.getInstance().getRandomMarketName());

        for (MarketDataSource mds : marketDataSources) {
            for (int i=0; i<2; i++) {
                new SimpleMarketDataSourceListener(mds);
            }
        }//end for

        //Populate market data source 1
        Runnable populateMarketDataSource1 = () -> {
            while (true) {

                ((SimpleMidMarketDataSource)marketDataSources[0]).publishMarketData(
                        RandomTestObjectFactory.getInstance().getRandomTick()
                );

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        new Thread(populateMarketDataSource1).start();


        //Populate market data source 2
        Runnable populateMarketDataSource2 = () -> {
            while (true) {

                ((SimpleMidMarketDataSource)marketDataSources[1]).publishMarketData(
                        RandomTestObjectFactory.getInstance().getRandomTick()
                );

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        new Thread(populateMarketDataSource2).start();

    }

    public static void main(String[] args) {
        new MarketMidPrice5sTickingDemo().demo();
    }
}
