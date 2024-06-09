package com.leafclient.leaf.utils

import com.leafclient.leaf.extension.syncCurrentItem
import net.minecraft.client.Minecraft
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.inventory.ClickType
import net.minecraft.inventory.Container
import net.minecraft.item.Item
import net.minecraft.network.play.client.CPacketCloseWindow
import net.minecraft.network.play.client.CPacketEntityAction


/**
 * Applies [action] when swapping to [slot] in the hot bar,
 * if the [slot] is an inventory slot, the item in slot 8 is swapped to apply the action
 * and doesn't swap back! (For anti-cheats)
 */
@JvmOverloads fun EntityPlayerSP.doWhenSwappingTo(slot: Int, ghost: Boolean = true, action: () -> Unit) {
    val mc = Minecraft.getMinecraft()

    val prevSlot = this.inventory.currentItem

    if(slot > 8) {
        this.connection.sendPacket(CPacketEntityAction(this, CPacketEntityAction.Action.OPEN_INVENTORY))
        this.inventory.currentItem = 8
        mc.playerController.windowClick(this.inventoryContainer.windowId, slot, 8, ClickType.SWAP, this)
        this.connection.sendPacket(CPacketCloseWindow())
    } else if(slot != this.inventory.currentItem) {
        this.inventory.currentItem = slot
    }
    mc.playerController.syncCurrentItem()
    action.invoke()
    if(ghost) {
        this.inventory.currentItem = prevSlot
        mc.playerController.syncCurrentItem()
    }
}

/**
 * Swaps the current item to [slot]
 * Returns true if that was a success or false if it was not possible
 */
fun EntityPlayerSP.setCurrentItem(slot: Int): Boolean {
    if(slot > 8)
        return false
    
    val mc = Minecraft.getMinecraft()
    this.inventory.currentItem = slot
    mc.playerController.syncCurrentItem()
    return true
}

/**
 * Swaps the [slot] to [targetSlot]
 */
fun EntityPlayerSP.swapItem(slot: Int, targetSlot: Int = inventory.currentItem){
    val mc = Minecraft.getMinecraft()
    this.connection.sendPacket(CPacketEntityAction(this, CPacketEntityAction.Action.OPEN_INVENTORY))
    mc.playerController.windowClick(mc.player.inventoryContainer.windowId, slot, 0, ClickType.PICKUP, mc.player)
    mc.playerController.windowClick(mc.player.inventoryContainer.windowId, targetSlot, 0, ClickType.PICKUP, mc.player)
    mc.playerController.windowClick(mc.player.inventoryContainer.windowId, slot, 0, ClickType.PICKUP, mc.player)
    mc.playerController.updateController()
    this.connection.sendPacket(CPacketCloseWindow())
}

/**
 * Tries to find [item] in [container]
 * Returns the slot if [item] was found
 */
fun EntityPlayerSP.getItemSlot(item: Item, container: Container = inventoryContainer): Int {
    var slot = 0
    for (i in container.inventorySlots) {
        if (container.getSlot(i.slotIndex).hasStack) {
            val itemStack = container.getSlot(i.slotIndex).stack
            if (itemStack.item === item)
                slot = i.slotIndex
        }
    }
    return slot
}


/**
 * Returns the total amount of [item] in [container]
 */
fun EntityPlayerSP.getItemCount( item: Item, container: Container = inventoryContainer): Int {
    var itemCount = 0
    for (i in container.inventorySlots) {
        if (container.getSlot(i.slotIndex).hasStack) {
            val itemStack = container.getSlot(i.slotIndex).stack
            if (itemStack.item === item) {
                itemCount += itemStack.count
            }
        }
    }
    return itemCount
}
