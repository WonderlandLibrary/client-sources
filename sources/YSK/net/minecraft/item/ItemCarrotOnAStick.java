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
    @Override
    public boolean shouldRotateAroundWhenRendering() {
        return " ".length() != 0;
    }
    
    public ItemCarrotOnAStick() {
        this.setCreativeTab(CreativeTabs.tabTransport);
        this.setMaxStackSize(" ".length());
        this.setMaxDamage(0x54 ^ 0x4D);
    }
    
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
            if (3 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (entityPlayer.isRiding() && entityPlayer.ridingEntity instanceof EntityPig) {
            final EntityPig entityPig = (EntityPig)entityPlayer.ridingEntity;
            if (entityPig.getAIControlledByPlayer().isControlledByPlayer() && itemStack.getMaxDamage() - itemStack.getMetadata() >= (0x51 ^ 0x56)) {
                entityPig.getAIControlledByPlayer().boostSpeed();
                itemStack.damageItem(0x5A ^ 0x5D, entityPlayer);
                if (itemStack.stackSize == 0) {
                    final ItemStack itemStack2 = new ItemStack(Items.fishing_rod);
                    itemStack2.setTagCompound(itemStack.getTagCompound());
                    return itemStack2;
                }
            }
        }
        entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return itemStack;
    }
    
    @Override
    public boolean isFull3D() {
        return " ".length() != 0;
    }
}
