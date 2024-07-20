/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2ShortFunction;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import java.util.Map;

public interface Char2ShortMap
extends Char2ShortFunction,
Map<Character, Short> {
    @Override
    public ObjectSet<Map.Entry<Character, Short>> entrySet();

    public ObjectSet<Entry> char2ShortEntrySet();

    public CharSet keySet();

    public ShortCollection values();

    public boolean containsValue(short var1);

    public static interface Entry
    extends Map.Entry<Character, Short> {
        @Override
        @Deprecated
        public Character getKey();

        public char getCharKey();

        @Override
        @Deprecated
        public Short getValue();

        @Override
        public short setValue(short var1);

        public short getShortValue();
    }

    public static interface FastEntrySet
    extends ObjectSet<Entry> {
        public ObjectIterator<Entry> fastIterator();
    }
}

