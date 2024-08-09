package mathax.client.gui.utils;

public enum AlignmentX {
    Left("Left"),
    Center("Center"),
    Right("Right");

    private final String title;

    AlignmentX(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
