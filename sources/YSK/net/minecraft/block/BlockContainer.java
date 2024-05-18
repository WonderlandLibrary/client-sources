package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.tileentity.*;

public abstract class BlockContainer extends Block implements ITileEntityProvider
{
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected boolean func_181087_e(final World world, final BlockPos blockPos) {
        if (!this.func_181086_a(world, blockPos, EnumFacing.NORTH) && !this.func_181086_a(world, blockPos, EnumFacing.SOUTH) && !this.func_181086_a(world, blockPos, EnumFacing.WEST) && !this.func_181086_a(world, blockPos, EnumFacing.EAST)) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    protected BlockContainer(final Material material) {
        this(material, material.getMaterialMapColor());
    }
    
    protected BlockContainer(final Material material, final MapColor mapColor) {
        super(material, mapColor);
        this.isBlockContainer = (" ".length() != 0);
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        super.breakBlock(world, blockPos, blockState);
        world.removeTileEntity(blockPos);
    }
    
    @Override
    public int getRenderType() {
        return -" ".length();
    }
    
    protected boolean func_181086_a(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        if (world.getBlockState(blockPos.offset(enumFacing)).getBlock().getMaterial() == Material.cactus) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public boolean onBlockEventReceived(final World world, final BlockPos blockPos, final IBlockState blockState, final int n, final int n2) {
        super.onBlockEventReceived(world, blockPos, blockState, n, n2);
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        int n3;
        if (tileEntity == null) {
            n3 = "".length();
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        else {
            n3 = (tileEntity.receiveClientEvent(n, n2) ? 1 : 0);
        }
        return n3 != 0;
    }
}
