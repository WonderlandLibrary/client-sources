/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public abstract class AbstractBannerBlock
extends ContainerBlock {
    private final DyeColor color;

    protected AbstractBannerBlock(DyeColor dyeColor, AbstractBlock.Properties properties) {
        super(properties);
        this.color = dyeColor;
    }

    @Override
    public boolean canSpawnInBlock() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        return new BannerTileEntity(this.color);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
        TileEntity tileEntity;
        if (itemStack.hasDisplayName() && (tileEntity = world.getTileEntity(blockPos)) instanceof BannerTileEntity) {
            ((BannerTileEntity)tileEntity).setName(itemStack.getDisplayName());
        }
    }

    @Override
    public ItemStack getItem(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState) {
        TileEntity tileEntity = iBlockReader.getTileEntity(blockPos);
        return tileEntity instanceof BannerTileEntity ? ((BannerTileEntity)tileEntity).getItem(blockState) : super.getItem(iBlockReader, blockPos, blockState);
    }

    public DyeColor getColor() {
        return this.color;
    }
}

