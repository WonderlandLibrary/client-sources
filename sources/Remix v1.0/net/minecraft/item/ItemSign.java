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
    public ItemSign() {
        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public boolean onItemUse(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (side == EnumFacing.DOWN) {
            return false;
        }
        if (!worldIn.getBlockState(pos).getBlock().getMaterial().isSolid()) {
            return false;
        }
        pos = pos.offset(side);
        if (!playerIn.func_175151_a(pos, side, stack)) {
            return false;
        }
        if (!Blocks.standing_sign.canPlaceBlockAt(worldIn, pos)) {
            return false;
        }
        if (worldIn.isRemote) {
            return true;
        }
        if (side == EnumFacing.UP) {
            final int var9 = MathHelper.floor_double((playerIn.rotationYaw + 180.0f) * 16.0f / 360.0f + 0.5) & 0xF;
            worldIn.setBlockState(pos, Blocks.standing_sign.getDefaultState().withProperty(BlockStandingSign.ROTATION_PROP, var9), 3);
        }
        else {
            worldIn.setBlockState(pos, Blocks.wall_sign.getDefaultState().withProperty(BlockWallSign.field_176412_a, side), 3);
        }
        --stack.stackSize;
        final TileEntity var10 = worldIn.getTileEntity(pos);
        if (var10 instanceof TileEntitySign && !ItemBlock.setTileEntityNBT(worldIn, pos, stack)) {
            playerIn.func_175141_a((TileEntitySign)var10);
        }
        return true;
    }
}
