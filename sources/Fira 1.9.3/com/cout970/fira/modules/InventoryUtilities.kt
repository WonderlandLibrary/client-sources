package com.cout970.fira.modules

import com.cout970.fira.Config
import com.cout970.fira.util.Utils
import net.minecraft.block.Block
import net.minecraft.init.Blocks
import net.minecraft.inventory.ClickType
import net.minecraft.item.ItemStack

object InventoryUtilities {

    fun takeItems() {
        val p = Utils.mc.player
        if (p.openContainer == p.inventoryContainer) return

        p.openContainer.inventorySlots.forEach { slot ->
            if (slot.inventory != p.inventory) {
                val contents = slot.stack
                if (!contents.isEmpty && (!Config.InventoryUtilities.onlyShulkers || contents.isShulkerBox())) {
                    Utils.mc.playerController.windowClick(p.openContainer.windowId, slot.slotNumber, 0, ClickType.QUICK_MOVE, p)
                }
            }
        }
    }

    private fun ItemStack.isShulkerBox(): Boolean {
        return when (Block.getBlockFromItem(item)) {
            Blocks.WHITE_SHULKER_BOX -> true
            Blocks.ORANGE_SHULKER_BOX -> true
            Blocks.MAGENTA_SHULKER_BOX -> true
            Blocks.LIGHT_BLUE_SHULKER_BOX -> true
            Blocks.YELLOW_SHULKER_BOX -> true
            Blocks.LIME_SHULKER_BOX -> true
            Blocks.PINK_SHULKER_BOX -> true
            Blocks.GRAY_SHULKER_BOX -> true
            Blocks.SILVER_SHULKER_BOX -> true
            Blocks.CYAN_SHULKER_BOX -> true
            Blocks.PURPLE_SHULKER_BOX -> true
            Blocks.BLUE_SHULKER_BOX -> true
            Blocks.BROWN_SHULKER_BOX -> true
            Blocks.GREEN_SHULKER_BOX -> true
            Blocks.RED_SHULKER_BOX -> true
            Blocks.BLACK_SHULKER_BOX -> true
            else -> false
        }
    }

    fun dropItems() {
        val p = Utils.mc.player
        if (p.openContainer == p.inventoryContainer) return

        p.openContainer.inventorySlots.forEach { slot ->
            if (slot.inventory == p.inventory) {
                val contents = slot.stack
                if (!contents.isEmpty && (!Config.InventoryUtilities.onlyShulkers || contents.isShulkerBox())) {
                    Utils.mc.playerController.windowClick(p.openContainer.windowId, slot.slotNumber, 0, ClickType.QUICK_MOVE, p)
                }
            }
        }
    }
}