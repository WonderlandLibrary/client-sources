/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import winter.event.EventListener;
import winter.event.events.UpdateEvent;
import winter.module.Module;
import winter.utils.other.Timer;

public class ChestStealer
extends Module {
    private Timer timer;
    private boolean zoom = false;

    public ChestStealer() {
        super("Chest Stealer", Module.Category.World, -8412);
        this.setBind(22);
        this.timer = new Timer();
    }

    @EventListener
    public void onUpdate(UpdateEvent event) {
        if (event.isPre() && this.mc.currentScreen instanceof GuiChest) {
            GuiChest guiChest = (GuiChest)this.mc.currentScreen;
            boolean full = true;
            boolean empty = true;
            int index = 0;
            while (index < guiChest.lowerChestInventory.getSizeInventory()) {
                ItemStack stack = guiChest.lowerChestInventory.getStackInSlot(index);
                if (stack != null) {
                    empty = false;
                }
                ++index;
            }
            if (empty) {
                this.mc.thePlayer.closeScreen();
            }
            ItemStack[] mainInventory = this.mc.thePlayer.inventory.mainInventory;
            int length = mainInventory.length;
            int i2 = 0;
            while (i2 < length) {
                ItemStack item = mainInventory[i2];
                if (item == null) {
                    full = false;
                    break;
                }
                ++i2;
            }
            if (!full) {
                index = 0;
                while (index < guiChest.lowerChestInventory.getSizeInventory()) {
                    ItemStack stack = guiChest.lowerChestInventory.getStackInSlot(index);
                    if (stack != null && this.timer.hasPassed(this.zoom ? 0 : 58)) {
                        this.mc.playerController.windowClick(guiChest.inventorySlots.windowId, index, 0, 1, this.mc.thePlayer);
                        this.zoom = !this.zoom;
                        this.timer.reset();
                        break;
                    }
                    ++index;
                }
            }
        }
        if (!(this.mc.currentScreen instanceof GuiChest)) {
            this.timer.reset();
        }
    }
}

