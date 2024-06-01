package io.github.liticane.clients.feature.module;

import io.github.liticane.clients.Client;
import io.github.liticane.clients.feature.event.api.annotations.SubscribeEvent;
import io.github.liticane.clients.feature.event.impl.input.KeyboardInputEvent;
import io.github.liticane.clients.feature.event.api.EventListener;
import io.github.liticane.clients.feature.event.Priority;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class ModuleManager extends ArrayList<Module> {

    public void init() {
        this.stream().filter(module -> module.autoEnabled).forEach(m -> m.setToggled(true));
        Client.INSTANCE.getEventManager().register(this);
    }

    public <T extends Module> T get(final String name) {
        // noinspection unchecked
        return (T) this.stream()
                .filter(module -> module.getName().equalsIgnoreCase(name))
                .findAny().orElse(null);
    }

    public <T extends Module> T get(final Class<T> clazz) {
        // noinspection unchecked
        return (T) this.stream()
                .filter(module -> module.getClass() == clazz)
                .findAny().orElse(null);
    }

    public List<Module> get(final Module.Category category) {
        return this.stream()
                .filter(module -> module.getInfo().category() == category)
                .collect(Collectors.toList());
    }

    @SubscribeEvent(value = Priority.VERY_HIGH)
    private final EventListener<KeyboardInputEvent> onKey = event -> {
        if (event.getGuiScreen() != null)
            return;

        this.stream()
                .filter(module -> module.getKeyBind() == event.getKeyCode())
                .forEach(Module::toggle);
    };

    public ArrayList<Module> getModulesFromCategory(Module.Category category) {
        return this.stream()
                .filter(module -> module.getCategory() == category)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
