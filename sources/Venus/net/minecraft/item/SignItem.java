/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WallOrFloorItem;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SignItem
extends WallOrFloorItem {
    public SignItem(Item.Properties properties, Block block, Block block2) {
        super(block, block2, properties);
    }

    @Override
    protected boolean onBlockPlaced(BlockPos blockPos, World world, @Nullable PlayerEntity playerEntity, ItemStack itemStack, BlockState blockState) {
        boolean bl = super.onBlockPlaced(blockPos, world, playerEntity, itemStack, blockState);
        if (!world.isRemote && !bl && playerEntity != null) {
            playerEntity.openSignEditor((SignTileEntity)world.getTileEntity(blockPos));
        }
        return bl;
    }
}

