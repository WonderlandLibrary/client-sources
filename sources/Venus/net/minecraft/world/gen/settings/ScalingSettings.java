/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.settings;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class ScalingSettings {
    private static final Codec<Double> field_236146_b_ = Codec.doubleRange(0.001, 1000.0);
    public static final Codec<ScalingSettings> field_236145_a_ = RecordCodecBuilder.create(ScalingSettings::lambda$static$0);
    private final double field_236147_c_;
    private final double field_236148_d_;
    private final double field_236149_e_;
    private final double field_236150_f_;

    public ScalingSettings(double d, double d2, double d3, double d4) {
        this.field_236147_c_ = d;
        this.field_236148_d_ = d2;
        this.field_236149_e_ = d3;
        this.field_236150_f_ = d4;
    }

    public double func_236151_a_() {
        return this.field_236147_c_;
    }

    public double func_236153_b_() {
        return this.field_236148_d_;
    }

    public double func_236154_c_() {
        return this.field_236149_e_;
    }

    public double func_236155_d_() {
        return this.field_236150_f_;
    }

    private static App lambda$static$0(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)field_236146_b_.fieldOf("xz_scale")).forGetter(ScalingSettings::func_236151_a_), ((MapCodec)field_236146_b_.fieldOf("y_scale")).forGetter(ScalingSettings::func_236153_b_), ((MapCodec)field_236146_b_.fieldOf("xz_factor")).forGetter(ScalingSettings::func_236154_c_), ((MapCodec)field_236146_b_.fieldOf("y_factor")).forGetter(ScalingSettings::func_236155_d_)).apply(instance, ScalingSettings::new);
    }
}

