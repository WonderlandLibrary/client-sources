package me.swezedcode.client.manager.managers;

import java.util.ArrayList;

import me.swezedcode.client.gui.clickGui.ClickGui;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.module.modules.Exploit.ArmorSpoof;
import me.swezedcode.client.module.modules.Exploit.EXTREMEBANWAVE;
import me.swezedcode.client.module.modules.Exploit.Firion;
import me.swezedcode.client.module.modules.Exploit.HCFPhase;
import me.swezedcode.client.module.modules.Exploit.InventoryPlus;
import me.swezedcode.client.module.modules.Exploit.MsgCrasher;
import me.swezedcode.client.module.modules.Exploit.NoHunger;
import me.swezedcode.client.module.modules.Exploit.NoSwing;
import me.swezedcode.client.module.modules.Exploit.Phase;
import me.swezedcode.client.module.modules.Exploit.PingSpoof;
import me.swezedcode.client.module.modules.Fight.AimAssist;
import me.swezedcode.client.module.modules.Fight.AutoArmor;
import me.swezedcode.client.module.modules.Fight.AutoClicker;
import me.swezedcode.client.module.modules.Fight.AutoHeal;
import me.swezedcode.client.module.modules.Fight.BowAimbot;
import me.swezedcode.client.module.modules.Fight.ClickPearl;
import me.swezedcode.client.module.modules.Fight.Criticals;
import me.swezedcode.client.module.modules.Fight.KeepSprint;
import me.swezedcode.client.module.modules.Fight.KillAura;
import me.swezedcode.client.module.modules.Fight.Reach;
import me.swezedcode.client.module.modules.Fight.TriggerBot;
import me.swezedcode.client.module.modules.Fight.Velocity;
import me.swezedcode.client.module.modules.Motion.Eagle;
import me.swezedcode.client.module.modules.Motion.Glide;
import me.swezedcode.client.module.modules.Motion.Jesus;
import me.swezedcode.client.module.modules.Motion.LongHop;
import me.swezedcode.client.module.modules.Motion.Speed;
import me.swezedcode.client.module.modules.Motion.Sprint;
import me.swezedcode.client.module.modules.Motion.Step;
import me.swezedcode.client.module.modules.Motion.Strafe;
import me.swezedcode.client.module.modules.Options.MemeNames;
import me.swezedcode.client.module.modules.Options.Music;
import me.swezedcode.client.module.modules.Options.Rainbow;
import me.swezedcode.client.module.modules.Options.SelfDestruct;
import me.swezedcode.client.module.modules.Player.FastUse;
import me.swezedcode.client.module.modules.Player.Flight;
import me.swezedcode.client.module.modules.Player.InvMove;
import me.swezedcode.client.module.modules.Player.MagicCarpet;
import me.swezedcode.client.module.modules.Player.NoFall;
import me.swezedcode.client.module.modules.Player.NoRotate;
import me.swezedcode.client.module.modules.Player.NoSlow;
import me.swezedcode.client.module.modules.Player.NoVoid;
import me.swezedcode.client.module.modules.Player.Sneak;
import me.swezedcode.client.module.modules.Player.StopPackets;
import me.swezedcode.client.module.modules.Player.Terrain;
import me.swezedcode.client.module.modules.Visual.ESP;
import me.swezedcode.client.module.modules.Visual.FreeCam;
import me.swezedcode.client.module.modules.Visual.NameProtect;
import me.swezedcode.client.module.modules.Visual.NameTags;
import me.swezedcode.client.module.modules.Visual.NoBob;
import me.swezedcode.client.module.modules.Visual.NoFov;
import me.swezedcode.client.module.modules.Visual.NoScoreBoard;
import me.swezedcode.client.module.modules.Visual.Outline;
import me.swezedcode.client.module.modules.Visual.Trajectories;
import me.swezedcode.client.module.modules.World.AntiBot;
import me.swezedcode.client.module.modules.World.ChestAura;
import me.swezedcode.client.module.modules.World.ChestStealer;
import me.swezedcode.client.module.modules.World.FastPlace;
import me.swezedcode.client.module.modules.World.FriendsPlus;
import me.swezedcode.client.module.modules.World.InvCleaner;
import me.swezedcode.client.module.modules.World.LeetSpeak;
import me.swezedcode.client.module.modules.World.MaxRotations;
import me.swezedcode.client.module.modules.World.MineStrike;
import me.swezedcode.client.module.modules.World.MurdurerFinder;
import me.swezedcode.client.module.modules.World.Spammer;
import me.swezedcode.client.module.modules.World.SpeedMine;

public class ModuleManager {

	private static ArrayList<Module> modules = new ArrayList<Module>();
	
	public static ClickGui clickGui;
	
	public static void registerModules() {
		modules = new ArrayList<Module>();
		addModule(new Step());
		addModule(new Sprint());
		addModule(new KillAura());
		addModule(new BowAimbot());
		addModule(new AutoArmor());
		addModule(new Velocity());
		addModule(new NameTags());
		addModule(new ESP());
		addModule(new NoScoreBoard());
		addModule(new FriendsPlus());
		addModule(new Speed());
		addModule(new NoVoid());
		addModule(new NoFall());
		addModule(new AimAssist());
		addModule(new Jesus());
		addModule(new LongHop());
		addModule(new Firion());
		addModule(new Phase());
		addModule(new AntiBot());
		addModule(new ChestAura());
		addModule(new ChestStealer());
		addModule(new SpeedMine());
		addModule(new Glide());
		addModule(new Eagle());
		addModule(new Terrain());
		addModule(new FastUse());
		addModule(new MagicCarpet());
		addModule(new NoSlow());
		addModule(new InvMove());
		addModule(new AutoClicker());
		addModule(new TriggerBot());
		addModule(new FastPlace());
		addModule(new MaxRotations());
		addModule(new MineStrike());
		addModule(new Flight());
		addModule(new Outline());
		addModule(new NoSwing());
		addModule(new Rainbow());
		addModule(new Strafe());
		addModule(new AutoHeal());
		addModule(new InvCleaner());
		addModule(new Criticals());
		addModule(new Spammer());
		addModule(new MemeNames());
		addModule(new Trajectories());
		addModule(new InventoryPlus());
		addModule(new NoRotate());
		addModule(new NameProtect());
		addModule(new PingSpoof());
		addModule(new MsgCrasher());
		addModule(new Music());
		addModule(new EXTREMEBANWAVE());
		addModule(new MurdurerFinder());
		addModule(new HCFPhase());
		addModule(new FreeCam());
		addModule(new Sneak());
		addModule(new Reach());
		addModule(new SelfDestruct());
		addModule(new NoFov());
		addModule(new ClickPearl());
		addModule(new KeepSprint());
		addModule(new LeetSpeak());
		addModule(new ArmorSpoof());
		addModule(new NoHunger());
		addModule(new StopPackets());
		addModule(new NoBob());
		addModule(new me.swezedcode.client.module.modules.Visual.ClickGui());
		clickGui = new ClickGui();
	}
	
	private static void addModule(Module mod) {
		modules.add(mod);
	}
	
	public static ArrayList<Module> getModules() {
		return modules;
	}
	
}
