/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.BaseItemArrayType;
import io.netty.buffer.ByteBuf;

public class FlatItemArrayType
extends BaseItemArrayType {
    public FlatItemArrayType() {
        super("Flat Item Array");
    }

    @Override
    public Item[] read(ByteBuf buffer) throws Exception {
        int amount = Type.SHORT.readPrimitive(buffer);
        Item[] array = new Item[amount];
        int i = 0;
        while (i < amount) {
            array[i] = (Item)Type.FLAT_ITEM.read(buffer);
            ++i;
        }
        return array;
    }

    @Override
    public void write(ByteBuf buffer, Item[] object) throws Exception {
        Type.SHORT.writePrimitive(buffer, (short)object.length);
        Item[] itemArray = object;
        int n = itemArray.length;
        int n2 = 0;
        while (n2 < n) {
            Item o = itemArray[n2];
            Type.FLAT_ITEM.write(buffer, o);
            ++n2;
        }
    }
}

