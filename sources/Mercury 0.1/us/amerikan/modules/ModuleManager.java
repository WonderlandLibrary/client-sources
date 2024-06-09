/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.modules;

import java.util.ArrayList;
import java.util.List;
import us.amerikan.amerikan;
import us.amerikan.modules.Module;
import us.amerikan.modules.impl.Combat.BedFucker;
import us.amerikan.modules.impl.Combat.BowAimbot;
import us.amerikan.modules.impl.Combat.KillAura;
import us.amerikan.modules.impl.Combat.TNTBlock;
import us.amerikan.modules.impl.Combat.TriggerBot;
import us.amerikan.modules.impl.GUI.GUI;
import us.amerikan.modules.impl.GUI.HUD;
import us.amerikan.modules.impl.Misc.AutoRespawn;
import us.amerikan.modules.impl.Misc.AutoWalk;
import us.amerikan.modules.impl.Misc.NoFriends;
import us.amerikan.modules.impl.Misc.Phase;
import us.amerikan.modules.impl.Misc.TPBedDestroyer;
import us.amerikan.modules.impl.Movement.Fly;
import us.amerikan.modules.impl.Movement.HighJump;
import us.amerikan.modules.impl.Movement.LongJump;
import us.amerikan.modules.impl.Movement.NoWeb;
import us.amerikan.modules.impl.Movement.Speed;
import us.amerikan.modules.impl.Movement.Sprint;
import us.amerikan.modules.impl.Player.AirStuck;
import us.amerikan.modules.impl.Player.ChestStealer;
import us.amerikan.modules.impl.Player.FastPlace;
import us.amerikan.modules.impl.Player.InvHelper;
import us.amerikan.modules.impl.Player.NoSlowDown;
import us.amerikan.modules.impl.Player.Nofall;
import us.amerikan.modules.impl.Player.Scaffold;
import us.amerikan.modules.impl.Player.Tower;
import us.amerikan.modules.impl.Player.Velocity;
import us.amerikan.modules.impl.Render.ChestESP;
import us.amerikan.modules.impl.Render.FakeName;
import us.amerikan.modules.impl.Render.Fullbright;
import us.amerikan.modules.impl.Render.ItemESP;
import us.amerikan.modules.impl.Render.ItemPhysics;
import us.amerikan.modules.impl.Render.PlayerESP;
import us.amerikan.utils.Logger;

public class ModuleManager {
    public static List<Module> modules = new ArrayList<Module>();

    public ModuleManager() {
        this.addModule(new TriggerBot());
        this.addModule(new KillAura());
        this.addModule(new BowAimbot());
        this.addModule(new TNTBlock());
        this.addModule(new HUD());
        this.addModule(new GUI());
        this.addModule(new Speed());
        this.addModule(new Sprint());
        this.addModule(new NoWeb());
        this.addModule(new BedFucker());
        this.addModule(new TPBedDestroyer());
        this.addModule(new AutoWalk());
        this.addModule(new HighJump());
        this.addModule(new Velocity());
        this.addModule(new Fly());
        this.addModule(new FastPlace());
        this.addModule(new Tower());
        this.addModule(new Scaffold());
        this.addModule(new AirStuck());
        this.addModule(new InvHelper());
        this.addModule(new Nofall());
        this.addModule(new FakeName());
        this.addModule(new ChestESP());
        this.addModule(new PlayerESP());
        this.addModule(new ChestStealer());
        this.addModule(new ItemESP());
        this.addModule(new Fullbright());
        this.addModule(new AutoRespawn());
        this.addModule(new Phase());
        this.addModule(new ItemPhysics());
        this.addModule(new NoSlowDown());
        this.addModule(new NoFriends());
        this.addModule(new LongJump());
        amerikan.instance.logger.Info("Loaded Modules: " + modules.size());
    }

    public void addModule(Module module) {
        modules.add(module);
        amerikan.instance.logger.Loading("Module: " + module.getName());
    }

    public List<Module> getModules() {
        return modules;
    }

    public static Module getModuleByName(String moduleName) {
        for (Module mod : modules) {
            if (!mod.getName().trim().equalsIgnoreCase(moduleName) && !mod.toString().trim().equalsIgnoreCase(moduleName.trim())) continue;
            return mod;
        }
        return null;
    }

    public Module getModule(Class<? extends Module> clazz) {
        for (Module m2 : modules) {
            if (m2.getClass() != clazz) continue;
            return m2;
        }
        return null;
    }

    public Module getModuleByClass(Class clazz) {
        for (Module m2 : modules) {
            if (m2.getClass() != clazz) continue;
            return m2;
        }
        return null;
    }
}

