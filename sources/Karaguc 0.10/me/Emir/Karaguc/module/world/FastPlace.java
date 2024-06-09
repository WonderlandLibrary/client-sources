package me.Emir.Karaguc.module.world;

import me.Emir.Karaguc.event.EventTarget;
import me.Emir.Karaguc.event.events.EventUpdate;
import me.Emir.Karaguc.module.Category;
import me.Emir.Karaguc.module.Module;

public class FastPlace extends Module {
	
	public FastPlace() {
		super("FastPlace", 0, Category.WORLD);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		mc.rightClickDelayTimer = 0;
	}
	
	@Override
	public void onDisable() {
		mc.rightClickDelayTimer = 6;
	}

}
