package me.rich.module.player;

import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;

public class AutoVClip extends Feature {

	public AutoVClip() {
		super("BedrockClip", 0, Category.PLAYER);
	}

	@EventTarget
	public void shlyxa(EventUpdate event) {
		if(mc.player.isInWater()) {
			mc.player.setPosition(mc.player.posX,  mc.player.posY - (mc.player.posY + 2),  mc.player.posZ);
			toggle();
		}
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
