/**
 * 
 */
package cafe.kagu.kagu.mods.impl.ghost;

import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.settings.impl.IntegerSetting;

/**
 * @author DistastefulBannock
 *
 */
public class ModChangeRightClickDelay extends Module {
	
	public ModChangeRightClickDelay() {
		super("ChangeRightClickDelay", Category.GHOST);
		setSettings(delay);
	}
	
	private IntegerSetting delay = new IntegerSetting("Delay", 2, 0, 4, 1);
	
	/**
	 * @return the delay
	 */
	public IntegerSetting getDelay() {
		return delay;
	}
	
}
