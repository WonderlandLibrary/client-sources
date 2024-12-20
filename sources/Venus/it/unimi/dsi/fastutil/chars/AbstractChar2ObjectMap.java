/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractChar2ObjectFunction;
import it.unimi.dsi.fastutil.chars.AbstractCharSet;
import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectMaps;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractChar2ObjectMap<V>
extends AbstractChar2ObjectFunction<V>
implements Char2ObjectMap<V>,
Serializable {
    private static final long serialVersionUID = -4940583368468432370L;

    protected AbstractChar2ObjectMap() {
    }

    @Override
    public boolean containsValue(Object object) {
        return this.values().contains(object);
    }

    @Override
    public boolean containsKey(char c) {
        Iterator iterator2 = this.char2ObjectEntrySet().iterator();
        while (iterator2.hasNext()) {
            if (((Char2ObjectMap.Entry)iterator2.next()).getCharKey() != c) continue;
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
            final AbstractChar2ObjectMap this$0;
            {
                this.this$0 = abstractChar2ObjectMap;
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
                    private final ObjectIterator<Char2ObjectMap.Entry<V>> i;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Char2ObjectMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public char nextChar() {
                        return ((Char2ObjectMap.Entry)this.i.next()).getCharKey();
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
    public ObjectCollection<V> values() {
        return new AbstractObjectCollection<V>(this){
            final AbstractChar2ObjectMap this$0;
            {
                this.this$0 = abstractChar2ObjectMap;
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
                    private final ObjectIterator<Char2ObjectMap.Entry<V>> i;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.i = Char2ObjectMaps.fastIterator(this.this$1.this$0);
                    }

                    @Override
                    public V next() {
                        return ((Char2ObjectMap.Entry)this.i.next()).getValue();
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
    public void putAll(Map<? extends Character, ? extends V> map) {
        if (map instanceof Char2ObjectMap) {
            ObjectIterator objectIterator = Char2ObjectMaps.fastIterator((Char2ObjectMap)map);
            while (objectIterator.hasNext()) {
                Char2ObjectMap.Entry entry = (Char2ObjectMap.Entry)objectIterator.next();
                this.put(entry.getCharKey(), entry.getValue());
            }
        } else {
            int n = map.size();
            Iterator<Map.Entry<Character, V>> iterator2 = map.entrySet().iterator();
            while (n-- != 0) {
                Map.Entry<Character, V> entry = iterator2.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        ObjectIterator objectIterator = Char2ObjectMaps.fastIterator(this);
        while (n2-- != 0) {
            n += ((Char2ObjectMap.Entry)objectIterator.next()).hashCode();
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
        return this.char2ObjectEntrySet().containsAll(map.entrySet());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectIterator objectIterator = Char2ObjectMaps.fastIterator(this);
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Char2ObjectMap.Entry entry = (Char2ObjectMap.Entry)objectIterator.next();
            stringBuilder.append(String.valueOf(entry.getCharKey()));
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
    extends AbstractObjectSet<Char2ObjectMap.Entry<V>> {
        protected final Char2ObjectMap<V> map;

        public BasicEntrySet(Char2ObjectMap<V> char2ObjectMap) {
            this.map = char2ObjectMap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Char2ObjectMap.Entry) {
                Char2ObjectMap.Entry entry = (Char2ObjectMap.Entry)object;
                char c = entry.getCharKey();
                return this.map.containsKey(c) && Objects.equals(this.map.get(c), entry.getValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Character)) {
                return true;
            }
            char c = ((Character)k).charValue();
            Object v = entry.getValue();
            return this.map.containsKey(c) && Objects.equals(this.map.get(c), v);
        }

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Map.Entry)) {
                return true;
            }
            if (object instanceof Char2ObjectMap.Entry) {
                Char2ObjectMap.Entry entry = (Char2ObjectMap.Entry)object;
                return this.map.remove(entry.getCharKey(), entry.getValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Character)) {
                return true;
            }
            char c = ((Character)k).charValue();
            Object v = entry.getValue();
            return this.map.remove(c, v);
        }

        @Override
        public int size() {
            return this.map.size();
        }
    }

    public static class BasicEntry<V>
    implements Char2ObjectMap.Entry<V> {
        protected char key;
        protected V value;

        public BasicEntry() {
        }

        public BasicEntry(Character c, V v) {
            this.key = c.charValue();
            this.value = v;
        }

        public BasicEntry(char c, V v) {
            this.key = c;
            this.value = v;
        }

        @Override
        public char getCharKey() {
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
            if (object instanceof Char2ObjectMap.Entry) {
                Char2ObjectMap.Entry entry = (Char2ObjectMap.Entry)object;
                return this.key == entry.getCharKey() && Objects.equals(this.value, entry.getValue());
            }
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            if (k == null || !(k instanceof Character)) {
                return true;
            }
            Object v = entry.getValue();
            return this.key == ((Character)k).charValue() && Objects.equals(this.value, v);
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

