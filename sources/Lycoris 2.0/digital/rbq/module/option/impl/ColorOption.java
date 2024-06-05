/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.option.impl;

import java.awt.Color;
import java.util.function.Supplier;
import digital.rbq.module.option.Option;

public class ColorOption
extends Option<Color> {
    public ColorOption(String label, Color defaultValue, Supplier<Boolean> supplier) {
        super(label, defaultValue, supplier);
    }

    public ColorOption(String label, Color defaultValue) {
        super(label, defaultValue, () -> true);
    }
}

