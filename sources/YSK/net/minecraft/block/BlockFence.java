package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.entity.*;

public class BlockFence extends Block
{
    public static final PropertyBool SOUTH;
    public static final PropertyBool EAST;
    private static final String[] I;
    public static final PropertyBool WEST;
    public static final PropertyBool NORTH;
    
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
            if (false) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[0x29 ^ 0x2D])["".length()] = I("'\u0007;\"9", "IhIVQ");
        BlockFence.I[" ".length()] = I("\u001f5\u0000\u0005", "zTsqD");
        BlockFence.I["  ".length()] = I("'(\u00000\u0007", "TGuDo");
        BlockFence.I["   ".length()] = I("\u0019(8\u001a", "nMKnc");
    }
    
    static {
        I();
        NORTH = PropertyBool.create(BlockFence.I["".length()]);
        EAST = PropertyBool.create(BlockFence.I[" ".length()]);
        SOUTH = PropertyBool.create(BlockFence.I["  ".length()]);
        WEST = PropertyBool.create(BlockFence.I["   ".length()]);
    }
    
    public BlockFence(final Material material, final MapColor mapColor) {
        super(material, mapColor);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockFence.NORTH, "".length() != 0).withProperty((IProperty<Comparable>)BlockFence.EAST, "".length() != 0).withProperty((IProperty<Comparable>)BlockFence.SOUTH, "".length() != 0).withProperty((IProperty<Comparable>)BlockFence.WEST, (boolean)("".length() != 0)));
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        int n4;
        if (world.isRemote) {
            n4 = " ".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            n4 = (ItemLead.attachToFence(entityPlayer, world, blockPos) ? 1 : 0);
        }
        return n4 != 0;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return "".length();
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final boolean canConnectTo = this.canConnectTo(blockAccess, blockPos.north());
        final boolean canConnectTo2 = this.canConnectTo(blockAccess, blockPos.south());
        final boolean canConnectTo3 = this.canConnectTo(blockAccess, blockPos.west());
        final boolean canConnectTo4 = this.canConnectTo(blockAccess, blockPos.east());
        float n = 0.375f;
        float n2 = 0.625f;
        float n3 = 0.375f;
        float n4 = 0.625f;
        if (canConnectTo) {
            n3 = 0.0f;
        }
        if (canConnectTo2) {
            n4 = 1.0f;
        }
        if (canConnectTo3) {
            n = 0.0f;
        }
        if (canConnectTo4) {
            n2 = 1.0f;
        }
        this.setBlockBounds(n, 0.0f, n3, n2, 1.0f, n4);
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[0x1 ^ 0x5];
        array["".length()] = BlockFence.NORTH;
        array[" ".length()] = BlockFence.EAST;
        array["  ".length()] = BlockFence.WEST;
        array["   ".length()] = BlockFence.SOUTH;
        return new BlockState(this, array);
    }
    
    public boolean canConnectTo(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final Block block = blockAccess.getBlockState(blockPos).getBlock();
        int n;
        if (block == Blocks.barrier) {
            n = "".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else if ((!(block instanceof BlockFence) || block.blockMaterial != this.blockMaterial) && !(block instanceof BlockFenceGate)) {
            if (block.blockMaterial.isOpaque() && block.isFullCube()) {
                if (block.blockMaterial != Material.gourd) {
                    n = " ".length();
                    "".length();
                    if (0 >= 1) {
                        throw null;
                    }
                }
                else {
                    n = "".length();
                    "".length();
                    if (1 < 0) {
                        throw null;
                    }
                }
            }
            else {
                n = "".length();
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        return " ".length() != 0;
    }
    
    public BlockFence(final Material material) {
        this(material, material.getMaterialMapColor());
    }
    
    @Override
    public void addCollisionBoxesToList(final World world, final BlockPos blockPos, final IBlockState blockState, final AxisAlignedBB axisAlignedBB, final List<AxisAlignedBB> list, final Entity entity) {
        final boolean canConnectTo = this.canConnectTo(world, blockPos.north());
        final boolean canConnectTo2 = this.canConnectTo(world, blockPos.south());
        final boolean canConnectTo3 = this.canConnectTo(world, blockPos.west());
        final boolean canConnectTo4 = this.canConnectTo(world, blockPos.east());
        float n = 0.375f;
        float n2 = 0.625f;
        float n3 = 0.375f;
        float n4 = 0.625f;
        if (canConnectTo) {
            n3 = 0.0f;
        }
        if (canConnectTo2) {
            n4 = 1.0f;
        }
        if (canConnectTo || canConnectTo2) {
            this.setBlockBounds(n, 0.0f, n3, n2, 1.5f, n4);
            super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        }
        float n5 = 0.375f;
        float n6 = 0.625f;
        if (canConnectTo3) {
            n = 0.0f;
        }
        if (canConnectTo4) {
            n2 = 1.0f;
        }
        if (canConnectTo3 || canConnectTo4 || (!canConnectTo && !canConnectTo2)) {
            this.setBlockBounds(n, 0.0f, n5, n2, 1.5f, n6);
            super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        }
        if (canConnectTo) {
            n5 = 0.0f;
        }
        if (canConnectTo2) {
            n6 = 1.0f;
        }
        this.setBlockBounds(n, 0.0f, n5, n2, 1.0f, n6);
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    @Override
    public IBlockState getActualState(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        return blockState.withProperty((IProperty<Comparable>)BlockFence.NORTH, this.canConnectTo(blockAccess, blockPos.north())).withProperty((IProperty<Comparable>)BlockFence.EAST, this.canConnectTo(blockAccess, blockPos.east())).withProperty((IProperty<Comparable>)BlockFence.SOUTH, this.canConnectTo(blockAccess, blockPos.south())).withProperty((IProperty<Comparable>)BlockFence.WEST, this.canConnectTo(blockAccess, blockPos.west()));
    }
    
    @Override
    public boolean isPassable(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return "".length() != 0;
    }
}
