package net.augustus.notify;

import net.augustus.Augustus;
import net.augustus.modules.render.Notifications;
import net.augustus.notify.reborn.CleanNotification;
import net.augustus.notify.reborn.CleanNotificationManager;
import net.augustus.notify.xenza.Notification;
import net.augustus.notify.xenza.NotificationsManager;
import net.augustus.utils.interfaces.MM;

public class GeneralNotifyManager implements MM {
    public static void addNotification(String content, NotificationType type) {
        switch(mm.notifications.mode.getSelected()) {
            case "Xenza": {
                NotificationsManager.addNotification(new Notification(content, type));
                break;
            }
            case "Rise5": {
                Augustus.getInstance().getRise5notifyManager().registerNotification(content, type);
                break;
            }
            case "New": {
                CleanNotificationManager.addCleanNotification(new CleanNotification(content, type));
                break;
            }
        }
    }
}
