package me.satisfactory.base.utils.other;

import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.potion.*;
import net.minecraft.item.*;
import java.util.*;

public class InventoryUtils
{
    static Minecraft mc;
    
    public static void grabPot() {
        for (int i = 9; i < 36; ++i) {
            final ItemStack stack = InventoryUtils.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (stack != null && isSplashPot(stack)) {
                InventoryUtils.mc.playerController.windowClick(InventoryUtils.mc.thePlayer.openContainer.windowId, i, 1, 2, InventoryUtils.mc.thePlayer);
                break;
            }
        }
    }
    
    public static boolean hotBarHasPots() {
        for (int i = 36; i < 45; ++i) {
            final ItemStack stack = InventoryUtils.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (stack != null && isSplashPot(stack)) {
                return true;
            }
        }
        return false;
    }
    
    public static int getPotionFromInventory() {
        int pot = -1;
        for (int i = 1; i < 45; ++i) {
            if (InventoryUtils.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = InventoryUtils.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                final Item item = is.getItem();
                if (item instanceof ItemPotion) {
                    final ItemPotion potion = (ItemPotion)item;
                    if (potion.getEffects(is) != null) {
                        for (final PotionEffect effect : potion.getEffects(is)) {
                            if (effect.getPotionID() == Potion.heal.id && ItemPotion.isSplash(is.getItemDamage())) {
                                pot = i;
                            }
                        }
                    }
                }
            }
        }
        return pot;
    }
    
    public static boolean isSplashPot(final ItemStack stack) {
        if (stack != null && stack.getItem() instanceof ItemPotion) {
            final ItemPotion potion = (ItemPotion)stack.getItem();
            if (ItemPotion.isSplash(stack.getItemDamage())) {
                for (final Object o : potion.getEffects(stack)) {
                    final PotionEffect effect = (PotionEffect)o;
                    if (effect.getPotionID() == Potion.heal.getId()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    static {
        InventoryUtils.mc = Minecraft.getMinecraft();
    }
}
