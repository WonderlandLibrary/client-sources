/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Size64;
import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObject2IntFunction;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMaps;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractObject2IntMap<K>
extends AbstractObject2IntFunction<K>
implements Object2IntMap<K>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractObject2IntMap() {
    }

    @Override
    public boolean containsKey(Object object) {
        Iterator iterator2 = this.object2IntEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Object2IntMap.Entry)iterator2.next()).getKey() != object) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean containsValue(int n) {
        Iterator iterator2 = this.object2IntEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Object2IntMap.Entry)iterator2.next()).getIntValue() != n) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public final int mergeInt(K k, int n, com.viaversion.viaversion.libs.fastutil.ints.IntBinaryOperator intBinaryOperator) {
        return this.mergeInt(k, n, (IntBinaryOperator)intBinaryOperator);
    }

    @Override
    public ObjectSet<K> keySet() {
        return new AbstractObjectSet<K>(this){
            final AbstractObject2IntMap this$0;
            {
                this.this$0 = abstractObject2IntMap;
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
                    private final ObjectIterator<Object2IntMap.Entry<K>> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Object2IntMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public K next() {
                        return ((Object2IntMap.Entry)this.i.next()).getKey();
                    }

                    @Override
                    public boolean hasNext() {
                        return this.i.hasNext();
                    }

                    @Override
                    public void remove() {
                        this.i.remove();
                    }

                    @Override
                    public void forEachRemaining(Consumer<? super K> consumer) {
                        this.i.forEachRemaining(arg_0 -> 1.lambda$forEachRemaining$0(consumer, arg_0));
                    }

                    private static void lambda$forEachRemaining$0(Consumer consumer, Object2IntMap.Entry entry) {
                        consumer.accept(entry.getKey());
                    }
                };
            }

            @Override
            public ObjectSpliterator<K> spliterator() {
                return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(this.this$0), 65);
            }

            @Override
            public Spliterator spliterator() {
                return this.spliterator();
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
            final AbstractObject2IntMap this$0;
            {
                this.this$0 = abstractObject2IntMap;
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
                    private final ObjectIterator<Object2IntMap.Entry<K>> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Object2IntMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public int nextInt() {
                        return ((Object2IntMap.Entry)this.i.next()).getIntValue();
                    }

                    @Override
                    public boolean hasNext() {
                        return this.i.hasNext();
                    }

                    @Override
                    public void remove() {
                        this.i.remove();
                    }

                    @Override
                    public void forEachRemaining(IntConsumer intConsumer) {
                        this.i.forEachRemaining(arg_0 -> 1.lambda$forEachRemaining$0(intConsumer, arg_0));
                    }

                    @Override
                    public void forEachRemaining(Object object) {
                        this.forEachRemaining((IntConsumer)object);
                    }

                    private static void lambda$forEachRemaining$0(IntConsumer intConsumer, Object2IntMap.Entry entry) {
                        intConsumer.accept(entry.getIntValue());
                    }
                };
            }

            @Override
            public IntSpliterator spliterator() {
                return IntSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(this.this$0), 320);
            }

            @Override
            public Spliterator spliterator() {
                return this.spliterator();
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        };
    }

    @Override
    public void putAll(Map<? extends K, ? extends Integer> map) {
        if (map instanceof Object2IntMap) {
            ObjectIterator objectIterator = Object2IntMaps.fastIterator((Object2IntMap)map);
            while (objectIterator.hasNext()) {
                Object2IntMap.Entry entry = (Object2IntMap.Entry)objectIterator.next();
                this.put(entry.getKey(), entry.getIntValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<K, Integer>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<K, Integer> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator objectIterator = Object2IntMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Object2IntMap.Entry)objectIterator.next()).hashCode();
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
        return this.object2IntEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator objectIterator = Object2IntMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Object2IntMap.Entry entry = (Object2IntMap.Entry)objectIterator.next();
            if (this == entry.getKey()) {
                stringBuilder.append("(this map)");
            } else {
                stringBuilder.append(String.valueOf(entry.getKey()));
            }
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

    public static abstract class BasicEntrySet<K>
    extends AbstractObjectSet<Object2IntMap.Entry<K>> {
        protected final Object2IntMap<K> map;

        public BasicEntrySet(Object2IntMap<K> object2IntMap) {
            this.map = object2IntMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Object2IntMap.Entry) {
                Object2IntMap.Entry entry = (Object2IntMap.Entry)object;
                Object k = entry.getKey();
                return this.map.containsKey(k) && this.map.getInt(k) == entry.getIntValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            Object v = entry.getValue();
            if (v == null || !(v instanceof Integer)) {
                return true;
            }
            return this.map.containsKey(k) && this.map.getInt(k) == ((Integer)v).intValue();
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Object2IntMap.Entry) {
                Object2IntMap.Entry entry = (Object2IntMap.Entry)object;
                return this.map.remove(entry.getKey(), entry.getIntValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            Object v = entry.getValue();
            if (v == null || !(v instanceof Integer)) {
                return true;
            }
            int n = (Integer)v;
            return this.map.remove(k, n);
        }

        @Override
        public int size() {
            return this.map.size();
        }

        @Override
        public ObjectSpliterator<Object2IntMap.Entry<K>> spliterator() {
            return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(this.map), 65);
        }

        @Override
        public Spliterator spliterator() {
            return this.spliterator();
        }
    }

    public static class BasicEntry<K>
    implements Object2IntMap.Entry<K> {
        protected K key;
        protected int value;

        public BasicEntry() {
        }

        public BasicEntry(K k, Integer n) {
            this.key = k;
            this.value = n;
        }

        public BasicEntry(K k, int n) {
            this.key = k;
            this.value = n;
        }

        @Override
        public K getKey() {
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
            if (object instanceof Object2IntMap.Entry) {
                Object2IntMap.Entry entry = (Object2IntMap.Entry)object;
                return Objects.equals(this.key, entry.getKey()) && this.value == entry.getIntValue();
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            Object v = entry.getValue();
            if (v == null || !(v instanceof Integer)) {
                return true;
            }
            return Objects.equals(this.key, k) && this.value == (Integer)v;
        }

        @Override
        public int hashCode() {
            return (this.key == null ? 0 : this.key.hashCode()) ^ this.value;
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

