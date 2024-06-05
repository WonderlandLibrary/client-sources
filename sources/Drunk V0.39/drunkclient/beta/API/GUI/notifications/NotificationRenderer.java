/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.API.GUI.notifications;

import drunkclient.beta.API.GUI.notifications.Notification;
import drunkclient.beta.API.GUI.notifications.NotificationManager;
import drunkclient.beta.Client;
import drunkclient.beta.IMPL.font.FontLoaders;
import java.awt.Color;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.apache.commons.lang3.StringUtils;

public final class NotificationRenderer {
    private static final int RED = new Color(255, 80, 80).getRGB();
    private static final int GREEN = new Color(135, 227, 49).getRGB();
    private static final int ORANGE = new Color(255, 215, 100).getRGB();
    private static final int WHITE = new Color(255, 255, 255).getRGB();
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final NotificationManager notificationManager = Client.instance.getNotificationManager();
    private static int displayHeight = 0;
    private static int displayWidth = 0;

    public static void render(ScaledResolution sr) {
        Iterator manager;
        if (!notificationManager.getNOTIFICATIONS().isEmpty()) {
            notificationManager.launch();
        }
        if (!(manager = notificationManager.getNOTIFICATIONS().iterator()).hasNext()) return;
        Notification notification = (Notification)manager.next();
        double X = notification.getX();
        double Y = (double)sr.getScaledHeight() - notification.getY();
        String CallReason = notification.getCallReason() == null ? StringUtils.capitalize(notification.getType().toString()) : notification.getCallReason();
        String Message = notification.getMessage();
        String Delay = String.valueOf(((double)notification.getDelay() - notification.getCount()) / 1000.0);
        String Delay2 = "(" + Delay.substring(0, Delay.indexOf(".") + 2) + "s) ";
        Gui.drawRect((float)((double)sr.getScaledWidth() - X - 2.0), (float)(Y - 3.0), sr.getScaledWidth(), (float)(Y + 24.0), new Color(0, 0, 0, 150).getRGB());
        FontLoaders.arial22.drawString(CallReason, (float)sr.getScaledWidth() - (float)X + 25.0f, (float)Y, -1, true);
    }
}

