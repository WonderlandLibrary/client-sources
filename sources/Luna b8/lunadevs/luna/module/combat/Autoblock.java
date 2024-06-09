package lunadevs.luna.module.combat;

import org.lwjgl.input.Keyboard;

import lunadevs.luna.category.Category;
import lunadevs.luna.main.Luna;
import lunadevs.luna.module.Module;
import net.minecraft.item.ItemSword;

public class Autoblock extends Module {

	public Autoblock() {
		super("Autoblock", Keyboard.KEY_NONE, Category.COMBAT, false);
	}
	
	public void onEnable() {
		String a = "Use this with Killaura.";
		Luna.addChatMessage(a);
	}
	
	public void onUpdate() {
		if(!this.isEnabled) return;
		if ((!mc.thePlayer.isBlocking()) && (mc.thePlayer.inventory.getCurrentItem() != null) && ((mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword))) {
		      mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem());
		    }

	}
	
	public String getValue() {
		return null;
	}
	
}
	