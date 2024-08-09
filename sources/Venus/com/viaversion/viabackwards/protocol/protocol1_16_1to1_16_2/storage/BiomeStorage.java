/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.storage;

import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.data.BiomeMappings;
import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;

public final class BiomeStorage
implements StorableObject {
    private final Int2IntMap modernToLegacyBiomes = new Int2IntOpenHashMap();

    public BiomeStorage() {
        this.modernToLegacyBiomes.defaultReturnValue(-1);
    }

    public void addBiome(String string, int n) {
        this.modernToLegacyBiomes.put(n, BiomeMappings.toLegacyBiome(string));
    }

    public int legacyBiome(int n) {
        return this.modernToLegacyBiomes.get(n);
    }

    public void clear() {
        this.modernToLegacyBiomes.clear();
    }
}

