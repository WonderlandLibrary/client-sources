/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_17to1_16_4.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrayList;
import com.viaversion.viaversion.libs.fastutil.ints.IntList;

public final class InventoryAcknowledgements
implements StorableObject {
    private final IntList ids = new IntArrayList();

    public void addId(int n) {
        this.ids.add(n);
    }

    public boolean removeId(int n) {
        return this.ids.rem(n);
    }
}

