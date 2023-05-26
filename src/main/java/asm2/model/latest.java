package asm2.model;

public class latest {
    private latestResponse response;
    private latestMeta meta;
    public latest(latestResponse response, latestMeta meta){
        this.meta =meta;
        this.response=response;

    }

    public latestMeta getLatestMeta() {
        return meta;
    }

    public latestResponse getLatestResponse() {
        return response;
    }

    @Override
    public String toString() {
//        return "rate based on this time: "+getLatestResponse().getDate()+"\n";
        return "code: "+getLatestMeta().getCode()+"\n"+"disclaimer: "+getLatestMeta().getDisclaimer()+"\n"
                +"date: "+getLatestResponse().getDate()+"\n"+"base: "+getLatestResponse().getBase()+"\n"+"rates: ";
    }
}
