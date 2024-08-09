/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClassInheritanceMultiMap<T>
extends AbstractCollection<T> {
    private final Map<Class<?>, List<T>> map = Maps.newHashMap();
    private final Class<T> baseClass;
    private final List<T> values = Lists.newArrayList();

    public ClassInheritanceMultiMap(Class<T> clazz) {
        this.baseClass = clazz;
        this.map.put(clazz, this.values);
    }

    @Override
    public boolean add(T t) {
        boolean bl = false;
        for (Map.Entry<Class<?>, List<T>> entry : this.map.entrySet()) {
            if (!entry.getKey().isInstance(t)) continue;
            bl |= entry.getValue().add(t);
        }
        return bl;
    }

    @Override
    public boolean remove(Object object) {
        boolean bl = false;
        for (Map.Entry<Class<?>, List<T>> entry : this.map.entrySet()) {
            if (!entry.getKey().isInstance(object)) continue;
            List<T> list = entry.getValue();
            bl |= list.remove(object);
        }
        return bl;
    }

    @Override
    public boolean contains(Object object) {
        return this.getByClass(object.getClass()).contains(object);
    }

    public <S> Collection<S> getByClass(Class<S> clazz) {
        if (!this.baseClass.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException("Don't know how to search for " + clazz);
        }
        List list = this.map.computeIfAbsent(clazz, this::lambda$getByClass$0);
        return Collections.unmodifiableCollection(list);
    }

    @Override
    public Iterator<T> iterator() {
        return this.values.isEmpty() ? Collections.emptyIterator() : Iterators.unmodifiableIterator(this.values.iterator());
    }

    public List<T> func_241289_a_() {
        return ImmutableList.copyOf(this.values);
    }

    @Override
    public int size() {
        return this.values.size();
    }

    private List lambda$getByClass$0(Class clazz) {
        return this.values.stream().filter(clazz::isInstance).collect(Collectors.toList());
    }
}

