package asm2.model;

public class latestMeta {
    private int code;
    private String disclaimer;
    public latestMeta(int code,String disclaimer){
        this.code =code;
        this.disclaimer =disclaimer;
    }

    public int getCode() {
        return code;
    }

    public String getDisclaimer() {
        return disclaimer;
    }
}
