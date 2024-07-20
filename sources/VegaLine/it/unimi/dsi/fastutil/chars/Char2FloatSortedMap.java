/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2FloatMap;
import it.unimi.dsi.fastutil.chars.CharComparator;
import it.unimi.dsi.fastutil.chars.CharSortedSet;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Char2FloatSortedMap
extends Char2FloatMap,
SortedMap<Character, Float> {
    @Override
    public ObjectSortedSet<Map.Entry<Character, Float>> entrySet();

    public ObjectSortedSet<Char2FloatMap.Entry> char2FloatEntrySet();

    @Override
    public CharSortedSet keySet();

    @Override
    public FloatCollection values();

    public CharComparator comparator();

    public Char2FloatSortedMap subMap(Character var1, Character var2);

    public Char2FloatSortedMap headMap(Character var1);

    public Char2FloatSortedMap tailMap(Character var1);

    public Char2FloatSortedMap subMap(char var1, char var2);

    public Char2FloatSortedMap headMap(char var1);

    public Char2FloatSortedMap tailMap(char var1);

    public char firstCharKey();

    public char lastCharKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Char2FloatMap.Entry>,
    Char2FloatMap.FastEntrySet {
        public ObjectBidirectionalIterator<Char2FloatMap.Entry> fastIterator(Char2FloatMap.Entry var1);
    }
}

