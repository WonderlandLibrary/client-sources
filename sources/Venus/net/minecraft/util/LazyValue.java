/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import java.util.function.Supplier;

public class LazyValue<T> {
    private Supplier<T> supplier;
    private T value;

    public LazyValue(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public T getValue() {
        Supplier<T> supplier = this.supplier;
        if (supplier != null) {
            this.value = supplier.get();
            this.supplier = null;
        }
        return this.value;
    }
}

