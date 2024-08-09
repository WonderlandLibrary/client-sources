/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_12to1_11_1.storage;

public class ItemTransaction {
    private final short windowId;
    private final short slotId;
    private final short actionId;

    public ItemTransaction(short s, short s2, short s3) {
        this.windowId = s;
        this.slotId = s2;
        this.actionId = s3;
    }

    public short getWindowId() {
        return this.windowId;
    }

    public short getSlotId() {
        return this.slotId;
    }

    public short getActionId() {
        return this.actionId;
    }

    public String toString() {
        return "ItemTransaction{windowId=" + this.windowId + ", slotId=" + this.slotId + ", actionId=" + this.actionId + '}';
    }
}

