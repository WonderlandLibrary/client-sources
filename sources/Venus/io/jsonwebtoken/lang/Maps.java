/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.lang;

import io.jsonwebtoken.lang.Builder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Maps {
    private Maps() {
    }

    public static <K, V> MapBuilder<K, V> of(K k, V v) {
        return new HashMapBuilder<K, V>(null).and(k, v);
    }

    static class 1 {
    }

    private static class HashMapBuilder<K, V>
    implements MapBuilder<K, V> {
        private final Map<K, V> data = new HashMap();

        private HashMapBuilder() {
        }

        @Override
        public MapBuilder<K, V> and(K k, V v) {
            this.data.put(k, v);
            return this;
        }

        @Override
        public Map<K, V> build() {
            return Collections.unmodifiableMap(this.data);
        }

        @Override
        public Object build() {
            return this.build();
        }

        HashMapBuilder(1 var1_1) {
            this();
        }
    }

    public static interface MapBuilder<K, V>
    extends Builder<Map<K, V>> {
        public MapBuilder<K, V> and(K var1, V var2);

        @Override
        public Map<K, V> build();
    }
}

