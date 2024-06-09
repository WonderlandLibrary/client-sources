package lunadevs.luna.module.combat;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventTarget;

import lunadevs.luna.category.Category;
import lunadevs.luna.events.DamageBlockEvent;
import lunadevs.luna.main.Luna;
import lunadevs.luna.module.Module;



public class AutoTool extends Module{

	  public static boolean enabled;
	 
	public AutoTool() {
		super("AutoTool", Keyboard.KEY_NONE, Category.COMBAT, false);
	}
	
	public void onEnable() {
		enabled = true;
	}
	
	public void onDisable() {
		enabled = false;
	}
	
	@EventTarget
	public void damageBlock(DamageBlockEvent e) {
		if(!this.isEnabled) return;
		mc.thePlayer.inventory.currentItem = Luna.getModuleHelper().getBestToolForBlock(e.getBlockPos());
	}
	
	public String getValue() {
		return null;
	}
	
}
