package me.rich.module.render;

import clickgui.setting.Setting;
import me.rich.Main;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;

public class Animations extends Feature {

	@Override
    public void setup() {
        Main.settingsManager.rSetting(new Setting("Size", this, 2.5F, 0.1F, 5F, false));
        Main.settingsManager.rSetting(new Setting("Distance", this, 0.5F, 0.5F, 5F, false));
        Main.settingsManager.rSetting(new Setting("Item360", this, false));


    }
	
	public Animations() {
		super("Animations", 0, Category.RENDER);
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