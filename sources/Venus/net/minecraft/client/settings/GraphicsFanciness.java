/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.settings;

import java.util.Arrays;
import java.util.Comparator;
import net.minecraft.util.math.MathHelper;

public enum GraphicsFanciness {
    FAST(0, "options.graphics.fast"),
    FANCY(1, "options.graphics.fancy"),
    FABULOUS(2, "options.graphics.fabulous");

    private static final GraphicsFanciness[] field_238159_d_;
    private final int field_238160_e_;
    private final String field_238161_f_;

    private GraphicsFanciness(int n2, String string2) {
        this.field_238160_e_ = n2;
        this.field_238161_f_ = string2;
    }

    public int func_238162_a_() {
        return this.field_238160_e_;
    }

    public String func_238164_b_() {
        return this.field_238161_f_;
    }

    public GraphicsFanciness func_238166_c_() {
        return GraphicsFanciness.func_238163_a_(this.func_238162_a_() + 1);
    }

    public String toString() {
        switch (1.$SwitchMap$net$minecraft$client$settings$GraphicsFanciness[this.ordinal()]) {
            case 1: {
                return "fast";
            }
            case 2: {
                return "fancy";
            }
            case 3: {
                return "fabulous";
            }
        }
        throw new IllegalArgumentException();
    }

    public static GraphicsFanciness func_238163_a_(int n) {
        return field_238159_d_[MathHelper.normalizeAngle(n, field_238159_d_.length)];
    }

    private static GraphicsFanciness[] lambda$static$0(int n) {
        return new GraphicsFanciness[n];
    }

    static {
        field_238159_d_ = (GraphicsFanciness[])Arrays.stream(GraphicsFanciness.values()).sorted(Comparator.comparingInt(GraphicsFanciness::func_238162_a_)).toArray(GraphicsFanciness::lambda$static$0);
    }
}

