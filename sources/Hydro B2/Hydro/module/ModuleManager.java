package Hydro.module;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import Hydro.Client;
import Hydro.module.modules.combat.Aura;
import Hydro.module.modules.combat.Criticals;
import Hydro.module.modules.cosmetics.Cape;
import Hydro.module.modules.misc.*;
import Hydro.module.modules.movement.AirJump;
import Hydro.module.modules.movement.Bhop;
import Hydro.module.modules.movement.Flight;
import Hydro.module.modules.movement.JetPack;
import Hydro.module.modules.movement.Sprint;
import Hydro.module.modules.player.AutoArmor;
import Hydro.module.modules.player.Breaker;
import Hydro.module.modules.player.ChestStealer;
import Hydro.module.modules.player.FastBridge;
import Hydro.module.modules.player.FastEat;
import Hydro.module.modules.player.InvManager;
import Hydro.module.modules.player.InvMove;
import Hydro.module.modules.player.LongJump;
import Hydro.module.modules.player.NoFall;
import Hydro.module.modules.player.Phase;
import Hydro.module.modules.player.Scaffold;
import Hydro.module.modules.player.Velocity;
import Hydro.module.modules.render.*;
import Hydro.script.ScriptModule;

public class ModuleManager {
	
	public CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<>();

	public ModuleManager() {
		
		//Movement
		modules.add(new Flight());
		modules.add(new Sprint());
		modules.add(new AirJump());
		modules.add(new JetPack());
		modules.add(new Bhop());
		
		//Render
		modules.add(new TabGUI());
		modules.add(new FullBright());
		modules.add(new ClickGui());
		modules.add(new Weather());
		modules.add(new CustomGlint());
		modules.add(new DamageParticles());
		modules.add(new PlayerESP());
		modules.add(new Animations());
		modules.add(new Hydro.module.modules.render.ArrayList()); //Have to add it like this or it will make errors.
		modules.add(new TargetHUD());
		modules.add(new ToxicFX());
		
		//Player
		modules.add(new NoFall());
		modules.add(new FastBridge());
		modules.add(new Velocity());
		modules.add(new Scaffold());
		modules.add(new ChestStealer());
		modules.add(new InvManager());
		modules.add(new InvMove());
		modules.add(new AutoArmor());
		modules.add(new FastEat());
		modules.add(new LongJump());
		modules.add(new Phase());
		
		//Combat
		modules.add(new Aura());
		modules.add(new Criticals());
		
		//Misc
		modules.add(new AntiVoid());
		modules.add(new KillSults());
		modules.add(new Breaker());
		modules.add(new AutoRegister());
		modules.add(new Disabler());
		modules.add(new AutoL());

		//Cosmetics
		modules.add(new Cape());
	}
	
	public CopyOnWriteArrayList<Module> getModules(){
		return modules;
	}
	
	public CopyOnWriteArrayList<Module> getEnabledModules(){
		CopyOnWriteArrayList<Module> enabledModules = new CopyOnWriteArrayList<>();

		for(Module m : getModules()) {
			if(m.isEnabled()) {
				enabledModules.add(m);
			}
		}
		
		return enabledModules;
	}
	
	public ArrayList<Module> getModulesInCategory(Category categoryIn){
		ArrayList<Module> mods = new ArrayList<Module>();
		for(Module m : Client.instance.moduleManager.getModules()){
			if(m.getCategory() == categoryIn)
				mods.add(m);
		}
		return mods;
	}
	
	public Module getModuleByName(String name) {
        for (Module m : getModules()) {
            if (m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }
        return null;
    }

	private void addModule(Module module) {
		modules.add(module);
	}

    public void addScriptModule(ScriptModule module){
		addModule(module);
	}
	
}
