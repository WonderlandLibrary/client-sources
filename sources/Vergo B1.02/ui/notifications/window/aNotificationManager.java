package xyz.vergoclient.ui.notifications.window;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import xyz.vergoclient.ui.notifications.ingame.Notification;
import xyz.vergoclient.ui.notifications.ingame.NotificationType;
import xyz.vergoclient.util.main.ColorUtils;
import xyz.vergoclient.util.Gl.BloomUtil;
import xyz.vergoclient.util.Gl.BlurUtil;
import xyz.vergoclient.util.main.RenderUtils;
import xyz.vergoclient.util.animations.Animation;
import xyz.vergoclient.util.animations.Direction;
import xyz.vergoclient.util.animations.impl.DecelerateAnimation;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DONT_CARE;

public class aNotificationManager {
    private final float spacing = 10;
    private final float widthSpacing = 25;
    public static final CopyOnWriteArrayList<Notification> notifications = new CopyOnWriteArrayList<>();
    Animation downAnimation = null;

    public void drawNotifications(ScaledResolution sr) {
        int count = 0;

        for (Notification notification : notifications) {
            if (notification.timerUtil.hasTimeElapsed((long) notification.getMaxTime(), false)) {
                if (notification.getAnimation() != null) {
                    if (notification.getAnimation().isDone()) {
                        notifications.remove(notification);
                        downAnimation = new DecelerateAnimation(200, 1, Direction.FORWARDS);
                        continue;
                    }
                } else vanish(notification);
            } else {
                if (notification.getAnimation() != null) {
                    if (notification.getAnimation().isDone()) notification.stopAnimation();
                }
            }

            float notifWidth = notification.getWidth() + widthSpacing;
            float notifX = sr.getScaledWidth() - (notifWidth + 5);
            if (count == 0) notification.notificationY = sr.getScaledHeight();
            notification.notificationY = notifications.get(Math.max(count - 1, 0)).notificationY - spacing - notification.getHeight();

            if (notification.isAnimating()) notifX += notifWidth * notification.getAnimation().getOutput();

            if (downAnimation != null) {
                if (downAnimation.isDone()) {
                    downAnimation = null;
                    return;
                }

                float newY = sr.getScaledHeight() - (spacing + notification.getHeight()) * (count + 2);
                notification.notificationY = (float) (newY + ((notification.getHeight() + spacing) * downAnimation.getOutput()));
            }

            notificationDraw(notifX, notification.notificationY, notifWidth, notification.getHeight(), notification);

            count++;
        }
    }

    public void notificationDraw(float x, float y, float width, float height, Notification notification) {
        int color = -1;
        int color2 = -1;
        String iconText = "";
        float yOffset = 8;
        float xOffset = 5;
        switch (notification.getNotificationType()) {
            case SUCCESS:
                color = new Color(101, 255, 144).getRGB();
                break;
            case WARNING:
                color = new Color(255, 66, 30).getRGB();
                break;
            case DISABLE:
                color = new Color(255, 77, 77).getRGB();
                yOffset = 9;
                break;
            case INFO:
                color = new Color(255, 255, 255).getRGB();
                xOffset = 7;
                break;
        }

        Color baseColor = new Color(20, 20, 20, 110);
        Color colorr = interpolateColorC(baseColor, new Color(applyOpacity(color, .4f)), 0.6f);

        // Enables Alias
        glEnable(GL_LINE_SMOOTH);
        glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);

        BlurUtil.blurAreaRounded(x, y, width, height, 4);
        RenderUtils.drawAlphaRoundedRect(x, y, width, height, 4, colorr);
        BloomUtil.drawAndBloom(() -> ColorUtils.drawRoundedRect(x, y - 0.5f, width, height, 4f, colorr.getRGB()));

        // Disabled Alias
        glDisable(GL_LINE_SMOOTH);
        glHint(GL_LINE_SMOOTH_HINT, GL_DONT_CARE);

        GlStateManager.color(1, 1, 1, 1);
        notification.titleFont.drawString(notification.getTitle(), x + 5, y + 6, -1);

        GlStateManager.color(1, 1, 1, 1);
        notification.descriptionFont.drawString(notification.getDescription(), x + 6, y + 18, -1);
    }

    public void blurNotifs(ScaledResolution sr) {
        int count = 0;
        for (Notification notification : notifications) {
            float notifWidth = notification.getWidth() + widthSpacing;
            float notifX = sr.getScaledWidth() - (notifWidth + 5);
            if (count == 0) notification.notificationY = sr.getScaledHeight(); //- Watermark.y;
            notification.notificationY = notifications.get(Math.max(count - 1, 0)).notificationY - spacing - notification.getHeight();

            if (notification.isAnimating()) notifX += notifWidth * notification.getAnimation().getOutput();

            if (downAnimation != null) {
                if (downAnimation.isDone()) {
                    downAnimation = null;
                    return;
                }
                float newY = sr.getScaledHeight() - (/*Watermark.y +*/ ((spacing + notification.getHeight()) * (count + 2)));
                notification.notificationY = (float) (newY + ((notification.getHeight() + spacing) * downAnimation.getOutput()));
            }

            Color baseColor = new Color(50, 50, 44, 80);
            Color colorr = interpolateColorC(baseColor, new Color(applyOpacity(-1, .3f)), 0.5f);

            RenderUtils.drawRoundedRect(notifX, notification.notificationY, notifWidth, notification.getHeight(), 4.75f, colorr);
            count++;
        }
    }



    public static void post(NotificationType type, String title, String description) {
        post(new Notification(type, title, description));
    }

    public static void post(NotificationType type, String title, String description, float time) {
        post(new Notification(type, title, description, time));
    }

    private static void post(Notification notification) {
        notifications.add(notification);
        notification.startAnimation(new DecelerateAnimation(200, 1, Direction.BACKWARDS));
    }

    public static void vanish(Notification notification) {
        notification.startAnimation(new DecelerateAnimation(200, 1, Direction.FORWARDS));
    }

    public static int interpolateInt(int oldValue, int newValue, double interpolationValue){
        return interpolate(oldValue, newValue, (float) interpolationValue).intValue();
    }

    public static Double interpolate(double oldValue, double newValue, double interpolationValue){
        return (oldValue + (newValue - oldValue) * interpolationValue);
    }

    public static Color interpolateColorC(Color color1, Color color2, float amount) {
        amount = Math.min(1, Math.max(0, amount));
        return new Color(interpolateInt(color1.getRed(), color2.getRed(), amount),
                interpolateInt(color1.getGreen(), color2.getGreen(), amount),
                interpolateInt(color1.getBlue(), color2.getBlue(), amount),
                interpolateInt(color1.getAlpha(), color2.getAlpha(), amount));
    }

    public static int applyOpacity(int color, float opacity) {
        Color old = new Color(color);
        return applyOpacity(old, opacity).getRGB();
    }

    //Opacity value ranges from 0-1
    public static Color applyOpacity(Color color, float opacity) {
        opacity = Math.min(1, Math.max(0, opacity));
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (color.getAlpha() * opacity));
    }
}
