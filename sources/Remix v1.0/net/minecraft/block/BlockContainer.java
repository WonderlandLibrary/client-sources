package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.tileentity.*;

public abstract class BlockContainer extends Block implements ITileEntityProvider
{
    protected BlockContainer(final Material materialIn) {
        super(materialIn);
        this.isBlockContainer = true;
    }
    
    @Override
    public int getRenderType() {
        return -1;
    }
    
    @Override
    public void breakBlock(final World worldIn, final BlockPos pos, final IBlockState state) {
        super.breakBlock(worldIn, pos, state);
        worldIn.removeTileEntity(pos);
    }
    
    @Override
    public boolean onBlockEventReceived(final World worldIn, final BlockPos pos, final IBlockState state, final int eventID, final int eventParam) {
        super.onBlockEventReceived(worldIn, pos, state, eventID, eventParam);
        final TileEntity var6 = worldIn.getTileEntity(pos);
        return var6 != null && var6.receiveClientEvent(eventID, eventParam);
    }
}
