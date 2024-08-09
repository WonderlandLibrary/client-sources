package mathax.client.events.game;

import mathax.client.events.Cancellable;
import net.minecraft.client.option.Perspective;

public class ChangePerspectiveEvent extends Cancellable {
    private static final ChangePerspectiveEvent INSTANCE = new ChangePerspectiveEvent();

    public Perspective perspective;

    public static ChangePerspectiveEvent get(Perspective perspective) {
        INSTANCE.setCancelled(false);
        INSTANCE.perspective = perspective;
        return INSTANCE;
    }
}
