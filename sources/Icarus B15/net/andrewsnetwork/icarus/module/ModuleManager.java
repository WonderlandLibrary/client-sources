// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module;

import java.awt.Font;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.andrewsnetwork.icarus.Icarus;
import net.andrewsnetwork.icarus.module.modules.AimStep;
import net.andrewsnetwork.icarus.module.modules.AntiHunger;
import net.andrewsnetwork.icarus.module.modules.AntiHurtcam;
import net.andrewsnetwork.icarus.module.modules.AutoArmor;
import net.andrewsnetwork.icarus.module.modules.AutoRespawn;
import net.andrewsnetwork.icarus.module.modules.BedFucker;
import net.andrewsnetwork.icarus.module.modules.Blink;
import net.andrewsnetwork.icarus.module.modules.BlockESP;
import net.andrewsnetwork.icarus.module.modules.BowAimbot;
import net.andrewsnetwork.icarus.module.modules.ChestAura;
import net.andrewsnetwork.icarus.module.modules.ChestStealer;
import net.andrewsnetwork.icarus.module.modules.ClickTeleport;
import net.andrewsnetwork.icarus.module.modules.Criticals;
import net.andrewsnetwork.icarus.module.modules.DashNames;
import net.andrewsnetwork.icarus.module.modules.Derp;
import net.andrewsnetwork.icarus.module.modules.Detector;
import net.andrewsnetwork.icarus.module.modules.Durability;
import net.andrewsnetwork.icarus.module.modules.Fly;
import net.andrewsnetwork.icarus.module.modules.Freecam;
import net.andrewsnetwork.icarus.module.modules.Fullbright;
import net.andrewsnetwork.icarus.module.modules.GUI;
import net.andrewsnetwork.icarus.module.modules.Glide;
import net.andrewsnetwork.icarus.module.modules.GodMode;
import net.andrewsnetwork.icarus.module.modules.GunAura;
import net.andrewsnetwork.icarus.module.modules.HUD;
import net.andrewsnetwork.icarus.module.modules.HealingBot;
import net.andrewsnetwork.icarus.module.modules.HealthSaver;
import net.andrewsnetwork.icarus.module.modules.InstantUse;
import net.andrewsnetwork.icarus.module.modules.InventoryMove;
import net.andrewsnetwork.icarus.module.modules.InventoryTweak;
import net.andrewsnetwork.icarus.module.modules.Jesus;
import net.andrewsnetwork.icarus.module.modules.KillAura;
import net.andrewsnetwork.icarus.module.modules.MidClick;
import net.andrewsnetwork.icarus.module.modules.ModulesNotifications;
import net.andrewsnetwork.icarus.module.modules.NameTags;
import net.andrewsnetwork.icarus.module.modules.Ninja;
import net.andrewsnetwork.icarus.module.modules.NoFall;
import net.andrewsnetwork.icarus.module.modules.NoRender;
import net.andrewsnetwork.icarus.module.modules.NoRotation;
import net.andrewsnetwork.icarus.module.modules.NoSlowdown;
import net.andrewsnetwork.icarus.module.modules.NoVelocity;
import net.andrewsnetwork.icarus.module.modules.Paralyze;
import net.andrewsnetwork.icarus.module.modules.PingSpoof;
import net.andrewsnetwork.icarus.module.modules.Reach;
import net.andrewsnetwork.icarus.module.modules.Regen;
import net.andrewsnetwork.icarus.module.modules.SafeWalk;
import net.andrewsnetwork.icarus.module.modules.Sneak;
import net.andrewsnetwork.icarus.module.modules.Spammer;
import net.andrewsnetwork.icarus.module.modules.Speed;
import net.andrewsnetwork.icarus.module.modules.Speedmine;
import net.andrewsnetwork.icarus.module.modules.Sprint;
import net.andrewsnetwork.icarus.module.modules.Step;
import net.andrewsnetwork.icarus.module.modules.StorageESP;
import net.andrewsnetwork.icarus.module.modules.Strafe;
import net.andrewsnetwork.icarus.module.modules.Teams;
import net.andrewsnetwork.icarus.module.modules.TerrainSpeed;
import net.andrewsnetwork.icarus.module.modules.Timer;
import net.andrewsnetwork.icarus.module.modules.Tower;
import net.andrewsnetwork.icarus.module.modules.Tracers;
import net.andrewsnetwork.icarus.module.modules.Trajectories;
import net.andrewsnetwork.icarus.module.modules.Zoot;
import net.andrewsnetwork.icarus.utilities.Logger;
import net.andrewsnetwork.icarus.utilities.UnicodeFontRenderer;
import net.minecraft.util.StringUtils;

