// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module;

import gg.childtrafficking.smokex.bind.Bindable;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import gg.childtrafficking.smokex.SmokeXClient;
import java.util.Arrays;
import gg.childtrafficking.smokex.module.modules.misc.AutoHypixelModule;
import gg.childtrafficking.smokex.module.modules.misc.PitAddonsModule;
import gg.childtrafficking.smokex.module.modules.misc.FriendProtectModule;
import gg.childtrafficking.smokex.module.modules.misc.AmbienceModule;
import gg.childtrafficking.smokex.module.modules.misc.HackerDetectorModule;
import gg.childtrafficking.smokex.module.modules.misc.AntiCuckModule;
import gg.childtrafficking.smokex.module.modules.misc.AutoLogGlitchModule;
import gg.childtrafficking.smokex.module.modules.misc.KillInsultModule;
import gg.childtrafficking.smokex.module.modules.exploit.NoRotateModule;
import gg.childtrafficking.smokex.module.modules.exploit.AntiLauncherModule;
import gg.childtrafficking.smokex.module.modules.exploit.AntiLimboModule;
import gg.childtrafficking.smokex.module.modules.exploit.DisablerModule;
import gg.childtrafficking.smokex.module.modules.exploit.BlinkModule;
import gg.childtrafficking.smokex.module.modules.player.PhaseModule;
import gg.childtrafficking.smokex.module.modules.player.InventoryManagerModule;
import gg.childtrafficking.smokex.module.modules.player.ChestStealerModule;
import gg.childtrafficking.smokex.module.modules.player.AntiFallModule;
import gg.childtrafficking.smokex.module.modules.player.NoFallModule;
import gg.childtrafficking.smokex.module.modules.player.InventoryMoveModule;
import gg.childtrafficking.smokex.module.modules.player.ScaffoldModule;
import gg.childtrafficking.smokex.module.modules.player.TimerModule;
import gg.childtrafficking.smokex.module.modules.player.NoSlowdownModule;
import gg.childtrafficking.smokex.module.modules.player.AutoSprintModule;
import gg.childtrafficking.smokex.module.modules.player.IdleFighterModule;
import gg.childtrafficking.smokex.module.modules.player.AutoHealModule;
import gg.childtrafficking.smokex.module.modules.player.AutoToolModule;
import gg.childtrafficking.smokex.module.modules.movement.TargetStrafeModule;
import gg.childtrafficking.smokex.module.modules.movement.LongJumpModule;
import gg.childtrafficking.smokex.module.modules.movement.FlightModule;
import gg.childtrafficking.smokex.module.modules.movement.SpeedModule;
import gg.childtrafficking.smokex.module.modules.movement.StepModule;
import gg.childtrafficking.smokex.module.modules.combat.AntiBotModule;
import gg.childtrafficking.smokex.module.modules.combat.AutoClickerModule;
import gg.childtrafficking.smokex.module.modules.combat.CriticalsModule;
import gg.childtrafficking.smokex.module.modules.combat.VelocityModule;
import gg.childtrafficking.smokex.module.modules.combat.KillauraModule;
import gg.childtrafficking.smokex.module.modules.combat.AimbotModule;
import gg.childtrafficking.smokex.module.modules.visuals.FocusModule;
import gg.childtrafficking.smokex.module.modules.visuals.AnimationsModule;
import gg.childtrafficking.smokex.module.modules.visuals.ESPModule;
import gg.childtrafficking.smokex.module.modules.visuals.SessionInformationModule;
import gg.childtrafficking.smokex.module.modules.visuals.ChinaHatModule;
import gg.childtrafficking.smokex.module.modules.visuals.HUDModule;
import java.util.ArrayList;
import java.util.List;

public final class ModuleManager
{
    private final List<Module> MODULELIST;
    
    public ModuleManager() {
        this.MODULELIST = new ArrayList<Module>();
    }
    
    public void init() {
        this.addModules(new HUDModule(), new ChinaHatModule(), new SessionInformationModule(), new ESPModule(), new AnimationsModule(), new FocusModule(), new AimbotModule(), new KillauraModule(), new VelocityModule(), new CriticalsModule(), new AutoClickerModule(), new AntiBotModule(), new StepModule(), new SpeedModule(), new FlightModule(), new LongJumpModule(), new TargetStrafeModule(), new AutoToolModule(), new AutoHealModule(), new IdleFighterModule(), new AutoSprintModule(), new NoSlowdownModule(), new TimerModule(), new ScaffoldModule(), new InventoryMoveModule(), new NoFallModule(), new AntiFallModule(), new ChestStealerModule(), new InventoryManagerModule(), new PhaseModule(), new BlinkModule(), new DisablerModule(), new AntiLimboModule(), new AntiLauncherModule(), new NoRotateModule(), new KillInsultModule(), new AutoLogGlitchModule(), new AntiCuckModule(), new HackerDetectorModule(), new AmbienceModule(), new FriendProtectModule(), new PitAddonsModule(), new AutoHypixelModule());
        this.getModules().forEach(Module::reflectProperties);
    }
    
    public <T extends Module> T getModule(final Class<T> clazz) {
        return (T)this.MODULELIST.stream().filter(mod -> mod.getClass().equals(clazz)).findFirst().orElse(null);
    }
    
    public Module getModule(final String name) {
        return this.MODULELIST.stream().filter(mod -> mod.getName().equalsIgnoreCase(name) || Arrays.stream(mod.getAliases()).filter(alias -> alias.equalsIgnoreCase(name)).findFirst().orElse(null) != null).findFirst().orElse(null);
    }
    
    public static <T extends Module> T getInstance(final Class<T> clazz) {
        return (T)SmokeXClient.getInstance().getModuleManager().getModule((Class<Module>)clazz);
    }
    
    public List<Module> getModulesForCategory(final ModuleCategory category) {
        return this.getModules().stream().filter(module -> module.getCategory() == category).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
    }
    
    private void addModules(final Module... modules) {
        for (final Module module : modules) {
            this.MODULELIST.add(module);
            this.getModule(module.getClass()).init();
            SmokeXClient.getInstance().getBindManager().addBindable(module);
        }
    }
    
    public List<Module> getModules() {
        return this.MODULELIST;
    }
}
