/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.AbstractLongIterator;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.AbstractShort2LongFunction;
import it.unimi.dsi.fastutil.shorts.AbstractShortIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShortSet;
import it.unimi.dsi.fastutil.shorts.Short2LongMap;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractShort2LongMap
extends AbstractShort2LongFunction
implements Short2LongMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractShort2LongMap() {
    }

    @Override
    public boolean containsValue(Object ov) {
        if (ov == null) {
            return false;
        }
        return this.containsValue((Long)ov);
    }

    @Override
    public boolean containsValue(long v) {
        return this.values().contains(v);
    }

    @Override
    public boolean containsKey(short k) {
        return this.keySet().contains(k);
    }

    @Override
    public void putAll(Map<? extends Short, ? extends Long> m) {
        int n = m.size();
        Iterator<Map.Entry<? extends Short, ? extends Long>> i = m.entrySet().iterator();
        if (m instanceof Short2LongMap) {
            while (n-- != 0) {
                Short2LongMap.Entry e = (Short2LongMap.Entry)i.next();
                this.put(e.getShortKey(), e.getLongValue());
            }
        } else {
            while (n-- != 0) {
                Map.Entry<? extends Short, ? extends Long> e = i.next();
                this.put(e.getKey(), e.getValue());
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public ShortSet keySet() {
        return new AbstractShortSet(){

            @Override
            public boolean contains(short k) {
                return AbstractShort2LongMap.this.containsKey(k);
            }

            @Override
            public int size() {
                return AbstractShort2LongMap.this.size();
            }

            @Override
            public void clear() {
                AbstractShort2LongMap.this.clear();
            }

            @Override
            public ShortIterator iterator() {
                return new AbstractShortIterator(){
                    final ObjectIterator<Map.Entry<Short, Long>> i;
                    {
                        this.i = AbstractShort2LongMap.this.entrySet().iterator();
                    }

                    @Override
                    public short nextShort() {
                        return ((Short2LongMap.Entry)this.i.next()).getShortKey();
                    }

                    @Override
                    public boolean hasNext() {
                        return this.i.hasNext();
                    }

                    @Override
                    public void remove() {
                        this.i.remove();
                    }
                };
            }
        };
    }

    @Override
    public LongCollection values() {
        return new AbstractLongCollection(){

            @Override
            public boolean contains(long k) {
                return AbstractShort2LongMap.this.containsValue(k);
            }

            @Override
            public int size() {
                return AbstractShort2LongMap.this.size();
            }

            @Override
            public void clear() {
                AbstractShort2LongMap.this.clear();
            }

            @Override
            public LongIterator iterator() {
                return new AbstractLongIterator(){
                    final ObjectIterator<Map.Entry<Short, Long>> i;
                    {
                        this.i = AbstractShort2LongMap.this.entrySet().iterator();
                    }

                    @Override
                    @Deprecated
                    public long nextLong() {
                        return ((Short2LongMap.Entry)this.i.next()).getLongValue();
                    }

                    @Override
                    public boolean hasNext() {
                        return this.i.hasNext();
                    }
                };
            }
        };
    }

    @Override
    public ObjectSet<Map.Entry<Short, Long>> entrySet() {
        return this.short2LongEntrySet();
    }

    @Override
    public int hashCode() {
        int h = 0;
        int n = this.size();
        ObjectIterator i = this.entrySet().iterator();
        while (n-- != 0) {
            h += ((Map.Entry)i.next()).hashCode();
        }
        return h;
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
        if (m.size() != this.size()) {
            return false;
        }
        return this.entrySet().containsAll(m.entrySet());
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        ObjectIterator i = this.entrySet().iterator();
        int n = this.size();
        boolean first = true;
        s.append("{");
        while (n-- != 0) {
            if (first) {
                first = false;
            } else {
                s.append(", ");
            }
            Short2LongMap.Entry e = (Short2LongMap.Entry)i.next();
            s.append(String.valueOf(e.getShortKey()));
            s.append("=>");
            s.append(String.valueOf(e.getLongValue()));
        }
        s.append("}");
        return s.toString();
    }

    public static class BasicEntry
    implements Short2LongMap.Entry {
        protected short key;
        protected long value;

        public BasicEntry(Short key, Long value) {
            this.key = key;
            this.value = value;
        }

        public BasicEntry(short key, long value) {
            this.key = key;
            this.value = value;
        }

        @Override
        @Deprecated
        public Short getKey() {
            return this.key;
        }

        @Override
        public short getShortKey() {
            return this.key;
        }

        @Override
        @Deprecated
        public Long getValue() {
            return this.value;
        }

        @Override
        public long getLongValue() {
            return this.value;
        }

        @Override
        public long setValue(long value) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Long setValue(Long value) {
            return this.setValue((long)value);
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
            return this.key == (Short)e.getKey() && this.value == (Long)e.getValue();
        }

        @Override
        public int hashCode() {
            return this.key ^ HashCommon.long2int(this.value);
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

