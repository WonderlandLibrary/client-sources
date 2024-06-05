/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.manager.module;

import cc.diablo.module.Module;
import cc.diablo.module.impl.combat.AutoPot;
import cc.diablo.module.impl.combat.BowAimbot;
import cc.diablo.module.impl.combat.Criticals;
import cc.diablo.module.impl.combat.KeepSprint;
import cc.diablo.module.impl.combat.KillAura;
import cc.diablo.module.impl.combat.TargetStrafe;
import cc.diablo.module.impl.combat.Velocity;
import cc.diablo.module.impl.exploit.AntiVanish;
import cc.diablo.module.impl.exploit.Blink;
import cc.diablo.module.impl.exploit.ClientSpoof;
import cc.diablo.module.impl.exploit.Crasher;
import cc.diablo.module.impl.exploit.Disabler;
import cc.diablo.module.impl.exploit.FastBow;
import cc.diablo.module.impl.exploit.LightningDetector;
import cc.diablo.module.impl.exploit.Regen;
import cc.diablo.module.impl.exploit.Viewer;
import cc.diablo.module.impl.ghost.AimAssistance;
import cc.diablo.module.impl.ghost.AutoClicker;
import cc.diablo.module.impl.misc.AntiAtlas;
import cc.diablo.module.impl.misc.AutoBlocksMC;
import cc.diablo.module.impl.misc.AutoDisable;
import cc.diablo.module.impl.misc.AutoHypixel;
import cc.diablo.module.impl.misc.AutoTool;
import cc.diablo.module.impl.misc.FastPlace;
import cc.diablo.module.impl.misc.HitSound;
import cc.diablo.module.impl.misc.IRC;
import cc.diablo.module.impl.misc.MiddleClickFriend;
import cc.diablo.module.impl.misc.NameHider;
import cc.diablo.module.impl.misc.Spammer;
import cc.diablo.module.impl.misc.StaffDetector;
import cc.diablo.module.impl.misc.Timer;
import cc.diablo.module.impl.movement.BowFly;
import cc.diablo.module.impl.movement.Fly;
import cc.diablo.module.impl.movement.Highjump;
import cc.diablo.module.impl.movement.Jesus;
import cc.diablo.module.impl.movement.LongJump;
import cc.diablo.module.impl.movement.NoSlow;
import cc.diablo.module.impl.movement.SafeWalk;
import cc.diablo.module.impl.movement.Sneak;
import cc.diablo.module.impl.movement.Speed;
import cc.diablo.module.impl.movement.Sprint;
import cc.diablo.module.impl.player.AntiAim;
import cc.diablo.module.impl.player.AntiVoid;
import cc.diablo.module.impl.player.InventoryCleaner;
import cc.diablo.module.impl.player.InventoryMove;
import cc.diablo.module.impl.player.NoFall;
import cc.diablo.module.impl.player.NoRotate;
import cc.diablo.module.impl.player.Phase;
import cc.diablo.module.impl.player.Scaffold;
import cc.diablo.module.impl.player.Stealer;
import cc.diablo.module.impl.render.Animations;
import cc.diablo.module.impl.render.Cape;
import cc.diablo.module.impl.render.Chams;
import cc.diablo.module.impl.render.ChestChams;
import cc.diablo.module.impl.render.ChestESP;
import cc.diablo.module.impl.render.ClickGUI;
import cc.diablo.module.impl.render.Crosshair;
import cc.diablo.module.impl.render.CustomChat;
import cc.diablo.module.impl.render.Esp2D;
import cc.diablo.module.impl.render.FOVChanger;
import cc.diablo.module.impl.render.FullBright;
import cc.diablo.module.impl.render.Glint;
import cc.diablo.module.impl.render.Glow;
import cc.diablo.module.impl.render.HUD;
import cc.diablo.module.impl.render.ItemESP;
import cc.diablo.module.impl.render.ItemPhysics;
import cc.diablo.module.impl.render.PlayerList;
import cc.diablo.module.impl.render.Scoreboard;
import cc.diablo.module.impl.render.ShowName;
import cc.diablo.module.impl.render.TimeChanger;
import cc.diablo.module.impl.render.XRay;
import java.util.ArrayList;
import java.util.Arrays;

public class ModuleManager {
    public static ArrayList<Module> modules = new ArrayList();

    public ModuleManager() {
        System.out.println("Loading modules...");
        this.addModules(new InventoryCleaner(), new Sprint(), new Fly(), new NoFall(), new HUD(), new AntiVoid(), new ClickGUI(), new Sneak(), new NoRotate(), new Timer(), new AimAssistance(), new AutoClicker(), new InventoryMove(), new KillAura(), new Glow(), new AntiAim(), new FullBright(), new NoSlow(), new KeepSprint(), new AutoTool(), new Speed(), new Disabler(), new Velocity(), new Stealer(), new LongJump(), new AutoHypixel(), new Scoreboard(), new Criticals(), new AutoPot(), new Cape(), new Scaffold(), new Esp2D(), new Chams(), new ChestChams(), new SafeWalk(), new BowFly(), new Phase(), new MiddleClickFriend(), new Glint(), new TimeChanger(), new Blink(), new TargetStrafe(), new AutoBlocksMC(), new ClientSpoof(), new Jesus(), new Animations(), new FOVChanger(), new LightningDetector(), new PlayerList(), new Highjump(), new CustomChat(), new ChestESP(), new AntiAtlas(), new Spammer(), new AutoDisable(), new ItemESP(), new ItemPhysics(), new XRay(), new HitSound(), new AntiVanish(), new FastBow(), new BowAimbot(), new StaffDetector(), new Regen(), new IRC(), new FastPlace(), new NameHider(), new Viewer(), new Crosshair(), new Crasher(), new ShowName());
        for (Module m : modules) {
            System.out.println(m.getName() + " | " + m.getDescription());
        }
        System.out.println("Loaded " + modules.size() + " modules!");
    }

    public void addModules(Module ... modulesAsList) {
        modules.addAll(Arrays.asList(modulesAsList));
    }

    public static ArrayList<Module> getModules() {
        return modules;
    }

    public static ArrayList<Module> getModulesToggled() {
        ArrayList modulesToggled = null;
        for (Module module : ModuleManager.getModules()) {
            if (!module.isToggled()) continue;
            modulesToggled.add(module);
        }
        return modulesToggled;
    }

    public static <T extends Module> Module getModule(Class<T> clas) {
        return ModuleManager.getModules().stream().filter(module -> module.getClass() == clas).findFirst().orElse(null);
    }

    public static <T extends Module> Module getModuleByName(String module) {
        for (Module m : ModuleManager.getModules()) {
            if (!m.getName().equalsIgnoreCase(module)) continue;
            return m;
        }
        return null;
    }
}

