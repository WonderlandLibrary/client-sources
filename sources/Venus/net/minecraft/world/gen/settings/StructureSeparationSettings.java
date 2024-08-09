/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.settings;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Function;

public class StructureSeparationSettings {
    public static final Codec<StructureSeparationSettings> field_236664_a_ = RecordCodecBuilder.create(StructureSeparationSettings::lambda$static$3).comapFlatMap(StructureSeparationSettings::lambda$static$4, Function.identity());
    private final int field_236665_b_;
    private final int field_236666_c_;
    private final int field_236667_d_;

    public StructureSeparationSettings(int n, int n2, int n3) {
        this.field_236665_b_ = n;
        this.field_236666_c_ = n2;
        this.field_236667_d_ = n3;
    }

    public int func_236668_a_() {
        return this.field_236665_b_;
    }

    public int func_236671_b_() {
        return this.field_236666_c_;
    }

    public int func_236673_c_() {
        return this.field_236667_d_;
    }

    private static DataResult lambda$static$4(StructureSeparationSettings structureSeparationSettings) {
        return structureSeparationSettings.field_236665_b_ <= structureSeparationSettings.field_236666_c_ ? DataResult.error("Spacing has to be smaller than separation") : DataResult.success(structureSeparationSettings);
    }

    private static App lambda$static$3(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)Codec.intRange(0, 4096).fieldOf("spacing")).forGetter(StructureSeparationSettings::lambda$static$0), ((MapCodec)Codec.intRange(0, 4096).fieldOf("separation")).forGetter(StructureSeparationSettings::lambda$static$1), ((MapCodec)Codec.intRange(0, Integer.MAX_VALUE).fieldOf("salt")).forGetter(StructureSeparationSettings::lambda$static$2)).apply(instance, StructureSeparationSettings::new);
    }

    private static Integer lambda$static$2(StructureSeparationSettings structureSeparationSettings) {
        return structureSeparationSettings.field_236667_d_;
    }

    private static Integer lambda$static$1(StructureSeparationSettings structureSeparationSettings) {
        return structureSeparationSettings.field_236666_c_;
    }

    private static Integer lambda$static$0(StructureSeparationSettings structureSeparationSettings) {
        return structureSeparationSettings.field_236665_b_;
    }
}

