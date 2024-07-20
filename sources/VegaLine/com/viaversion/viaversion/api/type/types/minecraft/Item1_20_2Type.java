/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.types.minecraft.BaseItemType;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import io.netty.buffer.ByteBuf;
import org.checkerframework.checker.nullness.qual.Nullable;

public class Item1_20_2Type
extends BaseItemType {
    public Item1_20_2Type() {
        super("Item1_20_2Type");
    }

    @Override
    public @Nullable Item read(ByteBuf buffer) throws Exception {
        if (!buffer.readBoolean()) {
            return null;
        }
        DataItem item = new DataItem();
        item.setIdentifier(VAR_INT.readPrimitive(buffer));
        item.setAmount(buffer.readByte());
        item.setTag((CompoundTag)NAMELESS_NBT.read(buffer));
        return item;
    }

    @Override
    public void write(ByteBuf buffer, @Nullable Item object) throws Exception {
        if (object == null) {
            buffer.writeBoolean(false);
        } else {
            buffer.writeBoolean(true);
            VAR_INT.writePrimitive(buffer, object.identifier());
            buffer.writeByte(object.amount());
            NAMELESS_NBT.write(buffer, object.tag());
        }
    }
}

