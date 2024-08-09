/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShort2IntFunction;
import it.unimi.dsi.fastutil.shorts.AbstractShortSet;
import it.unimi.dsi.fastutil.shorts.Short2IntMap;
import it.unimi.dsi.fastutil.shorts.Short2IntMaps;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractShort2IntMap
extends AbstractShort2IntFunction
implements Short2IntMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractShort2IntMap() {
    }

    @Override
    public boolean containsValue(int n) {
        return this.values().contains(n);
    }

    @Override
    public boolean containsKey(short s) {
        Iterator iterator2 = this.short2IntEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Short2IntMap.Entry)iterator2.next()).getShortKey() != s) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public ShortSet keySet() {
        return new AbstractShortSet(this){
            final AbstractShort2IntMap this$0;
            {
                this.this$0 = abstractShort2IntMap;
            }

            @Override
            public boolean contains(short s) {
                return this.this$0.containsKey(s);
            }

            @Override
            public int size() {
                return this.this$0.size();
            }

            @Override
            public void clear() {
                this.this$0.clear();
            }

            @Override
            public ShortIterator iterator() {
                return new ShortIterator(this){
                    private final ObjectIterator<Short2IntMap.Entry> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Short2IntMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public short nextShort() {
                        return ((Short2IntMap.Entry)this.i.next()).getShortKey();
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

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        };
    }

    @Override
    public IntCollection values() {
        return new AbstractIntCollection(this){
            final AbstractShort2IntMap this$0;
            {
                this.this$0 = abstractShort2IntMap;
            }

            @Override
            public boolean contains(int n) {
                return this.this$0.containsValue(n);
            }

            @Override
            public int size() {
                return this.this$0.size();
            }

            @Override
            public void clear() {
                this.this$0.clear();
            }

            @Override
            public IntIterator iterator() {
                return new IntIterator(this){
                    private final ObjectIterator<Short2IntMap.Entry> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Short2IntMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public int nextInt() {
                        return ((Short2IntMap.Entry)this.i.next()).getIntValue();
                    }

                    @Override
                    public boolean hasNext() {
                        return this.i.hasNext();
                    }
                };
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        };
    }

    @Override
    public void putAll(Map<? extends Short, ? extends Integer> map) {
        if (map instanceof Short2IntMap) {
            ObjectIterator<Short2IntMap.Entry> objectIterator = Short2IntMaps.fastIterator((Short2IntMap)map);
            while (objectIterator.hasNext()) {
                Short2IntMap.Entry entry = (Short2IntMap.Entry)objectIterator.next();
                this.put(entry.getShortKey(), entry.getIntValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<? extends Short, ? extends Integer>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<? extends Short, ? extends Integer> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator<Short2IntMap.Entry> objectIterator = Short2IntMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Short2IntMap.Entry)objectIterator.next()).hashCode();
        }
        return n;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof Map)) {
            return true;
        }
        Map map = (Map)object;
        if (map.size() != this.size()) {
            return true;
        }
        return this.short2IntEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator<Short2IntMap.Entry> objectIterator = Short2IntMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Short2IntMap.Entry entry = (Short2IntMap.Entry)objectIterator.next();
            stringBuilder.append(String.valueOf(entry.getShortKey()));
            stringBuilder.append("=>");
            stringBuilder.append(String.valueOf(entry.getIntValue()));
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public Collection values() {
        return this.values();
    }

    @Override
    public Set keySet() {
        return this.keySet();
    }

    public static abstract class BasicEntrySet
    extends AbstractObjectSet<Short2IntMap.Entry> {
        protected final Short2IntMap map;

        public BasicEntrySet(Short2IntMap short2IntMap) {
            this.map = short2IntMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Short2IntMap.Entry) {
                Short2IntMap.Entry entry = (Short2IntMap.Entry)object;
                short s = entry.getShortKey();
                return this.map.containsKey(s) && this.map.get(s) == entry.getIntValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Short)) {
                return true;
            }
            short s = (Short)k;
            Object v = entry.getValue();
            if (v == null || !(v instanceof Integer)) {
                return true;
            }
            return this.map.containsKey(s) && this.map.get(s) == ((Integer)v).intValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Short2IntMap.Entry) {
                Short2IntMap.Entry entry = (Short2IntMap.Entry)object;
                return this.map.remove(entry.getShortKey(), entry.getIntValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Short)) {
                return true;
            }
            short s = (Short)k;
            Object v = entry.getValue();
            if (v == null || !(v instanceof Integer)) {
                return true;
            }
            int n = (Integer)v;
            return this.map.remove(s, n);
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    public static class BasicEntry
    implements Short2IntMap.Entry {
        protected short key;
        protected int value;

        public BasicEntry() {
        }

        public BasicEntry(Short s, Integer n) {
            this.key = s;
            this.value = n;
        }

        public BasicEntry(short s, int n) {
            this.key = s;
            this.value = n;
        }

        @Override
        public short getShortKey() {
            return this.key;
        }

        @Override
        public int getIntValue() {
            return this.value;
        }

        @Override
        public int setValue(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Short2IntMap.Entry) {
                Short2IntMap.Entry entry = (Short2IntMap.Entry)object;
                return this.key == entry.getShortKey() && this.value == entry.getIntValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Short)) {
                return true;
            }
            Object v = entry.getValue();
            if (v == null || !(v instanceof Integer)) {
                return true;
            }
            return this.key == (Short)k && this.value == (Integer)v;
        }

        @Override
        public int hashCode() {
            return this.key ^ this.value;
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

