/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.settings;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.settings.ScalingSettings;
import net.minecraft.world.gen.settings.SlideSettings;

public class NoiseSettings {
    public static final Codec<NoiseSettings> field_236156_a_ = RecordCodecBuilder.create(NoiseSettings::lambda$static$0);
    private final int field_236157_b_;
    private final ScalingSettings field_236158_c_;
    private final SlideSettings field_236159_d_;
    private final SlideSettings field_236160_e_;
    private final int field_236161_f_;
    private final int field_236162_g_;
    private final double field_236163_h_;
    private final double field_236164_i_;
    private final boolean field_236165_j_;
    private final boolean field_236166_k_;
    private final boolean field_236167_l_;
    private final boolean field_236168_m_;

    public NoiseSettings(int n, ScalingSettings scalingSettings, SlideSettings slideSettings, SlideSettings slideSettings2, int n2, int n3, double d, double d2, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        this.field_236157_b_ = n;
        this.field_236158_c_ = scalingSettings;
        this.field_236159_d_ = slideSettings;
        this.field_236160_e_ = slideSettings2;
        this.field_236161_f_ = n2;
        this.field_236162_g_ = n3;
        this.field_236163_h_ = d;
        this.field_236164_i_ = d2;
        this.field_236165_j_ = bl;
        this.field_236166_k_ = bl2;
        this.field_236167_l_ = bl3;
        this.field_236168_m_ = bl4;
    }

    public int func_236169_a_() {
        return this.field_236157_b_;
    }

    public ScalingSettings func_236171_b_() {
        return this.field_236158_c_;
    }

    public SlideSettings func_236172_c_() {
        return this.field_236159_d_;
    }

    public SlideSettings func_236173_d_() {
        return this.field_236160_e_;
    }

    public int func_236174_e_() {
        return this.field_236161_f_;
    }

    public int func_236175_f_() {
        return this.field_236162_g_;
    }

    public double func_236176_g_() {
        return this.field_236163_h_;
    }

    public double func_236177_h_() {
        return this.field_236164_i_;
    }

    @Deprecated
    public boolean func_236178_i_() {
        return this.field_236165_j_;
    }

    @Deprecated
    public boolean func_236179_j_() {
        return this.field_236166_k_;
    }

    @Deprecated
    public boolean func_236180_k_() {
        return this.field_236167_l_;
    }

    @Deprecated
    public boolean func_236181_l_() {
        return this.field_236168_m_;
    }

    private static App lambda$static$0(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)Codec.intRange(0, 256).fieldOf("height")).forGetter(NoiseSettings::func_236169_a_), ((MapCodec)ScalingSettings.field_236145_a_.fieldOf("sampling")).forGetter(NoiseSettings::func_236171_b_), ((MapCodec)SlideSettings.field_236182_a_.fieldOf("top_slide")).forGetter(NoiseSettings::func_236172_c_), ((MapCodec)SlideSettings.field_236182_a_.fieldOf("bottom_slide")).forGetter(NoiseSettings::func_236173_d_), ((MapCodec)Codec.intRange(1, 4).fieldOf("size_horizontal")).forGetter(NoiseSettings::func_236174_e_), ((MapCodec)Codec.intRange(1, 4).fieldOf("size_vertical")).forGetter(NoiseSettings::func_236175_f_), ((MapCodec)Codec.DOUBLE.fieldOf("density_factor")).forGetter(NoiseSettings::func_236176_g_), ((MapCodec)Codec.DOUBLE.fieldOf("density_offset")).forGetter(NoiseSettings::func_236177_h_), ((MapCodec)Codec.BOOL.fieldOf("simplex_surface_noise")).forGetter(NoiseSettings::func_236178_i_), Codec.BOOL.optionalFieldOf("random_density_offset", false, Lifecycle.experimental()).forGetter(NoiseSettings::func_236179_j_), Codec.BOOL.optionalFieldOf("island_noise_override", false, Lifecycle.experimental()).forGetter(NoiseSettings::func_236180_k_), Codec.BOOL.optionalFieldOf("amplified", false, Lifecycle.experimental()).forGetter(NoiseSettings::func_236181_l_)).apply(instance, NoiseSettings::new);
    }
}

