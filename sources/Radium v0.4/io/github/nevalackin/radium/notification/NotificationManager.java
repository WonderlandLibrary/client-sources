package io.github.nevalackin.radium.notification;

import io.github.nevalackin.radium.RadiumClient;
import io.github.nevalackin.radium.utils.handler.Manager;
import io.github.nevalackin.radium.utils.render.LockedResolution;
import net.minecraft.client.gui.ScaledResolution;

import java.util.ArrayList;
import java.util.List;

public final class NotificationManager extends Manager<Notification> {
    public NotificationManager() {
        super(new ArrayList<>());
        RadiumClient.getInstance().getEventBus().subscribe(this);
    }

    public void render(ScaledResolution scaledResolution, LockedResolution lockedResolution, boolean inGame) {
        List<Notification> notifications = getElements();

        Notification remove = null;

        for (int i = 0; i < notifications.size(); i++) {
            Notification notification = notifications.get(i);

            if (notification.isDead()) {
                remove = notification;
                continue;
            }

            int width;
            int height;
            if (inGame) {
                width = lockedResolution.getWidth();
                height = lockedResolution.getHeight();
            } else {
                width = scaledResolution.getScaledWidth();
                height = scaledResolution.getScaledHeight();
            }

            notification.render(width, height, i + 1);
        }

        if (remove != null)
            getElements().remove(remove);
    }

    public void add(Notification notification) {
        getElements().add(notification);
    }

}
