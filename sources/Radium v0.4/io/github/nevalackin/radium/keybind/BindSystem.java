package io.github.nevalackin.radium.keybind;

import io.github.nevalackin.radium.event.impl.KeyPressEvent;
import io.github.nevalackin.radium.module.Module;
import me.zane.basicbus.api.annotations.Listener;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class BindSystem {

    private final Set<Bindable> objects;

    public BindSystem(Collection<Module> modules) {
        objects = new HashSet<>(modules);
    }

    public void register(Bindable object) {
        objects.add(object);
    }

    @Listener
    public void onKeyPress(KeyPressEvent e) {
        for (Bindable object : objects) {
            if (object.getKey() == e.getKey()) {
                object.onPress();
            }
        }
    }
}
