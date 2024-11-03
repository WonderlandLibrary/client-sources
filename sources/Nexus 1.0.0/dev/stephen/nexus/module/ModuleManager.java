package dev.stephen.nexus.module;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.module.modules.client.*;
import dev.stephen.nexus.module.modules.combat.*;
import dev.stephen.nexus.module.modules.ghost.*;
import dev.stephen.nexus.module.modules.movement.*;
import dev.stephen.nexus.module.modules.other.*;
import dev.stephen.nexus.module.modules.other.Timer;
import dev.stephen.nexus.module.modules.player.*;
import dev.stephen.nexus.module.modules.render.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
@Getter
public final class ModuleManager {
    public ModuleManager() {
        addModules();
    }

    private List<Module> modules = new ArrayList<>();

    public List<Module> getEnabledModules() {
        List<Module> enabled = new ArrayList<>();

        for (Module module : modules) {
            if (module.isEnabled()) {
                enabled.add(module);
            }
        }

        return enabled;
    }

    public List<Module> getModulesInCategory(ModuleCategory moduleCategory) {
        List<Module> categoryModules = new ArrayList<>();

        for (Module module : modules) {
            if (module.getModuleCategory().equals(moduleCategory)) {
                categoryModules.add(module);
            }
        }

        return categoryModules;
    }

    public <T extends Module> T getModule(Class<T> moduleClass) {
        for (Module module : modules) {
            if (moduleClass.isAssignableFrom(module.getClass())) {
                return (T) module;
            }
        }

        return null;
    }

    public Module getModuleByName(String name) {
        for (Module module : modules) {
            if (module.getName().toLowerCase().equalsIgnoreCase(name.toLowerCase())) {
                return module;
            }
        }
        return null;
    }

    public void addModules() {
        // CLIENT
        add(new ClickGUI());
        add(new Interface());
        add(new Notifications());
        add(new PostProcessing());
        add(new TargetHUD());
        add(new Theme());

        // COMBAT
        add(new AntiBot());
        add(new Backtrack());
        add(new Criticals());
        add(new InfiniteAura());
        add(new KillAura());
        add(new TargetStrafe());
        add(new TickBase());
        add(new Velocity());

        // GHOST
        add(new AimAssist());
        add(new AutoClicker());
        add(new LagRange());
        add(new TriggerBot());

        // MOVEMENT
        add(new AirStuck());
        add(new ClickTP());
        add(new ElytraFly());
        add(new Fly());
        add(new InvMove());
        add(new KeepSprint());
        add(new LongJump());
        add(new MoveFix());
        add(new NoSlow());
        add(new Speed());
        add(new Spider());
        add(new Sprint());

        // OTHER
        add(new AntiCheat());
        add(new Disabler());
        add(new FlagDetector());
        add(new NoRotate());
        add(new StaffDetector());
        add(new Timer());

        // PLAYER
        add(new AntiVoid());
        add(new BedAura());
        add(new Blink());
        add(new ChestStealer());
        add(new InventoryManager());
        add(new NoFall());
        add(new Regen());
        add(new Scaffold());

        // RENDER
        add(new Ambience());
        add(new Animations());
        add(new ESP());
        add(new Fullbright());
        add(new NoRender());

        Client.INSTANCE.getEventManager().subscribe(this);
    }

    public void add(Module module) {
        modules.add(module);
    }
}
