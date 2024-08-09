package fun.ellant.functions.settings.impl;

import java.util.function.Supplier;
import fun.ellant.functions.settings.Setting;

public class BooleanSetting
        extends Setting<Boolean> {
    public float anim;

    public BooleanSetting(String name, Boolean defaultVal) {
        super(name, defaultVal);
    }

    @Override
    public BooleanSetting setVisible(Supplier<Boolean> bool) {
        return (BooleanSetting) super.setVisible(bool);
    }

    public boolean is(String друзья) {
        return false;
    }
}