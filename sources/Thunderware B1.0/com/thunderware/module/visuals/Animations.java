package com.thunderware.module.visuals;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.thunderware.events.Event;
import com.thunderware.events.listeners.EventUpdate;
import com.thunderware.module.ModuleBase;
import com.thunderware.settings.settings.ModeSetting;

public class Animations extends ModuleBase {

	public static ModeSetting mode;
	
	public Animations() {
		super("Animations", Keyboard.KEY_NONE, Category.VISUALS);

		ArrayList<String> modes = new ArrayList<>();
		modes.add("1.7");
		modes.add("Spin");
		modes.add("Test");
		mode = new ModeSetting("Block Mode", modes);
		addSettings(mode);
	}
	
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			setSuffix(mode.getCurrentValue());
		}
	}
	
	

}
