package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;

public class ItemSign extends Item
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
            if (4 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ItemSign() {
        this.maxStackSize = (0xD3 ^ 0xC3);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, BlockPos offset, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (enumFacing == EnumFacing.DOWN) {
            return "".length() != 0;
        }
        if (!world.getBlockState(offset).getBlock().getMaterial().isSolid()) {
            return "".length() != 0;
        }
        offset = offset.offset(enumFacing);
        if (!entityPlayer.canPlayerEdit(offset, enumFacing, itemStack)) {
            return "".length() != 0;
        }
        if (!Blocks.standing_sign.canPlaceBlockAt(world, offset)) {
            return "".length() != 0;
        }
        if (world.isRemote) {
            return " ".length() != 0;
        }
        if (enumFacing == EnumFacing.UP) {
            world.setBlockState(offset, Blocks.standing_sign.getDefaultState().withProperty((IProperty<Comparable>)BlockStandingSign.ROTATION, MathHelper.floor_double((entityPlayer.rotationYaw + 180.0f) * 16.0f / 360.0f + 0.5) & (0x4B ^ 0x44)), "   ".length());
            "".length();
            if (4 == -1) {
                throw null;
            }
        }
        else {
            world.setBlockState(offset, Blocks.wall_sign.getDefaultState().withProperty((IProperty<Comparable>)BlockWallSign.FACING, enumFacing), "   ".length());
        }
        itemStack.stackSize -= " ".length();
        final TileEntity tileEntity = world.getTileEntity(offset);
        if (tileEntity instanceof TileEntitySign && !ItemBlock.setTileEntityNBT(world, entityPlayer, offset, itemStack)) {
            entityPlayer.openEditSign((TileEntitySign)tileEntity);
        }
        return " ".length() != 0;
    }
}
