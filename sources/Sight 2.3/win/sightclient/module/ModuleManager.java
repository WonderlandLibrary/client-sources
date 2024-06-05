package win.sightclient.module;

import java.util.ArrayList;

import win.sightclient.event.events.client.EventKey;
import win.sightclient.module.combat.AntiBot;
import win.sightclient.module.combat.Criticals;
import win.sightclient.module.combat.KillSults;
import win.sightclient.module.combat.Killaura;
import win.sightclient.module.combat.TargetStrafe;
import win.sightclient.module.combat.Velocity;
import win.sightclient.module.movement.AntiVoid;
import win.sightclient.module.movement.InvMove;
import win.sightclient.module.movement.NoSlow;
import win.sightclient.module.movement.Phase;
import win.sightclient.module.movement.Scaffold;
import win.sightclient.module.movement.Sprint;
import win.sightclient.module.movement.flight.Flight;
import win.sightclient.module.movement.speed.Speed;
import win.sightclient.module.movement.step.Step;
import win.sightclient.module.other.AntiDesync;
import win.sightclient.module.other.Blink;
import win.sightclient.module.other.ChatBypass;
import win.sightclient.module.other.Disabler;
import win.sightclient.module.other.PingSpoof;
import win.sightclient.module.other.RPCMod;
import win.sightclient.module.other.Streamer;
import win.sightclient.module.other.Timer;
import win.sightclient.module.other.UnStuck;
import win.sightclient.module.player.AutoPot;
import win.sightclient.module.player.AutoTool;
import win.sightclient.module.player.BedAura;
import win.sightclient.module.player.ChestStealer;
import win.sightclient.module.player.FastEat;
import win.sightclient.module.player.InvManager;
import win.sightclient.module.player.NoFall;
import win.sightclient.module.player.NoRotate;
import win.sightclient.module.render.Animations;
import win.sightclient.module.render.Chams;
import win.sightclient.module.render.ChestESP;
import win.sightclient.module.render.ClickGUIMod;
import win.sightclient.module.render.ESP;
import win.sightclient.module.render.FullBright;
import win.sightclient.module.render.HitEffects;
import win.sightclient.module.render.Hud;
import win.sightclient.module.render.ItemPhysics;
import win.sightclient.module.render.NoHurtCam;
import win.sightclient.module.render.NotifModule;
import win.sightclient.module.render.TabGUIMod;
import win.sightclient.module.render.Tracers;
import win.sightclient.module.scripts.Reload;
import win.sightclient.module.target.TargetAnimals;
import win.sightclient.module.target.TargetIgnoreDead;
import win.sightclient.module.target.TargetInvisibles;
import win.sightclient.module.target.TargetMobs;
import win.sightclient.module.target.TargetNoFriends;
import win.sightclient.module.target.TargetOther;
import win.sightclient.module.target.TargetPlayers;
import win.sightclient.module.target.TargetTeams;
import win.sightclient.script.Script;

public class ModuleManager {

	private ArrayList<Module> modules = new ArrayList<Module>();
	
	private int baseModSize;
	
	public ModuleManager() {
		this.modules.add(new TargetAnimals());
		this.modules.add(new TargetIgnoreDead());
		this.modules.add(new TargetInvisibles());
		this.modules.add(new TargetMobs());
		this.modules.add(new TargetNoFriends());
		this.modules.add(new TargetOther());
		this.modules.add(new TargetPlayers());
		this.modules.add(new TargetTeams());
		this.modules.add(new Killaura());
		this.modules.add(new Sprint());
		this.modules.add(new ClickGUIMod());
		this.modules.add(new FullBright());
		this.modules.add(new Hud());
		this.modules.add(new NoRotate());
		this.modules.add(new NotifModule());
		this.modules.add(new AntiVoid());
		this.modules.add(new AutoTool());
		this.modules.add(new ChestStealer());
		this.modules.add(new NoFall());
		this.modules.add(new TargetStrafe());
		this.modules.add(new ItemPhysics());
		this.modules.add(new InvManager());
		this.modules.add(new Speed());
		this.modules.add(new ESP());
		this.modules.add(new Scaffold());
		this.modules.add(new Step());
		this.modules.add(new Velocity());
		this.modules.add(new AntiBot());
		this.modules.add(new KillSults());
		this.modules.add(new PingSpoof());
		this.modules.add(new Flight());
		this.modules.add(new AntiDesync());
		this.modules.add(new Chams());
		this.modules.add(new NoHurtCam());
		this.modules.add(new Streamer());
		this.modules.add(new Blink());
		this.modules.add(new NoSlow());
		this.modules.add(new Criticals());
		this.modules.add(new BedAura());
		this.modules.add(new InvMove());
		this.modules.add(new UnStuck());
		this.modules.add(new Phase());
		this.modules.add(new ChestESP());
		this.modules.add(new Disabler());
		this.modules.add(new Animations());
		this.modules.add(new Reload());
		this.modules.add(new HitEffects());
		this.modules.add(new ChatBypass());
		this.modules.add(new AutoPot());
		this.modules.add(new TabGUIMod());
		this.modules.add(new RPCMod());
		this.modules.add(new Tracers());
		this.modules.add(new FastEat());
		
		this.modules.add(new Timer());
		
		baseModSize = this.modules.size();
	}
	
	public ArrayList<Module> getModules() {
		return this.modules;
	}
	
	public void addScript(Script s) {
		this.modules.add(s);
	}
	
	public int getBase() {
		return this.baseModSize;
	}
	
	public void removeScripts() {
		for (int i = 0; i < this.modules.size(); i++) {
			if (this.modules.get(i) instanceof Script) {
				this.modules.remove(i);
			}
		}
	}
	
	public ArrayList<Module> getModInCategory(Category c) {
		ArrayList<Module> mods = new ArrayList<Module>();
		for (Module m : this.modules) {
			if (m.getCategory() == c) {
				mods.add(m);
			}
		}
		return mods;
	}
	
	public boolean isEnabled(String module) {
		Module m = this.getModuleByName(module);
		if (m != null) {
			return m.isToggled();
		}
		return false;
	}
	
	public void onKey(int key) {
		new EventKey(key).call();
		for (Module m : this.modules) {
			if (m.getKey() == key) {
				m.toggle();
			}
		}
	}

	public Module getModuleByName(String string) {
		for (Module m : this.modules) {
			if (m.getName().equalsIgnoreCase(string)) {
				return m;
			}
		}
		return null;
	}
}
