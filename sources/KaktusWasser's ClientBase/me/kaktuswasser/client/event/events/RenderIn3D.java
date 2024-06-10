// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.event.events;

import me.kaktuswasser.client.event.Event;

public class RenderIn3D extends Event
{
    float partialTicks;
    int pass;
    
    public RenderIn3D(final float partialTicks, final int pass) {
        this.partialTicks = partialTicks;
        this.pass = pass;
    }
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
    
    public int getPass() {
        return this.pass;
    }
}
