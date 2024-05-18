package me.gishreload.edictum.gui.tab;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.input.Keyboard;

import me.gishreload.yukon.Edictum;
import me.gishreload.yukon.hacks.AAC;
import me.gishreload.yukon.hacks.AACNoEmptiness;
import me.gishreload.yukon.hacks.AACSpeed;
import me.gishreload.yukon.hacks.Aimbot;
import me.gishreload.yukon.hacks.AntiFire;
import me.gishreload.yukon.hacks.AntiPotion;
import me.gishreload.yukon.hacks.ArrowESP;
import me.gishreload.yukon.hacks.AutoArmor;
import me.gishreload.yukon.hacks.BedTest;
import me.gishreload.yukon.hacks.BoatFlight;
import me.gishreload.yukon.hacks.ChestESP;
import me.gishreload.yukon.hacks.ChestStealer;
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
import me.gishreload.yukon.hacks.KillAura;
import me.gishreload.yukon.hacks.LeftClick;
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
import me.gishreload.yukon.hacks.Tracers;
import me.gishreload.yukon.hacks.UpdateListAura;
import me.gishreload.yukon.hacks.Xray;
import me.gishreload.yukon.module.Module;


public class TabManager {

	private ArrayList<Tab> tabs;
	private int currentTab;

	private HashMap<Integer, Module> render, player, other, anticheat, combat;
	private int currentRender, currentPlayer,currentOther,currentAnticheat,currentCombat;

	public TabManager() {
		tabs = new ArrayList();
		currentTab = 0;
		combat= new HashMap();
		render = new HashMap();
		player = new HashMap();
		anticheat = new HashMap();
		other = new HashMap();
		
		currentCombat = 0;
		currentRender = 0;
		currentPlayer = 0;
		currentAnticheat = 0;
		currentOther = 0;
		
		tabs.add(new Tab1());
		tabs.add(new Tab2());
		tabs.add(new Tab3());
		tabs.add(new Tab4());
		tabs.add(new Tab5());
		
		combat.put(0, new KillAura());
		combat.put(1, new MobAura());
		combat.put(2, new Aimbot());
		combat.put(3, new Criticals());
		combat.put(4, new JumpCrits());
		combat.put(5, new FastBow());
		combat.put(6, new Regen());
		combat.put(7, new NoVelocity());
		combat.put(8, new AntiFire());
		combat.put(9, new AntiPotion());
		
		render.put(0, new ChestESP());
		render.put(1, new MobESP());
		render.put(2, new Xray());
		render.put(3, new PlayerESP());
		render.put(4, new ItemESP());
		render.put(5, new ArrowESP());
		render.put(6, new Tracers());
		render.put(7, new Light());
		render.put(8, new Glowing());
		render.put(9, new Radar());
		render.put(10, new NoScoreboard());
		
		player.put(0, new Flight());
		player.put(1, new Freecam());
		player.put(2, new Step());
		player.put(3, new IceSpeed());
		player.put(4, new SpeedMine());
		player.put(5, new Sprint());
		player.put(6, new Nofall());
		player.put(7, new BoatFlight());
		player.put(8, new AutoArmor());
		player.put(9, new NoSlowDown());
		player.put(10, new ChestStealer());
		player.put(11, new InvertoryMove());
		player.put(12, new AACSpeed());
		player.put(13, new Scaffold());
		player.put(14, new AACNoEmptiness());
		player.put(15, new Teleport());
		
		anticheat.put(0, new AAC());
		anticheat.put(1, new NCP());
		anticheat.put(2, new Reflex());
		
		other.put(0, new CrashNameTag());
		other.put(1, new RightClick());
		other.put(2, new LeftClick());
		other.put(3, new BedTest());
		other.put(4, new UpdateListAura());
		
	}
	
	public ArrayList<Tab> getTabs(){
		return tabs;
	}
	public int getCurrentCombatMod(){
		return currentCombat;
	}
	public int getCurrentRenderMod(){
		return currentRender;
	}
	public int getCurrentPlayerMod(){
		return currentPlayer;
	}
	public int getCurrentAnticheatMod(){
		return currentAnticheat;
	}
	public int getCurrentOtherMod(){
		return currentOther;
	}
	public int getCurrentTab(){
		return currentTab;
	}

	public void keyPressed(int i) {
		switch (i) {
		case Keyboard.KEY_NUMPAD8:
			if(tabs.get(currentTab).isExpanded()){
				switch(currentTab){
				case 0:
					if(currentCombat != 0){
						currentCombat--;
					}
					break;
				case 1:
					if(currentRender != 0){
						currentRender--;
					}
					break;
				case 2:
					if(currentPlayer != 0){
						currentPlayer--;
					}
					break;
				case 3:
					if(currentAnticheat != 0){
						currentAnticheat--;
					}
					break;
				case 4:
					if(currentOther != 0){
						currentOther--;
					}
					break;
				}
			}else{
				if(currentTab != 0){
					currentTab--;
				}
			}
			break;
		case Keyboard.KEY_NUMPAD5:
			if(tabs.get(currentTab).isExpanded()){
				switch(currentTab){
				case 0:
					if(currentCombat != combat.size()-1){
						currentCombat++;
					}
					break;
				case 1:
					if(currentRender != render.size()-1){
						currentRender++;
					}
					break;
				case 2:
					if(currentPlayer != player.size()-1){
						currentPlayer++;
					}
					break;
				case 3:
					if(currentAnticheat != anticheat.size()-1){
						currentAnticheat++;
					}
					break;
				case 4:
					if(currentOther != other.size()-1){
						currentOther++;
					}
					break;
				}
			}else{
				if(currentTab != 4){
					currentTab++;
				}
			}
			break;
		case Keyboard.KEY_NUMPAD6:
			if(tabs.get(currentTab).isExpanded()){
				switch(currentTab){
				case 0:
					toggleMod(combat.get(currentCombat).getName());
					break;
				case 1:
					toggleMod(render.get(currentRender).getName());
					break;
				case 2:
					toggleMod(player.get(currentPlayer).getName());
					break;
				case 3:
					toggleMod(anticheat.get(currentAnticheat).getName());
					break;
				case 4:
					toggleMod(other.get(currentOther).getName());
					break;
				}
			}else{
				tabs.get(currentTab).setExpanded(true);
			}
			break;
		case Keyboard.KEY_NUMPAD4:
			if(tabs.get(currentTab).isExpanded()){
				tabs.get(currentTab).setExpanded(false);
			}
			break;
		}
	}
	
	private void toggleMod(String n){
		for(Module m: Edictum.getModules()){
			if(m.getName().equalsIgnoreCase(n)){
				m.toggle();
				break;
			}
		}
	}

}
