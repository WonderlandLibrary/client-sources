package dev.client.tenacity.module.impl.render;

import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.settings.impl.BooleanSetting;
import dev.settings.impl.NumberSetting;

public class NotificationsMod extends Module {

    public static final NumberSetting colorInterpolation = new NumberSetting("Color Value", .5, 1, 0, .05);
    public static final BooleanSetting toggleNotifications = new BooleanSetting("Toggle", true);

    public NotificationsMod() {
        super("Notifications", Category.RENDER, "Allows you to customize the client notifications");
        this.addSettings(colorInterpolation, toggleNotifications);
    }

}
