 package me.vene.skilled.modules;
 
 import java.util.ArrayList;
 import me.vene.skilled.modules.mods.combat.AimAssist;
 import me.vene.skilled.modules.mods.combat.AutoClicker;
 import me.vene.skilled.modules.mods.combat.Delay17;
 import me.vene.skilled.modules.mods.combat.HitBoxes;
 import me.vene.skilled.modules.mods.combat.KillAura;
 import me.vene.skilled.modules.mods.combat.Reach;
 import me.vene.skilled.modules.mods.combat.Sprint;
 import me.vene.skilled.modules.mods.combat.Velocity;
 import me.vene.skilled.modules.mods.main.CombatGUI;
 import me.vene.skilled.modules.mods.main.OtherGUI;
 import me.vene.skilled.modules.mods.main.PlayerGUI;
 import me.vene.skilled.modules.mods.main.RenderGUI;
 import me.vene.skilled.modules.mods.main.UtilityGUI;
 import me.vene.skilled.modules.mods.other.ClickGUI;
 import me.vene.skilled.modules.mods.other.SelfDestruct;
 import me.vene.skilled.modules.mods.other.SumoFences;
 import me.vene.skilled.modules.mods.player.BlockFly;
 import me.vene.skilled.modules.mods.player.FastPlace;
 import me.vene.skilled.modules.mods.player.Fly;
 import me.vene.skilled.modules.mods.player.SpeedBridge;
 import me.vene.skilled.modules.mods.player.TimerModule;
 import me.vene.skilled.modules.mods.player.WTap;
 import me.vene.skilled.modules.mods.render.BlockESP;
 import me.vene.skilled.modules.mods.render.Chams;
 import me.vene.skilled.modules.mods.render.Fullbright;
 import me.vene.skilled.modules.mods.render.Nametags;
 import me.vene.skilled.modules.mods.render.PlayerESP;
 import me.vene.skilled.modules.mods.render.StorageESP;
 import me.vene.skilled.modules.mods.render.Tracers;
 import me.vene.skilled.modules.mods.utility.Array;
 import me.vene.skilled.modules.mods.utility.BedNuker;
 import me.vene.skilled.modules.mods.utility.InfoTab;
 import me.vene.skilled.modules.mods.utility.Refill;
 import me.vene.skilled.modules.mods.utility.ThrowPot;

public class ModuleManager
{
    private static ArrayList<Module> modules;
    
    public static ArrayList<Module> getModules() {
        return ModuleManager.modules;
    }
    
    public static void clearShit() {
        ModuleManager.modules.clear();
    }
    
    static {
        (ModuleManager.modules = new ArrayList<Module>()).add(new ClickGUI());
        ModuleManager.modules.add(new CombatGUI());
        ModuleManager.modules.add(new AimAssist());
        ModuleManager.modules.add(new AutoClicker());
        ModuleManager.modules.add(new Array());
        ModuleManager.modules.add(new BlockESP());
        ModuleManager.modules.add(new Chams());
        ModuleManager.modules.add(new FastPlace());
        ModuleManager.modules.add(new HitBoxes());
        ModuleManager.modules.add(new InfoTab());
        ModuleManager.modules.add(new OtherGUI());
        ModuleManager.modules.add(new PlayerESP());
        ModuleManager.modules.add(new PlayerGUI());
        ModuleManager.modules.add(new Reach());
        ModuleManager.modules.add(new RenderGUI());
        ModuleManager.modules.add(new SelfDestruct());
        ModuleManager.modules.add(new SpeedBridge());
        ModuleManager.modules.add(new StorageESP());
        ModuleManager.modules.add(new ThrowPot());
        ModuleManager.modules.add(new Tracers());
        ModuleManager.modules.add(new UtilityGUI());
        ModuleManager.modules.add(new Velocity());
        ModuleManager.modules.add(new WTap());
        ModuleManager.modules.add(new Fullbright());
        ModuleManager.modules.add(new Sprint());
        ModuleManager.modules.add(new Nametags());
        ModuleManager.modules.add(new TimerModule());
        ModuleManager.modules.add(new BedNuker());
        ModuleManager.modules.add(new Refill());
        ModuleManager.modules.add(new KillAura());
        ModuleManager.modules.add(new Fly());
        ModuleManager.modules.add(new SumoFences());
        ModuleManager.modules.add(new BlockFly());
        ModuleManager.modules.add(new Delay17());
    }
}
