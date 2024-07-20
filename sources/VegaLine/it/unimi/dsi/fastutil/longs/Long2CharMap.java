/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.longs.Long2CharFunction;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;

public interface Long2CharMap
extends Long2CharFunction,
Map<Long, Character> {
    @Override
    public ObjectSet<Map.Entry<Long, Character>> entrySet();

    public ObjectSet<Entry> long2CharEntrySet();

    public LongSet keySet();

    public CharCollection values();

    public boolean containsValue(char var1);

    public static interface Entry
    extends Map.Entry<Long, Character> {
        @Override
        @Deprecated
        public Long getKey();

        public long getLongKey();

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

