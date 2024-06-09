package com.leafclient.leaf.mod.combat

import com.leafclient.leaf.event.game.entity.PlayerMotionEvent
import com.leafclient.leaf.extension.isKeyPressed
import com.leafclient.leaf.management.mod.ToggleableMod
import com.leafclient.leaf.management.mod.category.Category
import com.leafclient.leaf.management.setting.asSetting
import com.leafclient.leaf.management.setting.contraint.bound
import com.leafclient.leaf.management.setting.contraint.increment
import com.leafclient.leaf.management.setting.setting
import com.leafclient.leaf.utils.Delayer
import com.leafclient.leaf.utils.doWhenSwappingTo
import fr.shyrogan.publisher4k.subscription.Listener
import fr.shyrogan.publisher4k.subscription.Subscribe
import net.minecraft.init.MobEffects
import net.minecraft.inventory.Slot
import net.minecraft.item.*
import net.minecraft.potion.PotionUtils
import net.minecraft.util.EnumHand
import org.lwjgl.input.Mouse

/**
 * @author auto on 4/11/2020
 */
class AutoHeal: ToggleableMod("AutoHeal", Category.FIGHT) {

    private val health by setting("Health", 3.0) {
        bound(0.5, 10.0)
        increment(0.5)
    }

    private var potion by setting("Potion", true)
    private var up by ::potion.asSetting
        .setting("Up", false)

    private var gapple by setting("Gapple", false)

    private var soup by setting("Soup", false)
    private var drop by ::soup.asSetting
        .setting("Drop", true)

    private val delayer = Delayer()
    private var previousSlotIndex: Int = -1
    private var slot: Slot? = null

    @Subscribe
    val onPreMotion = Listener { e: PlayerMotionEvent.Pre ->
        if (mc.player.health / 2.0 < health) {
            slot = mc.player.inventoryContainer.inventorySlots
                .filter { it.hasStack && it.stack.isValid }
                .sortedBy { it.slotIndex }
                .maxBy { it.stack.healthLevel }
            val slot = this.slot ?: return@Listener

            when(slot.stack.item) {
                is ItemSplashPotion -> {
                    e.rotationPitch = if (up) -90f else 90f
                    e.isCancelled = true
                }
                else -> {
                }
            }
        }
    }

    @Subscribe
    val onPostMotion = Listener<PlayerMotionEvent.Post> { e ->
        if (mc.player.health / 2.0 < health) {
            val slot = this.slot ?: return@Listener
            when(slot.stack.item) {
                is ItemPotion -> {
                    if (!delayer.hasReached(500L)) {
                        return@Listener
                    }
                    mc.player.doWhenSwappingTo(slot.slotIndex) {
                        mc.playerController.processRightClick(mc.player, mc.world, EnumHand.MAIN_HAND)
                    }
                    delayer.reset()

                    this.slot = null
                }
                is ItemSoup -> {
                    if (!delayer.hasReached(100L)) {
                        return@Listener
                    }

                    mc.player.doWhenSwappingTo(slot.slotIndex) {
                        mc.playerController.processRightClick(mc.player, mc.world, EnumHand.MAIN_HAND)
                        if (drop)
                            mc.playerController.sendPacketDropItem(slot.stack)
                    }
                    delayer.reset()
                }
            }
        }
    }

    private val ItemStack.healthLevel: Int
        get() = when (this.item) {
            is ItemSoup -> 3
            is ItemAppleGold -> if (this.metadata == 0) 2 else 10
            is ItemSplashPotion -> {
                PotionUtils.getEffectsFromStack(this)
                    .first {
                        it.potion == MobEffects.INSTANT_HEALTH
                    }.amplifier + 2
            }
            else -> -1
        }

    private val ItemStack.isValid: Boolean
        get() = ((potion && this.item is ItemSplashPotion && PotionUtils.getEffectsFromStack(this).any { it.potion == MobEffects.INSTANT_HEALTH})
            || (gapple && this.item is ItemAppleGold)
            || (soup && this.item is ItemSoup))

}