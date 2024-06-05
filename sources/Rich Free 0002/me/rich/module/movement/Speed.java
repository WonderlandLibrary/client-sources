package me.rich.module.movement;

import java.util.ArrayList;

import com.mojang.realmsclient.gui.ChatFormatting;

import clickgui.setting.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventPreMotionUpdate;
import me.rich.helpers.world.TimerHelper;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;
import net.minecraft.client.Minecraft;

public class Speed extends Feature {

	private int counter;
	TimerHelper timerUtils = new TimerHelper();

	public Speed() {
		super("Speed", 0, Category.MOVEMENT);
		ArrayList<String> speed = new ArrayList<String>();
		speed.add("Matrix");
		speed.add("MatrixDisabler");
		speed.add("Sunrise");
		Main.instance.settingsManager.rSetting(new Setting("Speed Mode", this, "Matrix", speed));
	}

	@EventTarget
	public void onPreMotion(EventPreMotionUpdate event) {
		if (this.isToggled()) {
			String mode = Main.instance.settingsManager.getSettingByName("Speed Mode").getValString();
			if (mode.equalsIgnoreCase("Matrix")) {
				if (!mc.player.isOnLadder() && !mc.player.isInLava() && !mc.player.isInWater()) {
					mc.gameSettings.keyBindJump.pressed = false;
					if (mc.player.onGround) {
						mc.player.jump();
						this.counter = 5;
					} else if (this.counter < 5) {
						if (this.counter == 1) {
							mc.timer.timerSpeed = 1.2f;
						}
						++this.counter;
					} else {
						this.counter = 0;
						mc.player.speedInAir = 0.0208f;
						mc.player.jumpMovementFactor = 0.027f;
					}
				}
				mc.timer.timerSpeed = mc.player.motionY == 0.0030162615090425808 ? 1.6f : 1f;
				if (mc.player.ticksExisted % 60 > 39) {
					mc.timer.timerSpeed = 20.0f;
				}
			}
			if (mode.equalsIgnoreCase("MatrixDisabler")) {
				if (mc.player.onGround)
					mc.player.jump();
				if (mc.player.motionY > 0)
					mc.player.motionY -= mc.player.motionY;
				mc.player.onGround = false;
			}

			if (mode.equalsIgnoreCase("Sunrise")) {
				if (mc.player.onGround && timerHelper.check(10)) {
					mc.player.jump();
				}

				if (!mc.player.onGround && mc.player.motionY > 0.0f && mc.player.fallDistance <= 0.1) {
					mc.player.motionY = -0.42;
					mc.player.speedInAir = 0.023f;
					mc.player.onGround = false;
					timerHelper.resetwatermark();
				}
			}

			this.setModuleName("Speed " + ChatFormatting.GRAY + "[" + mode + "]");
		}
	}

	@Override
	public void onDisable() {
		mc.timer.timerSpeed = 1.0f;
		mc.player.speedInAir = 0.02f;
		NotificationPublisher.queue(getName(), "was disabled.", NotificationType.INFO);
		super.onDisable();
	}

	@Override
	public void onEnable() {
		super.onEnable();
		NotificationPublisher.queue(getName(), "was enabled.", NotificationType.INFO);
	}
}
