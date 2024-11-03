package dev.stephen.nexus.module.setting.impl;

import dev.stephen.nexus.module.setting.Setting;
import lombok.Setter;

@Setter
public class BooleanSetting extends Setting {
    private boolean value;

    public BooleanSetting(String name, boolean value) {
        super(name);
        this.value = value;
    }

    public void toggle() {
        setValue(!value);
    }

    public boolean getValue() {
        return value;
    }

}
