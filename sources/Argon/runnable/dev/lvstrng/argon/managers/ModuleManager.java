// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.managers;

import dev.lvstrng.argon.modules.Category;
import dev.lvstrng.argon.modules.Module;
import dev.lvstrng.argon.modules.impl.*;

import java.util.ArrayList;
import java.util.List;

public final class ModuleManager {
    private final List<Module> modules;

    public ModuleManager() {
        this.modules = new ArrayList<>();
        this.method588();
        this.method590();
    }

    private static String method595(final int p0, final int p1, final int p2){return null;}

    private String method587(){return null;}

    public void method588() {
        add(new AimAssist());
        add(new AnchorMacro());
        add(new AutoClicker());
        add(new AutoCrystal());
        add(new AutoDoubleHand());
        add(new AutoHitCrystal());
        add(new AutoInventoryTotem());
        add(new AutoJumpReset());
        add(new AutoPot());
        add(new AutoPotRefill());
        add(new AutoWTap());
        add(new AutoXP());
        add(new ClickGui());
        add(new CrystalOptimizer());
        add(new DoubleAnchor());
        add(new FakeLag());
        add(new FakePlayer());
        add(new Freecam());
        add(new HoverTotem());
        add(new HUD());
        add(new NoBounce());
        add(new NoBreakDelay());
        add(new NoJumpDelay());
        add(new NoMissDelay());
        add(new PingSpoof());
        add(new PlayerESP());
        add(new Prevent());
        add(new SelfDestruct());
        add(new ShieldDisabler());
        add(new StorageESP());
        add(new TargetHUD());
        add(new TriggerBot());
    }

    public List<Module> getModules2() {
        return modules;
    }

    //???
    public void method590(){};

    public List<Module> getModulesInCategory(final Category category) {
        return modules.stream().filter(m -> m.getCategory() == category).toList();
    }

    public Module getModuleByClass(final Class moduleClass) {
        for (Module m : getModules()) {
            if (m.getClass().equals(moduleClass)) {
                return m;
            }
        }
        return null;
    }

    public void add(final Module module) {
        modules.add(module);
    }

    public List<Module> getModules() {
        return modules;
    }
}
