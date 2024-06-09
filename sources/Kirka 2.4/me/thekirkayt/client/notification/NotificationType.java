/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.notification;

public enum NotificationType {
    WARNING("WARNING", 0, "Warning"),
    HINT("HINT", 1, "Hint"),
    INFO("INFO", 2, "Info");
    
    private String name;

    private NotificationType(String s, int n2, String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

