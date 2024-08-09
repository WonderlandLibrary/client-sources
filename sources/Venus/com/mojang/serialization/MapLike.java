/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.serialization;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DynamicOps;
import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public interface MapLike<T> {
    @Nullable
    public T get(T var1);

    @Nullable
    public T get(String var1);

    public Stream<Pair<T, T>> entries();

    public static <T> MapLike<T> forMap(Map<T, T> map, DynamicOps<T> dynamicOps) {
        return new MapLike<T>(map, dynamicOps){
            final Map val$map;
            final DynamicOps val$ops;
            {
                this.val$map = map;
                this.val$ops = dynamicOps;
            }

            @Override
            @Nullable
            public T get(T t) {
                return this.val$map.get(t);
            }

            @Override
            @Nullable
            public T get(String string) {
                return this.get(this.val$ops.createString(string));
            }

            @Override
            public Stream<Pair<T, T>> entries() {
                return this.val$map.entrySet().stream().map(1::lambda$entries$0);
            }

            public String toString() {
                return "MapLike[" + this.val$map + "]";
            }

            private static Pair lambda$entries$0(Map.Entry entry) {
                return Pair.of(entry.getKey(), entry.getValue());
            }
        };
    }
}

