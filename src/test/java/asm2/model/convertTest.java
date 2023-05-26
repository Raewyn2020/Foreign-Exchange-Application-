package asm2.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class convertTest {
    public convert con;
    public latestResponse response;
    public latestMeta meta;

    @BeforeEach
    void setUp() {
        response =new latestResponse("2022-05-12T12:55:01Z",
                "CNY",1652360206,"CNY","USD",888,888);
        meta = new latestMeta(200,"Usage subject to terms: https://currencyscoop.com/terms");
        con =new convert(response,meta);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getLatestMeta() {
        latestMeta m =con.getLatestMeta();
        assertTrue(m.getDisclaimer().equals("Usage subject " +
                "to " +
                "terms: https://currencyscoop.com/terms"));
        assertTrue(m.getCode()==200);
        assertFalse(m.getDisclaimer().equals("test1"));
        assertFalse(m.getCode()==404);
        assertNotNull(m);
        assertNotNull(m.getCode());
        assertNotNull(m.getDisclaimer());


    }

    @Test
    void getLatestResponse() {
        latestResponse r =con.getLatestResponse();
        assertNotNull(r);
        assertNotNull(r.getAmount());
        assertNotNull(r.getBase());
        assertNotNull(r.getDate());
        assertNotNull(r.getFrom());
        assertNotNull(r.getTimestamp());
        assertNotNull(r.getTo());
        assertNotNull(r.getValue());
        assertTrue(r.getAmount() == 888);
        assertTrue(r.getValue()== 888);
        assertTrue(r.getBase().equals("CNY"));
        assertTrue(r.getFrom().equals("CNY"));
        assertTrue(r.getTo().equals("USD"));
        assertTrue(r.getTimestamp()==1652360206);
        assertTrue(r.getDate().equals("2022-05-12T12:55:01Z"));
        assertFalse(r.getFrom().equals("test2"));
    }

    @Test
    void testToString() {
        String s =con.toString();
        assertNotNull(s);
        assertEquals(s, "from: CNY\n" +
                "to: USD\n" +
                "amount: 888.0\n" +
                "value: 888.0\n");
        assertFalse(s.equals(
                "code: 200\n" +
                        "disclaimer: Usage subject to terms: https://currencyscoop.com/terms\n" +
                        "timestamp: 1652360206\n" +
                        "from: CNY\n" +
                        "to: USD\n" +
                        "amount: 888.0\n" +
                        "value: 889.0\n"));
    }
}