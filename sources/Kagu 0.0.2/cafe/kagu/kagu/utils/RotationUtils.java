/**
 * 
 */
package cafe.kagu.kagu.utils;

import javax.vecmath.Vector3d;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

/**
 * @author lavaflowglow
 *
 */
public class RotationUtils {
	
	private static Minecraft mc = Minecraft.getMinecraft();
	
	/**
	 * Called at the start of the client
	 */
	public static void start() {
		
	}
	
	/**
	 * Calculates the angle from the players eyes needed to look at pos2
	 * @param targetPos The target position
	 * @return A float array where the first index is the yaw and the second is the pitch
	 */
	public static float[] getRotations(Vector3d targetPos) {
		EntityPlayerSP thePlayer = mc.thePlayer;
		return getRotations(new Vector3d(thePlayer.posX, thePlayer.posY + thePlayer.getEyeHeight(), thePlayer.posZ), 
				targetPos);
	}
	
	/**
	 * Calculates the angle from pos1 needed to look at pos2
	 * @param pos1 The first position
	 * @param pos2 The target position
	 * @return A float array where the first index is the yaw and the second is the pitch
	 */
	public static float[] getRotations(Vector3d pos1, Vector3d pos2) {
		// soh cah toa
		double distX = pos2.getX() - pos1.getX();
		double distY = pos2.getY() - pos1.getY();
		double distZ = pos2.getZ() - pos1.getZ();
		double dist = Math.sqrt(distX * distX + distZ * distZ);
		double yaw = Math.toDegrees(Math.atan2(distZ, distX));
		double pitch = Math.toDegrees(Math.atan2(distY, dist));
		return new float[] {(float)yaw - 90, (float)-pitch};
	}
	
	/**
	 * Calculates the angle from pos1 needed to look at pos2
	 * @param pos1 The first position
	 * @param pos2 The target position
	 * @return A float array where the first index is the yaw and the second is the pitch
	 */
	public static float[] getRotations(double[] pos1, double[] pos2) {
		return getRotations(new Vector3d(pos1), new Vector3d(pos2));
	}
	
	/**
	 * @return The strafe yaw of the player, takes the players intended movements and generates a yaw with them
	 */
	public static float getStrafeYaw() {
		float yaw = 0;
		EntityPlayerSP thePlayer = mc.thePlayer;
		float moveForward = thePlayer.moveForward;
		float moveStrafing = thePlayer.moveStrafing;
		
		if (moveStrafing > 0) {
			yaw -= moveForward == 0 ? 90 : 45;
		}
		else if (moveStrafing < 0) {
			yaw += moveForward == 0 ? 90 : 45;
		}
		
		if (moveForward < 0) {
			yaw += 180;
			if (moveStrafing > 0) {
				yaw += 90;
			}
			else if (moveStrafing < 0) {
				yaw -= 90;
			}
		}
		
		yaw += thePlayer.rotationYaw;
		return yaw;
	}
	
	/**
	 * @param yaw The yaw to use for the calculations
	 * @param pitch The pitch to use for the calculations
	 * @param lastYaw The last yaw to use for the calculations
	 * @param lastPitch The last pitch to use for the calculations
	 * @return The look vec, calculated from modified mc code
	 */
	public static Vec3 getLook(float yaw, float pitch, float lastYaw, float lastPitch) {
		float partialTicks = mc.getTimer().getRenderPartialTicks();
		
		// If the partial ticks are not equal to 1 than we need to account for head interpolation
		if (partialTicks != 1.0F) {
			pitch = lastPitch + (pitch - lastPitch) * partialTicks;
			yaw = lastYaw + (yaw - lastYaw) * partialTicks;
		}
		
		// Calculate vec3
        float f = MathHelper.cos(-yaw * 0.017453292F - (float)Math.PI);
        float f1 = MathHelper.sin(-yaw * 0.017453292F - (float)Math.PI);
        float f2 = -MathHelper.cos(-pitch * 0.017453292F);
        float f3 = MathHelper.sin(-pitch * 0.017453292F);
        return new Vec3((double)(f1 * f2), (double)f3, (double)(f * f2));
	}
	
	/**
	 * Makes the two float arrays loop correctly so you don't get a weird flick effect
	 * @param currentRotations The current rotations
	 * @param targetRotations The target rotations
	 */
	public static void makeRotationValuesLoopCorrectly(float[] currentRotations, float[] targetRotations) {
		targetRotations[0] += Math.floor(currentRotations[0] / 360) * 360f;
		float normalYaw = currentRotations[0];
		float add360Yaw = currentRotations[0] + 360;
		float add360TargetYaw = targetRotations[0] + 360;
		float currentTargetYaw = targetRotations[0];
		if (Math.abs(normalYaw - add360TargetYaw) < Math.abs(normalYaw - currentTargetYaw)) {
			targetRotations[0] = add360TargetYaw;
		}
		else if (Math.abs(targetRotations[0] - add360Yaw) < Math.abs(targetRotations[0] - normalYaw)) {
			currentRotations[0] = add360Yaw;
		}
	}

	/**
	 * Creates a Vec3 using the pitch and yaw of the entities rotation.
	 * 
	 * @param yaw   The yaw
	 * @param pitch The pitch
	 * @return The vector for the rotation
	 */
	public static Vec3 getVectorForRotation(float yaw, float pitch) {
		float f = MathHelper.cos(-yaw * 0.017453292F - (float) Math.PI);
		float f1 = MathHelper.sin(-yaw * 0.017453292F - (float) Math.PI);
		float f2 = -MathHelper.cos(-pitch * 0.017453292F);
		float f3 = MathHelper.sin(-pitch * 0.017453292F);
		return new Vec3((double) (f1 * f2), (double) f3, (double) (f * f2));
	}
	
	/**
	 * Calculates the needed mouse offsets for a rotation offset
	 * @param rotOffset The rotation offset
	 * @return a float array containing the mouse deltas
	 */
	public static float[] getMouseDeltasForRotationOffset(float[] rotOffset) {
        float f = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
        float f1 = f * f * f * 8.0F;
        return new float[] {rotOffset[0] / f1, (rotOffset[1] / f1) * (mc.gameSettings.invertMouse ? -1 : 1)};
	}
	
	/**
	 * Calculates the resulting rotations for the provided mouse deltas
	 * @param mouseDeltas The mouse deltas offset
	 * @return a float array containing the resulting rotations
	 */
	public static float[] getRotationsForMouseDeltas(float[] mouseDeltas) {
        float f = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
        float f1 = f * f * f * 8.0F;
        return new float[] {mouseDeltas[0] * f1, (mouseDeltas[1] * f1) * (mc.gameSettings.invertMouse ? -1 : 1)};
	}

	public static double getMouseGCD() {
		final float sens = Minecraft.getMinecraft().gameSettings.mouseSensitivity * 0.6F + 0.2F;
		final float pow = sens * sens * sens * 8.0F;
		return (double) pow * 0.15;
	}

	/**
	 * Applies GCD fix to the rotation
	 * @param rotations The target rotations
	 * @param prevRots The last rotations
	 * @return The corrected rotations
	 */
	public static void applyGCD(final float[] rotations,
								final float[] prevRots) {
		final float yawDif = rotations[0] - prevRots[0];
		final float pitchDif = rotations[1] - prevRots[1];
		final double gcd = getMouseGCD();

		rotations[0] -= yawDif % gcd;
		rotations[1] -= pitchDif % gcd;
	}
	
}
