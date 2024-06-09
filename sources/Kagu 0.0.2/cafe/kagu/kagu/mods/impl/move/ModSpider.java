/**
 * 
 */
package cafe.kagu.kagu.mods.impl.move;

import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.impl.EventPlayerUpdate;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.settings.impl.DoubleSetting;
import cafe.kagu.kagu.settings.impl.ModeSetting;
import cafe.kagu.kagu.utils.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;

/**
 * @author DistastefulBannock
 *
 */
public class ModSpider extends Module {
	
	public ModSpider() {
		super("Spider", Category.MOVEMENT);
		setSettings(mode, speed);
	}
	
	private ModeSetting mode = new ModeSetting("Mode", "Vanilla", "Vanilla");
	private DoubleSetting speed = new DoubleSetting("Speed", 0.4, 0.05, 2, 0.05).setDependency(() -> mode.is("Vanilla"));
	
	@EventHandler
	private Handler<EventPlayerUpdate> onPlayerUpdate = e -> {
		if (e.isPost())
			return;
		
		EntityPlayerSP thePlayer = mc.thePlayer;
		switch (mode.getMode()) {
			case "Vanilla":{
				if (thePlayer.isCollidedHorizontally && MovementUtils.isPlayerMoving()) {
					thePlayer.motionY = speed.getValue();
					thePlayer.onGround = true;
				}
			}break;
		}
		
	};
	
}
