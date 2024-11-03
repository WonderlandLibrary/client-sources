package com.minus.module;

import java.util.ArrayList;
import java.util.List;
import com.minus.Client;
import com.minus.module.impl.Render.ClickGUI;
import com.minus.module.impl.Render.Interface;

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

    public List<Module> getModulesInCategory(Category category) {
        List<Module> categoryModules = new ArrayList<>();

        for (Module module : modules) {
            if (module.getCategory().equals(category)) {
                categoryModules.add(module);
            }
        }

        return categoryModules;
    }

    public List<Module> getModules() {
        return modules;
    }

    public <T extends Module> T getModule(Class<T> moduleClass) {
        for (Module module : modules) {
            if (moduleClass.isAssignableFrom(module.getClass())) {
                return (T) module;
            }
        }

        return null;
    }

    public void addModules() {

        //Render
        add(new ClickGUI());

        add(new Interface());

        Client.INSTANCE.getEventManager().subscribe(this);
    }

    public void add(Module module) {
        modules.add(module);
    }
}
