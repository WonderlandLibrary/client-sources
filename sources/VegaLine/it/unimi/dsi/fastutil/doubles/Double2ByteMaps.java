/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteCollections;
import it.unimi.dsi.fastutil.bytes.ByteSets;
import it.unimi.dsi.fastutil.doubles.Double2ByteFunctions;
import it.unimi.dsi.fastutil.doubles.Double2ByteMap;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import it.unimi.dsi.fastutil.doubles.DoubleSets;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import java.io.Serializable;
import java.util.Map;

public class Double2ByteMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Double2ByteMaps() {
    }

    public static Double2ByteMap singleton(double key, byte value) {
        return new Singleton(key, value);
    }

    public static Double2ByteMap singleton(Double key, Byte value) {
        return new Singleton(key, value);
    }

    public static Double2ByteMap synchronize(Double2ByteMap m) {
        return new SynchronizedMap(m);
    }

    public static Double2ByteMap synchronize(Double2ByteMap m, Object sync) {
        return new SynchronizedMap(m, sync);
    }

    public static Double2ByteMap unmodifiable(Double2ByteMap m) {
        return new UnmodifiableMap(m);
    }

    public static class UnmodifiableMap
    extends Double2ByteFunctions.UnmodifiableFunction
    implements Double2ByteMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2ByteMap map;
        protected transient ObjectSet<Double2ByteMap.Entry> entries;
        protected transient DoubleSet keys;
        protected transient ByteCollection values;

        protected UnmodifiableMap(Double2ByteMap m) {
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
        public byte put(double k, byte v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void putAll(Map<? extends Double, ? extends Byte> m) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Double2ByteMap.Entry> double2ByteEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.double2ByteEntrySet());
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
        @Deprecated
        public Byte put(Double k, Byte v) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public byte remove(double k) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public byte get(double k) {
            return this.map.get(k);
        }

        @Override
        public boolean containsKey(Object ok) {
            return this.map.containsKey(ok);
        }

        @Override
        public boolean containsValue(Object ov) {
            return this.map.containsValue(ov);
        }

        @Override
        public boolean isEmpty() {
            return this.map.isEmpty();
        }

        @Override
        public ObjectSet<Map.Entry<Double, Byte>> entrySet() {
            return ObjectSets.unmodifiable(this.map.entrySet());
        }
    }

    public static class SynchronizedMap
    extends Double2ByteFunctions.SynchronizedFunction
    implements Double2ByteMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Double2ByteMap map;
        protected transient ObjectSet<Double2ByteMap.Entry> entries;
        protected transient DoubleSet keys;
        protected transient ByteCollection values;

        protected SynchronizedMap(Double2ByteMap m, Object sync) {
            super(m, sync);
            this.map = m;
        }

        protected SynchronizedMap(Double2ByteMap m) {
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
        public byte put(double k, byte v) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.put(k, v);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void putAll(Map<? extends Double, ? extends Byte> m) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(m);
            }
        }

        @Override
        public ObjectSet<Double2ByteMap.Entry> double2ByteEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.synchronize(this.map.double2ByteEntrySet(), this.sync);
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
        public Byte put(Double k, Byte v) {
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
        public byte remove(double k) {
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
        public byte get(double k) {
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
        public ObjectSet<Map.Entry<Double, Byte>> entrySet() {
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

    public static class Singleton
    extends Double2ByteFunctions.Singleton
    implements Double2ByteMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Double2ByteMap.Entry> entries;
        protected transient DoubleSet keys;
        protected transient ByteCollection values;

        protected Singleton(double key, byte value) {
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
        public void putAll(Map<? extends Double, ? extends Byte> m) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Double2ByteMap.Entry> double2ByteEntrySet() {
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
        public ObjectSet<Map.Entry<Double, Byte>> entrySet() {
            return this.double2ByteEntrySet();
        }

        @Override
        public int hashCode() {
            return HashCommon.double2int(this.key) ^ this.value;
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
        implements Double2ByteMap.Entry,
        Map.Entry<Double, Byte> {
            protected SingletonEntry() {
            }

            @Override
            @Deprecated
            public Double getKey() {
                return Singleton.this.key;
            }

            @Override
            @Deprecated
            public Byte getValue() {
                return Singleton.this.value;
            }

            @Override
            public double getDoubleKey() {
                return Singleton.this.key;
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
                if (e.getKey() == null || !(e.getKey() instanceof Double)) {
                    return false;
                }
                if (e.getValue() == null || !(e.getValue() instanceof Byte)) {
                    return false;
                }
                return Double.doubleToLongBits(Singleton.this.key) == Double.doubleToLongBits((Double)e.getKey()) && Singleton.this.value == (Byte)e.getValue();
            }

            @Override
            public int hashCode() {
                return HashCommon.double2int(Singleton.this.key) ^ Singleton.this.value;
            }

            public String toString() {
                return Singleton.this.key + "->" + Singleton.this.value;
            }
        }
    }

    public static class EmptyMap
    extends Double2ByteFunctions.EmptyFunction
    implements Double2ByteMap,
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
        public void putAll(Map<? extends Double, ? extends Byte> m) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Double2ByteMap.Entry> double2ByteEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public DoubleSet keySet() {
            return DoubleSets.EMPTY_SET;
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
        public ObjectSet<Map.Entry<Double, Byte>> entrySet() {
            return this.double2ByteEntrySet();
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

