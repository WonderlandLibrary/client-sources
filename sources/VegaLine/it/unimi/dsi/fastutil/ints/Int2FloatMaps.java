/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatCollections;
import it.unimi.dsi.fastutil.floats.FloatSets;
import it.unimi.dsi.fastutil.ints.Int2FloatFunctions;
import it.unimi.dsi.fastutil.ints.Int2FloatMap;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntSets;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import java.io.Serializable;
import java.util.Map;

public class Int2FloatMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Int2FloatMaps() {
    }

    public static Int2FloatMap singleton(int key, float value) {
        return new Singleton(key, value);
    }

    public static Int2FloatMap singleton(Integer key, Float value) {
        return new Singleton(key, value.floatValue());
    }

    public static Int2FloatMap synchronize(Int2FloatMap m) {
        return new SynchronizedMap(m);
    }

    public static Int2FloatMap synchronize(Int2FloatMap m, Object sync) {
        return new SynchronizedMap(m, sync);
    }

    public static Int2FloatMap unmodifiable(Int2FloatMap m) {
        return new UnmodifiableMap(m);
    }

    public static class UnmodifiableMap
    extends Int2FloatFunctions.UnmodifiableFunction
    implements Int2FloatMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Int2FloatMap map;
        protected transient ObjectSet<Int2FloatMap.Entry> entries;
        protected transient IntSet keys;
        protected transient FloatCollection values;

        protected UnmodifiableMap(Int2FloatMap m) {
            super(m);
            this.map = m;
        }

        @Override
        public int size() {
            return this.map.size();
        }

        @Override
        public boolean containsKey(int k) {
            return this.map.containsKey(k);
        }

        @Override
        public boolean containsValue(float v) {
            return this.map.containsValue(v);
        }

        @Override
        public float defaultReturnValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void defaultReturnValue(float defRetValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        public float put(int k, float v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void putAll(Map<? extends Integer, ? extends Float> m) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Int2FloatMap.Entry> int2FloatEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.int2FloatEntrySet());
            }
            return this.entries;
        }

        @Override
        public IntSet keySet() {
            if (this.keys == null) {
                this.keys = IntSets.unmodifiable(this.map.keySet());
            }
            return this.keys;
        }

        @Override
        public FloatCollection values() {
            if (this.values == null) {
                return FloatCollections.unmodifiable(this.map.values());
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
        public Float put(Integer k, Float v) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public float remove(int k) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public float get(int k) {
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
        public ObjectSet<Map.Entry<Integer, Float>> entrySet() {
            return ObjectSets.unmodifiable(this.map.entrySet());
        }
    }

    public static class SynchronizedMap
    extends Int2FloatFunctions.SynchronizedFunction
    implements Int2FloatMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Int2FloatMap map;
        protected transient ObjectSet<Int2FloatMap.Entry> entries;
        protected transient IntSet keys;
        protected transient FloatCollection values;

        protected SynchronizedMap(Int2FloatMap m, Object sync) {
            super(m, sync);
            this.map = m;
        }

        protected SynchronizedMap(Int2FloatMap m) {
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
        public boolean containsKey(int k) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.containsKey(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsValue(float v) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.containsValue(v);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float defaultReturnValue() {
            Object object = this.sync;
            synchronized (object) {
                return this.map.defaultReturnValue();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void defaultReturnValue(float defRetValue) {
            Object object = this.sync;
            synchronized (object) {
                this.map.defaultReturnValue(defRetValue);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public float put(int k, float v) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.put(k, v);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void putAll(Map<? extends Integer, ? extends Float> m) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(m);
            }
        }

        @Override
        public ObjectSet<Int2FloatMap.Entry> int2FloatEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.synchronize(this.map.int2FloatEntrySet(), this.sync);
            }
            return this.entries;
        }

        @Override
        public IntSet keySet() {
            if (this.keys == null) {
                this.keys = IntSets.synchronize(this.map.keySet(), this.sync);
            }
            return this.keys;
        }

        @Override
        public FloatCollection values() {
            if (this.values == null) {
                return FloatCollections.synchronize(this.map.values(), this.sync);
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
        public Float put(Integer k, Float v) {
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
        public float remove(int k) {
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
        public float get(int k) {
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
        public ObjectSet<Map.Entry<Integer, Float>> entrySet() {
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
    extends Int2FloatFunctions.Singleton
    implements Int2FloatMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Int2FloatMap.Entry> entries;
        protected transient IntSet keys;
        protected transient FloatCollection values;

        protected Singleton(int key, float value) {
            super(key, value);
        }

        @Override
        public boolean containsValue(float v) {
            return this.value == v;
        }

        @Override
        public boolean containsValue(Object ov) {
            return ((Float)ov).floatValue() == this.value;
        }

        @Override
        public void putAll(Map<? extends Integer, ? extends Float> m) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Int2FloatMap.Entry> int2FloatEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new SingletonEntry());
            }
            return this.entries;
        }

        @Override
        public IntSet keySet() {
            if (this.keys == null) {
                this.keys = IntSets.singleton(this.key);
            }
            return this.keys;
        }

        @Override
        public FloatCollection values() {
            if (this.values == null) {
                this.values = FloatSets.singleton(this.value);
            }
            return this.values;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public ObjectSet<Map.Entry<Integer, Float>> entrySet() {
            return this.int2FloatEntrySet();
        }

        @Override
        public int hashCode() {
            return this.key ^ HashCommon.float2int(this.value);
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
        implements Int2FloatMap.Entry,
        Map.Entry<Integer, Float> {
            protected SingletonEntry() {
            }

            @Override
            @Deprecated
            public Integer getKey() {
                return Singleton.this.key;
            }

            @Override
            @Deprecated
            public Float getValue() {
                return Float.valueOf(Singleton.this.value);
            }

            @Override
            public int getIntKey() {
                return Singleton.this.key;
            }

            @Override
            public float getFloatValue() {
                return Singleton.this.value;
            }

            @Override
            public float setValue(float value) {
                throw new UnsupportedOperationException();
            }

            @Override
            public Float setValue(Float value) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean equals(Object o) {
                if (!(o instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry e = (Map.Entry)o;
                if (e.getKey() == null || !(e.getKey() instanceof Integer)) {
                    return false;
                }
                if (e.getValue() == null || !(e.getValue() instanceof Float)) {
                    return false;
                }
                return Singleton.this.key == (Integer)e.getKey() && Singleton.this.value == ((Float)e.getValue()).floatValue();
            }

            @Override
            public int hashCode() {
                return Singleton.this.key ^ HashCommon.float2int(Singleton.this.value);
            }

            public String toString() {
                return Singleton.this.key + "->" + Singleton.this.value;
            }
        }
    }

    public static class EmptyMap
    extends Int2FloatFunctions.EmptyFunction
    implements Int2FloatMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyMap() {
        }

        @Override
        public boolean containsValue(float v) {
            return false;
        }

        @Override
        public void putAll(Map<? extends Integer, ? extends Float> m) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Int2FloatMap.Entry> int2FloatEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public IntSet keySet() {
            return IntSets.EMPTY_SET;
        }

        @Override
        public FloatCollection values() {
            return FloatSets.EMPTY_SET;
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
        public ObjectSet<Map.Entry<Integer, Float>> entrySet() {
            return this.int2FloatEntrySet();
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

