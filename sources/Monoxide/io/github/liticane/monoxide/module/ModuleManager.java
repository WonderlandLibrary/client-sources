package io.github.liticane.monoxide.module;

import lombok.Getter;
import org.reflections.Reflections;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.listener.event.minecraft.input.KeyInputEvent;
import io.github.liticane.monoxide.listener.handling.EventHandling;
import io.github.liticane.monoxide.listener.radbus.Listen;

import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

public class ModuleManager extends ArrayList<Module> {

    @Getter
    private static ModuleManager instance;

    public ModuleManager() {
        EventHandling.getInstance().registerListener(this);

        new Reflections("io.github.liticane.monoxide").getTypesAnnotatedWith(ModuleData.class).forEach(aClass -> {
            try {
                this.add((Module) aClass.getDeclaredConstructor().newInstance());
            } catch (Exception ignored) {
                // ignored
            }
        });
    }

    @Listen
    public void onKey(KeyInputEvent keyInputEvent) {
        this.stream()
                .filter(module -> module.getKey() == keyInputEvent.getKey())
                .forEach(Module::toggle);
    }

    @SuppressWarnings("unchecked")
    public <V extends Module> V getModule(Class<V> aClass) {
        return (V) this.stream()
                .filter(m -> m.getClass().equals(aClass))
                .findFirst().orElse(null);
    }

    @SuppressWarnings("unchecked")
    public <T extends Module> T getModule(String name) {
        return (T) this.stream()
                .filter(module -> module.getName().equalsIgnoreCase(name))
                .findAny().orElse(null);
    }

    public List<Module> getModules(ModuleCategory category) {
        return this.stream()
                .filter(module -> module.getCategory() == category)
                .collect(Collectors.toList());
    }

    public static void setInstance(ModuleManager instance) {
        ModuleManager.instance = instance;
    }
}
