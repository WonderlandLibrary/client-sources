package me.rich.module.movement;

import clickgui.setting.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;

public class NoDelay extends Feature {

	public NoDelay() {
		super("NoDelay", 0, Category.RENDER);
		Main.settingsManager.rSetting(new Setting("NoJumpDelay", this, true));
		Main.settingsManager.rSetting(new Setting("NoRightClickDelay", this, true));


	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		if (Main.settingsManager.getSettingByName("NoJumpDelay").getValBoolean()) {
			mc.player.jumpTicks = 0;
		}
		if (Main.settingsManager.getSettingByName("NoRightClickDelay").getValBoolean()) {
			mc.rightClickDelayTimer = 0;
		}
	}
	   @Override
	    public void onDisable() {
	        super.onDisable();
	       mc.rightClickDelayTimer = 6;
	       mc.player.jumpTicks = 6;
	        NotificationPublisher.queue(getName(), "was disabled.", NotificationType.INFO);
	   }
	    @Override
	    public void onEnable() {
	        super.onEnable();
	        NotificationPublisher.queue(getName(), "was enabled.", NotificationType.INFO);
	    }

}