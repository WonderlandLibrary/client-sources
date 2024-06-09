/*
 * Decompiled with CFR 0_122.
 */
package winter.utils.value.types;

import winter.utils.value.Value;

public class BooleanValue
extends Value {
    private String name;
    private boolean value;

    public BooleanValue(String name, boolean value) {
        super(name);
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public boolean isEnabled() {
        return this.value;
    }

    public void toggle() {
        this.value = !this.value;
    }

    public void set(boolean newb) {
        this.value = newb;
    }
}

