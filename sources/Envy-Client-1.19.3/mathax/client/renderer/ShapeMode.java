package mathax.client.renderer;

public enum ShapeMode {
    Lines("Lines"),
    Sides("Sides"),
    Both("Both");

    private final String title;

    ShapeMode(String title) {
        this.title = title;
    }

    public boolean lines() {
        return this == Lines || this == Both;
    }

    public boolean sides() {
        return this == Sides ||this == Both;
    }

    @Override
    public String toString() {
        return title;
    }
}
