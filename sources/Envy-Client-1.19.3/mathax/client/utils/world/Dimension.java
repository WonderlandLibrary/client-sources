package mathax.client.utils.world;

public enum Dimension {
    Overworld("Overworld"),
    Nether("Nether"),
    End("End");

    private final String title;

    Dimension(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
