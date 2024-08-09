/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.IBeaconBeamColorProvider;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.BeaconTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BeaconBlock
extends ContainerBlock
implements IBeaconBeamColorProvider {
    public BeaconBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public DyeColor getColor() {
        return DyeColor.WHITE;
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        return new BeaconTileEntity();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (world.isRemote) {
            return ActionResultType.SUCCESS;
        }
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof BeaconTileEntity) {
            playerEntity.openContainer((BeaconTileEntity)tileEntity);
            playerEntity.addStat(Stats.INTERACT_WITH_BEACON);
        }
        return ActionResultType.CONSUME;
    }

    @Override
    public BlockRenderType getRenderType(BlockState blockState) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack itemStack) {
        TileEntity tileEntity;
        if (itemStack.hasDisplayName() && (tileEntity = world.getTileEntity(blockPos)) instanceof BeaconTileEntity) {
            ((BeaconTileEntity)tileEntity).setCustomName(itemStack.getDisplayName());
        }
    }
}

