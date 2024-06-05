package de.tired.module.impl.list.misc;

import de.tired.api.util.math.TimerUtil;
import de.tired.api.annotations.ModuleAnnotation;
import de.tired.api.guis.clickgui.setting.NumberSetting;
import de.tired.api.guis.clickgui.setting.impl.BooleanSetting;
import de.tired.api.util.misc.InventoryUtil;
import de.tired.event.EventTarget;
import de.tired.event.events.UpdateEvent;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Items;
import net.minecraft.item.*;

import java.util.HashMap;

@ModuleAnnotation(name = "Manager", category = ModuleCategory.MISC, clickG = "Manages the inventory for you")
public class Manager extends Module {

    public static boolean isWorking;
    public TimerUtil openInvDelayTimer;
    private final HashMap<ItemStack, Long> itemInvTime;
    public static final int BOW = 2;

    public NumberSetting openInvDelay = new NumberSetting("openInvDelay", this, 75.0D, 0, 175, 1);
    public NumberSetting clickDelay = new NumberSetting("clickDelay", this, 115.0D, 1.0D, 500.0D, 1);
    public BooleanSetting openInv = new BooleanSetting("openInv", this, true);
    public BooleanSetting keepTools = new BooleanSetting("keepTools", this, true);
    public BooleanSetting checkRealItem = new BooleanSetting("checkRealItem", this, true);
    public BooleanSetting preferSwords = new BooleanSetting("preferSwords", this, true);

    public Manager() {
        isWorking = false;
        this.openInvDelayTimer = new TimerUtil();
        this.itemInvTime = new HashMap<>();
    }

