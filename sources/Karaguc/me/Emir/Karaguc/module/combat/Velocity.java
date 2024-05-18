package me.Emir.Karaguc.module.combat;

import de.Hero.settings.Setting;
import me.Emir.Karaguc.Karaguc;
import me.Emir.Karaguc.event.EventTarget;
import me.Emir.Karaguc.event.events.EventUpdate;
import me.Emir.Karaguc.module.Category;
import me.Emir.Karaguc.module.Module;

import java.util.ArrayList;

public class Velocity extends Module {
	
	public Velocity() {
		super("Velocity", 0, Category.COMBAT);
	}

	public void setup() {
		ArrayList<String> modes = new ArrayList<String>();
		modes.add("AAC");
		modes.add("Hypixel");
		modes.add("Normal");
		modes.add("AAC-Reverse 3.3.15");
		Karaguc.instance.settingsManager.rSetting(new Setting("Velocity Mode", this, "Normal", modes));
		Karaguc.instance.settingsManager.rSetting(new Setting("Percent", this, 20, 0, 100, true));
		Karaguc.instance.settingsManager.rSetting(new Setting("Water", this, false));
	}
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if (!this.isToggled()) 
			return;
		
		if (mc.thePlayer.hurtTime > 0) {
			mc.thePlayer.onGround = true;
			mc.thePlayer.motionX = 0;
			mc.thePlayer.motionY = 0.000001;
			mc.thePlayer.motionZ = 0;
		}
	}

}
