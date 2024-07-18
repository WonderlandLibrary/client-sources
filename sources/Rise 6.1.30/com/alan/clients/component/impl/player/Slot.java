package com.alan.clients.component.impl.player;

import com.alan.clients.component.Component;
import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class Slot extends Component {

    private int slot, previousSlot, enabled;

    public void setSlot(final int slot) {
        if (slot < 0 || slot >= 9) return;

        setEnabled();
        this.slot = slot;
    }

    public void setSlotDelayed(final int slot, boolean force) {
        if (Math.random() * Math.random() > 0.25 || force) {
            setSlot(slot);
        } else {
            setEnabled();
        }
    }

    private void setEnabled() {
        if (!wasEnabled() && !isEnabled()) {
            previousSlot = getItemIndex();
        }
        enabled = mc.thePlayer.ticksExisted;
    }

    @EventLink(value = Priorities.VERY_LOW)
    private final Listener<PreUpdateEvent> onPreUpdate = event -> {
        final InventoryPlayer inventory = mc.thePlayer.inventory;

        if (this.isEnabled()) {
            inventory.currentItem = slot;
        } else if (wasEnabled()) {
            inventory.currentItem = previousSlot;
        }
    };

    public boolean isEnabled() {
        return enabled == mc.thePlayer.ticksExisted;
    }

    public boolean wasEnabled() {
        return enabled == mc.thePlayer.ticksExisted - 1;
    }

    public ItemStack getItemStack() {
        return (mc.thePlayer == null || mc.thePlayer.inventoryContainer == null ? null : mc.thePlayer.inventoryContainer.getSlot(getItemIndex() + 36).getStack());
    }

    public Item getItem() {
        ItemStack stack = getItemStack();
        return stack == null ? null : stack.getItem();
    }

    public int getItemIndex() {
        return mc.thePlayer.inventory.currentItem;
    }
}