package me.rich.module.render;

import org.lwjgl.input.Keyboard;

import clickgui.setting.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;

public class FullBright extends Feature {
	private float oldBrightness;

	public FullBright() {
		super("FullBright", Keyboard.KEY_NONE, Category.RENDER);
		Main.settingsManager.rSetting(new Setting("Brightness", this, 0, -10, 10, true));
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		mc.gameSettings.gammaSetting = Main.settingsManager.getSettingByName("Brightness").getValFloat();
	}

	@Override
	public void onEnable() {
		NotificationPublisher.queue(this.getName(), "was enabled.", NotificationType.INFO);
		super.onEnable();
		oldBrightness = mc.gameSettings.gammaSetting;
	}

	public void onDisable() {
		mc.gameSettings.gammaSetting = oldBrightness;
		NotificationPublisher.queue(this.getName(), "was disabled.", NotificationType.INFO);
		super.onDisable();
	}
}