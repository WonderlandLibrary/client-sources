package me.rich.module.movement;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import clickgui.setting.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventStep;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;
import net.minecraft.network.play.client.CPacketPlayer;

public class Step extends Feature {
	boolean resetTimer;

	public Step() {
		super("Step", Keyboard.KEY_NONE, Category.MOVEMENT);
		ArrayList<String> mode = new ArrayList<>();
		mode.add("Vanilla");
		mode.add("Matrix");
		Main.settingsManager.rSetting(new Setting("Step Mode", this, "Vanilla", mode));
		Main.settingsManager.rSetting(new Setting("Delay", this, 0.1, 0.0, 1.0, false));
	}

	@EventTarget
	public void onStepConfirm(EventStep step) {
		if(isToggled()) {
		String moded = Main.settingsManager.getSettingByName("Step Mode").getValString();
		this.setModuleName("Step §7[" + moded + "]");
		if(moded.equalsIgnoreCase("Vanilla")) {
			mc.player.stepHeight = 1;
		}
		
		if(moded.equalsIgnoreCase("Matrix")) {
		float timer = 0.37f;
		if (this.resetTimer) {
			this.resetTimer = !this.resetTimer;
			mc.timer.timerSpeed = 1.0f;
		}
		if (step.isPre()) {
			if (mc.player.isCollidedVertically && !mc.gameSettings.keyBindJump.isPressed()) {
				step.setStepHeight(1);
				step.setActive(true);
			}
		} else {
			boolean canStep;
			double rheight = mc.player.getEntityBoundingBox().minY - mc.player.posY;
			boolean bl = canStep = rheight >= 0.625;
			if (canStep) {
				timerHelper.reset();
				timerHelper.reset();
			}
			if (canStep) {
				this.matrixStep(rheight);
				this.resetTimer = true;
				mc.timer.timerSpeed = rheight < 2.0 ? 0.6f : 0.3f;
			}
		}
		}
		}
	}

	void matrixStep(double height) {
		double posX = mc.player.posX;
		double posZ = mc.player.posZ;
		double y = mc.player.posY;
		double first = 0.42;
		double second = 0.75;
		mc.player.connection.sendPacket(new CPacketPlayer.Position(posX, y + first, posZ, false));
	}

	@Override
	public void onEnable() {
		super.onEnable();
		NotificationPublisher.queue(this.getName(), "was enabled.", NotificationType.INFO);
	}

	public void onDisable() {
		
		mc.player.stepHeight = 0.625f;
		mc.timer.timerSpeed = 1.0f;
		NotificationPublisher.queue(this.getName(), "was disabled.", NotificationType.INFO);
		super.onDisable();
	}
}
