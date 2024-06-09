// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module.modules;

import net.minecraft.entity.player.EntityPlayer;
import net.andrewsnetwork.icarus.event.events.PreMotion;
import net.andrewsnetwork.icarus.event.Event;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.andrewsnetwork.icarus.values.Value;
import net.andrewsnetwork.icarus.module.Module;

public class InventoryTweak extends Module
{
    private final Value<Integer> id;
    
    public InventoryTweak() {
        super("InventoryTweak", -2252579, Category.PLAYER);
        this.id = new Value<Integer>("inventorytweak_Id", "id", 1, this);
        this.setTag("Inventory Tweak");
    }
    
    private int findInventoryItem(final int itemID) {
        for (int o = 9; o < 45; ++o) {
            if (InventoryTweak.mc.thePlayer.inventoryContainer.getSlot(o).getHasStack()) {
                final ItemStack item = InventoryTweak.mc.thePlayer.inventoryContainer.getSlot(o).getStack();
                if (item != null && Item.getIdFromItem(item.getItem()) == itemID) {
                    return o;
                }
            }
        }
        return -1;
    }
    
    private int findHotbarItem(final int itemID) {
        for (int o = 0; o < 9; ++o) {
            final ItemStack item = InventoryTweak.mc.thePlayer.inventory.getStackInSlot(o);
            if (item != null && Item.getIdFromItem(item.getItem()) == itemID) {
                return o;
            }
        }
        return -1;
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof PreMotion && (InventoryTweak.mc.thePlayer.inventory.getCurrentItem() == null || InventoryTweak.mc.thePlayer.inventory.getCurrentItem().getItem() == null)) {
            final int slotId = this.findInventoryItem(this.id.getValue());
            if (slotId != -1) {
                try {
                    InventoryTweak.mc.playerController.windowClick(0, slotId, InventoryTweak.mc.thePlayer.inventory.currentItem, 2, InventoryTweak.mc.thePlayer);
                }
                catch (Exception ex) {}
            }
        }
    }
}
