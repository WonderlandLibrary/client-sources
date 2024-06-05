package me.rich.module.render;

import clickgui.setting.Setting;
import me.rich.Main;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;

public class ScoreBoard extends Feature{

	public ScoreBoard() {
		super("Scoreboard", 0, Category.RENDER);
		Main.instance.settingsManager.rSetting(new Setting("PositionY", this, 5, 0, 215, false));
		Main.instance.settingsManager.rSetting(new Setting("Delete", this, false));
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
