package wtf.diablo.client.notification;

import best.azura.eventbus.core.EventBus;
import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import wtf.diablo.client.event.impl.client.renderering.OverlayEvent;

import java.util.concurrent.CopyOnWriteArrayList;

public class NotificationManager {
    public CopyOnWriteArrayList<Notification> notifications = new CopyOnWriteArrayList<>();

    public NotificationManager(final EventBus eventBus) {
        eventBus.register(this);
    }


    @EventHandler
    public final Listener<OverlayEvent> overlayEventListener = e -> {
        drawNotifications();
    };

    public void drawNotifications(){
        int count = 0;
        for(final Notification notification : notifications){
            if(notification.getStartTime() + notification.getDuration() < System.currentTimeMillis()){
                notifications.remove(notification);
                return;
            }
            notification.drawNotification(count, new ScaledResolution(Minecraft.getMinecraft()));
            if(!notification.isHiding()){
                count++;
            }
        }
    }

    public void addNotification(final Notification notification){
        notifications.add(notification);
    }
}