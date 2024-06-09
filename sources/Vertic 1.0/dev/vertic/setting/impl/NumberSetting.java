package dev.vertic.setting.impl;

import dev.vertic.setting.Setting;
import lombok.Getter;
import lombok.Setter;

import java.util.function.BooleanSupplier;

@Getter
@Setter
public class NumberSetting extends Setting {

    private double value;
    private double minimum;
    private double maximum;
    private double increment;

    public NumberSetting(final String name, final double defaultValue, final double minimumValue, final double maximumValue, final double incrementalValue) {
        super(name);
        this.value = defaultValue;
        this.minimum = minimumValue;
        this.maximum = maximumValue;
        this.increment = incrementalValue;
    }

    public NumberSetting(final String name, final BooleanSupplier visibility, final double defaultValue, final double minimumValue, final double maximumValue, final double incrementalValue) {
        super(name, visibility);
        this.value = defaultValue;
        this.minimum = minimumValue;
        this.maximum = maximumValue;
        this.increment = incrementalValue;
    }

    public int getInt() {
        return (int) value;
    }
    public double getDouble() {
        return value;
    }
    public float getFloat() {
        return (float) value;
    }
    public long getLong() {
        return (long) value;
    }

}
