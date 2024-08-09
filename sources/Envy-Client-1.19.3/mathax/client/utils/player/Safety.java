package mathax.client.utils.player;

public enum Safety {
    Safe("Safe"),
    Suicide("Suicide");

    private final String title;

    Safety(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
