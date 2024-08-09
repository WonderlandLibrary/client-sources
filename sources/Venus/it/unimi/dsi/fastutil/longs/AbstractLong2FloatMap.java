/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.longs.AbstractLong2FloatFunction;
import it.unimi.dsi.fastutil.longs.AbstractLongSet;
import it.unimi.dsi.fastutil.longs.Long2FloatMap;
import it.unimi.dsi.fastutil.longs.Long2FloatMaps;
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
public abstract class AbstractLong2FloatMap
extends AbstractLong2FloatFunction
implements Long2FloatMap,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractLong2FloatMap() {
    }

    @Override
    public boolean containsValue(float f) {
        return this.values().contains(f);
    }

    @Override
    public boolean containsKey(long l) {
        Iterator iterator2 = this.long2FloatEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Long2FloatMap.Entry)iterator2.next()).getLongKey() != l) continue;
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
            final AbstractLong2FloatMap this$0;
            {
                this.this$0 = abstractLong2FloatMap;
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
                    private final ObjectIterator<Long2FloatMap.Entry> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Long2FloatMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public long nextLong() {
                        return ((Long2FloatMap.Entry)this.i.next()).getLongKey();
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
    public FloatCollection values() {
        return new AbstractFloatCollection(this){
            final AbstractLong2FloatMap this$0;
            {
                this.this$0 = abstractLong2FloatMap;
            }

            @Override
            public boolean contains(float f) {
                return this.this$0.containsValue(f);
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
            public FloatIterator iterator() {
                return new FloatIterator(this){
                    private final ObjectIterator<Long2FloatMap.Entry> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Long2FloatMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public float nextFloat() {
                        return ((Long2FloatMap.Entry)this.i.next()).getFloatValue();
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
    public void putAll(Map<? extends Long, ? extends Float> map) {
        if (map instanceof Long2FloatMap) {
            ObjectIterator<Long2FloatMap.Entry> objectIterator = Long2FloatMaps.fastIterator((Long2FloatMap)map);
            while (objectIterator.hasNext()) {
                Long2FloatMap.Entry entry = (Long2FloatMap.Entry)objectIterator.next();
                this.put(entry.getLongKey(), entry.getFloatValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<? extends Long, ? extends Float>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<? extends Long, ? extends Float> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator<Long2FloatMap.Entry> objectIterator = Long2FloatMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Long2FloatMap.Entry)objectIterator.next()).hashCode();
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
        return this.long2FloatEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator<Long2FloatMap.Entry> objectIterator = Long2FloatMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Long2FloatMap.Entry entry = (Long2FloatMap.Entry)objectIterator.next();
            stringBuilder.append(String.valueOf(entry.getLongKey()));
            stringBuilder.append("=>");
            stringBuilder.append(String.valueOf(entry.getFloatValue()));
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
    extends AbstractObjectSet<Long2FloatMap.Entry> {
        protected final Long2FloatMap map;

        public BasicEntrySet(Long2FloatMap long2FloatMap) {
            this.map = long2FloatMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Long2FloatMap.Entry) {
                Long2FloatMap.Entry entry = (Long2FloatMap.Entry)object;
                long l = entry.getLongKey();
                return this.map.containsKey(l) && Float.floatToIntBits(this.map.get(l)) == Float.floatToIntBits(entry.getFloatValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Long)) {
                return true;
            }
            long l = (Long)k;
            Object v = entry.getValue();
            if (v == null || !(v instanceof Float)) {
                return true;
            }
            return this.map.containsKey(l) && Float.floatToIntBits(this.map.get(l)) == Float.floatToIntBits(((Float)v).floatValue());
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Long2FloatMap.Entry) {
                Long2FloatMap.Entry entry = (Long2FloatMap.Entry)object;
                return this.map.remove(entry.getLongKey(), entry.getFloatValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Long)) {
                return true;
            }
            long l = (Long)k;
            Object v = entry.getValue();
            if (v == null || !(v instanceof Float)) {
                return true;
            }
            float f = ((Float)v).floatValue();
            return this.map.remove(l, f);
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    public static class BasicEntry
    implements Long2FloatMap.Entry {
        protected long key;
        protected float value;

        public BasicEntry() {
        }

        public BasicEntry(Long l, Float f) {
            this.key = l;
            this.value = f.floatValue();
        }

        public BasicEntry(long l, float f) {
            this.key = l;
            this.value = f;
        }

        @Override
        public long getLongKey() {
            return this.key;
        }

        @Override
        public float getFloatValue() {
            return this.value;
        }

        @Override
        public float setValue(float f) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Long2FloatMap.Entry) {
                Long2FloatMap.Entry entry = (Long2FloatMap.Entry)object;
                return this.key == entry.getLongKey() && Float.floatToIntBits(this.value) == Float.floatToIntBits(entry.getFloatValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Long)) {
                return true;
            }
            Object v = entry.getValue();
            if (v == null || !(v instanceof Float)) {
                return true;
            }
            return this.key == (Long)k && Float.floatToIntBits(this.value) == Float.floatToIntBits(((Float)v).floatValue());
        }

        @Override
        public int hashCode() {
            return HashCommon.long2int(this.key) ^ HashCommon.float2int(this.value);
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

