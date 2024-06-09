package us.dev.direkt.event.internal.events.game.world;

import net.minecraft.world.World;
import us.dev.direkt.event.Event;

/**
 * @author Foundry
 */
public class EventLoadWorld implements Event {
    private World world;

    public EventLoadWorld(final World world) {
        this.world = world;
    }

    public World getWorld() {
        return this.world;
    }
}
