/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.ints.AbstractInt2BooleanFunction;
import it.unimi.dsi.fastutil.ints.AbstractIntSet;
import it.unimi.dsi.fastutil.ints.Int2BooleanMap;
import it.unimi.dsi.fastutil.ints.Int2BooleanMaps;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractInt2BooleanMap
extends AbstractInt2BooleanFunction
implements Int2BooleanMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractInt2BooleanMap() {
    }

    @Override
    public boolean containsValue(boolean bl) {
        return this.values().contains(bl);
    }

    @Override
    public boolean containsKey(int n) {
        Iterator iterator2 = this.int2BooleanEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Int2BooleanMap.Entry)iterator2.next()).getIntKey() != n) continue;
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
            final AbstractInt2BooleanMap this$0;
            {
                this.this$0 = abstractInt2BooleanMap;
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
                    private final ObjectIterator<Int2BooleanMap.Entry> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Int2BooleanMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public int nextInt() {
                        return ((Int2BooleanMap.Entry)this.i.next()).getIntKey();
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
    public BooleanCollection values() {
        return new AbstractBooleanCollection(this){
            final AbstractInt2BooleanMap this$0;
            {
                this.this$0 = abstractInt2BooleanMap;
            }

            @Override
            public boolean contains(boolean bl) {
                return this.this$0.containsValue(bl);
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
            public BooleanIterator iterator() {
                return new BooleanIterator(this){
                    private final ObjectIterator<Int2BooleanMap.Entry> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Int2BooleanMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public boolean nextBoolean() {
                        return ((Int2BooleanMap.Entry)this.i.next()).getBooleanValue();
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
    public void putAll(Map<? extends Integer, ? extends Boolean> map) {
        if (map instanceof Int2BooleanMap) {
            ObjectIterator<Int2BooleanMap.Entry> objectIterator = Int2BooleanMaps.fastIterator((Int2BooleanMap)map);
            while (objectIterator.hasNext()) {
                Int2BooleanMap.Entry entry = (Int2BooleanMap.Entry)objectIterator.next();
                this.put(entry.getIntKey(), entry.getBooleanValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<? extends Integer, ? extends Boolean>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<? extends Integer, ? extends Boolean> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator<Int2BooleanMap.Entry> objectIterator = Int2BooleanMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Int2BooleanMap.Entry)objectIterator.next()).hashCode();
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
        return this.int2BooleanEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator<Int2BooleanMap.Entry> objectIterator = Int2BooleanMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Int2BooleanMap.Entry entry = (Int2BooleanMap.Entry)objectIterator.next();
            stringBuilder.append(String.valueOf(entry.getIntKey()));
            stringBuilder.append("=>");
            stringBuilder.append(String.valueOf(entry.getBooleanValue()));
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
    extends AbstractObjectSet<Int2BooleanMap.Entry> {
        protected final Int2BooleanMap map;

        public BasicEntrySet(Int2BooleanMap int2BooleanMap) {
            this.map = int2BooleanMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Int2BooleanMap.Entry) {
                Int2BooleanMap.Entry entry = (Int2BooleanMap.Entry)object;
                int n = entry.getIntKey();
                return this.map.containsKey(n) && this.map.get(n) == entry.getBooleanValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Integer)) {
                return true;
            }
            int n = (Integer)k;
            Object v = entry.getValue();
            if (v == null || !(v instanceof Boolean)) {
                return true;
            }
            return this.map.containsKey(n) && this.map.get(n) == ((Boolean)v).booleanValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Int2BooleanMap.Entry) {
                Int2BooleanMap.Entry entry = (Int2BooleanMap.Entry)object;
                return this.map.remove(entry.getIntKey(), entry.getBooleanValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Integer)) {
                return true;
            }
            int n = (Integer)k;
            Object v = entry.getValue();
            if (v == null || !(v instanceof Boolean)) {
                return true;
            }
            boolean bl = (Boolean)v;
            return this.map.remove(n, bl);
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    public static class BasicEntry
    implements Int2BooleanMap.Entry {
        protected int key;
        protected boolean value;

        public BasicEntry() {
        }

        public BasicEntry(Integer n, Boolean bl) {
            this.key = n;
            this.value = bl;
        }

        public BasicEntry(int n, boolean bl) {
            this.key = n;
            this.value = bl;
        }

        @Override
        public int getIntKey() {
            return this.key;
        }

        @Override
        public boolean getBooleanValue() {
            return this.value;
        }

        @Override
        public boolean setValue(boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Int2BooleanMap.Entry) {
                Int2BooleanMap.Entry entry = (Int2BooleanMap.Entry)object;
                return this.key == entry.getIntKey() && this.value == entry.getBooleanValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Integer)) {
                return true;
            }
            Object v = entry.getValue();
            if (v == null || !(v instanceof Boolean)) {
                return true;
            }
            return this.key == (Integer)k && this.value == (Boolean)v;
        }

        @Override
        public int hashCode() {
            return this.key ^ (this.value ? 1231 : 1237);
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

