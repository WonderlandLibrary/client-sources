package xyz.mathax.mathaxclient.utils.world;

public enum Dimension {
    Overworld("Overworld"),
    Nether("Nether"),
    End("The End");

    private final String name;

    Dimension(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public Dimension opposite() {
        return switch (this) {
            case Overworld -> Nether;
            case Nether -> Overworld;
            default -> this;
        };
    }
}
