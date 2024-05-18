package net.minecraft.item;

import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.creativetab.*;

public class ItemBed extends Item
{
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, BlockPos up, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return " ".length() != 0;
        }
        if (enumFacing != EnumFacing.UP) {
            return "".length() != 0;
        }
        final boolean replaceable = world.getBlockState(up).getBlock().isReplaceable(world, up);
        if (!replaceable) {
            up = up.up();
        }
        final EnumFacing horizontal = EnumFacing.getHorizontal(MathHelper.floor_double(entityPlayer.rotationYaw * 4.0f / 360.0f + 0.5) & "   ".length());
        final BlockPos offset = up.offset(horizontal);
        if (!entityPlayer.canPlayerEdit(up, enumFacing, itemStack) || !entityPlayer.canPlayerEdit(offset, enumFacing, itemStack)) {
            return "".length() != 0;
        }
        final boolean replaceable2 = world.getBlockState(offset).getBlock().isReplaceable(world, offset);
        int n4;
        if (!replaceable && !world.isAirBlock(up)) {
            n4 = "".length();
            "".length();
            if (3 < 0) {
                throw null;
            }
        }
        else {
            n4 = " ".length();
        }
        final int n5 = n4;
        int n6;
        if (!replaceable2 && !world.isAirBlock(offset)) {
            n6 = "".length();
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        else {
            n6 = " ".length();
        }
        final int n7 = n6;
        if (n5 != 0 && n7 != 0 && World.doesBlockHaveSolidTopSurface(world, up.down()) && World.doesBlockHaveSolidTopSurface(world, offset.down())) {
            final IBlockState withProperty = Blocks.bed.getDefaultState().withProperty((IProperty<Comparable>)BlockBed.OCCUPIED, "".length() != 0).withProperty((IProperty<Comparable>)BlockBed.FACING, horizontal).withProperty(BlockBed.PART, BlockBed.EnumPartType.FOOT);
            if (world.setBlockState(up, withProperty, "   ".length())) {
                world.setBlockState(offset, withProperty.withProperty(BlockBed.PART, BlockBed.EnumPartType.HEAD), "   ".length());
            }
            itemStack.stackSize -= " ".length();
            return " ".length() != 0;
        }
        return "".length() != 0;
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
            if (1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ItemBed() {
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
}
