/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.settings;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class SlideSettings {
    public static final Codec<SlideSettings> field_236182_a_ = RecordCodecBuilder.create(SlideSettings::lambda$static$0);
    private final int field_236183_b_;
    private final int field_236184_c_;
    private final int field_236185_d_;

    public SlideSettings(int n, int n2, int n3) {
        this.field_236183_b_ = n;
        this.field_236184_c_ = n2;
        this.field_236185_d_ = n3;
    }

    public int func_236186_a_() {
        return this.field_236183_b_;
    }

    public int func_236188_b_() {
        return this.field_236184_c_;
    }

    public int func_236189_c_() {
        return this.field_236185_d_;
    }

    private static App lambda$static$0(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)Codec.INT.fieldOf("target")).forGetter(SlideSettings::func_236186_a_), ((MapCodec)Codec.intRange(0, 256).fieldOf("size")).forGetter(SlideSettings::func_236188_b_), ((MapCodec)Codec.INT.fieldOf("offset")).forGetter(SlideSettings::func_236189_c_)).apply(instance, SlideSettings::new);
    }
}

