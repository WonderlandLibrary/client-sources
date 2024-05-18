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
import com.darkcart.xdolf.mods.aura.CrystalLog;
import com.darkcart.xdolf.mods.aura.KillAura;
import com.darkcart.xdolf.mods.player.AutoEat;
import com.darkcart.xdolf.mods.player.AutoRespawn;
import com.darkcart.xdolf.mods.player.AutoWalk;
import com.darkcart.xdolf.mods.player.EntitySpeed;
import com.darkcart.xdolf.mods.player.EntityStep;
import com.darkcart.xdolf.mods.player.Flight;
import com.darkcart.xdolf.mods.player.HorseJump;
import com.darkcart.xdolf.mods.player.Jesus;
import com.darkcart.xdolf.mods.player.NoFall;
import com.darkcart.xdolf.mods.player.NoSlowdown;
import com.darkcart.xdolf.mods.player.SafeWalk;
import com.darkcart.xdolf.mods.player.Spammer;
import com.darkcart.xdolf.mods.player.Sprint;
import com.darkcart.xdolf.mods.render.AntiTotemAnimation;
import com.darkcart.xdolf.mods.render.Breadcrumb;
import com.darkcart.xdolf.mods.render.Chams;
import com.darkcart.xdolf.mods.render.EntityESP;
import com.darkcart.xdolf.mods.render.HitSpheres;
import com.darkcart.xdolf.mods.render.ItemESP;
import com.darkcart.xdolf.mods.render.LSD;
import com.darkcart.xdolf.mods.render.Nametags;
import com.darkcart.xdolf.mods.render.NoHurtCam;
import com.darkcart.xdolf.mods.render.NoPumpkinBlur;
import com.darkcart.xdolf.mods.render.OutlineESP;
import com.darkcart.xdolf.mods.render.PlayerESP;
import com.darkcart.xdolf.mods.render.StorageESP;
import com.darkcart.xdolf.mods.render.Tracers;
import com.darkcart.xdolf.mods.render.Trajectories;
import com.darkcart.xdolf.mods.render.Waypoints;
import com.darkcart.xdolf.mods.world.FastPlace;
import com.darkcart.xdolf.mods.world.Freecam;
import com.darkcart.xdolf.mods.world.Fullbright;
import com.darkcart.xdolf.mods.world.Speedmine;
import com.darkcart.xdolf.mods.world.Timer;
import com.darkcart.xdolf.mods.world.XRay;

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
			new NoFall(),
			new CrystalLog(),
			new AutoEat(),
			new Jesus(),
			new EntitySpeed(),
			new OutlineESP(),
			new Speedmine(),
			new EntityStep(),
			//new LSD()
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