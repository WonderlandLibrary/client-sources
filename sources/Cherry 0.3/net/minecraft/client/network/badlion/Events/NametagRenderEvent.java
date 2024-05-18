// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Events;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.network.badlion.memes.events.Event;

public class NametagRenderEvent implements Event
{
    public EntityOtherPlayerMP entity;
    public String string;
    public double x;
    public double y;
    public double z;
    
    public NametagRenderEvent(final EntityOtherPlayerMP entity, final String string, final double x, final double y, final double z) {
        this.entity = entity;
        this.string = string;
        this.x = x;
        this.y = y + 5.0;
        this.z = z;
    }
}
