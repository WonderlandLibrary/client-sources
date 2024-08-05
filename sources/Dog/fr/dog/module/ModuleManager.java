package fr.dog.module;

import fr.dog.structure.Manager;

import java.util.List;
import java.util.Objects;

public class ModuleManager extends Manager<Module> {
    @SuppressWarnings("unchecked")
    public <T extends Module> T getModule(final Class<T> clazz) {
        return (T) this.getBy(module -> Objects.equals(module.getClass(), clazz));
    }

    @SuppressWarnings("unchecked")
    public <T extends Module> T getModule(String name) {
        return (T) getObjects().stream()
                .filter(module -> module.getName().equalsIgnoreCase(name))
                .findAny()
                .orElse(null);
    }

    public List<Module> getModulesFromCategory(ModuleCategory category) {
        return this.getMultipleBy(module -> module.getCategory() == category);
    }
}