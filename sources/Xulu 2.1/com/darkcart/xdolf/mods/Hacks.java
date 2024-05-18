package com.darkcart.xdolf.mods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.mods.aura.AntiVelocity;
import com.darkcart.xdolf.mods.aura.AuraMob;
import com.darkcart.xdolf.mods.aura.AuraPlayer;
import com.darkcart.xdolf.mods.aura.AutoArmor;
import com.darkcart.xdolf.mods.aura.AutoLog;
import com.darkcart.xdolf.mods.aura.AutoTotem;
import com.darkcart.xdolf.mods.aura.Criticals;
import com.darkcart.xdolf.mods.aura.CrystalAura;
import com.darkcart.xdolf.mods.aura.KillAura;
import com.darkcart.xdolf.mods.aura.noDelay;
import com.darkcart.xdolf.mods.movement.AutoWalk;
import com.darkcart.xdolf.mods.movement.EntitySpeed;
import com.darkcart.xdolf.mods.movement.Flight;
import com.darkcart.xdolf.mods.movement.NoSlowdown;
import com.darkcart.xdolf.mods.movement.SafeWalk;
import com.darkcart.xdolf.mods.movement.SkipPhase;
import com.darkcart.xdolf.mods.movement.SlowMotion;
import com.darkcart.xdolf.mods.movement.Sprint;
import com.darkcart.xdolf.mods.movement.entityStep;
import com.darkcart.xdolf.mods.movement.packetFly;
import com.darkcart.xdolf.mods.movement.yPort;
import com.darkcart.xdolf.mods.player.AntiHunger;
import com.darkcart.xdolf.mods.player.AutoEat;
import com.darkcart.xdolf.mods.player.AutoRespawn;
import com.darkcart.xdolf.mods.player.ElytraPlus;
import com.darkcart.xdolf.mods.player.HorseJump;
import com.darkcart.xdolf.mods.player.Insulter;
import com.darkcart.xdolf.mods.player.Jesus;
import com.darkcart.xdolf.mods.player.NoFall;
import com.darkcart.xdolf.mods.player.Spammer;
import com.darkcart.xdolf.mods.player.SpeedMine;
import com.darkcart.xdolf.mods.player.XuluAC;
import com.darkcart.xdolf.mods.player.didYouKnow;
import com.darkcart.xdolf.mods.player.dropDupe;
import com.darkcart.xdolf.mods.player.enderDupe;
import com.darkcart.xdolf.mods.player.fakeDead;
import com.darkcart.xdolf.mods.player.haste;
import com.darkcart.xdolf.mods.player.serverCrasher;
import com.darkcart.xdolf.mods.player.serverJoin;
import com.darkcart.xdolf.mods.player.vClip;
import com.darkcart.xdolf.mods.render.AntiTotemAnimation;
import com.darkcart.xdolf.mods.render.Breadcrumb;
import com.darkcart.xdolf.mods.render.Chams;
import com.darkcart.xdolf.mods.render.EntityESP;
import com.darkcart.xdolf.mods.render.HitSpheres;
import com.darkcart.xdolf.mods.render.ItemESP;
import com.darkcart.xdolf.mods.render.Nametags;
import com.darkcart.xdolf.mods.render.NoHurtCam;
import com.darkcart.xdolf.mods.render.NoPumpkinBlur;
import com.darkcart.xdolf.mods.render.OutlineESP;
import com.darkcart.xdolf.mods.render.PlayerESP;
import com.darkcart.xdolf.mods.render.StorageESP;
import com.darkcart.xdolf.mods.render.Tracers;
import com.darkcart.xdolf.mods.render.Trajectories;
import com.darkcart.xdolf.mods.render.Waypoints;
import com.darkcart.xdolf.mods.render.antiBorder;
import com.darkcart.xdolf.mods.render.hudCoords;
import com.darkcart.xdolf.mods.render.rainbowClickGui;
import com.darkcart.xdolf.mods.world.BoatFly;
import com.darkcart.xdolf.mods.world.FastPlace;
import com.darkcart.xdolf.mods.world.Freecam;
import com.darkcart.xdolf.mods.world.Fullbright;
import com.darkcart.xdolf.mods.world.Timer;
import com.darkcart.xdolf.mods.world.XRay;
import com.darkcart.xdolf.mods.world.dayTime;
import com.darkcart.xdolf.mods.world.nightTime;

