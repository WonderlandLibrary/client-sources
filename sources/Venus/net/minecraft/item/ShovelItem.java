/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Map;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
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

public class ShovelItem
extends ToolItem {
    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.CLAY, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL, Blocks.FARMLAND, Blocks.GRASS_BLOCK, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.RED_SAND, Blocks.SNOW_BLOCK, Blocks.SNOW, Blocks.SOUL_SAND, Blocks.GRASS_PATH, Blocks.WHITE_CONCRETE_POWDER, Blocks.ORANGE_CONCRETE_POWDER, Blocks.MAGENTA_CONCRETE_POWDER, Blocks.LIGHT_BLUE_CONCRETE_POWDER, Blocks.YELLOW_CONCRETE_POWDER, Blocks.LIME_CONCRETE_POWDER, Blocks.PINK_CONCRETE_POWDER, Blocks.GRAY_CONCRETE_POWDER, Blocks.LIGHT_GRAY_CONCRETE_POWDER, Blocks.CYAN_CONCRETE_POWDER, Blocks.PURPLE_CONCRETE_POWDER, Blocks.BLUE_CONCRETE_POWDER, Blocks.BROWN_CONCRETE_POWDER, Blocks.GREEN_CONCRETE_POWDER, Blocks.RED_CONCRETE_POWDER, Blocks.BLACK_CONCRETE_POWDER, Blocks.SOUL_SOIL);
    protected static final Map<Block, BlockState> SHOVEL_LOOKUP = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Blocks.GRASS_PATH.getDefaultState()));

    public ShovelItem(IItemTier iItemTier, float f, float f2, Item.Properties properties) {
        super(f, f2, iItemTier, EFFECTIVE_ON, properties);
    }

    @Override
    public boolean canHarvestBlock(BlockState blockState) {
        return blockState.isIn(Blocks.SNOW) || blockState.isIn(Blocks.SNOW_BLOCK);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext itemUseContext) {
        World world = itemUseContext.getWorld();
        BlockPos blockPos = itemUseContext.getPos();
        BlockState blockState = world.getBlockState(blockPos);
        if (itemUseContext.getFace() == Direction.DOWN) {
            return ActionResultType.PASS;
        }
        PlayerEntity playerEntity = itemUseContext.getPlayer();
        BlockState blockState2 = SHOVEL_LOOKUP.get(blockState.getBlock());
        BlockState blockState3 = null;
        if (blockState2 != null && world.getBlockState(blockPos.up()).isAir()) {
            world.playSound(playerEntity, blockPos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0f, 1.0f);
            blockState3 = blockState2;
        } else if (blockState.getBlock() instanceof CampfireBlock && blockState.get(CampfireBlock.LIT).booleanValue()) {
            if (!world.isRemote()) {
                world.playEvent(null, 1009, blockPos, 0);
            }
            CampfireBlock.extinguish(world, blockPos, blockState);
            blockState3 = (BlockState)blockState.with(CampfireBlock.LIT, false);
        }
        if (blockState3 != null) {
            if (!world.isRemote) {
                world.setBlockState(blockPos, blockState3, 0);
                if (playerEntity != null) {
                    itemUseContext.getItem().damageItem(1, playerEntity, arg_0 -> ShovelItem.lambda$onItemUse$0(itemUseContext, arg_0));
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

