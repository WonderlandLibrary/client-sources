package arsenic.module.property.impl;

public enum DisplayMode {
    NORMAL(""), PERCENT("%"), MILLIS("ms");

    private final String suffix;

    DisplayMode(String suffix) {
        this.suffix = suffix;
    }

    public String getSuffix() { return suffix; }

    @Override
    public String toString() {
        return suffix;
    }
}