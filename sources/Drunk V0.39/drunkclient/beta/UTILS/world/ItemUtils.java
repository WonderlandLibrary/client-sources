/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.UTILS.world;

import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;

public class ItemUtils {
    protected static Minecraft mc = Minecraft.getMinecraft();

    public static boolean isInventoryFull() {
        int index = 9;
        while (index <= 44) {
            ItemStack stack = Minecraft.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack == null) {
                return false;
            }
            ++index;
        }
        return true;
    }

    public static boolean isBad(ItemStack item) {
        if (item.getItem() instanceof ItemArmor) return false;
        if (item.getItem() instanceof ItemTool) return false;
        if (item.getItem() instanceof ItemBlock) return false;
        if (item.getItem() instanceof ItemSword) return false;
        if (item.getItem() instanceof ItemEnderPearl) return false;
        if (item.getItem() instanceof ItemFood) return false;
        if (item.getItem() instanceof ItemPotion) {
            if (!ItemUtils.isBadPotion(item)) return false;
        }
        if (item.getDisplayName().toLowerCase().contains((Object)((Object)EnumChatFormatting.GRAY) + "(right click)")) return false;
        return true;
    }

    public static int getSwordSlot() {
        if (Minecraft.thePlayer == null) {
            return -1;
        }
        int bestSword = -1;
        float bestDamage = 1.0f;
        int i = 9;
        while (i < 45) {
            if (Minecraft.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack item = Minecraft.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (item != null && item.getItem() instanceof ItemSword) {
                    ItemSword is = (ItemSword)item.getItem();
                    float damage = is.getDamageVsEntity();
                    if ((damage += (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, item) * 1.26f + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, item) * 0.01f) > bestDamage) {
                        bestDamage = damage;
                        bestSword = i;
                    }
                }
            }
            ++i;
        }
        return bestSword;
    }

    public static int getEmptyHotbarSlot() {
        int k = 0;
        while (k < 9) {
            if (Minecraft.thePlayer.inventory.mainInventory[k] == null) {
                return k;
            }
            ++k;
        }
        return -1;
    }

    public static int getPickaxeSlot() {
        if (Minecraft.thePlayer == null) {
            return -1;
        }
        int bestSword = -1;
        float bestDamage = 1.0f;
        int i = 9;
        while (i < 45) {
            if (Minecraft.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemPickaxe is;
                float damage;
                ItemStack item = Minecraft.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (item != null && item.getItem() instanceof ItemPickaxe && (damage = (is = (ItemPickaxe)item.getItem()).getStrVsBlock(item, Block.getBlockById(4))) > bestDamage) {
                    bestDamage = damage;
                    bestSword = i;
                }
            }
            ++i;
        }
        return bestSword;
    }

    public static int getAxeSlot() {
        if (Minecraft.thePlayer == null) {
            return -1;
        }
        int bestSword = -1;
        float bestDamage = 1.0f;
        int i = 9;
        while (i < 45) {
            if (Minecraft.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemAxe is;
                float damage;
                ItemStack item = Minecraft.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (item != null && item.getItem() instanceof ItemAxe && (damage = (is = (ItemAxe)item.getItem()).getStrVsBlock(item, Block.getBlockById(17))) > bestDamage) {
                    bestDamage = damage;
                    bestSword = i;
                }
            }
            ++i;
        }
        return bestSword;
    }

    public static int getShovelSlot() {
        if (Minecraft.thePlayer == null) {
            return -1;
        }
        int bestSword = -1;
        float bestDamage = 1.0f;
        int i = 9;
        while (i < 45) {
            if (Minecraft.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                float damage;
                ItemTool is;
                ItemStack item = Minecraft.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (item != null && item.getItem() instanceof ItemTool && ItemUtils.isShovel(is = (ItemTool)item.getItem()) && (damage = is.getStrVsBlock(item, Block.getBlockById(3))) > bestDamage) {
                    bestDamage = damage;
                    bestSword = i;
                }
            }
            ++i;
        }
        return bestSword;
    }

    public static boolean isShovel(Item is) {
        if (Item.getItemById(256) == is) return true;
        if (Item.getItemById(269) == is) return true;
        if (Item.getItemById(273) == is) return true;
        if (Item.getItemById(277) == is) return true;
        if (Item.getItemById(284) == is) return true;
        return false;
    }

    public static void shiftClick(int k) {
        PlayerControllerMP playerControllerMP = Minecraft.getMinecraft().playerController;
        Minecraft.getMinecraft();
        int n = Minecraft.thePlayer.inventoryContainer.windowId;
        Minecraft.getMinecraft();
        playerControllerMP.windowClick(n, k, 0, 1, Minecraft.thePlayer);
    }

    public static boolean isBadPotion(ItemStack stack) {
        PotionEffect effect;
        if (stack == null) return false;
        if (!(stack.getItem() instanceof ItemPotion)) return false;
        ItemPotion potion = (ItemPotion)stack.getItem();
        if (!ItemPotion.isSplash(stack.getItemDamage())) return false;
        Iterator<PotionEffect> iterator = potion.getEffects(stack).iterator();
        do {
            if (!iterator.hasNext()) return false;
            PotionEffect o = iterator.next();
            effect = o;
            if (effect.getPotionID() == Potion.poison.getId()) return true;
            if (effect.getPotionID() == Potion.harm.getId()) return true;
            if (effect.getPotionID() == Potion.moveSlowdown.getId()) return true;
        } while (effect.getPotionID() != Potion.weakness.getId());
        return true;
    }
}

