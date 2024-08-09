package xyz.mathax.mathaxclient.utils.entity;

public enum Target {
    Head("Head"),
    Body("Body"),
    Feet("Feet");

    private final String name;

    Target(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
