package me.rich.module.movement;

import clickgui.setting.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;


public class NoSlowDown extends Feature {
	
	public NoSlowDown() {
		super("NoSlowdown", 0, Category.PLAYER);
		Main.settingsManager.rSetting(new Setting("CustomSpeed", this, 70, 10, 100, true));

	}

	@EventTarget
	public void onUpd(EventUpdate event) {
		this.setModuleName("NoSlowDown §7[" + Main.settingsManager.getSettingByName(Main.moduleManager.getModule(NoSlowDown.class), "CustomSpeed").getValInt() + "%]");
		
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