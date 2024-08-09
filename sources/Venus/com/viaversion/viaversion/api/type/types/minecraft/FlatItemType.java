/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.BaseItemType;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import io.netty.buffer.ByteBuf;
import org.checkerframework.checker.nullness.qual.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class FlatItemType
extends BaseItemType {
    public FlatItemType() {
        super("FlatItem");
    }

    @Override
    public @Nullable Item read(ByteBuf byteBuf) throws Exception {
        short s = byteBuf.readShort();
        if (s < 0) {
            return null;
        }
        DataItem dataItem = new DataItem();
        dataItem.setIdentifier(s);
        dataItem.setAmount(byteBuf.readByte());
        dataItem.setTag((CompoundTag)Type.NBT.read(byteBuf));
        return dataItem;
    }

    @Override
    public void write(ByteBuf byteBuf, @Nullable Item item) throws Exception {
        if (item == null) {
            byteBuf.writeShort(-1);
        } else {
            byteBuf.writeShort(item.identifier());
            byteBuf.writeByte(item.amount());
            Type.NBT.write(byteBuf, item.tag());
        }
    }

    @Override
    public @Nullable Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf, @Nullable Object object) throws Exception {
        this.write(byteBuf, (Item)object);
    }
}

