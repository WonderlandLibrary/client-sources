/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.minecraft.chunks;

import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrayList;
import com.viaversion.viaversion.libs.fastutil.ints.IntList;

public final class DataPaletteImpl
implements DataPalette {
    private final IntList palette;
    private final Int2IntMap inversePalette;
    private final int[] values;
    private final int sizeBits;

    public DataPaletteImpl(int n) {
        this(n, 8);
    }

    public DataPaletteImpl(int n, int n2) {
        this.values = new int[n];
        this.sizeBits = Integer.numberOfTrailingZeros(n) / 3;
        this.palette = new IntArrayList(n2);
        this.inversePalette = new Int2IntOpenHashMap(n2);
        this.inversePalette.defaultReturnValue(-1);
    }

    @Override
    public int index(int n, int n2, int n3) {
        return (n2 << this.sizeBits | n3) << this.sizeBits | n;
    }

    @Override
    public int idAt(int n) {
        int n2 = this.values[n];
        return this.palette.getInt(n2);
    }

    @Override
    public void setIdAt(int n, int n2) {
        int n3 = this.inversePalette.get(n2);
        if (n3 == -1) {
            n3 = this.palette.size();
            this.palette.add(n2);
            this.inversePalette.put(n2, n3);
        }
        this.values[n] = n3;
    }

    @Override
    public int paletteIndexAt(int n) {
        return this.values[n];
    }

    @Override
    public void setPaletteIndexAt(int n, int n2) {
        this.values[n] = n2;
    }

    @Override
    public int size() {
        return this.palette.size();
    }

    @Override
    public int idByIndex(int n) {
        return this.palette.getInt(n);
    }

    @Override
    public void setIdByIndex(int n, int n2) {
        int n3 = this.palette.set(n, n2);
        if (n3 == n2) {
            return;
        }
        this.inversePalette.put(n2, n);
        if (this.inversePalette.get(n3) == n) {
            this.inversePalette.remove(n3);
            for (int i = 0; i < this.palette.size(); ++i) {
                if (this.palette.getInt(i) != n3) continue;
                this.inversePalette.put(n3, i);
                break;
            }
        }
    }

    @Override
    public void replaceId(int n, int n2) {
        int n3 = this.inversePalette.remove(n);
        if (n3 == -1) {
            return;
        }
        this.inversePalette.put(n2, n3);
        for (int i = 0; i < this.palette.size(); ++i) {
            if (this.palette.getInt(i) != n) continue;
            this.palette.set(i, n2);
        }
    }

    @Override
    public void addId(int n) {
        this.inversePalette.put(n, this.palette.size());
        this.palette.add(n);
    }

    @Override
    public void clear() {
        this.palette.clear();
        this.inversePalette.clear();
    }
}

