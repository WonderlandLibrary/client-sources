// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Mod.Collection.Other;

import net.minecraft.client.network.badlion.memes.EventTarget;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.network.badlion.Utils.ClientUtils;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.network.badlion.Events.EventState;
import net.minecraft.client.network.badlion.Events.EventUpdate;
import net.minecraft.client.network.badlion.Mod.Category;
import net.minecraft.client.network.badlion.Utils.TimeMeme;
import net.minecraft.client.network.badlion.Mod.Mod;

public class ChestStealer extends Mod
{
    private TimeMeme timer;
    
    public ChestStealer() {
        super("ChestStealer", Category.OTHER);
        this.setBind(38);
        this.timer = new TimeMeme();
    }
    
    @EventTarget
    private void onUpdate(final EventUpdate event) {
        if (event.state.equals(EventState.PRE) && ClientUtils.mc().currentScreen instanceof GuiChest) {
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
            if (!(ClientUtils.mc().currentScreen instanceof GuiChest)) {
                this.timer.reset();
            }
            if (!full) {
                for (int index = 0; index < guiChest.lowerChestInventory.getSizeInventory(); ++index) {
                    final ItemStack stack = guiChest.lowerChestInventory.getStackInSlot(index);
                    if (stack != null && this.timer.hasPassed(150.0)) {
                        ClientUtils.playerController().windowClick(guiChest.inventorySlots.windowId, index, 0, 1, ClientUtils.player());
                        this.timer.reset();
                        break;
                    }
                }
            }
        }
    }
}
