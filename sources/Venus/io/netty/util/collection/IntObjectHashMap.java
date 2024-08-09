/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.collection;

import io.netty.util.collection.IntObjectMap;
import io.netty.util.internal.MathUtil;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class IntObjectHashMap<V>
implements IntObjectMap<V> {
    public static final int DEFAULT_CAPACITY = 8;
    public static final float DEFAULT_LOAD_FACTOR = 0.5f;
    private static final Object NULL_VALUE;
    private int maxSize;
    private final float loadFactor;
    private int[] keys;
    private V[] values;
    private int size;
    private int mask;
    private final Set<Integer> keySet = new KeySet(this, null);
    private final Set<Map.Entry<Integer, V>> entrySet = new EntrySet(this, null);
    private final Iterable<IntObjectMap.PrimitiveEntry<V>> entries = new Iterable<IntObjectMap.PrimitiveEntry<V>>(this){
        final IntObjectHashMap this$0;
        {
            this.this$0 = intObjectHashMap;
        }

        @Override
        public Iterator<IntObjectMap.PrimitiveEntry<V>> iterator() {
            return new PrimitiveIterator(this.this$0, null);
        }
    };
    static final boolean $assertionsDisabled;

    public IntObjectHashMap() {
        this(8, 0.5f);
    }

    public IntObjectHashMap(int n) {
        this(n, 0.5f);
    }

    public IntObjectHashMap(int n, float f) {
        if (f <= 0.0f || f > 1.0f) {
            throw new IllegalArgumentException("loadFactor must be > 0 and <= 1");
        }
        this.loadFactor = f;
        int n2 = MathUtil.safeFindNextPositivePowerOfTwo(n);
        this.mask = n2 - 1;
        this.keys = new int[n2];
        Object[] objectArray = new Object[n2];
        this.values = objectArray;
        this.maxSize = this.calcMaxSize(n2);
    }

    private static <T> T toExternal(T t) {
        if (!$assertionsDisabled && t == null) {
            throw new AssertionError((Object)"null is not a legitimate internal value. Concurrent Modification?");
        }
        return t == NULL_VALUE ? null : (T)t;
    }

    private static <T> T toInternal(T t) {
        return (T)(t == null ? NULL_VALUE : t);
    }

    @Override
    public V get(int n) {
        int n2 = this.indexOf(n);
        return n2 == -1 ? null : (V)IntObjectHashMap.toExternal(this.values[n2]);
    }

    @Override
    public V put(int n, V v) {
        int n2;
        int n3 = n2 = this.hashIndex(n);
        do {
            if (this.values[n3] == null) {
                this.keys[n3] = n;
                this.values[n3] = IntObjectHashMap.toInternal(v);
                this.growSize();
                return null;
            }
            if (this.keys[n3] != n) continue;
            V v2 = this.values[n3];
            this.values[n3] = IntObjectHashMap.toInternal(v);
            return IntObjectHashMap.toExternal(v2);
        } while ((n3 = this.probeNext(n3)) != n2);
        throw new IllegalStateException("Unable to insert");
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends V> map) {
        if (map instanceof IntObjectHashMap) {
            IntObjectHashMap intObjectHashMap = (IntObjectHashMap)map;
            for (int i = 0; i < intObjectHashMap.values.length; ++i) {
                V v = intObjectHashMap.values[i];
                if (v == null) continue;
                this.put(intObjectHashMap.keys[i], v);
            }
            return;
        }
        for (Map.Entry<Integer, V> entry : map.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public V remove(int n) {
        int n2 = this.indexOf(n);
        if (n2 == -1) {
            return null;
        }
        V v = this.values[n2];
        this.removeAt(n2);
        return IntObjectHashMap.toExternal(v);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public void clear() {
        Arrays.fill(this.keys, 0);
        Arrays.fill(this.values, null);
        this.size = 0;
    }

    @Override
    public boolean containsKey(int n) {
        return this.indexOf(n) >= 0;
    }

    @Override
    public boolean containsValue(Object object) {
        Object object2 = IntObjectHashMap.toInternal(object);
        for (V v : this.values) {
            if (v == null || !v.equals(object2)) continue;
            return false;
        }
        return true;
    }

    @Override
    public Iterable<IntObjectMap.PrimitiveEntry<V>> entries() {
        return this.entries;
    }

    @Override
    public Collection<V> values() {
        return new AbstractCollection<V>(this){
            final IntObjectHashMap this$0;
            {
                this.this$0 = intObjectHashMap;
            }

            @Override
            public Iterator<V> iterator() {
                return new Iterator<V>(this){
                    final PrimitiveIterator iter;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.iter = new PrimitiveIterator(this.this$1.this$0, null);
                    }

                    @Override
                    public boolean hasNext() {
                        return this.iter.hasNext();
                    }

                    @Override
                    public V next() {
                        return this.iter.next().value();
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }

            @Override
            public int size() {
                return IntObjectHashMap.access$300(this.this$0);
            }
        };
    }

    @Override
    public int hashCode() {
        int n = this.size;
        for (int n2 : this.keys) {
            n ^= IntObjectHashMap.hashCode(n2);
        }
        return n;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof IntObjectMap)) {
            return true;
        }
        IntObjectMap intObjectMap = (IntObjectMap)object;
        if (this.size != intObjectMap.size()) {
            return true;
        }
        for (int i = 0; i < this.values.length; ++i) {
            V v = this.values[i];
            if (v == null) continue;
            int n = this.keys[i];
            Object v2 = intObjectMap.get(n);
            if (!(v == NULL_VALUE ? v2 != null : !v.equals(v2))) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean containsKey(Object object) {
        return this.containsKey(this.objectToKey(object));
    }

    @Override
    public V get(Object object) {
        return this.get(this.objectToKey(object));
    }

    @Override
    public V put(Integer n, V v) {
        return this.put(this.objectToKey(n), v);
    }

    @Override
    public V remove(Object object) {
        return this.remove(this.objectToKey(object));
    }

    @Override
    public Set<Integer> keySet() {
        return this.keySet;
    }

    @Override
    public Set<Map.Entry<Integer, V>> entrySet() {
        return this.entrySet;
    }

    private int objectToKey(Object object) {
        return (Integer)object;
    }

    private int indexOf(int n) {
        int n2;
        int n3 = n2 = this.hashIndex(n);
        do {
            if (this.values[n3] == null) {
                return 1;
            }
            if (n != this.keys[n3]) continue;
            return n3;
        } while ((n3 = this.probeNext(n3)) != n2);
        return 1;
    }

    private int hashIndex(int n) {
        return IntObjectHashMap.hashCode(n) & this.mask;
    }

    private static int hashCode(int n) {
        return n;
    }

    private int probeNext(int n) {
        return n + 1 & this.mask;
    }

    private void growSize() {
        ++this.size;
        if (this.size > this.maxSize) {
            if (this.keys.length == Integer.MAX_VALUE) {
                throw new IllegalStateException("Max capacity reached at size=" + this.size);
            }
            this.rehash(this.keys.length << 1);
        }
    }

    private boolean removeAt(int n) {
        --this.size;
        this.keys[n] = 0;
        this.values[n] = null;
        int n2 = n;
        int n3 = this.probeNext(n);
        V v = this.values[n3];
        while (v != null) {
            int n4 = this.keys[n3];
            int n5 = this.hashIndex(n4);
            if (n3 < n5 && (n5 <= n2 || n2 <= n3) || n5 <= n2 && n2 <= n3) {
                this.keys[n2] = n4;
                this.values[n2] = v;
                this.keys[n3] = 0;
                this.values[n3] = null;
                n2 = n3;
            }
            n3 = this.probeNext(n3);
            v = this.values[n3];
        }
        return n2 != n;
    }

    private int calcMaxSize(int n) {
        int n2 = n - 1;
        return Math.min(n2, (int)((float)n * this.loadFactor));
    }

    private void rehash(int n) {
        int[] nArray = this.keys;
        V[] VArray = this.values;
        this.keys = new int[n];
        Object[] objectArray = new Object[n];
        this.values = objectArray;
        this.maxSize = this.calcMaxSize(n);
        this.mask = n - 1;
        block0: for (int i = 0; i < VArray.length; ++i) {
            V v = VArray[i];
            if (v == null) continue;
            int n2 = nArray[i];
            int n3 = this.hashIndex(n2);
            while (true) {
                if (this.values[n3] == null) {
                    this.keys[n3] = n2;
                    this.values[n3] = v;
                    continue block0;
                }
                n3 = this.probeNext(n3);
            }
        }
    }

    public String toString() {
        if (this.isEmpty()) {
            return "{}";
        }
        StringBuilder stringBuilder = new StringBuilder(4 * this.size);
        stringBuilder.append('{');
        boolean bl = true;
        for (int i = 0; i < this.values.length; ++i) {
            V v = this.values[i];
            if (v == null) continue;
            if (!bl) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(this.keyToString(this.keys[i])).append('=').append((Object)(v == this ? "(this Map)" : IntObjectHashMap.toExternal(v)));
            bl = false;
        }
        return stringBuilder.append('}').toString();
    }

    protected String keyToString(int n) {
        return Integer.toString(n);
    }

    @Override
    public Object put(Object object, Object object2) {
        return this.put((Integer)object, (V)object2);
    }

    static int access$300(IntObjectHashMap intObjectHashMap) {
        return intObjectHashMap.size;
    }

    static Set access$500(IntObjectHashMap intObjectHashMap) {
        return intObjectHashMap.entrySet;
    }

    static Object[] access$600(IntObjectHashMap intObjectHashMap) {
        return intObjectHashMap.values;
    }

    static boolean access$700(IntObjectHashMap intObjectHashMap, int n) {
        return intObjectHashMap.removeAt(n);
    }

    static int[] access$800(IntObjectHashMap intObjectHashMap) {
        return intObjectHashMap.keys;
    }

    static Object access$900(Object object) {
        return IntObjectHashMap.toExternal(object);
    }

    static Object access$1000(Object object) {
        return IntObjectHashMap.toInternal(object);
    }

    static {
        $assertionsDisabled = !IntObjectHashMap.class.desiredAssertionStatus();
        NULL_VALUE = new Object();
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Map.Entry<Integer, V> {
        private final int entryIndex;
        final IntObjectHashMap this$0;

        MapEntry(IntObjectHashMap intObjectHashMap, int n) {
            this.this$0 = intObjectHashMap;
            this.entryIndex = n;
        }

        @Override
        public Integer getKey() {
            this.verifyExists();
            return IntObjectHashMap.access$800(this.this$0)[this.entryIndex];
        }

        @Override
        public V getValue() {
            this.verifyExists();
            return IntObjectHashMap.access$900(IntObjectHashMap.access$600(this.this$0)[this.entryIndex]);
        }

        @Override
        public V setValue(V v) {
            this.verifyExists();
            Object object = IntObjectHashMap.access$900(IntObjectHashMap.access$600(this.this$0)[this.entryIndex]);
            IntObjectHashMap.access$600((IntObjectHashMap)this.this$0)[this.entryIndex] = IntObjectHashMap.access$1000(v);
            return object;
        }

        private void verifyExists() {
            if (IntObjectHashMap.access$600(this.this$0)[this.entryIndex] == null) {
                throw new IllegalStateException("The map entry has been removed");
            }
        }

        @Override
        public Object getKey() {
            return this.getKey();
        }
    }

    private final class MapIterator
    implements Iterator<Map.Entry<Integer, V>> {
        private final PrimitiveIterator iter;
        final IntObjectHashMap this$0;

        private MapIterator(IntObjectHashMap intObjectHashMap) {
            this.this$0 = intObjectHashMap;
            this.iter = new PrimitiveIterator(this.this$0, null);
        }

        @Override
        public boolean hasNext() {
            return this.iter.hasNext();
        }

        @Override
        public Map.Entry<Integer, V> next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.iter.next();
            return new MapEntry(this.this$0, PrimitiveIterator.access$1100(this.iter));
        }

        @Override
        public void remove() {
            this.iter.remove();
        }

        @Override
        public Object next() {
            return this.next();
        }

        MapIterator(IntObjectHashMap intObjectHashMap, 1 var2_2) {
            this(intObjectHashMap);
        }
    }

    private final class PrimitiveIterator
    implements Iterator<IntObjectMap.PrimitiveEntry<V>>,
    IntObjectMap.PrimitiveEntry<V> {
        private int prevIndex;
        private int nextIndex;
        private int entryIndex;
        final IntObjectHashMap this$0;

        private PrimitiveIterator(IntObjectHashMap intObjectHashMap) {
            this.this$0 = intObjectHashMap;
            this.prevIndex = -1;
            this.nextIndex = -1;
            this.entryIndex = -1;
        }

        private void scanNext() {
            while (++this.nextIndex != IntObjectHashMap.access$600(this.this$0).length && IntObjectHashMap.access$600(this.this$0)[this.nextIndex] == null) {
            }
        }

        @Override
        public boolean hasNext() {
            if (this.nextIndex == -1) {
                this.scanNext();
            }
            return this.nextIndex != IntObjectHashMap.access$600(this.this$0).length;
        }

        @Override
        public IntObjectMap.PrimitiveEntry<V> next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.prevIndex = this.nextIndex;
            this.scanNext();
            this.entryIndex = this.prevIndex;
            return this;
        }

        @Override
        public void remove() {
            if (this.prevIndex == -1) {
                throw new IllegalStateException("next must be called before each remove.");
            }
            if (IntObjectHashMap.access$700(this.this$0, this.prevIndex)) {
                this.nextIndex = this.prevIndex;
            }
            this.prevIndex = -1;
        }

        @Override
        public int key() {
            return IntObjectHashMap.access$800(this.this$0)[this.entryIndex];
        }

        @Override
        public V value() {
            return IntObjectHashMap.access$900(IntObjectHashMap.access$600(this.this$0)[this.entryIndex]);
        }

        @Override
        public void setValue(V v) {
            IntObjectHashMap.access$600((IntObjectHashMap)this.this$0)[this.entryIndex] = IntObjectHashMap.access$1000(v);
        }

        @Override
        public Object next() {
            return this.next();
        }

        PrimitiveIterator(IntObjectHashMap intObjectHashMap, 1 var2_2) {
            this(intObjectHashMap);
        }

        static int access$1100(PrimitiveIterator primitiveIterator) {
            return primitiveIterator.entryIndex;
        }
    }

    private final class KeySet
    extends AbstractSet<Integer> {
        final IntObjectHashMap this$0;

        private KeySet(IntObjectHashMap intObjectHashMap) {
            this.this$0 = intObjectHashMap;
        }

        @Override
        public int size() {
            return this.this$0.size();
        }

        @Override
        public boolean contains(Object object) {
            return this.this$0.containsKey(object);
        }

        @Override
        public boolean remove(Object object) {
            return this.this$0.remove(object) != null;
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            boolean bl = false;
            Iterator iterator2 = this.this$0.entries().iterator();
            while (iterator2.hasNext()) {
                IntObjectMap.PrimitiveEntry primitiveEntry = iterator2.next();
                if (collection.contains(primitiveEntry.key())) continue;
                bl = true;
                iterator2.remove();
            }
            return bl;
        }

        @Override
        public void clear() {
            this.this$0.clear();
        }

        @Override
        public Iterator<Integer> iterator() {
            return new Iterator<Integer>(this){
                private final Iterator<Map.Entry<Integer, V>> iter;
                final KeySet this$1;
                {
                    this.this$1 = keySet;
                    this.iter = IntObjectHashMap.access$500(this.this$1.this$0).iterator();
                }

                @Override
                public boolean hasNext() {
                    return this.iter.hasNext();
                }

                @Override
                public Integer next() {
                    return this.iter.next().getKey();
                }

                @Override
                public void remove() {
                    this.iter.remove();
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        KeySet(IntObjectHashMap intObjectHashMap, 1 var2_2) {
            this(intObjectHashMap);
        }
    }

    private final class EntrySet
    extends AbstractSet<Map.Entry<Integer, V>> {
        final IntObjectHashMap this$0;

        private EntrySet(IntObjectHashMap intObjectHashMap) {
            this.this$0 = intObjectHashMap;
        }

        @Override
        public Iterator<Map.Entry<Integer, V>> iterator() {
            return new MapIterator(this.this$0, null);
        }

        @Override
        public int size() {
            return this.this$0.size();
        }

        EntrySet(IntObjectHashMap intObjectHashMap, 1 var2_2) {
            this(intObjectHashMap);
        }
    }
}

