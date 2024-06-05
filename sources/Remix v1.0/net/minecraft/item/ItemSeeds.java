package net.minecraft.item;

import net.minecraft.block.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public class ItemSeeds extends Item
{
    private Block field_150925_a;
    private Block soilBlockID;
    
    public ItemSeeds(final Block p_i45352_1_, final Block p_i45352_2_) {
        this.field_150925_a = p_i45352_1_;
        this.soilBlockID = p_i45352_2_;
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }
    
    @Override
    public boolean onItemUse(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (side != EnumFacing.UP) {
            return false;
        }
        if (!playerIn.func_175151_a(pos.offset(side), side, stack)) {
            return false;
        }
        if (worldIn.getBlockState(pos).getBlock() == this.soilBlockID && worldIn.isAirBlock(pos.offsetUp())) {
            worldIn.setBlockState(pos.offsetUp(), this.field_150925_a.getDefaultState());
            --stack.stackSize;
            return true;
        }
        return false;
    }
}
