package zamorozka.modules.VISUALLY;

import org.lwjgl.input.Keyboard;

import net.minecraft.potion.Potion;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventUpdate;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.Wrapper;

public class NoBlindEffect extends Module {

	public NoBlindEffect() {
		super("NoBlindEffect", Keyboard.KEY_NONE, Category.POTION);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(Wrapper.getPlayer().isPotionActive(Potion.getPotionById(15))) {
		Wrapper.getPlayer().removeActivePotionEffect(Potion.getPotionById(15));
		}
	}

}
