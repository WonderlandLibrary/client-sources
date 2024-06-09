// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module;

import java.util.Collection;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.List;
import xyz.niggfaclient.Client;
import xyz.niggfaclient.module.impl.ghost.KeepSprint;
import xyz.niggfaclient.module.impl.ghost.Reach;
import xyz.niggfaclient.module.impl.ghost.AutoClicker;
import xyz.niggfaclient.module.impl.misc.Scoreboard;
import xyz.niggfaclient.module.impl.misc.MCF;
import xyz.niggfaclient.module.impl.misc.KillSults;
import xyz.niggfaclient.module.impl.misc.ChatBypass;
import xyz.niggfaclient.module.impl.misc.AutoTool;
import xyz.niggfaclient.module.impl.misc.AutoRegister;
import xyz.niggfaclient.module.impl.misc.AutoHypixel;
import xyz.niggfaclient.module.impl.misc.Ambiance;
import xyz.niggfaclient.module.impl.misc.ClientSpoofer;
import xyz.niggfaclient.module.impl.misc.Fucker;
import xyz.niggfaclient.module.impl.misc.No003;
import xyz.niggfaclient.module.impl.exploit.Phase;
import xyz.niggfaclient.module.impl.exploit.Disabler;
import xyz.niggfaclient.module.impl.exploit.PacketFixer;
import xyz.niggfaclient.module.impl.exploit.AntiCrasher;
import xyz.niggfaclient.module.impl.render.XRay;
import xyz.niggfaclient.module.impl.render.Streamer;
import xyz.niggfaclient.module.impl.render.SilentView;
import xyz.niggfaclient.module.impl.render.SessionInformation;
import xyz.niggfaclient.module.impl.render.MotionBlur;
import xyz.niggfaclient.module.impl.render.ItemPhysics;
import xyz.niggfaclient.module.impl.render.HUD;
import xyz.niggfaclient.module.impl.render.GlowESP;
import xyz.niggfaclient.module.impl.render.Fullbright;
import xyz.niggfaclient.module.impl.render.ESP;
import xyz.niggfaclient.module.impl.render.CustomMinecraft;
import xyz.niggfaclient.module.impl.render.Camera;
import xyz.niggfaclient.module.impl.render.Blur;
import xyz.niggfaclient.module.impl.render.BlockOutline;
import xyz.niggfaclient.module.impl.render.AntiBlind;
import xyz.niggfaclient.module.impl.render.Animations;
import xyz.niggfaclient.module.impl.render.Breadcrumbs;
import xyz.niggfaclient.module.impl.movement.Velocity;
import xyz.niggfaclient.module.impl.movement.TargetStrafe;
import xyz.niggfaclient.module.impl.movement.Step;
import xyz.niggfaclient.module.impl.movement.Sprint;
import xyz.niggfaclient.module.impl.movement.Speed;
import xyz.niggfaclient.module.impl.movement.SafeWalk;
import xyz.niggfaclient.module.impl.movement.NoSlowdown;
import xyz.niggfaclient.module.impl.movement.NoFall;
import xyz.niggfaclient.module.impl.movement.Flight;
import xyz.niggfaclient.module.impl.movement.CustomSpeed;
import xyz.niggfaclient.module.impl.movement.CustomFlight;
import xyz.niggfaclient.module.impl.movement.AntiVoid;
import xyz.niggfaclient.module.impl.player.Timer;
import xyz.niggfaclient.module.impl.player.Stealer;
import xyz.niggfaclient.module.impl.player.Speedmine;
import xyz.niggfaclient.module.impl.player.Jesus;
import xyz.niggfaclient.module.impl.player.InvMove;
import xyz.niggfaclient.module.impl.player.InvManager;
import xyz.niggfaclient.module.impl.player.FastPlace;
import xyz.niggfaclient.module.impl.player.Eagle;
import xyz.niggfaclient.module.impl.player.Blink;
import xyz.niggfaclient.module.impl.player.Scaffold;
import xyz.niggfaclient.module.impl.combat.KillAura;
import xyz.niggfaclient.module.impl.combat.FastBow;
import xyz.niggfaclient.module.impl.combat.Criticals;
import xyz.niggfaclient.module.impl.combat.AutoPotion;
import xyz.niggfaclient.module.impl.combat.AutoArmor;
import xyz.niggfaclient.module.impl.combat.AntiBot;
import java.util.ArrayList;

public class ModuleManager
{
    public static final ArrayList<Module> MODULES;
    
