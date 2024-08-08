package lol.point.returnclient.settings.impl;

import lol.point.returnclient.settings.Setting;

public class NumberSetting extends Setting {
    public Number max;
    public Number min;
    public Number value;
    public int decimalPoints;

    public NumberSetting(String name, Number value, Number min, Number max, int decimalPoints) {
        this.name = name;
        this.value = value;
        this.min = min;
        this.max = max;
        this.decimalPoints = decimalPoints;
    }

    public NumberSetting(String name, Number value, Number min, Number max) {
        this.name = name;
        this.value = value;
        this.min = min;
        this.max = max;
        this.decimalPoints = 0;
    }

}
