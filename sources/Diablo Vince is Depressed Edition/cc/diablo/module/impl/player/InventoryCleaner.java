/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.module.impl.player;

import cc.diablo.event.impl.UpdateEvent;
import cc.diablo.helpers.MathHelper;
import cc.diablo.helpers.Stopwatch;
import cc.diablo.helpers.player.InventoryUtils;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.setting.impl.BooleanSetting;
import cc.diablo.setting.impl.NumberSetting;
import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.Collections;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class InventoryCleaner
extends Module {
    public NumberSetting delay = new NumberSetting("Delay", 125.0, 1.0, 250.0, 1.0);
    public NumberSetting delayMin = new NumberSetting("Random Delay Min", 5.0, 0.0, 50.0, 1.0);
    public NumberSetting delayMax = new NumberSetting("Random Delay Max", 20.0, 0.0, 50.0, 1.0);
    public BooleanSetting openInventory = new BooleanSetting("Open Inventory", true);
    public int ticks;
    private Stopwatch timer = new Stopwatch();
    private final int[] boots = new int[]{313, 309, 317, 305, 301};
    private final int[] chestplate = new int[]{311, 307, 315, 303, 299};
    private final int[] helmet = new int[]{310, 306, 314, 302, 298};
    private final int[] leggings = new int[]{312, 308, 316, 304, 300};
    private static final int swordSlot = 0;
    private static final int pickaxeSlot = 1;
    private static final int axeSlot = 2;
    private static final int blockSlot = 3;
    private static final int gappleSlot = 4;

    public InventoryCleaner() {
        super("InvManager", "fuck up ur inv", 0, Category.Player);
        this.addSettings(this.delay, this.delayMin, this.delayMax, this.openInventory);
    }

    @Subscribe
    public void onPre(UpdateEvent event) {
        if (InventoryCleaner.mc.currentScreen instanceof GuiInventory) {
            if (this.openInventory.isChecked() && InventoryCleaner.mc.currentScreen instanceof GuiInventory) {
                this.runTheShit();
            } else if (!InventoryCleaner.mc.thePlayer.isMoving()) {
                this.runTheShit();
            }
        }
    }

    public void runTheShit() {
        ArrayList inventorySlots = Lists.newArrayList();
        inventorySlots.addAll(InventoryCleaner.mc.thePlayer.inventoryContainer.inventorySlots);
        Collections.shuffle(inventorySlots);
        for (Slot slot : inventorySlots) {
            ItemStack item = slot.getStack();
            if (item == null) continue;
            int windowId = InventoryCleaner.mc.thePlayer.inventoryContainer.windowId;
            if (!this.timer.hasReached((long)(this.delay.getVal() + (double)MathHelper.getRandInt((int)Math.round(this.delayMin.getVal()), (int)Math.round(this.delayMax.getVal())))) || !this.moveOrDropItem(slot, windowId)) continue;
            this.timer.reset();
            return;
        }
    }

    private int getProtValue(ItemStack stack) {
        if (stack != null && stack.getItem() instanceof ItemArmor) {
            int normalValue = ((ItemArmor)stack.getItem()).damageReduceAmount;
            int enchantmentValue = EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack);
            return normalValue + enchantmentValue;
        }
        return -1;
    }

    private boolean moveOrDropItem(Slot slot, int windowId) {
        boolean runBlock = true;
        boolean runGapple = true;
        ItemStack stack = slot.getStack();
        if (stack == null || stack.getItem() == null) {
            return false;
        }
        if (stack.getItem() instanceof ItemSword) {
            if (InventoryUtils.getBestSwordSlot() != null && InventoryUtils.getBestSwordSlot() == slot) {
                if (slot.slotNumber == 0) {
                    return false;
                }
                InventoryUtils.swap(slot.slotNumber, 0, windowId);
            } else {
                InventoryUtils.drop(slot.slotNumber, windowId);
            }
            return true;
        }
        if (stack.getItem() instanceof ItemPickaxe) {
            if (InventoryUtils.getBestPickaxeSlot() != null && InventoryUtils.getBestPickaxeSlot() == slot) {
                if (slot.slotNumber == 1) {
                    return false;
                }
                InventoryUtils.swap(slot.slotNumber, 1, windowId);
            } else {
                InventoryUtils.drop(slot.slotNumber, windowId);
            }
            return true;
        }
        if (stack.getItem() instanceof ItemAxe) {
            if (InventoryUtils.getBestAxeSlot() != null && InventoryUtils.getBestAxeSlot() == slot) {
                if (slot.slotNumber == 2) {
                    return false;
                }
                InventoryUtils.swap(slot.slotNumber, 2, windowId);
            } else {
                InventoryUtils.drop(slot.slotNumber, windowId);
            }
            return true;
        }
        if (stack.getItem() instanceof ItemSpade || stack.getItem() instanceof ItemHoe) {
            InventoryUtils.drop(slot.slotNumber, windowId);
            return true;
        }
        if (stack.getItem() instanceof ItemArmor) {
            if (slot.slotNumber >= 5 && slot.slotNumber <= 8) {
                return false;
            }
            for (int type = 1; type < 5; ++type) {
                ItemStack currentArmor = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(4 + type).getStack();
                if (!InventoryCleaner.isBestArmor(stack, type) || currentArmor != null && InventoryCleaner.isBestArmor(currentArmor, type)) continue;
                return false;
            }
            InventoryUtils.drop(slot.slotNumber, windowId);
            return true;
        }
        if (stack.getItem() instanceof ItemBlock) {
            int count = 0;
            for (Slot slot2 : InventoryCleaner.mc.thePlayer.inventoryContainer.inventorySlots) {
                if (slot2.getHasStack() && InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot((int)3).getStack().stackSize > slot2.getStack().stackSize) {
                    runBlock = false;
                }
                if (!(slot2.getStack().getItem() instanceof ItemBlock)) continue;
                ++count;
            }
            InventoryUtils.swap(slot.slotNumber, 3, windowId);
            if (count > 1 && runBlock) {
                InventoryUtils.click(slot.slotNumber);
                InventoryUtils.click(3);
            }
            return true;
        }
        if (Item.getIdFromItem(stack.getItem()) == 322) {
            int count = 0;
            for (Slot slot2 : InventoryCleaner.mc.thePlayer.inventoryContainer.inventorySlots) {
                if (slot2.getHasStack() && Item.getIdFromItem(slot2.getStack().getItem()) == 322 && InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot((int)4).getStack().stackSize > slot2.getStack().stackSize) {
                    runGapple = false;
                }
                if (Item.getIdFromItem(slot2.getStack().getItem()) != 322) continue;
                ++count;
            }
            InventoryUtils.swap(slot.slotNumber, 4, windowId);
            if (count > 1 && runGapple) {
                InventoryUtils.click(slot.slotNumber);
                InventoryUtils.click(4);
            }
            return true;
        }
        this.getBestArmor();
        return false;
    }

    public void getBestArmor() {
        for (int type = 1; type < 5; ++type) {
            if (InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot(4 + type).getHasStack()) {
                ItemStack item = InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot(4 + type).getStack();
                if (InventoryCleaner.isBestArmor(item, type)) continue;
                InventoryUtils.drop(4 + type, InventoryCleaner.mc.thePlayer.inventoryContainer.windowId);
                return;
            }
            for (int i = 9; i < 45; ++i) {
                ItemStack is;
                if (!InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() || !InventoryCleaner.isBestArmor(is = InventoryCleaner.mc.thePlayer.inventoryContainer.getSlot(i).getStack(), type) || !(InventoryCleaner.getProtection(is) > 0.0f)) continue;
                InventoryUtils.shiftClick(i, InventoryCleaner.mc.thePlayer.inventoryContainer.windowId);
                return;
            }
        }
    }

    public static boolean isBestArmor(ItemStack stack, int type) {
        float prot = InventoryCleaner.getProtection(stack);
        String strType = "";
        if (type == 1) {
            strType = "helmet";
        } else if (type == 2) {
            strType = "chestplate";
        } else if (type == 3) {
            strType = "leggings";
        } else if (type == 4) {
            strType = "boots";
        }
        if (!stack.getUnlocalizedName().contains(strType)) {
            return false;
        }
        for (int i = 5; i < 45; ++i) {
            ItemStack is;
            if (!Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getHasStack() || !(InventoryCleaner.getProtection(is = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getStack()) > prot) || !is.getUnlocalizedName().contains(strType)) continue;
            return false;
        }
        return true;
    }

    public static float getProtection(ItemStack stack) {
        float prot = 0.0f;
        if (stack.getItem() instanceof ItemArmor) {
            ItemArmor armor = (ItemArmor)stack.getItem();
            prot = (float)((double)(prot + (float)armor.damageReduceAmount) + (double)((100 - armor.damageReduceAmount) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack)) * 0.0075);
            prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, stack) / 100.0);
            prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, stack) / 100.0);
            prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack) / 100.0);
            prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack) / 50.0);
            prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack) / 100.0);
            prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.projectileProtection.effectId, stack) / 100.0);
        }
        return prot;
    }
}

