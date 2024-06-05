package de.tired.tired.other;

import de.tired.interfaces.IHook;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;

public enum ClientHelper implements IHook {

	INSTANCE;

	public boolean moving() {
		return MC.gameSettings.keyBindForward && MC.thePlayer.moveForward != 0;
	}

	public void doSpeedup(final double speed) {
		MC.thePlayer.motionX = -MathHelper.sin(getDirection()) * speed;
		MC.thePlayer.motionZ = MathHelper.cos(getDirection()) * speed;
	}

	public int getPotionLevel() {
		if (MC.thePlayer.isPotionActive(Potion.moveSpeed)) {
			return MC.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1;
		}

		return 0;
	}

	public static void setSpeed(double moveSpeed, double moveForward, double moveStrafe, float yaw) {
		if (moveForward == 0.0D && moveStrafe == 0.0D) {
			MC.thePlayer.motionX = (0.0D);
			MC.thePlayer.motionZ = (0.0D);
		} else {
			if (moveStrafe > 0) {
				moveStrafe = 1;
			} else if (moveStrafe < 0) {
				moveStrafe = -1;
			}
			if (moveForward != 0.0D) {
				if (moveStrafe > 0.0D) {
					yaw += (moveForward > 0.0D ? -45 : 45);
				} else if (moveStrafe < 0.0D) {
					yaw += (moveForward > 0.0D ? 45 : -45);
				}
				moveStrafe = 0.0D;
				if (moveForward > 0.0D) {
					moveForward = 1.0D;
				} else if (moveForward < 0.0D) {
					moveForward = -1.0D;
				}
			}
			MC.thePlayer.motionX = (moveForward * moveSpeed * Math.cos(Math.toRadians(yaw + 90)) + moveStrafe * moveSpeed * Math.sin(Math.toRadians(yaw + 90)));
			MC.thePlayer.motionZ = (moveForward * moveSpeed * Math.sin(Math.toRadians(yaw + 90)) - moveStrafe * moveSpeed * Math.cos(Math.toRadians(yaw + 90)));
		}
	}
	public float getDirection() {
		float yaw = MC.thePlayer.rotationYaw;
		final float forward = MC.thePlayer.moveForward;
		final float strafe = MC.thePlayer.moveStrafing;
		yaw += ((forward < 0.0f) ? 180 : 0);
		final int i = (forward < 0.0f) ? -45 : ((forward == 0.0f) ? 90 : 45);
		if (strafe < 0.0f) {
			yaw += i;
		}
		if (strafe > 0.0f) {
			yaw -= i;
		}
		return yaw * 0.017453292f; //-- look vec value.
	}

}
