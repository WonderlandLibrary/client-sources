package net.minecraft.dispenser;

import net.minecraft.dispenser.ILocatableSource;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;

public interface IBlockSource extends ILocatableSource {
   double getX();

   double getY();

   int getBlockMetadata();

   BlockPos getBlockPos();

   double getZ();

   TileEntity getBlockTileEntity();
}
