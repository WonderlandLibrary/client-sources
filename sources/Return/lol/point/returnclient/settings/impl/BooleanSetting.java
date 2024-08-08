package lol.point.returnclient.settings.impl;

import lol.point.returnclient.settings.Setting;

public class BooleanSetting extends Setting {
    public boolean value;

    public BooleanSetting(String name, boolean state) {
        this.name = name;
        this.value = state;
    }

    public void toggle() {
        value = !value;
    }
}
