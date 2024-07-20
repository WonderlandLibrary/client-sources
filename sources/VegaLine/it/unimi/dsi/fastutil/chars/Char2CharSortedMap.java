/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2CharMap;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharComparator;
import it.unimi.dsi.fastutil.chars.CharSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Char2CharSortedMap
extends Char2CharMap,
SortedMap<Character, Character> {
    @Override
    public ObjectSortedSet<Map.Entry<Character, Character>> entrySet();

    public ObjectSortedSet<Char2CharMap.Entry> char2CharEntrySet();

    @Override
    public CharSortedSet keySet();

    @Override
    public CharCollection values();

    public CharComparator comparator();

    public Char2CharSortedMap subMap(Character var1, Character var2);

    public Char2CharSortedMap headMap(Character var1);

    public Char2CharSortedMap tailMap(Character var1);

    public Char2CharSortedMap subMap(char var1, char var2);

    public Char2CharSortedMap headMap(char var1);

    public Char2CharSortedMap tailMap(char var1);

    public char firstCharKey();

    public char lastCharKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Char2CharMap.Entry>,
    Char2CharMap.FastEntrySet {
        public ObjectBidirectionalIterator<Char2CharMap.Entry> fastIterator(Char2CharMap.Entry var1);
    }
}

