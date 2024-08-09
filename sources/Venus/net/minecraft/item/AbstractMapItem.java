/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;

public class AbstractMapItem
extends Item {
    public AbstractMapItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public boolean isComplex() {
        return false;
    }

    @Nullable
    public IPacket<?> getUpdatePacket(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        return null;
    }
}

