/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.collection;

import io.netty.util.collection.ShortObjectMap;
import io.netty.util.internal.MathUtil;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class ShortObjectHashMap<V>
implements ShortObjectMap<V> {
    public static final int DEFAULT_CAPACITY = 8;
    public static final float DEFAULT_LOAD_FACTOR = 0.5f;
    private static final Object NULL_VALUE;
    private int maxSize;
    private final float loadFactor;
    private short[] keys;
    private V[] values;
    private int size;
    private int mask;
    private final Set<Short> keySet = new KeySet(this, null);
    private final Set<Map.Entry<Short, V>> entrySet = new EntrySet(this, null);
    private final Iterable<ShortObjectMap.PrimitiveEntry<V>> entries = new Iterable<ShortObjectMap.PrimitiveEntry<V>>(this){
        final ShortObjectHashMap this$0;
        {
            this.this$0 = shortObjectHashMap;
        }

        @Override
        public Iterator<ShortObjectMap.PrimitiveEntry<V>> iterator() {
            return new PrimitiveIterator(this.this$0, null);
        }
    };
    static final boolean $assertionsDisabled;

    public ShortObjectHashMap() {
        this(8, 0.5f);
    }

    public ShortObjectHashMap(int n) {
        this(n, 0.5f);
    }

    public ShortObjectHashMap(int n, float f) {
        if (f <= 0.0f || f > 1.0f) {
            throw new IllegalArgumentException("loadFactor must be > 0 and <= 1");
        }
        this.loadFactor = f;
        int n2 = MathUtil.safeFindNextPositivePowerOfTwo(n);
        this.mask = n2 - 1;
        this.keys = new short[n2];
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
    public V get(short s) {
        int n = this.indexOf(s);
        return n == -1 ? null : (V)ShortObjectHashMap.toExternal(this.values[n]);
    }

    @Override
    public V put(short s, V v) {
        int n;
        int n2 = n = this.hashIndex(s);
        do {
            if (this.values[n2] == null) {
                this.keys[n2] = s;
                this.values[n2] = ShortObjectHashMap.toInternal(v);
                this.growSize();
                return null;
            }
            if (this.keys[n2] != s) continue;
            V v2 = this.values[n2];
            this.values[n2] = ShortObjectHashMap.toInternal(v);
            return ShortObjectHashMap.toExternal(v2);
        } while ((n2 = this.probeNext(n2)) != n);
        throw new IllegalStateException("Unable to insert");
    }

    @Override
    public void putAll(Map<? extends Short, ? extends V> map) {
        if (map instanceof ShortObjectHashMap) {
            ShortObjectHashMap shortObjectHashMap = (ShortObjectHashMap)map;
            for (int i = 0; i < shortObjectHashMap.values.length; ++i) {
                V v = shortObjectHashMap.values[i];
                if (v == null) continue;
                this.put(shortObjectHashMap.keys[i], v);
            }
            return;
        }
        for (Map.Entry<Short, V> entry : map.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public V remove(short s) {
        int n = this.indexOf(s);
        if (n == -1) {
            return null;
        }
        V v = this.values[n];
        this.removeAt(n);
        return ShortObjectHashMap.toExternal(v);
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
        Arrays.fill(this.keys, (short)0);
        Arrays.fill(this.values, null);
        this.size = 0;
    }

    @Override
    public boolean containsKey(short s) {
        return this.indexOf(s) >= 0;
    }

    @Override
    public boolean containsValue(Object object) {
        Object object2 = ShortObjectHashMap.toInternal(object);
        for (V v : this.values) {
            if (v == null || !v.equals(object2)) continue;
            return false;
        }
        return true;
    }

    @Override
    public Iterable<ShortObjectMap.PrimitiveEntry<V>> entries() {
        return this.entries;
    }

    @Override
    public Collection<V> values() {
        return new AbstractCollection<V>(this){
            final ShortObjectHashMap this$0;
            {
                this.this$0 = shortObjectHashMap;
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
                return ShortObjectHashMap.access$300(this.this$0);
            }
        };
    }

    @Override
    public int hashCode() {
        int n = this.size;
        for (short s : this.keys) {
            n ^= ShortObjectHashMap.hashCode(s);
        }
        return n;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof ShortObjectMap)) {
            return true;
        }
        ShortObjectMap shortObjectMap = (ShortObjectMap)object;
        if (this.size != shortObjectMap.size()) {
            return true;
        }
        for (int i = 0; i < this.values.length; ++i) {
            V v = this.values[i];
            if (v == null) continue;
            short s = this.keys[i];
            Object v2 = shortObjectMap.get(s);
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
    public V put(Short s, V v) {
        return this.put(this.objectToKey(s), v);
    }

    @Override
    public V remove(Object object) {
        return this.remove(this.objectToKey(object));
    }

    @Override
    public Set<Short> keySet() {
        return this.keySet;
    }

    @Override
    public Set<Map.Entry<Short, V>> entrySet() {
        return this.entrySet;
    }

    private short objectToKey(Object object) {
        return (Short)object;
    }

    private int indexOf(short s) {
        int n;
        int n2 = n = this.hashIndex(s);
        do {
            if (this.values[n2] == null) {
                return 1;
            }
            if (s != this.keys[n2]) continue;
            return n2;
        } while ((n2 = this.probeNext(n2)) != n);
        return 1;
    }

    private int hashIndex(short s) {
        return ShortObjectHashMap.hashCode(s) & this.mask;
    }

    private static int hashCode(short s) {
        return s;
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
            short s = this.keys[n3];
            int n4 = this.hashIndex(s);
            if (n3 < n4 && (n4 <= n2 || n2 <= n3) || n4 <= n2 && n2 <= n3) {
                this.keys[n2] = s;
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
        short[] sArray = this.keys;
        V[] VArray = this.values;
        this.keys = new short[n];
        Object[] objectArray = new Object[n];
        this.values = objectArray;
        this.maxSize = this.calcMaxSize(n);
        this.mask = n - 1;
        block0: for (int i = 0; i < VArray.length; ++i) {
            V v = VArray[i];
            if (v == null) continue;
            short s = sArray[i];
            int n2 = this.hashIndex(s);
            while (true) {
                if (this.values[n2] == null) {
                    this.keys[n2] = s;
                    this.values[n2] = v;
                    continue block0;
                }
                n2 = this.probeNext(n2);
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
            stringBuilder.append(this.keyToString(this.keys[i])).append('=').append((Object)(v == this ? "(this Map)" : ShortObjectHashMap.toExternal(v)));
            bl = false;
        }
        return stringBuilder.append('}').toString();
    }

    protected String keyToString(short s) {
        return Short.toString(s);
    }

    @Override
    public Object put(Object object, Object object2) {
        return this.put((Short)object, (V)object2);
    }

    static int access$300(ShortObjectHashMap shortObjectHashMap) {
        return shortObjectHashMap.size;
    }

    static Set access$500(ShortObjectHashMap shortObjectHashMap) {
        return shortObjectHashMap.entrySet;
    }

    static Object[] access$600(ShortObjectHashMap shortObjectHashMap) {
        return shortObjectHashMap.values;
    }

    static boolean access$700(ShortObjectHashMap shortObjectHashMap, int n) {
        return shortObjectHashMap.removeAt(n);
    }

    static short[] access$800(ShortObjectHashMap shortObjectHashMap) {
        return shortObjectHashMap.keys;
    }

    static Object access$900(Object object) {
        return ShortObjectHashMap.toExternal(object);
    }

    static Object access$1000(Object object) {
        return ShortObjectHashMap.toInternal(object);
    }

    static {
        $assertionsDisabled = !ShortObjectHashMap.class.desiredAssertionStatus();
        NULL_VALUE = new Object();
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Map.Entry<Short, V> {
        private final int entryIndex;
        final ShortObjectHashMap this$0;

        MapEntry(ShortObjectHashMap shortObjectHashMap, int n) {
            this.this$0 = shortObjectHashMap;
            this.entryIndex = n;
        }

        @Override
        public Short getKey() {
            this.verifyExists();
            return ShortObjectHashMap.access$800(this.this$0)[this.entryIndex];
        }

        @Override
        public V getValue() {
            this.verifyExists();
            return ShortObjectHashMap.access$900(ShortObjectHashMap.access$600(this.this$0)[this.entryIndex]);
        }

        @Override
        public V setValue(V v) {
            this.verifyExists();
            Object object = ShortObjectHashMap.access$900(ShortObjectHashMap.access$600(this.this$0)[this.entryIndex]);
            ShortObjectHashMap.access$600((ShortObjectHashMap)this.this$0)[this.entryIndex] = ShortObjectHashMap.access$1000(v);
            return object;
        }

        private void verifyExists() {
            if (ShortObjectHashMap.access$600(this.this$0)[this.entryIndex] == null) {
                throw new IllegalStateException("The map entry has been removed");
            }
        }

        @Override
        public Object getKey() {
            return this.getKey();
        }
    }

    private final class MapIterator
    implements Iterator<Map.Entry<Short, V>> {
        private final PrimitiveIterator iter;
        final ShortObjectHashMap this$0;

        private MapIterator(ShortObjectHashMap shortObjectHashMap) {
            this.this$0 = shortObjectHashMap;
            this.iter = new PrimitiveIterator(this.this$0, null);
        }

        @Override
        public boolean hasNext() {
            return this.iter.hasNext();
        }

        @Override
        public Map.Entry<Short, V> next() {
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

        MapIterator(ShortObjectHashMap shortObjectHashMap, 1 var2_2) {
            this(shortObjectHashMap);
        }
    }

    private final class PrimitiveIterator
    implements Iterator<ShortObjectMap.PrimitiveEntry<V>>,
    ShortObjectMap.PrimitiveEntry<V> {
        private int prevIndex;
        private int nextIndex;
        private int entryIndex;
        final ShortObjectHashMap this$0;

        private PrimitiveIterator(ShortObjectHashMap shortObjectHashMap) {
            this.this$0 = shortObjectHashMap;
            this.prevIndex = -1;
            this.nextIndex = -1;
            this.entryIndex = -1;
        }

        private void scanNext() {
            while (++this.nextIndex != ShortObjectHashMap.access$600(this.this$0).length && ShortObjectHashMap.access$600(this.this$0)[this.nextIndex] == null) {
            }
        }

        @Override
        public boolean hasNext() {
            if (this.nextIndex == -1) {
                this.scanNext();
            }
            return this.nextIndex != ShortObjectHashMap.access$600(this.this$0).length;
        }

        @Override
        public ShortObjectMap.PrimitiveEntry<V> next() {
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
            if (ShortObjectHashMap.access$700(this.this$0, this.prevIndex)) {
                this.nextIndex = this.prevIndex;
            }
            this.prevIndex = -1;
        }

        @Override
        public short key() {
            return ShortObjectHashMap.access$800(this.this$0)[this.entryIndex];
        }

        @Override
        public V value() {
            return ShortObjectHashMap.access$900(ShortObjectHashMap.access$600(this.this$0)[this.entryIndex]);
        }

        @Override
        public void setValue(V v) {
            ShortObjectHashMap.access$600((ShortObjectHashMap)this.this$0)[this.entryIndex] = ShortObjectHashMap.access$1000(v);
        }

        @Override
        public Object next() {
            return this.next();
        }

        PrimitiveIterator(ShortObjectHashMap shortObjectHashMap, 1 var2_2) {
            this(shortObjectHashMap);
        }

        static int access$1100(PrimitiveIterator primitiveIterator) {
            return primitiveIterator.entryIndex;
        }
    }

    private final class KeySet
    extends AbstractSet<Short> {
        final ShortObjectHashMap this$0;

        private KeySet(ShortObjectHashMap shortObjectHashMap) {
            this.this$0 = shortObjectHashMap;
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
                ShortObjectMap.PrimitiveEntry primitiveEntry = iterator2.next();
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
        public Iterator<Short> iterator() {
            return new Iterator<Short>(this){
                private final Iterator<Map.Entry<Short, V>> iter;
                final KeySet this$1;
                {
                    this.this$1 = keySet;
                    this.iter = ShortObjectHashMap.access$500(this.this$1.this$0).iterator();
                }

                @Override
                public boolean hasNext() {
                    return this.iter.hasNext();
                }

                @Override
                public Short next() {
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

        KeySet(ShortObjectHashMap shortObjectHashMap, 1 var2_2) {
            this(shortObjectHashMap);
        }
    }

    private final class EntrySet
    extends AbstractSet<Map.Entry<Short, V>> {
        final ShortObjectHashMap this$0;

        private EntrySet(ShortObjectHashMap shortObjectHashMap) {
            this.this$0 = shortObjectHashMap;
        }

        @Override
        public Iterator<Map.Entry<Short, V>> iterator() {
            return new MapIterator(this.this$0, null);
        }

        @Override
        public int size() {
            return this.this$0.size();
        }

        EntrySet(ShortObjectHashMap shortObjectHashMap, 1 var2_2) {
            this(shortObjectHashMap);
        }
    }
}

