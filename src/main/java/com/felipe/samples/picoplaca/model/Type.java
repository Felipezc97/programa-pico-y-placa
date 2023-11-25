package com.felipe.samples.picoplaca.model;

public enum Type {



    AUTO(Constants.USAGE_PARTICULAR, false),
    TAXI(Constants.USAGE_PUBLIC, true),
    TRUCK(Constants.USAGE_PARTICULAR, false),
    SCHOOL(Constants.USAGE_PARTICULAR, false),
    BUS(Constants.USAGE_PUBLIC, true);

    private String usage;
    private boolean exempt;

    Type(String usage, boolean exempt) {
        this.usage = usage;
        this.exempt = exempt;
    }

    public String getUsage() {
        return usage;
    }

    public boolean isExempt() { return exempt; }
}
