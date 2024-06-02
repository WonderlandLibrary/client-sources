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
public class ModHitboxes extends Module {
	
	public ModHitboxes() {
		super("Hitboxes", Category.COMBAT);
		setSettings(expansion);
	}
	
	private DoubleSetting expansion = new DoubleSetting("Expansion", 0.1, 0, 1, 0.01);
	
	/**
	 * @return the expansion
	 */
	public DoubleSetting getExpansion() {
		return expansion;
	}
	
	@Override
	public String[] getInfo() {
		return new String[] {expansion.getValue() + ""};
	}
	
}
