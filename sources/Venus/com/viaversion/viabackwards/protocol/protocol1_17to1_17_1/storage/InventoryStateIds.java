/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_17to1_17_1.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;

public final class InventoryStateIds
implements StorableObject {
    private final Int2IntMap ids = new Int2IntOpenHashMap();

    public InventoryStateIds() {
        this.ids.defaultReturnValue(Integer.MAX_VALUE);
    }

    public void setStateId(short s, int n) {
        this.ids.put(s, n);
    }

    public int removeStateId(short s) {
        return this.ids.remove(s);
    }
}

