package net.minecraft.client.main.neptune.Events;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.main.neptune.memes.events.Memevnt;

public class NametagRenderEvent implements Memevnt
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
        this.y = y + 5;
        this.z = z;
    }
}
