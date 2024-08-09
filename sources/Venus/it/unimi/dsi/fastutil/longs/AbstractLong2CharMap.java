/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.longs.AbstractLong2CharFunction;
import it.unimi.dsi.fastutil.longs.AbstractLongSet;
import it.unimi.dsi.fastutil.longs.Long2CharMap;
import it.unimi.dsi.fastutil.longs.Long2CharMaps;
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
public abstract class AbstractLong2CharMap
extends AbstractLong2CharFunction
implements Long2CharMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractLong2CharMap() {
    }

    @Override
    public boolean containsValue(char c) {
        return this.values().contains(c);
    }

    @Override
    public boolean containsKey(long l) {
        Iterator iterator2 = this.long2CharEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Long2CharMap.Entry)iterator2.next()).getLongKey() != l) continue;
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
            final AbstractLong2CharMap this$0;
            {
                this.this$0 = abstractLong2CharMap;
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
                    private final ObjectIterator<Long2CharMap.Entry> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Long2CharMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public long nextLong() {
                        return ((Long2CharMap.Entry)this.i.next()).getLongKey();
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
    public CharCollection values() {
        return new AbstractCharCollection(this){
            final AbstractLong2CharMap this$0;
            {
                this.this$0 = abstractLong2CharMap;
            }

            @Override
            public boolean contains(char c) {
                return this.this$0.containsValue(c);
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
                    private final ObjectIterator<Long2CharMap.Entry> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Long2CharMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public char nextChar() {
                        return ((Long2CharMap.Entry)this.i.next()).getCharValue();
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
    public void putAll(Map<? extends Long, ? extends Character> map) {
        if (map instanceof Long2CharMap) {
            ObjectIterator<Long2CharMap.Entry> objectIterator = Long2CharMaps.fastIterator((Long2CharMap)map);
            while (objectIterator.hasNext()) {
                Long2CharMap.Entry entry = (Long2CharMap.Entry)objectIterator.next();
                this.put(entry.getLongKey(), entry.getCharValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<? extends Long, ? extends Character>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<? extends Long, ? extends Character> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator<Long2CharMap.Entry> objectIterator = Long2CharMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Long2CharMap.Entry)objectIterator.next()).hashCode();
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
        return this.long2CharEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator<Long2CharMap.Entry> objectIterator = Long2CharMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Long2CharMap.Entry entry = (Long2CharMap.Entry)objectIterator.next();
            stringBuilder.append(String.valueOf(entry.getLongKey()));
            stringBuilder.append("=>");
            stringBuilder.append(String.valueOf(entry.getCharValue()));
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
    extends AbstractObjectSet<Long2CharMap.Entry> {
        protected final Long2CharMap map;

        public BasicEntrySet(Long2CharMap long2CharMap) {
            this.map = long2CharMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Long2CharMap.Entry) {
                Long2CharMap.Entry entry = (Long2CharMap.Entry)object;
                long l = entry.getLongKey();
                return this.map.containsKey(l) && this.map.get(l) == entry.getCharValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Long)) {
                return true;
            }
            long l = (Long)k;
            Object v = entry.getValue();
            if (v == null || !(v instanceof Character)) {
                return true;
            }
            return this.map.containsKey(l) && this.map.get(l) == ((Character)v).charValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Long2CharMap.Entry) {
                Long2CharMap.Entry entry = (Long2CharMap.Entry)object;
                return this.map.remove(entry.getLongKey(), entry.getCharValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Long)) {
                return true;
            }
            long l = (Long)k;
            Object v = entry.getValue();
            if (v == null || !(v instanceof Character)) {
                return true;
            }
            char c = ((Character)v).charValue();
            return this.map.remove(l, c);
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    public static class BasicEntry
    implements Long2CharMap.Entry {
        protected long key;
        protected char value;

        public BasicEntry() {
        }

        public BasicEntry(Long l, Character c) {
            this.key = l;
            this.value = c.charValue();
        }

        public BasicEntry(long l, char c) {
            this.key = l;
            this.value = c;
        }

        @Override
        public long getLongKey() {
            return this.key;
        }

        @Override
        public char getCharValue() {
            return this.value;
        }

        @Override
        public char setValue(char c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Long2CharMap.Entry) {
                Long2CharMap.Entry entry = (Long2CharMap.Entry)object;
                return this.key == entry.getLongKey() && this.value == entry.getCharValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Long)) {
                return true;
            }
            Object v = entry.getValue();
            if (v == null || !(v instanceof Character)) {
                return true;
            }
            return this.key == (Long)k && this.value == ((Character)v).charValue();
        }

        @Override
        public int hashCode() {
            return HashCommon.long2int(this.key) ^ this.value;
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

