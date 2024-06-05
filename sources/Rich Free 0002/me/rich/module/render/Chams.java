package me.rich.module.render;

import clickgui.setting.Setting;
import me.rich.Main;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;

public class Chams extends Feature {

    public Chams() {
        super("Chams", 0, Category.RENDER);
        Main.instance.settingsManager.rSetting(new Setting("Alpha", this, 255.0, 1.0, 255.0, false));

    }

    @Override
    public void onEnable() {
        super.onEnable();
        NotificationPublisher.queue(getName(), "was enabled.", NotificationType.INFO);
    }

    public void onDisable() {
        NotificationPublisher.queue(getName(), "was disabled.", NotificationType.INFO);
        super.onDisable();
    }
}