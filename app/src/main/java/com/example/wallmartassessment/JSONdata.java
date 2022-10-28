package com.example.wallmartassessment;

public class JSONdata {

    private final String name;
    private final String region;
    private final String code;
    private final String capital;

    public JSONdata(String name, String region, String code, String capital) {
        this.name = name;
        this.region = region;
        this.code = code;
        this.capital = capital;
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public String getCode() {
        return code;
    }

    public String getCapital() {
        return capital;
    }
}
