package com.leafclient.leaf.mod.world

import com.leafclient.leaf.event.game.entity.PlayerUpdateEvent
import com.leafclient.leaf.management.mod.ToggleableMod
import com.leafclient.leaf.management.mod.category.Category
import com.leafclient.leaf.management.setting.contraint.bound
import com.leafclient.leaf.management.setting.contraint.increment
import com.leafclient.leaf.management.setting.setting
import com.leafclient.leaf.utils.Delayer
import fr.shyrogan.publisher4k.subscription.Listener
import fr.shyrogan.publisher4k.subscription.Subscribe
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.client.resources.I18n
import net.minecraft.inventory.ClickType
import net.minecraft.inventory.ContainerChest

class ChestStealer: ToggleableMod("ChestStealer", Category.WORLD) {

    var checkCustomName by setting("Avoid custom name", true)
    var delay by setting("Delay", 200L) {
        bound(0L, 500L)
        increment(10L)
    }

    private var delayer = Delayer()

    @ExperimentalStdlibApi
    @Subscribe
    val onPlayerUpdate = Listener<PlayerUpdateEvent.Pre> { e ->
        val screen = mc.currentScreen
        if(screen is GuiContainer) {
            val container = screen.inventorySlots
            if(container is ContainerChest) {
                if(checkCustomName && container.isNameCustomized)
                    return@Listener

                if(!delayer.hasReached(delay))
                    return@Listener

                val pickedStack = container.inventorySlots
                    .filter { it.hasStack && !it.stack.isEmpty && it.inventory == container.lowerChestInventory }
                    .randomOrNull() ?: return@Listener

                mc.playerController.windowClick(container.windowId, pickedStack.slotIndex, 0, ClickType.QUICK_MOVE, mc.player)
                delayer.reset()
            }
        }
    }

    /**
     * Checks whether this [ContainerChest] has a customized name
     */
    private val ContainerChest.isNameCustomized: Boolean
        get() = lowerChestInventory.hasCustomName() && !lowerChestInventory.name.toLowerCase().equals(I18n.format("tile.chest.name").toLowerCase(), true)

}