package client.metaware.api.gui.notis;

import client.metaware.Metaware;
import client.metaware.api.font.CustomFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class NotificationManager {

    private final int DEFAULT_DELAY = 2_000;

    private final List<Notification> NOTIFICATIONS = new CopyOnWriteArrayList<>();

    public void update() {
        CustomFontRenderer bigFont = Metaware.INSTANCE.getFontManager().currentFont().size(16);
        CustomFontRenderer smallFont = Metaware.INSTANCE.getFontManager().currentFont().size(14);
        for (Notification notification : NOTIFICATIONS) {
            int i = NOTIFICATIONS.indexOf(notification) * 27;

            if (notification.getY() < 50 + i) notification.setY(MathHelper.clamp_double(notification.getY() + 0.5 * (2000 / (Minecraft.getDebugFPS() == 0 ? 60 : Minecraft.getDebugFPS())),0,50 + i));
            if (notification.getY() > 50 + i) notification.setY(MathHelper.clamp_double(notification.getY() - 0.25 * (2000 / (Minecraft.getDebugFPS() == 0 ? 60 : Minecraft.getDebugFPS())),50 + i,99999));

            String seconds = notification.getDelay() / 1000 + "";
            String s = " (" + seconds.substring(0, seconds.indexOf(".") + 2) + "s) ";
            FontRenderer fontRendererObj = Minecraft.getMinecraft().fontRendererObj;
            if (notification.isExtending() && notification.getX() < Math.max(smallFont.getWidth(notification.getMessage() + s), bigFont.getWidth(notification.getCallReason())) + 36) {
                notification.setX(MathHelper.clamp_double(notification.getX() + 0.13 * (2000 / (Minecraft.getDebugFPS() == 0 ? 60 : Minecraft.getDebugFPS())),0,Math.max(smallFont.getWidth(notification.getMessage() + s), bigFont.getWidth(notification.getCallReason())) + 36));
                notification.getTimer().reset();
            } else {
                notification.setExtending(false);
            }

            if (!notification.isExtending() && notification.getTimer().delay(notification.getDelay() + 150) && notification.getX() > 0) {
                notification.setX(notification.getX() - 0.22 * (2000 / (Minecraft.getDebugFPS() == 0 ? 60 : Minecraft.getDebugFPS())));
            }

            if (notification.getX() <= 0) remove(notification);
        }
    }

    public void pop(@NotNull String message, int delay, @Nullable NotificationType type) {
        Notification notification = new Notification(message, delay, type != null ? type : NotificationType.SUCCESS);

        for (Notification prevNotification : NOTIFICATIONS) {
            if (notification.getMessage().equalsIgnoreCase(prevNotification.getMessage())) {
                prevNotification.getTimer().reset();
                return;
            }
        }

        notification.setExtending(true);
        notification.getTimer().reset();

        add(notification);
            if (notification.getNotificationType() == NotificationType.SUCCESS) {
                Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("random.pop"), 1.0F));
            } else if (notification.getNotificationType() == NotificationType.ERROR) {
                Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("random.orb"), 0.0F));
            }
    }

    public void pop(@NotNull String callReason, @NotNull String message, int delay, @Nullable NotificationType type) {
        Notification notification = new Notification(callReason, message, delay, type != null ? type : NotificationType.SUCCESS);

        for (Notification prevNotification : NOTIFICATIONS) {
            if (notification.getMessage().equalsIgnoreCase(prevNotification.getMessage())) {
                prevNotification.getTimer().reset();
                return;
            }
        }

        notification.setExtending(true);
        notification.getTimer().reset();

        add(notification);
    }

    public void pop(@NotNull String message, @Nullable NotificationType type) {
        pop(message, DEFAULT_DELAY, type);
    }

    public void pop(@NotNull String callReason, @NotNull String message, @Nullable NotificationType type) {
        pop(callReason, message, DEFAULT_DELAY, type);
    }

    public void add(@NotNull Notification notification) {
        notification.setExtending(true);
        notification.getTimer().reset();

        NOTIFICATIONS.add(notification);
    }

    public void remove(@Nullable Notification notification) {
        NOTIFICATIONS.remove(notification);
    }

    public List<Notification> getNotifications() {
        return NOTIFICATIONS;
    }

}