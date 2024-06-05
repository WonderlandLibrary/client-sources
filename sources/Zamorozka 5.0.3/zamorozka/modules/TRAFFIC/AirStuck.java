package zamorozka.modules.TRAFFIC;

import zamorozka.event.EventTarget;
import zamorozka.event.events.EventUpdate;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class AirStuck extends Module {

	public AirStuck() {
		super("AirStuck",0, Category.TRAFFIC);
	}
    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (!this.mc.player.isDead) {
            this.mc.player.isDead = true;
        }
    }

    public void onDisable() {
        this.mc.player.isDead = false;
        super.onDisable();
    }
	
}