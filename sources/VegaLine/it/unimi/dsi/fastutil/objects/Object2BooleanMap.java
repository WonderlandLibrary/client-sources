/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.objects.Object2BooleanFunction;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;

public interface Object2BooleanMap<K>
extends Object2BooleanFunction<K>,
Map<K, Boolean> {
    @Override
    public ObjectSet<Map.Entry<K, Boolean>> entrySet();

    public ObjectSet<Entry<K>> object2BooleanEntrySet();

    @Override
    public ObjectSet<K> keySet();

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

