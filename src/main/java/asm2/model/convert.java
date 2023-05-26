package asm2.model;

public class convert {
    private latestResponse response;
    private latestMeta meta;
    public convert(latestResponse response, latestMeta meta){
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
        return
//                "code: " + getLatestMeta().getCode() + "\n" + "disclaimer: " + getLatestMeta().getDisclaimer() + "\n"
//                +"timestamp: "+ getLatestResponse().getTimestamp()+"\n"
//                +"date" + ": " + getLatestResponse().getDate() + "\n"
                "from: "+getLatestResponse().getFrom()+"\n"
                +"to: "+getLatestResponse().getTo()+"\n"
                +"amount: "+getLatestResponse().getAmount()+"\n"
                +"value: "+getLatestResponse().getValue()+"\n";

    }

}
