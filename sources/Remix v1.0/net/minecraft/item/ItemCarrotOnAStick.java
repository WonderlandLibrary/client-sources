package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.stats.*;

public class ItemCarrotOnAStick extends Item
{
    public ItemCarrotOnAStick() {
        this.setCreativeTab(CreativeTabs.tabTransport);
        this.setMaxStackSize(1);
        this.setMaxDamage(25);
    }
    
    @Override
    public boolean isFull3D() {
        return true;
    }
    
    @Override
    public boolean shouldRotateAroundWhenRendering() {
        return true;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
        if (playerIn.isRiding() && playerIn.ridingEntity instanceof EntityPig) {
            final EntityPig var4 = (EntityPig)playerIn.ridingEntity;
            if (var4.getAIControlledByPlayer().isControlledByPlayer() && itemStackIn.getMaxDamage() - itemStackIn.getMetadata() >= 7) {
                var4.getAIControlledByPlayer().boostSpeed();
                itemStackIn.damageItem(7, playerIn);
                if (itemStackIn.stackSize == 0) {
                    final ItemStack var5 = new ItemStack(Items.fishing_rod);
                    var5.setTagCompound(itemStackIn.getTagCompound());
                    return var5;
                }
            }
        }
        playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return itemStackIn;
    }
}
