package me.rich.module.movement;

import clickgui.setting.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;

public class Timer extends Feature {
    public Timer() {
        super("Timer",0, Category.MOVEMENT);
        Main.instance.settingsManager.rSetting(new Setting("Timer", this, 1.0, 0.1, 10.0, false));
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (this.isToggled()) {
           mc.timer.timerSpeed = Main.instance.settingsManager.getSettingByName("Timer").getValFloat();
            this.setModuleName("Timer §7[" + Main.instance.settingsManager.getSettingByName("Timer").getValFloat() + "]");
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        NotificationPublisher.queue(getName(), "was disabled.", NotificationType.INFO);
        mc.timer.timerSpeed = 1.0f;
    }
    @Override
    public void onEnable() {
        super.onEnable();
        NotificationPublisher.queue(getName(), "was enabled.", NotificationType.INFO);
    }
}