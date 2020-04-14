package com.company;

import java.math.BigDecimal;

public class Tariff {
    private int minutes;
    private int rate;

    public Tariff(int minutes, int rate){
        setMinutes(minutes);
        setRate(rate);
    }
    public Tariff(){
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
