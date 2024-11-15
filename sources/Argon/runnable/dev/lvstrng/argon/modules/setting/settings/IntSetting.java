// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.modules.setting.settings;

import dev.lvstrng.argon.modules.setting.Setting;

public final class IntSetting extends Setting<IntSetting> {
    private double min;
    private double max;
    private double value;
    private double increment;

    public IntSetting(final CharSequence name, final double min, final double max, final double value, final double increment) {
        super(name);
        this.min = min;
        this.max = max;
        this.value = value;
        this.increment = increment;
    }

    public int getValueInt() {
        return (int) this.value;
    }

    public float getValueFloat() {
        return (float) this.value;
    }

    public long getValueLong() {
        return (long) this.value;
    }

    public double getMin() {
        return this.min;
    }

    public void setMin(final double min) {
        this.min = min;
    }

    public double getMax() {
        return this.max;
    }

    public void setMax(final double max) {
        this.max = max;
    }

    public double getValue() {
        return this.value;
    }

    public void setValue(final double value) {
        final double n = 1.0 / this.increment;
        this.value = Math.round(Math.max(this.min, Math.min(this.max, value)) * n) / n;
    }

    public double getIncrement() {
        return this.increment;
    }

    public void setIncrement(final double increment) {
        this.increment = increment;
    }
}
