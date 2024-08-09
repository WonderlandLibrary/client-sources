/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FireChargeItem
extends Item {
    public FireChargeItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext itemUseContext) {
        World world = itemUseContext.getWorld();
        BlockPos blockPos = itemUseContext.getPos();
        BlockState blockState = world.getBlockState(blockPos);
        boolean bl = false;
        if (CampfireBlock.canBeLit(blockState)) {
            this.playUseSound(world, blockPos);
            world.setBlockState(blockPos, (BlockState)blockState.with(CampfireBlock.LIT, true));
            bl = true;
        } else if (AbstractFireBlock.canLightBlock(world, blockPos = blockPos.offset(itemUseContext.getFace()), itemUseContext.getPlacementHorizontalFacing())) {
            this.playUseSound(world, blockPos);
            world.setBlockState(blockPos, AbstractFireBlock.getFireForPlacement(world, blockPos));
            bl = true;
        }
        if (bl) {
            itemUseContext.getItem().shrink(1);
            return ActionResultType.func_233537_a_(world.isRemote);
        }
        return ActionResultType.FAIL;
    }

    private void playUseSound(World world, BlockPos blockPos) {
        world.playSound(null, blockPos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f);
    }
}

