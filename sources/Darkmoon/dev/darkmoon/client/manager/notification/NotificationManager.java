package dev.darkmoon.client.manager.notification;

import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.module.impl.render.Notifications;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.CopyOnWriteArrayList;

public class NotificationManager {
    @Getter
    @Setter
    private static float defaultTime = 250;

    @Getter
    private static final CopyOnWriteArrayList<Notification> notifications = new CopyOnWriteArrayList<>();

    public static void notify(NotificationType type, String title, String description) {
        notify(new Notification(type, title, description));
    }

    public static void notify(NotificationType type, String title, String description, float time) {
        notify(new Notification(type, title, description, time));
    }

    private static void notify(Notification notification) {
        if (DarkMoon.getInstance().getModuleManager().getModule(Notifications.class).isEnabled()) {
            notifications.add(notification);
        }
    }
}
