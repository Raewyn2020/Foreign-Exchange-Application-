package asm2.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class currencyTest {
    public currency cur;

    @BeforeEach
    void setUp() {
//        "USD":{"currency_name":"United States dollar",
//        "currency_code":"USD","decimal_units":"2",
//        "countries":["United States","American Samoa (AS)",
//        "Barbados (BB) (as well as Barbados Dollar)",
//        "Bermuda (BM) (as well as Bermudian Dollar)",
//        "British Indian Ocean Territory (IO) (also uses GBP)",
//        "British Virgin Islands (VG)","Caribbean Netherlands (BQ \u2013 Bonaire",
//        "Sint Eustatius and Saba)","Ecuador (EC)","El Salvador (SV)","Guam (GU)",
//        "Haiti (HT)","Marshall Islands (MH)","Federated States of Micronesia (FM)",
//        "Northern Mariana Islands (MP)","Palau (PW)","Panama (PA) (as well as Panamanian Balboa)",
//        "Puerto Rico (PR)","Timor-Leste (TL)","Turks and Caicos Islands (TC)",
//        "U.S. Virgin Islands (VI)","United States Minor Outlying Islands (UM)  Cambodia also uses the USD officially."]
        List<String> countries = new ArrayList<String>(Arrays.asList("United States","American Samoa (AS)","Barbados (BB) (as well as Barbados Dollar)","Bermuda (BM) (as well as Bermudian Dollar)","British Indian Ocean Territory (IO) (also uses GBP)","British Virgin Islands (VG)","Caribbean Netherlands (BQ \u2013 Bonaire","Sint Eustatius and Saba)","Ecuador (EC)","El Salvador (SV)","Guam (GU)","Haiti (HT)","Marshall Islands (MH)","Federated States of Micronesia (FM)","Northern Mariana Islands (MP)","Palau (PW)","Panama (PA) (as well as Panamanian Balboa)","Puerto Rico (PR)","Timor-Leste (TL)","Turks and Caicos Islands (TC)","U.S. Virgin Islands (VI)","United States Minor Outlying Islands (UM)  Cambodia also uses the USD officially."));
        cur =new currency("United States dollar","USD","2",countries);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getCurrency_code() {
        assertNotNull(cur);
        assertNotNull(cur.getCurrency_code());
        assertTrue(cur.getCurrency_code().equals("USD"));
        assertFalse(cur.getCurrency_code().equals("CNY"));
    }

    @Test
    void getCurrency_name() {
        assertNotNull(cur);
        assertNotNull(cur.getCurrency_name());
        assertTrue(cur.getCurrency_name().equals("United States dollar"));
        assertFalse(cur.getCurrency_name().equals("CNY"));
    }

    @Test
    void getDecimal_units() {
        assertNotNull(cur);
        assertNotNull(cur.getDecimal_units());
        assertTrue(cur.getDecimal_units().equals("2"));
        assertFalse(cur.getDecimal_units().equals("CNY"));
    }

    @Test
    void getCountries() {
        assertNotNull(cur);
        assertNotNull(cur.getCountries());
        assertFalse(cur.getCountries().contains("China"));
        assertTrue(cur.getCountries().contains("United States"));
    }
}