/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.ForwardingMap;
import com.google.common.collect.ForwardingMapEntry;
import com.google.common.collect.ForwardingSet;
import com.google.common.collect.Maps;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.j2objc.annotations.RetainedWith;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import javax.annotation.Nullable;

@GwtCompatible(emulated=true)
abstract class AbstractBiMap<K, V>
extends ForwardingMap<K, V>
implements BiMap<K, V>,
Serializable {
    private transient Map<K, V> delegate;
    @RetainedWith
    transient AbstractBiMap<V, K> inverse;
    private transient Set<K> keySet;
    private transient Set<V> valueSet;
    private transient Set<Map.Entry<K, V>> entrySet;
    @GwtIncompatible
    private static final long serialVersionUID = 0L;

    AbstractBiMap(Map<K, V> map, Map<V, K> map2) {
        this.setDelegates(map, map2);
    }

    private AbstractBiMap(Map<K, V> map, AbstractBiMap<V, K> abstractBiMap) {
        this.delegate = map;
        this.inverse = abstractBiMap;
    }

    @Override
    protected Map<K, V> delegate() {
        return this.delegate;
    }

    @CanIgnoreReturnValue
    K checkKey(@Nullable K k) {
        return k;
    }

    @CanIgnoreReturnValue
    V checkValue(@Nullable V v) {
        return v;
    }

    void setDelegates(Map<K, V> map, Map<V, K> map2) {
        Preconditions.checkState(this.delegate == null);
        Preconditions.checkState(this.inverse == null);
        Preconditions.checkArgument(map.isEmpty());
        Preconditions.checkArgument(map2.isEmpty());
        Preconditions.checkArgument(map != map2);
        this.delegate = map;
        this.inverse = this.makeInverse(map2);
    }

    AbstractBiMap<V, K> makeInverse(Map<V, K> map) {
        return new Inverse<V, K>(map, this);
    }

    void setInverse(AbstractBiMap<V, K> abstractBiMap) {
        this.inverse = abstractBiMap;
    }

    @Override
    public boolean containsValue(@Nullable Object object) {
        return this.inverse.containsKey(object);
    }

    @Override
    @CanIgnoreReturnValue
    public V put(@Nullable K k, @Nullable V v) {
        return this.putInBothMaps(k, v, false);
    }

    @Override
    @CanIgnoreReturnValue
    public V forcePut(@Nullable K k, @Nullable V v) {
        return this.putInBothMaps(k, v, true);
    }

    private V putInBothMaps(@Nullable K k, @Nullable V v, boolean bl) {
        this.checkKey(k);
        this.checkValue(v);
        boolean bl2 = this.containsKey(k);
        if (bl2 && Objects.equal(v, this.get(k))) {
            return v;
        }
        if (bl) {
            this.inverse().remove(v);
        } else {
            Preconditions.checkArgument(!this.containsValue(v), "value already present: %s", v);
        }
        V v2 = this.delegate.put(k, v);
        this.updateInverseMap(k, bl2, v2, v);
        return v2;
    }

    private void updateInverseMap(K k, boolean bl, V v, V v2) {
        if (bl) {
            this.removeFromInverseMap(v);
        }
        this.inverse.delegate.put(v2, k);
    }

    @Override
    @CanIgnoreReturnValue
    public V remove(@Nullable Object object) {
        return this.containsKey(object) ? (V)this.removeFromBothMaps(object) : null;
    }

    @CanIgnoreReturnValue
    private V removeFromBothMaps(Object object) {
        V v = this.delegate.remove(object);
        this.removeFromInverseMap(v);
        return v;
    }

    private void removeFromInverseMap(V v) {
        this.inverse.delegate.remove(v);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> biFunction) {
        this.delegate.replaceAll(biFunction);
        this.inverse.delegate.clear();
        Map.Entry<K, V> entry = null;
        Iterator<Map.Entry<K, V>> iterator2 = this.delegate.entrySet().iterator();
        while (iterator2.hasNext()) {
            Map.Entry<K, V> entry2 = iterator2.next();
            K k = entry2.getKey();
            V v = entry2.getValue();
            V v2 = this.inverse.delegate.putIfAbsent(v, k);
            if (v2 == null) continue;
            entry = entry2;
            iterator2.remove();
        }
        if (entry != null) {
            throw new IllegalArgumentException("value already present: " + entry.getValue());
        }
    }

    @Override
    public void clear() {
        this.delegate.clear();
        this.inverse.delegate.clear();
    }

    @Override
    public BiMap<V, K> inverse() {
        return this.inverse;
    }

    @Override
    public Set<K> keySet() {
        KeySet keySet = this.keySet;
        return keySet == null ? (this.keySet = new KeySet(this, null)) : keySet;
    }

    @Override
    public Set<V> values() {
        ValueSet valueSet = this.valueSet;
        return valueSet == null ? (this.valueSet = new ValueSet(this, null)) : valueSet;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        EntrySet entrySet = this.entrySet;
        return entrySet == null ? (this.entrySet = new EntrySet(this, null)) : entrySet;
    }

    Iterator<Map.Entry<K, V>> entrySetIterator() {
        Iterator<Map.Entry<K, V>> iterator2 = this.delegate.entrySet().iterator();
        return new Iterator<Map.Entry<K, V>>(this, iterator2){
            Map.Entry<K, V> entry;
            final Iterator val$iterator;
            final AbstractBiMap this$0;
            {
                this.this$0 = abstractBiMap;
                this.val$iterator = iterator2;
            }

            @Override
            public boolean hasNext() {
                return this.val$iterator.hasNext();
            }

            @Override
            public Map.Entry<K, V> next() {
                this.entry = (Map.Entry)this.val$iterator.next();
                return new BiMapEntry(this.this$0, this.entry);
            }

            @Override
            public void remove() {
                CollectPreconditions.checkRemove(this.entry != null);
                Object v = this.entry.getValue();
                this.val$iterator.remove();
                AbstractBiMap.access$600(this.this$0, v);
            }

            @Override
            public Object next() {
                return this.next();
            }
        };
    }

    @Override
    public Collection values() {
        return this.values();
    }

    @Override
    protected Object delegate() {
        return this.delegate();
    }

    static Map access$100(AbstractBiMap abstractBiMap) {
        return abstractBiMap.delegate;
    }

    static Object access$200(AbstractBiMap abstractBiMap, Object object) {
        return abstractBiMap.removeFromBothMaps(object);
    }

    static void access$500(AbstractBiMap abstractBiMap, Object object, boolean bl, Object object2, Object object3) {
        abstractBiMap.updateInverseMap(object, bl, object2, object3);
    }

    static void access$600(AbstractBiMap abstractBiMap, Object object) {
        abstractBiMap.removeFromInverseMap(object);
    }

    AbstractBiMap(Map map, AbstractBiMap abstractBiMap, 1 var3_3) {
        this(map, abstractBiMap);
    }

    static class Inverse<K, V>
    extends AbstractBiMap<K, V> {
        @GwtIncompatible
        private static final long serialVersionUID = 0L;

        Inverse(Map<K, V> map, AbstractBiMap<V, K> abstractBiMap) {
            super(map, abstractBiMap, null);
        }

        @Override
        K checkKey(K k) {
            return this.inverse.checkValue(k);
        }

        @Override
        V checkValue(V v) {
            return this.inverse.checkKey(v);
        }

        @GwtIncompatible
        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.defaultWriteObject();
            objectOutputStream.writeObject(this.inverse());
        }

        @GwtIncompatible
        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            this.setInverse((AbstractBiMap)objectInputStream.readObject());
        }

        @GwtIncompatible
        Object readResolve() {
            return this.inverse().inverse();
        }

        @Override
        public Collection values() {
            return super.values();
        }

        @Override
        protected Object delegate() {
            return super.delegate();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class EntrySet
    extends ForwardingSet<Map.Entry<K, V>> {
        final Set<Map.Entry<K, V>> esDelegate;
        final AbstractBiMap this$0;

        private EntrySet(AbstractBiMap abstractBiMap) {
            this.this$0 = abstractBiMap;
            this.esDelegate = AbstractBiMap.access$100(this.this$0).entrySet();
        }

        @Override
        protected Set<Map.Entry<K, V>> delegate() {
            return this.esDelegate;
        }

        @Override
        public void clear() {
            this.this$0.clear();
        }

        @Override
        public boolean remove(Object object) {
            if (!this.esDelegate.contains(object)) {
                return true;
            }
            Map.Entry entry = (Map.Entry)object;
            AbstractBiMap.access$100(this.this$0.inverse).remove(entry.getValue());
            this.esDelegate.remove(entry);
            return false;
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return this.this$0.entrySetIterator();
        }

        @Override
        public Object[] toArray() {
            return this.standardToArray();
        }

        @Override
        public <T> T[] toArray(T[] TArray) {
            return this.standardToArray(TArray);
        }

        @Override
        public boolean contains(Object object) {
            return Maps.containsEntryImpl(this.delegate(), object);
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return this.standardContainsAll(collection);
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            return this.standardRemoveAll(collection);
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            return this.standardRetainAll(collection);
        }

        @Override
        protected Collection delegate() {
            return this.delegate();
        }

        @Override
        protected Object delegate() {
            return this.delegate();
        }

        EntrySet(AbstractBiMap abstractBiMap, 1 var2_2) {
            this(abstractBiMap);
        }
    }

    class BiMapEntry
    extends ForwardingMapEntry<K, V> {
        private final Map.Entry<K, V> delegate;
        final AbstractBiMap this$0;

        BiMapEntry(AbstractBiMap abstractBiMap, Map.Entry<K, V> entry) {
            this.this$0 = abstractBiMap;
            this.delegate = entry;
        }

        @Override
        protected Map.Entry<K, V> delegate() {
            return this.delegate;
        }

        @Override
        public V setValue(V v) {
            Preconditions.checkState(this.this$0.entrySet().contains(this), "entry no longer in map");
            if (Objects.equal(v, this.getValue())) {
                return v;
            }
            Preconditions.checkArgument(!this.this$0.containsValue(v), "value already present: %s", v);
            Object v2 = this.delegate.setValue(v);
            Preconditions.checkState(Objects.equal(v, this.this$0.get(this.getKey())), "entry no longer in map");
            AbstractBiMap.access$500(this.this$0, this.getKey(), true, v2, v);
            return v2;
        }

        @Override
        protected Object delegate() {
            return this.delegate();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class ValueSet
    extends ForwardingSet<V> {
        final Set<V> valuesDelegate;
        final AbstractBiMap this$0;

        private ValueSet(AbstractBiMap abstractBiMap) {
            this.this$0 = abstractBiMap;
            this.valuesDelegate = this.this$0.inverse.keySet();
        }

        @Override
        protected Set<V> delegate() {
            return this.valuesDelegate;
        }

        @Override
        public Iterator<V> iterator() {
            return Maps.valueIterator(this.this$0.entrySet().iterator());
        }

        @Override
        public Object[] toArray() {
            return this.standardToArray();
        }

        @Override
        public <T> T[] toArray(T[] TArray) {
            return this.standardToArray(TArray);
        }

        @Override
        public String toString() {
            return this.standardToString();
        }

        @Override
        protected Collection delegate() {
            return this.delegate();
        }

        @Override
        protected Object delegate() {
            return this.delegate();
        }

        ValueSet(AbstractBiMap abstractBiMap, 1 var2_2) {
            this(abstractBiMap);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private class KeySet
    extends ForwardingSet<K> {
        final AbstractBiMap this$0;

        private KeySet(AbstractBiMap abstractBiMap) {
            this.this$0 = abstractBiMap;
        }

        @Override
        protected Set<K> delegate() {
            return AbstractBiMap.access$100(this.this$0).keySet();
        }

        @Override
        public void clear() {
            this.this$0.clear();
        }

        @Override
        public boolean remove(Object object) {
            if (!this.contains(object)) {
                return true;
            }
            AbstractBiMap.access$200(this.this$0, object);
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            return this.standardRemoveAll(collection);
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            return this.standardRetainAll(collection);
        }

        @Override
        public Iterator<K> iterator() {
            return Maps.keyIterator(this.this$0.entrySet().iterator());
        }

        @Override
        protected Collection delegate() {
            return this.delegate();
        }

        @Override
        protected Object delegate() {
            return this.delegate();
        }

        KeySet(AbstractBiMap abstractBiMap, 1 var2_2) {
            this(abstractBiMap);
        }
    }
}

