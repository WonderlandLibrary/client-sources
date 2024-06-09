package com.client.glowclient.events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.item.*;

@Cancelable
public class EventBoat extends Event
{
    private float B;
    private EntityBoat b;
    
    public EventBoat(final EntityBoat b, final float b2) {
        super();
        this.b = b;
        this.B = b2;
    }
    
    public void setYaw(final float b) {
        this.B = b;
    }
    
    public EntityBoat getBoat() {
        return this.b;
    }
    
    public float getYaw() {
        return this.B;
    }
}
