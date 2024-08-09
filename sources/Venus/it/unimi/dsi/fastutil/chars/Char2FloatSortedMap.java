/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2FloatMap;
import it.unimi.dsi.fastutil.chars.CharComparator;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.chars.CharSortedSet;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Char2FloatSortedMap
extends Char2FloatMap,
SortedMap<Character, Float> {
    public Char2FloatSortedMap subMap(char var1, char var2);

    public Char2FloatSortedMap headMap(char var1);

    public Char2FloatSortedMap tailMap(char var1);

    public char firstCharKey();

    public char lastCharKey();

    @Deprecated
    default public Char2FloatSortedMap subMap(Character c, Character c2) {
        return this.subMap(c.charValue(), c2.charValue());
    }

    @Deprecated
    default public Char2FloatSortedMap headMap(Character c) {
        return this.headMap(c.charValue());
    }

    @Deprecated
    default public Char2FloatSortedMap tailMap(Character c) {
        return this.tailMap(c.charValue());
    }

    @Override
    @Deprecated
    default public Character firstKey() {
        return Character.valueOf(this.firstCharKey());
    }

    @Override
    @Deprecated
    default public Character lastKey() {
        return Character.valueOf(this.lastCharKey());
    }

    @Override
    @Deprecated
    default public ObjectSortedSet<Map.Entry<Character, Float>> entrySet() {
        return this.char2FloatEntrySet();
    }

    public ObjectSortedSet<Char2FloatMap.Entry> char2FloatEntrySet();

    @Override
    public CharSortedSet keySet();

    @Override
    public FloatCollection values();

    public CharComparator comparator();

    @Override
    default public CharSet keySet() {
        return this.keySet();
    }

    @Override
    @Deprecated
    default public ObjectSet entrySet() {
        return this.entrySet();
    }

    default public ObjectSet char2FloatEntrySet() {
        return this.char2FloatEntrySet();
    }

    @Override
    @Deprecated
    default public Set entrySet() {
        return this.entrySet();
    }

    @Override
    default public Collection values() {
        return this.values();
    }

    @Override
    default public Set keySet() {
        return this.keySet();
    }

    @Override
    @Deprecated
    default public Object lastKey() {
        return this.lastKey();
    }

    @Override
    @Deprecated
    default public Object firstKey() {
        return this.firstKey();
    }

    @Override
    @Deprecated
    default public SortedMap tailMap(Object object) {
        return this.tailMap((Character)object);
    }

    @Override
    @Deprecated
    default public SortedMap headMap(Object object) {
        return this.headMap((Character)object);
    }

    @Override
    @Deprecated
    default public SortedMap subMap(Object object, Object object2) {
        return this.subMap((Character)object, (Character)object2);
    }

    @Override
    default public Comparator comparator() {
        return this.comparator();
    }

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Char2FloatMap.Entry>,
    Char2FloatMap.FastEntrySet {
        public ObjectBidirectionalIterator<Char2FloatMap.Entry> fastIterator();

        public ObjectBidirectionalIterator<Char2FloatMap.Entry> fastIterator(Char2FloatMap.Entry var1);

        default public ObjectIterator fastIterator() {
            return this.fastIterator();
        }
    }
}

