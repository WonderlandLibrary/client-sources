/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShort2BooleanFunction;
import it.unimi.dsi.fastutil.shorts.AbstractShortSet;
import it.unimi.dsi.fastutil.shorts.Short2BooleanMap;
import it.unimi.dsi.fastutil.shorts.Short2BooleanMaps;
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
public abstract class AbstractShort2BooleanMap
extends AbstractShort2BooleanFunction
implements Short2BooleanMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractShort2BooleanMap() {
    }

    @Override
    public boolean containsValue(boolean bl) {
        return this.values().contains(bl);
    }

    @Override
    public boolean containsKey(short s) {
        Iterator iterator2 = this.short2BooleanEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Short2BooleanMap.Entry)iterator2.next()).getShortKey() != s) continue;
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
            final AbstractShort2BooleanMap this$0;
            {
                this.this$0 = abstractShort2BooleanMap;
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
                    private final ObjectIterator<Short2BooleanMap.Entry> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Short2BooleanMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public short nextShort() {
                        return ((Short2BooleanMap.Entry)this.i.next()).getShortKey();
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
            final AbstractShort2BooleanMap this$0;
            {
                this.this$0 = abstractShort2BooleanMap;
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
                    private final ObjectIterator<Short2BooleanMap.Entry> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Short2BooleanMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public boolean nextBoolean() {
                        return ((Short2BooleanMap.Entry)this.i.next()).getBooleanValue();
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
    public void putAll(Map<? extends Short, ? extends Boolean> map) {
        if (map instanceof Short2BooleanMap) {
            ObjectIterator<Short2BooleanMap.Entry> objectIterator = Short2BooleanMaps.fastIterator((Short2BooleanMap)map);
            while (objectIterator.hasNext()) {
                Short2BooleanMap.Entry entry = (Short2BooleanMap.Entry)objectIterator.next();
                this.put(entry.getShortKey(), entry.getBooleanValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<? extends Short, ? extends Boolean>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<? extends Short, ? extends Boolean> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator<Short2BooleanMap.Entry> objectIterator = Short2BooleanMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Short2BooleanMap.Entry)objectIterator.next()).hashCode();
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
        return this.short2BooleanEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator<Short2BooleanMap.Entry> objectIterator = Short2BooleanMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Short2BooleanMap.Entry entry = (Short2BooleanMap.Entry)objectIterator.next();
            stringBuilder.append(String.valueOf(entry.getShortKey()));
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
    extends AbstractObjectSet<Short2BooleanMap.Entry> {
        protected final Short2BooleanMap map;

        public BasicEntrySet(Short2BooleanMap short2BooleanMap) {
            this.map = short2BooleanMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Short2BooleanMap.Entry) {
                Short2BooleanMap.Entry entry = (Short2BooleanMap.Entry)object;
                short s = entry.getShortKey();
                return this.map.containsKey(s) && this.map.get(s) == entry.getBooleanValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Short)) {
                return true;
            }
            short s = (Short)k;
            Object v = entry.getValue();
            if (v == null || !(v instanceof Boolean)) {
                return true;
            }
            return this.map.containsKey(s) && this.map.get(s) == ((Boolean)v).booleanValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Short2BooleanMap.Entry) {
                Short2BooleanMap.Entry entry = (Short2BooleanMap.Entry)object;
                return this.map.remove(entry.getShortKey(), entry.getBooleanValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Short)) {
                return true;
            }
            short s = (Short)k;
            Object v = entry.getValue();
            if (v == null || !(v instanceof Boolean)) {
                return true;
            }
            boolean bl = (Boolean)v;
            return this.map.remove(s, bl);
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    public static class BasicEntry
    implements Short2BooleanMap.Entry {
        protected short key;
        protected boolean value;

        public BasicEntry() {
        }

        public BasicEntry(Short s, Boolean bl) {
            this.key = s;
            this.value = bl;
        }

        public BasicEntry(short s, boolean bl) {
            this.key = s;
            this.value = bl;
        }

        @Override
        public short getShortKey() {
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
            if (object instanceof Short2BooleanMap.Entry) {
                Short2BooleanMap.Entry entry = (Short2BooleanMap.Entry)object;
                return this.key == entry.getShortKey() && this.value == entry.getBooleanValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Short)) {
                return true;
            }
            Object v = entry.getValue();
            if (v == null || !(v instanceof Boolean)) {
                return true;
            }
            return this.key == (Short)k && this.value == (Boolean)v;
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

