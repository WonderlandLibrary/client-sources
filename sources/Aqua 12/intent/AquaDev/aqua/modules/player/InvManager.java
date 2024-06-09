// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.player;

import java.util.function.Consumer;
import net.minecraft.item.ItemTool;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemArmor;
import java.util.Arrays;
import java.util.Iterator;
import net.minecraft.item.ItemStack;
import java.util.Collections;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.gui.inventory.GuiInventory;
import events.listeners.EventClick;
import events.Event;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import java.util.ArrayList;
import intent.AquaDev.aqua.modules.Category;
import java.util.List;
import intent.AquaDev.aqua.utils.TimeUtil;
import intent.AquaDev.aqua.modules.Module;

public class InvManager extends Module
{
    private static int[] bestArmorDamageReducement;
    TimeUtil time;
    TimeUtil timeUtil;
    private int[] bestArmorSlots;
    private float bestSwordDamage;
    private int bestSwordSlot;
    private final List<Integer> trash;
    private boolean canFake;
    
    public InvManager() {
        super("InvManager", Type.Player, "InvManager", 0, Category.Player);
        this.time = new TimeUtil();
        this.timeUtil = new TimeUtil();
        this.trash = new ArrayList<Integer>();
        Aqua.setmgr.register(new Setting("Delay", this, 50.0, 0.0, 2000.0, false));
        Aqua.setmgr.register(new Setting("PrevSwords", this, true));
        Aqua.setmgr.register(new Setting("OpenInv", this, false));
        Aqua.setmgr.register(new Setting("FakeInv", this, true));
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventClick) {
            this.searchForItems();
            for (int i = 0; i < 4; ++i) {
                if (this.bestArmorSlots[i] != -1) {
                    final int bestSlot = this.bestArmorSlots[i];
                    final float DelayY = (float)Aqua.setmgr.getSetting("InvManagerDelay").getCurrentNumber();
                    final ItemStack oldArmor = InvManager.mc.thePlayer.inventory.armorItemInSlot(i);
                    if (Aqua.setmgr.getSetting("InvManagerOpenInv").isState() && InvManager.mc.currentScreen instanceof GuiInventory && !Aqua.setmgr.getSetting("InvManagerFakeInv").isState() && oldArmor != null && oldArmor.getItem() != null && this.time.hasReached((long)DelayY)) {
                        InvManager.mc.playerController.windowClick(InvManager.mc.thePlayer.inventoryContainer.windowId, 8 - i, 0, 1, InvManager.mc.thePlayer);
                        this.time.reset();
                    }
                    if (Aqua.setmgr.getSetting("InvManagerFakeInv").isState() && this.canFakeInv()) {
                        InvManager.mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(InvManager.mc.thePlayer, C0BPacketEntityAction.Action.OPEN_INVENTORY));
                        if (oldArmor != null && oldArmor.getItem() != null && this.time.hasReached((long)DelayY)) {
                            InvManager.mc.playerController.windowClick(InvManager.mc.thePlayer.inventoryContainer.windowId, 8 - i, 0, 1, InvManager.mc.thePlayer);
                            this.time.reset();
                        }
                    }
                    if (Aqua.setmgr.getSetting("InvManagerOpenInv").isState() && InvManager.mc.currentScreen instanceof GuiInventory && !Aqua.setmgr.getSetting("InvManagerFakeInv").isState() && this.time.hasReached((long)DelayY)) {
                        InvManager.mc.playerController.windowClick(InvManager.mc.thePlayer.inventoryContainer.windowId, (bestSlot < 9) ? (bestSlot + 36) : bestSlot, 0, 1, InvManager.mc.thePlayer);
                        this.time.reset();
                    }
                    if (Aqua.setmgr.getSetting("InvManagerFakeInv").isState() && this.canFakeInv()) {
                        InvManager.mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(InvManager.mc.thePlayer, C0BPacketEntityAction.Action.OPEN_INVENTORY));
                        if (this.time.hasReached((long)DelayY)) {
                            InvManager.mc.playerController.windowClick(InvManager.mc.thePlayer.inventoryContainer.windowId, (bestSlot < 9) ? (bestSlot + 36) : bestSlot, 0, 1, InvManager.mc.thePlayer);
                            this.time.reset();
                        }
                    }
                }
            }
            if (Aqua.setmgr.getSetting("InvManagerOpenInv").isState() && InvManager.mc.currentScreen instanceof GuiInventory && !Aqua.setmgr.getSetting("InvManagerFakeInv").isState() && this.bestSwordSlot != -1 && this.bestSwordDamage != -1.0f) {
                final float DelayY2 = (float)Aqua.setmgr.getSetting("InvManagerDelay").getCurrentNumber();
                if (this.time.hasReached((long)DelayY2)) {
                    InvManager.mc.playerController.windowClick(InvManager.mc.thePlayer.inventoryContainer.windowId, (this.bestSwordSlot < 9) ? (this.bestSwordSlot + 36) : this.bestSwordSlot, 0, 2, InvManager.mc.thePlayer);
                    this.time.reset();
                }
                InvManager.mc.playerController.syncCurrentPlayItem();
            }
            if (Aqua.setmgr.getSetting("InvManagerFakeInv").isState() && this.canFakeInv()) {
                InvManager.mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(InvManager.mc.thePlayer, C0BPacketEntityAction.Action.OPEN_INVENTORY));
                if (this.bestSwordSlot != -1 && this.bestSwordDamage != -1.0f) {
                    final float DelayY2 = (float)Aqua.setmgr.getSetting("InvManagerDelay").getCurrentNumber();
                    if (this.time.hasReached((long)DelayY2)) {
                        InvManager.mc.playerController.windowClick(InvManager.mc.thePlayer.inventoryContainer.windowId, (this.bestSwordSlot < 9) ? (this.bestSwordSlot + 36) : this.bestSwordSlot, 0, 2, InvManager.mc.thePlayer);
                        this.time.reset();
                    }
                }
            }
            this.searchForTrash();
            Collections.shuffle(this.trash);
            if (Aqua.setmgr.getSetting("InvManagerOpenInv").isState() && InvManager.mc.currentScreen instanceof GuiInventory && !Aqua.setmgr.getSetting("InvManagerFakeInv").isState()) {
                for (final Integer integer : this.trash) {
                    final float DelayY = (float)Aqua.setmgr.getSetting("InvManagerDelay").getCurrentNumber();
                    if (this.timeUtil.hasReached((long)DelayY)) {
                        InvManager.mc.playerController.windowClick(InvManager.mc.thePlayer.inventoryContainer.windowId, (integer < 9) ? (integer + 36) : ((int)integer), 1, 4, InvManager.mc.thePlayer);
                        this.timeUtil.reset();
                    }
                }
            }
            if (Aqua.setmgr.getSetting("InvManagerFakeInv").isState() && this.canFakeInv()) {
                InvManager.mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(InvManager.mc.thePlayer, C0BPacketEntityAction.Action.OPEN_INVENTORY));
                for (final Integer integer : this.trash) {
                    final float DelayY = (float)Aqua.setmgr.getSetting("InvManagerDelay").getCurrentNumber();
                    if (this.timeUtil.hasReached((long)DelayY)) {
                        InvManager.mc.playerController.windowClick(InvManager.mc.thePlayer.inventoryContainer.windowId, (integer < 9) ? (integer + 36) : ((int)integer), 1, 4, InvManager.mc.thePlayer);
                        this.timeUtil.reset();
                    }
                }
            }
        }
    }
    
    public boolean canFakeInv() {
        return !InvManager.mc.thePlayer.isUsingItem() && !InvManager.mc.thePlayer.isEating() && InvManager.mc.currentScreen == null && !InvManager.mc.gameSettings.keyBindUseItem.isKeyDown() && !InvManager.mc.gameSettings.keyBindAttack.isKeyDown() && !InvManager.mc.gameSettings.keyBindJump.isKeyDown() && InvManager.mc.thePlayer.swingProgress == 0.0;
    }
    
    private void searchForItems() {
        InvManager.bestArmorDamageReducement = new int[4];
        this.bestArmorSlots = new int[4];
        this.bestSwordDamage = -1.0f;
        this.bestSwordSlot = -1;
        Arrays.fill(InvManager.bestArmorDamageReducement, -1);
        Arrays.fill(this.bestArmorSlots, -1);
        for (int i = 0; i < this.bestArmorSlots.length; ++i) {
            final ItemStack itemStack = InvManager.mc.thePlayer.inventory.armorItemInSlot(i);
            if (itemStack != null && itemStack.getItem() != null && itemStack.getItem() instanceof ItemArmor) {
                final ItemArmor armor = (ItemArmor)itemStack.getItem();
                InvManager.bestArmorDamageReducement[i] = armor.damageReduceAmount;
            }
        }
        for (int i = 0; i < 36; ++i) {
            final ItemStack itemStack = InvManager.mc.thePlayer.inventory.getStackInSlot(i);
            if (itemStack != null) {
                if (itemStack.getItem() != null) {
                    if (itemStack.getItem() instanceof ItemArmor) {
                        final ItemArmor armor = (ItemArmor)itemStack.getItem();
                        final int armorType = 3 - armor.armorType;
                        if (InvManager.bestArmorDamageReducement[armorType] < armor.damageReduceAmount) {
                            InvManager.bestArmorDamageReducement[armorType] = armor.damageReduceAmount;
                            this.bestArmorSlots[armorType] = i;
                        }
                    }
                    if (itemStack.getItem() instanceof ItemSword) {
                        final ItemSword sword = (ItemSword)itemStack.getItem();
                        if (this.bestSwordDamage < sword.getDamageVsEntity() + EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack)) {
                            this.bestSwordDamage = sword.getDamageVsEntity() + EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack);
                            this.bestSwordSlot = i;
                        }
                    }
                    if (itemStack.getItem() instanceof ItemTool) {
                        final ItemTool sword2 = (ItemTool)itemStack.getItem();
                        float damage = sword2.getToolMaterial().getDamageVsEntity() + EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack);
                        try {
                            if (Aqua.setmgr.getSetting("InvManagerPrefSwords").isState()) {
                                --damage;
                            }
                            if (this.bestSwordDamage < damage) {
                                this.bestSwordDamage = damage;
                                this.bestSwordSlot = i;
                            }
                        }
                        catch (NullPointerException ex) {}
                    }
                }
            }
        }
    }
    
    private void searchForTrash() {
        this.trash.clear();
        InvManager.bestArmorDamageReducement = new int[4];
        this.bestArmorSlots = new int[4];
        this.bestSwordDamage = -1.0f;
        this.bestSwordSlot = -1;
        Arrays.fill(InvManager.bestArmorDamageReducement, -1);
        Arrays.fill(this.bestArmorSlots, -1);
        final List<Integer>[] allItems = (List<Integer>[])new List[4];
        final List<Integer> allSwords = new ArrayList<Integer>();
        for (int i = 0; i < this.bestArmorSlots.length; ++i) {
            final ItemStack itemStack = InvManager.mc.thePlayer.inventory.armorItemInSlot(i);
            allItems[i] = new ArrayList<Integer>();
            if (itemStack != null && itemStack.getItem() != null && itemStack.getItem() instanceof ItemArmor) {
                final ItemArmor armor = (ItemArmor)itemStack.getItem();
                InvManager.bestArmorDamageReducement[i] = armor.damageReduceAmount;
                this.bestArmorSlots[i] = 8 + i;
            }
        }
        for (int i = 9; i < InvManager.mc.thePlayer.inventoryContainer.inventorySlots.size(); ++i) {
            final ItemStack itemStack = InvManager.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (itemStack != null) {
                if (itemStack.getItem() != null) {
                    if (itemStack.getItem() instanceof ItemArmor) {
                        final ItemArmor armor = (ItemArmor)itemStack.getItem();
                        final int armorType = 3 - armor.armorType;
                        allItems[armorType].add(i);
                        if (InvManager.bestArmorDamageReducement[armorType] < armor.damageReduceAmount) {
                            InvManager.bestArmorDamageReducement[armorType] = armor.damageReduceAmount;
                            this.bestArmorSlots[armorType] = i;
                        }
                    }
                    if (itemStack.getItem() instanceof ItemSword) {
                        final ItemSword sword = (ItemSword)itemStack.getItem();
                        allSwords.add(i);
                        if (this.bestSwordDamage < sword.getDamageVsEntity() + EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack)) {
                            this.bestSwordDamage = sword.getDamageVsEntity() + EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack);
                            this.bestSwordSlot = i;
                        }
                    }
                    if (itemStack.getItem() instanceof ItemTool) {
                        final ItemTool sword2 = (ItemTool)itemStack.getItem();
                        float damage = sword2.getToolMaterial().getDamageVsEntity() + EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack);
                        try {
                            if (Aqua.setmgr.getSetting("InvManagerPrefSwords").isState()) {
                                --damage;
                            }
                            if (this.bestSwordDamage < damage) {
                                this.bestSwordDamage = damage;
                                this.bestSwordSlot = i;
                            }
                        }
                        catch (NullPointerException ex) {}
                    }
                }
            }
        }
        for (int i = 0; i < allItems.length; ++i) {
            final List<Integer> allItem = allItems[i];
            final int finalI = i;
            allItem.stream().filter(slot -> slot != this.bestArmorSlots[finalI]).forEach(this.trash::add);
        }
        allSwords.stream().filter(slot -> slot != this.bestSwordSlot).forEach(this.trash::add);
    }
}
