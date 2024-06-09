package me.hexxed.mercury.modulebase;

import java.util.ArrayList;
import java.util.Arrays;
import me.hexxed.mercury.modules.AutoArmor;
import me.hexxed.mercury.modules.ChestSteal;
import me.hexxed.mercury.modules.Ghost;
import me.hexxed.mercury.modules.Headless;
import me.hexxed.mercury.modules.Jesus;
import me.hexxed.mercury.modules.Killaura;
import me.hexxed.mercury.modules.Nameprotect;
import me.hexxed.mercury.modules.Nametags;
import me.hexxed.mercury.modules.SlimeFly;
import me.hexxed.mercury.modules.Translator;
import me.hexxed.mercury.modules.Xray;
import me.hexxed.mercury.modules.tabgui.Tabup;

public class ModuleManager
{
  public static java.util.List<Module> moduleList = Arrays.asList(new Module[] {
    new me.hexxed.mercury.modules.NoCheat(), 
    new me.hexxed.mercury.modules.Vanilla(), 
    new Nameprotect(), 
    new me.hexxed.mercury.modules.Fly(), 
    new me.hexxed.mercury.modules.NoFall(), 
    new me.hexxed.mercury.modules.Step(), 
    new me.hexxed.mercury.modules.Sprint(), 
    new me.hexxed.mercury.modules.Multijump(), 
    new me.hexxed.mercury.modules.God(), 
    new me.hexxed.mercury.modules.Glide(), 
    new me.hexxed.mercury.modules.Phase(), 
    new me.hexxed.mercury.modules.Fullbright(), 
    new me.hexxed.mercury.modules.Nuker(), 
    new SlimeFly(), 
    new me.hexxed.mercury.modules.Swift(), 
    new me.hexxed.mercury.modules.Sneak(), 
    new me.hexxed.mercury.modules.Giant(), 
    new me.hexxed.mercury.modules.Parkour(), 
    new me.hexxed.mercury.modules.Ladders(), 
    new me.hexxed.mercury.modules.Blink(), 
    new me.hexxed.mercury.modules.Highjump(), 
    new me.hexxed.mercury.modules.Velocity(), 
    new Jesus(), 
    new Ghost(), 
    new me.hexxed.mercury.modules.Waterfall(), 
    new Killaura(), 
    new me.hexxed.mercury.modules.Criticals(), 
    new me.hexxed.mercury.modules.FastUse(), 
    new me.hexxed.mercury.modules.MoreInventory(), 
    new me.hexxed.mercury.modules.Tracers(), 
    new me.hexxed.mercury.modules.Paralyze(), 
    new me.hexxed.mercury.modules.Zoot(), 
    new Headless(), 
    new Nametags(), 
    new me.hexxed.mercury.modules.Warp(), 
    new me.hexxed.mercury.modules.Fastbreak(), 
    new me.hexxed.mercury.modules.AutoSoup(), 
    new me.hexxed.mercury.modules.Instant(), 
    new me.hexxed.mercury.modules.NoSlow(), 
    new Xray(), 
    new me.hexxed.mercury.modules.ACC(), 
    new me.hexxed.mercury.modules.Freecam(), 
    new me.hexxed.mercury.modules.CClimb(), 
    new me.hexxed.mercury.modules.Latematt(), 
    new me.hexxed.mercury.modules.Civbreak(), 
    new me.hexxed.mercury.modules.Vclip(), 
    new me.hexxed.mercury.modules.CreativeCrash(), 
    new me.hexxed.mercury.modules.Safewalk(), 
    new me.hexxed.mercury.modules.AutoJump(), 
    new Translator(), 
    new me.hexxed.mercury.modules.SpookySkin(), 
    new me.hexxed.mercury.modules.FileSpammer(), 
    new me.hexxed.mercury.commands.Stream(), 
    new me.hexxed.mercury.modules.StorageESP(), 
    new ChestSteal(), 
    new AutoArmor(), 
    new me.hexxed.mercury.modules.AutoPot(), 
    new me.hexxed.mercury.modules.CTFSpammer(), 
    new me.hexxed.mercury.modules.Revive(), 
    new me.hexxed.mercury.modules.NoRotate(), 
    
    new me.hexxed.mercury.modules.BedDestroyer(), 
    new me.hexxed.mercury.modules.NoHurtcam(), 
    new me.hexxed.mercury.modules.NofallBypass(), 
    new me.hexxed.mercury.modules.Triggerbot(), 
    new me.hexxed.mercury.modules.AimAssist(), 
    new Tabup(), 
    new me.hexxed.mercury.modules.tabgui.Tabdown(), 
    new me.hexxed.mercury.modules.tabgui.Tabright(), 
    new me.hexxed.mercury.modules.tabgui.Tableft(), 
    new me.hexxed.mercury.modules.tabgui.Tabenter() });
  
  public ModuleManager() {}
  
  public static Module[] getEnabledHacks() { ArrayList<Module> enabledMods = new ArrayList();
    for (Module mod : moduleList) {
      if (mod.isEnabled()) {
        enabledMods.add(mod);
      }
    }
    return (Module[])enabledMods.toArray(new Module[enabledMods.size()]);
  }
  
  public static Module getModByClassName(String name) {
    for (Module mod : moduleList) {
      if (mod.getClass().getSimpleName().toLowerCase().trim().equals(name.toLowerCase().trim())) {
        return mod;
      }
    }
    
    return null;
  }
  
  public static Module getModByName(String name) {
    for (Module mod : moduleList) {
      if ((mod.getModuleName().trim().equalsIgnoreCase(name.trim())) || (mod.toString().trim().equalsIgnoreCase(name.trim()))) {
        return mod;
      }
    }
    
    return null;
  }
  
  public static Module findMod(Class<? extends Module> clazz)
  {
    for (Module mod : moduleList) {
      if (mod.getClass() != clazz) {
        return mod;
      }
    }
    
    return null;
  }
  
  public static Module findMod(String name)
  {
    Module mod = getModByName(name);
    if (mod != null) {
      return mod;
    }
    mod = getModByClassName(name);
    
    return mod;
  }
  
  public static boolean isAntiCheatOn() {
    return getModByName("NoCheat").isEnabled();
  }
}
