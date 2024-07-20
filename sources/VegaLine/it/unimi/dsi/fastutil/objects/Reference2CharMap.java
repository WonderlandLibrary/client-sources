/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.Reference2CharFunction;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import java.util.Map;

public interface Reference2CharMap<K>
extends Reference2CharFunction<K>,
Map<K, Character> {
    @Override
    public ObjectSet<Map.Entry<K, Character>> entrySet();

    public ObjectSet<Entry<K>> reference2CharEntrySet();

    @Override
    public ReferenceSet<K> keySet();

    public CharCollection values();

    public boolean containsValue(char var1);

    public static interface Entry<K>
    extends Map.Entry<K, Character> {
        @Override
        @Deprecated
        public Character getValue();

        @Override
        public char setValue(char var1);

        public char getCharValue();
    }

    public static interface FastEntrySet<K>
    extends ObjectSet<Entry<K>> {
        public ObjectIterator<Entry<K>> fastIterator();
    }
}

