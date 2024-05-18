package net.minecraft.dispenser;

import net.minecraft.tileentity.*;
import net.minecraft.util.*;

public interface IBlockSource extends ILocatableSource
{
    double getZ();
    
    double getY();
    
     <T extends TileEntity> T getBlockTileEntity();
    
    double getX();
    
    int getBlockMetadata();
    
    BlockPos getBlockPos();
}
