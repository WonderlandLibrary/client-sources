/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.notification;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Notification {
    public final String message;
    public final long addTime;
    public static final List<Notification> notifications = new CopyOnWriteArrayList<Notification>();

    public Notification(String message) {
        this.message = message;
        this.addTime = System.currentTimeMillis();
        notifications.add(this);
    }
}

