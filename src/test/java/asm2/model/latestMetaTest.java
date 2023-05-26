package asm2.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class latestMetaTest {

    public latestMeta meta;

    @BeforeEach
    void setUp() {
        meta = new latestMeta(200, "Usage subject to terms: https://currencyscoop.com/terms");

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getCode() {
        latestMeta m = meta;
        assertNotNull(m);
        assertTrue(m.getCode()==200);
        assertFalse(m.getCode()==404);
        assertNotNull(m.getCode());

    }

    @Test
    void getDisclaimer() {
        latestMeta m = meta;
        assertNotNull(m);
        assertTrue(m.getDisclaimer().equals("Usage subject " +
                "to " +
                "terms: https://currencyscoop.com/terms"));

        assertFalse(m.getDisclaimer().equals("test1"));
        assertNotNull(m.getDisclaimer());
    }
}