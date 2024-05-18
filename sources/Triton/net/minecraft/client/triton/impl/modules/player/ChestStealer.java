// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.impl.modules.player;

import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.triton.management.event.Event;
import net.minecraft.client.triton.management.event.EventTarget;
import net.minecraft.client.triton.management.event.events.UpdateEvent;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.Module.Mod;
import net.minecraft.client.triton.management.option.Option.Op;
import net.minecraft.client.triton.utils.ClientUtils;
import net.minecraft.client.triton.utils.Timer;
import net.minecraft.item.ItemStack;

@Mod(displayName = "Chest Stealer")
public class ChestStealer extends Module
{
	@Op(min=20, max=200, increment=10, name = "Delay")
	private int wait;
	Timer timer = new Timer();
	
    @EventTarget
    private void onUpdate(final UpdateEvent event) {
        if (event.getState().equals(Event.State.PRE) && ClientUtils.mc().currentScreen instanceof GuiChest) {
            final GuiChest guiChest = (GuiChest)ClientUtils.mc().currentScreen;
            boolean full = true;
            ItemStack[] mainInventory;
            for (int length = (mainInventory = ClientUtils.player().inventory.mainInventory).length, i = 0; i < length; ++i) {
                final ItemStack item = mainInventory[i];
                if (item == null) {
                    full = false;
                    break;
                }
            }
            if (!full && timer.delay(wait)) {
                for (int index = 0; index < guiChest.lowerChestInventory.getSizeInventory(); ++index) {
                    final ItemStack stack = guiChest.lowerChestInventory.getStackInSlot(index);
                    if (stack != null) {
                    	timer.reset();
                        ClientUtils.playerController().windowClick(guiChest.inventorySlots.windowId, index, 0, 1, ClientUtils.player());
                        break;
                    }
                }
            }
        }
    }
}
