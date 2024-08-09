/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.codec;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;

public class DatapackCodec {
    public static final DatapackCodec VANILLA_CODEC = new DatapackCodec(ImmutableList.of("vanilla"), ImmutableList.of());
    public static final Codec<DatapackCodec> CODEC = RecordCodecBuilder.create(DatapackCodec::lambda$static$2);
    private final List<String> enabled;
    private final List<String> disabled;

    public DatapackCodec(List<String> list, List<String> list2) {
        this.enabled = ImmutableList.copyOf(list);
        this.disabled = ImmutableList.copyOf(list2);
    }

    public List<String> getEnabled() {
        return this.enabled;
    }

    public List<String> getDisabled() {
        return this.disabled;
    }

    private static App lambda$static$2(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)Codec.STRING.listOf().fieldOf("Enabled")).forGetter(DatapackCodec::lambda$static$0), ((MapCodec)Codec.STRING.listOf().fieldOf("Disabled")).forGetter(DatapackCodec::lambda$static$1)).apply(instance, DatapackCodec::new);
    }

    private static List lambda$static$1(DatapackCodec datapackCodec) {
        return datapackCodec.disabled;
    }

    private static List lambda$static$0(DatapackCodec datapackCodec) {
        return datapackCodec.enabled;
    }
}

