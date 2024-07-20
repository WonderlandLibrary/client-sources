/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.objects.Object2CharFunction;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;

public interface Object2CharMap<K>
extends Object2CharFunction<K>,
Map<K, Character> {
    @Override
    public ObjectSet<Map.Entry<K, Character>> entrySet();

    public ObjectSet<Entry<K>> object2CharEntrySet();

    @Override
    public ObjectSet<K> keySet();

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

