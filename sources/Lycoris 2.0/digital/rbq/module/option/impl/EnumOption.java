/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.option.impl;

import java.util.function.Supplier;
import digital.rbq.module.option.Option;

public final class EnumOption<T extends Enum>
extends Option<T> {
    private final T[] values = (T[])((Enum)this.getValue()).getClass().getEnumConstants();

    public EnumOption(String label, T defaultValue, Supplier<Boolean> supplier) {
        super(label, defaultValue, supplier);
    }

    public EnumOption(String label, T defaultValue) {
        super(label, defaultValue, () -> true);
    }

    public T getValueOrNull(String constantName) {
        for (T value : this.values) {
            if (!((Enum)value).name().equals(constantName)) continue;
            return value;
        }
        return null;
    }

    public final T[] getValues() {
        return this.values;
    }
}

