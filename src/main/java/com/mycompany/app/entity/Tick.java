package com.mycompany.app.entity;

import java.math.BigDecimal;

/**
 * Created by admin on 30/6/17.
 */
public class Tick {

    String instrumentId;
    double bid;
    double ask;
    protected BigDecimal mid;

    public Tick(String instrumentId, double bid, double ask) {
        this.instrumentId = instrumentId;
        this.bid = bid;
        this.ask = ask;
        BigDecimal bidPrice = new BigDecimal(bid);
        BigDecimal askPrice = new BigDecimal(ask);
        BigDecimal two = new BigDecimal(2.0);
        mid = bidPrice.add(askPrice).divide(two).setScale(4, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tick)) return false;

        Tick tick = (Tick) o;

        if (Double.compare(tick.bid, bid) != 0) return false;
        if (Double.compare(tick.ask, ask) != 0) return false;
        return instrumentId.equals(tick.instrumentId);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = instrumentId.hashCode();
        temp = Double.doubleToLongBits(bid);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(ask);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public String getInstrumentId() {
        return instrumentId;
    }

    public double getBid() {
        return bid;
    }

    public double getAsk() {
        return ask;
    }

    public BigDecimal getMid() {
        return mid;
    }

    @Override
    public String toString() {
        return "Tick{" +
                "instrumentId='" + instrumentId + '\'' +
                ", bid=" + bid +
                ", ask=" + ask +
                ", mid=" + mid +
                '}';
    }
}


