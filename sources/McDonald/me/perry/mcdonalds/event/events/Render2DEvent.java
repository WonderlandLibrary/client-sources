// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.event.events;

import net.minecraft.client.gui.ScaledResolution;
import me.perry.mcdonalds.event.EventStage;

public class Render2DEvent extends EventStage
{
    public float partialTicks;
    public ScaledResolution scaledResolution;
    
    public Render2DEvent(final float partialTicks, final ScaledResolution scaledResolution) {
        this.partialTicks = partialTicks;
        this.scaledResolution = scaledResolution;
    }
    
    public void setPartialTicks(final float partialTicks) {
        this.partialTicks = partialTicks;
    }
    
    public void setScaledResolution(final ScaledResolution scaledResolution) {
        this.scaledResolution = scaledResolution;
    }
    
    public double getScreenWidth() {
        return this.scaledResolution.getScaledWidth_double();
    }
    
    public double getScreenHeight() {
        return this.scaledResolution.getScaledHeight_double();
    }
}
