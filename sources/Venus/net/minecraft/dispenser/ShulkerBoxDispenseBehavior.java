/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.dispenser;

import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.OptionalDispenseBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DirectionalPlaceContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ShulkerBoxDispenseBehavior
extends OptionalDispenseBehavior {
    @Override
    protected ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
        this.setSuccessful(true);
        Item item = itemStack.getItem();
        if (item instanceof BlockItem) {
            Direction direction = iBlockSource.getBlockState().get(DispenserBlock.FACING);
            BlockPos blockPos = iBlockSource.getBlockPos().offset(direction);
            Direction direction2 = iBlockSource.getWorld().isAirBlock(blockPos.down()) ? direction : Direction.UP;
            this.setSuccessful(((BlockItem)item).tryPlace(new DirectionalPlaceContext((World)iBlockSource.getWorld(), blockPos, direction, itemStack, direction2)).isSuccessOrConsume());
        }
        return itemStack;
    }
}

