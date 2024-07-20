/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.world.World;

public class ItemMapBase
extends Item {
    @Override
    public boolean isMap() {
        return true;
    }

    @Nullable
    public Packet<?> createMapDataPacket(ItemStack stack, World worldIn, EntityPlayer player) {
        return null;
    }
}

