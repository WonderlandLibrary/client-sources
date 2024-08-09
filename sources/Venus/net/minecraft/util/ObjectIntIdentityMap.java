/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.util.IObjectIntIterable;

public class ObjectIntIdentityMap<T>
implements IObjectIntIterable<T> {
    private int nextId;
    private final IdentityHashMap<T, Integer> identityMap;
    private final List<T> objectList;

    public ObjectIntIdentityMap() {
        this(512);
    }

    public ObjectIntIdentityMap(int n) {
        this.objectList = Lists.newArrayListWithExpectedSize(n);
        this.identityMap = new IdentityHashMap(n);
    }

    public void put(T t, int n) {
        this.identityMap.put(t, n);
        while (this.objectList.size() <= n) {
            this.objectList.add(null);
        }
        this.objectList.set(n, t);
        if (this.nextId <= n) {
            this.nextId = n + 1;
        }
    }

    public void add(T t) {
        this.put(t, this.nextId);
    }

    @Override
    public int getId(T t) {
        Integer n = this.identityMap.get(t);
        return n == null ? -1 : n;
    }

    @Override
    @Nullable
    public final T getByValue(int n) {
        return n >= 0 && n < this.objectList.size() ? (T)this.objectList.get(n) : null;
    }

    @Override
    public Iterator<T> iterator() {
        return Iterators.filter(this.objectList.iterator(), Predicates.notNull());
    }

    public int size() {
        return this.identityMap.size();
    }
}

