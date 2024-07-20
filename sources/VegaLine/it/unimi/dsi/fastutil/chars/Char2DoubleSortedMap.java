/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2DoubleMap;
import it.unimi.dsi.fastutil.chars.CharComparator;
import it.unimi.dsi.fastutil.chars.CharSortedSet;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Char2DoubleSortedMap
extends Char2DoubleMap,
SortedMap<Character, Double> {
    @Override
    public ObjectSortedSet<Map.Entry<Character, Double>> entrySet();

    public ObjectSortedSet<Char2DoubleMap.Entry> char2DoubleEntrySet();

    @Override
    public CharSortedSet keySet();

    @Override
    public DoubleCollection values();

    public CharComparator comparator();

    public Char2DoubleSortedMap subMap(Character var1, Character var2);

    public Char2DoubleSortedMap headMap(Character var1);

    public Char2DoubleSortedMap tailMap(Character var1);

    public Char2DoubleSortedMap subMap(char var1, char var2);

    public Char2DoubleSortedMap headMap(char var1);

    public Char2DoubleSortedMap tailMap(char var1);

    public char firstCharKey();

    public char lastCharKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Char2DoubleMap.Entry>,
    Char2DoubleMap.FastEntrySet {
        public ObjectBidirectionalIterator<Char2DoubleMap.Entry> fastIterator(Char2DoubleMap.Entry var1);
    }
}