public class ModuleManager
{
    private final UnicodeFontRenderer arrayText;
    private List<Module> modules;
    
    public ModuleManager() {
        this.arrayText = new UnicodeFontRenderer(new Font("Verdana", 0, 18));
    }
    
    public Module getModuleByName(final String modname) {
        for (final Module module : this.modules) {
            if (module.getName().equalsIgnoreCase(modname)) {
                return module;
            }
        }
        return null;
    }
    
    public void setupModules() {
        this.modules = new CopyOnWriteArrayList<Module>();
        Logger.writeConsole("Started loading up modules.");
        this.modules.add(new HUD());
        this.modules.add(new Sprint());
        this.modules.add(new NameTags());
        this.modules.add(new MidClick());
        this.modules.add(new Speed());
        this.modules.add(new NoRotation());
        this.modules.add(new NoSlowdown());
        this.modules.add(new KillAura());
        modules.add(new Step());
        this.modules.add(new Durability());
        this.modules.add(new InstantUse());
        this.modules.add(new NoVelocity());
        this.modules.add(new Criticals());
        this.modules.add(new Speedmine());
        this.modules.add(new HealingBot());
        this.modules.add(new Trajectories());
        this.modules.add(new BowAimbot());
        this.modules.add(new Jesus());
        this.modules.add(new InventoryMove());
        this.modules.add(new Strafe());
        this.modules.add(new Glide());
        this.modules.add(new Fly());
        this.modules.add(new Fullbright());
        this.modules.add(new Reach());
        this.modules.add(new PingSpoof());
        this.modules.add(new GodMode());
        this.modules.add(new NoFall());
        this.modules.add(new Blink());
        this.modules.add(new AutoArmor());
        this.modules.add(new ChestStealer());
        this.modules.add(new ChestAura());
        this.modules.add(new Zoot());
        this.modules.add(new Freecam());
        this.modules.add(new TerrainSpeed());
        this.modules.add(new GUI());
        this.modules.add(new AntiHurtcam());
        this.modules.add(new StorageESP());
        this.modules.add(new Tower());
        this.modules.add(new SafeWalk());
        this.modules.add(new Tracers());
        this.modules.add(new AutoRespawn());
        this.modules.add(new DashNames());
        this.modules.add(new Regen());
        this.modules.add(new Spammer());
        this.modules.add(new BedFucker());
        this.modules.add(new ClickTeleport());
        this.modules.add(new Timer());
        this.modules.add(new AntiHunger());
        this.modules.add(new BlockESP());
        this.modules.add(new Sneak());
        this.modules.add(new HealthSaver());
        this.modules.add(new Ninja());
        this.modules.add(new Detector());
        this.modules.add(new ModulesNotifications());
        this.modules.add(new InventoryTweak());
        this.modules.add(new NoRender());
        this.modules.add(new Teams());
        this.modules.add(new GunAura());
        this.modules.add(new Derp());
        this.modules.add(new Paralyze());
        this.modules.add(new AimStep());
        Collections.sort(Icarus.getModuleManager().getModules(), new Comparator<Module>() {
            @Override
            public int compare(final Module mod1, final Module mod2) {
                if (ModuleManager.this.arrayText.getStringWidth(StringUtils.stripControlCodes(mod1.getTag())) > ModuleManager.this.arrayText.getStringWidth(StringUtils.stripControlCodes(mod2.getTag()))) {
                    return -1;
                }
                if (ModuleManager.this.arrayText.getStringWidth(StringUtils.stripControlCodes(mod1.getTag())) < ModuleManager.this.arrayText.getStringWidth(StringUtils.stripControlCodes(mod2.getTag()))) {
                    return 1;
                }
                return 0;
            }
        });
        Logger.writeConsole("Succesfully loaded " + this.modules.size() + " modules.");
    }
    
    public List<Module> getModules() {
        return this.modules;
    }
}
