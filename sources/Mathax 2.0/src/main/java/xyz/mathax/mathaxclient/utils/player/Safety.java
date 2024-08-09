package xyz.mathax.mathaxclient.utils.player;

public enum Safety {
    Safe("Safe"),
    Suicide("Suicide");

    private final String name;

    Safety(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
