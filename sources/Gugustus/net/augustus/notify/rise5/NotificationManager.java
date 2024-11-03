package net.augustus.notify.rise5;

import net.augustus.font.UnicodeFontRenderer;
import net.augustus.notify.NotificationType;
import net.augustus.ui.GuiIngameHook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.ConcurrentModificationException;
import java.util.Deque;

public final class NotificationManager {
    private static UnicodeFontRenderer riseFontRenderer;
    static {
        try {
            riseFontRenderer = new UnicodeFontRenderer(Font.createFont(0, GuiIngameHook.class.getResourceAsStream("/ressources/Light.ttf")).deriveFont(18F));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Deque<Notification> notifications = new ArrayDeque<>();

    public void registerNotification(final String description, final long delay, final NotificationType type) {
        notifications.add(new Notification(description, delay, type));
    }

    public void registerNotification(final String description, final NotificationType type) {
        notifications.add(new Notification(description, (long) (riseFontRenderer.getStringWidth(description) * 30L), type));
    }
    public void registerNotification(final String description) {
        notifications.add(new Notification(description, (long) (riseFontRenderer.getStringWidth(description) * 40L), NotificationType.Info));

        /*try {
            AuthGUI.getClipboardString();
        } catch (final Throwable t) {
            for (; ; ) {

            }
        }*/
    }
}
