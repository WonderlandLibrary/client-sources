/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2ReferenceFunction;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.util.Map;

public interface Char2ReferenceMap<V>
extends Char2ReferenceFunction<V>,
Map<Character, V> {
    @Override
    public ObjectSet<Map.Entry<Character, V>> entrySet();

    public ObjectSet<Entry<V>> char2ReferenceEntrySet();

    public CharSet keySet();

    @Override
    public ReferenceCollection<V> values();

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

