package com.kilo.mod.all;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.util.Timer;
import com.kilo.util.Util;

public class AntiAFK extends Module {
	
	private Timer timer = new Timer();
	
	public AntiAFK(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
		
		addOption("Delay", "Delay between jumps", Interactable.TYPE.SLIDER, 0.5f, new float[] {0, 5}, true);
	}
	
	public void update() {
		if (!active) { return; }
		
		if (timer.isTime(Util.makeFloat(getOptionValue("delay"))*60)) {
			if (mc.thePlayer.onGround) {
				mc.thePlayer.motionY = 0.1f;
				timer.reset();
			}
		}
	}
}
