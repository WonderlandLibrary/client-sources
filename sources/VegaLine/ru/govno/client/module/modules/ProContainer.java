/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiRepair;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAir;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketCloseWindow;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.server.SPacketConfirmTransaction;
import net.minecraft.network.play.server.SPacketHeldItemChange;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Mouse;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.EventReceivePacket;
import ru.govno.client.event.events.EventSendPacket;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.ElytraBoost;
import ru.govno.client.module.modules.OffHand;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Math.TimerHelper;

public class ProContainer
extends Module {
    public static ProContainer get;
    public Settings ContainerInfo;
    public Settings MouseTweaks;
    public Settings QuickSwap;
    public Settings ScrollItems;
    public Settings CraftSlotsSafe;
    public Settings CraftSlotsManager;
    public Settings CtrlRDroper;
    public Settings AutoArmor;
    public Settings StackUnlimit;
    public Settings InvDesyncFix;
    public Settings AllowUiParagraph;
    public Settings BetterSwapHands;
    public Settings HandElytraSwap;
    public static boolean allowParagraphToRepairUi;
    public static boolean autoArmorOFF;
    public final TimerHelper timer = new TimerHelper();
    boolean autoArmorIgnoreForElytra;
    public short action;

    public ProContainer() {
        super("ProContainer", 0, Module.Category.PLAYER);
        this.ContainerInfo = new Settings("ContainerInfo", true, (Module)this);
        this.settings.add(this.ContainerInfo);
        this.MouseTweaks = new Settings("MouseTweaks", true, (Module)this);
        this.settings.add(this.MouseTweaks);
        this.QuickSwap = new Settings("QuickSwap", true, (Module)this);
        this.settings.add(this.QuickSwap);
        this.ScrollItems = new Settings("ScrollItems", true, (Module)this);
        this.settings.add(this.ScrollItems);
        this.CraftSlotsSafe = new Settings("CraftSlotsSafe", false, (Module)this);
        this.settings.add(this.CraftSlotsSafe);
        this.CraftSlotsManager = new Settings("CraftSlotsManager", false, (Module)this, () -> this.CraftSlotsSafe.bValue);
        this.settings.add(this.CraftSlotsManager);
        this.CtrlRDroper = new Settings("CtrlRDroper", false, (Module)this);
        this.settings.add(this.CtrlRDroper);
        this.AutoArmor = new Settings("AutoArmor", false, (Module)this);
        this.settings.add(this.AutoArmor);
        this.StackUnlimit = new Settings("StackUnlimit", true, (Module)this);
        this.settings.add(this.StackUnlimit);
        this.InvDesyncFix = new Settings("InvDesyncFix", true, (Module)this);
        this.settings.add(this.InvDesyncFix);
        this.AllowUiParagraph = new Settings("AllowUiParagraph", true, (Module)this);
        this.settings.add(this.AllowUiParagraph);
        this.BetterSwapHands = new Settings("BetterSwapHands", true, (Module)this);
        this.settings.add(this.BetterSwapHands);
        this.HandElytraSwap = new Settings("HandElytraSwap", true, (Module)this);
        this.settings.add(this.HandElytraSwap);
        get = this;
    }

    public void onKey(int key) {
        if (this.BetterSwapHands.bValue && key == ProContainer.mc.gameSettings.keyBindSwapHands.getKeyCode()) {
            Minecraft.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.SWAP_HELD_ITEMS, BlockPos.ORIGIN, EnumFacing.DOWN));
            ItemStack main = Minecraft.player.getHeldItemMainhand();
            ItemStack off = Minecraft.player.getHeldItemOffhand();
            Minecraft.player.setHeldItem(EnumHand.MAIN_HAND, off);
            Minecraft.player.setHeldItem(EnumHand.OFF_HAND, main);
            OffHand.oldSlot = null;
        }
    }

    @EventTarget
    public void onPacket(EventSendPacket event) {
        SPacketHeldItemChange change;
        int slot;
        Packet packet;
        if (!this.actived && Minecraft.player == null) {
            return;
        }
        if (this.InvDesyncFix.bValue && (packet = event.getPacket()) instanceof SPacketHeldItemChange && ((slot = (change = (SPacketHeldItemChange)packet).getHeldItemHotbarIndex()) < 0 || slot > 8)) {
            Minecraft.player.connection.preSendPacket(new CPacketHeldItemChange(Minecraft.player.inventory.currentItem));
            event.cancel();
        }
        if ((packet = event.getPacket()) instanceof CPacketCloseWindow) {
            CPacketCloseWindow win = (CPacketCloseWindow)packet;
            if (this.CraftSlotsSafe.bValue && win.windowId == 0) {
                event.setCancelled(true);
            }
        }
    }

    @Override
    public void onUpdate() {
        boolean allowParagraph;
        Object stackInSlot3;
        if (Minecraft.player == null) {
            return;
        }
        if (Minecraft.player.openContainer instanceof ContainerPlayer && this.CraftSlotsSafe.bValue && this.CraftSlotsManager.bValue) {
            ArrayList<Integer> emptyItemSlotsINV = new ArrayList<Integer>();
            ArrayList<Integer> notEmptyItemSlotsINV = new ArrayList<Integer>();
            List<Slot> inventorySlots = Minecraft.player.inventoryContainer.inventorySlots;
            for (int Islot = 9; Islot < 44; ++Islot) {
                Slot slot = inventorySlots.get(Islot);
                int slotNum = slot.slotNumber;
                ItemStack stackInSlot2 = slot.getStack();
                Item itemInStack = stackInSlot2.getItem();
                if (itemInStack instanceof ItemAir) {
                    emptyItemSlotsINV.add(slotNum);
                    continue;
                }
                notEmptyItemSlotsINV.add(slot.slotNumber);
            }
            ArrayList<Integer> notEmptyItemSlotsCRAFT = new ArrayList<Integer>();
            ArrayList<Integer> emptyItemSlotsCRAFT = new ArrayList<Integer>();
            for (int Islot = 4; Islot > 0; --Islot) {
                Slot slot = inventorySlots.get(Islot);
                stackInSlot3 = slot.getStack();
                Item itemInStack = ((ItemStack)stackInSlot3).getItem();
                if (itemInStack instanceof ItemAir) {
                    emptyItemSlotsCRAFT.add(slot.slotNumber);
                    continue;
                }
                notEmptyItemSlotsCRAFT.add(slot.slotNumber);
            }
            List entityItemsToPickup = ProContainer.mc.world.getLoadedEntityList().stream().filter(entity -> entity instanceof EntityItem).map(entity -> (EntityItem)entity).filter(Objects::nonNull).filter(eItem -> !eItem.cannotPickup() && eItem.ticksExisted > 10 && eItem.delayBeforeCanPickup == 0 && (eItem.getOwner() == null || 6000 - eItem.getAge() <= 200) && (Minecraft.player.getDistanceToEntity((Entity)eItem) <= 1.0f + Minecraft.player.width / 2.0f || Minecraft.player.getDistanceAtEye(eItem.posX, eItem.posY, eItem.posZ) <= (double)(1.0f + Minecraft.player.width / 2.0f))).collect(Collectors.toList());
            if (!(entityItemsToPickup.isEmpty() || emptyItemSlotsCRAFT.isEmpty() || notEmptyItemSlotsINV.isEmpty() || emptyItemSlotsINV.size() >= entityItemsToPickup.size())) {
                ProContainer.mc.playerController.windowClick(0, (Integer)notEmptyItemSlotsINV.get(0), 0, ClickType.PICKUP, Minecraft.player);
                ProContainer.mc.playerController.windowClick(0, (Integer)emptyItemSlotsCRAFT.get(0), 0, ClickType.PICKUP, Minecraft.player);
            }
            if (entityItemsToPickup.isEmpty() && !notEmptyItemSlotsCRAFT.isEmpty() && !emptyItemSlotsINV.isEmpty()) {
                ProContainer.mc.playerController.windowClick(0, (Integer)notEmptyItemSlotsCRAFT.get(0), 1, ClickType.QUICK_MOVE, Minecraft.player);
            }
        }
        boolean bl = allowParagraph = ProContainer.mc.currentScreen instanceof GuiRepair && this.AllowUiParagraph.bValue;
        if (allowParagraphToRepairUi != allowParagraph) {
            allowParagraphToRepairUi = allowParagraph;
        }
        if (Mouse.isButtonDown(1) && (ProContainer.mc.rightClickDelayTimer == 4 || ProContainer.mc.rightClickDelayTimer == 0) && ProContainer.mc.currentScreen == null && Minecraft.player.openContainer instanceof ContainerPlayer && this.currentBooleanValue("HandElytraSwap")) {
            ItemStack stackInHand = Minecraft.player.inventory.getCurrentItem();
            ItemStack stackOnChestplate = Minecraft.player.inventory.armorItemInSlot(2);
            Item itemInHand = stackInHand.getItem();
            Item itemOnChestplate = stackOnChestplate.getItem();
            if (itemInHand instanceof ItemArmor && itemOnChestplate instanceof ItemElytra || itemInHand instanceof ItemElytra && itemOnChestplate instanceof ItemArmor) {
                this.autoArmorIgnoreForElytra = itemInHand instanceof ItemElytra;
                if (this.autoArmorIgnoreForElytra) {
                    this.timer.reset();
                }
                int mouseKeyToWinClick = stackOnChestplate.stackSize == 1 ? 1 : 0;
                ProContainer.mc.playerController.windowClick(0, 6, mouseKeyToWinClick, ClickType.PICKUP, Minecraft.player);
                ProContainer.mc.playerController.processRightClick(Minecraft.player, ProContainer.mc.world, EnumHand.MAIN_HAND);
                ProContainer.mc.playerController.windowClickMemory(0, Minecraft.player.inventory.currentItem + 36, mouseKeyToWinClick, ClickType.PICKUP, Minecraft.player, 100);
                ProContainer.mc.gameSettings.keyBindUseItem.pressed = false;
                Minecraft.player.resetActiveHand();
                ProContainer.mc.playerController.onStoppedUsingItem(Minecraft.player);
            }
        }
        if (this.autoArmorIgnoreForElytra && !this.HandElytraSwap.bValue) {
            this.autoArmorIgnoreForElytra = false;
        }
        if (this.InvDesyncFix.bValue && (Minecraft.player.inventory.currentItem < 0 || Minecraft.player.inventory.currentItem > 8 || Minecraft.player.ticksExisted == 1)) {
            Minecraft.player.inventory.currentItem = 0;
            this.syncSlot();
        }
        if (!(ProContainer.mc.currentScreen != null && !(ProContainer.mc.currentScreen instanceof GuiInventory) || autoArmorOFF || !this.AutoArmor.bValue || ElytraBoost.get.actived && ElytraBoost.canElytra())) {
            ItemArmor item;
            if (!this.timer.hasReached(200.0)) {
                return;
            }
            this.timer.reset();
            InventoryPlayer inventory = Minecraft.player.inventory;
            int[] bestArmorSlots = new int[4];
            int[] bestArmorValues = new int[4];
            for (int type2 = 0; type2 < 4; ++type2) {
                bestArmorSlots[type2] = -1;
                ItemStack stack = inventory.armorItemInSlot(type2);
                if (ProContainer.isNullOrEmpty(stack) || !((stackInSlot3 = stack.getItem()) instanceof ItemArmor)) continue;
                item = (ItemArmor)stackInSlot3;
                bestArmorValues[type2] = this.getArmorValue(item, stack);
            }
            for (int slot = 0; slot < 36; ++slot) {
                Item stackInSlot3;
                ItemStack stack = inventory.getStackInSlot(slot);
                if (ProContainer.isNullOrEmpty(stack) || !((stackInSlot3 = stack.getItem()) instanceof ItemArmor)) continue;
                item = (ItemArmor)stackInSlot3;
                int armorType = item.armorType.getIndex();
                int armorValue = this.getArmorValue(item, stack);
                if (armorValue <= bestArmorValues[armorType]) continue;
                bestArmorSlots[armorType] = slot;
                if (this.autoArmorIgnoreForElytra && inventory.armorInventory.get(armorType).getItem() instanceof ItemElytra) {
                    bestArmorSlots[armorType] = -1;
                }
                bestArmorValues[armorType] = armorValue;
            }
            ArrayList<Integer> types = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3));
            Collections.shuffle(types);
            for (int i : types) {
                ItemStack oldArmor;
                int j = bestArmorSlots[i];
                if (j == -1 || !ProContainer.isNullOrEmpty(oldArmor = inventory.armorItemInSlot(i)) && inventory.getFirstEmptyStack() == -1) continue;
                if (j < 9) {
                    j += 36;
                }
                if (!ProContainer.isNullOrEmpty(oldArmor)) {
                    ProContainer.mc.playerController.windowClick(0, 8 - i, 1, ClickType.QUICK_MOVE, Minecraft.player);
                }
                ProContainer.mc.playerController.windowClick(0, j, 1, ClickType.QUICK_MOVE, Minecraft.player);
                break;
            }
        }
    }

    @EventTarget
    public void onPacket(EventReceivePacket event) {
        if (!this.actived || Minecraft.player == null) {
            return;
        }
        if (this.InvDesyncFix.bValue) {
            SPacketHeldItemChange held;
            Packet packet = event.getPacket();
            if (packet instanceof SPacketConfirmTransaction) {
                SPacketConfirmTransaction wrapper = (SPacketConfirmTransaction)packet;
                Container inventory = Minecraft.player.inventoryContainer;
                if (inventory != null && wrapper != null && wrapper.getWindowId() == inventory.windowId) {
                    this.action = wrapper.getActionNumber();
                    if (this.action > 0 && this.action < inventory.transactionID) {
                        inventory.transactionID = (short)(this.action + 1);
                    }
                }
            }
            if ((packet = event.getPacket()) instanceof SPacketHeldItemChange && (held = (SPacketHeldItemChange)packet).getHeldItemHotbarIndex() != Minecraft.player.inventory.currentItem) {
                Minecraft.player.connection.sendPacket(new CPacketHeldItemChange(held.getHeldItemHotbarIndex()));
                Minecraft.player.connection.sendPacket(new CPacketHeldItemChange(Minecraft.player.inventory.currentItem));
                event.cancel();
            }
        }
    }

    private int getArmorValue(ItemArmor item, ItemStack stack) {
        int armorPoints = item.damageReduceAmount;
        int prtPoints = 0;
        int armorToughness = (int)item.toughness;
        int armorType = item.getArmorMaterial().getDamageReductionAmount(item.armorType);
        Enchantment protection = Enchantments.PROTECTION;
        int prtLvl = EnchantmentHelper.getEnchantmentLevel(protection, stack);
        DamageSource dmgSource = DamageSource.causePlayerDamage(Minecraft.player);
        prtPoints = protection.calcModifierDamage(prtLvl, dmgSource);
        return armorPoints * 5 + prtPoints * 3 + armorToughness + armorType;
    }

    public static boolean isNullOrEmpty(ItemStack stack) {
        return stack == null || stack.isEmpty();
    }

    @Override
    public void onToggled(boolean actived) {
        this.timer.reset();
        allowParagraphToRepairUi = false;
        super.onToggled(actived);
    }

    private void syncSlot() {
        Minecraft.player.connection.sendPacket(new CPacketHeldItemChange((Minecraft.player.inventory.currentItem + 1) % 9));
        Minecraft.player.connection.sendPacket(new CPacketHeldItemChange(Minecraft.player.inventory.currentItem));
        ProContainer.mc.playerController.syncCurrentPlayItem();
    }
}

