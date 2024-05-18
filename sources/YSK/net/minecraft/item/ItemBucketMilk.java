package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.stats.*;
import net.minecraft.init.*;

public class ItemBucketMilk extends Item
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
            if (2 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getMaxItemUseDuration(final ItemStack itemStack) {
        return 0x59 ^ 0x79;
    }
    
    @Override
    public EnumAction getItemUseAction(final ItemStack itemStack) {
        return EnumAction.DRINK;
    }
    
    public ItemBucketMilk() {
        this.setMaxStackSize(" ".length());
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        entityPlayer.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
        return itemStack;
    }
    
    @Override
    public ItemStack onItemUseFinish(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (!entityPlayer.capabilities.isCreativeMode) {
            itemStack.stackSize -= " ".length();
        }
        if (!world.isRemote) {
            entityPlayer.clearActivePotions();
        }
        entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        ItemStack itemStack2;
        if (itemStack.stackSize <= 0) {
            itemStack2 = new ItemStack(Items.bucket);
            "".length();
            if (2 < 0) {
                throw null;
            }
        }
        else {
            itemStack2 = itemStack;
        }
        return itemStack2;
    }
}
