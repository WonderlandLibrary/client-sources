package dev.excellent.client.notification;


import dev.excellent.Excellent;
import dev.excellent.api.event.impl.render.Render2DEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.api.interfaces.game.IMinecraft;
import dev.excellent.client.notification.impl.ErrorNotification;
import dev.excellent.client.notification.impl.InfoNotification;
import dev.excellent.client.notification.impl.WarningNotification;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public final class NotificationManager extends ArrayList<Notification> implements IMinecraft {

    public void init() {
        Excellent.getInst().getEventBus().register(this);
    }

    public void register(final String content, final NotificationType type, long delay) {
        final Notification notification = switch (type) {
            case WARNING -> new WarningNotification(content, delay);
            case ERROR -> new ErrorNotification(content, delay);
            default -> new InfoNotification(content, delay);
        };

        this.add(notification);
    }

    private final Listener<Render2DEvent> onRender2D = event -> {
        if (this.size() == 0 || mc.player == null || mc.world == null) return;
        int i = 0;
        Iterator<Notification> iterator = this.iterator();
        try {
            while (iterator.hasNext()) {
                Notification notification = iterator.next();
                notification.render(event.getMatrix(), i);
                if (notification.hasExpired()) {
                    iterator.remove();
                }
                i++;
            }
        } catch (ConcurrentModificationException ignored) {
        }
    };
}
