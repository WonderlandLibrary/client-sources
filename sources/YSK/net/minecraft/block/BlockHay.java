package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;

public class BlockHay extends BlockRotatedPillar
{
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockHay.AXIS;
        return new BlockState(this, array);
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
            if (2 != 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int length = "".length();
        final EnumFacing.Axis axis = blockState.getValue(BlockHay.AXIS);
        if (axis == EnumFacing.Axis.X) {
            length |= (0x39 ^ 0x3D);
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        else if (axis == EnumFacing.Axis.Z) {
            length |= (0x50 ^ 0x58);
        }
        return length;
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return super.onBlockPlaced(world, blockPos, enumFacing, n, n2, n3, n4, entityLivingBase).withProperty(BlockHay.AXIS, enumFacing.getAxis());
    }
    
    public BlockHay() {
        super(Material.grass, MapColor.yellowColor);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockHay.AXIS, EnumFacing.Axis.Y));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    protected ItemStack createStackedBlock(final IBlockState blockState) {
        return new ItemStack(Item.getItemFromBlock(this), " ".length(), "".length());
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        EnumFacing.Axis axis = EnumFacing.Axis.Y;
        final int n2 = n & (0x25 ^ 0x29);
        if (n2 == (0x2E ^ 0x2A)) {
            axis = EnumFacing.Axis.X;
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        else if (n2 == (0x24 ^ 0x2C)) {
            axis = EnumFacing.Axis.Z;
        }
        return this.getDefaultState().withProperty(BlockHay.AXIS, axis);
    }
}
