/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;

public class PlayerLastCursorItem
implements StorableObject {
    private Item lastCursorItem;

    public Item getLastCursorItem() {
        return PlayerLastCursorItem.copyItem(this.lastCursorItem);
    }

    public void setLastCursorItem(Item item) {
        this.lastCursorItem = PlayerLastCursorItem.copyItem(item);
    }

    public void setLastCursorItem(Item item, int n) {
        this.lastCursorItem = PlayerLastCursorItem.copyItem(item);
        this.lastCursorItem.setAmount(n);
    }

    public boolean isSet() {
        return this.lastCursorItem != null;
    }

    private static Item copyItem(Item item) {
        DataItem dataItem;
        if (item == null) {
            return null;
        }
        dataItem.setTag((dataItem = new DataItem(item)).tag() == null ? null : dataItem.tag().clone());
        return dataItem;
    }
}

