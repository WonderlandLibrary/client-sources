package club.mega.module.setting.impl;

import club.mega.module.Module;
import club.mega.module.setting.Setting;

import java.util.function.Supplier;

public class NumberSetting extends Setting {

    private final double min, max, increment;
    private double current;

    public NumberSetting(final String name, final Module parent, final double min, final double max, final double current, final double increment, final boolean configurable, final Supplier<Boolean> visible) {
        super(name, parent, configurable, visible);
        this.min = min;
        this.max = max;
        this.current = current;
        this.increment = increment;
    }

    public NumberSetting(final String name, final Module parent, final double min, final double max, final double current, final double increment, final Supplier<Boolean> visible) {
        this(name, parent, min, max, current, increment, true, visible);
    }

    public NumberSetting(final String name, final Module parent, final double min, final double max, final double current, final double increment, final boolean configurable) {
        this(name, parent, min, max, current, increment, configurable, () -> true);
    }

    public NumberSetting(final String name, final Module parent, final double min, final double max, final double current, final double increment) {
        this(name, parent, min, max, current, increment, true, () -> true);
    }

    public final double getMin() {
        return min;
    }

    public final double getMax() {
        return max;
    }

    public final double getAsDouble() {
        return current;
    }

    public final float getAsFloat() {
        return (float) current;
    }

    public final long getAsLong() {
        return (long) current;
    }

    public final int getAsInt() {
        return (int) current;
    }

    public final void setCurrent(final double current) {
        this.current = current;
    }

    public final double getIncrement() {
        return increment;
    }

}
