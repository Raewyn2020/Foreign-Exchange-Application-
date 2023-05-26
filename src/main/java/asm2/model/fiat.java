package asm2.model;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class fiat {
    private httpRequest request = new httpRequest();

    /**
     * Add a float number as a parameter in order to be able to store the
     * balance.
     */
    private float balance;

    /**
     * A boolean value to be used for checking if the user has set a balance.
     */
    private boolean hasBalance = false;

    private Boolean isPlaying;
    private String about = "My name: Xiaowen Qin\n\nApplication name: " +
            "CurrencyScoop\n\n" +
            "Reference: \nThe background music is: \nBet On Me (Walk Off the " +
            "Earth & D Smoke)\n\nWalk Off the Earth. and Smoke, D., 2022. Bet" +
            " " +
            "On Me. [online] Pop. \nAvailable at: <https://orcd.co/wotebetonme> \n[Accessed 18 March 2022].\n" +
            "\nThe CurrencyScoop link: https://currencyscoop.com/\n" +
            "The Pastebin link: https://pastebin.com/\n";
    private List<String> thedata = new ArrayList<>();
    private List<String> currData = new ArrayList<>();
    private HashMap<String,String> currAllData = new HashMap<>();
    private Map<String, currency> mapCur = this.mapFiat();
    private SQLcache cache = new SQLcache();
    public List<String> getThedata(){
        return thedata;
    }
    public Boolean getIsPlaying() {
        return isPlaying;
    }
    public void setIsPlaying(Boolean isPlaying){
        this.isPlaying = isPlaying;
    }
    public SQLcache getCache(){
        return cache;
    }
    public void setCache(SQLcache cache){
        this.cache =cache;
    }
    public void setRequest(httpRequest request){
        this.request =request;
    }
    public httpRequest getRequest(){
        return this.request;
    }
    public List<String> getCurrData(){
        return currData;
    }
    public HashMap<String,String> getCurrAllData(){
        return currAllData;
    }
    public Map<String,currency> getMapCur(){
        return mapCur;
    }
    public String getAbout(){
        return about;
    }

    /**
     * Set the boolean value which is used for checking if the user has set a
     * balance.
     * @param hasBalance The boolean value used for determining
     *                   if the user has set the balance.
     */
    public void setHasBalance(boolean hasBalance) {
        this.hasBalance = hasBalance;
    }

    /**
     * Get the boolean value which is used for checking if the user has
     * set a balance.
     * @return  return the boolean value which is used for checking if the user has set a balance.
     */
    public boolean isHasBalance() {
        return hasBalance;
    }

    /**
     * Get the balance set by the user.
     * @return  return the balance set by the user.
     */
    public float getBalance() {
        return balance;
    }

    /**
     * Stores the balance set by the user.
     * @param balance The balance set by the user.
     */
    public void setBalance(float balance) {
        this.balance = balance;}

    /**
     * After the conversion, the amount entered 'from' is subtracted from the balance.
     * @param amount the amount entered 'from'.
     */
    public void updateBalance(String amount) {
        this.balance = balance - Float.valueOf(amount.toString());
    }

    /**
     * Before the conversion, it needs to check whether the balance can
     * support the conversion or not. (if the balance would go below 0')
     * @param amount the amount entered 'from'.
     * @return  Returns true if balance would go below 0, false if balance can support the conversion.
     */
    public boolean ifOutOfMoney(String amount){
        if(this.balance==0 || this.balance<Float.valueOf(amount.toString())){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Check if the balance set by the user is between 10 and 1000 or not.
     * @param balanceNum the balance set by the user.
     * @return  Returns true if balance is between 10 and 1000. Otherwise, return false.
     */
    public boolean ifAcceptableNum(String balanceNum){
        if(10<Float.valueOf(balanceNum.toString())&& Float.valueOf(balanceNum.toString())<1000){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Check if the balance set by the user is an integer or not.
     * @param balanceNum the balance set by the user.
     * @return  Returns true if balance is an integer. Otherwise, return false.
     */
    public boolean ifInteger(String balanceNum){
        if(IfDigital(balanceNum)){
            if(balanceNum.contains(".")){
                return false;
            }else{
                String firstCharacter = balanceNum.substring(0,1);
                if(!firstCharacter.equals("0")){
                    return true;
                }else{
                    return false;
                }
            }
        }else{
            return false;
        }
    }
    public void clearAll(){
        thedata.clear();
        currData.clear();
        currAllData.clear();
    }

    public void clearCache(){
        cache.clearDB();
    }
    public Boolean IfDataContainsCountry(String country){
        for(String i:this.getThedata()){
            if(i.equals(country)){
                return true;
            }
        }
        return false;
    }
    public void Del(String item){
        for(String i: this.getThedata()){
            if(i.equals(item)){
                this.getThedata().remove(i);
//                getListView().getItems().remove(getItem());
                return;
            }
        }
        Iterator<String> iterator = this.getCurrAllData().keySet().iterator();
        try {
            while (iterator.hasNext()) {
                if (iterator.next().equals(item.substring(0, 3))) {
                    this.getCurrData().remove(item.substring(0, 3));
                    this.getCurrAllData().remove(item.substring(0, 3));
                    int sign = 0;
                    List<String> d = new ArrayList<>();
                    for (String k : this.getThedata()) {
                        for (String n :
                                this.getMapCur().get(item.substring(0,
                                        3)).getCountries()) {
                            if (k.equals(n)) {
                                sign = 1;
                                d.add(n);
                            }
                        }
                    }
                    if (sign == 0 && d.size() == 0) {
//                        getListView().getItems().remove(getItem());
                    } else {
                        this.getThedata().removeAll(d);

                    }

                }
            }
        }catch (java.util.ConcurrentModificationException e){

        }
    }
    public void setMapCur(Map<String, currency> mapCur){
        this.mapCur = mapCur;
    }
    public String WriteReport(String base,String symbol,String amount){
//        if(balance < Double.valueOf(amount.toString())){
//            return "Out of money";
//        }
        if(base!=null && symbol!= null && amount!=null&&  !amount.equals("") && this.IfDigital(amount)) {
            String infoall = "(API)\nBase information: \n";
            infoall += "  currency name: " + mapCur.get(base).getCurrency_name() + "\n";
            infoall += "  currency code: " + mapCur.get(base).getCurrency_code() + "\n";
            infoall += "  decimal units: " + mapCur.get(base).getDecimal_units() + "\n";
            infoall += "  countries: \n";
            for (String ccc :
                    mapCur.get(base).getCountries()) {
                infoall += "    " + ccc + "\n";
            }

            infoall += "\nSymbol information:\n";
            infoall += "  currency name: " + mapCur.get(symbol).getCurrency_name() + "\n";
            infoall += "  currency code: " + mapCur.get(symbol).getCurrency_code() + "\n";
            infoall += "  decimal units: " + mapCur.get(symbol).getDecimal_units() + "\n";
            infoall += "  countries: \n";
            for (String cc2 :
                    mapCur.get(symbol).getCountries()) {
                infoall += "    " + cc2 + "\n";
            }
            String info = request.getConvertRequest(
                    base,
                    symbol, amount);
            String rate = request.getLatestRequest(
                    base, symbol);
            cache.cache(base,symbol,rate);
            String report =   infoall + "\n" + info + "rate: " + rate;
            report = report.replace("&","and");
            return report;
        }else{
            return "";
        }
    }
    public Boolean IfCache(String base,String symbol){
        return cache.IfContains(base,symbol);
    }
    public String UseCache(String base,String symbol,String amount){
        if(base!=null && symbol!= null && amount!=null&&  !amount.equals("") && this.IfDigital(amount)) {
            String rate = cache.getRate(base,symbol);
            float value = Float.parseFloat(rate) * Float.parseFloat(amount);
            String report ="(Cache)\nBase information: "+
                    this.getCurrAllData().get(base).substring(3,
                            this.getCurrAllData().get(base).length())  +
                    "\nSymbol " +
                    "information:"+this.getCurrAllData().get(symbol).substring(3,this.getCurrAllData().get(symbol).length()) + "\nfrom: " +base+
                    "\nto: "+symbol+"\namount: "+amount+"\nvalue:"+String.valueOf(value)+
                    "\nrate: " + rate;
            report = report.replace("&","and");
            return report;

        }
        return "";

    }
    public Boolean IfDigital(String amount){
        if(amount==null || amount.equals("")){
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]*\\.?[0-9]+");
        Matcher isNum = pattern.matcher(amount);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
    public Collection<String> getAllInfo(){
        return currAllData.values();
    }
    public void SaveInfoInListView(String item){
        thedata.add(item);
        for(String i: mapCur.keySet()) {
            for (String j : mapCur.get(i).getCountries()) {

                if (j.equals(item)) {
                    String infomation =
                            i + "\n" +"  currency name: "+ mapCur.get(i).getCurrency_name() + "\n" +"  currency code: "+ mapCur.get(i).getCurrency_code()+"\n"+
                                    "  decimal " +
                                    "units: " +
                                    mapCur.get(i).getDecimal_units() + "\n"+
                                    "  countries:\n";

                    for (String co : mapCur.get(i).getCountries()) {
                        infomation += "    "+co + "\n";
                    }
                    if (!currData.contains(i)) {
                        currData.add(i);
                        currAllData.put(i, infomation);
                    }
                } else if (item.contains("Congo")) {

                    String infomation =
                            "CDF" + "\n"+"  currency name: " +  mapCur.get(
                                    "CDF").getCurrency_name() +
                                    "\n" +"  currency code: "+ mapCur.get(
                                            "CDF").getCurrency_code()+"\n"+
                                    "  decimal " +
                                    "units: " +
                                    mapCur.get(
                                            "CDF").getDecimal_units() + "\n"+
                                    "  countries:\n";

                    for (String co : mapCur.get("CDF").getCountries()) {
                        infomation += "    "+co + "\n";
                    }
                    if (!currData.contains("CDF")) {
                        currData.add("CDF");
                        currAllData.put("CDF", infomation);
                    }
                } else if (item.contains("Myanmar")) {

                    String infomation =
                            "MMK" + "\n" +"  currency name: " +  mapCur.get(
                                    "MMK").getCurrency_name() +
                                     "\n" +"  currency code: "+ mapCur.get(
                                             "MMK").getCurrency_code()+"\n"+
                                    "  decimal " +
                                    "units: " +
                                    mapCur.get(
                                            "MMK").getDecimal_units() + "\n"+
                                    "  countries:\n";

                    for (String co : mapCur.get("MMK").getCountries()) {
                        infomation += "    "+co + "\n";
                    }
                    if (!currData.contains("MMK")) {
                        currData.add("MMK");
                        currAllData.put("MMK", infomation);
                    }
                }
            }
        }
    }
    public String outPut(String code){
        return request.output(code);
    }
    public Map<String, currency> mapFiat(){

        Map<String, currency> mapCur = request.getCurrenciesRequest("fiat");

        if(mapCur!=null) {
            for(String i: mapCur.keySet()) {

                for (int j = 0; j < mapCur.get(i).getCountries().size(); j++) {

                    int index1 =
                            mapCur.get(i).getCountries().get(j).indexOf(String.valueOf("("));
                    if (index1 != -1) {
                        String new1 =
                                mapCur.get(i).getCountries().get(j).substring(0, index1-1);
                        mapCur.get(i).getCountries().set(j,new1);
                    }
                    int index2 = mapCur.get(i).getCountries().get(j).indexOf(String.valueOf(" ["));
                    if (index2 != -1) {
                        String new1 = mapCur.get(i).getCountries().get(j).substring(0, index2);
                        mapCur.get(i).getCountries().set(j,new1);
                    }
                    int index3 =
                            mapCur.get(i).getCountries().get(j).indexOf(String.valueOf("["));
                    if (index3 != -1) {
                        String new1 = mapCur.get(i).getCountries().get(j).substring(0, index3);
                        mapCur.get(i).getCountries().set(j,new1);
                    }
                    if (mapCur.get(i).getCountries().get(j).equals("Trinidad and Tobago")) {
                        String new1 = "Trinidad & Tobago";
                        mapCur.get(i).getCountries().set(j,new1);
                    }
                    if (mapCur.get(i).getCountries().get(j).contains(" and ")) {
                        String new1 =
                                mapCur.get(i).getCountries().get(j).replace(
                                        " and ", " & ");
                        mapCur.get(i).getCountries().set(j,new1);
//                        Svalbard & Jan Mayen
//                        Svalbard &  Jan Mayen


                    }
                    if (
                            mapCur.get(i).getCountries().get(j).equals(
                            "Svalbard " +
                            "and  Jan Mayen")|| mapCur.get(i).getCountries().get(j).equals("Svalbard and  Jan Mayen (SJ)")||

                            mapCur.get(i).getCountries().get(j).equals("Svalbard &  Jan Mayen")) {
                        String new1 = "Svalbard & Jan Mayen";
                        mapCur.get(i).getCountries().set(j,new1);
                    }

                    if (mapCur.get(i).getCountries().get(j).equals("Côte d'Ivoire")) {
                        String new1 = "Côte d’Ivoire";
                        mapCur.get(i).getCountries().set(j,new1);
                    }

                }
            }
            mapCur.get("CDF").getCountries().add("Congo - Kinshasa");
            mapCur.get("CDF").getCountries().add("Congo - Brazzaville");
//        } else if (item.contains("Myanmar")) {
            mapCur.get("MMK").getCountries().add("Myanmar (Burma)");
            return mapCur;
        }else{
            return new HashMap<String, currency>();
        }
    }
    public Boolean IfPastebinLink(String response){
        String str = response.substring(0,4);
        if(str.equals("http")){
            return true;
        }else {
            return false;
        }
    }
}
