// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.handlers.notifications;

import com.klintos.twelve.Twelve;

public class Notification
{
    private String message;
    private int colour;
    private long prevMS;
    
    public Notification(final String message, final int colour) {
        this.message = message;
        this.colour = colour;
        this.prevMS = System.nanoTime() / 1000000L;
        Twelve.getMinecraft().thePlayer.playSound("random.pop", 100.0f, 1.0f);
    }
    
    public int getColour() {
        return this.colour;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public boolean shouldClose() {
        return System.nanoTime() / 1000000L - this.prevMS >= 4000L;
    }
}
