// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.notification;

import java.util.Iterator;
import net.minecraft.util.StringUtils;
import net.minecraft.client.Minecraft;

import java.util.concurrent.CopyOnWriteArrayList;

import me.kaktuswasser.client.utilities.RenderHelper;
import me.kaktuswasser.client.utilities.UnicodeFontRenderer;

import java.awt.Font;
import java.util.List;

public class NotificationManager
{
    private final UnicodeFontRenderer notificationText;
    private List<Notification> notifications;
    
    public NotificationManager() {
        this.notificationText = new UnicodeFontRenderer(new Font("Verdana", 0, 28));
    }
    
    public void setupNotifications() {
        this.notifications = new CopyOnWriteArrayList<Notification>();
    }
    
    public void addNotification(final String text, final NotificationType type) {
        if (this.notifications == null || this.notifications.size() > 16) {
            return;
        }
        this.notifications.add(new Notification(text, RenderHelper.getScaledRes().getScaledWidth() / 2 - this.notificationText.getStringWidth(text) / 4, 4.0f, type));
    }
    
    public void removeNotification(final String text) {
        this.notifications.stream().filter(notification -> notification.getText() == text).forEach(notification -> this.notifications.remove(notification));
    }
    
    public void drawNotifications() {
        float posY = 0.0f;
        for (final Notification notification : this.notifications) {
            int color = Integer.MIN_VALUE;
            if (notification.getType() == NotificationType.HINT) {
                color = -2228480;
                if (!notification.getTimeHelper().hasReached(25L)) {
                    Minecraft.getMinecraft().thePlayer.playSound("note.pling", 0.5f, 0.7f);
                }
            }
            else if (notification.getType() == NotificationType.INFO) {
                color = -1;
                if (!notification.getTimeHelper().hasReached(25L)) {
                    Minecraft.getMinecraft().thePlayer.playSound("note.pling", 0.5f, 1.0f);
                }
            }
            else if (notification.getType() == NotificationType.WARNING) {
                color = -65536;
                if (!notification.getTimeHelper().hasReached(25L)) {
                    Minecraft.getMinecraft().thePlayer.playSound("note.pling", 0.5f, 0.4f);
                }
            }
            int fade = 0;
            boolean beginFade = true;
            if (beginFade) {
                for (int i = 0; i < 1000; ++i) {
                    if (notification.getTimeHelper().hasReached(i * 4) && fade != 250) {
                        ++fade;
                    }
                }
                beginFade = false;
            }
            boolean endFade = true;
            if (notification.getTimeHelper().hasReached(3000L) && endFade) {
                for (int j = 0; j < 250; ++j) {
                    if (notification.getTimeHelper().hasReached(3000 + j * 4) && fade > 1) {
                        --fade;
                    }
                }
                endFade = false;
            }
            this.notificationText.drawString(StringUtils.stripControlCodes(notification.getText()), (int)notification.getX() - 2 + 1, (int)notification.getY() + (int)posY - 2 + 1, -16777216 + (fade << 24));
            this.notificationText.drawString(notification.getText(), (int)notification.getX() - 2, (int)notification.getY() + (int)posY - 2, color + (fade << 24));
            if (notification.getTimeHelper().hasReached(4000L)) {
                this.notifications.remove(notification);
            }
            posY += 14.0f;
        }
    }
}
