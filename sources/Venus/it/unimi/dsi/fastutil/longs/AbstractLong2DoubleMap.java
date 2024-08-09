/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.longs.AbstractLong2DoubleFunction;
import it.unimi.dsi.fastutil.longs.AbstractLongSet;
import it.unimi.dsi.fastutil.longs.Long2DoubleMap;
import it.unimi.dsi.fastutil.longs.Long2DoubleMaps;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongSet;
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
public abstract class AbstractLong2DoubleMap
extends AbstractLong2DoubleFunction
implements Long2DoubleMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractLong2DoubleMap() {
    }

    @Override
    public boolean containsValue(double d) {
        return this.values().contains(d);
    }

    @Override
    public boolean containsKey(long l) {
        Iterator iterator2 = this.long2DoubleEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Long2DoubleMap.Entry)iterator2.next()).getLongKey() != l) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public LongSet keySet() {
        return new AbstractLongSet(this){
            final AbstractLong2DoubleMap this$0;
            {
                this.this$0 = abstractLong2DoubleMap;
            }

            @Override
            public boolean contains(long l) {
                return this.this$0.containsKey(l);
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
            public LongIterator iterator() {
                return new LongIterator(this){
                    private final ObjectIterator<Long2DoubleMap.Entry> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Long2DoubleMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public long nextLong() {
                        return ((Long2DoubleMap.Entry)this.i.next()).getLongKey();
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
    public DoubleCollection values() {
        return new AbstractDoubleCollection(this){
            final AbstractLong2DoubleMap this$0;
            {
                this.this$0 = abstractLong2DoubleMap;
            }

            @Override
            public boolean contains(double d) {
                return this.this$0.containsValue(d);
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
            public DoubleIterator iterator() {
                return new DoubleIterator(this){
                    private final ObjectIterator<Long2DoubleMap.Entry> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Long2DoubleMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public double nextDouble() {
                        return ((Long2DoubleMap.Entry)this.i.next()).getDoubleValue();
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
    public void putAll(Map<? extends Long, ? extends Double> map) {
        if (map instanceof Long2DoubleMap) {
            ObjectIterator<Long2DoubleMap.Entry> objectIterator = Long2DoubleMaps.fastIterator((Long2DoubleMap)map);
            while (objectIterator.hasNext()) {
                Long2DoubleMap.Entry entry = (Long2DoubleMap.Entry)objectIterator.next();
                this.put(entry.getLongKey(), entry.getDoubleValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<? extends Long, ? extends Double>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<? extends Long, ? extends Double> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator<Long2DoubleMap.Entry> objectIterator = Long2DoubleMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Long2DoubleMap.Entry)objectIterator.next()).hashCode();
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
        return this.long2DoubleEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator<Long2DoubleMap.Entry> objectIterator = Long2DoubleMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Long2DoubleMap.Entry entry = (Long2DoubleMap.Entry)objectIterator.next();
            stringBuilder.append(String.valueOf(entry.getLongKey()));
            stringBuilder.append("=>");
            stringBuilder.append(String.valueOf(entry.getDoubleValue()));
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
    extends AbstractObjectSet<Long2DoubleMap.Entry> {
        protected final Long2DoubleMap map;

        public BasicEntrySet(Long2DoubleMap long2DoubleMap) {
            this.map = long2DoubleMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Long2DoubleMap.Entry) {
                Long2DoubleMap.Entry entry = (Long2DoubleMap.Entry)object;
                long l = entry.getLongKey();
                return this.map.containsKey(l) && Double.doubleToLongBits(this.map.get(l)) == Double.doubleToLongBits(entry.getDoubleValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Long)) {
                return true;
            }
            long l = (Long)k;
            Object v = entry.getValue();
            if (v == null || !(v instanceof Double)) {
                return true;
            }
            return this.map.containsKey(l) && Double.doubleToLongBits(this.map.get(l)) == Double.doubleToLongBits((Double)v);
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Long2DoubleMap.Entry) {
                Long2DoubleMap.Entry entry = (Long2DoubleMap.Entry)object;
                return this.map.remove(entry.getLongKey(), entry.getDoubleValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Long)) {
                return true;
            }
            long l = (Long)k;
            Object v = entry.getValue();
            if (v == null || !(v instanceof Double)) {
                return true;
            }
            double d = (Double)v;
            return this.map.remove(l, d);
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    public static class BasicEntry
    implements Long2DoubleMap.Entry {
        protected long key;
        protected double value;

        public BasicEntry() {
        }

        public BasicEntry(Long l, Double d) {
            this.key = l;
            this.value = d;
        }

        public BasicEntry(long l, double d) {
            this.key = l;
            this.value = d;
        }

        @Override
        public long getLongKey() {
            return this.key;
        }

        @Override
        public double getDoubleValue() {
            return this.value;
        }

        @Override
        public double setValue(double d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Long2DoubleMap.Entry) {
                Long2DoubleMap.Entry entry = (Long2DoubleMap.Entry)object;
                return this.key == entry.getLongKey() && Double.doubleToLongBits(this.value) == Double.doubleToLongBits(entry.getDoubleValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Long)) {
                return true;
            }
            Object v = entry.getValue();
            if (v == null || !(v instanceof Double)) {
                return true;
            }
            return this.key == (Long)k && Double.doubleToLongBits(this.value) == Double.doubleToLongBits((Double)v);
        }

        @Override
        public int hashCode() {
            return HashCommon.long2int(this.key) ^ HashCommon.double2int(this.value);
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

