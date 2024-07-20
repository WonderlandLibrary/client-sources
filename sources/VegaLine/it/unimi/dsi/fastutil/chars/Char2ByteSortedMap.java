/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.chars.Char2ByteMap;
import it.unimi.dsi.fastutil.chars.CharComparator;
import it.unimi.dsi.fastutil.chars.CharSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Char2ByteSortedMap
extends Char2ByteMap,
SortedMap<Character, Byte> {
    @Override
    public ObjectSortedSet<Map.Entry<Character, Byte>> entrySet();

    public ObjectSortedSet<Char2ByteMap.Entry> char2ByteEntrySet();

    @Override
    public CharSortedSet keySet();

    @Override
    public ByteCollection values();

    public CharComparator comparator();

    public Char2ByteSortedMap subMap(Character var1, Character var2);

    public Char2ByteSortedMap headMap(Character var1);

    public Char2ByteSortedMap tailMap(Character var1);

    public Char2ByteSortedMap subMap(char var1, char var2);

    public Char2ByteSortedMap headMap(char var1);

    public Char2ByteSortedMap tailMap(char var1);

    public char firstCharKey();

    public char lastCharKey();

    public static interface FastSortedEntrySet
    extends ObjectSortedSet<Char2ByteMap.Entry>,
    Char2ByteMap.FastEntrySet {
        public ObjectBidirectionalIterator<Char2ByteMap.Entry> fastIterator(Char2ByteMap.Entry var1);
    }
}

