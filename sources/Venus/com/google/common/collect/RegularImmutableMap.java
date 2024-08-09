/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.Hashing;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMapEntry;
import com.google.common.collect.ImmutableMapEntrySet;
import com.google.common.collect.ImmutableSet;
import com.google.j2objc.annotations.Weak;
import java.io.Serializable;
import java.util.Map;
import java.util.function.BiConsumer;
import javax.annotation.Nullable;

@GwtCompatible(serializable=true, emulated=true)
final class RegularImmutableMap<K, V>
extends ImmutableMap<K, V> {
    private final transient Map.Entry<K, V>[] entries;
    private final transient ImmutableMapEntry<K, V>[] table;
    private final transient int mask;
    private static final double MAX_LOAD_FACTOR = 1.2;
    private static final long serialVersionUID = 0L;

    static <K, V> RegularImmutableMap<K, V> fromEntries(Map.Entry<K, V> ... entryArray) {
        return RegularImmutableMap.fromEntryArray(entryArray.length, entryArray);
    }

    static <K, V> RegularImmutableMap<K, V> fromEntryArray(int n, Map.Entry<K, V>[] entryArray) {
        Preconditions.checkPositionIndex(n, entryArray.length);
        Map.Entry<K, V>[] entryArray2 = n == entryArray.length ? entryArray : ImmutableMapEntry.createEntryArray(n);
        int n2 = Hashing.closedTableSize(n, 1.2);
        ImmutableMapEntry<K, V>[] immutableMapEntryArray = ImmutableMapEntry.createEntryArray(n2);
        int n3 = n2 - 1;
        for (int i = 0; i < n; ++i) {
            ImmutableMapEntry immutableMapEntry;
            Map.Entry<K, V> entry = entryArray[i];
            K k = entry.getKey();
            V v = entry.getValue();
            CollectPreconditions.checkEntryNotNull(k, v);
            int n4 = Hashing.smear(k.hashCode()) & n3;
            ImmutableMapEntry immutableMapEntry2 = immutableMapEntryArray[n4];
            if (immutableMapEntry2 == null) {
                boolean bl = entry instanceof ImmutableMapEntry && ((ImmutableMapEntry)entry).isReusable();
                immutableMapEntry = bl ? (ImmutableMapEntry)entry : new ImmutableMapEntry<K, V>(k, v);
            } else {
                immutableMapEntry = new ImmutableMapEntry.NonTerminalImmutableMapEntry<K, V>(k, v, immutableMapEntry2);
            }
            immutableMapEntryArray[n4] = immutableMapEntry;
            entryArray2[i] = immutableMapEntry;
            RegularImmutableMap.checkNoConflictInKeyBucket(k, immutableMapEntry, immutableMapEntry2);
        }
        return new RegularImmutableMap<K, V>(entryArray2, immutableMapEntryArray, n3);
    }

    private RegularImmutableMap(Map.Entry<K, V>[] entryArray, ImmutableMapEntry<K, V>[] immutableMapEntryArray, int n) {
        this.entries = entryArray;
        this.table = immutableMapEntryArray;
        this.mask = n;
    }

    static void checkNoConflictInKeyBucket(Object object, Map.Entry<?, ?> entry, @Nullable ImmutableMapEntry<?, ?> immutableMapEntry) {
        while (immutableMapEntry != null) {
            RegularImmutableMap.checkNoConflict(!object.equals(immutableMapEntry.getKey()), "key", entry, immutableMapEntry);
            immutableMapEntry = immutableMapEntry.getNextInKeyBucket();
        }
    }

    @Override
    public V get(@Nullable Object object) {
        return RegularImmutableMap.get(object, this.table, this.mask);
    }

    @Nullable
    static <V> V get(@Nullable Object object, ImmutableMapEntry<?, V>[] immutableMapEntryArray, int n) {
        if (object == null) {
            return null;
        }
        int n2 = Hashing.smear(object.hashCode()) & n;
        for (ImmutableMapEntry<?, V> immutableMapEntry = immutableMapEntryArray[n2]; immutableMapEntry != null; immutableMapEntry = immutableMapEntry.getNextInKeyBucket()) {
            Object k = immutableMapEntry.getKey();
            if (!object.equals(k)) continue;
            return immutableMapEntry.getValue();
        }
        return null;
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> biConsumer) {
        Preconditions.checkNotNull(biConsumer);
        for (Map.Entry<K, V> entry : this.entries) {
            biConsumer.accept(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public int size() {
        return this.entries.length;
    }

    @Override
    boolean isPartialView() {
        return true;
    }

    @Override
    ImmutableSet<Map.Entry<K, V>> createEntrySet() {
        return new ImmutableMapEntrySet.RegularEntrySet<K, V>(this, this.entries);
    }

    @Override
    ImmutableSet<K> createKeySet() {
        return new KeySet(this);
    }

    @Override
    ImmutableCollection<V> createValues() {
        return new Values(this);
    }

    static Map.Entry[] access$000(RegularImmutableMap regularImmutableMap) {
        return regularImmutableMap.entries;
    }

    @GwtCompatible(emulated=true)
    private static final class Values<K, V>
    extends ImmutableList<V> {
        @Weak
        final RegularImmutableMap<K, V> map;

        Values(RegularImmutableMap<K, V> regularImmutableMap) {
            this.map = regularImmutableMap;
        }

        @Override
        public V get(int n) {
            return RegularImmutableMap.access$000(this.map)[n].getValue();
        }

        @Override
        public int size() {
            return this.map.size();
        }

        @Override
        boolean isPartialView() {
            return false;
        }

        @Override
        @GwtIncompatible
        Object writeReplace() {
            return new SerializedForm<V>(this.map);
        }

        @GwtIncompatible
        private static class SerializedForm<V>
        implements Serializable {
            final ImmutableMap<?, V> map;
            private static final long serialVersionUID = 0L;

            SerializedForm(ImmutableMap<?, V> immutableMap) {
                this.map = immutableMap;
            }

            Object readResolve() {
                return this.map.values();
            }
        }
    }

    @GwtCompatible(emulated=true)
    private static final class KeySet<K, V>
    extends ImmutableSet.Indexed<K> {
        @Weak
        private final RegularImmutableMap<K, V> map;

        KeySet(RegularImmutableMap<K, V> regularImmutableMap) {
            this.map = regularImmutableMap;
        }

        @Override
        K get(int n) {
            return RegularImmutableMap.access$000(this.map)[n].getKey();
        }

        @Override
        public boolean contains(Object object) {
            return this.map.containsKey(object);
        }

        @Override
        boolean isPartialView() {
            return false;
        }

        @Override
        public int size() {
            return this.map.size();
        }

        @Override
        @GwtIncompatible
        Object writeReplace() {
            return new SerializedForm<K>(this.map);
        }

        @GwtIncompatible
        private static class SerializedForm<K>
        implements Serializable {
            final ImmutableMap<K, ?> map;
            private static final long serialVersionUID = 0L;

            SerializedForm(ImmutableMap<K, ?> immutableMap) {
                this.map = immutableMap;
            }

            Object readResolve() {
                return this.map.keySet();
            }
        }
    }
}

