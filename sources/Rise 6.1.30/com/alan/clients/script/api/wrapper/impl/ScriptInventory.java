package com.alan.clients.script.api.wrapper.impl;

import com.alan.clients.script.api.wrapper.ScriptWrapper;
import net.minecraft.entity.player.InventoryPlayer;

public class ScriptInventory extends ScriptWrapper<InventoryPlayer> {

    public ScriptInventory(InventoryPlayer wrapped) {
        super(wrapped);
    }

    public ScriptItemStack getItemStackInSlot(int slot) {
        return new ScriptItemStack(this.wrapped.getStackInSlot(slot));
    }

//    public void throwItem(final int slot) {
//        MC.playerController.windowClick(MC.thePlayer.inventoryContainer.windowId, slot(slot), 1, 4, MC.thePlayer);
//        ChatUtil.display("Thrown " + slot);
//    }

    private int slot(final int slot) {
        if (slot >= 36) {
            return 8 - (slot - 36);
        }

        if (slot < 9) {
            return slot + 36;
        }

        return slot;
    }

    public ScriptItemStack getHeldItem() {
        return new ScriptItemStack(this.wrapped.getCurrentItem());
    }
}
