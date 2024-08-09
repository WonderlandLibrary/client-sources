/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.AbstractMapEntry;
import com.google.common.collect.ForwardingMap;
import com.google.common.collect.Maps;
import com.google.common.collect.TransformedIterator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@GwtCompatible
final class WellBehavedMap<K, V>
extends ForwardingMap<K, V> {
    private final Map<K, V> delegate;
    private Set<Map.Entry<K, V>> entrySet;

    private WellBehavedMap(Map<K, V> map) {
        this.delegate = map;
    }

    static <K, V> WellBehavedMap<K, V> wrap(Map<K, V> map) {
        return new WellBehavedMap<K, V>(map);
    }

    @Override
    protected Map<K, V> delegate() {
        return this.delegate;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = this.entrySet;
        if (set != null) {
            return set;
        }
        this.entrySet = new EntrySet(this, null);
        return this.entrySet;
    }

    @Override
    protected Object delegate() {
        return this.delegate();
    }

    private final class EntrySet
    extends Maps.EntrySet<K, V> {
        final WellBehavedMap this$0;

        private EntrySet(WellBehavedMap wellBehavedMap) {
            this.this$0 = wellBehavedMap;
        }

        @Override
        Map<K, V> map() {
            return this.this$0;
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return new TransformedIterator<K, Map.Entry<K, V>>(this, this.this$0.keySet().iterator()){
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    super(iterator2);
                }

                @Override
                Map.Entry<K, V> transform(K k) {
                    return new AbstractMapEntry<K, V>(this, k){
                        final Object val$key;
                        final 1 this$2;
                        {
                            this.this$2 = var1_1;
                            this.val$key = object;
                        }

                        @Override
                        public K getKey() {
                            return this.val$key;
                        }

                        @Override
                        public V getValue() {
                            return this.this$2.this$1.this$0.get(this.val$key);
                        }

                        @Override
                        public V setValue(V v) {
                            return this.this$2.this$1.this$0.put(this.val$key, v);
                        }
                    };
                }

                @Override
                Object transform(Object object) {
                    return this.transform(object);
                }
            };
        }

        EntrySet(WellBehavedMap wellBehavedMap, com.google.common.collect.WellBehavedMap$1 var2_2) {
            this(wellBehavedMap);
        }
    }
}

