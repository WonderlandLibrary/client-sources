/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteCollections;
import it.unimi.dsi.fastutil.bytes.ByteSets;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import it.unimi.dsi.fastutil.objects.Reference2ByteFunctions;
import it.unimi.dsi.fastutil.objects.Reference2ByteMap;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import it.unimi.dsi.fastutil.objects.ReferenceSets;
import java.io.Serializable;
import java.util.Map;

public class Reference2ByteMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Reference2ByteMaps() {
    }

    public static <K> Reference2ByteMap<K> emptyMap() {
        return EMPTY_MAP;
    }

    public static <K> Reference2ByteMap<K> singleton(K key, byte value) {
        return new Singleton<K>(key, value);
    }

    public static <K> Reference2ByteMap<K> singleton(K key, Byte value) {
        return new Singleton<K>(key, value);
    }

    public static <K> Reference2ByteMap<K> synchronize(Reference2ByteMap<K> m) {
        return new SynchronizedMap<K>(m);
    }

    public static <K> Reference2ByteMap<K> synchronize(Reference2ByteMap<K> m, Object sync) {
        return new SynchronizedMap<K>(m, sync);
    }

    public static <K> Reference2ByteMap<K> unmodifiable(Reference2ByteMap<K> m) {
        return new UnmodifiableMap<K>(m);
    }

    public static class UnmodifiableMap<K>
    extends Reference2ByteFunctions.UnmodifiableFunction<K>
    implements Reference2ByteMap<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Reference2ByteMap<K> map;
        protected transient ObjectSet<Reference2ByteMap.Entry<K>> entries;
        protected transient ReferenceSet<K> keys;
        protected transient ByteCollection values;

        protected UnmodifiableMap(Reference2ByteMap<K> m) {
            super(m);
            this.map = m;
        }

        @Override
        public int size() {
            return this.map.size();
        }

        @Override
        public boolean containsKey(Object k) {
            return this.map.containsKey(k);
        }

        @Override
        public boolean containsValue(byte v) {
            return this.map.containsValue(v);
        }

        @Override
        public byte defaultReturnValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void defaultReturnValue(byte defRetValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte put(K k, byte v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void putAll(Map<? extends K, ? extends Byte> m) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Reference2ByteMap.Entry<K>> reference2ByteEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.reference2ByteEntrySet());
            }
            return this.entries;
        }

        @Override
        public ReferenceSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ReferenceSets.unmodifiable(this.map.keySet());
            }
            return this.keys;
        }

        @Override
        public ByteCollection values() {
            if (this.values == null) {
                return ByteCollections.unmodifiable(this.map.values());
            }
            return this.values;
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        public String toString() {
            return this.map.toString();
        }

        @Override
        public boolean containsValue(Object ov) {
            return this.map.containsValue(ov);
        }

        @Override
        public byte removeByte(Object k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public byte getByte(Object k) {
            return this.map.getByte(k);
        }

        @Override
        public boolean isEmpty() {
            return this.map.isEmpty();
        }

        @Override
        public ObjectSet<Map.Entry<K, Byte>> entrySet() {
            return ObjectSets.unmodifiable(this.map.entrySet());
        }
    }

    public static class SynchronizedMap<K>
    extends Reference2ByteFunctions.SynchronizedFunction<K>
    implements Reference2ByteMap<K>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Reference2ByteMap<K> map;
        protected transient ObjectSet<Reference2ByteMap.Entry<K>> entries;
        protected transient ReferenceSet<K> keys;
        protected transient ByteCollection values;

        protected SynchronizedMap(Reference2ByteMap<K> m, Object sync) {
            super(m, sync);
            this.map = m;
        }

        protected SynchronizedMap(Reference2ByteMap<K> m) {
            super(m);
            this.map = m;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int size() {
            Object object = this.sync;
            synchronized (object) {
                return this.map.size();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsKey(Object k) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.containsKey(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsValue(byte v) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.containsValue(v);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte defaultReturnValue() {
            Object object = this.sync;
            synchronized (object) {
                return this.map.defaultReturnValue();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void defaultReturnValue(byte defRetValue) {
            Object object = this.sync;
            synchronized (object) {
                this.map.defaultReturnValue(defRetValue);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte put(K k, byte v) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.put(k, v);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void putAll(Map<? extends K, ? extends Byte> m) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(m);
            }
        }

        @Override
        public ObjectSet<Reference2ByteMap.Entry<K>> reference2ByteEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.synchronize(this.map.reference2ByteEntrySet(), this.sync);
            }
            return this.entries;
        }

        @Override
        public ReferenceSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ReferenceSets.synchronize(this.map.keySet(), this.sync);
            }
            return this.keys;
        }

        @Override
        public ByteCollection values() {
            if (this.values == null) {
                return ByteCollections.synchronize(this.map.values(), this.sync);
            }
            return this.values;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void clear() {
            Object object = this.sync;
            synchronized (object) {
                this.map.clear();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public String toString() {
            Object object = this.sync;
            synchronized (object) {
                return this.map.toString();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Byte put(K k, Byte v) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.put(k, v);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public boolean containsValue(Object ov) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.containsValue(ov);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public byte removeByte(Object k) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.removeByte(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public byte getByte(Object k) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.getByte(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean isEmpty() {
            Object object = this.sync;
            synchronized (object) {
                return this.map.isEmpty();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public ObjectSet<Map.Entry<K, Byte>> entrySet() {
            Object object = this.sync;
            synchronized (object) {
                return this.map.entrySet();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int hashCode() {
            Object object = this.sync;
            synchronized (object) {
                return this.map.hashCode();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean equals(Object o) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.equals(o);
            }
        }
    }

    public static class Singleton<K>
    extends Reference2ByteFunctions.Singleton<K>
    implements Reference2ByteMap<K>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Reference2ByteMap.Entry<K>> entries;
        protected transient ReferenceSet<K> keys;
        protected transient ByteCollection values;

        protected Singleton(K key, byte value) {
            super(key, value);
        }

        @Override
        public boolean containsValue(byte v) {
            return this.value == v;
        }

        @Override
        public boolean containsValue(Object ov) {
            return (Byte)ov == this.value;
        }

        @Override
        public void putAll(Map<? extends K, ? extends Byte> m) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Reference2ByteMap.Entry<K>> reference2ByteEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new SingletonEntry());
            }
            return this.entries;
        }

        @Override
        public ReferenceSet<K> keySet() {
            if (this.keys == null) {
                this.keys = ReferenceSets.singleton(this.key);
            }
            return this.keys;
        }

        @Override
        public ByteCollection values() {
            if (this.values == null) {
                this.values = ByteSets.singleton(this.value);
            }
            return this.values;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public ObjectSet<Map.Entry<K, Byte>> entrySet() {
            return this.reference2ByteEntrySet();
        }

        @Override
        public int hashCode() {
            return System.identityHashCode(this.key) ^ this.value;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof Map)) {
                return false;
            }
            Map m = (Map)o;
            if (m.size() != 1) {
                return false;
            }
            return ((Map.Entry)this.entrySet().iterator().next()).equals(m.entrySet().iterator().next());
        }

        public String toString() {
            return "{" + this.key + "=>" + this.value + "}";
        }

        protected class SingletonEntry
        implements Reference2ByteMap.Entry<K>,
        Map.Entry<K, Byte> {
            protected SingletonEntry() {
            }

            @Override
            public K getKey() {
                return Singleton.this.key;
            }

            @Override
            @Deprecated
            public Byte getValue() {
                return Singleton.this.value;
            }

            @Override
            public byte getByteValue() {
                return Singleton.this.value;
            }

            @Override
            public byte setValue(byte value) {
                throw new UnsupportedOperationException();
            }

            @Override
            public Byte setValue(Byte value) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean equals(Object o) {
                if (!(o instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry e = (Map.Entry)o;
                if (e.getValue() == null || !(e.getValue() instanceof Byte)) {
                    return false;
                }
                return Singleton.this.key == e.getKey() && Singleton.this.value == (Byte)e.getValue();
            }

            @Override
            public int hashCode() {
                return System.identityHashCode(Singleton.this.key) ^ Singleton.this.value;
            }

            public String toString() {
                return Singleton.this.key + "->" + Singleton.this.value;
            }
        }
    }

    public static class EmptyMap<K>
    extends Reference2ByteFunctions.EmptyFunction<K>
    implements Reference2ByteMap<K>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyMap() {
        }

        @Override
        public boolean containsValue(byte v) {
            return false;
        }

        @Override
        public void putAll(Map<? extends K, ? extends Byte> m) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Reference2ByteMap.Entry<K>> reference2ByteEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public ReferenceSet<K> keySet() {
            return ReferenceSets.EMPTY_SET;
        }

        @Override
        public ByteCollection values() {
            return ByteSets.EMPTY_SET;
        }

        @Override
        public boolean containsValue(Object ov) {
            return false;
        }

        private Object readResolve() {
            return EMPTY_MAP;
        }

        @Override
        public Object clone() {
            return EMPTY_MAP;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public ObjectSet<Map.Entry<K, Byte>> entrySet() {
            return this.reference2ByteEntrySet();
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Map)) {
                return false;
            }
            return ((Map)o).isEmpty();
        }

        public String toString() {
            return "{}";
        }
    }
}

