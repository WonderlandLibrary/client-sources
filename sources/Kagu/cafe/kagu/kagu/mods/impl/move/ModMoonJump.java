/**
 * 
 */
package cafe.kagu.kagu.mods.impl.move;

import cafe.kagu.kagu.eventBus.Event;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.impl.EventPlayerUpdate;
import cafe.kagu.kagu.eventBus.impl.EventTick;
import cafe.kagu.kagu.mods.Module;
import net.minecraft.client.Minecraft;

/**
 * @author lavaflowglow
 *
 */
public class ModMoonJump extends Module {
	
	public ModMoonJump() {
		super("MoonJump", Category.MOVEMENT);
	}
	
	@EventHandler
	private Handler<EventPlayerUpdate> onUpadate = e -> {
		if (e.isPost())
			return;
		Minecraft.getMinecraft().thePlayer.motionY += 0.05;
	};
	
}
