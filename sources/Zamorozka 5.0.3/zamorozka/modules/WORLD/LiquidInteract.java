package zamorozka.modules.WORLD;

import zamorozka.event.EventTarget;
import zamorozka.event.events.EventCanCollide;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class LiquidInteract extends Module {

	public LiquidInteract() {
		super("LiquidInteract", 0, Category.WORLD);
	}
	
	@EventTarget
	public void Interact(EventCanCollide event) {
		event.setCancelled(true);
	}

}
