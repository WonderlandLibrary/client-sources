package me.xatzdevelopments.modules.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventMotion;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.settings.BooleanSetting;
import me.xatzdevelopments.settings.NumberSetting;
import me.xatzdevelopments.util.Timer2;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFlower;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;

public class InventoryManager extends Module
{
    private static int bestSwordSlot;
    private static int bestPickaxeSlot;
    private static int bestBowSlot;
    private static int bestBlockSlot;
    private static int[] bestArmorDamageReducment;
    private static int[] bestArmorSlot;
    private List<Integer> allSwords;
    private List<Integer> allBows;
    private List<Integer> allPickaxes;
    private List<Integer>[] allArmors;
    private List<Integer> trash;
    private Timer2 delayTimer;
    private Timer2 startDelayTimer;
    public NumberSetting minDelay;
    public NumberSetting MaxDelay;
    public NumberSetting startDelay;
    public BooleanSetting Sort;
    public BooleanSetting AutoArmor;
    public BooleanSetting random;
    
    public InventoryManager() {
        super("InvManager", 38, Category.PLAYER, null);
        this.allSwords = new ArrayList<Integer>();
        this.allBows = new ArrayList<Integer>();
        this.allPickaxes = new ArrayList<Integer>();
        this.allArmors = (List<Integer>[])new List[4];
        this.trash = new ArrayList<Integer>();
        this.delayTimer = new Timer2();
        this.startDelayTimer = new Timer2();
        this.minDelay = new NumberSetting("Min Delay", 10.0, 0.0, 99.0, 10.0);
        this.MaxDelay = new NumberSetting("Max Delay", 20.0, 1.0, 100.0, 10.0);
        this.startDelay = new NumberSetting("StartDelay", 20.0, 0.0, 100.0, 10.0);
        this.Sort = new BooleanSetting("Sort", true);
        this.AutoArmor = new BooleanSetting("Auto Armor", true);
        this.random = new BooleanSetting("Random", true);
        this.addSettings(this.minDelay, this.MaxDelay, this.startDelay, this.Sort, this.AutoArmor, this.random);
    }
    
