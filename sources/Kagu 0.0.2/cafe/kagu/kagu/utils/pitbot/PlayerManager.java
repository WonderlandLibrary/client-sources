/**
 * 
 */
package cafe.kagu.kagu.utils.pitbot;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;

/**
 * @author DistastefulBannock
 * Contains code that calculates movement, rotation, clicking, and slot selection
 */
public interface PlayerManager {
	
	/**
	 * @param targetEntity The target entity
	 * Decides when the autoclicker should click
	 */
	public void autoclickerCalculations(EntityLivingBase targetEntity);
	
	/**
	 * Calculates the movement needed to get closer to the target destination
	 * @param pathToTarget A list of positions that need to be cleared to reach the target
	 * @param targetEntity The target entity
	 * @return A float array containing the moveForward, moveStrafe, rotYaw, and rotPitch in that order, or null if the desired position cannot easily be reached
	 */
	public float[] getMovement(List<BlockPos> pathToTarget, EntityLivingBase targetEntity);
	
	/**
	 * Decides what slot the player should have
	 * @param targetEntity The target entity
	 * @return The slot number that the player should switch to
	 */
	public int selectSlot(EntityLivingBase targetEntity);
	
}
