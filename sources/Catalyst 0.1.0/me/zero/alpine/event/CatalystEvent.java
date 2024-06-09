// 
// Decompiled by Procyon v0.5.36
// 

package me.zero.alpine.event;

import net.minecraft.client.Minecraft;
import me.zero.alpine.event.type.Cancellable;

public class CatalystEvent extends Cancellable
{
    private Era era;
    private final float partialTicks;
    
    public CatalystEvent() {
        this.era = Era.PRE;
        this.partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
    }
    
    public Era getEra() {
        return this.era;
    }
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
    
    public enum Era
    {
        PRE, 
        PERI, 
        POST;
    }
}
