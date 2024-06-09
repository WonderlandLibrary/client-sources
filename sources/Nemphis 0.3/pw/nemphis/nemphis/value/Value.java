/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.nemphis.value;

import java.io.IOException;
import pw.vertexcode.util.Nameable;
import pw.vertexcode.util.module.types.ToggleableModule;

public class Value<T>
implements Nameable {
    private String label;
    private T value;
    private ToggleableModule module;

    public Value(String label, T value, ToggleableModule module) {
        this.label = label;
        this.value = value;
        this.module = module;
    }

    @Override
    public String getName() {
        return this.label;
    }

    public void setValue(T value) {
        this.value = value;
        try {
            this.getModule().save();
        }
        catch (IOException var2_2) {
            // empty catch block
        }
    }

    public void setValue(T value, boolean dontSave) {
        this.value = value;
        if (dontSave) {
            return;
        }
        try {
            this.getModule().save();
        }
        catch (IOException var3_3) {
            // empty catch block
        }
    }

    public T getValue() {
        return this.value;
    }

    public ToggleableModule getModule() {
        return this.module;
    }

    public void setModule(ToggleableModule module) {
        this.module = module;
    }
}

