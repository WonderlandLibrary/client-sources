package dev.darkmoon.client.module.setting.impl;

import dev.darkmoon.client.module.setting.Setting;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class ModeSetting extends Setting {
    public final List<String> modes;
    public String currentMode;
    public int index;

    public ModeSetting(String name, String currentMode, String... options) {
        super(name, currentMode);
        this.modes = Arrays.asList(options);
        this.index = modes.indexOf(currentMode);
        this.currentMode = modes.get(index);
        this.setVisible(() -> true);
    }

    public ModeSetting(String name, String currentMode, Supplier<Boolean> visible, String... options) {
        super(name, currentMode);
        this.modes = Arrays.asList(options);
        this.index = modes.indexOf(currentMode);
        this.currentMode = modes.get(index);
        setVisible(visible);
    }

    public boolean is(String mode) {
        return currentMode.equals(mode);
    }

    public String get() {
        return currentMode;
    }

    public void set(String selected) {
        this.currentMode = selected;
        this.index = this.modes.indexOf(selected);
    }

    public List<String> getModes() {
        return modes;
    }

    public String getOptions() {
        return this.modes.get(this.index);
    }
}

