package mathax.client.gui.utils;

public enum AlignmentY {
    Top("Top"),
    Center("Center"),
    Bottom("Bottom");

    private final String title;

    AlignmentY(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
