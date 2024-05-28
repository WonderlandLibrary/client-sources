package dev.vertic.module.api;

import dev.vertic.Client;
import dev.vertic.event.api.EventLink;
import dev.vertic.event.api.Priority;
import dev.vertic.event.impl.input.KeyPressEvent;
import dev.vertic.event.impl.input.SettingUpdateEvent;
import dev.vertic.module.Module;
import dev.vertic.module.impl.combat.*;
import dev.vertic.module.impl.ghost.*;
import dev.vertic.module.impl.movement.*;
import dev.vertic.module.impl.other.AntiBot;
import dev.vertic.module.impl.other.Teams;
import dev.vertic.module.impl.player.*;
import dev.vertic.module.impl.render.*;

import java.util.*;

public class ModuleManager {

    public final ArrayList<Module> modules = new ArrayList<>();
    public ModuleManager() {
        modules.addAll(Arrays.asList(
                new Sprint(),
                new ClickGUI(),
                new Blur(),
                new AutoClicker(),
                new AimAssist(),
                new AimBot(),
                new LegitScaffold(),
                new KeepSprint(),
                new FreeLook(),
                new ArrayListModule(),
                new NoClickDelay(),
                new Blink(),
                new TimeChanger(),
                new Strafe(),
                new AutoTool(),
                new FastPlace(),
                new ClientTheme(),
                new Animations(),
                new Rotations(),
                new Velocity(),
                new AntiBot(),
                new Teams()
        ));
        modules.sort(Comparator.comparing(Module::getName));
        Client.instance.getEventBus().register(this);
    }

    public <T extends Module> T getModule(final Class<T> clazz) {
        Optional<Module> optional = modules.stream().filter(m -> m.getClass().equals(clazz)).findFirst();
        if (optional.isPresent()) {
            return (T) optional.get();
        }
        return null;
    }

    public <T extends Module> T getModule(final String name) {
        Optional<Module> optional = modules.stream().filter(m -> m.getName().equalsIgnoreCase(name)).findFirst();
        if (optional.isPresent()) {
            return (T) optional.get();
        }
        return null;
    }

    public <T extends Module> T getModuleNoSpace(final String name) {
        Optional<Module> optional = modules.stream().filter(m -> m.getName().replaceAll(" ", "").equalsIgnoreCase(name)).findFirst();
        return (T) optional.orElse(null);
    }

    public List<Module> getModulesByCategory(final Category category) {
        final List<Module> modules = new ArrayList<>();
        for (final Module module : this.modules) {
            if (module.getCategory() == category) modules.add(module);
        }
        return modules;
    }

    @EventLink(Priority.VERY_HIGH)
    public void onKeyPress(KeyPressEvent event) {
        modules.stream().filter(
                m -> m.getKey() == event.getKey()
        ).forEach(
                Module::toggle
        );
    }

    @EventLink(Priority.LOW)
    public void onSettingUpdate(SettingUpdateEvent event) {
        modules.forEach(Module::update);
    }

}
