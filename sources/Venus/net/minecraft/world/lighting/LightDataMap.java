/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.lighting;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import javax.annotation.Nullable;
import net.minecraft.world.chunk.NibbleArray;

public abstract class LightDataMap<M extends LightDataMap<M>> {
    private final long[] recentPositions = new long[2];
    private final NibbleArray[] recentArrays = new NibbleArray[2];
    private boolean useCaching;
    protected final Long2ObjectOpenHashMap<NibbleArray> arrays;

    protected LightDataMap(Long2ObjectOpenHashMap<NibbleArray> long2ObjectOpenHashMap) {
        this.arrays = long2ObjectOpenHashMap;
        this.invalidateCaches();
        this.useCaching = true;
    }

    public abstract M copy();

    public void copyArray(long l) {
        this.arrays.put(l, this.arrays.get(l).copy());
        this.invalidateCaches();
    }

    public boolean hasArray(long l) {
        return this.arrays.containsKey(l);
    }

    @Nullable
    public NibbleArray getArray(long l) {
        NibbleArray nibbleArray;
        if (this.useCaching) {
            for (int i = 0; i < 2; ++i) {
                if (l != this.recentPositions[i]) continue;
                return this.recentArrays[i];
            }
        }
        if ((nibbleArray = this.arrays.get(l)) == null) {
            return null;
        }
        if (this.useCaching) {
            for (int i = 1; i > 0; --i) {
                this.recentPositions[i] = this.recentPositions[i - 1];
                this.recentArrays[i] = this.recentArrays[i - 1];
            }
            this.recentPositions[0] = l;
            this.recentArrays[0] = nibbleArray;
        }
        return nibbleArray;
    }

    @Nullable
    public NibbleArray removeArray(long l) {
        return this.arrays.remove(l);
    }

    public void setArray(long l, NibbleArray nibbleArray) {
        this.arrays.put(l, nibbleArray);
    }

    public void invalidateCaches() {
        for (int i = 0; i < 2; ++i) {
            this.recentPositions[i] = Long.MAX_VALUE;
            this.recentArrays[i] = null;
        }
    }

    public void disableCaching() {
        this.useCaching = false;
    }
}

