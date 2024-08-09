/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.collection;

import io.netty.util.collection.CharObjectMap;
import io.netty.util.internal.MathUtil;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class CharObjectHashMap<V>
implements CharObjectMap<V> {
    public static final int DEFAULT_CAPACITY = 8;
    public static final float DEFAULT_LOAD_FACTOR = 0.5f;
    private static final Object NULL_VALUE;
    private int maxSize;
    private final float loadFactor;
    private char[] keys;
    private V[] values;
    private int size;
    private int mask;
    private final Set<Character> keySet = new KeySet(this, null);
    private final Set<Map.Entry<Character, V>> entrySet = new EntrySet(this, null);
    private final Iterable<CharObjectMap.PrimitiveEntry<V>> entries = new Iterable<CharObjectMap.PrimitiveEntry<V>>(this){
        final CharObjectHashMap this$0;
        {
            this.this$0 = charObjectHashMap;
        }

        @Override
        public Iterator<CharObjectMap.PrimitiveEntry<V>> iterator() {
            return new PrimitiveIterator(this.this$0, null);
        }
    };
    static final boolean $assertionsDisabled;

    public CharObjectHashMap() {
        this(8, 0.5f);
    }

    public CharObjectHashMap(int n) {
        this(n, 0.5f);
    }

    public CharObjectHashMap(int n, float f) {
        if (f <= 0.0f || f > 1.0f) {
            throw new IllegalArgumentException("loadFactor must be > 0 and <= 1");
        }
        this.loadFactor = f;
        int n2 = MathUtil.safeFindNextPositivePowerOfTwo(n);
        this.mask = n2 - 1;
        this.keys = new char[n2];
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
    public V get(char c) {
        int n = this.indexOf(c);
        return n == -1 ? null : (V)CharObjectHashMap.toExternal(this.values[n]);
    }

    @Override
    public V put(char c, V v) {
        int n;
        int n2 = n = this.hashIndex(c);
        do {
            if (this.values[n2] == null) {
                this.keys[n2] = c;
                this.values[n2] = CharObjectHashMap.toInternal(v);
                this.growSize();
                return null;
            }
            if (this.keys[n2] != c) continue;
            V v2 = this.values[n2];
            this.values[n2] = CharObjectHashMap.toInternal(v);
            return CharObjectHashMap.toExternal(v2);
        } while ((n2 = this.probeNext(n2)) != n);
        throw new IllegalStateException("Unable to insert");
    }

    @Override
    public void putAll(Map<? extends Character, ? extends V> map) {
        if (map instanceof CharObjectHashMap) {
            CharObjectHashMap charObjectHashMap = (CharObjectHashMap)map;
            for (int i = 0; i < charObjectHashMap.values.length; ++i) {
                V v = charObjectHashMap.values[i];
                if (v == null) continue;
                this.put(charObjectHashMap.keys[i], v);
            }
            return;
        }
        for (Map.Entry<Character, V> entry : map.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public V remove(char c) {
        int n = this.indexOf(c);
        if (n == -1) {
            return null;
        }
        V v = this.values[n];
        this.removeAt(n);
        return CharObjectHashMap.toExternal(v);
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
        Arrays.fill(this.keys, '\u0000');
        Arrays.fill(this.values, null);
        this.size = 0;
    }

    @Override
    public boolean containsKey(char c) {
        return this.indexOf(c) >= 0;
    }

    @Override
    public boolean containsValue(Object object) {
        Object object2 = CharObjectHashMap.toInternal(object);
        for (V v : this.values) {
            if (v == null || !v.equals(object2)) continue;
            return false;
        }
        return true;
    }

    @Override
    public Iterable<CharObjectMap.PrimitiveEntry<V>> entries() {
        return this.entries;
    }

    @Override
    public Collection<V> values() {
        return new AbstractCollection<V>(this){
            final CharObjectHashMap this$0;
            {
                this.this$0 = charObjectHashMap;
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
                return CharObjectHashMap.access$300(this.this$0);
            }
        };
    }

    @Override
    public int hashCode() {
        int n = this.size;
        for (char c : this.keys) {
            n ^= CharObjectHashMap.hashCode(c);
        }
        return n;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof CharObjectMap)) {
            return true;
        }
        CharObjectMap charObjectMap = (CharObjectMap)object;
        if (this.size != charObjectMap.size()) {
            return true;
        }
        for (int i = 0; i < this.values.length; ++i) {
            V v = this.values[i];
            if (v == null) continue;
            char c = this.keys[i];
            Object v2 = charObjectMap.get(c);
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
    public V put(Character c, V v) {
        return this.put(this.objectToKey(c), v);
    }

    @Override
    public V remove(Object object) {
        return this.remove(this.objectToKey(object));
    }

    @Override
    public Set<Character> keySet() {
        return this.keySet;
    }

    @Override
    public Set<Map.Entry<Character, V>> entrySet() {
        return this.entrySet;
    }

    private char objectToKey(Object object) {
        return ((Character)object).charValue();
    }

    private int indexOf(char c) {
        int n;
        int n2 = n = this.hashIndex(c);
        do {
            if (this.values[n2] == null) {
                return 1;
            }
            if (c != this.keys[n2]) continue;
            return n2;
        } while ((n2 = this.probeNext(n2)) != n);
        return 1;
    }

    private int hashIndex(char c) {
        return CharObjectHashMap.hashCode(c) & this.mask;
    }

    private static int hashCode(char c) {
        return c;
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
        this.keys[n] = '\u0000';
        this.values[n] = null;
        int n2 = n;
        int n3 = this.probeNext(n);
        V v = this.values[n3];
        while (v != null) {
            char c = this.keys[n3];
            int n4 = this.hashIndex(c);
            if (n3 < n4 && (n4 <= n2 || n2 <= n3) || n4 <= n2 && n2 <= n3) {
                this.keys[n2] = c;
                this.values[n2] = v;
                this.keys[n3] = '\u0000';
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
        char[] cArray = this.keys;
        V[] VArray = this.values;
        this.keys = new char[n];
        Object[] objectArray = new Object[n];
        this.values = objectArray;
        this.maxSize = this.calcMaxSize(n);
        this.mask = n - 1;
        block0: for (int i = 0; i < VArray.length; ++i) {
            V v = VArray[i];
            if (v == null) continue;
            char c = cArray[i];
            int n2 = this.hashIndex(c);
            while (true) {
                if (this.values[n2] == null) {
                    this.keys[n2] = c;
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
            stringBuilder.append(this.keyToString(this.keys[i])).append('=').append((Object)(v == this ? "(this Map)" : CharObjectHashMap.toExternal(v)));
            bl = false;
        }
        return stringBuilder.append('}').toString();
    }

    protected String keyToString(char c) {
        return Character.toString(c);
    }

    @Override
    public Object put(Object object, Object object2) {
        return this.put((Character)object, (V)object2);
    }

    static int access$300(CharObjectHashMap charObjectHashMap) {
        return charObjectHashMap.size;
    }

    static Set access$500(CharObjectHashMap charObjectHashMap) {
        return charObjectHashMap.entrySet;
    }

    static Object[] access$600(CharObjectHashMap charObjectHashMap) {
        return charObjectHashMap.values;
    }

    static boolean access$700(CharObjectHashMap charObjectHashMap, int n) {
        return charObjectHashMap.removeAt(n);
    }

    static char[] access$800(CharObjectHashMap charObjectHashMap) {
        return charObjectHashMap.keys;
    }

    static Object access$900(Object object) {
        return CharObjectHashMap.toExternal(object);
    }

    static Object access$1000(Object object) {
        return CharObjectHashMap.toInternal(object);
    }

    static {
        $assertionsDisabled = !CharObjectHashMap.class.desiredAssertionStatus();
        NULL_VALUE = new Object();
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class MapEntry
    implements Map.Entry<Character, V> {
        private final int entryIndex;
        final CharObjectHashMap this$0;

        MapEntry(CharObjectHashMap charObjectHashMap, int n) {
            this.this$0 = charObjectHashMap;
            this.entryIndex = n;
        }

        @Override
        public Character getKey() {
            this.verifyExists();
            return Character.valueOf(CharObjectHashMap.access$800(this.this$0)[this.entryIndex]);
        }

        @Override
        public V getValue() {
            this.verifyExists();
            return CharObjectHashMap.access$900(CharObjectHashMap.access$600(this.this$0)[this.entryIndex]);
        }

        @Override
        public V setValue(V v) {
            this.verifyExists();
            Object object = CharObjectHashMap.access$900(CharObjectHashMap.access$600(this.this$0)[this.entryIndex]);
            CharObjectHashMap.access$600((CharObjectHashMap)this.this$0)[this.entryIndex] = CharObjectHashMap.access$1000(v);
            return object;
        }

        private void verifyExists() {
            if (CharObjectHashMap.access$600(this.this$0)[this.entryIndex] == null) {
                throw new IllegalStateException("The map entry has been removed");
            }
        }

        @Override
        public Object getKey() {
            return this.getKey();
        }
    }

    private final class MapIterator
    implements Iterator<Map.Entry<Character, V>> {
        private final PrimitiveIterator iter;
        final CharObjectHashMap this$0;

        private MapIterator(CharObjectHashMap charObjectHashMap) {
            this.this$0 = charObjectHashMap;
            this.iter = new PrimitiveIterator(this.this$0, null);
        }

        @Override
        public boolean hasNext() {
            return this.iter.hasNext();
        }

        @Override
        public Map.Entry<Character, V> next() {
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

        MapIterator(CharObjectHashMap charObjectHashMap, 1 var2_2) {
            this(charObjectHashMap);
        }
    }

    private final class PrimitiveIterator
    implements Iterator<CharObjectMap.PrimitiveEntry<V>>,
    CharObjectMap.PrimitiveEntry<V> {
        private int prevIndex;
        private int nextIndex;
        private int entryIndex;
        final CharObjectHashMap this$0;

        private PrimitiveIterator(CharObjectHashMap charObjectHashMap) {
            this.this$0 = charObjectHashMap;
            this.prevIndex = -1;
            this.nextIndex = -1;
            this.entryIndex = -1;
        }

        private void scanNext() {
            while (++this.nextIndex != CharObjectHashMap.access$600(this.this$0).length && CharObjectHashMap.access$600(this.this$0)[this.nextIndex] == null) {
            }
        }

        @Override
        public boolean hasNext() {
            if (this.nextIndex == -1) {
                this.scanNext();
            }
            return this.nextIndex != CharObjectHashMap.access$600(this.this$0).length;
        }

        @Override
        public CharObjectMap.PrimitiveEntry<V> next() {
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
            if (CharObjectHashMap.access$700(this.this$0, this.prevIndex)) {
                this.nextIndex = this.prevIndex;
            }
            this.prevIndex = -1;
        }

        @Override
        public char key() {
            return CharObjectHashMap.access$800(this.this$0)[this.entryIndex];
        }

        @Override
        public V value() {
            return CharObjectHashMap.access$900(CharObjectHashMap.access$600(this.this$0)[this.entryIndex]);
        }

        @Override
        public void setValue(V v) {
            CharObjectHashMap.access$600((CharObjectHashMap)this.this$0)[this.entryIndex] = CharObjectHashMap.access$1000(v);
        }

        @Override
        public Object next() {
            return this.next();
        }

        PrimitiveIterator(CharObjectHashMap charObjectHashMap, 1 var2_2) {
            this(charObjectHashMap);
        }

        static int access$1100(PrimitiveIterator primitiveIterator) {
            return primitiveIterator.entryIndex;
        }
    }

    private final class KeySet
    extends AbstractSet<Character> {
        final CharObjectHashMap this$0;

        private KeySet(CharObjectHashMap charObjectHashMap) {
            this.this$0 = charObjectHashMap;
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
                CharObjectMap.PrimitiveEntry primitiveEntry = iterator2.next();
                if (collection.contains(Character.valueOf(primitiveEntry.key()))) continue;
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
        public Iterator<Character> iterator() {
            return new Iterator<Character>(this){
                private final Iterator<Map.Entry<Character, V>> iter;
                final KeySet this$1;
                {
                    this.this$1 = keySet;
                    this.iter = CharObjectHashMap.access$500(this.this$1.this$0).iterator();
                }

                @Override
                public boolean hasNext() {
                    return this.iter.hasNext();
                }

                @Override
                public Character next() {
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

        KeySet(CharObjectHashMap charObjectHashMap, 1 var2_2) {
            this(charObjectHashMap);
        }
    }

    private final class EntrySet
    extends AbstractSet<Map.Entry<Character, V>> {
        final CharObjectHashMap this$0;

        private EntrySet(CharObjectHashMap charObjectHashMap) {
            this.this$0 = charObjectHashMap;
        }

        @Override
        public Iterator<Map.Entry<Character, V>> iterator() {
            return new MapIterator(this.this$0, null);
        }

        @Override
        public int size() {
            return this.this$0.size();
        }

        EntrySet(CharObjectHashMap charObjectHashMap, 1 var2_2) {
            this(charObjectHashMap);
        }
    }
}

