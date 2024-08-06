package club.strifeclient.setting.implementations;

import club.strifeclient.setting.Setting;
import imgui.type.ImFloat;
import lombok.Getter;

import java.util.function.Supplier;

@Getter
public class DoubleSetting extends Setting<Double> {

    private double min, max, increment;
    private ImFloat imEquivalent;

    public DoubleSetting(String name, double value, double min, double max, Supplier<Boolean> dependency) {
        this(name, value, min, max, 1, dependency);
    }

    public DoubleSetting(String name, double value, double min, double max, double increment) {
        this(name, value, min, max, increment, () -> true);
    }

    public DoubleSetting(String name, double value, double min, double max) {
        this(name, value, min, max, 1, () -> true);
    }

    public DoubleSetting(String name, double value, double min, double max, double increment, Supplier<Boolean> dependency) {
        super(name, value, dependency);
        this.min = min;
        this.max = max;
        this.increment = increment;

        imEquivalent = new ImFloat((float)value);
        super.addChangeCallback((settingOld, settingNew) -> imEquivalent.set(settingNew.getValue().floatValue()));
    }

    @Override
    public void setValue(Double value) {
        if (this.value != null && !this.value.equals(value)) {
            if (value < min) value = min;
            if (value > max) value = max;
            super.setValue(value);
        }
    }

    public long getLong() {
        return this.getValue().longValue();
    }
    public double getDouble() {
        return this.getValue();
    }
    public float getFloat() {
        return this.getValue().floatValue();
    }
    public int getInt() {
        return this.getValue().intValue();
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public void parse(Object original) {
        setValue(Double.parseDouble(original.toString()));
    }
}
