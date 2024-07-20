/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2ReferenceMap;
import it.unimi.dsi.fastutil.chars.CharComparator;
import it.unimi.dsi.fastutil.chars.CharSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.util.Map;
import java.util.SortedMap;

public interface Char2ReferenceSortedMap<V>
extends Char2ReferenceMap<V>,
SortedMap<Character, V> {
    @Override
    public ObjectSortedSet<Map.Entry<Character, V>> entrySet();

    @Override
    public ObjectSortedSet<Char2ReferenceMap.Entry<V>> char2ReferenceEntrySet();

    @Override
    public CharSortedSet keySet();

    @Override
    public ReferenceCollection<V> values();

    public CharComparator comparator();

    public Char2ReferenceSortedMap<V> subMap(Character var1, Character var2);

    public Char2ReferenceSortedMap<V> headMap(Character var1);

    public Char2ReferenceSortedMap<V> tailMap(Character var1);

    public Char2ReferenceSortedMap<V> subMap(char var1, char var2);

    public Char2ReferenceSortedMap<V> headMap(char var1);

    public Char2ReferenceSortedMap<V> tailMap(char var1);

    public char firstCharKey();

    public char lastCharKey();

    public static interface FastSortedEntrySet<V>
    extends ObjectSortedSet<Char2ReferenceMap.Entry<V>>,
    Char2ReferenceMap.FastEntrySet<V> {
        public ObjectBidirectionalIterator<Char2ReferenceMap.Entry<V>> fastIterator(Char2ReferenceMap.Entry<V> var1);
    }
}

