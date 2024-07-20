/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2LongMap;
import it.unimi.dsi.fastutil.chars.CharComparator;
import it.unimi.dsi.fastutil.chars.CharSortedSet;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Char2LongSortedMap
extends Char2LongMap,
SortedMap<Character, Long> {
    @Override
    public ObjectSortedSet<Map.Entry<Character, Long>> entrySet();

    public ObjectSortedSet<Char2LongMap.Entry> char2LongEntrySet();

    @Override
    public CharSortedSet keySet();

    @Override
    public LongCollection values();

    public CharComparator comparator();

    public Char2LongSortedMap subMap(Character var1, Character var2);

    public Char2LongSortedMap headMap(Character var1);

    public Char2LongSortedMap tailMap(Character var1);

    public Char2LongSortedMap subMap(char var1, char var2);

    public Char2LongSortedMap headMap(char var1);

    public Char2LongSortedMap tailMap(char var1);

    public char firstCharKey();

    public char lastCharKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Char2LongMap.Entry>,
    Char2LongMap.FastEntrySet {
        public ObjectBidirectionalIterator<Char2LongMap.Entry> fastIterator(Char2LongMap.Entry var1);
    }
}

