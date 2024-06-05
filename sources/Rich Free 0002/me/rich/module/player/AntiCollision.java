package me.rich.module.player;

import clickgui.setting.Setting;
import me.rich.Main;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;

public class AntiCollision extends Feature {

    public AntiCollision() {
        super("NoPush", 0, Category.PLAYER);
        Main.settingsManager.rSetting(new Setting("Players", this, true));
        Main.settingsManager.rSetting(new Setting("Blocks", this, true));
        Main.settingsManager.rSetting(new Setting("Water", this, true));

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

