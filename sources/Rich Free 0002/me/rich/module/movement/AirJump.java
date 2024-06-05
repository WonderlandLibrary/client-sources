package me.rich.module.movement;

import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;

public class AirJump extends Feature {

	public AirJump() {
		super("AirJump", 0, Category.MOVEMENT);
	}
	
    @EventTarget
    public void onUpdate(EventUpdate event){
        if(mc.gameSettings.keyBindJump.isKeyDown()){
            mc.player.jump();
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
