/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.option;

import java.util.function.Supplier;

public class Option<T> {
    private final String label;
    private T value;
    private final Supplier<Boolean> supplier;

    public Option(String label, T defaultValue, Supplier<Boolean> supplier) {
        this.label = label;
        this.value = defaultValue;
        this.supplier = supplier;
    }

    public boolean check() {
        return this.supplier.get();
    }

    public String getLabel() {
        return this.label;
    }

    public T getValue() {
        return this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}