    @EventTarget
    public void onUpdate(UpdateEvent e) {
        setDesc("OpenInv? " + openInv.getValue() + " " + "ClickDelay: " + clickDelay.getValue());
        for (int i = 0; i < 45; ++i) {
            if (this.MC.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack stack = this.MC.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (!this.itemInvTime.containsKey(stack)) {
                    this.itemInvTime.put(stack, System.currentTimeMillis());
                }
            }
        }

        if (this.MC.theWorld != null) {
            if (!(this.MC.currentScreen instanceof GuiChest)) {
                if (!isWorking) {
                    if (this.openInv.getValue() && !(this.MC.currentScreen instanceof GuiInventory)) {
                        this.openInvDelayTimer.doReset();
                    } else if (!this.openInv.getValue() || this.openInvDelayTimer.reachedTime(this.openInvDelay.getValueInt())) {
                        isWorking = true;
                        (new Thread(() -> {
                            try {
                                int bestWeaponSlot = InventoryUtil.getStrongestWeapon(true, this.preferSwords.getValue());
                                int bestBowSlot = InventoryUtil.getBestBow(true);
                                int bestFoodSlot = InventoryUtil.getBestFood();
                                if (bestWeaponSlot != 36 && bestWeaponSlot != -1 && this.itemInvTime.containsKey(this.MC.thePlayer.inventoryContainer.getSlot(bestWeaponSlot).getStack()) && System.currentTimeMillis() - this.itemInvTime.get(this.MC.thePlayer.inventoryContainer.getSlot(bestWeaponSlot).getStack()) >= 500L) {
                                    this.swapItem(bestWeaponSlot, 0);
                                    this.itemInvTime.remove(this.MC.thePlayer.inventoryContainer.getSlot(bestWeaponSlot).getStack());

                                    try {
                                        Thread.sleep(this.clickDelay.getValueInt());
                                    } catch (Exception ignored) {
                                    }
                                }

                                if (bestBowSlot != 38 && bestBowSlot != -1 && this.itemInvTime.containsKey(this.MC.thePlayer.inventoryContainer.getSlot(bestBowSlot).getStack()) && System.currentTimeMillis() - this.itemInvTime.get(this.MC.thePlayer.inventoryContainer.getSlot(bestBowSlot).getStack()) >= 500L) {
                                    this.swapItem(bestBowSlot, 2);
                                    this.itemInvTime.remove(this.MC.thePlayer.inventoryContainer.getSlot(bestBowSlot).getStack());

                                    try {
                                        Thread.sleep(this.clickDelay.getValueInt());
                                    } catch (Exception ignored) {
                                    }
                                }

                                if (bestFoodSlot != 39 && bestFoodSlot != -1 && this.itemInvTime.containsKey(this.MC.thePlayer.inventoryContainer.getSlot(bestFoodSlot).getStack()) && System.currentTimeMillis() - this.itemInvTime.get(this.MC.thePlayer.inventoryContainer.getSlot(bestFoodSlot).getStack()) >= 500L) {
                                    this.swapItem(bestFoodSlot, 3);
                                    this.itemInvTime.remove(this.MC.thePlayer.inventoryContainer.getSlot(bestFoodSlot).getStack());

                                    try {
                                        Thread.sleep(this.clickDelay.getValueInt());
                                    } catch (Exception ignored) {
                                    }
                                }

                                for (int i = 9; i < this.MC.thePlayer.inventoryContainer.getInventory().size(); ++i) {
                                    if (i != bestWeaponSlot && i != bestBowSlot && i != bestFoodSlot) {
                                        boolean isRodInSlot = this.MC.thePlayer.inventoryContainer.getInventory().get(37) != null && this.MC.thePlayer.inventoryContainer.getInventory().get(37).getItem() == Items.fishing_rod;
                                        boolean areBlocksInSlot = this.MC.thePlayer.inventoryContainer.getInventory().get(40) != null && InventoryUtil.isItemBlock((ItemStack) this.MC.thePlayer.inventoryContainer.getInventory().get(40));
                                        ItemStack item = this.MC.thePlayer.inventoryContainer.getInventory().get(i);
                                        if (this.itemInvTime.containsKey(item) && System.currentTimeMillis() - this.itemInvTime.get(item) >= 500L && item != null) {
                                            if (!isRodInSlot && item.getItem() == Items.fishing_rod) {
                                                this.swapItem(i, 1);
                                                this.itemInvTime.remove(item);
                                                try {
                                                    Thread.sleep(this.clickDelay.getValueInt());
                                                } catch (Exception ignored) {
                                                }
                                            } else if (!areBlocksInSlot && InventoryUtil.isItemBlock(item)) {
                                                this.swapItem(i, 4);
                                                this.itemInvTime.remove(item);

                                                try {
                                                    Thread.sleep(this.clickDelay.getValueInt());
                                                } catch (Exception ignored) {
                                                }
                                            } else if (InventoryUtil.isUselessItem(item)) {
                                                this.dropSlot(i);
                                            } else if (InventoryUtil.isUsefultem(item) && !(item.getItem() instanceof ItemArmor) && (i != 36 || !(item.getItem() instanceof ItemSword) && !(item.getItem() instanceof ItemTool)) && (i != 37 || item.getItem() != Items.fishing_rod) && (i != 38 || item.getItem() != Items.bow)) {
                                                int bestPickaxe = InventoryUtil.getBestPickaxe(true);
                                                int bestAxe = InventoryUtil.getBestAxe(true);
                                                int bestSpade = InventoryUtil.getBestSpade(true);
                                                if (this.keepTools.getValue() && item.getItem() instanceof ItemTool) {
                                                    try {
                                                        Thread.sleep(this.clickDelay.getValueInt());
                                                    } catch (Exception ignored) {
                                                    }

                                                    if (this.openInv.getValue() && !(this.MC.currentScreen instanceof GuiInventory)) {
                                                        isWorking = false;
                                                        return;
                                                    }

                                                    if ((bestPickaxe != -1 && item.getItem() instanceof ItemPickaxe && i != bestPickaxe || bestSpade != -1 && item.getItem() instanceof ItemSpade && i != bestSpade || bestAxe != -1 && item.getItem() instanceof ItemAxe && i != bestAxe) && !(MC.thePlayer.inventoryContainer.getSlot(i).getStack().getItem() instanceof ItemArmor)) {
                                                        this.dropSlot(i);
                                                    }
                                                } else {
                                                    try {
                                                        Thread.sleep(this.clickDelay.getValueInt());
                                                    } catch (Exception ignored) {
                                                    }

                                                    if (openInv.getValue() && !(MC.currentScreen instanceof GuiInventory)) {
                                                        isWorking = false;
                                                        return;
                                                    }

                                                    if (!(MC.thePlayer.inventoryContainer.getSlot(i).getStack().getItem() instanceof ItemArmor) && (!checkRealItem.getValue() || !MC.thePlayer.inventoryContainer.getSlot(i).getStack().hasDisplayName())) {
                                                        this.dropSlot(i);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } catch (Exception ignored) {
                            }

                            isWorking = false;
                        })).start();
                    }
                }
            }
        }
    }


    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {

    }

    public void dropSlot(int slot) {
        MC.thePlayer.inventoryContainer.slotClick(slot, 0, 4, MC.thePlayer);
        MC.playerController.windowClick(MC.thePlayer.inventoryContainer.windowId, slot, 1, 4, MC.thePlayer);
    }

    public void swapItem(int from, int to) {
        MC.playerController.windowClick(MC.thePlayer.inventoryContainer.windowId, from, to, 2, MC.thePlayer);
    }

}
