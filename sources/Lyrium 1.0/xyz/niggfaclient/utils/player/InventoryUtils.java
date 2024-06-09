// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.utils.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBucket;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import xyz.niggfaclient.utils.Utils;

public class InventoryUtils extends Utils
{
    public static boolean isValid(final ItemStack stack) {
        if (stack == null) {
            return false;
        }
        if (stack.getItem() instanceof ItemBlock) {
            return isGoodBlockStack(stack);
        }
        return isGoodItem(stack);
    }
    
    public static boolean isGoodBlockStack(final ItemStack stack) {
        return stack.stackSize >= 1 && isValidBlock(Block.getBlockFromItem(stack.getItem()), true);
    }
    
    public static boolean isValidBlock(final Block block, final boolean toPlace) {
        return !(block instanceof BlockContainer) && block.isFullBlock() && block.isFullCube() && (!toPlace || !(block instanceof BlockFalling)) && !block.getMaterial().isLiquid() && block.getMaterial().isSolid();
    }
    
    public static boolean isGoodItem(final ItemStack stack) {
        final Item item = stack.getItem();
        return (!(item instanceof ItemBucket) || ((ItemBucket)item).isFull == Blocks.flowing_water) && !(item instanceof ItemExpBottle) && !(item instanceof ItemFishingRod) && !(item instanceof ItemEgg) && !(item instanceof ItemSnowball) && !(item instanceof ItemSkull) && !(item instanceof ItemBucket);
    }
    
    public static boolean isBestArmor(final ItemStack stack, final int type) {
        final float prot = getProtection(stack);
        String strType = "";
        if (type == 1) {
            strType = "helmet";
        }
        else if (type == 2) {
            strType = "chestplate";
        }
        else if (type == 3) {
            strType = "leggings";
        }
        else if (type == 4) {
            strType = "boots";
        }
        if (!stack.getUnlocalizedName().contains(strType)) {
            return false;
        }
        for (int i = 5; i < 45; ++i) {
            if (InventoryUtils.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = InventoryUtils.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (getProtection(is) > prot && is.getUnlocalizedName().contains(strType)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static float getProtection(final ItemStack stack) {
        float prot = 0.0f;
        if (stack.getItem() instanceof ItemArmor) {
            final ItemArmor armor = (ItemArmor)stack.getItem();
            prot += (float)(armor.damageReduceAmount + (100 - armor.damageReduceAmount) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack) * 0.0075);
            prot += (float)(EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, stack) / 100.0);
            prot += (float)(EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, stack) / 100.0);
            prot += (float)(EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack) / 100.0);
            prot += (float)(EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack) / 50.0);
        }
        return prot;
    }
    
    public static void shiftClick(final int slot) {
        InventoryUtils.mc.playerController.windowClick(InventoryUtils.mc.thePlayer.inventoryContainer.windowId, slot, 0, 1, InventoryUtils.mc.thePlayer);
    }
    
    public static void swap(final int slot1, final int hotbarSlot) {
        InventoryUtils.mc.playerController.windowClick(InventoryUtils.mc.thePlayer.inventoryContainer.windowId, slot1, hotbarSlot, 2, InventoryUtils.mc.thePlayer);
    }
    
    public static void drop(final int slot) {
        InventoryUtils.mc.playerController.windowClick(InventoryUtils.mc.thePlayer.inventoryContainer.windowId, slot, 1, 4, InventoryUtils.mc.thePlayer);
    }
}
