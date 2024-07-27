package dev.nexus.modules.setting;

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

    public void setValue(boolean value) {
        this.value = value;
    }
}
