package com.kilo.mod.all;

import java.util.Random;

import com.kilo.manager.ChatSpamManager;
import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.mod.toolbar.dropdown.SettingsRel;
import com.kilo.util.Timer;
import com.kilo.util.Util;

public class ChatSpam extends Module {
	
	private Timer timer = new Timer();
	private int cur = 0;
	
	public ChatSpam(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
		
		addOption("Time", "Time between each random message", Interactable.TYPE.SLIDER, 2, new float[] {0.1f, 4}, true);
		addOption("Edit", "Add, change or remove messages to be spammed", Interactable.TYPE.SETTINGS, SettingsRel.SPAMBOT, null, false);
	}
	
	public void update() {
		if (!active) { return; }
		
		if (timer.isTime(Util.makeFloat(getOptionValue("time")))) {
			if (ChatSpamManager.getSize() > 0) {
				if (cur >= 0 && cur <= ChatSpamManager.getSize()-1) {
					mc.thePlayer.sendChatMessage(ChatSpamManager.getMessage(cur));
				} else {
					cur = 0;
					mc.thePlayer.sendChatMessage(ChatSpamManager.getMessage(cur));
				}
				cur++;
			}
			timer.reset();
		}
	}
}
