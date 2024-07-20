/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2ObjectFunction;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;

public interface Char2ObjectMap<V>
extends Char2ObjectFunction<V>,
Map<Character, V> {
    @Override
    public ObjectSet<Map.Entry<Character, V>> entrySet();

    public ObjectSet<Entry<V>> char2ObjectEntrySet();

    public CharSet keySet();

    @Override
    public ObjectCollection<V> values();

    public static interface Entry<V>
    extends Map.Entry<Character, V> {
        @Override
        @Deprecated
        public Character getKey();

        public char getCharKey();
    }

    public static interface FastEntrySet<V>
    extends ObjectSet<Entry<V>> {
        public ObjectIterator<Entry<V>> fastIterator();
    }
}

