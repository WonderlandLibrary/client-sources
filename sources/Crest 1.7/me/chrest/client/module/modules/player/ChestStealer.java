// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.player;

import me.chrest.event.EventTarget;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import me.chrest.utils.ClientUtils;
import net.minecraft.client.gui.inventory.GuiChest;
import me.chrest.event.Event;
import me.chrest.event.events.UpdateEvent;
import me.chrest.utils.Timer;
import me.chrest.client.option.Option;
import me.chrest.client.module.Module;

@Mod(displayName = "Chest Stealer")
public class ChestStealer extends Module
{
    @Option.Op(min = 20.0, max = 200.0, increment = 10.0, name = "Delay")
    private int wait;
    Timer timer;
    
    public ChestStealer() {
        this.timer = new Timer();
    }
    
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
            if (!full && this.timer.delay(this.wait)) {
                for (int index = 0; index < guiChest.lowerChestInventory.getSizeInventory(); ++index) {
                    final ItemStack stack = guiChest.lowerChestInventory.getStackInSlot(index);
                    if (stack != null) {
                        this.timer.reset();
                        ClientUtils.playerController().windowClick(guiChest.inventorySlots.windowId, index, 0, 1, ClientUtils.player());
                        break;
                    }
                }
            }
        }
    }
}
