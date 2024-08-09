package im.expensive.functions.settings.impl;


import im.expensive.functions.settings.Setting;

import java.util.function.Supplier;

public class SliderSetting extends Setting<Float> {

    public float min;
    public float max;
    public float increment;

    public SliderSetting(String name, float defaultVal, float min, float max, float increment) {
        super(name, defaultVal);
        this.min = min;
        this.max = max;
        this.increment = increment;
    }

    @Override
    public SliderSetting setVisible(Supplier<Boolean> bool) {
        return (SliderSetting) super.setVisible(bool);
    }
}