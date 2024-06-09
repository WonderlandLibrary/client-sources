package Squad.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import Squad.Modules.Combat.Coming;
import Squad.Modules.Combat.ModKillAura;
import Squad.Modules.Combat.NoSlowdown;
import Squad.Modules.Combat.Teams;
import Squad.Modules.Gui.Keystrokes;
import Squad.Modules.Gui.ModClickGui;
import Squad.Modules.Movement.*;
import Squad.Modules.Other.FastFall;
import Squad.Modules.Other.HUD;
import Squad.Modules.Other.InvCleaner;
import Squad.Modules.Other.NoPitchLimit;
import Squad.Modules.Other.NoSwing;
import Squad.Modules.Other.Streammode;
import Squad.Modules.Player.AutoArmor;
import Squad.Modules.Player.AutoRespawn;
import Squad.Modules.Player.BetterHit;
import Squad.Modules.Player.ChestStealer;
import Squad.Modules.Player.FastBridge;
import Squad.Modules.Player.FastLadder;
import Squad.Modules.Player.FastPlace;
import Squad.Modules.Player.FastUse;
import Squad.Modules.Player.FullBright;
import Squad.Modules.Player.ModFly;
import Squad.Modules.Player.Nofall;
import Squad.Modules.Player.SafeWalk;
import Squad.Modules.Player.Spammer;
import Squad.Modules.Player.TntBlock;
import Squad.Modules.Player.Velocity;
import Squad.Modules.Player.Zoot;
import Squad.Modules.Render.Chams;
import Squad.Modules.Render.HurtCam;
import Squad.Modules.Render.ItemEsp;
import Squad.Modules.Render.NoBob;
import Squad.Modules.Render.OutlineEsp;
import Squad.Modules.Render.Rec;
import Squad.Modules.Settings.AutoSettings;
import Squad.Modules.World.BedFucker;
import Squad.Modules.World.Tower;
import net.minecraft.client.Minecraft;

public class ModuleManager {
	

	 public static List<Module> modules = new ArrayList<Module>();

	 public ModuleManager(){
		this.modules.add(new ModClickGui());
		this.modules.add(new ModuleSprint());
		this.modules.add(new Spammer());
		this.modules.add(new ModFly());
		this.modules.add(new Nofall());
		this.modules.add(new OutlineEsp());
		this.modules.add(new HighJump());
		this.modules.add(new ItemEsp());
		this.modules.add(new ModKillAura());
		this.modules.add(new BetterHit());
		this.modules.add(new InvCleaner());
		this.modules.add(new GommeFly());
		this.modules.add(new Scaffoldhelper());
		this.modules.add(new Streammode());
		this.modules.add(new LazyAir());
		this.modules.add(new NoPitchLimit());
		this.modules.add(new NoSwing());
		this.modules.add(new WaterSpeed());
		this.modules.add(new LongJump());
		this.modules.add(new InventoryMove());
		this.modules.add(new FastFall());
		this.modules.add(new SafeWalk());
		this.modules.add(new BedFucker());
		this.modules.add(new FastLadder());
		this.modules.add(new Jetpack());
		this.modules.add(new Velocity());
		this.modules.add(new ScaffoldWalkAAC());
		this.modules.add(new Tower());
		this.modules.add(new Jesus());
		this.modules.add(new ChestStealer());
		this.modules.add(new AutoArmor());
		this.modules.add(new FastPlace());
		this.modules.add(new RewiTP());
		this.modules.add(new FastUse());
		this.modules.add(new AutoWalk());
		this.modules.add(new Zoot());
		this.modules.add(new HurtCam());
		this.modules.add(new Chams());
		this.modules.add(new FastStairs());
		this.modules.add(new Speed());
		this.modules.add(new Spider());
		this.modules.add(new Step());
		this.modules.add(new WallSpeed());
		this.modules.add(new AutoRespawn());
		this.modules.add(new FastBridge());
		this.modules.add(new FullBright());
		this.modules.add(new NoSlowdown());
		this.modules.add(new TntBlock());
		this.modules.add(new NoBob());
		this.modules.add(new Coming());
		this.modules.add(new AutoSettings());
		this.modules.add(new LJHypixel());
		this.modules.add(new Rec());
		this.modules.add(new HUD());
		this.modules.add(new Teams());
			 // Sorting
	 Collections.sort(modules, new Comparator<Module>() {
	  @Override
	  public int compare(Module m1, Module m2) {

	   return Integer.valueOf(Minecraft.getMinecraft().fontRendererObj.getStringWidth(m2.getName())).compareTo(
	     Integer.valueOf(Minecraft.getMinecraft().fontRendererObj.getStringWidth(m1.getName())));
	  }

	 });
	 
	 }

	 public void setup() {
	 }

	 public void addModule(Module module) {
	  this.modules.add(module);

	 }

	 public static List<Module> getModules() {
	  return modules;
	 }

	 public static Module getModuleByName(String moduleName) {
	  for (Module mod : modules) {
	   if ((mod.getName().trim().equalsIgnoreCase(moduleName))
	     || (mod.toString().trim().equalsIgnoreCase(moduleName
	       .trim()))) {
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

	

}
