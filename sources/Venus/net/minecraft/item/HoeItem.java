/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.ToolItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HoeItem
extends ToolItem {
    private static final Set<Block> EFFECTIVE_ON_BLOCKS = ImmutableSet.of(Blocks.NETHER_WART_BLOCK, Blocks.WARPED_WART_BLOCK, Blocks.HAY_BLOCK, Blocks.DRIED_KELP_BLOCK, Blocks.TARGET, Blocks.SHROOMLIGHT, Blocks.SPONGE, Blocks.WET_SPONGE, Blocks.JUNGLE_LEAVES, Blocks.OAK_LEAVES, Blocks.SPRUCE_LEAVES, Blocks.DARK_OAK_LEAVES, Blocks.ACACIA_LEAVES, Blocks.BIRCH_LEAVES);
    protected static final Map<Block, BlockState> HOE_LOOKUP = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Blocks.FARMLAND.getDefaultState(), Blocks.GRASS_PATH, Blocks.FARMLAND.getDefaultState(), Blocks.DIRT, Blocks.FARMLAND.getDefaultState(), Blocks.COARSE_DIRT, Blocks.DIRT.getDefaultState()));

    protected HoeItem(IItemTier iItemTier, int n, float f, Item.Properties properties) {
        super(n, f, iItemTier, EFFECTIVE_ON_BLOCKS, properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext itemUseContext) {
        BlockState blockState;
        World world = itemUseContext.getWorld();
        BlockPos blockPos = itemUseContext.getPos();
        if (itemUseContext.getFace() != Direction.DOWN && world.getBlockState(blockPos.up()).isAir() && (blockState = HOE_LOOKUP.get(world.getBlockState(blockPos).getBlock())) != null) {
            PlayerEntity playerEntity = itemUseContext.getPlayer();
            world.playSound(playerEntity, blockPos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
            if (!world.isRemote) {
                world.setBlockState(blockPos, blockState, 0);
                if (playerEntity != null) {
                    itemUseContext.getItem().damageItem(1, playerEntity, arg_0 -> HoeItem.lambda$onItemUse$0(itemUseContext, arg_0));
                }
            }
            return ActionResultType.func_233537_a_(world.isRemote);
        }
        return ActionResultType.PASS;
    }

    private static void lambda$onItemUse$0(ItemUseContext itemUseContext, PlayerEntity playerEntity) {
        playerEntity.sendBreakAnimation(itemUseContext.getHand());
    }
}

