/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.option.impl;

import java.util.function.Supplier;
import digital.rbq.module.option.Option;

public final class BoolOption
extends Option<Boolean> {
    public BoolOption(String label, Boolean defaultValue, Supplier<Boolean> supplier) {
        super(label, defaultValue, supplier);
    }

    public BoolOption(String label, Boolean defaultValue) {
        super(label, defaultValue, () -> true);
    }

    @Override
    public Boolean getValue() {
        return (Boolean)super.getValue() != false && this.check();
    }
}

