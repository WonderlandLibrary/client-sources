/**
 * 
 */
package cafe.kagu.kagu.mods.impl.visual;

import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.impl.EventTick;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.settings.impl.BooleanSetting;
import cafe.kagu.kagu.settings.impl.ModeSetting;

/**
 * @author DistastefulBannock
 *
 */
public class ModFunnyLimbs extends Module {
	
	public ModFunnyLimbs() {
		super("FunnyLimbs", Category.VISUAL);
		setSettings(mode, selfOnly);
	}
	
	private ModeSetting mode = new ModeSetting("Mode", "Choppy", "Choppy", "Jitter", "Walk");
	private BooleanSetting selfOnly = new BooleanSetting("Self Only", true);
	
	@EventHandler
	private Handler<EventTick> onTick = e -> {
		if (e.isPost())
			return;
		setInfo(mode.getMode());
	};
	
	/**
	 * @return the mode
	 */
	public ModeSetting getMode() {
		return mode;
	}
	
	/**
	 * @return the selfOnly
	 */
	public BooleanSetting getSelfOnly() {
		return selfOnly;
	}
	
}
