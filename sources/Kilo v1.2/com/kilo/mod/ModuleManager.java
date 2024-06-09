package com.kilo.mod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.kilo.Kilo;
import com.kilo.mod.all.Aimbot;
import com.kilo.mod.all.AntiAFK;
import com.kilo.mod.all.AntiBurn;
import com.kilo.mod.all.AutoArmor;
import com.kilo.mod.all.AutoBuild;
import com.kilo.mod.all.AutoDisconnect;
import com.kilo.mod.all.AutoJump;
import com.kilo.mod.all.AutoMine;
import com.kilo.mod.all.AutoPotion;
import com.kilo.mod.all.AutoRespawn;
import com.kilo.mod.all.AutoSoup;
import com.kilo.mod.all.AutoSwitch;
import com.kilo.mod.all.AutoTool;
import com.kilo.mod.all.AutoWalk;
import com.kilo.mod.all.AutoWeapon;
import com.kilo.mod.all.BlankSign;
import com.kilo.mod.all.Blink;
import com.kilo.mod.all.BowDamage;
import com.kilo.mod.all.Breadcrumbs;
import com.kilo.mod.all.BunnyHop;
import com.kilo.mod.all.CameraNoClip;
import com.kilo.mod.all.ChatSpam;
import com.kilo.mod.all.Cleanse;
import com.kilo.mod.all.Commands;
import com.kilo.mod.all.Creatives;
import com.kilo.mod.all.Damage;
import com.kilo.mod.all.Dolphin;
import com.kilo.mod.all.ESP;
import com.kilo.mod.all.FastBow;
import com.kilo.mod.all.FastEat;
import com.kilo.mod.all.FastFall;
import com.kilo.mod.all.FastLadder;
import com.kilo.mod.all.FastPlace;
import com.kilo.mod.all.FightBot;
import com.kilo.mod.all.Flip;
import com.kilo.mod.all.Fly;
import com.kilo.mod.all.Follow;
import com.kilo.mod.all.Freecam;
import com.kilo.mod.all.Friends;
import com.kilo.mod.all.Gamma;
import com.kilo.mod.all.Glide;
import com.kilo.mod.all.HighJump;
import com.kilo.mod.all.History;
import com.kilo.mod.all.HorseJump;
import com.kilo.mod.all.InventoryWalk;
import com.kilo.mod.all.Jetpack;
import com.kilo.mod.all.KillAura;
import com.kilo.mod.all.KnockbackMultiplier;
import com.kilo.mod.all.LSD;
import com.kilo.mod.all.Lean;
import com.kilo.mod.all.LiquidVision;
import com.kilo.mod.all.Macros;
import com.kilo.mod.all.MassTPA;
import com.kilo.mod.all.Nametags;
import com.kilo.mod.all.NoFall;
import com.kilo.mod.all.NoHurtCam;
import com.kilo.mod.all.NoKnockback;
import com.kilo.mod.all.NoOverlay;
import com.kilo.mod.all.NoSlowDown;
import com.kilo.mod.all.NoSwing;
import com.kilo.mod.all.NoViewReset;
import com.kilo.mod.all.NoWeather;
import com.kilo.mod.all.Nuker;
import com.kilo.mod.all.Panic;
import com.kilo.mod.all.Parkour;
import com.kilo.mod.all.Phase;
import com.kilo.mod.all.PlayerSettings;
import com.kilo.mod.all.RainbowEnchant;
import com.kilo.mod.all.RapidJump;
import com.kilo.mod.all.Regen;
import com.kilo.mod.all.Retard;
import com.kilo.mod.all.SafeWalk;
import com.kilo.mod.all.SelectionBox;
import com.kilo.mod.all.Sneak;
import com.kilo.mod.all.Speed;
import com.kilo.mod.all.SpeedMine;
import com.kilo.mod.all.Spider;
import com.kilo.mod.all.Sprint;
import com.kilo.mod.all.StatusHUD;
import com.kilo.mod.all.Step;
import com.kilo.mod.all.StorageESP;
import com.kilo.mod.all.TeleportAccept;
import com.kilo.mod.all.Timer;
import com.kilo.mod.all.Tracer;
import com.kilo.mod.all.Trajectories;
import com.kilo.mod.all.TriggerBot;
import com.kilo.mod.all.TrueVision;
import com.kilo.mod.all.UnderwaterWalk;
import com.kilo.mod.all.WallHack;
import com.kilo.mod.all.Waypoints;
import com.kilo.mod.all.WorldEditESP;
import com.kilo.mod.all.WorldTime;
import com.kilo.mod.all.Xray;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.util.Util;

