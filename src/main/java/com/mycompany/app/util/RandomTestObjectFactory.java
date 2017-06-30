package com.mycompany.app.util;

import com.mycompany.app.entity.Tick;
import com.mycompany.app.marketdata.MarketDataSource;

import java.util.Random;
import java.util.UUID;

/**
 * Created by admin on 30/6/17.
 */
public final class RandomTestObjectFactory {
    private final static RandomTestObjectFactory ourInstance = new RandomTestObjectFactory();

    private final static Random random = new Random();

    public double randomInRange(double min, double max) {
        double range = max - min;
        double scaled = random.nextDouble() * range;
        double shifted = scaled + min;
        return shifted; // == (rand.nextDouble() * (max-min)) + min;
    }

    public static RandomTestObjectFactory getInstance() {
        return ourInstance;
    }

    private RandomTestObjectFactory() {
    }

    public Tick getRandomTick() {
        Tick tick = new Tick(UUID.randomUUID().toString(),
                             randomInRange(0.0, 100.0),
                             randomInRange(0.0, 100.0));

        return tick;
    }

    public String getRandomMarketName() {
        String[] names = {"NASDAQ", "EURONEXT", "HKE", "Deutsche Borse","Bursa Malaysia",
                          "BM&F Bovespa", "NZX", "CME Group", "DFM", "Moscow Exchange"};

        return names[random.nextInt(10)];
    }
}
