package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import com.google.common.base.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;

public class BlockEndPortalFrame extends Block
{
    public static final PropertyBool EYE;
    public static final PropertyDirection FACING;
    private static final String[] I;
    
    public BlockEndPortalFrame() {
        super(Material.rock, MapColor.greenColor);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockEndPortalFrame.FACING, EnumFacing.NORTH).withProperty((IProperty<Comparable>)BlockEndPortalFrame.EYE, (boolean)("".length() != 0)));
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
            if (4 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = "".length() | blockState.getValue((IProperty<EnumFacing>)BlockEndPortalFrame.FACING).getHorizontalIndex();
        if (blockState.getValue((IProperty<Boolean>)BlockEndPortalFrame.EYE)) {
            n |= (0x7D ^ 0x79);
        }
        return n;
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.8125f, 1.0f);
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty["  ".length()];
        array["".length()] = BlockEndPortalFrame.FACING;
        array[" ".length()] = BlockEndPortalFrame.EYE;
        return new BlockState(this, array);
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return " ".length() != 0;
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\n\u0012/\u0010\u0004\u000b", "lsLyj");
        BlockEndPortalFrame.I[" ".length()] = I("#\u0014\u000f", "FmjEm");
    }
    
    @Override
    public void addCollisionBoxesToList(final World world, final BlockPos blockPos, final IBlockState blockState, final AxisAlignedBB axisAlignedBB, final List<AxisAlignedBB> list, final Entity entity) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.8125f, 1.0f);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        if (world.getBlockState(blockPos).getValue((IProperty<Boolean>)BlockEndPortalFrame.EYE)) {
            this.setBlockBounds(0.3125f, 0.8125f, 0.3125f, 0.6875f, 1.0f, 0.6875f);
            super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        }
        this.setBlockBoundsForItemRender();
    }
    
    static {
        I();
        FACING = PropertyDirection.create(BlockEndPortalFrame.I["".length()], (Predicate<EnumFacing>)EnumFacing.Plane.HORIZONTAL);
        EYE = PropertyBool.create(BlockEndPortalFrame.I[" ".length()]);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return null;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    @Override
    public int getComparatorInputOverride(final World world, final BlockPos blockPos) {
        int length;
        if (world.getBlockState(blockPos).getValue((IProperty<Boolean>)BlockEndPortalFrame.EYE)) {
            length = (0x26 ^ 0x29);
            "".length();
            if (!true) {
                throw null;
            }
        }
        else {
            length = "".length();
        }
        return length;
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockEndPortalFrame.FACING, entityLivingBase.getHorizontalFacing().getOpposite()).withProperty((IProperty<Comparable>)BlockEndPortalFrame.EYE, "".length() != 0);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final IBlockState defaultState = this.getDefaultState();
        final PropertyBool eye = BlockEndPortalFrame.EYE;
        int n2;
        if ((n & (0x93 ^ 0x97)) != 0x0) {
            n2 = " ".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        return defaultState.withProperty((IProperty<Comparable>)eye, n2 != 0).withProperty((IProperty<Comparable>)BlockEndPortalFrame.FACING, EnumFacing.getHorizontal(n & "   ".length()));
    }
}