public class ModuleManager {
	private static List<Module> modules = new CopyOnWriteArrayList<Module>();
	private static List<Module> wasEnabled = new ArrayList<Module>();
	private static List<Module> enabledModules = new CopyOnWriteArrayList<Module>();
	private static String[] blackList = new String[] {"blink", "freecam", "paramode"};
	
	public static void updateHacks() {
		modules.clear();
		enabledModules.clear();
		
		/******************* BUILD *******************/
		modules.add(new AutoBuild("autobuild", Category.BUILD, "Auto Build", "Automatically build structures", ""));
		modules.add(new AutoMine("automine", Category.BUILD, "Auto Mine", "Automatically mine when looking at a block", ""));
		modules.add(new AutoTool("autotool", Category.BUILD, "Auto Tool", "Automatically switch to the most suitable tool in your hotbar", ""));
		modules.add(new BlankSign("blanksign", Category.BUILD, "Blank Sign", "Place empty signs faster", ""));
		modules.add(new FastPlace("fastplace", Category.BUILD, "Fast Place", "Remove the delay between placing blocks", ""));
		modules.add(new SpeedMine("speedmine", Category.BUILD, "Speed Mine", "Break blocks faster", ""));
		modules.add(new Nuker("nuker", Category.BUILD, "Nuker", "Destroy blocks around you", ""));

		/******************* COMBAT *******************/
		modules.add(new Aimbot("aimbot", Category.COMBAT, "Aimbot", "Face entities around you", ""));
		modules.add(new AutoArmor("autoarmor", Category.COMBAT, "Auto Armor", "Put on the best armor in your inventory", ""));
		modules.add(new AutoDisconnect("autodisconnect", Category.COMBAT, "Auto Disconnect", "Disconnect from the current server/world when your health is low", ""));
		modules.add(new AutoPotion("autopotion", Category.COMBAT, "Auto Potion", "Use splash healing potions when your health is low", ""));
		modules.add(new AutoSoup("autosoup", Category.COMBAT, "Auto Soup", "Eat soups when your health is low|Requires a soup regen plugin", ""));
		modules.add(new AutoWeapon("autoweapon", Category.COMBAT, "Auto Weapon", "Automatically switch to the best weapon on your hotbar", ""));
		modules.add(new FastBow("fastbow", Category.COMBAT, "Fast Bow", "Instantly shoot your bow", ""));
		modules.add(new FightBot("fightbot", Category.COMBAT, "FightBot", "Automatically kill entities", ""));
		modules.add(new KillAura("killaura", Category.COMBAT, "Kill Aura", "Attack entities around you", ""));
		modules.add(new KnockbackMultiplier("knockbackmultiplier", Category.COMBAT, "Knockback Multiplier", "Change the knockback distance", ""));
		modules.add(new NoKnockback("noknockback", Category.COMBAT, "No Knockback", "Disable attack damage knockback", ""));
		modules.add(new NoHurtCam("nohurtcam", Category.COMBAT, "No Hurt Cam", "Disable view from shaking when hurt", ""));
		modules.add(new Regen("regen", Category.COMBAT, "Regen", "Regenerate hearts faster", ""));
		modules.add(new TriggerBot("triggerbot", Category.COMBAT, "Trigger Bot", "Hit entities when looking at them", ""));
		
		/******************* DISPLAY *******************/
		modules.add(new Breadcrumbs("breadcrumbs", Category.DISPLAY, "Breadcrumbs", "Draw a trail of your previous steps", ""));
		modules.add(new ESP("esp", Category.DISPLAY, "ESP", "Draw boxes around entities", ""));
		modules.add(new Gamma("gamma", Category.DISPLAY, "Gamma", "Change the maximum gamma value", ""));
		modules.add(new LiquidVision("liquidvision", Category.DISPLAY, "Liquid Vision", "Allow higher visibility in water and lava", ""));
		modules.add(new LSD("lsd", Category.DISPLAY, "LSD", "See the world in a whole new way", ""));
		modules.add(new Nametags("nametags", Category.DISPLAY, "Nametags", "Show nametags through blocks", ""));
		modules.add(new NoOverlay("nooverlay", Category.DISPLAY, "No Overlay", "Disable all view overlays", ""));
		modules.add(new NoWeather("noweather", Category.DISPLAY, "No Weather", "Disable rain and snow particles", ""));
		modules.add(new SelectionBox("selectionbox", Category.DISPLAY, "Selection Box", "Stylize the block you're looking at", ""));
		modules.add(new StatusHUD("statushud", Category.DISPLAY, "Status HUD", "Draw armor durability and potion effects", ""));
		modules.add(new StorageESP("storageesp", Category.DISPLAY, "Storage ESP", "Draw boxes around storages", ""));
		modules.add(new Tracer("tracer", Category.DISPLAY, "Tracer", "Draw lines to entities", ""));
		modules.add(new Trajectories("trajectories", Category.DISPLAY, "Trajectories", "Draw trajectory lines for projectiles", ""));
		modules.add(new TrueVision("truevision", Category.DISPLAY, "True Vision", "See invisible entities", ""));
		modules.add(new WallHack("wallhack", Category.DISPLAY, "Wall Hack", "Draw entities through walls", ""));
		modules.add(new WorldEditESP("worldeditesp", Category.DISPLAY, "WorldEdit ESP", "Draw a selection box around the WorldEdit selection", ""));
		modules.add(new Xray("xray", Category.DISPLAY, "Xray", "Show only selected blocks", ""));

		/******************* MISC *******************/
		modules.add(new CameraNoClip("cameranoclip", Category.MISC, "Camera NoClip", "Set thirdperson camera into noclip mode", ""));
		modules.add(new ChatSpam("chatspam", Category.MISC, "Chat Spam", "Spam the chat with supplied messages", ""));
		modules.add(new Creatives("creatives", Category.MISC, "Creatives", "Have fun when in creative mode", ""));
		
		modules.add(new History("history", Category.MISC, "History", "View a players username history", ""));
		modules.add(new InventoryWalk("inventorywalk", Category.MISC, "Inventory Walk", "Move freely when using your inventory", ""));
		modules.add(new Lean("lean", Category.MISC, "Lean", "Simulate leaning to the left and right using the arrow keys", ""));
		modules.add(new MassTPA("masstpa", Category.MISC, "Mass TPA", "Send a \"/tpa\" request to all players", ""));
		modules.add(new Macros("macros", Category.MISC, "Macros", "Bind commands to keys", ""));
		modules.add(new Panic("panic", Category.MISC, "Panic", "Disable all hacks", ""));
		modules.add(new PlayerSettings("playersettings", Category.MISC, "Player Settings", "Change how your player looks", ""));
		modules.add(new RainbowEnchant("rainbowenchant", Category.MISC, "Rainbow Enchant", "Change the enchant glint color", ""));
		modules.add(new Waypoints("waypoints", Category.MISC, "Waypoints", "Show waypoints around the world", ""));
		modules.add(new WorldTime("worldtime", Category.MISC, "World Time", "Change the world time", ""));

		/******************* MOVEMENT *******************/
		modules.add(new AutoJump("autojump", Category.MOVEMENT, "Auto Jump", "Automatically jump", ""));
		modules.add(new AutoWalk("autowalk", Category.MOVEMENT, "Auto Walk", "Automatically walk", ""));
		modules.add(new BunnyHop("bunnyhop", Category.MOVEMENT, "Bunny Hop", "Automatically jump when sprinting", ""));
		modules.add(new Dolphin("dolphin", Category.MOVEMENT, "Dolphin", "Always stay on top of water like a dolphin", ""));
		modules.add(new FastFall("fastfall", Category.MOVEMENT, "Fast Fall", "Fall instantly", ""));
		modules.add(new FastLadder("fastladder", Category.MOVEMENT, "Fast Ladder", "Climb ladders faster", ""));
		modules.add(new Fly("fly", Category.MOVEMENT, "Fly", "Enable flying", ""));
		modules.add(new Follow("follow", Category.MOVEMENT, "Follow", "Follow the selected player", ""));
		modules.add(new Glide("glide", Category.MOVEMENT, "Glide", "Prevent falling fast", ""));
		modules.add(new HighJump("highjump", Category.MOVEMENT, "High Jump", "Increase your jump height", ""));
		modules.add(new HorseJump("horsejump", Category.MOVEMENT, "Horse Jump", "Automatically jump at the highest peak on horses", ""));
		modules.add(new Jetpack("jetpack", Category.MOVEMENT, "Jetpack", "Fly using a jetpack", ""));
		modules.add(new NoFall("nofall", Category.MOVEMENT, "No Fall", "Prevent getting fall damage", ""));
		modules.add(new Parkour("parkour", Category.MOVEMENT, "Parkour", "Automatically jump from the edge of blocks", ""));
		modules.add(new Phase("phase", Category.MOVEMENT, "Phase", "Phase through non-solid blocks", ""));
		modules.add(new RapidJump("rapidjump", Category.MOVEMENT, "Rapid Jump", "Disable the jump delay", ""));
		modules.add(new SafeWalk("safewalk", Category.MOVEMENT, "Safe Walk", "Prevent walking off the edge of blocks", ""));
		modules.add(new Speed("speed", Category.MOVEMENT, "Speed", "Change movement speed", ""));
		modules.add(new Spider("spider", Category.MOVEMENT, "Spider", "Climb walls like a spider", ""));
		modules.add(new Sprint("sprint", Category.MOVEMENT, "Sprint", "Automatically sprint when moving", ""));
		modules.add(new Step("step", Category.MOVEMENT, "Step", "Step up blocks like slabs", ""));
		modules.add(new Timer("timer", Category.MOVEMENT, "Timer", "Increase game speed", ""));
		modules.add(new UnderwaterWalk("underwaterwalk", Category.MOVEMENT, "Underwater Walk", "Remove water friction", ""));

		/******************* PLAYER *******************/
		modules.add(new AntiAFK("antiafk", Category.PLAYER, "Anti AFK", "Prevent you from appearing AFK", ""));
		modules.add(new AntiBurn("antiburn", Category.PLAYER, "Anti Burn", "Prevent you from burning", ""));
		modules.add(new AutoRespawn("autorespawn", Category.PLAYER, "Auto Respawn", "Automatically respawn when you die", ""));
		modules.add(new AutoSwitch("autoswitch", Category.PLAYER, "Auto Switch", "Automatically switch between items on your hotbar", ""));
		modules.add(new Blink("blink", Category.PLAYER, "Blink", "Make players think you're lagging", ""));
		modules.add(new BowDamage("bowdamage", Category.PLAYER, "Bow Damage", "Always shoot arrows up", ""));
		modules.add(new Cleanse("cleanse", Category.PLAYER, "Cleanse", "Remove bad potion effects", ""));
		modules.add(new Damage("damage", Category.PLAYER, "Damage", "Force damage on yourself", ""));
		modules.add(new FastEat("fasteat", Category.PLAYER, "Fast Eat", "Eat food faster", ""));
		modules.add(new Flip("flip", Category.PLAYER, "Flip", "Do a 180 degree turn", ""));
		modules.add(new Freecam("freecam", Category.PLAYER, "Freecam", "Become a ghost and fly through blocks", ""));
		modules.add(new Friends("friends", Category.PLAYER, "Friends", "Prevent targetting your friends", ""));
		modules.add(new NoSlowDown("noslowdown", Category.PLAYER, "No Slow Down", "Prevent slowing down while using items", ""));
		modules.add(new NoSwing("noswing", Category.PLAYER, "No Swing", "Disable the item swing animation", ""));
		modules.add(new NoViewReset("noviewreset", Category.PLAYER, "No View Reset", "Prevent servers from changing your view rotation", ""));
		modules.add(new Retard("retard", Category.PLAYER, "Retard", "Make yourself retarded", ""));
		modules.add(new Sneak("sneak", Category.PLAYER, "Sneak", "Automatically sneak", ""));
		modules.add(new TeleportAccept("teleportaccept", Category.PLAYER, "Teleport Accept", "Accept all \"/tpa\" requests", ""));
		
		for(Module m : modules) {
			for(ModuleOption mo : m.options) {
				if (mo.limit == null) {
					continue;
				}
				mo.value = Math.min(Math.max(mo.limit[0], Util.makeFloat(mo.value)), mo.limit[1]);
			}
		}
		
		List<String> keys = new ArrayList();
		List<Module> sorted = new ArrayList();
		for(Module m : list()) {
			keys.add(m.finder.toLowerCase());
		}
		Collections.sort(keys);
		for(String k : keys){
			sorted.add(get(k));
		}
		keys.clear();
		modules.clear();
		modules.addAll(sorted);
		sorted.clear();
	}
	
