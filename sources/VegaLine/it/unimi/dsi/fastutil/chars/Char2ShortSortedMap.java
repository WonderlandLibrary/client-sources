/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2ShortMap;
import it.unimi.dsi.fastutil.chars.CharComparator;
import it.unimi.dsi.fastutil.chars.CharSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import java.util.Map;
import java.util.SortedMap;

public interface Char2ShortSortedMap
extends Char2ShortMap,
SortedMap<Character, Short> {
    @Override
    public ObjectSortedSet<Map.Entry<Character, Short>> entrySet();

    public ObjectSortedSet<Char2ShortMap.Entry> char2ShortEntrySet();

    @Override
    public CharSortedSet keySet();

    @Override
    public ShortCollection values();

    public CharComparator comparator();

    public Char2ShortSortedMap subMap(Character var1, Character var2);

    public Char2ShortSortedMap headMap(Character var1);

    public Char2ShortSortedMap tailMap(Character var1);

    public Char2ShortSortedMap subMap(char var1, char var2);

    public Char2ShortSortedMap headMap(char var1);

    public Char2ShortSortedMap tailMap(char var1);

    public char firstCharKey();

    public char lastCharKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Char2ShortMap.Entry>,
    Char2ShortMap.FastEntrySet {
        public ObjectBidirectionalIterator<Char2ShortMap.Entry> fastIterator(Char2ShortMap.Entry var1);
    }
}

