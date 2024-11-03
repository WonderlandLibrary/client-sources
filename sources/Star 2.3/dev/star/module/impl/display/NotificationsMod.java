package dev.star.module.impl.display;

import dev.star.module.Category;
import dev.star.module.Module;
import dev.star.module.settings.impl.BooleanSetting;
import dev.star.module.settings.impl.ModeSetting;
import dev.star.module.settings.impl.NumberSetting;
import dev.star.gui.notifications.Notification;
import dev.star.gui.notifications.NotificationManager;
import dev.star.utils.animations.Animation;
import dev.star.utils.animations.Direction;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;

public class NotificationsMod extends Module {
    private final NumberSetting time = new NumberSetting("Time on Screen", 2, 10, 1, .5);
    public static final ModeSetting mode = new ModeSetting("Mode", "Default", "Default");
    public static final BooleanSetting onlyTitle = new BooleanSetting("Only Title", false);
    public static final BooleanSetting toggleNotifications = new BooleanSetting("Show Toggle", true);

    public NotificationsMod() {
        super("Notifications", Category.DISPLAY, "Allows you to customize the client notifications");
        onlyTitle.addParent(mode, modeSetting -> modeSetting.is("Default"));
        this.addSettings(time, mode, onlyTitle, toggleNotifications);
        if (!enabled) this.toggleSilent();
    }

    public void render() {
        float yOffset = 0;
        int notificationHeight = 0;
        int notificationWidth;
        int actualOffset = 0;
        ScaledResolution sr = new ScaledResolution(mc);

        NotificationManager.setToggleTime(time.getValue().floatValue());

        for (Notification notification : NotificationManager.getNotifications()) {
            Animation animation = notification.getAnimation();
            animation.setDirection(notification.getTimerUtil().hasTimeElapsed((long) notification.getTime()) ? Direction.BACKWARDS : Direction.FORWARDS);

            if (animation.finished(Direction.BACKWARDS)) {
                NotificationManager.getNotifications().remove(notification);
                continue;
            }

            float x, y;

            switch (mode.getMode()) {
                case "Default":
                    animation.setDuration(250);
                    actualOffset = 8;
                    if (onlyTitle.isEnabled()) {
                        notificationHeight = 19;
                        notificationWidth = (int) BoldFont22.getStringWidth(notification.getTitle()) + 35;
                    } else {
                        notificationHeight = 28;
                        notificationWidth = (int) Math.max(BoldFont22.getStringWidth(notification.getTitle()), Font18.getStringWidth(notification.getDescription())) + 35;
                    }

                    x = sr.getScaledWidth() - (notificationWidth + 5) * animation.getOutput().floatValue();
                    y = sr.getScaledHeight() - (yOffset + 18 + HUDMod.offsetValue + notificationHeight * animation.getOutput().floatValue() + (15 * GuiChat.openingAnimation.getOutput().floatValue()));

                    notification.drawDefault(x, y, notificationWidth, notificationHeight, animation.getOutput().floatValue(), onlyTitle.isEnabled());
                    break;
            }
            yOffset += (notificationHeight + actualOffset) * animation.getOutput().floatValue();
        }
    }

    public void renderEffects() {
        float yOffset = 0;
        int notificationHeight = 0;
        int notificationWidth;
        int actualOffset = 0;
        ScaledResolution sr = new ScaledResolution(mc);


        for (Notification notification : NotificationManager.getNotifications()) {
            Animation animation = notification.getAnimation();
            animation.setDirection(notification.getTimerUtil().hasTimeElapsed((long) notification.getTime()) ? Direction.BACKWARDS : Direction.FORWARDS);

            if (animation.finished(Direction.BACKWARDS)) {
                NotificationManager.getNotifications().remove(notification);
                continue;
            }

            float x, y;

            switch (mode.getMode()) {
                case "Default":
                    actualOffset = 8;
                    if (onlyTitle.isEnabled()) {
                        notificationHeight = 19;
                        notificationWidth = (int) BoldFont22.getStringWidth(notification.getTitle()) + 35;
                    } else {
                        notificationHeight = 28;
                        notificationWidth = (int) Math.max(BoldFont22.getStringWidth(notification.getTitle()), Font18.getStringWidth(notification.getDescription())) + 35;
                    }

                    x = sr.getScaledWidth() - (notificationWidth + 5) * animation.getOutput().floatValue();
                    y = sr.getScaledHeight() - (yOffset + 18 + HUDMod.offsetValue + notificationHeight * animation.getOutput().floatValue() + (15 * GuiChat.openingAnimation.getOutput().floatValue()));

                    notification.blurDefault(x, y, notificationWidth, notificationHeight, animation.getOutput().floatValue());
                    break;
            }
            yOffset += (notificationHeight + actualOffset) * animation.getOutput().floatValue();
        }
    }
}
