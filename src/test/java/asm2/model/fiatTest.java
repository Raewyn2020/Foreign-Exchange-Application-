package asm2.model;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SuppressWarnings("unchecked")
class fiatTest {
//    httpRequest yourHttpRequest;
    fiat f1 = new fiat();
    httpRequest yourHttpRequest;
    SQLcache sqLcache;

    @BeforeEach
    void setUp() {

        yourHttpRequest = mock(httpRequest.class);
        sqLcache = mock(SQLcache.class);
        f1.setRequest(yourHttpRequest);
        f1.setCache(sqLcache);
        List<String> countries = new ArrayList<String>(Arrays.asList("United States","American Samoa (AS)","Barbados (BB) (as well as Barbados Dollar)","Bermuda (BM) (as well as Bermudian Dollar)","British Indian Ocean Territory (IO) (also uses GBP)","British Virgin Islands (VG)","Caribbean Netherlands (BQ \u2013 Bonaire","Sint Eustatius and Saba)","Ecuador (EC)","El Salvador (SV)","Guam (GU)","Haiti (HT)","Marshall Islands (MH)","Federated States of Micronesia (FM)","Northern Mariana Islands (MP)","Palau (PW)","Panama (PA) (as well as Panamanian Balboa)","Puerto Rico (PR)","Timor-Leste (TL)","Turks and Caicos Islands (TC)","U.S. Virgin Islands (VI)","United States Minor Outlying Islands (UM)  Cambodia also uses the USD officially."));
        currency cur =new currency("United States dollar","USD","2",countries);
        Map<String, currency> map = new HashMap<String, currency>();
        map.put("USD",cur);
        List<String> XOFcon = new ArrayList<String>(Arrays.asList("Benin (BJ)",
                "Burkina Faso (BF)", "Côte d'Ivoire (CI)", "Guinea-Bissau (GW)","Mali (ML)", "Niger (NE)", "Senegal (SN)",
                "Togo (TG)"));
        currency XOF = new currency("CFA franc BCEAO","XOF","0",XOFcon);
        map.put("XOF",XOF);
        List<String> TTDcon = new ArrayList<String>(Arrays.asList("Trinidad and " +
                "Tobago"));
        currency TTD =new currency("Trinidad and Tobago dollar","TTD","2",
                TTDcon);
        map.put("TTD",TTD);
        List<String> NOKcon = new ArrayList<String>(Arrays.asList("Norway",
                "Svalbard" +
                        " and  Jan Mayen (SJ)", "Bouvet Island (BV)"));
        currency NOK = new currency("Norwegian krone","NOK","2",NOKcon);
        map.put("NOK",NOK);
        List<String> SZLcon = new ArrayList<String>(Arrays.asList("Eswatini[13]"));
        currency SZL =new currency("Swazi lilangeni","SZL","2",SZLcon);
        map.put("SZL",SZL);
        List<String> CZKcon = new ArrayList<String>(Arrays.asList("Czechia [9]"));
        currency CZK = new currency("Czech koruna","CZK","2", CZKcon);
        map.put("CZK",CZK);

        List<String> CDFcon = new ArrayList<String>(Arrays.asList("Congo - " +
                "Kinshasa","Congo - Brazzaville"));
        currency CDF = new currency("test1","CDF","2",CDFcon);
        List<String> MMKcon = new ArrayList<String>(Arrays.asList("Myanmar (Burma)"));
        currency MMK = new currency("test2","MMK","2",MMKcon);
        map.put("MMK",MMK);
        map.put("CDF",CDF);
        when(yourHttpRequest.getCurrenciesRequest(anyString())).thenReturn(map);
        f1.setIsPlaying(true);
//        f1.setBase("XOF");
//        f1.setSymbol("USD");
        when(yourHttpRequest.getConvertRequest("XOF","USD","85")).thenReturn(
                "report with all Information1\n");
        when(yourHttpRequest.getConvertRequest("XOF","USD","9")).thenReturn(
                "report with all Information2\n");
        when(yourHttpRequest.getConvertRequest("XOF","USD","9")).thenReturn(
                "report with all Information2\n");
        when(yourHttpRequest.getConvertRequest("XOF","USD","y2es1")).thenReturn(
                "report with all Information3\n");
        when(yourHttpRequest.getConvertRequest("XOF","USD","0.0.2")).thenReturn(
                "report with all Information4\n");
        when(yourHttpRequest.getConvertRequest("XOF","USD",null)).thenReturn(
                "report with all Information5\n");
        when(yourHttpRequest.getLatestRequest("XOF","USD")).thenReturn(
                "rate");

        when(yourHttpRequest.getConvertRequest("XOF","USD","5")).thenReturn(
                "value:91991\n");
        f1.setMapCur(this.f1.mapFiat());
        when(sqLcache.IfContains("USD","XOF")).thenReturn(true);
        when(sqLcache.IfContains("MMK","USD")).thenReturn(false);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void mapFiat() {


        Map<String,currency> fmap = f1.mapFiat();
        assertNotNull(fmap);
        assertTrue(fmap.size()==8);
        List allcountries = new ArrayList();
        for(currency c :fmap.values()){
            for(String i: c.getCountries()){
                allcountries.add(i);
            }
        }
        assertTrue(allcountries.contains("Bouvet Island"));
        assertTrue(allcountries.contains("Côte d’Ivoire"));
        assertTrue(allcountries.contains("Czechia"));
        assertTrue(allcountries.contains("Svalbard & Jan Mayen"));
        assertTrue(allcountries.contains("Trinidad & Tobago"));
        assertTrue(allcountries.contains("Niger"));
        assertTrue(allcountries.contains("Barbados"));
        assertTrue(allcountries.contains("Panama"));
        Map<String, currency> map2 = null;
        when(yourHttpRequest.getCurrenciesRequest(anyString())).thenReturn(map2);
        Map<String,currency> f2map = f1.mapFiat();
        assertNotNull(f2map);
        assertTrue(f2map.size()==0);


    }

    @Test
    void writeReport() {
        assertEquals(f1.WriteReport("XOF","USD","9"),"(API)\n" +
                "Base information: \n" +
                "  currency name: CFA franc BCEAO\n" +
                "  currency code: XOF\n" +
                "  decimal units: 0\n" +
                "  countries: \n" +
                "    Benin\n" +
                "    Burkina Faso\n" +
                "    Côte d’Ivoire\n" +
                "    Guinea-Bissau\n" +
                "    Mali\n" +
                "    Niger\n" +
                "    Senegal\n" +
                "    Togo\n" +
                "\n" +
                "Symbol information:\n" +
                "  currency name: United States dollar\n" +
                "  currency code: USD\n" +
                "  decimal units: 2\n" +
                "  countries: \n" +
                "    United States\n" +
                "    American Samoa\n" +
                "    Barbados\n" +
                "    Bermuda\n" +
                "    British Indian Ocean Territory\n" +
                "    British Virgin Islands\n" +
                "    Caribbean Netherlands\n" +
                "    Sint Eustatius and Saba)\n" +
                "    Ecuador\n" +
                "    El Salvador\n" +
                "    Guam\n" +
                "    Haiti\n" +
                "    Marshall Islands\n" +
                "    Federated States of Micronesia\n" +
                "    Northern Mariana Islands\n" +
                "    Palau\n" +
                "    Panama\n" +
                "    Puerto Rico\n" +
                "    Timor-Leste\n" +
                "    Turks and Caicos Islands\n" +
                "    U.S. Virgin Islands\n" +
                "    United States Minor Outlying Islands\n" +
                "\n" +
                "report with all Information2\n" +
                "rate: rate");
        assertEquals(f1.WriteReport("XOF","USD","85"),"(API)\n" +
                "Base information: \n" +
                "  currency name: CFA franc BCEAO\n" +
                "  currency code: XOF\n" +
                "  decimal units: 0\n" +
                "  countries: \n" +
                "    Benin\n" +
                "    Burkina Faso\n" +
                "    Côte d’Ivoire\n" +
                "    Guinea-Bissau\n" +
                "    Mali\n" +
                "    Niger\n" +
                "    Senegal\n" +
                "    Togo\n" +
                "\n" +
                "Symbol information:\n" +
                "  currency name: United States dollar\n" +
                "  currency code: USD\n" +
                "  decimal units: 2\n" +
                "  countries: \n" +
                "    United States\n" +
                "    American Samoa\n" +
                "    Barbados\n" +
                "    Bermuda\n" +
                "    British Indian Ocean Territory\n" +
                "    British Virgin Islands\n" +
                "    Caribbean Netherlands\n" +
                "    Sint Eustatius and Saba)\n" +
                "    Ecuador\n" +
                "    El Salvador\n" +
                "    Guam\n" +
                "    Haiti\n" +
                "    Marshall Islands\n" +
                "    Federated States of Micronesia\n" +
                "    Northern Mariana Islands\n" +
                "    Palau\n" +
                "    Panama\n" +
                "    Puerto Rico\n" +
                "    Timor-Leste\n" +
                "    Turks and Caicos Islands\n" +
                "    U.S. Virgin Islands\n" +
                "    United States Minor Outlying Islands\n" +
                "\n" +
                "report with all Information1\n" +
                "rate: rate");
        assertEquals(f1.WriteReport("XOF","USD","y2es1"),"");
        assertEquals(f1.WriteReport("XOF","USD","0.0.2"),
                "");
        assertEquals(f1.WriteReport("XOF","USD",null), "");
        assertEquals(f1.WriteReport("XOF","USD",""), "");


    }

    @Test
    void saveInfoInListView() {
        f1.SaveInfoInListView("Congo");
        assertTrue(f1.getThedata().contains("Congo"));
        assertTrue(f1.getCurrData().contains("CDF"));
        assertTrue(f1.getCurrAllData().keySet().contains("CDF"));


        f1.SaveInfoInListView("Myanmar");
        assertTrue(f1.getThedata().contains("Myanmar"));
        assertTrue(f1.getCurrData().contains("MMK"));
        assertTrue(f1.getCurrAllData().keySet().contains("MMK"));
//        assertTrue(f1.getCurrAllData().values().contains("MMK"));

        f1.SaveInfoInListView("Myanmar");
        assertTrue(f1.getThedata().contains("Myanmar"));
        assertTrue(f1.getCurrData().contains("MMK"));
        assertTrue(f1.getCurrAllData().keySet().contains("MMK"));
//        assertTrue(f1.getCurrAllData().values().contains("MMK"));

        f1.SaveInfoInListView("Côte d’Ivoire");
        assertTrue(f1.getThedata().contains("Côte d’Ivoire"));
        assertTrue(f1.getCurrData().contains("XOF"));
        assertTrue(f1.getCurrAllData().keySet().contains("XOF"));
//        assertTrue(f1.getCurrAllData().values().contains("XOF"));

    }


    @Test
    void ifCache() {


//        f1.setBase("USD");
//        f1.setSymbol("XOF");
        assertTrue(f1.IfCache("USD","XOF"));
//        f1.setBase("MMK");
//        f1.setSymbol("USD");
        assertFalse(f1.IfCache("MMK","USD"));

    }

    @Test
    void useCache() {
//        String rate = cache.getRate(base,symbol);
        f1.SaveInfoInListView("Côte d’Ivoire");
        f1.SaveInfoInListView("United States");
        when(sqLcache.getRate("XOF","USD")).thenReturn("100");
        assertEquals(f1.UseCache("XOF","USD","9"),"(Cache)\n" +
                "Base information: \n" +
                "  currency name: CFA franc BCEAO\n" +
                "  currency code: XOF\n" +
                "  decimal units: 0\n" +
                "  countries:\n" +
                "    Benin\n" +
                "    Burkina Faso\n" +
                "    Côte d’Ivoire\n" +
                "    Guinea-Bissau\n" +
                "    Mali\n" +
                "    Niger\n" +
                "    Senegal\n" +
                "    Togo\n" +
                "\n" +
                "Symbol information:\n" +
                "  currency name: United States dollar\n" +
                "  currency code: USD\n" +
                "  decimal units: 2\n" +
                "  countries:\n" +
                "    United States\n" +
                "    American Samoa\n" +
                "    Barbados\n" +
                "    Bermuda\n" +
                "    British Indian Ocean Territory\n" +
                "    British Virgin Islands\n" +
                "    Caribbean Netherlands\n" +
                "    Sint Eustatius and Saba)\n" +
                "    Ecuador\n" +
                "    El Salvador\n" +
                "    Guam\n" +
                "    Haiti\n" +
                "    Marshall Islands\n" +
                "    Federated States of Micronesia\n" +
                "    Northern Mariana Islands\n" +
                "    Palau\n" +
                "    Panama\n" +
                "    Puerto Rico\n" +
                "    Timor-Leste\n" +
                "    Turks and Caicos Islands\n" +
                "    U.S. Virgin Islands\n" +
                "    United States Minor Outlying Islands\n" +
                "\n" +
                "from: XOF\n" +
                "to: USD\n" +
                "amount: 9\n" +
                "value:900.0\n" +
                "rate: 100");
        assertEquals(f1.UseCache("XOF","USD",null),"");
        assertEquals(f1.UseCache("XOF","USD","0.0.2"),"");
        assertEquals(f1.UseCache("XOF","USD","y2es1"),"");
        assertEquals(f1.UseCache("XOF","USD",""),"");
        assertEquals(f1.UseCache("XOF","USD","85"), "(Cache)\n" +
                "Base information: \n" +
                "  currency name: CFA franc BCEAO\n" +
                "  currency code: XOF\n" +
                "  decimal units: 0\n" +
                "  countries:\n" +
                "    Benin\n" +
                "    Burkina Faso\n" +
                "    Côte d’Ivoire\n" +
                "    Guinea-Bissau\n" +
                "    Mali\n" +
                "    Niger\n" +
                "    Senegal\n" +
                "    Togo\n" +
                "\n" +
                "Symbol information:\n" +
                "  currency name: United States dollar\n" +
                "  currency code: USD\n" +
                "  decimal units: 2\n" +
                "  countries:\n" +
                "    United States\n" +
                "    American Samoa\n" +
                "    Barbados\n" +
                "    Bermuda\n" +
                "    British Indian Ocean Territory\n" +
                "    British Virgin Islands\n" +
                "    Caribbean Netherlands\n" +
                "    Sint Eustatius and Saba)\n" +
                "    Ecuador\n" +
                "    El Salvador\n" +
                "    Guam\n" +
                "    Haiti\n" +
                "    Marshall Islands\n" +
                "    Federated States of Micronesia\n" +
                "    Northern Mariana Islands\n" +
                "    Palau\n" +
                "    Panama\n" +
                "    Puerto Rico\n" +
                "    Timor-Leste\n" +
                "    Turks and Caicos Islands\n" +
                "    U.S. Virgin Islands\n" +
                "    United States Minor Outlying Islands\n" +
                "\n" +
                "from: XOF\n" +
                "to: USD\n" +
                "amount: 85\n" +
                "value:8500.0\n" +
                "rate: 100");
        assertEquals(f1.UseCache("XOF","USD","5"),"(Cache)\n" +
                "Base information: \n" +
                "  currency name: CFA franc BCEAO\n" +
                "  currency code: XOF\n" +
                "  decimal units: 0\n" +
                "  countries:\n" +
                "    Benin\n" +
                "    Burkina Faso\n" +
                "    Côte d’Ivoire\n" +
                "    Guinea-Bissau\n" +
                "    Mali\n" +
                "    Niger\n" +
                "    Senegal\n" +
                "    Togo\n" +
                "\n" +
                "Symbol information:\n" +
                "  currency name: United States dollar\n" +
                "  currency code: USD\n" +
                "  decimal units: 2\n" +
                "  countries:\n" +
                "    United States\n" +
                "    American Samoa\n" +
                "    Barbados\n" +
                "    Bermuda\n" +
                "    British Indian Ocean Territory\n" +
                "    British Virgin Islands\n" +
                "    Caribbean Netherlands\n" +
                "    Sint Eustatius and Saba)\n" +
                "    Ecuador\n" +
                "    El Salvador\n" +
                "    Guam\n" +
                "    Haiti\n" +
                "    Marshall Islands\n" +
                "    Federated States of Micronesia\n" +
                "    Northern Mariana Islands\n" +
                "    Palau\n" +
                "    Panama\n" +
                "    Puerto Rico\n" +
                "    Timor-Leste\n" +
                "    Turks and Caicos Islands\n" +
                "    U.S. Virgin Islands\n" +
                "    United States Minor Outlying Islands\n" +
                "\n" +
                "from: XOF\n" +
                "to: USD\n" +
                "amount: 5\n" +
                "value:500.0\n" +
                "rate: 100");

    }

    @Test
    void ifDigital() {
        assertTrue(f1.IfDigital("888"));
        assertTrue(f1.IfDigital("01011"));
        assertTrue(f1.IfDigital("0.3838"));
        assertFalse(f1.IfDigital("13f1c9a72781"));
        assertFalse(f1.IfDigital(null));
        assertFalse(f1.IfDigital(""));
        assertFalse(f1.IfDigital("yesthe"));

    }


    @Test
    void getThedata() {
        assertNotNull(f1.getThedata());
        assertEquals(f1.getThedata().size(),0);
        f1.SaveInfoInListView("Côte d’Ivoire");
        assertTrue(f1.getThedata().contains("Côte d’Ivoire"));
        assertEquals(f1.getThedata().size(),1);
    }

    @Test
    void setRequest() {
//        f1.setRequest(yourHttpRequest);
        fiat f2 =new fiat();
        f2.setRequest(yourHttpRequest);
        assertEquals(f1.getRequest(),yourHttpRequest);
        assertEquals(f2.getRequest(),yourHttpRequest);
        assertEquals(f2.getRequest(),f1.getRequest());
    }

    @Test
    void getRequest() {
        fiat f2 =new fiat();
        f2.setRequest(yourHttpRequest);
        assertEquals(f1.getRequest(),yourHttpRequest);
        assertEquals(f2.getRequest(),yourHttpRequest);
        assertEquals(f2.getRequest(),f1.getRequest());
    }

    @Test
    void getCurrData() {
        assertNotNull(f1.getCurrData());
        assertEquals(f1.getCurrData().size(),0);
        f1.SaveInfoInListView("Côte d’Ivoire");
        assertTrue(f1.getCurrData().contains("XOF"));
        assertEquals(f1.getCurrData().size(),1);
    }

    @Test
    void getCurrAllData() {
        assertNotNull(f1.getCurrAllData());
        assertEquals(f1.getCurrAllData().keySet().size(),0);
        f1.SaveInfoInListView("Côte d’Ivoire");
//        System.out.println(f1.getCurrAllData().keySet());
//        assertEquals(f1.getCurrAllData().keySet().);
        assertEquals(f1.getCurrAllData().keySet().size(),1);
    }

    @Test
    void getMapCur() {
        assertTrue(f1.getMapCur().keySet().contains("CDF"));
        assertTrue(f1.getMapCur().keySet().contains("MMK"));
    }

    @Test
    void getAbout() {
        assertEquals(f1.getAbout(), "My name: Xiaowen Qin\n" +
                "\n" +
                "Application name: CurrencyScoop\n" +
                "\n" +
                "Reference: \n" +
                "The background music is: \n" +
                "Bet On Me (Walk Off the Earth & D Smoke)\n" +
                "\n" +
                "Walk Off the Earth. and Smoke, D., 2022. Bet On Me. [online] Pop. \n" +
                "Available at: <https://orcd.co/wotebetonme> \n" +
                "[Accessed 18 March 2022].\n" +
                "\n" +
                "The CurrencyScoop link: https://currencyscoop.com/\n" +
                "The Pastebin link: https://pastebin.com/\n");
    }

    @Test
    void clearAll() {
        f1.SaveInfoInListView("Côte d’Ivoire");
        assertEquals(f1.getCurrData().size(),1);
        assertEquals(f1.getCurrAllData().keySet().size(),1);
        assertEquals(f1.getThedata().size(),1);
        f1.clearAll();
        assertEquals(f1.getCurrData().size(),0);
        assertEquals(f1.getCurrAllData().keySet().size(),0);
        assertEquals(f1.getThedata().size(),0);
    }

    @Test
    void ifDataContainsCountry() {
        f1.SaveInfoInListView("Côte d’Ivoire");
        assertTrue(f1.IfDataContainsCountry("Côte d’Ivoire"));
        assertFalse(f1.IfDataContainsCountry("yes!"));
    }

    @Test
    void del() {
        f1.SaveInfoInListView("Côte d’Ivoire");
        assertTrue(f1.getThedata().contains("Côte d’Ivoire"));
        assertTrue(f1.getThedata().size()>0);
        assertTrue(f1.getCurrData().contains("XOF"));
        assertEquals(f1.getCurrData().size(),1);
        assertTrue(f1.getCurrAllData().keySet().contains("XOF"));
        assertEquals(f1.getCurrAllData().keySet().size(),1);
        f1.Del("XOF");
        assertEquals(f1.getCurrData().size(),0);
        assertEquals(f1.getCurrAllData().keySet().size(),0);
        assertEquals(f1.getThedata().size(),0);
        f1.SaveInfoInListView("Côte d’Ivoire");
        f1.Del("Côte d’Ivoire");
        assertEquals(f1.getThedata().size(),0);
        assertTrue(f1.getCurrData().contains("XOF"));
        assertEquals(f1.getCurrData().size(),1);
        assertTrue(f1.getCurrAllData().keySet().contains("XOF"));
        assertEquals(f1.getCurrAllData().keySet().size(),1);

    }

    @Test
    void getAllInfo() {
        assertNotNull(f1.getAllInfo());
        assertEquals(f1.getAllInfo().size(),0);
        f1.SaveInfoInListView("Côte d’Ivoire");

        assertEquals(f1.getAllInfo().size(),1);

    }

    @Test
    void outPut() {
        when(yourHttpRequest.output("Information report")).thenReturn("link");
        assertEquals(f1.outPut("Information report"),"link");

    }


    @Test
    void ifPastebinLink() {
        assertTrue(f1.IfPastebinLink("https://"));
        assertFalse(f1.IfPastebinLink("test"));
    }

    @Test
    void getIsPlaying() {
        f1.setIsPlaying(true);
        assertTrue(f1.getIsPlaying());
        f1.setIsPlaying(false);
        assertFalse(f1.getIsPlaying());
    }

    @Test
    void setIsPlaying() {
        f1.setIsPlaying(true);
        assertTrue(f1.getIsPlaying());
        f1.setIsPlaying(false);
        assertFalse(f1.getIsPlaying());
    }

    @Test
    void getCache() {
        f1.setCache(sqLcache);
        assertEquals(sqLcache,f1.getCache());
    }

    @Test
    void setCache() {
        f1.setCache(sqLcache);
        assertEquals(sqLcache,f1.getCache());
    }

    @Test
    void clearCache() {
        f1.clearCache();

    }

    @Test
    void setHasBalance() {
        f1.setHasBalance(true);
        assertTrue(f1.isHasBalance());
        f1.setHasBalance(false);
        assertFalse(f1.isHasBalance());
    }

    @Test
    void isHasBalance() {
        f1.setHasBalance(true);
        assertTrue(f1.isHasBalance());
        f1.setHasBalance(false);
        assertFalse(f1.isHasBalance());
    }

    @Test
    void getBalance() {
        f1.setBalance(50);
        assertEquals(f1.getBalance(),50);
        assertNotEquals(f1.getBalance(),100.5);
    }

    @Test
    void setBalance() {
        f1.setBalance(60);
        assertEquals(f1.getBalance(),60);
        assertNotEquals(f1.getBalance(),120.5);
    }

    @Test
    void updateBalance() {
        f1.setBalance(60);
        f1.updateBalance("20");
        assertEquals(f1.getBalance(),40);
        assertNotEquals(f1.getBalance(),1223.5);
    }

    @Test
    void ifOutOfMoney() {
        f1.setBalance(10);
        assertTrue(f1.ifOutOfMoney("1000"));
        assertFalse(f1.ifOutOfMoney("10"));
        f1.setBalance(0);
        assertTrue(f1.ifOutOfMoney("10"));
    }

    @Test
    void ifAcceptableNum() {
        assertTrue(f1.ifAcceptableNum("999"));
        assertTrue(f1.ifAcceptableNum("11"));
        assertFalse(f1.ifAcceptableNum("1000"));
        assertFalse(f1.ifAcceptableNum("10"));
    }

    @Test
    void ifInteger() {
        assertTrue(f1.ifInteger("928"));
        assertTrue(f1.ifInteger("82"));
        assertFalse(f1.ifInteger("000318"));
        assertFalse(f1.ifInteger("00ajw"));
        assertFalse(f1.ifInteger("928.3313"));
    }
}