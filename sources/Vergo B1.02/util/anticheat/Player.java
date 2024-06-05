package xyz.vergoclient.util.anticheat;

import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.EventTick;
import xyz.vergoclient.event.impl.EventUpdate;
import xyz.vergoclient.modules.OnEventInterface;
import xyz.vergoclient.util.main.MovementUtils;
import net.minecraft.client.Minecraft;

public class Player implements OnEventInterface {
	
	// How much the motionX and motionZ is multiplied by while in the air
	private static float airFriction = 0.91f;
	
	// How much the motionX and motionZ is multiplied by while on the ground
	private static float groundFriction = Float.NaN;
	
	// How fast you fall
	private static double fallSpeed = 0.08;
	
	// How strong the gravity is
	private static double gravity = 0.9800000190734863;
	
	// Data to help me bypasses anticheats
	private static transient double dist = 0, distX = 0, distZ = 0, lastDist = 0, lastDistX = 0, lastDistZ = 0;
	private static transient boolean onGround = false, lastOnGround = false, lastLastOnground = false;
	
	// Minecraft
	public static transient Minecraft mc = Minecraft.getMinecraft();
	
	// Event hook
	@Override
	public void onEvent(Event e) {
		
		// Reset any values that need resetting
		if (e instanceof EventUpdate && e.isPre()) {
			airFriction = 0.91f;
			fallSpeed = 0.08;
			gravity = 0.9800000190734863;
			groundFriction = Float.NaN;
		}
		
		// Set distance floats and ground booleans
		else if (e instanceof EventTick && e.isPost()) {
			lastDist = dist;
			lastDistX = distX;
			lastDistZ = distZ;
			distX = mc.thePlayer.posX - mc.thePlayer.lastTickPosX;
			distZ = mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ;
			dist = (distX * distX) + (distZ * distZ);
			
			lastLastOnground = lastOnGround;
			lastOnGround = onGround;
			onGround = MovementUtils.isOnGround(0.0001);
		}
	}
	
	// Getters and setters
	public static float getAirFriction() {
		return airFriction;
	}

	public static void setAirFriction(float airFriction) {
		Player.airFriction = airFriction;
	}
	
	public static double getDist() {
		return dist;
	}

	public static double getDistX() {
		return distX;
	}

	public static double getDistZ() {
		return distZ;
	}

	public static double getLastDist() {
		return lastDist;
	}

	public static double getLastDistX() {
		return lastDistX;
	}

	public static double getLastDistZ() {
		return lastDistZ;
	}

	public static boolean isOnGround() {
		onGround = MovementUtils.isOnGround(0.0001);
		return onGround;
	}

	public static boolean isLastOnGround() {
		return lastOnGround;
	}

	public static boolean isLastLastOnground() {
		return lastLastOnground;
	}
	
	public static double getFallSpeed() {
		return fallSpeed;
	}

	public static void setFallSpeed(double fallSpeed) {
		Player.fallSpeed = fallSpeed;
	}

	public static double getGravity() {
		return gravity;
	}

	public static void setGravity(double gravity) {
		Player.gravity = gravity;
	}

	public static float getGroundFriction() {
		return groundFriction;
	}

	public static void setGroundFriction(float groundFriction) {
		Player.groundFriction = groundFriction;
	}
	
}