	public static void enable(Module hack) {
		if (!enabledModules.contains(hack)) {
			enabledModules.add(hack);
		}
	}
	
	public static void disable(Module hack) {
		if (enabledModules.contains(hack)) {
			enabledModules.remove(hack);
		}
	}
	
	public static void toggle(Module hack) {
		if (enabledModules.contains(hack)) {
			hack.onDisable();
		} else {
			hack.onEnable();
		}
	}
	
	public static List<Module> list() {
		return modules;
	}
	
	public static List<Module> enabledList() {
		return enabledModules;
	}
	
	public static Module get(int i) {
		return modules.get(i);
	}
	
	public static Module get(String s) {
		for(Module m : modules) {
			if (m.finder.equalsIgnoreCase(s)) {
				return m;
			}
		}
		return null;
	}
	
	/*public static Module getFromClass(Class c) {
		for(Module m : modules) {
			if (m.getClass().getName().equalsIgnoreCase(c.getName())) {
				return m;
			}
		}
		return null;
	}*/
	
	public static List<ModuleSubOption> getColorOptions() {
		List<ModuleSubOption> tl = new ArrayList<ModuleSubOption>();
		
		tl.add(new ModuleSubOption("Red", "", Interactable.TYPE.SLIDER, 0, new float[] {0, 255}, false));
		tl.add(new ModuleSubOption("Green", "", Interactable.TYPE.SLIDER, 0, new float[] {0, 255}, false));
		tl.add(new ModuleSubOption("Blue", "", Interactable.TYPE.SLIDER, 0, new float[] {0, 255}, false));
		tl.add(new ModuleSubOption("Opacity", "", Interactable.TYPE.SLIDER, 96, new float[] {0, 255}, false));

		return tl;
	}
	
