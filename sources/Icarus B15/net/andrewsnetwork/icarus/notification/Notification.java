// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.notification;

import net.andrewsnetwork.icarus.utilities.TimeHelper;

public class Notification
{
    private final TimeHelper time;
    private float x;
    private float y;
    private String text;
    private NotificationType type;
    
    public Notification(final String text, final float x, final float y, final NotificationType type) {
        (this.time = new TimeHelper()).reset();
        this.text = text;
        this.x = x;
        this.y = y;
        this.type = type;
    }
    
    public TimeHelper getTimeHelper() {
        return this.time;
    }
    
    public void setX(final float x) {
        this.x = x;
    }
    
    public void setY(final float y) {
        this.y = y;
    }
    
    public float getX() {
        return this.x;
    }
    
    public float getY() {
        return this.y;
    }
    
    public void setText(final String text) {
        this.text = text;
    }
    
    public String getText() {
        return this.text;
    }
    
    public void setType(final NotificationType type) {
        this.type = type;
    }
    
    public NotificationType getType() {
        return this.type;
    }
}
