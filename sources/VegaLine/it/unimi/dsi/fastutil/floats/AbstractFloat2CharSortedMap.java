/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.AbstractCharIterator;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.floats.AbstractFloat2CharMap;
import it.unimi.dsi.fastutil.floats.AbstractFloatBidirectionalIterator;
import it.unimi.dsi.fastutil.floats.AbstractFloatSortedSet;
import it.unimi.dsi.fastutil.floats.Float2CharSortedMap;
import it.unimi.dsi.fastutil.floats.FloatBidirectionalIterator;
import it.unimi.dsi.fastutil.floats.FloatComparator;
import it.unimi.dsi.fastutil.floats.FloatSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;

public abstract class AbstractFloat2CharSortedMap
extends AbstractFloat2CharMap
implements Float2CharSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractFloat2CharSortedMap() {
    }

    @Override
    @Deprecated
    public Float2CharSortedMap headMap(Float to) {
        return this.headMap(to.floatValue());
    }

    @Override
    @Deprecated
    public Float2CharSortedMap tailMap(Float from) {
        return this.tailMap(from.floatValue());
    }

    @Override
    @Deprecated
    public Float2CharSortedMap subMap(Float from, Float to) {
        return this.subMap(from.floatValue(), to.floatValue());
    }

    @Override
    @Deprecated
    public Float firstKey() {
        return Float.valueOf(this.firstFloatKey());
    }

    @Override
    @Deprecated
    public Float lastKey() {
        return Float.valueOf(this.lastFloatKey());
    }

    @Override
    public FloatSortedSet keySet() {
        return new KeySet();
    }

    @Override
    public CharCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Float, Character>> entrySet() {
        return this.float2CharEntrySet();
    }

    protected static class ValuesIterator
    extends AbstractCharIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Float, Character>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Float, Character>> i) {
            this.i = i;
        }

        @Override
        public char nextChar() {
            return ((Character)((Map.Entry)this.i.next()).getValue()).charValue();
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }
    }

    protected class ValuesCollection
    extends AbstractCharCollection {
        protected ValuesCollection() {
        }

        @Override
        public CharIterator iterator() {
            return new ValuesIterator(AbstractFloat2CharSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(char k) {
            return AbstractFloat2CharSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractFloat2CharSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractFloat2CharSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator
    extends AbstractFloatBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Float, Character>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Float, Character>> i) {
            this.i = i;
        }

        @Override
        public float nextFloat() {
            return ((Float)((Map.Entry)this.i.next()).getKey()).floatValue();
        }

        @Override
        public float previousFloat() {
            return ((Float)((Map.Entry)this.i.previous()).getKey()).floatValue();
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }

        @Override
        public boolean hasPrevious() {
            return this.i.hasPrevious();
        }
    }

    protected class KeySet
    extends AbstractFloatSortedSet {
        protected KeySet() {
        }

        @Override
        public boolean contains(float k) {
            return AbstractFloat2CharSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractFloat2CharSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractFloat2CharSortedMap.this.clear();
        }

        @Override
        public FloatComparator comparator() {
            return AbstractFloat2CharSortedMap.this.comparator();
        }

        @Override
        public float firstFloat() {
            return AbstractFloat2CharSortedMap.this.firstFloatKey();
        }

        @Override
        public float lastFloat() {
            return AbstractFloat2CharSortedMap.this.lastFloatKey();
        }

        @Override
        public FloatSortedSet headSet(float to) {
            return AbstractFloat2CharSortedMap.this.headMap(to).keySet();
        }

        @Override
        public FloatSortedSet tailSet(float from) {
            return AbstractFloat2CharSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public FloatSortedSet subSet(float from, float to) {
            return AbstractFloat2CharSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public FloatBidirectionalIterator iterator(float from) {
            return new KeySetIterator(AbstractFloat2CharSortedMap.this.entrySet().iterator(new AbstractFloat2CharMap.BasicEntry(from, '\u0000')));
        }

        @Override
        public FloatBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractFloat2CharSortedMap.this.entrySet().iterator());
        }
    }
}

