/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.NOT;

import drunkclient.beta.IMPL.font.CFontRenderer;
import drunkclient.beta.IMPL.font.FontLoaders;
import drunkclient.beta.NOT.NotificationType;
import drunkclient.beta.UTILS.render.RenderUtil;
import java.awt.Color;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class NotificationManager {
    private static final ArrayList<Notification> notis = new ArrayList();
    private static final ArrayList<Notification> removeQueue = new ArrayList();

    public static void render() {
        Notification[] notifs;
        CFontRenderer tFontRenderer = FontLoaders.arial18;
        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        float height = 30.0f;
        float margin = 5.0f;
        float inMargin = 5.0f;
        float y = height + margin;
        Notification[] notificationArray = notifs = notis.toArray(new Notification[0]);
        int n = notificationArray.length;
        int n2 = 0;
        while (true) {
            if (n2 >= n) {
                notis.removeAll(removeQueue);
                removeQueue.clear();
                return;
            }
            Notification n3 = notificationArray[n2];
            float t = Math.max(tFontRenderer.getStringWidth(n3.title), tFontRenderer.getStringWidth(n3.desc));
            float width = 40.0f + inMargin + t;
            if (System.currentTimeMillis() - n3.timeStarted > n3.time) {
                n3.baixarMinecraftFree2021(-(width + margin), 0.0f, 0.15f);
                if (n3.yOffset <= 0.0f) {
                    removeQueue.add(n3);
                }
            } else {
                n3.yOffset = height + margin;
                n3.baxiarX(width + margin, 0.15f);
            }
            GlStateManager.pushMatrix();
            RenderUtil.blur((float)res.getScaledWidth() - n3.xOffset, (float)res.getScaledHeight() - y, (float)res.getScaledWidth() - n3.xOffset + width, (float)res.getScaledHeight() - y + height);
            GlStateManager.translate((float)res.getScaledWidth() - n3.xOffset, (float)res.getScaledHeight() - y, 0.0f);
            RenderUtil.rect(0.0f, 0.0f, width, height, new Color(10, 10, 30, 120));
            float b = ((float)n3.time - n3.getTimePassed()) / (float)n3.time % 1.0f;
            RenderUtil.rect(0.0f, 0.0f, Math.max(width * (1.0f - b), 0.1f), 1.0f, n3.type.getColor());
            tFontRenderer.drawString(n3.title, inMargin * 2.0f, height / 2.0f - (float)tFontRenderer.getHeight() / 2.0f, n3.type.getColor().getRGB());
            GlStateManager.popMatrix();
            y += n3.yOffset;
            ++n2;
        }
    }

    public static void addNoti(String title, String desc, NotificationType notiType, long time) {
        notis.add(new Notification(title, desc, notiType, time));
    }

    public static void publish(String content, NotificationType type, long ms) {
        notis.add(new Notification(content, "", type, ms));
    }

    public static void publish(Object content, NotificationType type, long ms) {
        notis.add(new Notification(String.valueOf(content), "", type, ms));
    }

    public static class Notification {
        public String title;
        public String desc;
        public long time;
        public NotificationType type;
        public long timeStarted;
        public float xOffset;
        public float yOffset;

        public Notification(String title, String desc, NotificationType notiType, long time) {
            this.title = title;
            this.desc = desc;
            this.time = time;
            this.type = notiType;
            this.timeStarted = System.currentTimeMillis();
            this.xOffset = 0.0f;
            this.yOffset = 0.0f;
        }

        public float getTimePassed() {
            return System.currentTimeMillis() - this.timeStarted;
        }

        public void baxiarX(float x, float s) {
            this.xOffset = RenderUtil.animate(x, this.xOffset, s);
        }

        public void baxiarY(float y, float s) {
            this.yOffset = RenderUtil.animate(y, this.yOffset, s);
        }

        public void baixarMinecraftFree2021(float x, float y, float s) {
            this.baxiarX(x, s);
            this.baxiarY(y, s);
        }
    }
}

