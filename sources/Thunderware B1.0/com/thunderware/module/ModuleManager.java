package com.thunderware.module;

import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

import com.thunderware.events.Event;
import com.thunderware.events.listeners.EventKey;
import com.thunderware.module.combat.AntiBot;
import com.thunderware.module.combat.Killaura;
import com.thunderware.module.exploit.Disabler;
import com.thunderware.module.movement.Flight;
import com.thunderware.module.movement.Speed;
import com.thunderware.module.player.ChestStealer;
import com.thunderware.module.player.Sprint;
import com.thunderware.module.visuals.Animations;
import com.thunderware.module.visuals.ClickGUI;
import com.thunderware.module.visuals.Compass;
import com.thunderware.module.visuals.Hud;

public class ModuleManager {

	private CopyOnWriteArrayList<ModuleBase> modules = new CopyOnWriteArrayList<>();
	
	public void addModule(ModuleBase mod) {
		System.out.println("Registed Module : " + mod.getName());
		this.modules.add(mod);
	}

	
	public void setupModules() {
		addModule(new AntiBot());
		addModule(new Disabler());
		addModule(new Hud());
		addModule(new Sprint());
		addModule(new Flight());
		addModule(new ChestStealer());
		addModule(new Killaura());
		addModule(new Speed());
		addModule(new Animations());
		addModule(new Compass());
		addModule(new ClickGUI());
	}
	
	public void sortModules() {
		  Collections.sort(modules, new Comparator<ModuleBase>() {
		      @Override
		      public int compare(final ModuleBase object1, final ModuleBase object2) {
		          return object1.getName().compareTo(object2.getName());
		      }
		  });
	}
	
	public CopyOnWriteArrayList<ModuleBase> getModules() {
		return modules;
	}
	
	public ModuleBase getModuleByName(String name) {
		for (ModuleBase module : modules)
			if(module.getName().equalsIgnoreCase(name))
				return module;
		return null;
	}
	
    public void onEvent(Event event) {
		if(event instanceof EventKey) {
			EventKey e = (EventKey)event;
	        for (ModuleBase module : modules) {
				if(module.getKey() == e.getCode())
					module.toggle();
	        }
		}
		for(ModuleBase mod : modules) {
			if(mod.isToggled())
				mod.onEvent(event);
			else
				mod.onSkipEvent(event);
		}
    }
}
