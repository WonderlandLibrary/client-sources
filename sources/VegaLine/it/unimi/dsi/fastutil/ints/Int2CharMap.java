/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.ints.Int2CharFunction;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;

public interface Int2CharMap
extends Int2CharFunction,
Map<Integer, Character> {
    @Override
    public ObjectSet<Map.Entry<Integer, Character>> entrySet();

    public ObjectSet<Entry> int2CharEntrySet();

    public IntSet keySet();

    public CharCollection values();

    public boolean containsValue(char var1);

    public static interface Entry
    extends Map.Entry<Integer, Character> {
        @Override
        @Deprecated
        public Integer getKey();

        public int getIntKey();

        @Override
        @Deprecated
        public Character getValue();

        @Override
        public char setValue(char var1);

        public char getCharValue();
    }

    public static interface FastEntrySet
    extends ObjectSet<Entry> {
        public ObjectIterator<Entry> fastIterator();
    }
}

