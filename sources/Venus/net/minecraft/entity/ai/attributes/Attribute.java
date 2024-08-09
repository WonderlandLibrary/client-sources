/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.attributes;

public class Attribute {
    private final double defaultValue;
    private boolean shouldWatch;
    private final String attributeName;

    protected Attribute(String string, double d) {
        this.defaultValue = d;
        this.attributeName = string;
    }

    public double getDefaultValue() {
        return this.defaultValue;
    }

    public boolean getShouldWatch() {
        return this.shouldWatch;
    }

    public Attribute setShouldWatch(boolean bl) {
        this.shouldWatch = bl;
        return this;
    }

    public double clampValue(double d) {
        return d;
    }

    public String getAttributeName() {
        return this.attributeName;
    }
}

