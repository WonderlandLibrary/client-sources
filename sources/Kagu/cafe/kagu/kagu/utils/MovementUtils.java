/**
 * 
 */
package cafe.kagu.kagu.utils;

import java.util.List;

import cafe.kagu.kagu.eventBus.EventBus;
import cafe.kagu.kagu.mods.Module;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.potion.Potion;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

/**
 * @author lavaflowglow
 *
 */
public class MovementUtils {
	
	private static Minecraft mc = Minecraft.getMinecraft();
	
	/**
	 * Called at the start of the client
	 */
	public static void start() {
		
	}
	
	/**
	 * @return true if on ground, otherwise false
	 */
	public static boolean isTrueOnGround() {
		return isTrueOnGround(0.0784000015258789);
	}
	
	/**
	 * @param distance The distance from the ground to check
	 * @return true if on ground, otherwise false
	 */
	public static boolean isTrueOnGround(double distance) {
		EntityPlayerSP thePlayer = mc.thePlayer;
		return !mc.theWorld.getCollidingBoundingBoxes(thePlayer, thePlayer.getEntityBoundingBox().offset(0, -distance, 0)).isEmpty();
	}
	
	/**
	 * @param distance The distance from the ground to check
	 * @return true if can step, otherwise false
	 */
	public static boolean canStep(double distance) {
		EntityPlayerSP thePlayer = mc.thePlayer;
		float strafeYaw = RotationUtils.getStrafeYaw() + 90;
		double intendedMotion = MathHelper.clamp_double(Math.sqrt(thePlayer.moveForward * thePlayer.moveForward + thePlayer.moveStrafing * thePlayer.moveStrafing), -0.15, 0.15);
		return isTrueOnGround(distance) && isPlayerMoving()
				&& ((!mc.theWorld.getCollidingBoundingBoxes(thePlayer,
						thePlayer.getEntityBoundingBox().offset(Math.cos(Math.toRadians(strafeYaw)) * intendedMotion,
								0.1, Math.sin(Math.toRadians(strafeYaw)) * intendedMotion))
						.isEmpty()
						&& mc.theWorld
								.getCollidingBoundingBoxes(thePlayer,
										thePlayer.getEntityBoundingBox().offset(Math.cos(Math.toRadians(strafeYaw))
												* intendedMotion, distance,
												Math.sin(Math.toRadians(strafeYaw)) * intendedMotion))
								.isEmpty())
						|| (!mc.theWorld
								.getCollidingBoundingBoxes(thePlayer,
										thePlayer.getEntityBoundingBox()
												.offset(Math.cos(Math.toRadians(strafeYaw)) * intendedMotion, 0.1, 0))
								.isEmpty()
								&& mc.theWorld
										.getCollidingBoundingBoxes(thePlayer,
												thePlayer.getEntityBoundingBox().offset(
														Math.cos(Math.toRadians(strafeYaw)) * intendedMotion, distance,
														0))
										.isEmpty())
						|| (!mc.theWorld
								.getCollidingBoundingBoxes(thePlayer,
										thePlayer.getEntityBoundingBox().offset(0, 0.1,
												Math.sin(Math.toRadians(strafeYaw)) * intendedMotion))
								.isEmpty()
								&& mc.theWorld
										.getCollidingBoundingBoxes(thePlayer,
												thePlayer.getEntityBoundingBox().offset(0, distance,
														Math.sin(Math.toRadians(strafeYaw)) * intendedMotion))
										.isEmpty()));
	}
	
	/**
	 * @return true if the player is moving, otherwise false
	 */
	public static boolean isPlayerMoving() {
		EntityPlayerSP thePlayer = mc.thePlayer;
		return thePlayer.moveForward != 0 || thePlayer.moveStrafing != 0;
	}
	
	/**
	 * Sets the motion of the player, uses strafe motion
	 * @param motion The motion to set
	 */
	public static void setMotion(double motion) {
		double yaw = RotationUtils.getStrafeYaw() + 90;
		EntityPlayerSP thePlayer = mc.thePlayer;
		thePlayer.motionX = Math.cos(Math.toRadians(yaw)) * motion;
		thePlayer.motionZ = Math.sin(Math.toRadians(yaw)) * motion;
	}
	
	/**
	 * Sets the motion of the player
	 * @param motion The motion to set
	 * @param yaw The yaw to use for motion calculations
	 */
	public static void setMotion(double motion, double yaw) {
		yaw += 90;
		EntityPlayerSP thePlayer = mc.thePlayer;
		thePlayer.motionX = Math.cos(Math.toRadians(yaw)) * motion;
		thePlayer.motionZ = Math.sin(Math.toRadians(yaw)) * motion;
	}
	
	/**
	 * @return The speed of the player
	 */
	public static double getMotion() {
		EntityPlayerSP thePlayer = mc.thePlayer;
		return Math.sqrt(thePlayer.motionX * thePlayer.motionX + thePlayer.motionZ * thePlayer.motionZ);
	}
	
	/**
	 * @return true if the player is over the void, otherwise false
	 */
	public static boolean isOverVoid() {
		if (isTrueOnGround(1))
			return false;
		EntityPlayerSP thePlayer = mc.thePlayer;
		WorldClient theWorld = mc.theWorld;
		double[] playerPosition = new double[] {thePlayer.posX, thePlayer.posZ};
		
		for (double d = thePlayer.posY; d > 0; d -= 0.5) {
			BlockPos checkPos = new BlockPos(playerPosition[0], d, playerPosition[1]);
			Block block = theWorld.getBlockState(checkPos).getBlock();
			if (block.isBlockSolid(theWorld, checkPos, EnumFacing.UP))
				return false;
		}
		
		return true;
	}
	
}
