package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockContainer extends Block implements ITileEntityProvider
{
    protected BlockContainer(Material materialIn)
    {
        this(materialIn, materialIn.getMaterialMapColor());
    }

    protected BlockContainer(Material materialIn, MapColor color)
    {
        super(materialIn, color);
        this.isBlockContainer = true;
    }

    protected boolean isInvalidNeighbor(World worldIn, BlockPos pos, EnumFacing facing)
    {
        return worldIn.getBlockState(pos.offset(facing)).getMaterial() == Material.CACTUS;
    }

    protected boolean hasInvalidNeighbor(World worldIn, BlockPos pos)
    {
        return this.isInvalidNeighbor(worldIn, pos, EnumFacing.NORTH) || this.isInvalidNeighbor(worldIn, pos, EnumFacing.SOUTH) || this.isInvalidNeighbor(worldIn, pos, EnumFacing.WEST) || this.isInvalidNeighbor(worldIn, pos, EnumFacing.EAST);
    }

    /**
     * The type of render function called. 3 for standard block models, 2 for TESR's, 1 for liquids, -1 is no render
     */
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.INVISIBLE;
    }

    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        super.breakBlock(worldIn, pos, state);
        worldIn.removeTileEntity(pos);
    }

    public boolean func_189539_a(IBlockState p_189539_1_, World p_189539_2_, BlockPos p_189539_3_, int p_189539_4_, int p_189539_5_)
    {
        super.func_189539_a(p_189539_1_, p_189539_2_, p_189539_3_, p_189539_4_, p_189539_5_);
        TileEntity tileentity = p_189539_2_.getTileEntity(p_189539_3_);
        return tileentity == null ? false : tileentity.receiveClientEvent(p_189539_4_, p_189539_5_);
    }
}
