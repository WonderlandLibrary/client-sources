package me.rich.module.combat;

import clickgui.setting.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;

public class HitBox extends Feature {
	public HitBox() {
		super("HitBox", 0, Category.COMBAT);
	}

	@Override
	public void setup() {
		Main.settingsManager.rSetting(new Setting("Size", this, 0.18, 0.1, 1, false));
	}

	@EventTarget
	public void fsdgsd(EventUpdate event) {
		this.setModuleName("HitBox §7[" + Main.settingsManager.getSettingByName(Main.moduleManager.getModule(HitBox.class), "Size").getValFloat() + "]");
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		NotificationPublisher.queue(this.getName(), "was enabled.", NotificationType.INFO);
	}

	public void onDisable() {
		NotificationPublisher.queue(this.getName(), "was disabled.", NotificationType.INFO);
		super.onDisable();
	}

}