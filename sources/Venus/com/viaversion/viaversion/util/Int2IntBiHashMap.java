/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.util;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSet;
import com.viaversion.viaversion.util.Int2IntBiMap;
import java.util.Collection;
import java.util.Set;
import org.checkerframework.checker.nullness.qual.NonNull;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Int2IntBiHashMap
implements Int2IntBiMap {
    private final Int2IntMap map;
    private final Int2IntBiHashMap inverse;

    public Int2IntBiHashMap() {
        this.map = new Int2IntOpenHashMap();
        this.inverse = new Int2IntBiHashMap(this, -1);
    }

    public Int2IntBiHashMap(int n) {
        this.map = new Int2IntOpenHashMap(n);
        this.inverse = new Int2IntBiHashMap(this, n);
    }

    private Int2IntBiHashMap(Int2IntBiHashMap int2IntBiHashMap, int n) {
        this.map = n != -1 ? new Int2IntOpenHashMap(n) : new Int2IntOpenHashMap();
        this.inverse = int2IntBiHashMap;
    }

    @Override
    public Int2IntBiMap inverse() {
        return this.inverse;
    }

    @Override
    public int put(int n, int n2) {
        if (this.containsKey(n) && n2 == this.get(n)) {
            return n2;
        }
        Preconditions.checkArgument(!this.containsValue(n2), "value already present: %s", new Object[]{n2});
        this.map.put(n, n2);
        this.inverse.map.put(n2, n);
        return this.defaultReturnValue();
    }

    @Override
    public boolean remove(int n, int n2) {
        this.map.remove(n, n2);
        return this.inverse.map.remove(n, n2);
    }

    @Override
    public int get(int n) {
        return this.map.get(n);
    }

    @Override
    public void clear() {
        this.map.clear();
        this.inverse.map.clear();
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public void defaultReturnValue(int n) {
        this.map.defaultReturnValue(n);
        this.inverse.map.defaultReturnValue(n);
    }

    @Override
    public int defaultReturnValue() {
        return this.map.defaultReturnValue();
    }

    @Override
    public ObjectSet<Int2IntMap.Entry> int2IntEntrySet() {
        return this.map.int2IntEntrySet();
    }

    @Override
    public @NonNull IntSet keySet() {
        return this.map.keySet();
    }

    @Override
    public @NonNull IntSet values() {
        return this.inverse.map.keySet();
    }

    @Override
    public boolean containsKey(int n) {
        return this.map.containsKey(n);
    }

    @Override
    public boolean containsValue(int n) {
        return this.inverse.map.containsKey(n);
    }

    @Override
    public @NonNull IntCollection values() {
        return this.values();
    }

    @Override
    public @NonNull Collection values() {
        return this.values();
    }

    @Override
    public @NonNull Set keySet() {
        return this.keySet();
    }
}

