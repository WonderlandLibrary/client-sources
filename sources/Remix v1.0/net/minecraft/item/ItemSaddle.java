package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;

public class ItemSaddle extends Item
{
    public ItemSaddle() {
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabTransport);
    }
    
    @Override
    public boolean itemInteractionForEntity(final ItemStack stack, final EntityPlayer playerIn, final EntityLivingBase target) {
        if (target instanceof EntityPig) {
            final EntityPig var4 = (EntityPig)target;
            if (!var4.getSaddled() && !var4.isChild()) {
                var4.setSaddled(true);
                var4.worldObj.playSoundAtEntity(var4, "mob.horse.leather", 0.5f, 1.0f);
                --stack.stackSize;
            }
            return true;
        }
        return false;
    }
    
    @Override
    public boolean hitEntity(final ItemStack stack, final EntityLivingBase target, final EntityLivingBase attacker) {
        this.itemInteractionForEntity(stack, null, target);
        return true;
    }
}
