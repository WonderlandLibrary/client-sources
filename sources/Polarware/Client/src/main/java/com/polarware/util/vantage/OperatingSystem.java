package com.polarware.util.vantage;

public enum OperatingSystem {
    WINDOWS("Windows"),
    MACOSX("MacOS"),
    LINUX("Linux");

    private final String os;

    OperatingSystem(String os) {
        this.os = os;
    }
    public String getNiceName() {
        return this.os;
    }
}