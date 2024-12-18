/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Size64;
import com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMaps;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
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
import java.util.function.IntConsumer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractInt2ObjectMap<V>
extends AbstractInt2ObjectFunction<V>
implements Int2ObjectMap<V>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractInt2ObjectMap() {
    }

    @Override
    public boolean containsKey(int n) {
        Iterator iterator2 = this.int2ObjectEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Int2ObjectMap.Entry)iterator2.next()).getIntKey() != n) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean containsValue(Object object) {
        Iterator iterator2 = this.int2ObjectEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Int2ObjectMap.Entry)iterator2.next()).getValue() != object) continue;
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
            final AbstractInt2ObjectMap this$0;
            {
                this.this$0 = abstractInt2ObjectMap;
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
                    private final ObjectIterator<Int2ObjectMap.Entry<V>> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Int2ObjectMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public int nextInt() {
                        return ((Int2ObjectMap.Entry)this.i.next()).getIntKey();
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

                    private static void lambda$forEachRemaining$0(IntConsumer intConsumer, Int2ObjectMap.Entry entry) {
                        intConsumer.accept(entry.getIntKey());
                    }
                };
            }

            @Override
            public IntSpliterator spliterator() {
                return IntSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(this.this$0), 321);
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
    public ObjectCollection<V> values() {
        return new AbstractObjectCollection<V>(this){
            final AbstractInt2ObjectMap this$0;
            {
                this.this$0 = abstractInt2ObjectMap;
            }

            @Override
            public boolean contains(Object object) {
                return this.this$0.containsValue(object);
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
            public ObjectIterator<V> iterator() {
                return new ObjectIterator<V>(this){
                    private final ObjectIterator<Int2ObjectMap.Entry<V>> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Int2ObjectMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public V next() {
                        return ((Int2ObjectMap.Entry)this.i.next()).getValue();
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
                    public void forEachRemaining(Consumer<? super V> consumer) {
                        this.i.forEachRemaining(arg_0 -> 1.lambda$forEachRemaining$0(consumer, arg_0));
                    }

                    private static void lambda$forEachRemaining$0(Consumer consumer, Int2ObjectMap.Entry entry) {
                        consumer.accept(entry.getValue());
                    }
                };
            }

            @Override
            public ObjectSpliterator<V> spliterator() {
                return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(this.this$0), 64);
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
    public void putAll(Map<? extends Integer, ? extends V> map) {
        if (map instanceof Int2ObjectMap) {
            ObjectIterator objectIterator = Int2ObjectMaps.fastIterator((Int2ObjectMap)map);
            while (objectIterator.hasNext()) {
                Int2ObjectMap.Entry entry = (Int2ObjectMap.Entry)objectIterator.next();
                this.put(entry.getIntKey(), entry.getValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<Integer, V>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<Integer, V> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator objectIterator = Int2ObjectMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Int2ObjectMap.Entry)objectIterator.next()).hashCode();
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
        return this.int2ObjectEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator objectIterator = Int2ObjectMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Int2ObjectMap.Entry entry = (Int2ObjectMap.Entry)objectIterator.next();
            stringBuilder.append(String.valueOf(entry.getIntKey()));
            stringBuilder.append("=>");
            if (this == entry.getValue()) {
                stringBuilder.append("(this map)");
                continue;
            }
            stringBuilder.append(String.valueOf(entry.getValue()));
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

    public static abstract class BasicEntrySet<V>
    extends AbstractObjectSet<Int2ObjectMap.Entry<V>> {
        protected final Int2ObjectMap<V> map;

        public BasicEntrySet(Int2ObjectMap<V> int2ObjectMap) {
            this.map = int2ObjectMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Int2ObjectMap.Entry) {
                Int2ObjectMap.Entry entry = (Int2ObjectMap.Entry)object;
                int n = entry.getIntKey();
                return this.map.containsKey(n) && Objects.equals(this.map.get(n), entry.getValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Integer)) {
                return true;
            }
            int n = (Integer)k;
            Object v = entry.getValue();
            return this.map.containsKey(n) && Objects.equals(this.map.get(n), v);
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Int2ObjectMap.Entry) {
                Int2ObjectMap.Entry entry = (Int2ObjectMap.Entry)object;
                return this.map.remove(entry.getIntKey(), entry.getValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Integer)) {
                return true;
            }
            int n = (Integer)k;
            Object v = entry.getValue();
            return this.map.remove(n, v);
        }

        @Override
        public int size() {
            return this.map.size();
        }

        @Override
        public ObjectSpliterator<Int2ObjectMap.Entry<V>> spliterator() {
            return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(this.map), 65);
        }

        @Override
        public Spliterator spliterator() {
            return this.spliterator();
        }
    }

    public static class BasicEntry<V>
    implements Int2ObjectMap.Entry<V> {
        protected int key;
        protected V value;

        public BasicEntry() {
        }

        public BasicEntry(Integer n, V v) {
            this.key = n;
            this.value = v;
        }

        public BasicEntry(int n, V v) {
            this.key = n;
            this.value = v;
        }

        @Override
        public int getIntKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Int2ObjectMap.Entry) {
                Int2ObjectMap.Entry entry = (Int2ObjectMap.Entry)object;
                return this.key == entry.getIntKey() && Objects.equals(this.value, entry.getValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Integer)) {
                return true;
            }
            Object v = entry.getValue();
            return this.key == (Integer)k && Objects.equals(this.value, v);
        }

        @Override
        public int hashCode() {
            return this.key ^ (this.value == null ? 0 : this.value.hashCode());
        }

        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}

