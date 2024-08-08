// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.event.events;

import net.minecraft.entity.player.EntityPlayer;
import java.util.UUID;
import me.perry.mcdonalds.event.EventStage;

public class ConnectionEvent extends EventStage
{
    private final UUID uuid;
    private final EntityPlayer entity;
    private final String name;
    
    public ConnectionEvent(final int stage, final UUID uuid, final String name) {
        super(stage);
        this.uuid = uuid;
        this.name = name;
        this.entity = null;
    }
    
    public ConnectionEvent(final int stage, final EntityPlayer entity, final UUID uuid, final String name) {
        super(stage);
        this.entity = entity;
        this.uuid = uuid;
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public UUID getUuid() {
        return this.uuid;
    }
    
    public EntityPlayer getEntity() {
        return this.entity;
    }
}
