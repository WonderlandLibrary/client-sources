/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.AbstractInt2ShortFunction;
import it.unimi.dsi.fastutil.ints.AbstractIntSet;
import it.unimi.dsi.fastutil.ints.Int2ShortMap;
import it.unimi.dsi.fastutil.ints.Int2ShortMaps;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractInt2ShortMap
extends AbstractInt2ShortFunction
implements Int2ShortMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractInt2ShortMap() {
    }

    @Override
    public boolean containsValue(short s) {
        return this.values().contains(s);
    }

    @Override
    public boolean containsKey(int n) {
        Iterator iterator2 = this.int2ShortEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Int2ShortMap.Entry)iterator2.next()).getIntKey() != n) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public IntSet keySet() {
        return new AbstractIntSet(this){
            final AbstractInt2ShortMap this$0;
            {
                this.this$0 = abstractInt2ShortMap;
            }

            @Override
            public boolean contains(int n) {
                return this.this$0.containsKey(n);
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
                    private final ObjectIterator<Int2ShortMap.Entry> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Int2ShortMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public int nextInt() {
                        return ((Int2ShortMap.Entry)this.i.next()).getIntKey();
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
    public ShortCollection values() {
        return new AbstractShortCollection(this){
            final AbstractInt2ShortMap this$0;
            {
                this.this$0 = abstractInt2ShortMap;
            }

            @Override
            public boolean contains(short s) {
                return this.this$0.containsValue(s);
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
                    private final ObjectIterator<Int2ShortMap.Entry> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Int2ShortMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public short nextShort() {
                        return ((Int2ShortMap.Entry)this.i.next()).getShortValue();
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
    public void putAll(Map<? extends Integer, ? extends Short> map) {
        if (map instanceof Int2ShortMap) {
            ObjectIterator<Int2ShortMap.Entry> objectIterator = Int2ShortMaps.fastIterator((Int2ShortMap)map);
            while (objectIterator.hasNext()) {
                Int2ShortMap.Entry entry = (Int2ShortMap.Entry)objectIterator.next();
                this.put(entry.getIntKey(), entry.getShortValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<? extends Integer, ? extends Short>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<? extends Integer, ? extends Short> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator<Int2ShortMap.Entry> objectIterator = Int2ShortMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Int2ShortMap.Entry)objectIterator.next()).hashCode();
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
        return this.int2ShortEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator<Int2ShortMap.Entry> objectIterator = Int2ShortMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Int2ShortMap.Entry entry = (Int2ShortMap.Entry)objectIterator.next();
            stringBuilder.append(String.valueOf(entry.getIntKey()));
            stringBuilder.append("=>");
            stringBuilder.append(String.valueOf(entry.getShortValue()));
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
    extends AbstractObjectSet<Int2ShortMap.Entry> {
        protected final Int2ShortMap map;

        public BasicEntrySet(Int2ShortMap int2ShortMap) {
            this.map = int2ShortMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Int2ShortMap.Entry) {
                Int2ShortMap.Entry entry = (Int2ShortMap.Entry)object;
                int n = entry.getIntKey();
                return this.map.containsKey(n) && this.map.get(n) == entry.getShortValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Integer)) {
                return true;
            }
            int n = (Integer)k;
            Object v = entry.getValue();
            if (v == null || !(v instanceof Short)) {
                return true;
            }
            return this.map.containsKey(n) && this.map.get(n) == ((Short)v).shortValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Int2ShortMap.Entry) {
                Int2ShortMap.Entry entry = (Int2ShortMap.Entry)object;
                return this.map.remove(entry.getIntKey(), entry.getShortValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Integer)) {
                return true;
            }
            int n = (Integer)k;
            Object v = entry.getValue();
            if (v == null || !(v instanceof Short)) {
                return true;
            }
            short s = (Short)v;
            return this.map.remove(n, s);
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    public static class BasicEntry
    implements Int2ShortMap.Entry {
        protected int key;
        protected short value;

        public BasicEntry() {
        }

        public BasicEntry(Integer n, Short s) {
            this.key = n;
            this.value = s;
        }

        public BasicEntry(int n, short s) {
            this.key = n;
            this.value = s;
        }

        @Override
        public int getIntKey() {
            return this.key;
        }

        @Override
        public short getShortValue() {
            return this.value;
        }

        @Override
        public short setValue(short s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Int2ShortMap.Entry) {
                Int2ShortMap.Entry entry = (Int2ShortMap.Entry)object;
                return this.key == entry.getIntKey() && this.value == entry.getShortValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Integer)) {
                return true;
            }
            Object v = entry.getValue();
            if (v == null || !(v instanceof Short)) {
                return true;
            }
            return this.key == (Integer)k && this.value == (Short)v;
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

