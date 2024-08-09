package mathax.client.events.entity.player;

public class InteractEvent {
    private static final InteractEvent INSTANCE = new InteractEvent();
    public boolean usingItem;

    public static InteractEvent get(final boolean usingItem) {
        InteractEvent.INSTANCE.usingItem = usingItem;
        return InteractEvent.INSTANCE;
    }
}
