package asm2.model;

import java.util.List;

public class currency {

    private String currency_name;
    private String currency_code;
    private String decimal_units;
    private List<String> countries;
    public currency(String currency_name,String currency_code,
                    String decimal_units,List<String> countrie){
        this.currency_code =currency_code;
        this.currency_name =currency_name;
        this.decimal_units =decimal_units;
        this.countries =countrie;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public String getCurrency_name() {
        return currency_name;
    }

    public String getDecimal_units() {
        return decimal_units;
    }

    public List<String> getCountries() {
        return countries;
    }
}
