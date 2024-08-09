package xyz.mathax.mathaxclient.renderer;

public enum ShapeMode {
    Lines("Lines"),
    Sides("Sides"),
    Both("Both");

    private final String name;

    ShapeMode(String name) {
        this.name = name;
    }

    public boolean lines() {
        return this == Lines || this == Both;
    }

    public boolean sides() {
        return this == Sides ||this == Both;
    }

    @Override
    public String toString() {
        return name;
    }
}
