/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.chars.Char2BooleanMap;
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
public interface Char2BooleanSortedMap
extends Char2BooleanMap,
SortedMap<Character, Boolean> {
    public Char2BooleanSortedMap subMap(char var1, char var2);

    public Char2BooleanSortedMap headMap(char var1);

    public Char2BooleanSortedMap tailMap(char var1);

    public char firstCharKey();

    public char lastCharKey();

    @Deprecated
    default public Char2BooleanSortedMap subMap(Character c, Character c2) {
        return this.subMap(c.charValue(), c2.charValue());
    }

    @Deprecated
    default public Char2BooleanSortedMap headMap(Character c) {
        return this.headMap(c.charValue());
    }

    @Deprecated
    default public Char2BooleanSortedMap tailMap(Character c) {
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
    default public ObjectSortedSet<Map.Entry<Character, Boolean>> entrySet() {
        return this.char2BooleanEntrySet();
    }

    public ObjectSortedSet<Char2BooleanMap.Entry> char2BooleanEntrySet();

    @Override
    public CharSortedSet keySet();

    @Override
    public BooleanCollection values();

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

    default public ObjectSet char2BooleanEntrySet() {
        return this.char2BooleanEntrySet();
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
    extends ObjectSortedSet<Char2BooleanMap.Entry>,
    Char2BooleanMap.FastEntrySet {
        public ObjectBidirectionalIterator<Char2BooleanMap.Entry> fastIterator();

        public ObjectBidirectionalIterator<Char2BooleanMap.Entry> fastIterator(Char2BooleanMap.Entry var1);

        default public ObjectIterator fastIterator() {
            return this.fastIterator();
        }
    }
}

