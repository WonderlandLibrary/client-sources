package net.minecraft.item;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;

public class ItemSoup extends ItemFood
{
    public ItemSoup(final int p_i45330_1_) {
        super(p_i45330_1_, false);
        this.setMaxStackSize(1);
    }
    
    @Override
    public ItemStack onItemUseFinish(final ItemStack stack, final World worldIn, final EntityPlayer playerIn) {
        super.onItemUseFinish(stack, worldIn, playerIn);
        return new ItemStack(Items.bowl);
    }
}
