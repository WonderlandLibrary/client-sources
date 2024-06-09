package com.leafclient.leaf.mod.world

import com.leafclient.leaf.event.game.world.WorldSoundEvent
import com.leafclient.leaf.event.game.entity.PlayerMotionEvent
import com.leafclient.leaf.event.game.render.ItemRenderEvent
import com.leafclient.leaf.extension.isBlocking
import com.leafclient.leaf.management.mod.ToggleableMod
import com.leafclient.leaf.management.mod.category.Category
import fr.shyrogan.publisher4k.subscription.Listener
import fr.shyrogan.publisher4k.subscription.Subscribe
import net.minecraft.init.Items
import net.minecraft.init.SoundEvents
import net.minecraft.item.ItemSword
import net.minecraft.util.EnumHand

class ProtocolSupport: ToggleableMod("ProtocolSupport", Category.WORLD) {

    @Subscribe(100000)
    val onPlayerMotion = Listener<PlayerMotionEvent.Pre> {
        mc.player.isBlocking = mc.gameSettings.keyBindUseItem.isKeyDown
                && !mc.player.heldItemMainhand.isEmpty
                && mc.player.heldItemMainhand.item is ItemSword
    }

    @Subscribe
    val onPlaySound = Listener<WorldSoundEvent> { e ->
        e.isCancelled = e.soundEvent == SoundEvents.ITEM_SHIELD_BLOCK || e.soundEvent == SoundEvents.ITEM_ARMOR_EQUIP_GENERIC
    }

    /**
     * Removes the rendering of the shield in the second hand while [ProtocolSupport] is
     * enabled.
     */
    @Subscribe
    val onRenderItem = Listener<ItemRenderEvent.Render> { e ->
        e.isCancelled = e.hand == EnumHand.OFF_HAND && !e.itemStack.isEmpty && e.itemStack.item == Items.SHIELD
    }

    override fun onDisable() {
        if(mc.player != null)
            mc.player.isBlocking = false
    }

    init {
        isRunning = true
    }

}