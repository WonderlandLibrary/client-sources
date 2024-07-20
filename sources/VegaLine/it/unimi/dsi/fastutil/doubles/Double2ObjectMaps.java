/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.Double2ObjectFunctions;
import it.unimi.dsi.fastutil.doubles.Double2ObjectMap;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import it.unimi.dsi.fastutil.doubles.DoubleSets;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectCollections;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import java.io.Serializable;
import java.util.Map;

public class Double2ObjectMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Double2ObjectMaps() {
    }

    public static <V> Double2ObjectMap<V> emptyMap() {
        return EMPTY_MAP;
    }

    public static <V> Double2ObjectMap<V> singleton(double key, V value) {
        return new Singleton<V>(key, value);
    }

    public static <V> Double2ObjectMap<V> singleton(Double key, V value) {
        return new Singleton<V>(key, value);
    }

    public static <V> Double2ObjectMap<V> synchronize(Double2ObjectMap<V> m) {
        return new SynchronizedMap<V>(m);
    }

    public static <V> Double2ObjectMap<V> synchronize(Double2ObjectMap<V> m, Object sync) {
        return new SynchronizedMap<V>(m, sync);
    }

    public static <V> Double2ObjectMap<V> unmodifiable(Double2ObjectMap<V> m) {
        return new UnmodifiableMap<V>(m);
    }

    public static class UnmodifiableMap<V>
    extends Double2ObjectFunctions.UnmodifiableFunction<V>
    implements Double2ObjectMap<V>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2ObjectMap<V> map;
        protected transient ObjectSet<Double2ObjectMap.Entry<V>> entries;
        protected transient DoubleSet keys;
        protected transient ObjectCollection<V> values;

        protected UnmodifiableMap(Double2ObjectMap<V> m) {
            super(m);
            this.map = m;
        }

        @Override
        public int size() {
            return this.map.size();
        }

        @Override
        public boolean containsKey(double k) {
            return this.map.containsKey(k);
        }

        @Override
        public boolean containsValue(Object v) {
            return this.map.containsValue(v);
        }

        @Override
        public V defaultReturnValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void defaultReturnValue(V defRetValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V put(double k, V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void putAll(Map<? extends Double, ? extends V> m) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Double2ObjectMap.Entry<V>> double2ObjectEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.double2ObjectEntrySet());
            }
            return this.entries;
        }

        @Override
        public DoubleSet keySet() {
            if (this.keys == null) {
                this.keys = DoubleSets.unmodifiable(this.map.keySet());
            }
            return this.keys;
        }

        @Override
        public ObjectCollection<V> values() {
            if (this.values == null) {
                return ObjectCollections.unmodifiable(this.map.values());
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
        @Deprecated
        public V remove(double k) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public V get(double k) {
            return this.map.get(k);
        }

        @Override
        public boolean containsKey(Object ok) {
            return this.map.containsKey(ok);
        }

        @Override
        @Deprecated
        public V remove(Object k) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public V get(Object k) {
            return this.map.get(k);
        }

        @Override
        public boolean isEmpty() {
            return this.map.isEmpty();
        }

        @Override
        public ObjectSet<Map.Entry<Double, V>> entrySet() {
            return ObjectSets.unmodifiable(this.map.entrySet());
        }
    }

    public static class SynchronizedMap<V>
    extends Double2ObjectFunctions.SynchronizedFunction<V>
    implements Double2ObjectMap<V>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2ObjectMap<V> map;
        protected transient ObjectSet<Double2ObjectMap.Entry<V>> entries;
        protected transient DoubleSet keys;
        protected transient ObjectCollection<V> values;

        protected SynchronizedMap(Double2ObjectMap<V> m, Object sync) {
            super(m, sync);
            this.map = m;
        }

        protected SynchronizedMap(Double2ObjectMap<V> m) {
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
        public boolean containsKey(double k) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.containsKey(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsValue(Object v) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.containsValue(v);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V defaultReturnValue() {
            Object object = this.sync;
            synchronized (object) {
                return this.map.defaultReturnValue();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void defaultReturnValue(V defRetValue) {
            Object object = this.sync;
            synchronized (object) {
                this.map.defaultReturnValue(defRetValue);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V put(double k, V v) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.put(k, v);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void putAll(Map<? extends Double, ? extends V> m) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(m);
            }
        }

        @Override
        public ObjectSet<Double2ObjectMap.Entry<V>> double2ObjectEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.synchronize(this.map.double2ObjectEntrySet(), this.sync);
            }
            return this.entries;
        }

        @Override
        public DoubleSet keySet() {
            if (this.keys == null) {
                this.keys = DoubleSets.synchronize(this.map.keySet(), this.sync);
            }
            return this.keys;
        }

        @Override
        public ObjectCollection<V> values() {
            if (this.values == null) {
                return ObjectCollections.synchronize(this.map.values(), this.sync);
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
        public V put(Double k, V v) {
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
        public V remove(double k) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.remove(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public V get(double k) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.get(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsKey(Object ok) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.containsKey(ok);
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
        public ObjectSet<Map.Entry<Double, V>> entrySet() {
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

    public static class Singleton<V>
    extends Double2ObjectFunctions.Singleton<V>
    implements Double2ObjectMap<V>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Double2ObjectMap.Entry<V>> entries;
        protected transient DoubleSet keys;
        protected transient ObjectCollection<V> values;

        protected Singleton(double key, V value) {
            super(key, value);
        }

        @Override
        public boolean containsValue(Object v) {
            return this.value == null ? v == null : this.value.equals(v);
        }

        @Override
        public void putAll(Map<? extends Double, ? extends V> m) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Double2ObjectMap.Entry<V>> double2ObjectEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new SingletonEntry());
            }
            return this.entries;
        }

        @Override
        public DoubleSet keySet() {
            if (this.keys == null) {
                this.keys = DoubleSets.singleton(this.key);
            }
            return this.keys;
        }

        @Override
        public ObjectCollection<V> values() {
            if (this.values == null) {
                this.values = ObjectSets.singleton(this.value);
            }
            return this.values;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public ObjectSet<Map.Entry<Double, V>> entrySet() {
            return this.double2ObjectEntrySet();
        }

        @Override
        public int hashCode() {
            return HashCommon.double2int(this.key) ^ (this.value == null ? 0 : this.value.hashCode());
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
        implements Double2ObjectMap.Entry<V>,
        Map.Entry<Double, V> {
            protected SingletonEntry() {
            }

            @Override
            @Deprecated
            public Double getKey() {
                return Singleton.this.key;
            }

            @Override
            public V getValue() {
                return Singleton.this.value;
            }

            @Override
            public double getDoubleKey() {
                return Singleton.this.key;
            }

            @Override
            public V setValue(V value) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean equals(Object o) {
                if (!(o instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry e = (Map.Entry)o;
                if (e.getKey() == null || !(e.getKey() instanceof Double)) {
                    return false;
                }
                return Double.doubleToLongBits(Singleton.this.key) == Double.doubleToLongBits((Double)e.getKey()) && (Singleton.this.value == null ? e.getValue() == null : Singleton.this.value.equals(e.getValue()));
            }

            @Override
            public int hashCode() {
                return HashCommon.double2int(Singleton.this.key) ^ (Singleton.this.value == null ? 0 : Singleton.this.value.hashCode());
            }

            public String toString() {
                return Singleton.this.key + "->" + Singleton.this.value;
            }
        }
    }

    public static class EmptyMap<V>
    extends Double2ObjectFunctions.EmptyFunction<V>
    implements Double2ObjectMap<V>,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyMap() {
        }

        @Override
        public boolean containsValue(Object v) {
            return false;
        }

        @Override
        public void putAll(Map<? extends Double, ? extends V> m) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Double2ObjectMap.Entry<V>> double2ObjectEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public DoubleSet keySet() {
            return DoubleSets.EMPTY_SET;
        }

        @Override
        public ObjectCollection<V> values() {
            return ObjectSets.EMPTY_SET;
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
        public ObjectSet<Map.Entry<Double, V>> entrySet() {
            return this.double2ObjectEntrySet();
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

