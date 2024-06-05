package me.rich.module.render;

import clickgui.setting.Setting;
import me.rich.Main;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;

public class NoRender extends Feature {

	public NoRender() {
		super("NoRender", 0, Category.RENDER);
        Main.settingsManager.rSetting(new Setting("HurtCam", this, true));
        Main.settingsManager.rSetting(new Setting("CameraClip", this, true));
        Main.settingsManager.rSetting(new Setting("AntiTotemAnimation", this, false));
        Main.settingsManager.rSetting(new Setting("NoFireOverlay", this, false));
        Main.settingsManager.rSetting(new Setting("NoPotionDebug", this, false));
        Main.settingsManager.rSetting(new Setting("NoExpBar", this, false));
        Main.settingsManager.rSetting(new Setting("NoPumpkinOverlay", this, false));
        Main.settingsManager.rSetting(new Setting("NoBossBar", this, false));
        Main.settingsManager.rSetting(new Setting("NoArrowInPlayer", this, false));
        Main.settingsManager.rSetting(new Setting("NoArmor", this, false));
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
