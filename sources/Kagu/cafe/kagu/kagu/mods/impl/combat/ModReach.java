/**
 * 
 */
package cafe.kagu.kagu.mods.impl.combat;

import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.settings.impl.DoubleSetting;

/**
 * @author lavaflowglow
 *
 */
public class ModReach extends Module {
	
	public ModReach() {
		super("Reach", Category.COMBAT);
		setSettings(combatReach, blockReach);
	}
	
	private DoubleSetting combatReach = new DoubleSetting("Combat Reach", 3.5, 3, 7, 0.1);
	private DoubleSetting blockReach = new DoubleSetting("Block Reach", 5, 4.5, 6, 0.1);
	
	/**
	 * @return the combatReach
	 */
	public DoubleSetting getCombatReach() {
		return combatReach;
	}
	
	/**
	 * @return the blockReach
	 */
	public DoubleSetting getBlockReach() {
		return blockReach;
	}
	
	@Override
	public String[] getInfo() {
		return new String[] {combatReach.getValue() + "", blockReach.getValue() + ""};
	}
	
}
