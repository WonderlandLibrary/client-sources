package me.rich.module.movement;

import org.lwjgl.input.Keyboard;

import clickgui.setting.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.Event3D;
import me.rich.event.events.EventUpdate;
import me.rich.helpers.combat.RotationHelper;
import me.rich.helpers.movement.MovementHelper;
import me.rich.helpers.render.RenderHelper;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.module.combat.KillAura;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;

public class TargetStrafe extends Feature {
	private int direction = -1;

	public TargetStrafe() {
		super("TargetStrafe", Keyboard.KEY_Z, Category.MOVEMENT);
		Main.settingsManager.rSetting(new Setting("TSpeed", this, 3.18, 0.15, 50, false));
		Main.settingsManager.rSetting(new Setting("Radius", this, 3, 1, 6, false));
		Main.settingsManager.rSetting(new Setting("AutoJump", this, true));
		Main.settingsManager.rSetting(new Setting("KeepDistance", this, true));

	}

	public final void doStrafeAtSpeed(double d) {
		if (Main.moduleManager.getModule(KillAura.class).isToggled() && KillAura.target != null) {
			float[] arrf = RotationHelper.getRotations(KillAura.target);
			if (Main.settingsManager.getSettingByName(Main.moduleManager.getModule(TargetStrafe.class), "KeepDistance")
					.getValBoolean()) {
				if (mc.player.getDistanceToEntity(KillAura.target) <= Main.settingsManager
						.getSettingByName(Main.moduleManager.getModule(TargetStrafe.class), "Radius").getValDouble()) {
					MovementHelper.setSpeed(d - (0.20 - Main.settingsManager
							.getSettingByName(Main.moduleManager.getModule(TargetStrafe.class), "TSpeed").getValFloat()
							/ 100.0), arrf[0], direction, -1.0);
				} else {
					MovementHelper.setSpeed(d - (0.20 - Main.settingsManager
							.getSettingByName(Main.moduleManager.getModule(TargetStrafe.class), "TSpeed").getValFloat()
							/ 100.0), arrf[0], direction, 1.0);
				}
			} else {
				if (mc.player.getDistanceToEntity(KillAura.target) <= Main.settingsManager
						.getSettingByName(Main.moduleManager.getModule(TargetStrafe.class), "Radius").getValDouble()) {
					MovementHelper.setSpeed(d - (0.20 - Main.settingsManager
							.getSettingByName(Main.moduleManager.getModule(TargetStrafe.class), "TSpeed").getValFloat()
							/ 100.0), arrf[0], direction, 0);
				} else {
					MovementHelper.setSpeed(d - (0.20 - Main.settingsManager
							.getSettingByName(Main.moduleManager.getModule(TargetStrafe.class), "TSpeed").getValFloat()
							/ 100.0), arrf[0], direction, 1.0);
				}
			}
		}
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		this.setModuleName("TargetStrafe §7[" + Main.settingsManager.getSettingByName(Main.moduleManager.getModule(TargetStrafe.class), "Radius").getValFloat() + "]");
		if (Main.moduleManager.getModule(KillAura.class).isToggled()) {
			if (mc.player.isCollidedHorizontally && this.timerHelper.check(30.0f)) {
				this.invertStrafe();
			} else {
				this.timerHelper.resetwatermark();
			}
			if (Main.settingsManager.getSettingByName("AutoJump").getValBoolean()) {
				if (KillAura.target != null) {
					if (mc.player.onGround) {
						mc.player.jump();
					}
				}
			}

			if (mc.gameSettings.keyBindLeft.isPressed()) {
				direction = 1;
			}
			if (mc.gameSettings.keyBindRight.isPressed()) {
				direction = -1;
			}

			mc.player.movementInput.moveForward = 0.0f;
			double d = 0.2873;
			this.doStrafeAtSpeed(d);
		}  
	}

	private void invertStrafe() {
		direction = -direction;
	}

	@Override
	public void onEnable() {
		super.onEnable();
		NotificationPublisher.queue(this.getName(), "was enabled.", NotificationType.INFO);
	}

	public void onDisable() {
		NotificationPublisher.queue(this.getName(), "was disabled.", NotificationType.INFO);
		super.onDisable();
	}
}
