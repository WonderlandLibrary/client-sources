/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.attributes;

import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.util.math.MathHelper;

public class RangedAttribute
extends Attribute {
    private final double minimumValue;
    private final double maximumValue;

    public RangedAttribute(String string, double d, double d2, double d3) {
        super(string, d);
        this.minimumValue = d2;
        this.maximumValue = d3;
        if (d2 > d3) {
            throw new IllegalArgumentException("Minimum value cannot be bigger than maximum value!");
        }
        if (d < d2) {
            throw new IllegalArgumentException("Default value cannot be lower than minimum value!");
        }
        if (d > d3) {
            throw new IllegalArgumentException("Default value cannot be bigger than maximum value!");
        }
    }

    @Override
    public double clampValue(double d) {
        return MathHelper.clamp(d, this.minimumValue, this.maximumValue);
    }
}

