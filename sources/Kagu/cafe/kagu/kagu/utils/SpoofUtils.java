/**
 * 
 */
package cafe.kagu.kagu.utils;

import cafe.kagu.kagu.Kagu;
import cafe.kagu.kagu.eventBus.EventBus;
import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.impl.EventTick;
import net.minecraft.client.Minecraft;

/**
 * @author lavaflowglow
 *
 */
public class SpoofUtils {
	
	private static float spoofedYaw = 0, spoofedPitch = 0, spoofedLastYaw = 0, spoofedLastPitch = 0;
	private static boolean spoofYaw = false, spoofPitch = false;
	private static boolean spoofSneakMovement = false;
	private static float spoofedMovementYaw = 0;
	private static boolean spoofMovementYaw = false;
	
	private static Minecraft mc = Minecraft.getMinecraft();
	
	/**
	 * Called at the start of the client
	 */
	public static void start() {
		Kagu.getEventBus().subscribe(new SpoofUtils());
	}
	
	@EventHandler
	private Handler<EventTick> onTick = e -> {
		if (e.isPre()) {
			spoofYaw = false;
			spoofPitch = false;
			spoofBlocking = false;
			spoofSneakMovement = false;
		}else {
			
			if (!spoofYaw) {
				spoofedLastYaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
			}else {
				spoofedLastYaw = spoofedYaw;
			}
			
			if (!spoofPitch) {
				spoofedLastPitch = Minecraft.getMinecraft().thePlayer.rotationPitch;
			}else {
				spoofedLastPitch = spoofedPitch;
			}
			
		}
	};
	
	/**
	 * @return the spoofedYaw
	 */
	public static float getSpoofedYaw() {
		return spoofedYaw;
	}

	/**
	 * @param spoofedYaw the spoofedYaw to set
	 */
	public static void setSpoofedYaw(float spoofedYaw) {
		SpoofUtils.spoofedYaw = spoofedYaw;
		spoofYaw = true;
	}

	/**
	 * @return the spoofedPitch
	 */
	public static float getSpoofedPitch() {
		return spoofedPitch;
	}

	/**
	 * @param spoofedPitch the spoofedPitch to set
	 */
	public static void setSpoofedPitch(float spoofedPitch) {
		SpoofUtils.spoofedPitch = spoofedPitch;
		spoofPitch = true;
	}

	/**
	 * @return the spoofedLastYaw
	 */
	public static float getSpoofedLastYaw() {
		return spoofedLastYaw;
	}

	/**
	 * @param spoofedLastYaw the spoofedLastYaw to set
	 */
	public static void setSpoofedLastYaw(float spoofedLastYaw) {
		SpoofUtils.spoofedLastYaw = spoofedLastYaw;
	}

	/**
	 * @return the spoofedLastPitch
	 */
	public static float getSpoofedLastPitch() {
		return spoofedLastPitch;
	}

	/**
	 * @param spoofedLastPitch the spoofedLastPitch to set
	 */
	public static void setSpoofedLastPitch(float spoofedLastPitch) {
		SpoofUtils.spoofedLastPitch = spoofedLastPitch;
	}

	/**
	 * @return the spoofYaw
	 */
	public static boolean isSpoofYaw() {
		return spoofYaw;
	}

	/**
	 * @return the spoofPitch
	 */
	public static boolean isSpoofPitch() {
		return spoofPitch;
	}
	
	private static boolean spoofBlocking = false;

	/**
	 * @return the spoofBlocking
	 */
	public static boolean isSpoofBlocking() {
		return spoofBlocking;
	}

	/**
	 * @param spoofBlocking the spoofBlocking to set
	 */
	public static void setSpoofBlocking(boolean spoofBlocking) {
		SpoofUtils.spoofBlocking = spoofBlocking;
	}

	/**
	 * @return the spoofSneakMovement
	 */
	public static boolean isSpoofSneakMovement() {
		return spoofSneakMovement;
	}

	/**
	 * @param spoofSneakMovement the spoofSneakMovement to set
	 */
	public static void setSpoofSneakMovement(boolean spoofSneakMovement) {
		SpoofUtils.spoofSneakMovement = spoofSneakMovement;
	}
	
	/**
	 * @param spoofedMovementYaw the spoofedMovementYaw to set
	 */
	public static void setSpoofedMovementYaw(float spoofedMovementYaw) {
		spoofMovementYaw = true;
		SpoofUtils.spoofedMovementYaw = spoofedMovementYaw;
	}
	
	/**
	 * @return the spoofedMovementYaw
	 */
	public static float getSpoofedMovementYaw() {
		return spoofedMovementYaw;
	}
	
	/**
	 * @return the spoofMovementYaw
	 */
	public static boolean isSpoofMovementYaw() {
		return spoofMovementYaw;
	}
	
	/**
	 * @param spoofMovementYaw the spoofMovementYaw to set
	 */
	public static void setSpoofMovementYaw(boolean spoofMovementYaw) {
		SpoofUtils.spoofMovementYaw = spoofMovementYaw;
	}
	
}
