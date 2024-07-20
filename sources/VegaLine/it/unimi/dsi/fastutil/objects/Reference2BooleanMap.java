/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.Reference2BooleanFunction;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import java.util.Map;

public interface Reference2BooleanMap<K>
extends Reference2BooleanFunction<K>,
Map<K, Boolean> {
    @Override
    public ObjectSet<Map.Entry<K, Boolean>> entrySet();

    public ObjectSet<Entry<K>> reference2BooleanEntrySet();

    @Override
    public ReferenceSet<K> keySet();

    public BooleanCollection values();

    public boolean containsValue(boolean var1);

    public static interface Entry<K>
    extends Map.Entry<K, Boolean> {
        @Override
        @Deprecated
        public Boolean getValue();

        @Override
        public boolean setValue(boolean var1);

        public boolean getBooleanValue();
    }

    public static interface FastEntrySet<K>
    extends ObjectSet<Entry<K>> {
        public ObjectIterator<Entry<K>> fastIterator();
    }
}

