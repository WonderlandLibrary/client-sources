/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2LongFunction;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;

public interface Char2LongMap
extends Char2LongFunction,
Map<Character, Long> {
    @Override
    public ObjectSet<Map.Entry<Character, Long>> entrySet();

    public ObjectSet<Entry> char2LongEntrySet();

    public CharSet keySet();

    public LongCollection values();

    public boolean containsValue(long var1);

    public static interface Entry
    extends Map.Entry<Character, Long> {
        @Override
        @Deprecated
        public Character getKey();

        public char getCharKey();

        @Override
        @Deprecated
        public Long getValue();

        @Override
        public long setValue(long var1);

        public long getLongValue();
    }

    public static interface FastEntrySet
    extends ObjectSet<Entry> {
        public ObjectIterator<Entry> fastIterator();
    }
}

