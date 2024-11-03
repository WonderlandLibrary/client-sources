package dev.stephen.nexus.utils.render.notifications;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.player.EventTickPre;
import dev.stephen.nexus.event.impl.render.EventRender2D;
import dev.stephen.nexus.module.modules.client.Notifications;
import dev.stephen.nexus.utils.Utils;
import dev.stephen.nexus.utils.render.notifications.impl.Notification;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class NotificationManager implements Utils {

    private final List<Notification> notifications = new CopyOnWriteArrayList<>();

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        notifications.removeIf(Notification::shouldDisappear);
    };

    @EventLink
    public final Listener<EventRender2D> eventRender2DListener = event -> {
        if (mc.player == null || mc.world == null) {
            return;
        }
        if (!Client.INSTANCE.getModuleManager().getModule(Notifications.class).isEnabled()) {
            return;
        }
        int yOffset = 0;
        for (Notification notification : notifications) {
            notification.render(event, yOffset);
            yOffset += 17;
        }
    };

    public void addNewNotification(Notification notification) {
        notifications.add(notification);
    }
}
