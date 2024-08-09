/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.Hashing;
import com.google.common.collect.ImmutableAsList;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMapEntry;
import com.google.common.collect.ImmutableMapEntrySet;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.RegularImmutableMap;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.concurrent.LazyInit;
import com.google.j2objc.annotations.RetainedWith;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javax.annotation.Nullable;

@GwtCompatible(serializable=true, emulated=true)
class RegularImmutableBiMap<K, V>
extends ImmutableBiMap<K, V> {
    static final RegularImmutableBiMap<Object, Object> EMPTY = new RegularImmutableBiMap(null, null, ImmutableMap.EMPTY_ENTRY_ARRAY, 0, 0);
    static final double MAX_LOAD_FACTOR = 1.2;
    private final transient ImmutableMapEntry<K, V>[] keyTable;
    private final transient ImmutableMapEntry<K, V>[] valueTable;
    private final transient Map.Entry<K, V>[] entries;
    private final transient int mask;
    private final transient int hashCode;
    @LazyInit
    @RetainedWith
    private transient ImmutableBiMap<V, K> inverse;

    static <K, V> RegularImmutableBiMap<K, V> fromEntries(Map.Entry<K, V> ... entryArray) {
        return RegularImmutableBiMap.fromEntryArray(entryArray.length, entryArray);
    }

    static <K, V> RegularImmutableBiMap<K, V> fromEntryArray(int n, Map.Entry<K, V>[] entryArray) {
        Preconditions.checkPositionIndex(n, entryArray.length);
        int n2 = Hashing.closedTableSize(n, 1.2);
        int n3 = n2 - 1;
        ImmutableMapEntry<K, V>[] immutableMapEntryArray = ImmutableMapEntry.createEntryArray(n2);
        ImmutableMapEntry<K, V>[] immutableMapEntryArray2 = ImmutableMapEntry.createEntryArray(n2);
        Map.Entry<K, V>[] entryArray2 = n == entryArray.length ? entryArray : ImmutableMapEntry.createEntryArray(n);
        int n4 = 0;
        for (int i = 0; i < n; ++i) {
            ImmutableMapEntry immutableMapEntry;
            Map.Entry<K, V> entry = entryArray[i];
            K k = entry.getKey();
            V v = entry.getValue();
            CollectPreconditions.checkEntryNotNull(k, v);
            int n5 = k.hashCode();
            int n6 = v.hashCode();
            int n7 = Hashing.smear(n5) & n3;
            int n8 = Hashing.smear(n6) & n3;
            ImmutableMapEntry immutableMapEntry2 = immutableMapEntryArray[n7];
            RegularImmutableMap.checkNoConflictInKeyBucket(k, entry, immutableMapEntry2);
            ImmutableMapEntry immutableMapEntry3 = immutableMapEntryArray2[n8];
            RegularImmutableBiMap.checkNoConflictInValueBucket(v, entry, immutableMapEntry3);
            if (immutableMapEntry3 == null && immutableMapEntry2 == null) {
                boolean bl = entry instanceof ImmutableMapEntry && ((ImmutableMapEntry)entry).isReusable();
                immutableMapEntry = bl ? (ImmutableMapEntry)entry : new ImmutableMapEntry<K, V>(k, v);
            } else {
                immutableMapEntry = new ImmutableMapEntry.NonTerminalImmutableBiMapEntry<K, V>(k, v, immutableMapEntry2, immutableMapEntry3);
            }
            immutableMapEntryArray[n7] = immutableMapEntry;
            immutableMapEntryArray2[n8] = immutableMapEntry;
            entryArray2[i] = immutableMapEntry;
            n4 += n5 ^ n6;
        }
        return new RegularImmutableBiMap(immutableMapEntryArray, immutableMapEntryArray2, entryArray2, n3, n4);
    }

    private RegularImmutableBiMap(ImmutableMapEntry<K, V>[] immutableMapEntryArray, ImmutableMapEntry<K, V>[] immutableMapEntryArray2, Map.Entry<K, V>[] entryArray, int n, int n2) {
        this.keyTable = immutableMapEntryArray;
        this.valueTable = immutableMapEntryArray2;
        this.entries = entryArray;
        this.mask = n;
        this.hashCode = n2;
    }

    private static void checkNoConflictInValueBucket(Object object, Map.Entry<?, ?> entry, @Nullable ImmutableMapEntry<?, ?> immutableMapEntry) {
        while (immutableMapEntry != null) {
            RegularImmutableBiMap.checkNoConflict(!object.equals(immutableMapEntry.getValue()), "value", entry, immutableMapEntry);
            immutableMapEntry = immutableMapEntry.getNextInValueBucket();
        }
    }

    @Override
    @Nullable
    public V get(@Nullable Object object) {
        return this.keyTable == null ? null : (V)RegularImmutableMap.get(object, this.keyTable, this.mask);
    }

    @Override
    ImmutableSet<Map.Entry<K, V>> createEntrySet() {
        return this.isEmpty() ? ImmutableSet.of() : new ImmutableMapEntrySet.RegularEntrySet<K, V>(this, this.entries);
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> biConsumer) {
        Preconditions.checkNotNull(biConsumer);
        for (Map.Entry<K, V> entry : this.entries) {
            biConsumer.accept(entry.getKey(), entry.getValue());
        }
    }

    @Override
    boolean isHashCodeFast() {
        return false;
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }

    @Override
    boolean isPartialView() {
        return true;
    }

    @Override
    public int size() {
        return this.entries.length;
    }

    @Override
    public ImmutableBiMap<V, K> inverse() {
        if (this.isEmpty()) {
            return ImmutableBiMap.of();
        }
        Inverse inverse = this.inverse;
        return inverse == null ? (this.inverse = new Inverse(this, null)) : inverse;
    }

    @Override
    public BiMap inverse() {
        return this.inverse();
    }

    static ImmutableMapEntry[] access$100(RegularImmutableBiMap regularImmutableBiMap) {
        return regularImmutableBiMap.valueTable;
    }

    static int access$200(RegularImmutableBiMap regularImmutableBiMap) {
        return regularImmutableBiMap.mask;
    }

    static int access$300(RegularImmutableBiMap regularImmutableBiMap) {
        return regularImmutableBiMap.hashCode;
    }

    static Map.Entry[] access$400(RegularImmutableBiMap regularImmutableBiMap) {
        return regularImmutableBiMap.entries;
    }

    private static class InverseSerializedForm<K, V>
    implements Serializable {
        private final ImmutableBiMap<K, V> forward;
        private static final long serialVersionUID = 1L;

        InverseSerializedForm(ImmutableBiMap<K, V> immutableBiMap) {
            this.forward = immutableBiMap;
        }

        Object readResolve() {
            return this.forward.inverse();
        }
    }

    private final class Inverse
    extends ImmutableBiMap<V, K> {
        final RegularImmutableBiMap this$0;

        private Inverse(RegularImmutableBiMap regularImmutableBiMap) {
            this.this$0 = regularImmutableBiMap;
        }

        @Override
        public int size() {
            return this.inverse().size();
        }

        @Override
        public ImmutableBiMap<K, V> inverse() {
            return this.this$0;
        }

        @Override
        public void forEach(BiConsumer<? super V, ? super K> biConsumer) {
            Preconditions.checkNotNull(biConsumer);
            this.this$0.forEach((arg_0, arg_1) -> Inverse.lambda$forEach$0(biConsumer, arg_0, arg_1));
        }

        @Override
        public K get(@Nullable Object object) {
            if (object == null || RegularImmutableBiMap.access$100(this.this$0) == null) {
                return null;
            }
            int n = Hashing.smear(object.hashCode()) & RegularImmutableBiMap.access$200(this.this$0);
            for (ImmutableMapEntry immutableMapEntry = RegularImmutableBiMap.access$100(this.this$0)[n]; immutableMapEntry != null; immutableMapEntry = immutableMapEntry.getNextInValueBucket()) {
                if (!object.equals(immutableMapEntry.getValue())) continue;
                return immutableMapEntry.getKey();
            }
            return null;
        }

        @Override
        ImmutableSet<Map.Entry<V, K>> createEntrySet() {
            return new InverseEntrySet(this);
        }

        @Override
        boolean isPartialView() {
            return true;
        }

        @Override
        Object writeReplace() {
            return new InverseSerializedForm(this.this$0);
        }

        @Override
        public BiMap inverse() {
            return this.inverse();
        }

        private static void lambda$forEach$0(BiConsumer biConsumer, Object object, Object object2) {
            biConsumer.accept(object2, object);
        }

        Inverse(RegularImmutableBiMap regularImmutableBiMap, 1 var2_2) {
            this(regularImmutableBiMap);
        }

        final class InverseEntrySet
        extends ImmutableMapEntrySet<V, K> {
            final Inverse this$1;

            InverseEntrySet(Inverse inverse) {
                this.this$1 = inverse;
            }

            @Override
            ImmutableMap<V, K> map() {
                return this.this$1;
            }

            @Override
            boolean isHashCodeFast() {
                return false;
            }

            @Override
            public int hashCode() {
                return RegularImmutableBiMap.access$300(this.this$1.this$0);
            }

            @Override
            public UnmodifiableIterator<Map.Entry<V, K>> iterator() {
                return this.asList().iterator();
            }

            @Override
            public void forEach(Consumer<? super Map.Entry<V, K>> consumer) {
                this.asList().forEach(consumer);
            }

            @Override
            ImmutableList<Map.Entry<V, K>> createAsList() {
                return new ImmutableAsList<Map.Entry<V, K>>(this){
                    final InverseEntrySet this$2;
                    {
                        this.this$2 = inverseEntrySet;
                    }

                    @Override
                    public Map.Entry<V, K> get(int n) {
                        Map.Entry entry = RegularImmutableBiMap.access$400(this.this$2.this$1.this$0)[n];
                        return Maps.immutableEntry(entry.getValue(), entry.getKey());
                    }

                    @Override
                    ImmutableCollection<Map.Entry<V, K>> delegateCollection() {
                        return this.this$2;
                    }

                    @Override
                    public Object get(int n) {
                        return this.get(n);
                    }
                };
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        }
    }
}

