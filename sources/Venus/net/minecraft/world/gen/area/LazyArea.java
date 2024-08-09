/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.area;

import it.unimi.dsi.fastutil.longs.Long2IntLinkedOpenHashMap;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IPixelTransformer;

public final class LazyArea
implements IArea {
    private final IPixelTransformer pixelTransformer;
    private final Long2IntLinkedOpenHashMap cachedValues;
    private final int maxCacheSize;

    public LazyArea(Long2IntLinkedOpenHashMap long2IntLinkedOpenHashMap, int n, IPixelTransformer iPixelTransformer) {
        this.cachedValues = long2IntLinkedOpenHashMap;
        this.maxCacheSize = n;
        this.pixelTransformer = iPixelTransformer;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int getValue(int n, int n2) {
        long l = ChunkPos.asLong(n, n2);
        Long2IntLinkedOpenHashMap long2IntLinkedOpenHashMap = this.cachedValues;
        synchronized (long2IntLinkedOpenHashMap) {
            int n3 = this.cachedValues.get(l);
            if (n3 != Integer.MIN_VALUE) {
                return n3;
            }
            int n4 = this.pixelTransformer.apply(n, n2);
            this.cachedValues.put(l, n4);
            if (this.cachedValues.size() > this.maxCacheSize) {
                for (int i = 0; i < this.maxCacheSize / 16; ++i) {
                    this.cachedValues.removeFirstInt();
                }
            }
            return n4;
        }
    }

    public int getmaxCacheSize() {
        return this.maxCacheSize;
    }
}

