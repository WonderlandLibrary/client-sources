/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.serialization;

import com.mojang.serialization.Compressable;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.KeyCompressor;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import java.util.Map;

public abstract class CompressorHolder
implements Compressable {
    private final Map<DynamicOps<?>, KeyCompressor<?>> compressors = new Object2ObjectArrayMap();

    @Override
    public <T> KeyCompressor<T> compressor(DynamicOps<T> dynamicOps) {
        return this.compressors.computeIfAbsent(dynamicOps, arg_0 -> this.lambda$compressor$0(dynamicOps, arg_0));
    }

    private KeyCompressor lambda$compressor$0(DynamicOps dynamicOps, DynamicOps dynamicOps2) {
        return new KeyCompressor(dynamicOps, this.keys(dynamicOps));
    }
}

