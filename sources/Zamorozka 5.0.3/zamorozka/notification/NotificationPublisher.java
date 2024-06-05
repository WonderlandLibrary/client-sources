package zamorozka.notification;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import zamorozka.ui.AnimationUtil;
import zamorozka.ui.MCUtil;
import zamorozka.ui.RenderingUtils;
import zamorozka.ui.font.CFontRenderer;
import zamorozka.ui.font.Fonts;

import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class NotificationPublisher implements MCUtil {
    private static final List<Notification> NOTIFICATIONS = new CopyOnWriteArrayList<>();

    public static void publish() {
        if (NOTIFICATIONS.isEmpty())
            return;
        ScaledResolution sr = new ScaledResolution(mc);
        int srScaledHeight = sr.getScaledHeight();
        int scaledWidth = sr.getScaledWidth();
        int y = srScaledHeight - 53;
        CFontRenderer title = Fonts.Tahoma20;
        CFontRenderer content = Fonts.Tahoma18;
        for (Notification notification : NOTIFICATIONS) {
            Translate2 translate = notification.getTranslate();
            int width = notification.getWidth();
            if (!notification.getTimer().elapsed(notification.getTime())) {
                notification.scissorBoxWidth = AnimationUtil.animate(width, notification.scissorBoxWidth, 0.25D * mc.frameTime / 5);
                translate.interpolate((scaledWidth - width), y, 0.03D);
            } else {
                notification.scissorBoxWidth = AnimationUtil.animate(0.0, notification.scissorBoxWidth, 0.25D * mc.frameTime / 7);
                if (notification.getWidth() > scaledWidth) {
                    NOTIFICATIONS.remove(notification);
                }
                y += 35;
            }
            float translateX = (float) translate.getX();
            float translateY = (float) translate.getY();
            GL11.glPushMatrix();
            GL11.glEnable(3089);
            RenderingUtils.prepareScissorBox((float) (scaledWidth - notification.scissorBoxWidth), translateY, scaledWidth, translateY + 30.0F);
            RenderingUtils.drawRect(translateX, translateY, scaledWidth, (translateY + 28.0F), new Color(10, 10, 10, 180).getRGB());
            RenderingUtils.drawRect(translateX, (translateY + 28.0F - 2.0F), scaledWidth, (translateY + 28.0F), new Color(10, 10, 10, 180).getRGB());
            RenderingUtils.drawRect(translateX, (translateY + 28.0F - 2.0F), translateX + width * (notification.getTime() - notification.getTimer().getElapsedTime()) / notification.getTime(), (translateY + 28.0F), notification.getType().getColor());

            title.drawStringWithShadow(notification.getTitle(), translateX + 5.0F, translateY + 4.0F, -1);
            content.drawStringWithShadow(notification.getContent(), translateX + 5.0F, translateY + 16.0F, -1);
            GL11.glDisable(3089);
            GL11.glPopMatrix();
            y -= 35;
        }
    }

    public static void queue(String title, String content, NotificationType type) {
        CFontRenderer fr = Fonts.Tahoma16;
        NOTIFICATIONS.add(new Notification(title, content, type, fr));
    }
}