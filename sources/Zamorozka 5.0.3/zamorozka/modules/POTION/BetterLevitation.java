package zamorozka.modules.POTION;

import org.lwjgl.input.Keyboard;

import net.minecraft.potion.Potion;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventUpdate;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.Wrapper;

public class BetterLevitation extends Module {

	public BetterLevitation() {
		super("BetterLevitation", Keyboard.KEY_NONE, Category.POTION);
		// TODO Auto-generated constructor stub
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(Wrapper.getPlayer().isPotionActive(Potion.getPotionById(25))) {
		Wrapper.getPlayer().removeActivePotionEffect(Potion.getPotionById(25));
		}
	}

}
