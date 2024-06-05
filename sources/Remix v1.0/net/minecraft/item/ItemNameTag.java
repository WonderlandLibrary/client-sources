package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;

public class ItemNameTag extends Item
{
    public ItemNameTag() {
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    
    @Override
    public boolean itemInteractionForEntity(final ItemStack stack, final EntityPlayer playerIn, final EntityLivingBase target) {
        if (!stack.hasDisplayName()) {
            return false;
        }
        if (target instanceof EntityLiving) {
            final EntityLiving var4 = (EntityLiving)target;
            var4.setCustomNameTag(stack.getDisplayName());
            var4.enablePersistence();
            --stack.stackSize;
            return true;
        }
        return super.itemInteractionForEntity(stack, playerIn, target);
    }
}