public class Hacks
{
	public static ArrayList<Module> display = new ArrayList<Module>();
	
	/** All mods stored in this list **/
	public static List<Module> hackList = Arrays.asList(new Module[] {
			new Fullbright(),
			new Tracers(),
			new StorageESP(),
			new EntityESP(),
			new NoHurtCam(),
			new AntiVelocity(),
			new Flight(),
			new Spammer(),
			new Timer(),
			new NoPumpkinBlur(),
			new XRay(),
			new NoFall(),
			new KillAura(),
			new AutoRespawn(),
			new AutoArmor(),
			new AutoWalk(),
			new Chams(),
			new AuraMob(),
			new AuraPlayer(),
			new GUI(),
			new AntiTotemAnimation(),
			new SafeWalk(),
			new AutoLog(),
			new ItemESP(),
			new NoSlowdown(),
			new Breadcrumb(),
			new Waypoints(),
			new FastPlace(),
			new AutoTotem(),
			new HorseJump(),
			new Sprint(),
			new Trajectories(),
			new HitSpheres(),
			new CrystalAura(),
			new Freecam(),
			new PlayerESP(),
			new Nametags(),
			new Criticals(),
			new SpeedMine(),
			new AntiHunger(),
			new ElytraPlus(),
			new OutlineESP(),
			new BoatFly(),
			//new ElytraFlyNew(),
			new Insulter(),
			new SlowMotion(),
			//new xuluSpam(),
			new fakeDead(),
			new dropDupe(),
			new nightTime(),
			new dayTime(),
			new didYouKnow(),	
			new Jesus(),
			new AutoEat(),
			new EntitySpeed(),
			new antiBorder(),
			new enderDupe(),
			new serverJoin(),
			new noDelay(),
			new vClip(),
            /**No longer being used.
             * new oldCrystalAura(),
             */
			new rainbowClickGui(),
			//new Greeter(),
			new yPort(),
			new haste(),
			//new phase(),
			new serverCrasher(),
			new packetFly(),
			new hudCoords(),
			new XuluAC(),
			new SkipPhase(),
		new entityStep(),
			//new nameProtect(),
	});

	public static Module[] getEnabledHacks()
	{
		ArrayList<Module> enabledMods = new ArrayList<Module>();
		for(Module mod: hackList)
		{
			if(mod.isEnabled()) 
			{
				enabledMods.add(mod);
			}
		}
		
		return enabledMods.toArray(new Module[enabledMods.size()]);
	}
	
	public static Module getModByClassName(String name)
	{
		for(Module mod: hackList) 
		{
			if(mod.getClass().getSimpleName().toLowerCase().trim().equals(name.toLowerCase().trim()))
			{
				return mod;
			}
		}
		
		return null;
	}
	
	public static Module getModByName(String name) 
	{
		for(Module mod: hackList)
		{
			if(mod.getName().trim().equalsIgnoreCase(name.trim()) || mod.toString().trim().equalsIgnoreCase(name.trim())) 
			{
				return mod;
			}
		}
		
		return null;
	}
	
	public static <T extends Module> T findMod(Class<T> clazz)
	{
		for(Module mod: hackList)
		{
			if(mod.getClass() == clazz)
			{
				return clazz.cast(mod);
			}
		}
		
		return null;
	}
	
	public static Module findMod(String name)
	{
		Module mod = getModByName(name);
		if(mod != null) 
		{
			return mod;
		}
		mod = getModByClassName(name);
		
		return mod;
	}
}