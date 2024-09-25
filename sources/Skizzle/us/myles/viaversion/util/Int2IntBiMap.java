/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 *  org.jetbrains.annotations.NotNull
 */
package us.myles.ViaVersion.util;

import com.google.common.base.Preconditions;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import us.myles.viaversion.libs.fastutil.ints.Int2IntMap;
import us.myles.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
import us.myles.viaversion.libs.fastutil.ints.IntSet;
import us.myles.viaversion.libs.fastutil.objects.ObjectSet;

public class Int2IntBiMap
implements Int2IntMap {
    private final Int2IntMap map = new Int2IntOpenHashMap();
    private final Int2IntBiMap inverse;

    public Int2IntBiMap() {
        this.inverse = new Int2IntBiMap(this);
    }

    private Int2IntBiMap(Int2IntBiMap inverse) {
        this.inverse = inverse;
    }

    public Int2IntBiMap inverse() {
        return this.inverse;
    }

    @Override
    public int put(int key, int value) {
        if (this.containsKey(key) && value == this.get(key)) {
            return value;
        }
        Preconditions.checkArgument((!this.containsValue(value) ? 1 : 0) != 0, (String)"value already present: %s", (Object[])new Object[]{value});
        this.map.put(key, value);
        this.inverse.map.put(value, key);
        return this.defaultReturnValue();
    }

    @Override
    public boolean remove(int key, int value) {
        this.map.remove(key, value);
        return this.inverse.map.remove(key, value);
    }

    @Override
    public int get(int key) {
        return this.map.get(key);
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
    @Deprecated
    public void putAll(@NotNull Map<? extends Integer, ? extends Integer> m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void defaultReturnValue(int rv) {
        this.map.defaultReturnValue(rv);
        this.inverse.map.defaultReturnValue(rv);
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
    @NotNull
    public IntSet keySet() {
        return this.map.keySet();
    }

    @Override
    @NotNull
    public IntSet values() {
        return this.inverse.map.keySet();
    }

    @Override
    public boolean containsKey(int key) {
        return this.map.containsKey(key);
    }

    @Override
    public boolean containsValue(int value) {
        return this.inverse.map.containsKey(value);
    }
}

