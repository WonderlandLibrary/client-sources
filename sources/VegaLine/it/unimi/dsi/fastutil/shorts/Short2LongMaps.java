/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongCollections;
import it.unimi.dsi.fastutil.longs.LongSets;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import it.unimi.dsi.fastutil.shorts.Short2LongFunctions;
import it.unimi.dsi.fastutil.shorts.Short2LongMap;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import it.unimi.dsi.fastutil.shorts.ShortSets;
import java.io.Serializable;
import java.util.Map;

public class Short2LongMaps {
    public static final EmptyMap EMPTY_MAP = new EmptyMap();

    private Short2LongMaps() {
    }

    public static Short2LongMap singleton(short key, long value) {
        return new Singleton(key, value);
    }

    public static Short2LongMap singleton(Short key, Long value) {
        return new Singleton(key, value);
    }

    public static Short2LongMap synchronize(Short2LongMap m) {
        return new SynchronizedMap(m);
    }

    public static Short2LongMap synchronize(Short2LongMap m, Object sync) {
        return new SynchronizedMap(m, sync);
    }

    public static Short2LongMap unmodifiable(Short2LongMap m) {
        return new UnmodifiableMap(m);
    }

    public static class UnmodifiableMap
    extends Short2LongFunctions.UnmodifiableFunction
    implements Short2LongMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Short2LongMap map;
        protected transient ObjectSet<Short2LongMap.Entry> entries;
        protected transient ShortSet keys;
        protected transient LongCollection values;

        protected UnmodifiableMap(Short2LongMap m) {
            super(m);
            this.map = m;
        }

        @Override
        public int size() {
            return this.map.size();
        }

        @Override
        public boolean containsKey(short k) {
            return this.map.containsKey(k);
        }

        @Override
        public boolean containsValue(long v) {
            return this.map.containsValue(v);
        }

        @Override
        public long defaultReturnValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void defaultReturnValue(long defRetValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long put(short k, long v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void putAll(Map<? extends Short, ? extends Long> m) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Short2LongMap.Entry> short2LongEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.unmodifiable(this.map.short2LongEntrySet());
            }
            return this.entries;
        }

        @Override
        public ShortSet keySet() {
            if (this.keys == null) {
                this.keys = ShortSets.unmodifiable(this.map.keySet());
            }
            return this.keys;
        }

        @Override
        public LongCollection values() {
            if (this.values == null) {
                return LongCollections.unmodifiable(this.map.values());
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
        public Long put(Short k, Long v) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public long remove(short k) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public long get(short k) {
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
        public ObjectSet<Map.Entry<Short, Long>> entrySet() {
            return ObjectSets.unmodifiable(this.map.entrySet());
        }
    }

    public static class SynchronizedMap
    extends Short2LongFunctions.SynchronizedFunction
    implements Short2LongMap,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Short2LongMap map;
        protected transient ObjectSet<Short2LongMap.Entry> entries;
        protected transient ShortSet keys;
        protected transient LongCollection values;

        protected SynchronizedMap(Short2LongMap m, Object sync) {
            super(m, sync);
            this.map = m;
        }

        protected SynchronizedMap(Short2LongMap m) {
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
        public boolean containsKey(short k) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.containsKey(k);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsValue(long v) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.containsValue(v);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long defaultReturnValue() {
            Object object = this.sync;
            synchronized (object) {
                return this.map.defaultReturnValue();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void defaultReturnValue(long defRetValue) {
            Object object = this.sync;
            synchronized (object) {
                this.map.defaultReturnValue(defRetValue);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long put(short k, long v) {
            Object object = this.sync;
            synchronized (object) {
                return this.map.put(k, v);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void putAll(Map<? extends Short, ? extends Long> m) {
            Object object = this.sync;
            synchronized (object) {
                this.map.putAll(m);
            }
        }

        @Override
        public ObjectSet<Short2LongMap.Entry> short2LongEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.synchronize(this.map.short2LongEntrySet(), this.sync);
            }
            return this.entries;
        }

        @Override
        public ShortSet keySet() {
            if (this.keys == null) {
                this.keys = ShortSets.synchronize(this.map.keySet(), this.sync);
            }
            return this.keys;
        }

        @Override
        public LongCollection values() {
            if (this.values == null) {
                return LongCollections.synchronize(this.map.values(), this.sync);
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
        public Long put(Short k, Long v) {
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
        public long remove(short k) {
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
        public long get(short k) {
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
        public ObjectSet<Map.Entry<Short, Long>> entrySet() {
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
    extends Short2LongFunctions.Singleton
    implements Short2LongMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet<Short2LongMap.Entry> entries;
        protected transient ShortSet keys;
        protected transient LongCollection values;

        protected Singleton(short key, long value) {
            super(key, value);
        }

        @Override
        public boolean containsValue(long v) {
            return this.value == v;
        }

        @Override
        public boolean containsValue(Object ov) {
            return (Long)ov == this.value;
        }

        @Override
        public void putAll(Map<? extends Short, ? extends Long> m) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Short2LongMap.Entry> short2LongEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new SingletonEntry());
            }
            return this.entries;
        }

        @Override
        public ShortSet keySet() {
            if (this.keys == null) {
                this.keys = ShortSets.singleton(this.key);
            }
            return this.keys;
        }

        @Override
        public LongCollection values() {
            if (this.values == null) {
                this.values = LongSets.singleton(this.value);
            }
            return this.values;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public ObjectSet<Map.Entry<Short, Long>> entrySet() {
            return this.short2LongEntrySet();
        }

        @Override
        public int hashCode() {
            return this.key ^ HashCommon.long2int(this.value);
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
        implements Short2LongMap.Entry,
        Map.Entry<Short, Long> {
            protected SingletonEntry() {
            }

            @Override
            @Deprecated
            public Short getKey() {
                return Singleton.this.key;
            }

            @Override
            @Deprecated
            public Long getValue() {
                return Singleton.this.value;
            }

            @Override
            public short getShortKey() {
                return Singleton.this.key;
            }

            @Override
            public long getLongValue() {
                return Singleton.this.value;
            }

            @Override
            public long setValue(long value) {
                throw new UnsupportedOperationException();
            }

            @Override
            public Long setValue(Long value) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean equals(Object o) {
                if (!(o instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry e = (Map.Entry)o;
                if (e.getKey() == null || !(e.getKey() instanceof Short)) {
                    return false;
                }
                if (e.getValue() == null || !(e.getValue() instanceof Long)) {
                    return false;
                }
                return Singleton.this.key == (Short)e.getKey() && Singleton.this.value == (Long)e.getValue();
            }

            @Override
            public int hashCode() {
                return Singleton.this.key ^ HashCommon.long2int(Singleton.this.value);
            }

            public String toString() {
                return Singleton.this.key + "->" + Singleton.this.value;
            }
        }
    }

    public static class EmptyMap
    extends Short2LongFunctions.EmptyFunction
    implements Short2LongMap,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyMap() {
        }

        @Override
        public boolean containsValue(long v) {
            return false;
        }

        @Override
        public void putAll(Map<? extends Short, ? extends Long> m) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ObjectSet<Short2LongMap.Entry> short2LongEntrySet() {
            return ObjectSets.EMPTY_SET;
        }

        @Override
        public ShortSet keySet() {
            return ShortSets.EMPTY_SET;
        }

        @Override
        public LongCollection values() {
            return LongSets.EMPTY_SET;
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
        public ObjectSet<Map.Entry<Short, Long>> entrySet() {
            return this.short2LongEntrySet();
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

