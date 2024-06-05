package zamorozka.modules.COMBAT;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.util.EnumHand;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventUpdate;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.EntityUtil;

public class AutoSword extends Module {

	public AutoSword() {
		super("AutoSword", Keyboard.KEY_NONE, Category.COMBAT);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(mc.player.hurtTime < 0) {
			mc.player.inventory.currentItem = EntityUtil.getSwordAtHotbar();
		}
	}
	
	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		super.onDisable();
	}
}