	public static List<ModuleSubOption> getDistanceColorOptions() {
		List<ModuleSubOption> tl = new ArrayList<ModuleSubOption>();
		
		tl.add(new ModuleSubOption("Distance", "Change from green to red as an entity gets closer", Interactable.TYPE.CHECKBOX, false, null, false));
		tl.add(new ModuleSubOption("Red", "", Interactable.TYPE.SLIDER, 0, new float[] {0, 255}, false));
		tl.add(new ModuleSubOption("Green", "", Interactable.TYPE.SLIDER, 0, new float[] {0, 255}, false));
		tl.add(new ModuleSubOption("Blue", "", Interactable.TYPE.SLIDER, 0, new float[] {0, 255}, false));
		tl.add(new ModuleSubOption("Opacity", "", Interactable.TYPE.SLIDER, 128, new float[] {0, 255}, false));
		
		return tl;
	}
	
	public static boolean isBlackListed(Module m) {
		for(String s : blackList) {
			if (m != null && m.finder.equalsIgnoreCase(s)) {
				return true;
			}
		}
		return false;
	}
	
	public static void canHack(boolean can) {
		if (can != Kilo.kilo().canHack) {
			if (can) {
				if (!wasEnabled.isEmpty()) {
					enabledModules.clear();
					enabledModules.addAll(wasEnabled);
					wasEnabled.clear();
				}
			} else {
				wasEnabled.clear();
				wasEnabled.addAll(enabledModules);
				enabledModules.clear();
			}
			Kilo.kilo().canHack = can;
		}
	}
}
