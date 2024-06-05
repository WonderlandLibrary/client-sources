package me.rich.module.render;

import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventTransformSideFirstPerson;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.module.combat.KillAura;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumHandSide;

public class BlockHitAnimation extends Feature {
	public static boolean blocking;

	public BlockHitAnimation() {
		super("BlockHitAnimation", 0, Category.MISC);
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		this.blocking = Main.moduleManager.getModule(KillAura.class).isToggled() && KillAura.target != null;
	}

	@EventTarget
	public void onSidePerson(EventTransformSideFirstPerson event) {
		if (this.blocking) {
			if (event.getEnumHandSide() == EnumHandSide.RIGHT) {
				GlStateManager.translate(0.29, 0.10, -0.31);
			}
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