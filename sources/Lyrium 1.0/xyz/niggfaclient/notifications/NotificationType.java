// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.notifications;

import java.awt.Color;

public enum NotificationType
{
    SUCCESS("Success", new Color(3462484).getRGB()), 
    INFO("Info", new Color(16777215).getRGB()), 
    WARNING("Warning", new Color(13423136).getRGB()), 
    ERROR("Error", new Color(13120307).getRGB());
    
    public final String name;
    public final int color;
    
    private NotificationType(final String name, final int color) {
        this.name = name;
        this.color = color;
    }
    
    public int getColor() {
        return this.color;
    }
    
    public String getName() {
        return this.name;
    }
}
