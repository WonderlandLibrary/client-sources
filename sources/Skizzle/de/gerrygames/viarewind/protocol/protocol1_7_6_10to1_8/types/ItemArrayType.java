/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;

import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import io.netty.buffer.ByteBuf;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.type.Type;

public class ItemArrayType
extends Type<Item[]> {
    private final boolean compressed;

    public ItemArrayType(boolean compressed) {
        super(Item[].class);
        this.compressed = compressed;
    }

    @Override
    public Item[] read(ByteBuf buffer) throws Exception {
        int amount = Type.SHORT.read(buffer).shortValue();
        Item[] items = new Item[amount];
        for (int i = 0; i < amount; ++i) {
            items[i] = (Item)(this.compressed ? Types1_7_6_10.COMPRESSED_NBT_ITEM : Types1_7_6_10.ITEM).read(buffer);
        }
        return items;
    }

    @Override
    public void write(ByteBuf buffer, Item[] items) throws Exception {
        Type.SHORT.write(buffer, (short)items.length);
        for (Item item : items) {
            (this.compressed ? Types1_7_6_10.COMPRESSED_NBT_ITEM : Types1_7_6_10.ITEM).write(buffer, item);
        }
    }
}

