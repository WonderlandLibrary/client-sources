/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BreakableBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class IceBlock
extends BreakableBlock {
    public IceBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public void harvestBlock(World world, PlayerEntity playerEntity, BlockPos blockPos, BlockState blockState, @Nullable TileEntity tileEntity, ItemStack itemStack) {
        super.harvestBlock(world, playerEntity, blockPos, blockState, tileEntity, itemStack);
        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, itemStack) == 0) {
            if (world.getDimensionType().isUltrawarm()) {
                world.removeBlock(blockPos, true);
                return;
            }
            Material material = world.getBlockState(blockPos.down()).getMaterial();
            if (material.blocksMovement() || material.isLiquid()) {
                world.setBlockState(blockPos, Blocks.WATER.getDefaultState());
            }
        }
    }

    @Override
    public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        if (serverWorld.getLightFor(LightType.BLOCK, blockPos) > 11 - blockState.getOpacity(serverWorld, blockPos)) {
            this.turnIntoWater(blockState, serverWorld, blockPos);
        }
    }

    protected void turnIntoWater(BlockState blockState, World world, BlockPos blockPos) {
        if (world.getDimensionType().isUltrawarm()) {
            world.removeBlock(blockPos, true);
        } else {
            world.setBlockState(blockPos, Blocks.WATER.getDefaultState());
            world.neighborChanged(blockPos, Blocks.WATER, blockPos);
        }
    }

    @Override
    public PushReaction getPushReaction(BlockState blockState) {
        return PushReaction.NORMAL;
    }
}

