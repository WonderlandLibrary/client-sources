/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.chars.Char2BooleanFunction;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;

public interface Char2BooleanMap
extends Char2BooleanFunction,
Map<Character, Boolean> {
    @Override
    public ObjectSet<Map.Entry<Character, Boolean>> entrySet();

    public ObjectSet<Entry> char2BooleanEntrySet();

    public CharSet keySet();

    public BooleanCollection values();

    public boolean containsValue(boolean var1);

    public static interface Entry
    extends Map.Entry<Character, Boolean> {
        @Override
        @Deprecated
        public Character getKey();

        public char getCharKey();

        @Override
        @Deprecated
        public Boolean getValue();

        @Override
        public boolean setValue(boolean var1);

        public boolean getBooleanValue();
    }

    public static interface FastEntrySet
    extends ObjectSet<Entry> {
        public ObjectIterator<Entry> fastIterator();
    }
}

