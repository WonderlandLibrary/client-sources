package cn.Hanabi.modules;

import org.jetbrains.annotations.*;
import cn.Hanabi.modules.Movement.*;
import cn.Hanabi.modules.Render.*;
import cn.Hanabi.modules.Combat.*;
import ClassSub.*;
import cn.Hanabi.modules.World.*;
import cn.Hanabi.modules.Player.*;
import cn.Hanabi.modules.Ghost.*;
import cn.Hanabi.*;
import cn.Hanabi.events.*;
import java.util.*;
import com.darkmagician6.eventapi.*;
import cn.Hanabi.utils.fontmanager.*;

public class ModManager
{
    @NotNull
    public static List<Mod> modules;
    public static boolean needsort;
    public static ArrayList<Mod> sortedModList;
    private static ArrayList<Mod> enabledModList;
    
    public ModManager() {
        EventManager.register(this);
    }
    
    public void addModules() {
        Class302.fuckman();
        this.addModule(new Hitbox());
        this.addModule(new KeepSprint());
        this.addModule(new Velocity());
        this.addModule(new KillAura());
        this.addModule(new Criticals());
        this.addModule(new AutoSword());
        this.addModule(new Reach());
        this.addModule(new TPHit());
        this.addModule(new Sprint());
        this.addModule(new Speed());
        this.addModule(new Fly());
        this.addModule(new Strafe());
        this.addModule(new LongJump());
        this.addModule(new NoSlow());
        this.addModule(new FakeLag());
        this.addModule(new AutoArmor());
        this.addModule(new InvCleaner());
        this.addModule(new InvMove());
        this.addModule(new NoFall());
        this.addModule(new AutoAbuse());
        this.addModule(new Spammer());
        this.addModule(new StaffAnalyzer());
        this.addModule(new ChestStealer());
        this.addModule(new Nuker());
        this.addModule(new Blink());
        this.addModule(new FastPlace());
        this.addModule(new AutoTools());
        this.addModule(new Mod(this, "NoCommand", Category.PLAYER) {
            final ModManager this$0;
        });
        this.addModule(new Class135());
        this.addModule(new Nametags());
        this.addModule(new Fullbright());
        this.addModule(new ESP());
        this.addModule(new Projectiles());
        this.addModule(new BedESP());
        this.addModule(new ChestESP());
        this.addModule(new HitAnimation());
        this.addModule(new CaveFinder());
        this.addModule(new MusicPlayer());
        this.addModule(new Chams());
        this.addModule(new AntiBot());
        this.addModule(new Teams());
        this.addModule(new Scaffold());
        this.addModule(new AntiFall());
        this.addModule(new AutoL());
        this.addModule(new IRC());
        this.addModule(new HideAndSeek());
        this.addModule(new Eagle());
        this.addModule(new Class118());
        this.addModule(new SpeedMine());
        if (Class302.whatfuck()) {
            this.addModule(new TP2Bed());
        }
        this.addModule(new AimAssist());
        this.addModule(new AutoClicker());
        this.addModule(new LegitVelocity());
        Hanabi.INSTANCE.hmlManager.onModuleManagerLoading(this);
    }
    
    public void addModule(@NotNull final Mod module) {
        ModManager.modules.add(module);
    }
    
    @NotNull
    public List<Mod> getModules() {
        return ModManager.modules;
    }
    
    @NotNull
    public <T extends Mod> T getModule(final Class<T> clazz) {
        return (T)ModManager.modules.stream().filter(ModManager::lambda$getModule$0).findFirst().orElse(null);
    }
    
    public static Mod getModule(@NotNull final String name) {
        try {
            return getModule(name, false);
        }
        catch (Exception e) {
            return new Mod("NMSL", Category.COMBAT) {};
        }
    }
    
    public static Mod getModule(@NotNull final String name, final boolean caseSensitive) {
        return ModManager.modules.stream().filter(ModManager::lambda$getModule$1).findFirst().orElse(null);
    }
    
    @EventTarget
    private void onKey(@NotNull final EventKey event) {
        for (final Mod module : ModManager.modules) {
            if (module.getKeybind() == event.getKey()) {
                module.setState(!module.getState());
            }
        }
    }
    
    public static List<Mod> getModList() {
        return ModManager.modules;
    }
    
    public static ArrayList<Mod> getEnabledModList() {
        final ArrayList<Mod> enabledModList = new ArrayList<Mod>();
        for (final Mod m : ModManager.modules) {
            if (m.isEnabled()) {
                enabledModList.add(m);
            }
        }
        return enabledModList;
    }
    
    public static ArrayList<Mod> getEnabledModListHUD() {
        if (ModManager.needsort) {
            ModManager.enabledModList = new ArrayList<Mod>();
            for (final Mod m : ModManager.modules) {
                ModManager.enabledModList.add(m);
            }
            final UnicodeFontRenderer fr = Hanabi.INSTANCE.fontManager.raleway17;
            ModManager.enabledModList.sort(ModManager::lambda$getEnabledModListHUD$2);
            ModManager.needsort = false;
        }
        return ModManager.enabledModList;
    }
    
    private static int lambda$getEnabledModListHUD$2(final UnicodeFontRenderer fr, final Mod o1, final Mod o2) {
        return fr.func_78256_a(o2.getName() + ((o2.getDisplayName() != null) ? (o2.getDisplayName() + "..") : "")) - fr.func_78256_a(o1.getName() + ((o1.getDisplayName() != null) ? (o1.getDisplayName() + "..") : ""));
    }
    
    private static boolean lambda$getModule$1(final boolean caseSensitive, final String name, final Mod mod) {
        return (!caseSensitive && name.equalsIgnoreCase(mod.getName())) || name.equals(mod.getName());
    }
    
    private static boolean lambda$getModule$0(final Class clazz, final Mod mod) {
        return mod.getClass() == clazz;
    }
    
    static {
        ModManager.modules = new ArrayList<Mod>();
        ModManager.needsort = true;
        ModManager.sortedModList = new ArrayList<Mod>();
    }
}
