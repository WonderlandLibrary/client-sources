package dev.nexus.modules;

import lombok.Getter;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;

import static org.reflections.scanners.Scanners.SubTypes;

@Getter
public final class ModuleManager {
    private final List<Module> modules = new ArrayList<>();

    public ModuleManager() {
        Reflections reflections = new Reflections("dev.nexus.modules");
        reflections.get(SubTypes.of(Module.class).asClass()).forEach(this::addModule);
    }

    public List<Module> getEnabledModules() {
        List<Module> enabled = new ArrayList<>();

        for (Module module : modules) {
            if (module.isEnabled()) {
                enabled.add(module);
            }
        }

        return enabled;
    }

    public List<Module> getModulesInCategory(ModuleCategory category) {
        List<Module> categoryModules = new ArrayList<>();

        for (Module module : modules) {
            if (module.getCategory().equals(category)) {
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

    public void onKey(int key) {
        for (Module module : modules) {
            if (module.getKey() == key) {
                module.toggle();
            }
        }
    }

    private void addModule(Class<?> moduleClass) {
        try {
            Module module = (Module) moduleClass.getDeclaredConstructor().newInstance();
            add(module);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add(Module module) {
        modules.add(module);
    }
}
