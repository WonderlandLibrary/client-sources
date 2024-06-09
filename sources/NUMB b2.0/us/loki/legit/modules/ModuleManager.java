package us.loki.legit.modules;

import java.util.List;

import us.loki.legit.Client;
import us.loki.legit.gui.test.Radar;
import us.loki.legit.modules.impl.GUI.*;
import us.loki.legit.modules.impl.Mods.*;
import us.loki.legit.modules.impl.Render.*;
import us.loki.legit.modules.impl.Movement.*;
import us.loki.legit.modules.impl.Other.*;

import java.util.ArrayList;

public class ModuleManager {

	public static List<Module> modules = new ArrayList<Module>();

	public ModuleManager() {

		// |-|-|-|xxxx-Modules-xxxx|-|-|-|//

		// -GUI-//
		addModule(new HUD());
		addModule(new GuiModule());
		addModule(new Arraylist());
		addModule(new Minime());
		
		//-COSMETICS-//
		addModule(new Cosmetics());
		
		//-TEST-//
		addModule(new FPSBoost());
		
		//-COMMANDS-//
		addModule(new ChatCommands());
		
		//-OTHER-//		
		addModule(new Dab());
		//addModule(new MiniMap());
		addModule(new Radar());

		// -Movement-//
		addModule(new Sprint());
		addModule(new Sneak());

		// -MODS-//
		addModule(new ArmorHUD());
		addModule(new StatusHUD());
		addModule(new DirectionHUD());
		addModule(new ItemPhysics());
		addModule(new Keystrokes());
		addModule(new HitAnimation());

		// -Render-//
		addModule(new FakeName());
		addModule(new NoScoreBoard());
		addModule(new Gammabright());

		// |-|-|-|xxxx-Modules-xxxx|-|-|-|//

		Client.instance.logger.Info("Loaded Modules: " + modules.size());
	}

	public void addModule(Module module) {
		this.modules.add(module);
		Client.instance.logger.Loading("Module: " + module.getName());
	}

	public static List<Module> getModules() {
		return modules;
	}

	public static Module getModuleByName(String moduleName) {
		for (Module mod : modules) {
			if ((mod.getName().trim().equalsIgnoreCase(moduleName))
					|| (mod.toString().trim().equalsIgnoreCase(moduleName.trim()))) {
				return mod;
			}
		}
		return null;
	}

	public Module getModule(Class<? extends Module> clazz) {
		for (Module m : modules) {
			if (m.getClass() == clazz) {
				return m;
			}
		}
		return null;
	}

	public static ArrayList<Module> getModulesInCategory(Category cat2) {
		ArrayList<Module> modsInCat = new ArrayList<Module>();
		for (Module mod : ModuleManager.getModules()) {
			if (!mod.getCategory().equals((Object) cat2))
				continue;
			modsInCat.add(mod);
		}
		return modsInCat;
	}

	public Module getModuleByClass(Class clazz) {
		for (Module m : modules) {
			if (m.getClass() == clazz) {
				return m;
			}
		}
		return null;
	}

	public static <T extends Module> T findMod(Class<T> clazz) {
		for (Module mod : modules) {
			if (mod.getClass() == clazz) {
				return clazz.cast(mod);
			}
		}

		return null;
	}

	public static List<Module> enabledList() {
		return modules;
	}

}
