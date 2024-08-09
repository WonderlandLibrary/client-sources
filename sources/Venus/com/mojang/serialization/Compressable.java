/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.serialization;

import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.KeyCompressor;
import com.mojang.serialization.Keyable;

public interface Compressable
extends Keyable {
    public <T> KeyCompressor<T> compressor(DynamicOps<T> var1);
}

