package net.minecraft.dispenser;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;

public interface IBlockSource extends ILocatableSource {
   Block getBlock();

   BlockPos getBlockPos();

   int getBlockMetadata();

   double getZ();

   double getX();

   TileEntity getBlockTileEntity();

   double getY();
}
