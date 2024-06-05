/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package cc.diablo.render.notification;

import cc.diablo.Main;
import cc.diablo.font.TTFFontRenderer;
import cc.diablo.helpers.render.ChatHelper;
import cc.diablo.helpers.render.ColorHelper;
import cc.diablo.helpers.render.RenderUtils;
import cc.diablo.render.notification.Notification;
import java.awt.Color;
import java.util.ArrayList;
import org.lwjgl.opengl.GL11;

public class NotificationManager {
    public static ArrayList<Notification> notifications = new ArrayList();

    public static void SendNotification(String text, long time) {
        ChatHelper.addChat("sent");
        notifications.add(new Notification(text, time));
    }

    public static void drawNotifications() {
        for (Notification n : notifications) {
            float width2 = (float)n.width;
            float var37 = (float)n.width;
            float var42 = (float)((double)Math.round((double)((float)n.timer.time() * width2) / (double)width2));
            float var46 = width2 / (float)n.delay;
            float var48 = var42 * var46;
            float var51 = var37 / width2;
            GL11.glPushMatrix();
            GL11.glScissor((int)((int)n.x), (int)((int)n.y), (int)((int)n.x2), (int)((int)n.y2 + 105));
            RenderUtils.drawRect(n.x, n.y, n.x2, n.y2, RenderUtils.transparency(new Color(27, 27, 27).getRGB(), 0.6f));
            RenderUtils.drawRect(n.x, n.y2 - 1.0, n.x2 - (double)(var48 * var51), n.y2, ColorHelper.getColor(150));
            TTFFontRenderer fr = Main.getInstance().getFontManager().getFont("clean 18");
            fr.drawStringWithShadow(n.text, (float)n.x2 - fr.getWidth(n.text) - 2.0f, (float)n.y2 - 14.0f, -1);
            GL11.glPopMatrix();
        }
    }
}

