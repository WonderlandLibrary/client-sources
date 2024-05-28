package arsenic.module;

import arsenic.event.bus.Listener;
import arsenic.event.bus.annotations.EventLink;
import arsenic.event.impl.EventKey;
import arsenic.main.Nexus;
import arsenic.module.impl.combat.*;
import arsenic.module.impl.exploit.*;
import arsenic.module.impl.movement.Sprint;
import arsenic.module.impl.exploit.chargetp.ChargeTp;
import arsenic.module.impl.movement.*;
import arsenic.module.impl.other.*;
import arsenic.module.impl.player.*;
import arsenic.module.impl.visual.*;
import arsenic.module.impl.visual.custommainmenu.CustomMainMenu;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class ModuleManager {

    private final Map<Class<? extends Module>, Module> modules = new HashMap<>();

    public final int initialize() {
	if(modules.size() != 0)
	    throw new RuntimeException("Double initialization of Module Manager.");

        addModule(
                ChargeTp.class,
                FullBright.class,
                Sprint.class,
                InvMove.class,
                AntiBot.class,
                NoJumpDelay.class,
                ClickGui.class,
                ChestStealer.class,
                Notifications.class,
                FastPlace.class,
                SafeWalk.class,
                Velocity.class,
                DelayRemover.class,
                CustomDisabler.class,
                NoHurtCam.class,
                Reach.class,
                Targets.class,
                ESP.class,
                GodMode.class,
                PingSpoof.class,
                Clicker.class,
                Scaffold.class,
                HitBox.class,
                AutoClutch.class,
                NoSlow.class,
                Disabler.class,
                Criticals.class,
                Blink.class,
                Flight.class,
                CustomFOV.class,
                InvManager.class,
                HUD.class,
                Timer.class,
                Aura.class,
                Speed.class,
                Backtrack.class,
                AntiVoid.class,
                KeepSprint.class,
                AgroPearl.class,
                NoFall.class,
                AutoCombo.class,
                BedAura.class,
                TargetHud.class,
                Particles.class,
                AutoGG.class,
                TickBase.class,
                FlagDetector.class,
                ChargeTp.class,
                CustomMainMenu.class);

        Nexus.getInstance().getEventManager().subscribe(this);
        return modules.size();
    }

    public final Collection<Module> getModules() { return modules.values(); }

    public final Collection<Module> getEnabledModules() {
        return getModules().stream().filter(Module::isEnabled).collect(Collectors.toList());
    }

    public final Collection<Module> getModulesByCategory(ModuleCategory category) {
        return getModules().stream().filter(m -> m.getCategory() == category).collect(Collectors.toList());
    }

    public <T extends Module> T getModuleByClass(Class<T> moduleClass) {
        return (T) modules.get(moduleClass);
    }

    public final Module getModuleByName(String str) {
        for (Module module : getModules()) {
            if (module.getName().equalsIgnoreCase(str))
                return module;
        }
        return null;
    }

    @EventLink
    public final Listener<EventKey> onKeyPress = event -> {
        AtomicBoolean saveConfig = new AtomicBoolean(false); // for eff

	getModules().stream().filter(m -> m.getKeybind() == event.getKeycode())
	    .forEach(m -> {
		m.setEnabled(!m.isEnabled());
		saveConfig.set(true);
	    });

        if (saveConfig.get()) { Nexus.getNexus().getConfigManager().saveConfig(); }
    };

    private void addModule(Class<? extends Module>... moduleClassArray) {
        for(Class<? extends Module> moduleClass : moduleClassArray) {
            try {
                Module module = moduleClass.newInstance();
                module.registerProperties();
                modules.put(moduleClass, module);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
