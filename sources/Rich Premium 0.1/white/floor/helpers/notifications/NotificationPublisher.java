package white.floor.helpers.notifications;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import white.floor.Main;
import white.floor.features.Feature;
import white.floor.features.impl.display.Notifications;
import white.floor.font.CFontRenderer;
import white.floor.font.Fonts;
import white.floor.helpers.DrawHelper;
import white.floor.helpers.render.AnimationHelper;
import white.floor.helpers.render.Translate;

import java.awt.*;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

public final class NotificationPublisher {
    private static final List<Notification> NOTIFICATIONS = new CopyOnWriteArrayList<>();

    public static void publish() {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int srScaledHeight = sr.getScaledHeight();
        int scaledWidth = sr.getScaledWidth();
        int y = srScaledHeight - 53;
        for (Notification notification : NOTIFICATIONS) {
            Translate translate = notification.getTranslate();
            int width = notification.getWidth();
            if (!notification.getTimer().elapsed(notification.getTime())) {
                notification.scissorBoxWidth = AnimationHelper.animate(notification.scissorBoxWidth, width,  3);
                translate.interpolate(scaledWidth - width, y, (float) 0.015);
            } else {
                notification.scissorBoxWidth = AnimationHelper.animate(notification.scissorBoxWidth, 0.0, 3);
                if (notification.scissorBoxWidth < 1.0) {
                    NOTIFICATIONS.remove(notification);
                }
                y += 30;
            }
            float translateX = translate.getX();
            float translateY = translate.getY();
            DrawHelper.drawRoundedRect1(translateX + 39, translateY - 1, scaledWidth - 3, translateY + 29.0f, new Color(0, 0, 0, 45).getRGB());
            DrawHelper.drawRoundedRect1(translateX + 40, translateY, scaledWidth - 4, translateY + 28.0f, DrawHelper.setAlpha(notification.getType().getColor(), 160).getRGB());
            Fonts.urw20.drawStringWithShadow(notification.getTitle(), translateX + 70.0f, translateY + 4.0f, -1);
            Fonts.urw19.drawStringWithShadow(notification.getContent(), translateX + 70.0f, translateY + 17f, -1);
            Fonts.elegant_30.drawStringWithShadow(notification.getType().getColorstr(), translateX + 47.0f, translateY + 9f, -1);
            y -= 37;
        }
    }



    public static void queue(String title, String content, NotificationType type) {
        CFontRenderer fr = Fonts.neverlose500_16;
        NOTIFICATIONS.add(new Notification(title, content, type, fr));
    }
}