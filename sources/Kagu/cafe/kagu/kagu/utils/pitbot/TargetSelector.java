/**
 * 
 */
package cafe.kagu.kagu.utils.pitbot;

import net.minecraft.entity.EntityLivingBase;

/**
 * @author DistastefulBannock
 *
 */
public interface TargetSelector {
	
	/**
	 * Selects a preferred target
	 * @param maxDistance The max distance to search for the target
	 * @return The selected target
	 */
	public EntityLivingBase getTarget(double maxDistance);
	
}
