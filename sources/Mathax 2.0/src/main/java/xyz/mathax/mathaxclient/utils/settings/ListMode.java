package xyz.mathax.mathaxclient.utils.settings;

public enum ListMode {
    Whitelist("Whitelist"),
    Blacklist("Blacklist");

    private final String name;

    ListMode(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
