package asm2.model;

import java.util.ArrayList;
import java.util.List;

public class latestResponse {
    private String date;
    private String base;
//    private symbol rates;
    private long timestamp;
    private String from;
    private String to;
    private double amount;
    private double value;
    public latestResponse(String date,String base,
//                          symbol rates,
                          long timestamp,String from,String to,long amount,
                          long value){
        this.date =date;
        this.base =base;
//        this.rates =rates;
        this.timestamp =timestamp;
        this.from =from;
        this.to = to;
        this.amount =amount;
        this.value =value;
    }

    public String getBase() {
        return base;
    }

    public String getDate() {
        return date;
    }

//    public symbol getRates() {
//
//        return rates;
//    }

    public double getAmount() {
        return amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public double getValue() {
        return value;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }


}
