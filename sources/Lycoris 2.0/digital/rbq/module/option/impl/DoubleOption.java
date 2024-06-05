/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.option.impl;

import java.util.function.Supplier;
import digital.rbq.module.option.Option;

public final class DoubleOption
extends Option<Double> {
    private final double minValue;
    private final double maxValue;
    private final double increment;

    public DoubleOption(String label, double defaultValue, Supplier<Boolean> supplier, double minValue, double maxValue, double increment) {
        super(label, defaultValue, supplier);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.increment = increment;
    }

    public DoubleOption(String label, double defaultValue, double minValue, double maxValue, double increment) {
        super(label, defaultValue, () -> true);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.increment = increment;
    }

    public double getMinValue() {
        return this.minValue;
    }

    public double getMaxValue() {
        return this.maxValue;
    }

    public double getIncrement() {
        return this.increment;
    }
}

