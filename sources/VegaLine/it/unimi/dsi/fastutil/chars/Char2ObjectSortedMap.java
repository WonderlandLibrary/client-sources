/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import it.unimi.dsi.fastutil.chars.CharComparator;
import it.unimi.dsi.fastutil.chars.CharSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.SortedMap;

public interface Char2ObjectSortedMap<V>
extends Char2ObjectMap<V>,
SortedMap<Character, V> {
    @Override
    public ObjectSortedSet<Map.Entry<Character, V>> entrySet();

    @Override
    public ObjectSortedSet<Char2ObjectMap.Entry<V>> char2ObjectEntrySet();

    @Override
    public CharSortedSet keySet();

    @Override
    public ObjectCollection<V> values();

    public CharComparator comparator();

    public Char2ObjectSortedMap<V> subMap(Character var1, Character var2);

    public Char2ObjectSortedMap<V> headMap(Character var1);

    public Char2ObjectSortedMap<V> tailMap(Character var1);

    public Char2ObjectSortedMap<V> subMap(char var1, char var2);

    public Char2ObjectSortedMap<V> headMap(char var1);

    public Char2ObjectSortedMap<V> tailMap(char var1);

    public char firstCharKey();

    public char lastCharKey();

    public static interface FastSortedEntrySet<V>
    extends ObjectSortedSet<Char2ObjectMap.Entry<V>>,
    Char2ObjectMap.FastEntrySet<V> {
        public ObjectBidirectionalIterator<Char2ObjectMap.Entry<V>> fastIterator(Char2ObjectMap.Entry<V> var1);
    }
}

