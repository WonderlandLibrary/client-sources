package com.client.glowclient.events;

import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.*;
import net.minecraft.client.gui.*;
import com.client.glowclient.utils.*;

public class EventRenderScreen extends Event
{
    private final float A;
    private final ca B;
    private final ScaledResolution b;
    
    public EventRenderScreen(final float a) {
        super();
        this.b = new ScaledResolution(Wrapper.mc);
        this.B = new ca();
        this.A = a;
    }
    
    public ca getSurfaceBuilder() {
        return this.B;
    }
    
    public float getPartialTicks() {
        return this.A;
    }
    
    public double getScreenHeight() {
        return this.b.getScaledHeight_double();
    }
    
    public double getScreenWidth() {
        return this.b.getScaledWidth_double();
    }
}
