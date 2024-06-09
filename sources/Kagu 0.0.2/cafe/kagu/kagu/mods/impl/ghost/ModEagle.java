/**
 * 
 */
package cafe.kagu.kagu.mods.impl.ghost;

import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.impl.EventTick;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.settings.impl.DoubleSetting;
import cafe.kagu.kagu.settings.impl.IntegerSetting;
import cafe.kagu.kagu.utils.SpoofUtils;

/**
 * @author DistastefulBannock
 *
 */
public class ModEagle extends Module {
	
	public ModEagle() {
		super("Eagle", Category.GHOST);
		setSettings(releaseDelay);
	}
	
	private IntegerSetting releaseDelay = new IntegerSetting("Delay", 0, 0, 500, 1);
	
	private boolean shouldSneak = false;
	private long releaseAt = 0;
	
	@Override
	public void onEnable() {
		shouldSneak = false;
		releaseAt = 0;
	}
	
	@Override
	public void onDisable() {
		shouldSneak = false;
		releaseAt = 0;
	}
	
	@EventHandler
	private Handler<EventTick> onTick = e -> {
		if (e.isPost())
			return;
		SpoofUtils.setSpoofSneakMovement(true);
	};
	
	/**
	 * @param shouldSneak the shouldSneak to set
	 */
	public void setShouldSneak(boolean shouldSneak) {
		this.shouldSneak = shouldSneak;
		if (shouldSneak)
			releaseAt = System.currentTimeMillis() + releaseDelay.getValue();
	}
	
	/**
	 * @return the shouldSneak
	 */
	public boolean isShouldSneak() {
		return shouldSneak || System.currentTimeMillis() < releaseAt;
	}
	
}
