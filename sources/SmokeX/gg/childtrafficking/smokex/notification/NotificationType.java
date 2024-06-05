// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.notification;

import java.awt.Color;

public enum NotificationType
{
    SUCCESS(new Color(62994).getRGB()), 
    INFO(new Color(40, 40, 40).getRGB()), 
    WARNING(new Color(16774931).getRGB()), 
    ERROR(new Color(16736066).getRGB());
    
    private final int color;
    
    private NotificationType(final int color) {
        this.color = color;
    }
    
    public int getColor() {
        return this.color;
    }
}
