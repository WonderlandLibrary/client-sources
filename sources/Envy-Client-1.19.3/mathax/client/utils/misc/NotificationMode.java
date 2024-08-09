package mathax.client.utils.misc;

public enum NotificationMode {
    Chat("Chat"),
    Toast("Toast"),
    Both("Both");

    private final String title;

    NotificationMode(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
