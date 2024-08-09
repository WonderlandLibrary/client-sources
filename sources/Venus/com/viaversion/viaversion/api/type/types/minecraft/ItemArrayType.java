/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.types.minecraft.BaseItemArrayType;
import io.netty.buffer.ByteBuf;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ItemArrayType
extends BaseItemArrayType {
    public ItemArrayType() {
        super("Item Array");
    }

    @Override
    public Item[] read(ByteBuf byteBuf) throws Exception {
        int n = SHORT.readPrimitive(byteBuf);
        Item[] itemArray = new Item[n];
        for (int i = 0; i < n; ++i) {
            itemArray[i] = (Item)ITEM.read(byteBuf);
        }
        return itemArray;
    }

    @Override
    public void write(ByteBuf byteBuf, Item[] itemArray) throws Exception {
        SHORT.writePrimitive(byteBuf, (short)itemArray.length);
        for (Item item : itemArray) {
            ITEM.write(byteBuf, item);
        }
    }

    @Override
    public Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf, Object object) throws Exception {
        this.write(byteBuf, (Item[])object);
    }
}

