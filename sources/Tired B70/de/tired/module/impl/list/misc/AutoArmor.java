package de.tired.module.impl.list.misc;

import de.tired.api.util.math.TimerUtil;
import de.tired.api.annotations.ModuleAnnotation;
import de.tired.api.guis.clickgui.setting.NumberSetting;
import de.tired.api.guis.clickgui.setting.impl.BooleanSetting;
import de.tired.event.EventTarget;
import de.tired.event.events.UpdateEvent;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;
import de.tired.api.util.misc.InventoryUtil;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.util.BlockPos;

import java.util.HashMap;
import java.util.Random;

@ModuleAnnotation(name = "AutoArmor", category = ModuleCategory.PLAYER, clickG = "automatically equips armor")

public class AutoArmor extends Module {

    private final HashMap<ItemStack, Long> armorInvTime;
    public static TimerUtil wear = new TimerUtil();
    public static TimerUtil drop = new TimerUtil();
    public static boolean openedInventory;
    public static boolean finished;
    public BooleanSetting openINV = new BooleanSetting("openINV", this, true);
    public BooleanSetting autoArmorStand = new BooleanSetting("StandStill", this, true);
    public BooleanSetting dropItems = new BooleanSetting("dropItems", this, true);
    public BooleanSetting invPacket = new BooleanSetting("Packet", this, true);
    public BooleanSetting hotbarAutoArmor = new BooleanSetting("hotbarAutoArmor", this, true);
    public NumberSetting putDelay = new NumberSetting("putDelay", this, 1, 1, 1000, 1);
    public NumberSetting dropDelay = new NumberSetting("dropDelay", this, 1, 1, 1000, 1);

    public AutoArmor() {
        this.armorInvTime = new HashMap<>();
    }

    @EventTarget
    public void onUpdate(UpdateEvent e) {
        if (this.hotbarAutoArmor.getValue()) {
            int hotbarArmorInt = InventoryUtil.findArmorHotbar();
            if (hotbarArmorInt != -1 && wear.reachedTime(putDelay.getValueInt())) {
                MC.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(hotbarArmorInt));
                MC.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, MC.thePlayer.getCurrentEquippedItem(), 0.0F, 0.0F, 0.0F));
                MC.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(MC.thePlayer.inventory.currentItem));
                wear.doReset();
                return;
            }
        }

        boolean doAutoArmor = !this.openINV.getValue() && !(MC.currentScreen instanceof GuiChest) || (MC.currentScreen instanceof GuiInventory);

        finished = true;
        if (this.autoArmorStand.getValue() && (this.MC.thePlayer.moveForward != 0.0F || this.MC.thePlayer.moveStrafing != 0.0F)) {
            if (openedInventory && this.invPacket.getValue()) {
                this.MC.thePlayer.sendQueue.addToSendQueue(new C0DPacketCloseWindow(this.MC.thePlayer.inventoryContainer.windowId));
                openedInventory = false;
            }

        } else {
            final InventoryUtil.ARMOR_TYPE[] armor_types = InventoryUtil.ARMOR_TYPE.values();
            for (InventoryUtil.ARMOR_TYPE armorType : armor_types) {
                int currentSlot = armorType.slot;
                int bestArmorSlot = InventoryUtil.getBestArmorInInventory(armorType);
                boolean shouldAdd = true;
                if (bestArmorSlot != -1) {
                    shouldAdd = InventoryUtil.getArmorGoodness(MC.thePlayer.inventoryContainer.getSlot(bestArmorSlot).getStack()) > InventoryUtil.getArmorGoodness(MC.thePlayer.inventoryContainer.getSlot(currentSlot).getStack());
                }


                if (shouldAdd && bestArmorSlot != -1 && !armorInvTime.containsKey(MC.thePlayer.inventoryContainer.getSlot(bestArmorSlot).getStack())) {
                    armorInvTime.put(MC.thePlayer.inventoryContainer.getSlot(bestArmorSlot).getStack(), System.currentTimeMillis());
                }

                if (doAutoArmor) {
                    if (bestArmorSlot != -1 && MC.thePlayer.inventoryContainer.getSlot(currentSlot).getHasStack() && InventoryUtil.getArmorGoodness(MC.thePlayer.inventoryContainer.getSlot(bestArmorSlot).getStack()) < InventoryUtil.getArmorGoodness(MC.thePlayer.inventoryContainer.getSlot(currentSlot).getStack())) {
                        bestArmorSlot = -1;
                    }

                    if (wear.reachedTime(putDelay.getValueInt() + (long) getRandom(-100, 100)) && bestArmorSlot != -1) {
                        if (invPacket.getValue() && MC.currentScreen == null && !openedInventory) {
                            MC.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(MC.thePlayer, C0BPacketEntityAction.Action.OPEN_INVENTORY));
                            openedInventory = true;
                        }

                        finished = false;
                        if (armorInvTime.containsKey(MC.thePlayer.inventoryContainer.getSlot(bestArmorSlot).getStack()) && System.currentTimeMillis() - armorInvTime.get(MC.thePlayer.inventoryContainer.getSlot(bestArmorSlot).getStack()) >= 12) {
                            putOnItem(currentSlot, bestArmorSlot);
                            armorInvTime.remove(MC.thePlayer.inventoryContainer.getSlot(bestArmorSlot).getStack());
                            wear.doReset();
                            drop.doReset();
                        }
                    }

                    for (int anotherArmors : InventoryUtil.findArmor(armorType)) {
                        boolean isOldBetter = false;
                        if (currentSlot != -1) {
                            isOldBetter = InventoryUtil.getArmorGoodness(MC.thePlayer.inventoryContainer.getSlot(currentSlot).getStack()) >= InventoryUtil.getArmorGoodness(MC.thePlayer.inventoryContainer.getSlot(anotherArmors).getStack());
                        }

                        if (isOldBetter) {
                            finished = false;
                            if (drop.reachedTime(dropDelay.getValueInt() + (long) getRandom(-100, 100)) && dropItems.getValue()) {
                                this.dropOldArmor(anotherArmors);
                                drop.doReset();
                            }
                        }
                    }
                }
            }

            if (finished && this.invPacket.getValue()) {
                MC.thePlayer.sendQueue.addToSendQueue(new C0DPacketCloseWindow(MC.thePlayer.inventoryContainer.windowId));
                openedInventory = false;
            }
        }
        setDesc(" PutDelay: " + putDelay.getValue() + " DropDelay: " + dropDelay.getValue());
    }

    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {

    }

    private void putOnItem(int armorSlot, int slot) {
        if (armorSlot != -1 && this.MC.thePlayer.inventoryContainer.getSlot(armorSlot).getStack() != null) {
            this.dropOldArmor(armorSlot);
        } else {
            this.inventoryAction(slot);
        }
    }

    public static double getRandom(double min, double max) {
        final Random rand = new Random();
        return rand.nextDouble() * (max - min) + min;
    }


    private void dropOldArmor(int slot) {
        MC.thePlayer.inventoryContainer.slotClick(slot, 0, 4, this.MC.thePlayer);
        MC.playerController.windowClick(this.MC.thePlayer.inventoryContainer.windowId, slot, 1, 4, this.MC.thePlayer);
    }

    private void inventoryAction(int click) {
        MC.playerController.windowClick(this.MC.thePlayer.inventoryContainer.windowId, click, 1, 1, this.MC.thePlayer);
    }

}
