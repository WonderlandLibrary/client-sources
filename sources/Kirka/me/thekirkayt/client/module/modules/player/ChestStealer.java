/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.player;

import java.util.List;
import me.thekirkayt.client.Client;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.event.Event;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S30PacketWindowItems;

@Module.Mod(displayName="ChestStealer")
public class ChestStealer
extends Module {
    private S30PacketWindowItems packet;

    @EventTarget
    private void onUpdate(UpdateEvent event) {
        if (event.getState().equals((Object)Event.State.PRE)) {
            ClientUtils.mc();
            if (Minecraft.currentScreen instanceof GuiChest) {
                if (!this.isContainerEmpty(ClientUtils.player().openContainer)) {
                    ClientUtils.mc();
                    GuiChest guiChest = (GuiChest)Minecraft.currentScreen;
                    boolean full = true;
                    for (ItemStack item : ClientUtils.player().inventory.mainInventory) {
                        if (item != null) continue;
                        full = false;
                        break;
                    }
                    if (!full) {
                        for (int index = 0; index < guiChest.lowerChestInventory.getSizeInventory(); ++index) {
                            ItemStack stack = guiChest.lowerChestInventory.getStackInSlot(index);
                            if (stack == null) continue;
                            ClientUtils.playerController().windowClick(guiChest.inventorySlots.windowId, index, 0, 1, ClientUtils.player());
                            break;
                        }
                    }
                } else {
                    ClientUtils.player().closeScreen();
                    Client.getNotificationManager().addInfo("Container emptied!");
                }
            }
        }
    }

    public boolean isContainerEmpty(Container container) {
        boolean temp = true;
        int slotAmount = container.inventorySlots.size() == 90 ? 54 : 27;
        for (int i = 0; i < slotAmount; ++i) {
            if (!container.getSlot(i).getHasStack()) continue;
            temp = false;
        }
        return temp;
    }
}

