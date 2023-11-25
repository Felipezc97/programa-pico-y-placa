package com.felipe.samples.picoplaca.model;

public enum PicoPlacaConfiguration {

    MONDAY(new int[]{1, 2}),
    TUESDAY(new int[]{3, 4}),
    WEDNESDAY(new int[]{5, 6}),
    THURSDAY(new int[]{7, 8}),
    FRIDAY(new int[]{9, 0});
    private int[] allowedDigits;

    PicoPlacaConfiguration(int[] allowedDigits) {
        this.allowedDigits = allowedDigits;
    }

    public int[] getAllowedDigits() {
        return allowedDigits;
    }
}
