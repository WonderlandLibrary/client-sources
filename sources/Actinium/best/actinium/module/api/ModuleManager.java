package best.actinium.module.api;

import best.actinium.event.api.Callback;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.Actinium;
import best.actinium.event.impl.input.KeyboardEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleManager extends ArrayList<Module> {
    public ModuleManager() {
        Actinium.INSTANCE.getEventManager().subscribe(this);
    }

    @Callback
    public void onKeyboardEvent(KeyboardEvent keyboardEvent) {
        this.forEach(module -> {
            if (module.getKeyBind() == keyboardEvent.getKeyCode()) {
                module.toggle();
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <T extends Module> T get(final Class<T> clazz) {
        // noinspection unchecked
        return (T) this.stream()
                .filter(module -> module.getClass() == clazz)
                .findAny().orElse(null);
    }

    public <T extends Module> T get(final String name) {
        // noinspection unchecked
        return (T) this.stream()
                .filter(module -> module.getName().equalsIgnoreCase(name))
                .findAny().orElse(null);
    }

    public <T extends Module> T getToggledModules() {
        return (T) this.stream().filter(Module::isEnabled).collect(Collectors.toList());
    }

    public List<Module> getGetByCategory(ModuleCategory category) {
        return this.stream()
                .filter(module -> module.getCategory() == category)
                .collect(Collectors.toList());
    }
}