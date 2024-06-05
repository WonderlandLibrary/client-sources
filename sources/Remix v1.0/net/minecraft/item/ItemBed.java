package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;

public class ItemBed extends Item
{
    public ItemBed() {
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public boolean onItemUse(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (worldIn.isRemote) {
            return true;
        }
        if (side != EnumFacing.UP) {
            return false;
        }
        final IBlockState var9 = worldIn.getBlockState(pos);
        final Block var10 = var9.getBlock();
        final boolean var11 = var10.isReplaceable(worldIn, pos);
        if (!var11) {
            pos = pos.offsetUp();
        }
        final int var12 = MathHelper.floor_double(playerIn.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
        final EnumFacing var13 = EnumFacing.getHorizontal(var12);
        final BlockPos var14 = pos.offset(var13);
        final boolean var15 = var10.isReplaceable(worldIn, var14);
        final boolean var16 = worldIn.isAirBlock(pos) || var11;
        final boolean var17 = worldIn.isAirBlock(var14) || var15;
        if (!playerIn.func_175151_a(pos, side, stack) || !playerIn.func_175151_a(var14, side, stack)) {
            return false;
        }
        if (var16 && var17 && World.doesBlockHaveSolidTopSurface(worldIn, pos.offsetDown()) && World.doesBlockHaveSolidTopSurface(worldIn, var14.offsetDown())) {
            final int var18 = var13.getHorizontalIndex();
            final IBlockState var19 = Blocks.bed.getDefaultState().withProperty(BlockBed.OCCUPIED_PROP, false).withProperty(BlockBed.AGE, var13).withProperty(BlockBed.PART_PROP, BlockBed.EnumPartType.FOOT);
            if (worldIn.setBlockState(pos, var19, 3)) {
                final IBlockState var20 = var19.withProperty(BlockBed.PART_PROP, BlockBed.EnumPartType.HEAD);
                worldIn.setBlockState(var14, var20, 3);
            }
            --stack.stackSize;
            return true;
        }
        return false;
    }
}
