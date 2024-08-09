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

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class FlatVarIntItemType
extends BaseItemType {
    public FlatVarIntItemType() {
        super("FlatVarIntItem");
    }

    @Override
    public @Nullable Item read(ByteBuf byteBuf) throws Exception {
        boolean bl = byteBuf.readBoolean();
        if (!bl) {
            return null;
        }
        DataItem dataItem = new DataItem();
        dataItem.setIdentifier(VAR_INT.readPrimitive(byteBuf));
        dataItem.setAmount(byteBuf.readByte());
        dataItem.setTag((CompoundTag)NBT.read(byteBuf));
        return dataItem;
    }

    @Override
    public void write(ByteBuf byteBuf, @Nullable Item item) throws Exception {
        if (item == null) {
            byteBuf.writeBoolean(true);
        } else {
            byteBuf.writeBoolean(false);
            VAR_INT.writePrimitive(byteBuf, item.identifier());
            byteBuf.writeByte(item.amount());
            NBT.write(byteBuf, item.tag());
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

