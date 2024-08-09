/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ShearsItem
extends Item {
    public ShearsItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack itemStack, World world, BlockState blockState, BlockPos blockPos, LivingEntity livingEntity) {
        if (!world.isRemote && !blockState.getBlock().isIn(BlockTags.FIRE)) {
            itemStack.damageItem(1, livingEntity, ShearsItem::lambda$onBlockDestroyed$0);
        }
        return !blockState.isIn(BlockTags.LEAVES) && !blockState.isIn(Blocks.COBWEB) && !blockState.isIn(Blocks.GRASS) && !blockState.isIn(Blocks.FERN) && !blockState.isIn(Blocks.DEAD_BUSH) && !blockState.isIn(Blocks.VINE) && !blockState.isIn(Blocks.TRIPWIRE) && !blockState.isIn(BlockTags.WOOL) ? super.onBlockDestroyed(itemStack, world, blockState, blockPos, livingEntity) : true;
    }

    @Override
    public boolean canHarvestBlock(BlockState blockState) {
        return blockState.isIn(Blocks.COBWEB) || blockState.isIn(Blocks.REDSTONE_WIRE) || blockState.isIn(Blocks.TRIPWIRE);
    }

    @Override
    public float getDestroySpeed(ItemStack itemStack, BlockState blockState) {
        if (!blockState.isIn(Blocks.COBWEB) && !blockState.isIn(BlockTags.LEAVES)) {
            return blockState.isIn(BlockTags.WOOL) ? 5.0f : super.getDestroySpeed(itemStack, blockState);
        }
        return 15.0f;
    }

    private static void lambda$onBlockDestroyed$0(LivingEntity livingEntity) {
        livingEntity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
    }
}

