package me.rich.module.movement;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import clickgui.setting.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;
import net.minecraft.block.Block;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.BlockPos;

public class Jesus extends Feature {
	public Jesus() {
		super("Jesus", Keyboard.KEY_NONE, Category.MOVEMENT);
		ArrayList<String> jesus = new ArrayList<>();
		jesus.add("Default");
		jesus.add("MatrixZoom");
		Main.instance.settingsManager.rSetting(new Setting("Jesus Mode", this, "Default", jesus));
		Main.settingsManager.rSetting(new Setting("UseTimer", this, false));
		Main.settingsManager.rSetting(new Setting("MotionVal", this, 1.2, 0.2, 3, false));
		Main.settingsManager.rSetting(new Setting("ZoomMotionVal", this, 1, 1, 15, false));

	}

	@EventTarget
	public void Update(EventUpdate event) {
		String jesus = Main.settingsManager.getSettingByName("Jesus Mode").getValString();
		if (jesus.equalsIgnoreCase("Default")) {
			double ff = Main.settingsManager.getSettingByName("MotionVal").getValFloat();
			if ((mc.player.isInWater() || mc.player.isInLava())) {
				mc.player.motionY = 0.45;
				setSpeed(ff);
				if (Main.settingsManager.getSettingByName("UseTimer").getValBoolean()) {
					mc.timer.timerSpeed = 2f;
				}
			} else {
				mc.timer.timerSpeed = 1f;
			}
		}

		if (jesus.equalsIgnoreCase("MatrixZoom")) {
			BlockPos blockPos = new BlockPos(mc.player.posX, mc.player.posY - 0.1, mc.player.posZ);
			Block block = mc.world.getBlockState(blockPos).getBlock();
			if (Block.getIdFromBlock(block) == 9) {
				if (!mc.player.onGround) {
					setSpeed(Main.settingsManager.getSettingByName("ZoomMotionVal").getValFloat());

					if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY + 0.0000001, mc.player.posZ))
							.getBlock() == Block.getBlockById(9)) {
						mc.player.fallDistance = 0.0f;
						mc.player.motionX = 0.0;
						mc.player.motionY = 0.06f;
						mc.player.jumpMovementFactor = 0.01f;
						mc.player.motionZ = 0.0;
					}
				}
			}
		}

		
		this.setModuleName("Jesus §7Mode: " + Main.settingsManager.getSettingByName("Jesus Mode").getValString() + "; [xz=" + Main.settingsManager.getSettingByName("MotionVal").getValFloat() + ", y=" + Main.settingsManager.getSettingByName("JumpVal").getValFloat() + "]");
	}

	@Override
	public void onEnable() {
		super.onEnable();
		NotificationPublisher.queue(this.getName(), "was enabled.", NotificationType.INFO);
	}

	public void onDisable() {
		mc.timer.timerSpeed = 1f;
		NotificationPublisher.queue(this.getName(), "was disabled.", NotificationType.INFO);
		mc.player.capabilities.isFlying = false;
		super.onDisable();

	}

	public static void setSpeed(double speed) {
		double forward = MovementInput.moveForward;
		double strafe = MovementInput.moveStrafe;
		float yaw = mc.player.rotationYaw;
		if (forward == 0.0 && strafe == 0.0) {
			mc.player.motionX = 0.0;
			mc.player.motionZ = 0.0;
		} else {
			if (forward != 0.0) {
				if (strafe > 0.0) {
					yaw += (float) (forward > 0.0 ? -45 : 45);
				} else if (strafe < 0.0) {
					yaw += (float) (forward > 0.0 ? 45 : -45);
				}
				strafe = 0.0;
				if (forward > 0.0) {
					forward = 1.0;
				} else if (forward < 0.0) {
					forward = -1.0;
				}
			}
			mc.player.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0))
					+ strafe * speed * Math.sin(Math.toRadians(yaw + 90.0));
			mc.player.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0))
					- strafe * speed * Math.cos(Math.toRadians(yaw + 90.0));
		}
	}
}
