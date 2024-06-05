/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package digital.rbq.notification;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;
import digital.rbq.core.Autumn;
import digital.rbq.module.impl.visuals.hud.HUDMod;
import digital.rbq.notification.Notification;
import digital.rbq.notification.NotificationType;
import digital.rbq.utils.render.AnimationUtils;
import digital.rbq.utils.render.RenderUtils;
import digital.rbq.utils.render.Translate;

public final class NotificationPublisher {
    private static final List<Notification> NOTIFICATIONS = new CopyOnWriteArrayList<Notification>();

    public static void publish(ScaledResolution sr) {
        if (NOTIFICATIONS.isEmpty()) {
            return;
        }
        int srScaledHeight = sr.getScaledHeight();
        int scaledWidth = sr.getScaledWidth();
        int y = srScaledHeight - 30;
        Minecraft mc = Minecraft.getMinecraft();
        HUDMod hud = Autumn.MANAGER_REGISTRY.moduleManager.getModuleOrNull(HUDMod.class);
        FontRenderer fr = hud.defaultFont.getValue() != false ? mc.fontRendererObj : mc.fontRenderer;
        for (Notification notification : NOTIFICATIONS) {
            Translate translate = notification.getTranslate();
            int width = notification.getWidth();
            if (!notification.getTimer().elapsed(notification.getTime())) {
                notification.scissorBoxWidth = AnimationUtils.animate(width, notification.scissorBoxWidth, 0.1);
                translate.interpolate(scaledWidth - width, y, 0.15);
            } else {
                notification.scissorBoxWidth = AnimationUtils.animate(0.0, notification.scissorBoxWidth, 0.1);
                if (notification.scissorBoxWidth < 1.0) {
                    NOTIFICATIONS.remove(notification);
                }
                y += 30;
            }
            float translateX = (float)translate.getX();
            float translateY = (float)translate.getY();
            GL11.glPushMatrix();
            GL11.glEnable((int)3089);
            RenderUtils.prepareScissorBox((float)((double)scaledWidth - notification.scissorBoxWidth), translateY, scaledWidth, translateY + 30.0f);
            Gui.drawRect(translateX, translateY, scaledWidth, translateY + 30.0f, -1879048192);
            Gui.drawRect(translateX, translateY + 30.0f - 2.0f, (double)translateX + (double)width * ((double)((long)notification.getTime() - notification.getTimer().getElapsedTime()) / (double)notification.getTime()), translateY + 30.0f, notification.getType().getColor());
            fr.drawStringWithShadow(notification.getTitle(), translateX + 4.0f, translateY + 4.0f, -1);
            fr.drawStringWithShadow(notification.getContent(), translateX + 4.0f, translateY + 17.0f, -1);
            GL11.glDisable((int)3089);
            GL11.glPopMatrix();
            y -= 30;
        }
    }

    public static void queue(String title, String content, NotificationType type) {
        Minecraft mc = Minecraft.getMinecraft();
        HUDMod hud = Autumn.MANAGER_REGISTRY.moduleManager.getModuleOrNull(HUDMod.class);
        FontRenderer fr = hud.defaultFont.getValue() != false ? mc.fontRendererObj : mc.fontRenderer;
        NOTIFICATIONS.add(new Notification(title, content, type, fr));
    }
}

