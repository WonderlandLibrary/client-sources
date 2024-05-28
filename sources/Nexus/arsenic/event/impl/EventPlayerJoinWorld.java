package arsenic.event.impl;

import arsenic.event.types.Event;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EventPlayerJoinWorld implements Event {

    private final EntityPlayer entity;
    private final World world;

    public EventPlayerJoinWorld(EntityPlayer entity, World world) {
        this.entity = entity;
        this.world = world;
    }

    public EntityPlayer getEntity() {
        return entity;
    }

    public World getWorld() {
        return world;
    }

}
