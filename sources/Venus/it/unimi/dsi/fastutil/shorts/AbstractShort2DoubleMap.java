/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShort2DoubleFunction;
import it.unimi.dsi.fastutil.shorts.AbstractShortSet;
import it.unimi.dsi.fastutil.shorts.Short2DoubleMap;
import it.unimi.dsi.fastutil.shorts.Short2DoubleMaps;
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
public abstract class AbstractShort2DoubleMap
extends AbstractShort2DoubleFunction
implements Short2DoubleMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractShort2DoubleMap() {
    }

    @Override
    public boolean containsValue(double d) {
        return this.values().contains(d);
    }

    @Override
    public boolean containsKey(short s) {
        Iterator iterator2 = this.short2DoubleEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Short2DoubleMap.Entry)iterator2.next()).getShortKey() != s) continue;
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
            final AbstractShort2DoubleMap this$0;
            {
                this.this$0 = abstractShort2DoubleMap;
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
                    private final ObjectIterator<Short2DoubleMap.Entry> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Short2DoubleMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public short nextShort() {
                        return ((Short2DoubleMap.Entry)this.i.next()).getShortKey();
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
            final AbstractShort2DoubleMap this$0;
            {
                this.this$0 = abstractShort2DoubleMap;
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
                    private final ObjectIterator<Short2DoubleMap.Entry> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Short2DoubleMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public double nextDouble() {
                        return ((Short2DoubleMap.Entry)this.i.next()).getDoubleValue();
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
    public void putAll(Map<? extends Short, ? extends Double> map) {
        if (map instanceof Short2DoubleMap) {
            ObjectIterator<Short2DoubleMap.Entry> objectIterator = Short2DoubleMaps.fastIterator((Short2DoubleMap)map);
            while (objectIterator.hasNext()) {
                Short2DoubleMap.Entry entry = (Short2DoubleMap.Entry)objectIterator.next();
                this.put(entry.getShortKey(), entry.getDoubleValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<? extends Short, ? extends Double>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<? extends Short, ? extends Double> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator<Short2DoubleMap.Entry> objectIterator = Short2DoubleMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Short2DoubleMap.Entry)objectIterator.next()).hashCode();
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
        return this.short2DoubleEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator<Short2DoubleMap.Entry> objectIterator = Short2DoubleMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Short2DoubleMap.Entry entry = (Short2DoubleMap.Entry)objectIterator.next();
            stringBuilder.append(String.valueOf(entry.getShortKey()));
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
    extends AbstractObjectSet<Short2DoubleMap.Entry> {
        protected final Short2DoubleMap map;

        public BasicEntrySet(Short2DoubleMap short2DoubleMap) {
            this.map = short2DoubleMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Short2DoubleMap.Entry) {
                Short2DoubleMap.Entry entry = (Short2DoubleMap.Entry)object;
                short s = entry.getShortKey();
                return this.map.containsKey(s) && Double.doubleToLongBits(this.map.get(s)) == Double.doubleToLongBits(entry.getDoubleValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Short)) {
                return true;
            }
            short s = (Short)k;
            Object v = entry.getValue();
            if (v == null || !(v instanceof Double)) {
                return true;
            }
            return this.map.containsKey(s) && Double.doubleToLongBits(this.map.get(s)) == Double.doubleToLongBits((Double)v);
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Short2DoubleMap.Entry) {
                Short2DoubleMap.Entry entry = (Short2DoubleMap.Entry)object;
                return this.map.remove(entry.getShortKey(), entry.getDoubleValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Short)) {
                return true;
            }
            short s = (Short)k;
            Object v = entry.getValue();
            if (v == null || !(v instanceof Double)) {
                return true;
            }
            double d = (Double)v;
            return this.map.remove(s, d);
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    public static class BasicEntry
    implements Short2DoubleMap.Entry {
        protected short key;
        protected double value;

        public BasicEntry() {
        }

        public BasicEntry(Short s, Double d) {
            this.key = s;
            this.value = d;
        }

        public BasicEntry(short s, double d) {
            this.key = s;
            this.value = d;
        }

        @Override
        public short getShortKey() {
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
            if (object instanceof Short2DoubleMap.Entry) {
                Short2DoubleMap.Entry entry = (Short2DoubleMap.Entry)object;
                return this.key == entry.getShortKey() && Double.doubleToLongBits(this.value) == Double.doubleToLongBits(entry.getDoubleValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Short)) {
                return true;
            }
            Object v = entry.getValue();
            if (v == null || !(v instanceof Double)) {
                return true;
            }
            return this.key == (Short)k && Double.doubleToLongBits(this.value) == Double.doubleToLongBits((Double)v);
        }

        @Override
        public int hashCode() {
            return this.key ^ HashCommon.double2int(this.value);
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

