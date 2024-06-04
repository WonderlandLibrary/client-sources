package com.polarware.module.impl.combat;

import com.polarware.event.annotations.EventLink;
import com.polarware.event.bus.Listener;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.other.LegitClickEvent;
import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.value.impl.BooleanValue;
import com.polarware.value.impl.NumberValue;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.*;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import java.util.concurrent.ThreadLocalRandom;

@ModuleInfo(name = "AutoSoup", description = "auto drinks soups with my cum", category = Category.COMBAT)
public class AutoSoup extends Module {
    private final NumberValue health = new NumberValue("Health", this, 10, 1, 20, 1);
    public BooleanValue drop = new BooleanValue("Drop", this, false);
    public BooleanValue fill = new BooleanValue("Fill", this, false);
    public BooleanValue autoOpen = new BooleanValue("Auto Open", this, false);
    public BooleanValue autoClose = new BooleanValue("Auto Close", this, false);
    private int slotOnLastTick = 0;
    private int previousSlot;
    private boolean clicked;
    private boolean dropped;

    @EventLink()
    public final Listener<LegitClickEvent> onPreMotionEvent = event -> {
        if (AutoSoup.mc.currentScreen == null) {
            if (((AutoSoup.mc.thePlayer.getCurrentEquippedItem() != null && AutoSoup.mc.thePlayer.getCurrentEquippedItem().getItem() == Items.bowl) || this.clicked) && AutoSoup.mc.thePlayer.inventory.currentItem == this.slotOnLastTick) {
                this.dropped = true;
                this.clicked = false;
                if (this.drop.getValue()) {
                    AutoSoup.mc.thePlayer.dropOneItem(true);
                    return;
                }
            }
            if (this.dropped) {
                this.dropped = false;
                if (this.previousSlot != -1) {
                    AutoSoup.mc.thePlayer.inventory.currentItem = this.previousSlot;
                }
                this.previousSlot = -1;
            }
            final int slot = this.getSoup();
            if (slot != -1) {
                if (AutoSoup.mc.thePlayer.getHealth() <= this.health.getValue().intValue() && !this.clicked) {
                    if (AutoSoup.mc.thePlayer.getCurrentEquippedItem() != null && AutoSoup.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSoup) {
                        AutoSoup.mc.rightClickMouse();
                        this.clicked = true;
                    }
                    else {
                        if (this.previousSlot == -1) {
                            this.previousSlot = AutoSoup.mc.thePlayer.inventory.currentItem;
                        }
                        AutoSoup.mc.thePlayer.inventory.currentItem = slot;
                    }
                }
            }
            else if (this.autoOpen.getValue() && this.fill.getValue()) {
                final int wholeInv = this.getSoupInWholeInventory();
                if (wholeInv != -1) {
                    this.openInventory();
                }
            }
        }
        else if (AutoSoup.mc.currentScreen instanceof GuiInventory && this.fill.getValue()) {
            final int emptySoup = this.getEmptySoup();
            if (emptySoup != -1) {
                if (Math.sin(ThreadLocalRandom.current().nextDouble(0.0, 6.283185307179586)) <= 0.5) {
                    AutoSoup.mc.playerController.windowClick(AutoSoup.mc.thePlayer.inventoryContainer.windowId, emptySoup, 1, 4, AutoSoup.mc.thePlayer);
                }
            }
            else {
                final int slot2 = this.getSoupExceptHotbar();
                boolean full = true;
                for (int i = 0; i < 9; ++i) {
                    final ItemStack item = AutoSoup.mc.thePlayer.inventory.mainInventory[i];
                    if (item == null) {
                        full = false;
                        break;
                    }
                }
                if (this.autoClose.getValue() && (slot2 == -1 || full)) {
                    AutoSoup.mc.thePlayer.closeScreen();
                    AutoSoup.mc.displayGuiScreen(null);
                    AutoSoup.mc.setIngameFocus();
                    return;
                }
                AutoSoup.mc.playerController.windowClick(AutoSoup.mc.thePlayer.inventoryContainer.windowId, slot2, 0, 1, AutoSoup.mc.thePlayer);
            }
        }
        this.slotOnLastTick = AutoSoup.mc.thePlayer.inventory.currentItem;
    };
    public void openInventory() {
        AutoSoup.mc.getNetHandler().addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
        AutoSoup.mc.displayGuiScreen(new GuiInventory(AutoSoup.mc.thePlayer));
    }

    public int getSoup() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack item = AutoSoup.mc.thePlayer.inventory.mainInventory[i];
            if (item != null && item.getItem() instanceof ItemSoup) {
                return i;
            }
        }
        return -1;
    }

    public int getEmptySoup() {
        if (AutoSoup.mc.currentScreen instanceof GuiInventory) {
            final GuiInventory inventory = (GuiInventory)AutoSoup.mc.currentScreen;
            for (int i = 36; i < 45; ++i) {
                final ItemStack item = inventory.inventorySlots.getInventory().get(i);
                if (item != null && item.getItem() == Items.bowl) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int getSoupExceptHotbar() {
        for (int i = 9; i < AutoSoup.mc.thePlayer.inventory.mainInventory.length; ++i) {
            final ItemStack item = AutoSoup.mc.thePlayer.inventory.mainInventory[i];
            if (item != null && item.getItem() instanceof ItemSoup) {
                return i;
            }
        }
        return -1;
    }

    public int getSoupInWholeInventory() {
        for (int i = 0; i < AutoSoup.mc.thePlayer.inventory.mainInventory.length; ++i) {
            final ItemStack item = AutoSoup.mc.thePlayer.inventory.mainInventory[i];
            if (item != null && item.getItem() instanceof ItemSoup) {
                return i;
            }
        }
        return -1;
    }

}
