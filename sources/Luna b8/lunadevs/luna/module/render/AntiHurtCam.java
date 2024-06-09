package lunadevs.luna.module.render;

import com.darkmagician6.eventapi.EventTarget;

import lunadevs.luna.category.Category;
import lunadevs.luna.events.EventHurtCam;
import lunadevs.luna.module.Module;

public class AntiHurtCam extends Module{

	public AntiHurtCam() {
		super("AntiHurtCam", 0, Category.RENDER, false);
	}
	
	@EventTarget
	public void onHurtCam(EventHurtCam e) {
		if(!this.isEnabled) return;
		e.setCancelled(true);
	}
	
	public String getValue() {
		return null;
	}
	
}
