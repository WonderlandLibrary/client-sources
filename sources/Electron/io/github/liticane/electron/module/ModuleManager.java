package io.github.liticane.electron.module;

import io.github.liticane.electron.Electron;
import io.github.liticane.electron.module.impl.movement.SprintModule;
import io.github.liticane.electron.structure.Manager;

import java.util.List;

public class ModuleManager extends Manager<Module> {

    @Override
    public void init() {
        this.register(
                // Movement
                new SprintModule()
        );

        Electron.getInstance().getEventBus().subscribe(this);
    }

    @SuppressWarnings("unchecked")
    public <T extends Module> T getByClass(Class<? extends Module> aClass) {
        return (T) this.getMemberBy(module -> module.getClass().equals(aClass));
    }

    @SuppressWarnings("unchecked")
    public <T extends Module> T getByName(String name) {
        return (T) this.getMemberBy(module -> module.getName().equalsIgnoreCase(name));
    }

    public List<Module> getByType(ModuleType type) {
        return this.getMembersBy(module -> module.getType().equals(type));
    }

}
