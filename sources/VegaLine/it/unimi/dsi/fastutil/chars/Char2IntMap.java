/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2IntFunction;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;

public interface Char2IntMap
extends Char2IntFunction,
Map<Character, Integer> {
    @Override
    public ObjectSet<Map.Entry<Character, Integer>> entrySet();

    public ObjectSet<Entry> char2IntEntrySet();

    public CharSet keySet();

    public IntCollection values();

    public boolean containsValue(int var1);

    public static interface Entry
    extends Map.Entry<Character, Integer> {
        @Override
        @Deprecated
        public Character getKey();

        public char getCharKey();

        @Override
        @Deprecated
        public Integer getValue();

        @Override
        public int setValue(int var1);

        public int getIntValue();
    }

    public static interface FastEntrySet
    extends ObjectSet<Entry> {
        public ObjectIterator<Entry> fastIterator();
    }
}

