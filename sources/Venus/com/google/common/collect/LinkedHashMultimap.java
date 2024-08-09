/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractSetMultimap;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.CollectSpliterators;
import com.google.common.collect.Hashing;
import com.google.common.collect.ImmutableEntry;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Sets;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javax.annotation.Nullable;

@GwtCompatible(serializable=true, emulated=true)
public final class LinkedHashMultimap<K, V>
extends AbstractSetMultimap<K, V> {
    private static final int DEFAULT_KEY_CAPACITY = 16;
    private static final int DEFAULT_VALUE_SET_CAPACITY = 2;
    @VisibleForTesting
    static final double VALUE_SET_LOAD_FACTOR = 1.0;
    @VisibleForTesting
    transient int valueSetCapacity = 2;
    private transient ValueEntry<K, V> multimapHeaderEntry;
    @GwtIncompatible
    private static final long serialVersionUID = 1L;

    public static <K, V> LinkedHashMultimap<K, V> create() {
        return new LinkedHashMultimap<K, V>(16, 2);
    }

    public static <K, V> LinkedHashMultimap<K, V> create(int n, int n2) {
        return new LinkedHashMultimap<K, V>(Maps.capacity(n), Maps.capacity(n2));
    }

    public static <K, V> LinkedHashMultimap<K, V> create(Multimap<? extends K, ? extends V> multimap) {
        LinkedHashMultimap<K, V> linkedHashMultimap = LinkedHashMultimap.create(multimap.keySet().size(), 2);
        linkedHashMultimap.putAll((Multimap)multimap);
        return linkedHashMultimap;
    }

    private static <K, V> void succeedsInValueSet(ValueSetLink<K, V> valueSetLink, ValueSetLink<K, V> valueSetLink2) {
        valueSetLink.setSuccessorInValueSet(valueSetLink2);
        valueSetLink2.setPredecessorInValueSet(valueSetLink);
    }

    private static <K, V> void succeedsInMultimap(ValueEntry<K, V> valueEntry, ValueEntry<K, V> valueEntry2) {
        valueEntry.setSuccessorInMultimap(valueEntry2);
        valueEntry2.setPredecessorInMultimap(valueEntry);
    }

    private static <K, V> void deleteFromValueSet(ValueSetLink<K, V> valueSetLink) {
        LinkedHashMultimap.succeedsInValueSet(valueSetLink.getPredecessorInValueSet(), valueSetLink.getSuccessorInValueSet());
    }

    private static <K, V> void deleteFromMultimap(ValueEntry<K, V> valueEntry) {
        LinkedHashMultimap.succeedsInMultimap(valueEntry.getPredecessorInMultimap(), valueEntry.getSuccessorInMultimap());
    }

    private LinkedHashMultimap(int n, int n2) {
        super(new LinkedHashMap(n));
        CollectPreconditions.checkNonnegative(n2, "expectedValuesPerKey");
        this.valueSetCapacity = n2;
        this.multimapHeaderEntry = new ValueEntry<Object, Object>(null, null, 0, null);
        LinkedHashMultimap.succeedsInMultimap(this.multimapHeaderEntry, this.multimapHeaderEntry);
    }

    @Override
    Set<V> createCollection() {
        return new LinkedHashSet(this.valueSetCapacity);
    }

    @Override
    Collection<V> createCollection(K k) {
        return new ValueSet(this, k, this.valueSetCapacity);
    }

    @Override
    @CanIgnoreReturnValue
    public Set<V> replaceValues(@Nullable K k, Iterable<? extends V> iterable) {
        return super.replaceValues((Object)k, (Iterable)iterable);
    }

    @Override
    public Set<Map.Entry<K, V>> entries() {
        return super.entries();
    }

    @Override
    public Set<K> keySet() {
        return super.keySet();
    }

    @Override
    public Collection<V> values() {
        return super.values();
    }

    @Override
    Iterator<Map.Entry<K, V>> entryIterator() {
        return new Iterator<Map.Entry<K, V>>(this){
            ValueEntry<K, V> nextEntry;
            ValueEntry<K, V> toRemove;
            final LinkedHashMultimap this$0;
            {
                this.this$0 = linkedHashMultimap;
                this.nextEntry = LinkedHashMultimap.access$300((LinkedHashMultimap)this.this$0).successorInMultimap;
            }

            @Override
            public boolean hasNext() {
                return this.nextEntry != LinkedHashMultimap.access$300(this.this$0);
            }

            @Override
            public Map.Entry<K, V> next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                ValueEntry valueEntry = this.nextEntry;
                this.toRemove = valueEntry;
                this.nextEntry = this.nextEntry.successorInMultimap;
                return valueEntry;
            }

            @Override
            public void remove() {
                CollectPreconditions.checkRemove(this.toRemove != null);
                this.this$0.remove(this.toRemove.getKey(), this.toRemove.getValue());
                this.toRemove = null;
            }

            @Override
            public Object next() {
                return this.next();
            }
        };
    }

    @Override
    Spliterator<Map.Entry<K, V>> entrySpliterator() {
        return Spliterators.spliterator(this.entries(), 17);
    }

    @Override
    Iterator<V> valueIterator() {
        return Maps.valueIterator(this.entryIterator());
    }

    @Override
    Spliterator<V> valueSpliterator() {
        return CollectSpliterators.map(this.entrySpliterator(), Map.Entry::getValue);
    }

    @Override
    public void clear() {
        super.clear();
        LinkedHashMultimap.succeedsInMultimap(this.multimapHeaderEntry, this.multimapHeaderEntry);
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.keySet().size());
        for (Object object : this.keySet()) {
            objectOutputStream.writeObject(object);
        }
        objectOutputStream.writeInt(this.size());
        for (Object object : this.entries()) {
            objectOutputStream.writeObject(object.getKey());
            objectOutputStream.writeObject(object.getValue());
        }
    }

    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        int n;
        objectInputStream.defaultReadObject();
        this.multimapHeaderEntry = new ValueEntry<Object, Object>(null, null, 0, null);
        LinkedHashMultimap.succeedsInMultimap(this.multimapHeaderEntry, this.multimapHeaderEntry);
        this.valueSetCapacity = 2;
        int n2 = objectInputStream.readInt();
        LinkedHashMap<Object, Collection<V>> linkedHashMap = new LinkedHashMap<Object, Collection<V>>();
        for (n = 0; n < n2; ++n) {
            Object object = objectInputStream.readObject();
            linkedHashMap.put(object, this.createCollection(object));
        }
        n = objectInputStream.readInt();
        for (int i = 0; i < n; ++i) {
            Object object = objectInputStream.readObject();
            Object object2 = objectInputStream.readObject();
            ((Collection)linkedHashMap.get(object)).add(object2);
        }
        this.setMap(linkedHashMap);
    }

    @Override
    public boolean equals(@Nullable Object object) {
        return super.equals(object);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean put(@Nullable Object object, @Nullable Object object2) {
        return super.put(object, object2);
    }

    @Override
    public Map asMap() {
        return super.asMap();
    }

    @Override
    @CanIgnoreReturnValue
    public Set removeAll(@Nullable Object object) {
        return super.removeAll(object);
    }

    @Override
    public Set get(@Nullable Object object) {
        return super.get(object);
    }

    @Override
    public Collection entries() {
        return this.entries();
    }

    @Override
    @CanIgnoreReturnValue
    public Collection replaceValues(@Nullable Object object, Iterable iterable) {
        return this.replaceValues(object, iterable);
    }

    @Override
    public void forEach(BiConsumer biConsumer) {
        super.forEach(biConsumer);
    }

    @Override
    public boolean containsKey(@Nullable Object object) {
        return super.containsKey(object);
    }

    @Override
    public int size() {
        return super.size();
    }

    @Override
    Collection createCollection() {
        return this.createCollection();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public Multiset keys() {
        return super.keys();
    }

    @Override
    @CanIgnoreReturnValue
    public boolean putAll(Multimap multimap) {
        return super.putAll(multimap);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean putAll(@Nullable Object object, Iterable iterable) {
        return super.putAll(object, iterable);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean remove(@Nullable Object object, @Nullable Object object2) {
        return super.remove(object, object2);
    }

    @Override
    public boolean containsEntry(@Nullable Object object, @Nullable Object object2) {
        return super.containsEntry(object, object2);
    }

    @Override
    public boolean containsValue(@Nullable Object object) {
        return super.containsValue(object);
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    static void access$200(ValueSetLink valueSetLink, ValueSetLink valueSetLink2) {
        LinkedHashMultimap.succeedsInValueSet(valueSetLink, valueSetLink2);
    }

    static ValueEntry access$300(LinkedHashMultimap linkedHashMultimap) {
        return linkedHashMultimap.multimapHeaderEntry;
    }

    static void access$400(ValueEntry valueEntry, ValueEntry valueEntry2) {
        LinkedHashMultimap.succeedsInMultimap(valueEntry, valueEntry2);
    }

    static void access$500(ValueSetLink valueSetLink) {
        LinkedHashMultimap.deleteFromValueSet(valueSetLink);
    }

    static void access$600(ValueEntry valueEntry) {
        LinkedHashMultimap.deleteFromMultimap(valueEntry);
    }

    @VisibleForTesting
    final class ValueSet
    extends Sets.ImprovedAbstractSet<V>
    implements ValueSetLink<K, V> {
        private final K key;
        @VisibleForTesting
        ValueEntry<K, V>[] hashTable;
        private int size;
        private int modCount;
        private ValueSetLink<K, V> firstEntry;
        private ValueSetLink<K, V> lastEntry;
        final LinkedHashMultimap this$0;

        ValueSet(LinkedHashMultimap linkedHashMultimap, K k, int n) {
            this.this$0 = linkedHashMultimap;
            this.size = 0;
            this.modCount = 0;
            this.key = k;
            this.firstEntry = this;
            this.lastEntry = this;
            int n2 = Hashing.closedTableSize(n, 1.0);
            ValueEntry[] valueEntryArray = new ValueEntry[n2];
            this.hashTable = valueEntryArray;
        }

        private int mask() {
            return this.hashTable.length - 1;
        }

        @Override
        public ValueSetLink<K, V> getPredecessorInValueSet() {
            return this.lastEntry;
        }

        @Override
        public ValueSetLink<K, V> getSuccessorInValueSet() {
            return this.firstEntry;
        }

        @Override
        public void setPredecessorInValueSet(ValueSetLink<K, V> valueSetLink) {
            this.lastEntry = valueSetLink;
        }

        @Override
        public void setSuccessorInValueSet(ValueSetLink<K, V> valueSetLink) {
            this.firstEntry = valueSetLink;
        }

        @Override
        public Iterator<V> iterator() {
            return new Iterator<V>(this){
                ValueSetLink<K, V> nextEntry;
                ValueEntry<K, V> toRemove;
                int expectedModCount;
                final ValueSet this$1;
                {
                    this.this$1 = valueSet;
                    this.nextEntry = ValueSet.access$000(this.this$1);
                    this.expectedModCount = ValueSet.access$100(this.this$1);
                }

                private void checkForComodification() {
                    if (ValueSet.access$100(this.this$1) != this.expectedModCount) {
                        throw new ConcurrentModificationException();
                    }
                }

                @Override
                public boolean hasNext() {
                    this.checkForComodification();
                    return this.nextEntry != this.this$1;
                }

                @Override
                public V next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    ValueEntry valueEntry = (ValueEntry)this.nextEntry;
                    Object v = valueEntry.getValue();
                    this.toRemove = valueEntry;
                    this.nextEntry = valueEntry.getSuccessorInValueSet();
                    return v;
                }

                @Override
                public void remove() {
                    this.checkForComodification();
                    CollectPreconditions.checkRemove(this.toRemove != null);
                    this.this$1.remove(this.toRemove.getValue());
                    this.expectedModCount = ValueSet.access$100(this.this$1);
                    this.toRemove = null;
                }
            };
        }

        @Override
        public void forEach(Consumer<? super V> consumer) {
            Preconditions.checkNotNull(consumer);
            for (ValueSetLink valueSetLink = this.firstEntry; valueSetLink != this; valueSetLink = valueSetLink.getSuccessorInValueSet()) {
                consumer.accept(((ValueEntry)valueSetLink).getValue());
            }
        }

        @Override
        public int size() {
            return this.size;
        }

        @Override
        public boolean contains(@Nullable Object object) {
            int n = Hashing.smearedHash(object);
            ValueEntry valueEntry = this.hashTable[n & this.mask()];
            while (valueEntry != null) {
                if (valueEntry.matchesValue(object, n)) {
                    return false;
                }
                valueEntry = valueEntry.nextInValueBucket;
            }
            return true;
        }

        @Override
        public boolean add(@Nullable V v) {
            ValueEntry valueEntry;
            int n = Hashing.smearedHash(v);
            int n2 = n & this.mask();
            ValueEntry valueEntry2 = valueEntry = this.hashTable[n2];
            while (valueEntry2 != null) {
                if (valueEntry2.matchesValue(v, n)) {
                    return true;
                }
                valueEntry2 = valueEntry2.nextInValueBucket;
            }
            valueEntry2 = new ValueEntry(this.key, v, n, valueEntry);
            LinkedHashMultimap.access$200(this.lastEntry, valueEntry2);
            LinkedHashMultimap.access$200(valueEntry2, this);
            LinkedHashMultimap.access$400(LinkedHashMultimap.access$300(this.this$0).getPredecessorInMultimap(), valueEntry2);
            LinkedHashMultimap.access$400(valueEntry2, LinkedHashMultimap.access$300(this.this$0));
            this.hashTable[n2] = valueEntry2;
            ++this.size;
            ++this.modCount;
            this.rehashIfNecessary();
            return false;
        }

        private void rehashIfNecessary() {
            if (Hashing.needsResizing(this.size, this.hashTable.length, 1.0)) {
                ValueEntry[] valueEntryArray = new ValueEntry[this.hashTable.length * 2];
                this.hashTable = valueEntryArray;
                int n = valueEntryArray.length - 1;
                for (ValueSetLink valueSetLink = this.firstEntry; valueSetLink != this; valueSetLink = valueSetLink.getSuccessorInValueSet()) {
                    ValueEntry valueEntry = (ValueEntry)valueSetLink;
                    int n2 = valueEntry.smearedValueHash & n;
                    valueEntry.nextInValueBucket = valueEntryArray[n2];
                    valueEntryArray[n2] = valueEntry;
                }
            }
        }

        @Override
        @CanIgnoreReturnValue
        public boolean remove(@Nullable Object object) {
            int n = Hashing.smearedHash(object);
            int n2 = n & this.mask();
            ValueEntry valueEntry = null;
            ValueEntry valueEntry2 = this.hashTable[n2];
            while (valueEntry2 != null) {
                if (valueEntry2.matchesValue(object, n)) {
                    if (valueEntry == null) {
                        this.hashTable[n2] = valueEntry2.nextInValueBucket;
                    } else {
                        valueEntry.nextInValueBucket = valueEntry2.nextInValueBucket;
                    }
                    LinkedHashMultimap.access$500(valueEntry2);
                    LinkedHashMultimap.access$600(valueEntry2);
                    --this.size;
                    ++this.modCount;
                    return false;
                }
                valueEntry = valueEntry2;
                valueEntry2 = valueEntry2.nextInValueBucket;
            }
            return true;
        }

        @Override
        public void clear() {
            Arrays.fill(this.hashTable, null);
            this.size = 0;
            for (ValueSetLink valueSetLink = this.firstEntry; valueSetLink != this; valueSetLink = valueSetLink.getSuccessorInValueSet()) {
                ValueEntry valueEntry = (ValueEntry)valueSetLink;
                LinkedHashMultimap.access$600(valueEntry);
            }
            LinkedHashMultimap.access$200(this, this);
            ++this.modCount;
        }

        static ValueSetLink access$000(ValueSet valueSet) {
            return valueSet.firstEntry;
        }

        static int access$100(ValueSet valueSet) {
            return valueSet.modCount;
        }
    }

    @VisibleForTesting
    static final class ValueEntry<K, V>
    extends ImmutableEntry<K, V>
    implements ValueSetLink<K, V> {
        final int smearedValueHash;
        @Nullable
        ValueEntry<K, V> nextInValueBucket;
        ValueSetLink<K, V> predecessorInValueSet;
        ValueSetLink<K, V> successorInValueSet;
        ValueEntry<K, V> predecessorInMultimap;
        ValueEntry<K, V> successorInMultimap;

        ValueEntry(@Nullable K k, @Nullable V v, int n, @Nullable ValueEntry<K, V> valueEntry) {
            super(k, v);
            this.smearedValueHash = n;
            this.nextInValueBucket = valueEntry;
        }

        boolean matchesValue(@Nullable Object object, int n) {
            return this.smearedValueHash == n && Objects.equal(this.getValue(), object);
        }

        @Override
        public ValueSetLink<K, V> getPredecessorInValueSet() {
            return this.predecessorInValueSet;
        }

        @Override
        public ValueSetLink<K, V> getSuccessorInValueSet() {
            return this.successorInValueSet;
        }

        @Override
        public void setPredecessorInValueSet(ValueSetLink<K, V> valueSetLink) {
            this.predecessorInValueSet = valueSetLink;
        }

        @Override
        public void setSuccessorInValueSet(ValueSetLink<K, V> valueSetLink) {
            this.successorInValueSet = valueSetLink;
        }

        public ValueEntry<K, V> getPredecessorInMultimap() {
            return this.predecessorInMultimap;
        }

        public ValueEntry<K, V> getSuccessorInMultimap() {
            return this.successorInMultimap;
        }

        public void setSuccessorInMultimap(ValueEntry<K, V> valueEntry) {
            this.successorInMultimap = valueEntry;
        }

        public void setPredecessorInMultimap(ValueEntry<K, V> valueEntry) {
            this.predecessorInMultimap = valueEntry;
        }
    }

    private static interface ValueSetLink<K, V> {
        public ValueSetLink<K, V> getPredecessorInValueSet();

        public ValueSetLink<K, V> getSuccessorInValueSet();

        public void setPredecessorInValueSet(ValueSetLink<K, V> var1);

        public void setSuccessorInValueSet(ValueSetLink<K, V> var1);
    }
}

