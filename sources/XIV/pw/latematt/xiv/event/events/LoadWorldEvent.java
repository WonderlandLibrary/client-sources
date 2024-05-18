package pw.latematt.xiv.event.events;

import net.minecraft.client.multiplayer.WorldClient;
import pw.latematt.xiv.event.Event;

/**
 * @author Matthew
 */
public class LoadWorldEvent extends Event {
    private final WorldClient world;

    public LoadWorldEvent(WorldClient world) {
        this.world = world;
    }

    public WorldClient getWorld() {
        return world;
    }
}
