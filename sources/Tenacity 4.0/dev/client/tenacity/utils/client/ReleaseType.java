package dev.client.tenacity.utils.client;

public enum ReleaseType {

    PUBLIC("Public"),
    BETA("Beta"),
    DEV("Developer");

    private final String name;

    ReleaseType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
