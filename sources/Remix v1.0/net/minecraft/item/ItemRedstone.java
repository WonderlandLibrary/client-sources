package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.block.*;

public class ItemRedstone extends Item
{
    public ItemRedstone() {
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    public boolean onItemUse(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        final boolean var9 = worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos);
        final BlockPos var10 = var9 ? pos : pos.offset(side);
        if (!playerIn.func_175151_a(var10, side, stack)) {
            return false;
        }
        final Block var11 = worldIn.getBlockState(var10).getBlock();
        if (!worldIn.canBlockBePlaced(var11, var10, false, side, null, stack)) {
            return false;
        }
        if (Blocks.redstone_wire.canPlaceBlockAt(worldIn, var10)) {
            --stack.stackSize;
            worldIn.setBlockState(var10, Blocks.redstone_wire.getDefaultState());
            return true;
        }
        return false;
    }
}
