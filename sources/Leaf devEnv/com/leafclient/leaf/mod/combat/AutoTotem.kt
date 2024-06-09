package com.leafclient.leaf.mod.combat

import com.leafclient.leaf.event.game.entity.PlayerMotionEvent
import com.leafclient.leaf.management.mod.ToggleableMod
import com.leafclient.leaf.management.mod.category.Category
import com.leafclient.leaf.management.setting.contraint.bound
import com.leafclient.leaf.management.setting.contraint.increment
import com.leafclient.leaf.management.setting.setting
import com.leafclient.leaf.utils.getItemCount
import com.leafclient.leaf.utils.getItemSlot
import com.leafclient.leaf.utils.swapItem
import fr.shyrogan.publisher4k.subscription.Listener
import fr.shyrogan.publisher4k.subscription.Subscribe
import net.minecraft.client.gui.inventory.GuiInventory
import net.minecraft.init.Items
import net.minecraft.item.Item

/**
 * @author auto on 4/23/2020
 */
class AutoTotem: ToggleableMod("AutoTotem", Category.FIGHT) {

    private val health by setting("Health", 3.0) {
        bound(0.5, 10.0)
        increment(0.5)
    }

    @Subscribe
    val onPreMotion = Listener { e: PlayerMotionEvent.Pre ->
        //need to add the ability to switch back to previous item after health rises
        if (mc.player.health / 2.0 <= health) {
            if (mc.currentScreen == null || mc.currentScreen is GuiInventory) {
                val offhand: Item = mc.player.heldItemOffhand.item
                if (mc.player.getItemCount(Items.TOTEM_OF_UNDYING) > 0 && offhand != Items.TOTEM_OF_UNDYING) {
                    mc.player.swapItem(mc.player.getItemSlot(Items.TOTEM_OF_UNDYING), 45)
                }
            }
        }
    }
}