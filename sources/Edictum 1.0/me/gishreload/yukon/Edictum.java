package me.gishreload.yukon;

import java.util.ArrayList;
import java.util.List;

import org.darkstorm.minecraft.gui.theme.simple.SimpleTheme;
import org.lwjgl.opengl.Display;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.*;
import net.minecraft.util.*;
import net.minecraft.util.text.TextComponentTranslation;
import me.gishreload.edictum.gui.tab.TabManager;
import me.gishreload.yukon.command.CommandManager;
import me.gishreload.yukon.gui.WatermarkRenderer;
import me.gishreload.yukon.gui.YukonGuiManager;
import me.gishreload.yukon.hacks.AAC;
import me.gishreload.yukon.hacks.AACJesus;
import me.gishreload.yukon.hacks.AACNoEmptiness;
import me.gishreload.yukon.hacks.AACSpeed;
import me.gishreload.yukon.hacks.Aimbot;
import me.gishreload.yukon.hacks.AntiFire;
import me.gishreload.yukon.hacks.AntiPotion;
import me.gishreload.yukon.hacks.ArrowESP;
import me.gishreload.yukon.hacks.AutoArmor;
import me.gishreload.yukon.hacks.BedTest;
import me.gishreload.yukon.hacks.KillAura;
import me.gishreload.yukon.hacks.LeftClick;
import me.gishreload.yukon.hacks.BoatFlight;
import me.gishreload.yukon.hacks.ChestESP;
import me.gishreload.yukon.hacks.ChestStealer;
import me.gishreload.yukon.hacks.ClickGui;
import me.gishreload.yukon.hacks.CrashNameTag;
import me.gishreload.yukon.hacks.Criticals;
import me.gishreload.yukon.hacks.FPS;
import me.gishreload.yukon.hacks.FastBow;
import me.gishreload.yukon.hacks.Flight;
import me.gishreload.yukon.hacks.Freecam;
import me.gishreload.yukon.hacks.Glowing;
import me.gishreload.yukon.hacks.IceSpeed;
import me.gishreload.yukon.hacks.InvertoryMove;
import me.gishreload.yukon.hacks.ItemESP;
import me.gishreload.yukon.hacks.JumpCrits;
import me.gishreload.yukon.hacks.Light;
import me.gishreload.yukon.hacks.MobAura;
import me.gishreload.yukon.hacks.MobESP;
import me.gishreload.yukon.hacks.NCP;
import me.gishreload.yukon.hacks.NoScoreboard;
import me.gishreload.yukon.hacks.NoSlowDown;
import me.gishreload.yukon.hacks.NoVelocity;
import me.gishreload.yukon.hacks.Nofall;
import me.gishreload.yukon.hacks.PlayerESP;
import me.gishreload.yukon.hacks.Radar;
import me.gishreload.yukon.hacks.Reflex;
import me.gishreload.yukon.hacks.Regen;
import me.gishreload.yukon.hacks.RightClick;
import me.gishreload.yukon.hacks.Scaffold;
import me.gishreload.yukon.hacks.SpeedMine;
import me.gishreload.yukon.hacks.Sprint;
import me.gishreload.yukon.hacks.Step;
import me.gishreload.yukon.hacks.Teleport;
import me.gishreload.yukon.hacks.TickRegen;
import me.gishreload.yukon.hacks.Tracers;
import me.gishreload.yukon.hacks.UpdateListAura;
import me.gishreload.yukon.hacks.Xray;
import me.gishreload.yukon.module.Module;
import me.gishreload.yukon.utils.XrayUtils;

public class Edictum {
	
	public static final String NAME = "Edictum";
    public static final double VERSION = 1.0;
	private static ArrayList<Module> mods;
	private static CommandManager cmdManager;
	public static YukonGuiManager guiManager;
	public static TabManager tabManager;
	
	public Edictum(){
		mods = new ArrayList<Module>();
		cmdManager = new CommandManager();
		tabManager = new TabManager();
		XrayUtils.initXRayBlocks();
		addMod(new Flight());
		addMod(new Light());
		addMod(new Glowing());
		addMod(new KillAura());
		addMod(new MobAura());
		addMod(new Freecam());
		addMod(new Step());
		addMod(new Xray());
		addMod(new IceSpeed());
		addMod(new SpeedMine());
		addMod(new Sprint());
		addMod(new Nofall());
		addMod(new BoatFlight());
		addMod(new Criticals());
		addMod(new JumpCrits());
		addMod(new AutoArmor());
		addMod(new AntiFire());
		addMod(new Aimbot());
		addMod(new AntiPotion());
		addMod(new Regen());
		addMod(new TickRegen());
		addMod(new Radar());
		addMod(new FPS());
		addMod(new NoSlowDown());
		addMod(new NoVelocity());
		addMod(new ChestESP());
		addMod(new ItemESP());
		addMod(new Tracers());
		addMod(new MobESP());
		addMod(new FastBow());
		addMod(new ChestStealer());
		addMod(new PlayerESP());
		addMod(new ArrowESP());
		addMod(new NoScoreboard());
		addMod(new InvertoryMove());
		addMod(new Scaffold());
		addMod(new CrashNameTag());
		addMod(new RightClick());
		addMod(new LeftClick());
		addMod(new Teleport());
		addMod(new BedTest());
		addMod(new AACJesus());
		addMod(new AACSpeed());
		addMod(new AACNoEmptiness());
		addMod(new UpdateListAura());
		addMod(new AAC());
		addMod(new Reflex());
		addMod(new NCP());
		addMod(new ClickGui());
		guiManager = new YukonGuiManager();
		guiManager.setTheme(new SimpleTheme());
		guiManager.setup();
	}
	public static void addMod(Module m){
		mods.add(m);
	}
	
	public static ArrayList<Module> getModules(){
		return mods;
	}
	
	public static void onUpdate(){
		for(Module m: mods){
			m.onUpdate();
		}
	}

	public static void onRender(){
		for(Module m: mods){
			m.onRender();
		}
	}
	
	public static void onKeyPressed(int i){
		for(Module m: mods){
			if(m.getKey() == i){
				m.toggle();
			}
		}
	}

	public static void addChatMessage(String s){
		Minecraft.getMinecraft().thePlayer.addChatMessage(new TextComponentTranslation("" + s));
	}
	
	public static boolean onSendChatMessage(String s){
		if(s.startsWith(".")){
			cmdManager.callCommand(s.substring(1));
			return false;
		}
		for(Module m: getModules()){
			if(m.isToggled()){
				return m.onSendChatMessage(s);
			}
		}
		return true;
	}
	
	public static boolean onRecieveChatMessage(SPacketChat packet){
		for(Module m: getModules()){
			if(m.isToggled()){
				return m.onRecieveChatMessage(packet);
			}
		}
		return true;
	}
}

