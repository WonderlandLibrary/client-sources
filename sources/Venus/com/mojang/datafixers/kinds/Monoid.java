/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.kinds;

import com.google.common.collect.ImmutableList;
import java.util.List;

public interface Monoid<T> {
    public T point();

    public T add(T var1, T var2);

    public static <T> Monoid<List<T>> listMonoid() {
        return new Monoid<List<T>>(){

            @Override
            public List<T> point() {
                return ImmutableList.of();
            }

            @Override
            public List<T> add(List<T> list, List<T> list2) {
                ImmutableList.Builder builder = ImmutableList.builder();
                builder.addAll((Iterable)list);
                builder.addAll((Iterable)list2);
                return builder.build();
            }

            @Override
            public Object add(Object object, Object object2) {
                return this.add((List)object, (List)object2);
            }

            @Override
            public Object point() {
                return this.point();
            }
        };
    }
}

