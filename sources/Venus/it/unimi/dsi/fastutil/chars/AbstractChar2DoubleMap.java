/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.chars.AbstractChar2DoubleFunction;
import it.unimi.dsi.fastutil.chars.AbstractCharSet;
import it.unimi.dsi.fastutil.chars.Char2DoubleMap;
import it.unimi.dsi.fastutil.chars.Char2DoubleMaps;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
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
public abstract class AbstractChar2DoubleMap
extends AbstractChar2DoubleFunction
implements Char2DoubleMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractChar2DoubleMap() {
    }

    @Override
    public boolean containsValue(double d) {
        return this.values().contains(d);
    }

    @Override
    public boolean containsKey(char c) {
        Iterator iterator2 = this.char2DoubleEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Char2DoubleMap.Entry)iterator2.next()).getCharKey() != c) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public CharSet keySet() {
        return new AbstractCharSet(this){
            final AbstractChar2DoubleMap this$0;
            {
                this.this$0 = abstractChar2DoubleMap;
            }

            @Override
            public boolean contains(char c) {
                return this.this$0.containsKey(c);
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
            public CharIterator iterator() {
                return new CharIterator(this){
                    private final ObjectIterator<Char2DoubleMap.Entry> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Char2DoubleMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public char nextChar() {
                        return ((Char2DoubleMap.Entry)this.i.next()).getCharKey();
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
            final AbstractChar2DoubleMap this$0;
            {
                this.this$0 = abstractChar2DoubleMap;
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
                    private final ObjectIterator<Char2DoubleMap.Entry> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Char2DoubleMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public double nextDouble() {
                        return ((Char2DoubleMap.Entry)this.i.next()).getDoubleValue();
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
    public void putAll(Map<? extends Character, ? extends Double> map) {
        if (map instanceof Char2DoubleMap) {
            ObjectIterator<Char2DoubleMap.Entry> objectIterator = Char2DoubleMaps.fastIterator((Char2DoubleMap)map);
            while (objectIterator.hasNext()) {
                Char2DoubleMap.Entry entry = (Char2DoubleMap.Entry)objectIterator.next();
                this.put(entry.getCharKey(), entry.getDoubleValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<? extends Character, ? extends Double>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<? extends Character, ? extends Double> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator<Char2DoubleMap.Entry> objectIterator = Char2DoubleMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Char2DoubleMap.Entry)objectIterator.next()).hashCode();
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
        return this.char2DoubleEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator<Char2DoubleMap.Entry> objectIterator = Char2DoubleMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Char2DoubleMap.Entry entry = (Char2DoubleMap.Entry)objectIterator.next();
            stringBuilder.append(String.valueOf(entry.getCharKey()));
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
    extends AbstractObjectSet<Char2DoubleMap.Entry> {
        protected final Char2DoubleMap map;

        public BasicEntrySet(Char2DoubleMap char2DoubleMap) {
            this.map = char2DoubleMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Char2DoubleMap.Entry) {
                Char2DoubleMap.Entry entry = (Char2DoubleMap.Entry)object;
                char c = entry.getCharKey();
                return this.map.containsKey(c) && Double.doubleToLongBits(this.map.get(c)) == Double.doubleToLongBits(entry.getDoubleValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Character)) {
                return true;
            }
            char c = ((Character)k).charValue();
            Object v = entry.getValue();
            if (v == null || !(v instanceof Double)) {
                return true;
            }
            return this.map.containsKey(c) && Double.doubleToLongBits(this.map.get(c)) == Double.doubleToLongBits((Double)v);
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Char2DoubleMap.Entry) {
                Char2DoubleMap.Entry entry = (Char2DoubleMap.Entry)object;
                return this.map.remove(entry.getCharKey(), entry.getDoubleValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Character)) {
                return true;
            }
            char c = ((Character)k).charValue();
            Object v = entry.getValue();
            if (v == null || !(v instanceof Double)) {
                return true;
            }
            double d = (Double)v;
            return this.map.remove(c, d);
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    public static class BasicEntry
    implements Char2DoubleMap.Entry {
        protected char key;
        protected double value;

        public BasicEntry() {
        }

        public BasicEntry(Character c, Double d) {
            this.key = c.charValue();
            this.value = d;
        }

        public BasicEntry(char c, double d) {
            this.key = c;
            this.value = d;
        }

        @Override
        public char getCharKey() {
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
            if (object instanceof Char2DoubleMap.Entry) {
                Char2DoubleMap.Entry entry = (Char2DoubleMap.Entry)object;
                return this.key == entry.getCharKey() && Double.doubleToLongBits(this.value) == Double.doubleToLongBits(entry.getDoubleValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Character)) {
                return true;
            }
            Object v = entry.getValue();
            if (v == null || !(v instanceof Double)) {
                return true;
            }
            return this.key == ((Character)k).charValue() && Double.doubleToLongBits(this.value) == Double.doubleToLongBits((Double)v);
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

