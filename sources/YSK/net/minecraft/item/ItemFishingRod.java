package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.stats.*;

public class ItemFishingRod extends Item
{
    private static final String[] I;
    
    @Override
    public int getItemEnchantability() {
        return " ".length();
    }
    
    public ItemFishingRod() {
        this.setMaxDamage(0xEC ^ 0xAC);
        this.setMaxStackSize(" ".length());
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (entityPlayer.fishEntity != null) {
            itemStack.damageItem(entityPlayer.fishEntity.handleHookRetraction(), entityPlayer);
            entityPlayer.swingItem();
            "".length();
            if (4 == 3) {
                throw null;
            }
        }
        else {
            world.playSoundAtEntity(entityPlayer, ItemFishingRod.I["".length()], 0.5f, 0.4f / (ItemFishingRod.itemRand.nextFloat() * 0.4f + 0.8f));
            if (!world.isRemote) {
                world.spawnEntityInWorld(new EntityFishHook(world, entityPlayer));
            }
            entityPlayer.swingItem();
            entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        }
        return itemStack;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u001b\"&/\u001b\u0004m*$\u0003", "iCHKt");
    }
    
    @Override
    public boolean shouldRotateAroundWhenRendering() {
        return " ".length() != 0;
    }
    
    @Override
    public boolean isFull3D() {
        return " ".length() != 0;
    }
    
    static {
        I();
    }
    
    @Override
    public boolean isItemTool(final ItemStack itemStack) {
        return super.isItemTool(itemStack);
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
            if (0 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
}
