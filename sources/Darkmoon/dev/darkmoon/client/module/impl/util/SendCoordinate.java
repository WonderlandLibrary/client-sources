package dev.darkmoon.client.module.impl.util;

import com.darkmagician6.eventapi.EventTarget;
import com.darkmagician6.eventapi.events.Event;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.manager.notification.NotificationManager;
import dev.darkmoon.client.manager.notification.NotificationType;

@ModuleAnnotation(name = "SendCoordinate", category = Category.UTIL)
public class SendCoordinate extends Module {

    @Override
    public void onEnable() {
        if (mc.player != null) {
            mc.player.sendChatMessage("!" + (int) mc.player.getPosX() + ", " + (int) mc.player.getPosY() + ", " + (int) mc.player.getPosZ());
            NotificationManager.notify(NotificationType.SUCCESS, "SendCoordinate", "Coordinates sended to chat!", 2.3f);
            super.onEnable();
        }
    }
}