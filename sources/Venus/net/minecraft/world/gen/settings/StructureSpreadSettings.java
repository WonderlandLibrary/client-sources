/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.settings;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class StructureSpreadSettings {
    public static final Codec<StructureSpreadSettings> field_236656_a_ = RecordCodecBuilder.create(StructureSpreadSettings::lambda$static$0);
    private final int field_236657_b_;
    private final int field_236658_c_;
    private final int field_236659_d_;

    public StructureSpreadSettings(int n, int n2, int n3) {
        this.field_236657_b_ = n;
        this.field_236658_c_ = n2;
        this.field_236659_d_ = n3;
    }

    public int func_236660_a_() {
        return this.field_236657_b_;
    }

    public int func_236662_b_() {
        return this.field_236658_c_;
    }

    public int func_236663_c_() {
        return this.field_236659_d_;
    }

    private static App lambda$static$0(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)Codec.intRange(0, 1023).fieldOf("distance")).forGetter(StructureSpreadSettings::func_236660_a_), ((MapCodec)Codec.intRange(0, 1023).fieldOf("spread")).forGetter(StructureSpreadSettings::func_236662_b_), ((MapCodec)Codec.intRange(1, 4095).fieldOf("count")).forGetter(StructureSpreadSettings::func_236663_c_)).apply(instance, StructureSpreadSettings::new);
    }
}

