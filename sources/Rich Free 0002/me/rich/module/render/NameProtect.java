package me.rich.module.render;

import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;

public class NameProtect extends Feature {

	public NameProtect() {
		super("NameProtect", 0, Category.RENDER);
	}
	
	@Override
	public void onEnable()
	{
		super.onEnable();
		NotificationPublisher.queue(this.getName(), "was enabled.", NotificationType.INFO);
	}
	public void onDisable() {
		NotificationPublisher.queue(this.getName(), "was disabled.", NotificationType.INFO);
		super.onDisable();
	}
}
