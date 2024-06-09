package chaos.modules;

import java.util.List;

import chaos.chaos;
import chaos.modules.impl.GUI.HUD;
import chaos.modules.impl.Render.FakeName;

import java.util.ArrayList;

public class ModuleManager {

	public static List<Module> modules = new ArrayList<Module>();
	
	public ModuleManager() {
		
		//|-|-|-|xxxx-Modules-xxxx|-|-|-|//
		//addModule(new Beispiel());
		
		//-Combat-//
		
		//-GUI-//
		addModule(new HUD());
		
		//-Movement-//
		
		//-Player-//
		
		//-Render-//
		addModule(new FakeName());
;		
		//addModule(new Beispiel());
		//|-|-|-|xxxx-Modules-xxxx|-|-|-|//
		
		chaos.instance.logger.Info("Loaded Modules: " + modules.size());
	}
	
	public void addModule(Module module) {
		this.modules.add(module);
		chaos.instance.logger.Loading("Module: " + module.getName());
	}
	
	public List<Module> getModules() {
		return modules;
	}
	
	public static Module getModuleByName(String moduleName) {
		for(Module mod : modules) {
			if((mod.getName().trim().equalsIgnoreCase(moduleName)) || (mod.toString().trim().equalsIgnoreCase(moduleName.trim()))) {
				return mod;
			}
		}
		return null;
	}
	
	public Module getModule(Class <? extends Module> clazz) {
		for(Module m : modules) {
			if(m.getClass() == clazz) {
				return m;
			}
		}
		return null;
	}
	public Module getModuleByClass(Class clazz) {
		for (Module m : modules) {
			if (m.getClass() == clazz) {
				return m;
			}
		}
		return null;
	}
}

