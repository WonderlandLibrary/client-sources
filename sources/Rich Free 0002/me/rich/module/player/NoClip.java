package me.rich.module.player;

import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;
import net.minecraft.entity.Entity;

public class NoClip extends Feature {

	public NoClip() {
		super("NoClip", 0, Category.PLAYER);
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		    mc.player.noClip = true;
		    mc.player.motionY = 0.0001;
			if(mc.gameSettings.keyBindJump.isKeyDown()) {
				mc.player.motionY = 0.4;
			}
			if(mc.gameSettings.keyBindSneak.isKeyDown()) {
				mc.player.motionY = -0.4;
			}
		}
	

	public static boolean isNoClip(Entity entity) {
		if (Main.moduleManager.getModule(NoClip.class).isToggled() && mc.player != null
				&& (mc.player.ridingEntity == null || entity == mc.player.ridingEntity))
			return true;
		return entity.noClip;

	}

	public void onDisable() {
		mc.player.noClip = false;
        NotificationPublisher.queue(getName(), "was disabled.", NotificationType.INFO);
		super.onDisable();
	}
}
