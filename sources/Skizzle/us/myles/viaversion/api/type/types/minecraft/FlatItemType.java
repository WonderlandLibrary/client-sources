/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package us.myles.ViaVersion.api.type.types.minecraft;

import io.netty.buffer.ByteBuf;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.api.type.types.minecraft.BaseItemType;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;

public class FlatItemType
extends BaseItemType {
    public FlatItemType() {
        super("FlatItem");
    }

    @Override
    public Item read(ByteBuf buffer) throws Exception {
        short id = buffer.readShort();
        if (id < 0) {
            return null;
        }
        Item item = new Item();
        item.setIdentifier(id);
        item.setAmount(buffer.readByte());
        item.setTag((CompoundTag)Type.NBT.read(buffer));
        return item;
    }

    @Override
    public void write(ByteBuf buffer, Item object) throws Exception {
        if (object == null) {
            buffer.writeShort(-1);
        } else {
            buffer.writeShort(object.getIdentifier());
            buffer.writeByte((int)object.getAmount());
            Type.NBT.write(buffer, object.getTag());
        }
    }
}

