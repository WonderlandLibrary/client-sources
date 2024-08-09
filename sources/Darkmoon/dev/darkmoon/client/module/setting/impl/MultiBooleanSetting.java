package dev.darkmoon.client.module.setting.impl;

import dev.darkmoon.client.module.setting.Setting;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class MultiBooleanSetting extends Setting {
    public List<Boolean> selectedValues = new ArrayList<>();
    public List<String> values;

    public MultiBooleanSetting(String name, List<String> values) {
        this(name, values, null);
        setVisible(() -> true);

    }

    public MultiBooleanSetting(String name, List<String> values, Supplier<Boolean> visible) {
        super(name, visible);
        this.values = values;
        for (int i = 0; i < values.size(); i++) {
            this.selectedValues.add(false);
        }

        setVisible(visible);
    }

    public MultiBooleanSetting(String name, List<String> values, boolean allEnabled, Supplier<Boolean> visible) {
        super(name, visible);
        this.values = values;
        for (int i = 0; i < values.size(); i++) {
            this.selectedValues.add(allEnabled);
        }

        setVisible(visible);
    }

    public boolean get(int id) {
        return this.selectedValues.get(id);
    }

    public void set(int id, Boolean value) {
        this.selectedValues.set(id, value);
    }
}