  /*  @Override
    public void onEvent(final Event e) {
        if (e instanceof EventMotion && e.isPost()) {
            if (this.minDelay.getValue() > this.MaxDelay.getValue()) {
                this.minDelay.setValue(this.minDelay.getValue() - 10.0);
            }
            this.searchForItems();
            this.searchForBestArmor();
            this.searchForTrash();
            for (final int slot : this.trash) {
                if (this.hasNoDelay(slot)) {
                    return;
                }
                this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, (slot < 9) ? (slot + 36) : slot, 1, 4, this.mc.thePlayer);
                Timer2.Reset();
            }
            if (this.AutoArmor.isEnabled()) {
                for (int i = 0; i < 4; ++i) {
                    if (InventoryManager.bestArmorSlot[i] != -1) {
                        final int bestSlot = InventoryManager.bestArmorSlot[i];
                        final ItemStack oldArmor = this.mc.thePlayer.inventory.armorItemInSlot(i);
                        if (this.hasNoDelay(i)) {
                            return;
                        }
                        if (oldArmor != null && oldArmor.getItem() != null) {
                            this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, 8 - i, 0, 4, this.mc.thePlayer);
                            Timer2.Reset();
                        }
                        final int slot2 = (bestSlot < 9) ? (bestSlot + 36) : bestSlot;
                        if (this.hasNoDelay(slot2)) {
                            return;
                        }
                        this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, slot2, 0, 1, this.mc.thePlayer);
                        Timer2.Reset();
                    }
                }
            }
            if (this.Sort.isEnabled()) {
                final int switchSlotSword = 0;
                final int switchSlotBow = 1;
                final int switchSlotPickaxe = 2;
                final int switchSlotBlock = 8;
                if (InventoryManager.bestSwordSlot != -1 && InventoryManager.bestSwordSlot != switchSlotSword) {
                    final int slot3 = (InventoryManager.bestSwordSlot < 9) ? (InventoryManager.bestSwordSlot + 36) : InventoryManager.bestSwordSlot;
                    if (this.hasNoDelay(slot3) && slot3 != switchSlotSword) {
                        return;
                    }
                    this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, slot3, switchSlotSword, 2, this.mc.thePlayer);
                    Timer2.Reset();
                }
                if (InventoryManager.bestBowSlot != -1 && InventoryManager.bestBowSlot != switchSlotBow) {
                    final int slot3 = (InventoryManager.bestBowSlot < 9) ? (InventoryManager.bestBowSlot + 36) : InventoryManager.bestBowSlot;
                    if (this.hasNoDelay(slot3) && slot3 != switchSlotBow) {
                        return;
                    }
                    this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, slot3, switchSlotBow, 2, this.mc.thePlayer);
                    Timer2.Reset();
                }
                if (InventoryManager.bestPickaxeSlot != -1 && InventoryManager.bestPickaxeSlot != switchSlotPickaxe) {
                    final int slot3 = (InventoryManager.bestPickaxeSlot < 9) ? (InventoryManager.bestPickaxeSlot + 36) : InventoryManager.bestPickaxeSlot;
                    if (this.hasNoDelay(slot3) && slot3 != switchSlotPickaxe) {
                        return;
                    }
                    this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, slot3, switchSlotPickaxe, 2, this.mc.thePlayer);
                    Timer2.Reset();
                }
                if (InventoryManager.bestBlockSlot != -1 && InventoryManager.bestBlockSlot != switchSlotBlock) {
                    final int slot3 = (InventoryManager.bestBlockSlot < 9) ? (InventoryManager.bestBlockSlot + 36) : InventoryManager.bestBlockSlot;
                    if (this.mc.thePlayer.inventory.getStackInSlot(switchSlotBlock) != null && this.mc.thePlayer.inventory.getStackInSlot(switchSlotBlock).getItem() instanceof ItemBlock) {
                        return;
                    }
                    if (this.hasNoDelay(slot3) && slot3 != switchSlotBlock) {
                        return;
                    }
                    this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, slot3, switchSlotBlock, 2, this.mc.thePlayer);
                    Timer2.Reset();
                }
            }
        }
    }
    
    private boolean hasNoDelay(final int slot) {
        return !Timer2.hasTimeElapsed((long)this.startDelay.getValue() * 10L, false) || !Timer2.hasTimeElapsed((long)(Math.random() * (this.MaxDelay.getValue() * 10.0 - this.minDelay.getValue() * 10.0 + 1.0) + this.minDelay.getValue() * 10.0), false) || !(this.mc.currentScreen instanceof GuiInventory);
    }
    
    private void searchForTrash() {
        this.trash.clear();
        for (int i = 0; i < this.allArmors.length; ++i) {
            final List<Integer> armorItem = this.allArmors[i];
            if (armorItem != null) {
                final int finalI = i;
                armorItem.stream().filter(slot -> slot != InventoryManager.bestArmorSlot[finalI]).forEach(this.trash::add);
            }
        }
        this.allBows.stream().filter(slot -> slot != InventoryManager.bestBowSlot).forEach(this.trash::add);
        this.allSwords.stream().filter(slot -> slot != InventoryManager.bestSwordSlot).forEach(this.trash::add);
        this.allPickaxes.stream().filter(slot -> slot != InventoryManager.bestPickaxeSlot).forEach(this.trash::add);
        if (this.random.isEnabled()) {
            Collections.shuffle(this.trash);
        }
        else {
            Collections.sort(this.trash);
        }
    }
    
    private void searchForBestArmor() {
        InventoryManager.bestArmorDamageReducment = new int[4];
        InventoryManager.bestArmorSlot = new int[4];
        Arrays.fill(InventoryManager.bestArmorDamageReducment, -1);
        Arrays.fill(InventoryManager.bestArmorSlot, -1);
        for (int i = 0; i < InventoryManager.bestArmorSlot.length; ++i) {
            final ItemStack itemStack = this.mc.thePlayer.inventory.armorItemInSlot(i);
            this.allArmors[i] = new ArrayList<Integer>();
            if (itemStack != null && itemStack.getItem() != null && itemStack.getItem() instanceof ItemArmor) {
                final ItemArmor armor = (ItemArmor)itemStack.getItem();
                final int currentProtection = armor.damageReduceAmount + EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[] { itemStack }, DamageSource.generic);
                InventoryManager.bestArmorDamageReducment[i] = currentProtection;
            }
        }
        for (int i = 0; i < 36; ++i) {
            final ItemStack itemStack = this.mc.thePlayer.inventory.getStackInSlot(i);
            if (itemStack != null) {
                if (itemStack.getItem() != null) {
                    if (itemStack.getItem() instanceof ItemArmor) {
                        final ItemArmor armor = (ItemArmor)itemStack.getItem();
                        final int armorType = 3 - armor.armorType;
                        this.allArmors[armorType].add(i);
                        final int slotProtectionLevel = armor.damageReduceAmount + EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[] { itemStack }, DamageSource.generic);
                        if (InventoryManager.bestArmorDamageReducment[armorType] < slotProtectionLevel) {
                            InventoryManager.bestArmorDamageReducment[armorType] = slotProtectionLevel;
                            InventoryManager.bestArmorSlot[armorType] = i;
                        }
                    }
                }
            }
        }
    }
    
    private void searchForItems() {
        InventoryManager.bestSwordSlot = -1;
        InventoryManager.bestBowSlot = -1;
        InventoryManager.bestPickaxeSlot = -1;
        InventoryManager.bestBlockSlot = -1;
        this.allSwords.clear();
        this.allBows.clear();
        this.allPickaxes.clear();
        float bestSwordDamage = -1.0f;
        float bestSwordDurability = -1.0f;
        float bestPickaxeEfficiency = -1.0f;
        float bestPickaxeDurability = -1.0f;
        float bestBowDurability = -1.0f;
        int bestBowDamage = -1;
        int bestBlockSize = -1;
        for (int i = 0; i < 36; ++i) {
            final ItemStack itemStack = this.mc.thePlayer.inventory.getStackInSlot(i);
            if (itemStack != null) {
                if (itemStack.getItem() != null) {
                    if (itemStack.getItem() instanceof ItemSword) {
                        final ItemSword sword = (ItemSword)itemStack.getItem();
                        final int level = EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180314_l.effectId, itemStack);
                        final float damageLevel = (float)(sword.getDamageVsEntity() + level * 1.25);
                        this.allSwords.add(i);
                        if (bestSwordDamage < damageLevel) {
                            bestSwordDamage = damageLevel;
                            bestSwordDurability = sword.getDamageVsEntity();
                            InventoryManager.bestSwordSlot = i;
                        }
                        if (damageLevel == bestSwordDamage && sword.getDamageVsEntity() < bestSwordDurability) {
                            bestSwordDurability = sword.getDamageVsEntity();
                            InventoryManager.bestSwordSlot = i;
                        }
                    }
                    if (itemStack.getItem() instanceof ItemPickaxe) {
                        final ItemPickaxe pickaxe = (ItemPickaxe)itemStack.getItem();
                        this.allPickaxes.add(i);
                        final float efficiencyLevel = this.getPickaxeEfficiency(pickaxe);
                        if (bestPickaxeEfficiency < efficiencyLevel) {
                            bestPickaxeEfficiency = efficiencyLevel;
                            bestPickaxeDurability = (float)pickaxe.getMaxDamage();
                            InventoryManager.bestPickaxeSlot = i;
                        }
                        if (efficiencyLevel == bestPickaxeEfficiency && pickaxe.getMaxDamage() < bestPickaxeDurability) {
                            bestPickaxeDurability = (float)pickaxe.getMaxDamage();
                            InventoryManager.bestPickaxeSlot = i;
                        }
                    }
                    if (itemStack.getItem() instanceof ItemBow) {
                        final ItemBow bow = (ItemBow)itemStack.getItem();
                        final int level = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemStack);
                        this.allBows.add(i);
                        if (bestBowDamage < level || (bestBowDamage == -1 && level == 0)) {
                            bestBowDamage = level;
                            bestBowDurability = (float)bow.getMaxDamage();
                            InventoryManager.bestBowSlot = i;
                        }
                        if (level == bestBowDamage && bow.getMaxDamage() < bestBowDurability) {
                            bestBowDurability = (float)bow.getMaxDamage();
                            InventoryManager.bestBowSlot = i;
                        }
                    }
                    if (itemStack.getItem() instanceof ItemBlock) {
                        final ItemBlock block = (ItemBlock)itemStack.getItem();
                        if (block.block != Blocks.web && block.block != Blocks.bed && block.block != Blocks.noteblock && block.block != Blocks.cactus && block.block != Blocks.cake && block.block != Blocks.anvil && block.block != Blocks.skull && !(block.block instanceof BlockDoor) && !(block.block instanceof BlockFlower)) {
                            if (!(block.block instanceof BlockCarpet)) {
                                if (bestBlockSize < itemStack.stackSize) {
                                    bestBlockSize = itemStack.stackSize;
                                    InventoryManager.bestBlockSlot = i;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    private float getPickaxeEfficiency(final ItemPickaxe pickaxe) {
        int level = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, new ItemStack(pickaxe));
        switch (level) {
            case 1: {
                level = 30;
                break;
            }
            case 2: {
                level = 69;
                break;
            }
            case 3: {
                level = 120;
                break;
            }
            case 4: {
                level = 186;
                break;
            }
            case 5: {
                level = 271;
                break;
            }
            default: {
                level = 0;
                break;
            }
        }
        return pickaxe.getToolMaterial().getEfficiencyOnProperMaterial() + level;
    } */
}
