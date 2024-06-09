// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.notification;

public enum NotificationType
{
    WARNING("WARNING", 0, "Warning"), 
    HINT("HINT", 1, "Hint"), 
    INFO("INFO", 2, "Info");
    
    private String name;
    
    private NotificationType(final String s, final int n, final String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
}
