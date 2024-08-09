/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FlintAndSteelItem
extends Item {
    public FlintAndSteelItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext itemUseContext) {
        BlockPos blockPos;
        PlayerEntity playerEntity = itemUseContext.getPlayer();
        World world = itemUseContext.getWorld();
        BlockState blockState = world.getBlockState(blockPos = itemUseContext.getPos());
        if (CampfireBlock.canBeLit(blockState)) {
            world.playSound(playerEntity, blockPos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0f, random.nextFloat() * 0.4f + 0.8f);
            world.setBlockState(blockPos, (BlockState)blockState.with(BlockStateProperties.LIT, true), 0);
            if (playerEntity != null) {
                itemUseContext.getItem().damageItem(1, playerEntity, arg_0 -> FlintAndSteelItem.lambda$onItemUse$0(itemUseContext, arg_0));
            }
            return ActionResultType.func_233537_a_(world.isRemote());
        }
        BlockPos blockPos2 = blockPos.offset(itemUseContext.getFace());
        if (AbstractFireBlock.canLightBlock(world, blockPos2, itemUseContext.getPlacementHorizontalFacing())) {
            world.playSound(playerEntity, blockPos2, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0f, random.nextFloat() * 0.4f + 0.8f);
            BlockState blockState2 = AbstractFireBlock.getFireForPlacement(world, blockPos2);
            world.setBlockState(blockPos2, blockState2, 0);
            ItemStack itemStack = itemUseContext.getItem();
            if (playerEntity instanceof ServerPlayerEntity) {
                CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity)playerEntity, blockPos2, itemStack);
                itemStack.damageItem(1, playerEntity, arg_0 -> FlintAndSteelItem.lambda$onItemUse$1(itemUseContext, arg_0));
            }
            return ActionResultType.func_233537_a_(world.isRemote());
        }
        return ActionResultType.FAIL;
    }

    private static void lambda$onItemUse$1(ItemUseContext itemUseContext, PlayerEntity playerEntity) {
        playerEntity.sendBreakAnimation(itemUseContext.getHand());
    }

    private static void lambda$onItemUse$0(ItemUseContext itemUseContext, PlayerEntity playerEntity) {
        playerEntity.sendBreakAnimation(itemUseContext.getHand());
    }
}

