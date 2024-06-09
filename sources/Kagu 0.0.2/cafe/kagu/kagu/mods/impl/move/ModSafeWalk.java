/**
 * 
 */
package cafe.kagu.kagu.mods.impl.move;

import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.impl.EventTick;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.utils.SpoofUtils;

/**
 * @author DistastefulBannock
 *
 */
public class ModSafeWalk extends Module {
	
	public ModSafeWalk() {
		super("SafeWalk", Category.MOVEMENT);
	}
	
	@EventHandler
	private Handler<EventTick> onTick = e -> {
		if (e.isPost())
			return;
		SpoofUtils.setSpoofSneakMovement(true);
	};
	
}
