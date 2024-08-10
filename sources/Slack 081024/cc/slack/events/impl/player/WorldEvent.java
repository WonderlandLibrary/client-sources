package cc.slack.events.impl.player;

import cc.slack.events.Event;
import net.minecraft.world.World;

public class WorldEvent extends Event {

    public World lastWorld, newWorld;
    public WorldEvent(World lastWorld, World newWorld) {
        this.lastWorld = lastWorld;
        this.newWorld = newWorld;
    }

}
