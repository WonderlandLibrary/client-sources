// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.module;

import java.awt.Font;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import me.kaktuswasser.client.Client;
import me.kaktuswasser.client.module.modules.AimStep;
import me.kaktuswasser.client.module.modules.AntiHunger;
import me.kaktuswasser.client.module.modules.AntiHurtcam;
import me.kaktuswasser.client.module.modules.AutoArmor;
import me.kaktuswasser.client.module.modules.AutoRespawn;
import me.kaktuswasser.client.module.modules.BedFucker;
import me.kaktuswasser.client.module.modules.Blink;
import me.kaktuswasser.client.module.modules.BlockESP;
import me.kaktuswasser.client.module.modules.BowAimbot;
import me.kaktuswasser.client.module.modules.ChestAura;
import me.kaktuswasser.client.module.modules.ChestStealer;
import me.kaktuswasser.client.module.modules.ClickTeleport;
import me.kaktuswasser.client.module.modules.Criticals;
import me.kaktuswasser.client.module.modules.DashNames;
import me.kaktuswasser.client.module.modules.Derp;
import me.kaktuswasser.client.module.modules.Detector;
import me.kaktuswasser.client.module.modules.Durability;
import me.kaktuswasser.client.module.modules.Fly;
import me.kaktuswasser.client.module.modules.Freecam;
import me.kaktuswasser.client.module.modules.Fullbright;
import me.kaktuswasser.client.module.modules.GUI;
import me.kaktuswasser.client.module.modules.Glide;
import me.kaktuswasser.client.module.modules.GodMode;
import me.kaktuswasser.client.module.modules.GunAura;
import me.kaktuswasser.client.module.modules.HUD;
import me.kaktuswasser.client.module.modules.HealingBot;
import me.kaktuswasser.client.module.modules.HealthSaver;
import me.kaktuswasser.client.module.modules.InstantUse;
import me.kaktuswasser.client.module.modules.InventoryMove;
import me.kaktuswasser.client.module.modules.InventoryTweak;
import me.kaktuswasser.client.module.modules.Jesus;
import me.kaktuswasser.client.module.modules.KillAura;
import me.kaktuswasser.client.module.modules.MidClick;
import me.kaktuswasser.client.module.modules.ModulesNotifications;
import me.kaktuswasser.client.module.modules.NameTags;
import me.kaktuswasser.client.module.modules.Ninja;
import me.kaktuswasser.client.module.modules.NoFall;
import me.kaktuswasser.client.module.modules.NoRender;
import me.kaktuswasser.client.module.modules.NoRotation;
import me.kaktuswasser.client.module.modules.NoSlowdown;
import me.kaktuswasser.client.module.modules.NoVelocity;
import me.kaktuswasser.client.module.modules.Paralyze;
import me.kaktuswasser.client.module.modules.PingSpoof;
import me.kaktuswasser.client.module.modules.PlayerESP;
import me.kaktuswasser.client.module.modules.Reach;
import me.kaktuswasser.client.module.modules.Regen;
import me.kaktuswasser.client.module.modules.SafeWalk;
import me.kaktuswasser.client.module.modules.Sneak;
import me.kaktuswasser.client.module.modules.Spammer;
import me.kaktuswasser.client.module.modules.Speed;
import me.kaktuswasser.client.module.modules.Speedmine;
import me.kaktuswasser.client.module.modules.Sprint;
import me.kaktuswasser.client.module.modules.Step;
import me.kaktuswasser.client.module.modules.StorageESP;
import me.kaktuswasser.client.module.modules.Strafe;
import me.kaktuswasser.client.module.modules.Teams;
import me.kaktuswasser.client.module.modules.TerrainSpeed;
import me.kaktuswasser.client.module.modules.Timer;
import me.kaktuswasser.client.module.modules.Tower;
import me.kaktuswasser.client.module.modules.Tracers;
import me.kaktuswasser.client.module.modules.Trajectories;
import me.kaktuswasser.client.module.modules.Zoot;
import me.kaktuswasser.client.utilities.Logger;
import me.kaktuswasser.client.utilities.UnicodeFontRenderer;
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
        this.modules.add(new PlayerESP()); /*todo*/
        this.modules.add(new Step()); /*not working*/
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
//        this.modules.add(new GodMode()); /*crash*/
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
        this.modules.add(new Tracers()); /*Crash*/
        this.modules.add(new AutoRespawn());
        this.modules.add(new DashNames());/*what the fuck is this shit*/
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
        Collections.sort(Client.getModuleManager().getModules(), new Comparator<Module>() {
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
