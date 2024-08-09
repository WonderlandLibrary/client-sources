/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.AbstractReference2BooleanFunction;
import it.unimi.dsi.fastutil.objects.AbstractReferenceSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Reference2BooleanMap;
import it.unimi.dsi.fastutil.objects.Reference2BooleanMaps;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractReference2BooleanMap<K>
extends AbstractReference2BooleanFunction<K>
implements Reference2BooleanMap<K>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractReference2BooleanMap() {
    }

    @Override
    public boolean containsValue(boolean bl) {
        return this.values().contains(bl);
    }

    @Override
    public boolean containsKey(Object object) {
        Iterator iterator2 = this.reference2BooleanEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Reference2BooleanMap.Entry)iterator2.next()).getKey() != object) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public ReferenceSet<K> keySet() {
        return new AbstractReferenceSet<K>(this){
            final AbstractReference2BooleanMap this$0;
            {
                this.this$0 = abstractReference2BooleanMap;
            }

            @Override
            public boolean contains(Object object) {
                return this.this$0.containsKey(object);
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
            public ObjectIterator<K> iterator() {
                return new ObjectIterator<K>(this){
                    private final ObjectIterator<Reference2BooleanMap.Entry<K>> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Reference2BooleanMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public K next() {
                        return ((Reference2BooleanMap.Entry)this.i.next()).getKey();
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
            final AbstractReference2BooleanMap this$0;
            {
                this.this$0 = abstractReference2BooleanMap;
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
                    private final ObjectIterator<Reference2BooleanMap.Entry<K>> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Reference2BooleanMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public boolean nextBoolean() {
                        return ((Reference2BooleanMap.Entry)this.i.next()).getBooleanValue();
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
    public void putAll(Map<? extends K, ? extends Boolean> map) {
        if (map instanceof Reference2BooleanMap) {
            ObjectIterator objectIterator = Reference2BooleanMaps.fastIterator((Reference2BooleanMap)map);
            while (objectIterator.hasNext()) {
                Reference2BooleanMap.Entry entry = (Reference2BooleanMap.Entry)objectIterator.next();
                this.put(entry.getKey(), entry.getBooleanValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<K, Boolean>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<K, Boolean> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator objectIterator = Reference2BooleanMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Reference2BooleanMap.Entry)objectIterator.next()).hashCode();
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
        return this.reference2BooleanEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator objectIterator = Reference2BooleanMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Reference2BooleanMap.Entry entry = (Reference2BooleanMap.Entry)objectIterator.next();
            if (this == entry.getKey()) {
                stringBuilder.append("(this map)");
            } else {
                stringBuilder.append(String.valueOf(entry.getKey()));
            }
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

    public static abstract class BasicEntrySet<K>
    extends AbstractObjectSet<Reference2BooleanMap.Entry<K>> {
        protected final Reference2BooleanMap<K> map;

        public BasicEntrySet(Reference2BooleanMap<K> reference2BooleanMap) {
            this.map = reference2BooleanMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Reference2BooleanMap.Entry) {
                Reference2BooleanMap.Entry entry = (Reference2BooleanMap.Entry)object;
                Object k = entry.getKey();
                return this.map.containsKey(k) && this.map.getBoolean(k) == entry.getBooleanValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            Object v = entry.getValue();
            if (v == null || !(v instanceof Boolean)) {
                return true;
            }
            return this.map.containsKey(k) && this.map.getBoolean(k) == ((Boolean)v).booleanValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Reference2BooleanMap.Entry) {
                Reference2BooleanMap.Entry entry = (Reference2BooleanMap.Entry)object;
                return this.map.remove(entry.getKey(), entry.getBooleanValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            Object v = entry.getValue();
            if (v == null || !(v instanceof Boolean)) {
                return true;
            }
            boolean bl = (Boolean)v;
            return this.map.remove(k, bl);
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    public static class BasicEntry<K>
    implements Reference2BooleanMap.Entry<K> {
        protected K key;
        protected boolean value;

        public BasicEntry() {
        }

        public BasicEntry(K k, Boolean bl) {
            this.key = k;
            this.value = bl;
        }

        public BasicEntry(K k, boolean bl) {
            this.key = k;
            this.value = bl;
        }

        @Override
        public K getKey() {
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
            if (object instanceof Reference2BooleanMap.Entry) {
                Reference2BooleanMap.Entry entry = (Reference2BooleanMap.Entry)object;
                return this.key == entry.getKey() && this.value == entry.getBooleanValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            Object v = entry.getValue();
            if (v == null || !(v instanceof Boolean)) {
                return true;
            }
            return this.key == k && this.value == (Boolean)v;
        }

        @Override
        public int hashCode() {
            return System.identityHashCode(this.key) ^ (this.value ? 1231 : 1237);
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

