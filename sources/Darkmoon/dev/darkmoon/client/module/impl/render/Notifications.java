package dev.darkmoon.client.module.impl.render;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.event.render.EventRender2D;
import dev.darkmoon.client.manager.notification.Notification;
import dev.darkmoon.client.manager.notification.NotificationManager;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.module.setting.impl.BooleanSetting;
import dev.darkmoon.client.utility.render.animation.Animation;
import dev.darkmoon.client.utility.render.animation.Direction;
import dev.darkmoon.client.utility.render.font.Fonts;

import java.util.Iterator;

@ModuleAnnotation(name = "Notifications", category = Category.RENDER)
public class Notifications extends Module {
    public static BooleanSetting debugSetting = new BooleanSetting("Debug", true);
    @EventTarget
    public void onRender2D(EventRender2D event) {
        float offset = -20.0F;
        float notificationHeight = 28.0F;
        int scaledWidth = DarkMoon.getInstance().getScaleMath().calc(event.getResolution().getScaledWidth());
        int scaledHeight = DarkMoon.getInstance().getScaleMath().calc(event.getResolution().getScaledHeight());
        DarkMoon.getInstance().getScaleMath().pushScale();
        Iterator var6 = NotificationManager.getNotifications().iterator();

        while(var6.hasNext()) {
            Notification notification = (Notification)var6.next();
            Animation animation = notification.getAnimation();
            animation.setDirection(notification.getTimerHelper().hasReached((double)notification.getTime()) ? Direction.BACKWARDS : Direction.FORWARDS);
            if (animation.finished(Direction.BACKWARDS)) {
                NotificationManager.getNotifications().remove(notification);
            } else {
                float notificationWidth = (float)(Math.max(Fonts.tenacityBold16.getStringWidth(notification.getTitle()), Fonts.tenacityBold16.getStringWidth(notification.getDescription())) + 32);
                float x = (float)scaledWidth - (notificationWidth + 5.0F) * animation.getOutput();
                float y = (float)scaledHeight - (offset + 18.0F + notificationHeight);
                notification.render(x, y, notificationWidth, notificationHeight, animation.getOutput());
                offset += notificationHeight * animation.getOutput();
            }
        }

        DarkMoon.getInstance().getScaleMath().popScale();
    }
}
