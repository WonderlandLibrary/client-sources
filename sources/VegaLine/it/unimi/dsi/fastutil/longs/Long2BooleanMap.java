/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.longs.Long2BooleanFunction;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Map;

public interface Long2BooleanMap
extends Long2BooleanFunction,
Map<Long, Boolean> {
    @Override
    public ObjectSet<Map.Entry<Long, Boolean>> entrySet();

    public ObjectSet<Entry> long2BooleanEntrySet();

    public LongSet keySet();

    public BooleanCollection values();

    public boolean containsValue(boolean var1);

    public static interface Entry
    extends Map.Entry<Long, Boolean> {
        @Override
        @Deprecated
        public Long getKey();

        public long getLongKey();

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

