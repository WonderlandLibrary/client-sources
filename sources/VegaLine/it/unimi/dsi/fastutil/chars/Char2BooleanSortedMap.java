/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.chars.Char2BooleanMap;
import it.unimi.dsi.fastutil.chars.CharComparator;
import it.unimi.dsi.fastutil.chars.CharSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Char2BooleanSortedMap
extends Char2BooleanMap,
SortedMap<Character, Boolean> {
    @Override
    public ObjectSortedSet<Map.Entry<Character, Boolean>> entrySet();

    public ObjectSortedSet<Char2BooleanMap.Entry> char2BooleanEntrySet();

    @Override
    public CharSortedSet keySet();

    @Override
    public BooleanCollection values();

    public CharComparator comparator();

    public Char2BooleanSortedMap subMap(Character var1, Character var2);

    public Char2BooleanSortedMap headMap(Character var1);

    public Char2BooleanSortedMap tailMap(Character var1);

    public Char2BooleanSortedMap subMap(char var1, char var2);

    public Char2BooleanSortedMap headMap(char var1);

    public Char2BooleanSortedMap tailMap(char var1);

    public char firstCharKey();

    public char lastCharKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Char2BooleanMap.Entry>,
    Char2BooleanMap.FastEntrySet {
        public ObjectBidirectionalIterator<Char2BooleanMap.Entry> fastIterator(Char2BooleanMap.Entry var1);
    }
}

