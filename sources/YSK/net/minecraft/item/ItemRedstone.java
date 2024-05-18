package net.minecraft.item;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.creativetab.*;

public class ItemRedstone extends Item
{
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        BlockPos offset;
        if (world.getBlockState(blockPos).getBlock().isReplaceable(world, blockPos)) {
            offset = blockPos;
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else {
            offset = blockPos.offset(enumFacing);
        }
        final BlockPos blockPos2 = offset;
        if (!entityPlayer.canPlayerEdit(blockPos2, enumFacing, itemStack)) {
            return "".length() != 0;
        }
        if (!world.canBlockBePlaced(world.getBlockState(blockPos2).getBlock(), blockPos2, "".length() != 0, enumFacing, null, itemStack)) {
            return "".length() != 0;
        }
        if (Blocks.redstone_wire.canPlaceBlockAt(world, blockPos2)) {
            itemStack.stackSize -= " ".length();
            world.setBlockState(blockPos2, Blocks.redstone_wire.getDefaultState());
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
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ItemRedstone() {
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
}
