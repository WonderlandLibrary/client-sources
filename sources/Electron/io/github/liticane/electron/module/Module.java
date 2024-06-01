package io.github.liticane.electron.module;

import io.github.liticane.electron.Electron;
import io.github.liticane.electron.property.Property;
import io.github.liticane.electron.structure.interfaces.Instances;
import io.github.liticane.electron.structure.interfaces.Nameable;
import io.github.liticane.electron.structure.interfaces.Toggleable;
import lombok.Getter;
import lombok.Setter;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class Module implements Nameable, Toggleable, Instances {
    private final String name;
    private final ModuleType type;

    private String suffix;
    private int keyBind;
    private boolean enabled;

    private List<Property<?>> properties = new ArrayList<>();

    public Module(String name, ModuleType type, int keyBind) {
        this.name = name;
        this.type = type;
        this.keyBind = keyBind;
    }

    public Module(String name, ModuleType type) {
        this(name, type, Keyboard.KEY_NONE);
    }

    @Override
    public void toggle() {
        this.setEnabled(!this.enabled);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;

        if (this.enabled) {
            onEnable();
            Electron.getInstance().getEventBus().subscribe(this);
        } else {
            onDisable();
            Electron.getInstance().getEventBus().unsubscribe(this);
        }
    }

    public void registerSetting(Property<?> property) {
        this.properties.add(property);
    }

    public void registerSettings(Property<?> ... properties) {
        this.properties.addAll(Arrays.asList(properties));
    }

}