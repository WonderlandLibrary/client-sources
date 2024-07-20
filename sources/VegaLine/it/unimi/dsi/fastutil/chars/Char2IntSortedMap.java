/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2IntMap;
import it.unimi.dsi.fastutil.chars.CharComparator;
import it.unimi.dsi.fastutil.chars.CharSortedSet;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Char2IntSortedMap
extends Char2IntMap,
SortedMap<Character, Integer> {
    @Override
    public ObjectSortedSet<Map.Entry<Character, Integer>> entrySet();

    public ObjectSortedSet<Char2IntMap.Entry> char2IntEntrySet();

    @Override
    public CharSortedSet keySet();

    @Override
    public IntCollection values();

    public CharComparator comparator();

    public Char2IntSortedMap subMap(Character var1, Character var2);

    public Char2IntSortedMap headMap(Character var1);

    public Char2IntSortedMap tailMap(Character var1);

    public Char2IntSortedMap subMap(char var1, char var2);

    public Char2IntSortedMap headMap(char var1);

    public Char2IntSortedMap tailMap(char var1);

    public char firstCharKey();

    public char lastCharKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Char2IntMap.Entry>,
    Char2IntMap.FastEntrySet {
        public ObjectBidirectionalIterator<Char2IntMap.Entry> fastIterator(Char2IntMap.Entry var1);
    }
}

