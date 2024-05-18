package me.swezedcode.client.utils;

import me.swezedcode.client.manager.Manager;
import me.swezedcode.client.module.Module;

public class ModuleUtils {

	public static void onKey(int key) {
		for (Module mod : Manager.getManager().getModuleManager().getModules()) {
			if (mod.getKeycode() == key) {
				mod.toggle();
			}
		}
	}

	public static Module getMod(Class<? extends Module> theMod) {
		for (Module mod : Manager.getManager().getModuleManager().getModules()) {
			if (mod.getClass() == theMod) {
				return mod;
			}
		}
		return null;
	}

	public static Module getModuleFromName(String name) {
		for (Module m : Manager.getManager().getModuleManager().getModules()) {
			if (m.getName().equalsIgnoreCase(name))
				return m;
		}
		return null;
	}
	
}
