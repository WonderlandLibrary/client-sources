/**
 * 
 */
package cafe.kagu.kagu.mods.impl.move;

import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.impl.EventPlayerUpdate;
import cafe.kagu.kagu.eventBus.impl.EventTick;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.settings.impl.BooleanSetting;
import cafe.kagu.kagu.settings.impl.ModeSetting;
import cafe.kagu.kagu.utils.ChatUtils;
import cafe.kagu.kagu.utils.MovementUtils;
import net.minecraft.client.settings.KeyBinding;

/**
 * @author lavaflowglow
 *
 */
public class ModSprint extends Module {

	public ModSprint() {
		super("Sprint", Category.MOVEMENT);
		setSettings(mode);
	}

	public ModeSetting mode = new ModeSetting("Mode", "Vanilla", "Vanilla", "On Move", "Omni");
	
	@EventHandler
	private Handler<EventTick> onTick = e -> {
		if (e.isPost())
			return;
		setInfo(mode.getMode());
	};
	
	@EventHandler
	private Handler<EventPlayerUpdate> onUpdate = e -> {
		if (e.isPost())
			return;
		
		switch (mode.getMode()) {
			case "On Move":{
				if (MovementUtils.isPlayerMoving()) {
					mc.thePlayer.setSprinting(true);
					return;
				}
			}break;
			case "Vanilla":{
				KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);
			}break;
			case "Omni":{
				if (mc.thePlayer.onGround && mc.thePlayer.getFoodStats().getFoodLevel() >= 3
						&& !mc.thePlayer.isCollidedHorizontally && !mc.thePlayer.isUsingItem()
						&& MovementUtils.isPlayerMoving()) {
					mc.thePlayer.setSprinting(true);
				}
			}break;
		}
		
	};

}
