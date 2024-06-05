/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Type;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import io.netty.buffer.ByteBuf;

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
        int i = 0;
        while (i < amount) {
            items[i] = (Item)(this.compressed ? Types1_7_6_10.COMPRESSED_NBT_ITEM : Types1_7_6_10.ITEM).read(buffer);
            ++i;
        }
        return items;
    }

    @Override
    public void write(ByteBuf buffer, Item[] items) throws Exception {
        Type.SHORT.write(buffer, (short)items.length);
        Item[] itemArray = items;
        int n = itemArray.length;
        int n2 = 0;
        while (n2 < n) {
            Item item = itemArray[n2];
            (this.compressed ? Types1_7_6_10.COMPRESSED_NBT_ITEM : Types1_7_6_10.ITEM).write(buffer, item);
            ++n2;
        }
    }
}

