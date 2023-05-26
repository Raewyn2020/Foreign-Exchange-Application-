package asm2.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class latestResponseTest {

    public latestResponse response;

    @BeforeEach
    void setUp() {
        response =new latestResponse("2022-05-12T12:55:01Z",
                "CNY",1652360206,"CNY","USD",888,888);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getBase() {
        latestResponse r =response;
        assertNotNull(r);
        assertNotNull(r.getBase());
        assertTrue(r.getBase().equals("CNY"));
        assertFalse(r.getBase().equals("USD"));
    }

    @Test
    void getDate() {
        latestResponse r =response;
        assertNotNull(r);

        assertNotNull(r.getDate());

        assertTrue(r.getDate().equals("2022-05-12T12:55:01Z"));
        assertFalse(r.getDate().equals("testdate"));
    }

    @Test
    void getAmount() {
        latestResponse r =response;
        assertNotNull(r);
        assertNotNull(r.getAmount());

        assertTrue(r.getAmount() == 888);

        assertFalse(r.getAmount()==1000);
    }

    @Test
    void getTimestamp() {
        latestResponse r =response;
        assertNotNull(r);
        assertNotNull(r.getTimestamp());
        assertTrue(r.getTimestamp()==1652360206);
        assertFalse(r.getTimestamp()==2022);
    }

    @Test
    void getValue() {
        latestResponse r =response;
        assertNotNull(r);
        assertNotNull(r.getValue());
        assertTrue(r.getValue()== 888);
        assertFalse(r.getValue()==1000);
    }

    @Test
    void getFrom() {
        latestResponse r =response;
        assertNotNull(r);
        assertNotNull(r.getFrom());
        assertTrue(r.getFrom().equals("CNY"));
        assertFalse(r.getFrom().equals("test2"));
    }

    @Test
    void getTo() {
        latestResponse r =response;
        assertNotNull(r);
        assertNotNull(r.getTo());
        assertTrue(r.getTo().equals("USD"));
        assertFalse(r.getTo().equals("test2"));
    }
}