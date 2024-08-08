package lol.point.returnclient.managers;

import lol.point.Return;
import lol.point.returnclient.events.impl.input.EventKey;
import lol.point.returnclient.module.Category;
import lol.point.returnclient.module.Module;
import lol.point.returnclient.module.ModuleInfo;
import lol.point.returnclient.module.managers.impl.BedwarsManager;
import lol.point.returnclient.module.managers.impl.BlinkManager;
import lol.point.returnclient.module.managers.impl.TargetManager;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import me.zero.alpine.listener.Subscriber;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class ModuleManager implements Subscriber {

    public final ArrayList<Module> modules = new ArrayList<>();
    public BedwarsManager bedwarsManager;
    public TargetManager targetManager;
    public BlinkManager blinkManager;

    public ModuleManager() {
        Return.BUS.subscribe(this);

        targetManager = new TargetManager(this);
        bedwarsManager = new BedwarsManager(this);
        blinkManager = new BlinkManager(this);

        Reflections reflections = new Reflections("lol.point.returnclient.module.impl");
        Set<Class<? extends Module>> moduleClasses = reflections.getSubTypesOf(Module.class);

        for (Class<? extends Module> moduleClass : moduleClasses) {
            if (moduleClass.isAnnotationPresent(ModuleInfo.class)) {
                try {
                    Module module = moduleClass.getDeclaredConstructor().newInstance();
                    modules.add(module);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public Module getModuleByName(String name) {
        return modules.stream().filter(module -> module.name.equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public List<Module> getModulesByCategory(Category input) {
        return modules.stream().filter(module -> module.category.equals(input)).collect(Collectors.toList());
    }

    public List<Module> getEnabledModules() {
        return modules.stream().filter(module -> module.enabled).collect(Collectors.toList());
    }


    public <V extends Module> V getByClass(final Class<V> clazz) {
        Module feature = modules.stream().filter(m -> m.getClass().equals(clazz)).findFirst().orElse(null);

        if (feature == null) {
            return null;
        }

        return clazz.cast(feature);
    }

    @Subscribe
    private final Listener<EventKey> onKey = new Listener<>(eventKey -> {
        modules.forEach(module -> {
            if (module.key == eventKey.key) {
                module.setEnabled(!module.enabled);
            }
        });
    });
}
