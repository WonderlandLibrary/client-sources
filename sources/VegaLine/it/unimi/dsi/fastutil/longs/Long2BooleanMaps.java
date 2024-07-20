/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollections;
import it.unimi.dsi.fastutil.booleans.BooleanSets;
import it.unimi.dsi.fastutil.longs.Long2BooleanFunctions;
import it.unimi.dsi.fastutil.longs.Long2BooleanMap;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.LongSets;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import java.io.Serializable;
import java.util.Map;

public class Long2BooleanMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Long2BooleanMaps() {
    }

    public static Long2BooleanMap singleton(long key, boolean value) {
        return new Singleton(key, value);
    }

    public static Long2BooleanMap singleton(Long key, Boolean value) {
        return new Singleton(key, value);
    }

    public static Long2BooleanMap synchronize(Long2BooleanMap m) {
        return new SynchronizedMap(m);
    }

    public static Long2BooleanMap synchronize(Long2BooleanMap m, Object sync) {
        return new SynchronizedMap(m, sync);
    }

    public static Long2BooleanMap unmodifiable(Long2BooleanMap m) {
        return new UnmodifiableMap(m);
    }

    public static class UnmodifiableMap
    extends Long2BooleanFunctions.UnmodifiableFunction
    implements Long2BooleanMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Long2BooleanMap map;
        protected transient ObjectSet<Long2BooleanMap.Entry> entries;
        protected transient LongSet keys;
        protected transient BooleanCollection values;

        protected UnmodifiableMap(Long2BooleanMap m) {
            super(m);
            this.map = m;
        }

        @Override
        public int size() {
            return this.map.size();
        }

        @Override
        public boolean containsKey(long k) {
            return this.map.containsKey(k);
        }

        @Override
        public boolean containsValue(boolean v) {
            return this.map.containsValue(v);
        }

        @Override
        public boolean defaultReturnValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void defaultReturnValue(boolean defRetValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean put(long k, boolean v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void putAll(Map<? extends Long, ? extends Boolean> m) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Long2BooleanMap.Entry> long2BooleanEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.long2BooleanEntrySet());
            }
            return this.entries;
        }

        @Override
        public LongSet keySet() {
            if (this.keys == null) {
                this.keys = LongSets.unmodifiable(this.map.keySet());
            }
            return this.keys;
        }

        @Override
        public BooleanCollection values() {
            if (this.values == null) {
                return BooleanCollections.unmodifiable(this.map.values());
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
        public Boolean put(Long k, Boolean v) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean remove(long k) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean get(long k) {
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
        public ObjectSet<Map.Entry<Long, Boolean>> entrySet() {
            return ObjectSets.unmodifiable(this.map.entrySet());
        }
    }

    public static class SynchronizedMap
    extends Long2BooleanFunctions.SynchronizedFunction
    implements Long2BooleanMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Long2BooleanMap map;
        protected transient ObjectSet<Long2BooleanMap.Entry> entries;
        protected transient LongSet keys;
        protected transient BooleanCollection values;

        protected SynchronizedMap(Long2BooleanMap m, Object sync) {
            super(m, sync);
            this.map = m;
        }

        protected SynchronizedMap(Long2BooleanMap m) {
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
        public boolean containsKey(long k) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.containsKey(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsValue(boolean v) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.containsValue(v);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean defaultReturnValue() {
            Object object = this.sync;
            synchronized (object) {
                return this.map.defaultReturnValue();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void defaultReturnValue(boolean defRetValue) {
            Object object = this.sync;
            synchronized (object) {
                this.map.defaultReturnValue(defRetValue);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean put(long k, boolean v) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.put(k, v);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void putAll(Map<? extends Long, ? extends Boolean> m) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(m);
            }
        }

        @Override
        public ObjectSet<Long2BooleanMap.Entry> long2BooleanEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.synchronize(this.map.long2BooleanEntrySet(), this.sync);
            }
            return this.entries;
        }

        @Override
        public LongSet keySet() {
            if (this.keys == null) {
                this.keys = LongSets.synchronize(this.map.keySet(), this.sync);
            }
            return this.keys;
        }

        @Override
        public BooleanCollection values() {
            if (this.values == null) {
                return BooleanCollections.synchronize(this.map.values(), this.sync);
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
        public Boolean put(Long k, Boolean v) {
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
        public boolean remove(long k) {
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
        public boolean get(long k) {
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
        public ObjectSet<Map.Entry<Long, Boolean>> entrySet() {
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
    extends Long2BooleanFunctions.Singleton
    implements Long2BooleanMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Long2BooleanMap.Entry> entries;
        protected transient LongSet keys;
        protected transient BooleanCollection values;

        protected Singleton(long key, boolean value) {
            super(key, value);
        }

        @Override
        public boolean containsValue(boolean v) {
            return this.value == v;
        }

        @Override
        public boolean containsValue(Object ov) {
            return (Boolean)ov == this.value;
        }

        @Override
        public void putAll(Map<? extends Long, ? extends Boolean> m) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Long2BooleanMap.Entry> long2BooleanEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new SingletonEntry());
            }
            return this.entries;
        }

        @Override
        public LongSet keySet() {
            if (this.keys == null) {
                this.keys = LongSets.singleton(this.key);
            }
            return this.keys;
        }

        @Override
        public BooleanCollection values() {
            if (this.values == null) {
                this.values = BooleanSets.singleton(this.value);
            }
            return this.values;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public ObjectSet<Map.Entry<Long, Boolean>> entrySet() {
            return this.long2BooleanEntrySet();
        }

        @Override
        public int hashCode() {
            return HashCommon.long2int(this.key) ^ (this.value ? 1231 : 1237);
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
        implements Long2BooleanMap.Entry,
        Map.Entry<Long, Boolean> {
            protected SingletonEntry() {
            }

            @Override
            @Deprecated
            public Long getKey() {
                return Singleton.this.key;
            }

            @Override
            @Deprecated
            public Boolean getValue() {
                return Singleton.this.value;
            }

            @Override
            public long getLongKey() {
                return Singleton.this.key;
            }

            @Override
            public boolean getBooleanValue() {
                return Singleton.this.value;
            }

            @Override
            public boolean setValue(boolean value) {
                throw new UnsupportedOperationException();
            }

            @Override
            public Boolean setValue(Boolean value) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean equals(Object o) {
                if (!(o instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry e = (Map.Entry)o;
                if (e.getKey() == null || !(e.getKey() instanceof Long)) {
                    return false;
                }
                if (e.getValue() == null || !(e.getValue() instanceof Boolean)) {
                    return false;
                }
                return Singleton.this.key == (Long)e.getKey() && Singleton.this.value == (Boolean)e.getValue();
            }

            @Override
            public int hashCode() {
                return HashCommon.long2int(Singleton.this.key) ^ (Singleton.this.value ? 1231 : 1237);
            }

            public String toString() {
                return Singleton.this.key + "->" + Singleton.this.value;
            }
        }
    }

    public static class EmptyMap
    extends Long2BooleanFunctions.EmptyFunction
    implements Long2BooleanMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyMap() {
        }

        @Override
        public boolean containsValue(boolean v) {
            return false;
        }

        @Override
        public void putAll(Map<? extends Long, ? extends Boolean> m) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Long2BooleanMap.Entry> long2BooleanEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public LongSet keySet() {
            return LongSets.EMPTY_SET;
        }

        @Override
        public BooleanCollection values() {
            return BooleanSets.EMPTY_SET;
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
        public ObjectSet<Map.Entry<Long, Boolean>> entrySet() {
            return this.long2BooleanEntrySet();
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

