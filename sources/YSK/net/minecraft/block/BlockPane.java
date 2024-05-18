package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;

public class BlockPane extends Block
{
    public static final PropertyBool NORTH;
    public static final PropertyBool WEST;
    public static final PropertyBool SOUTH;
    private static final String[] I;
    private final boolean canDrop;
    public static final PropertyBool EAST;
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        float n = 0.4375f;
        float n2 = 0.5625f;
        float n3 = 0.4375f;
        float n4 = 0.5625f;
        final boolean canPaneConnectToBlock = this.canPaneConnectToBlock(blockAccess.getBlockState(blockPos.north()).getBlock());
        final boolean canPaneConnectToBlock2 = this.canPaneConnectToBlock(blockAccess.getBlockState(blockPos.south()).getBlock());
        final boolean canPaneConnectToBlock3 = this.canPaneConnectToBlock(blockAccess.getBlockState(blockPos.west()).getBlock());
        final boolean canPaneConnectToBlock4 = this.canPaneConnectToBlock(blockAccess.getBlockState(blockPos.east()).getBlock());
        if ((!canPaneConnectToBlock3 || !canPaneConnectToBlock4) && (canPaneConnectToBlock3 || canPaneConnectToBlock4 || canPaneConnectToBlock || canPaneConnectToBlock2)) {
            if (canPaneConnectToBlock3) {
                n = 0.0f;
                "".length();
                if (3 == 1) {
                    throw null;
                }
            }
            else if (canPaneConnectToBlock4) {
                n2 = 1.0f;
                "".length();
                if (0 == 3) {
                    throw null;
                }
            }
        }
        else {
            n = 0.0f;
            n2 = 1.0f;
        }
        if ((!canPaneConnectToBlock || !canPaneConnectToBlock2) && (canPaneConnectToBlock3 || canPaneConnectToBlock4 || canPaneConnectToBlock || canPaneConnectToBlock2)) {
            if (canPaneConnectToBlock) {
                n3 = 0.0f;
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            else if (canPaneConnectToBlock2) {
                n4 = 1.0f;
                "".length();
                if (4 <= 1) {
                    throw null;
                }
            }
        }
        else {
            n3 = 0.0f;
            n4 = 1.0f;
        }
        this.setBlockBounds(n, 0.0f, n3, n2, 1.0f, n4);
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT_MIPPED;
    }
    
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
            if (2 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[0x3 ^ 0x7];
        array["".length()] = BlockPane.NORTH;
        array[" ".length()] = BlockPane.EAST;
        array["  ".length()] = BlockPane.WEST;
        array["   ".length()] = BlockPane.SOUTH;
        return new BlockState(this, array);
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        int n;
        if (blockAccess.getBlockState(blockPos).getBlock() == this) {
            n = "".length();
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        else {
            n = (super.shouldSideBeRendered(blockAccess, blockPos, enumFacing) ? 1 : 0);
        }
        return n != 0;
    }
    
    @Override
    public void addCollisionBoxesToList(final World world, final BlockPos blockPos, final IBlockState blockState, final AxisAlignedBB axisAlignedBB, final List<AxisAlignedBB> list, final Entity entity) {
        final boolean canPaneConnectToBlock = this.canPaneConnectToBlock(world.getBlockState(blockPos.north()).getBlock());
        final boolean canPaneConnectToBlock2 = this.canPaneConnectToBlock(world.getBlockState(blockPos.south()).getBlock());
        final boolean canPaneConnectToBlock3 = this.canPaneConnectToBlock(world.getBlockState(blockPos.west()).getBlock());
        final boolean canPaneConnectToBlock4 = this.canPaneConnectToBlock(world.getBlockState(blockPos.east()).getBlock());
        if ((!canPaneConnectToBlock3 || !canPaneConnectToBlock4) && (canPaneConnectToBlock3 || canPaneConnectToBlock4 || canPaneConnectToBlock || canPaneConnectToBlock2)) {
            if (canPaneConnectToBlock3) {
                this.setBlockBounds(0.0f, 0.0f, 0.4375f, 0.5f, 1.0f, 0.5625f);
                super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
                "".length();
                if (false) {
                    throw null;
                }
            }
            else if (canPaneConnectToBlock4) {
                this.setBlockBounds(0.5f, 0.0f, 0.4375f, 1.0f, 1.0f, 0.5625f);
                super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
                "".length();
                if (0 == 2) {
                    throw null;
                }
            }
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.4375f, 1.0f, 1.0f, 0.5625f);
            super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        }
        if ((!canPaneConnectToBlock || !canPaneConnectToBlock2) && (canPaneConnectToBlock3 || canPaneConnectToBlock4 || canPaneConnectToBlock || canPaneConnectToBlock2)) {
            if (canPaneConnectToBlock) {
                this.setBlockBounds(0.4375f, 0.0f, 0.0f, 0.5625f, 1.0f, 0.5f);
                super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
                "".length();
                if (4 == 1) {
                    throw null;
                }
            }
            else if (canPaneConnectToBlock2) {
                this.setBlockBounds(0.4375f, 0.0f, 0.5f, 0.5625f, 1.0f, 1.0f);
                super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
        }
        else {
            this.setBlockBounds(0.4375f, 0.0f, 0.0f, 0.5625f, 1.0f, 1.0f);
            super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        }
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    @Override
    public IBlockState getActualState(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        return blockState.withProperty((IProperty<Comparable>)BlockPane.NORTH, this.canPaneConnectToBlock(blockAccess.getBlockState(blockPos.north()).getBlock())).withProperty((IProperty<Comparable>)BlockPane.SOUTH, this.canPaneConnectToBlock(blockAccess.getBlockState(blockPos.south()).getBlock())).withProperty((IProperty<Comparable>)BlockPane.WEST, this.canPaneConnectToBlock(blockAccess.getBlockState(blockPos.west()).getBlock())).withProperty((IProperty<Comparable>)BlockPane.EAST, this.canPaneConnectToBlock(blockAccess.getBlockState(blockPos.east()).getBlock()));
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return "".length();
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        Item itemDropped;
        if (!this.canDrop) {
            itemDropped = null;
            "".length();
            if (4 == 1) {
                throw null;
            }
        }
        else {
            itemDropped = super.getItemDropped(blockState, random, n);
        }
        return itemDropped;
    }
    
    private static void I() {
        (I = new String[0x23 ^ 0x27])["".length()] = I(":;=\u0003\u001a", "TTOwr");
        BlockPane.I[" ".length()] = I("=\u0013\u0014%", "XrgQr");
        BlockPane.I["  ".length()] = I("\u0007\u000e\r9\u0011", "taxMy");
        BlockPane.I["   ".length()] = I("\u000f\u0007\u001e?", "xbmKD");
    }
    
    @Override
    protected boolean canSilkHarvest() {
        return " ".length() != 0;
    }
    
    static {
        I();
        NORTH = PropertyBool.create(BlockPane.I["".length()]);
        EAST = PropertyBool.create(BlockPane.I[" ".length()]);
        SOUTH = PropertyBool.create(BlockPane.I["  ".length()]);
        WEST = PropertyBool.create(BlockPane.I["   ".length()]);
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public final boolean canPaneConnectToBlock(final Block block) {
        if (!block.isFullBlock() && block != this && block != Blocks.glass && block != Blocks.stained_glass && block != Blocks.stained_glass_pane && !(block instanceof BlockPane)) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    protected BlockPane(final Material material, final boolean canDrop) {
        super(material);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockPane.NORTH, "".length() != 0).withProperty((IProperty<Comparable>)BlockPane.EAST, "".length() != 0).withProperty((IProperty<Comparable>)BlockPane.SOUTH, "".length() != 0).withProperty((IProperty<Comparable>)BlockPane.WEST, (boolean)("".length() != 0)));
        this.canDrop = canDrop;
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
}
