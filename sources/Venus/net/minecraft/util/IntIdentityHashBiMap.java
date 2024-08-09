/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
import java.util.Arrays;
import java.util.Iterator;
import javax.annotation.Nullable;
import net.minecraft.util.IObjectIntIterable;
import net.minecraft.util.math.MathHelper;

public class IntIdentityHashBiMap<K>
implements IObjectIntIterable<K> {
    private static final Object EMPTY = null;
    private K[] values;
    private int[] intKeys;
    private K[] byId;
    private int nextFreeIndex;
    private int mapSize;

    public IntIdentityHashBiMap(int n) {
        n = (int)((float)n / 0.8f);
        this.values = new Object[n];
        this.intKeys = new int[n];
        this.byId = new Object[n];
    }

    @Override
    public int getId(@Nullable K k) {
        return this.getValue(this.getIndex(k, this.hashObject(k)));
    }

    @Override
    @Nullable
    public K getByValue(int n) {
        return n >= 0 && n < this.byId.length ? (K)this.byId[n] : null;
    }

    private int getValue(int n) {
        return n == -1 ? -1 : this.intKeys[n];
    }

    public int add(K k) {
        int n = this.nextId();
        this.put(k, n);
        return n;
    }

    private int nextId() {
        while (this.nextFreeIndex < this.byId.length && this.byId[this.nextFreeIndex] != null) {
            ++this.nextFreeIndex;
        }
        return this.nextFreeIndex;
    }

    private void grow(int n) {
        K[] KArray = this.values;
        int[] nArray = this.intKeys;
        this.values = new Object[n];
        this.intKeys = new int[n];
        this.byId = new Object[n];
        this.nextFreeIndex = 0;
        this.mapSize = 0;
        for (int i = 0; i < KArray.length; ++i) {
            if (KArray[i] == null) continue;
            this.put(KArray[i], nArray[i]);
        }
    }

    public void put(K k, int n) {
        int n2;
        int n3 = Math.max(n, this.mapSize + 1);
        if ((float)n3 >= (float)this.values.length * 0.8f) {
            for (n2 = this.values.length << 1; n2 < n; n2 <<= 1) {
            }
            this.grow(n2);
        }
        n2 = this.findEmpty(this.hashObject(k));
        this.values[n2] = k;
        this.intKeys[n2] = n;
        this.byId[n] = k;
        ++this.mapSize;
        if (n == this.nextFreeIndex) {
            ++this.nextFreeIndex;
        }
    }

    private int hashObject(@Nullable K k) {
        return (MathHelper.hash(System.identityHashCode(k)) & Integer.MAX_VALUE) % this.values.length;
    }

    private int getIndex(@Nullable K k, int n) {
        int n2;
        for (n2 = n; n2 < this.values.length; ++n2) {
            if (this.values[n2] == k) {
                return n2;
            }
            if (this.values[n2] != EMPTY) continue;
            return 1;
        }
        for (n2 = 0; n2 < n; ++n2) {
            if (this.values[n2] == k) {
                return n2;
            }
            if (this.values[n2] != EMPTY) continue;
            return 1;
        }
        return 1;
    }

    private int findEmpty(int n) {
        int n2;
        for (n2 = n; n2 < this.values.length; ++n2) {
            if (this.values[n2] != EMPTY) continue;
            return n2;
        }
        for (n2 = 0; n2 < n; ++n2) {
            if (this.values[n2] != EMPTY) continue;
            return n2;
        }
        throw new RuntimeException("Overflowed :(");
    }

    @Override
    public Iterator<K> iterator() {
        return Iterators.filter(Iterators.forArray(this.byId), Predicates.notNull());
    }

    public void clear() {
        Arrays.fill(this.values, null);
        Arrays.fill(this.byId, null);
        this.nextFreeIndex = 0;
        this.mapSize = 0;
    }

    public int size() {
        return this.mapSize;
    }
}

