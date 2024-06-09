package com.client.glowclient.events;

import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.*;

public class EventRenderWorld extends Event
{
    private final La B;
    private final Vec3d b;
    
    public void setTranslation(final Vec3d vec3d) {
        this.getBuffer().setTranslation(-vec3d.x, -vec3d.y, -vec3d.z);
    }
    
    public Vec3d getRenderPos() {
        return this.b;
    }
    
    public EventRenderWorld(final La b, final Vec3d b2) {
        super();
        this.B = b;
        this.b = b2;
    }
    
    public void resetTranslation() {
        this.setTranslation(this.b);
    }
    
    public BufferBuilder getBuffer() {
        return this.B.getBuffer();
    }
    
    public La getTessellator() {
        return this.B;
    }
}
