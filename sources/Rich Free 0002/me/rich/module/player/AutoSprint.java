package me.rich.module.player;

import org.lwjgl.input.Keyboard;

import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;

public class AutoSprint extends Feature {

	public AutoSprint() {
		super("AutoSprint", 0, Category.PLAYER);
	}

	@EventTarget
	public void lox(EventUpdate event) {
		if(isToggled()) {
		if(mc.gameSettings.keyBindForward.isKeyDown()) {
			mc.player.setSprinting(true);
		}
		}
	}
	
	public void onEnables() {
    	NotificationPublisher.queue(this.getName(), "was enabled.", NotificationType.INFO);
	}
	
	public void onDisable() {
		NotificationPublisher.queue(this.getName(), "was disabled.", NotificationType.INFO);
		mc.player.setSprinting(false);
	}
}