    public ModuleManager() {
        ModuleManager.MODULES.add(new AntiBot());
        ModuleManager.MODULES.add(new AutoArmor());
        ModuleManager.MODULES.add(new AutoPotion());
        ModuleManager.MODULES.add(new Criticals());
        ModuleManager.MODULES.add(new FastBow());
        ModuleManager.MODULES.add(new KillAura());
        ModuleManager.MODULES.add(new Scaffold());
        ModuleManager.MODULES.add(new Blink());
        ModuleManager.MODULES.add(new Eagle());
        ModuleManager.MODULES.add(new FastPlace());
        ModuleManager.MODULES.add(new InvManager());
        ModuleManager.MODULES.add(new InvMove());
        ModuleManager.MODULES.add(new Jesus());
        ModuleManager.MODULES.add(new Speedmine());
        ModuleManager.MODULES.add(new Stealer());
        ModuleManager.MODULES.add(new Timer());
        ModuleManager.MODULES.add(new AntiVoid());
        ModuleManager.MODULES.add(new CustomFlight());
        ModuleManager.MODULES.add(new CustomSpeed());
        ModuleManager.MODULES.add(new Flight());
        ModuleManager.MODULES.add(new NoFall());
        ModuleManager.MODULES.add(new NoSlowdown());
        ModuleManager.MODULES.add(new SafeWalk());
        ModuleManager.MODULES.add(new Speed());
        ModuleManager.MODULES.add(new Sprint());
        ModuleManager.MODULES.add(new Step());
        ModuleManager.MODULES.add(new TargetStrafe());
        ModuleManager.MODULES.add(new Velocity());
        ModuleManager.MODULES.add(new Breadcrumbs());
        ModuleManager.MODULES.add(new Animations());
        ModuleManager.MODULES.add(new AntiBlind());
        ModuleManager.MODULES.add(new BlockOutline());
        ModuleManager.MODULES.add(new Blur());
        ModuleManager.MODULES.add(new Camera());
        ModuleManager.MODULES.add(new CustomMinecraft());
        ModuleManager.MODULES.add(new ESP());
        ModuleManager.MODULES.add(new Fullbright());
        ModuleManager.MODULES.add(new GlowESP());
        ModuleManager.MODULES.add(new HUD());
        ModuleManager.MODULES.add(new ItemPhysics());
        ModuleManager.MODULES.add(new MotionBlur());
        ModuleManager.MODULES.add(new SessionInformation());
        ModuleManager.MODULES.add(new SilentView());
        ModuleManager.MODULES.add(new Streamer());
        ModuleManager.MODULES.add(new XRay());
        ModuleManager.MODULES.add(new AntiCrasher());
        ModuleManager.MODULES.add(new PacketFixer());
        ModuleManager.MODULES.add(new Disabler());
        ModuleManager.MODULES.add(new Phase());
        ModuleManager.MODULES.add(new No003());
        ModuleManager.MODULES.add(new Fucker());
        ModuleManager.MODULES.add(new ClientSpoofer());
        ModuleManager.MODULES.add(new Ambiance());
        ModuleManager.MODULES.add(new AutoHypixel());
        ModuleManager.MODULES.add(new AutoRegister());
        ModuleManager.MODULES.add(new AutoTool());
        ModuleManager.MODULES.add(new ChatBypass());
        ModuleManager.MODULES.add(new KillSults());
        ModuleManager.MODULES.add(new MCF());
        ModuleManager.MODULES.add(new Scoreboard());
        ModuleManager.MODULES.add(new AutoClicker());
        ModuleManager.MODULES.add(new Reach());
        ModuleManager.MODULES.add(new KeepSprint());
        this.getModules().forEach(Module::reflectProperties);
        this.getModules().forEach(Module::resetPropertyValues);
        Client.getInstance().getEventBus().subscribe(this);
    }
    
    public void postInit() {
        this.getModules().forEach(Module::resetPropertyValues);
    }
    
    public static <T extends Module> T getModule(final Class<T> clazz) {
        return (T)ModuleManager.MODULES.stream().filter(mod -> mod.getClass().equals(clazz)).findFirst().orElse(null);
    }
    
    public List<Module> getModulesForCategory(final Category c) {
        return ModuleManager.MODULES.stream().filter(module -> module.getCategory() == c).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
    }
    
    public Collection<Module> getModules() {
        return ModuleManager.MODULES;
    }
    
    static {
        MODULES = new ArrayList<Module>();
    }
}
