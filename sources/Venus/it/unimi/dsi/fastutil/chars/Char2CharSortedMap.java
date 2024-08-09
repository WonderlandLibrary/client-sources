/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2CharMap;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharComparator;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.chars.CharSortedSet;
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
public interface Char2CharSortedMap
extends Char2CharMap,
SortedMap<Character, Character> {
    public Char2CharSortedMap subMap(char var1, char var2);

    public Char2CharSortedMap headMap(char var1);

    public Char2CharSortedMap tailMap(char var1);

    public char firstCharKey();

    public char lastCharKey();

    @Deprecated
    default public Char2CharSortedMap subMap(Character c, Character c2) {
        return this.subMap(c.charValue(), c2.charValue());
    }

    @Deprecated
    default public Char2CharSortedMap headMap(Character c) {
        return this.headMap(c.charValue());
    }

    @Deprecated
    default public Char2CharSortedMap tailMap(Character c) {
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
    default public ObjectSortedSet<Map.Entry<Character, Character>> entrySet() {
        return this.char2CharEntrySet();
    }

    public ObjectSortedSet<Char2CharMap.Entry> char2CharEntrySet();

    @Override
    public CharSortedSet keySet();

    @Override
    public CharCollection values();

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

    default public ObjectSet char2CharEntrySet() {
        return this.char2CharEntrySet();
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
    extends ObjectSortedSet<Char2CharMap.Entry>,
    Char2CharMap.FastEntrySet {
        public ObjectBidirectionalIterator<Char2CharMap.Entry> fastIterator();

        public ObjectBidirectionalIterator<Char2CharMap.Entry> fastIterator(Char2CharMap.Entry var1);

        default public ObjectIterator fastIterator() {
            return this.fastIterator();
        }
    }
}

