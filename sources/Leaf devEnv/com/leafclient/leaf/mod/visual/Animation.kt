package com.leafclient.leaf.mod.visual

import com.leafclient.leaf.event.game.render.ItemRenderEvent
import com.leafclient.leaf.extension.isBlocking
import com.leafclient.leaf.management.mod.ToggleableMod
import com.leafclient.leaf.management.mod.category.Category
import com.leafclient.leaf.mod.movement.NoSlow
import fr.shyrogan.publisher4k.subscription.Listener
import fr.shyrogan.publisher4k.subscription.Subscribe
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.init.Items
import net.minecraft.item.ItemSword
import net.minecraft.util.EnumHand
import net.minecraft.util.EnumHandSide
import net.minecraft.util.math.MathHelper




class Animation: ToggleableMod("Animation", Category.VISUAL) {

    /**
     * Checks whether the current action can be considered as blocking
     */
    private val isConsideredAsBlocking: Boolean
        get() = ((mc.player.isHandActive && mc.player.activeHand == EnumHand.OFF_HAND // 1.12
                && mc.player.heldItemOffhand.item == Items.SHIELD
                && mc.player.heldItemMainhand.item is ItemSword))

    @Subscribe
    val onRenderItem = Listener { e: ItemRenderEvent.Render ->
        val oldBlock = (mc.player.isBlocking && !mc.player.heldItemMainhand.isEmpty && mc.player.heldItemMainhand.item is ItemSword)
        if(oldBlock || isConsideredAsBlocking)
            e.isCancelled = e.itemStack.item == Items.SHIELD && e.hand == EnumHand.OFF_HAND

    }

    @Subscribe
    val onTransformItem = Listener { e: ItemRenderEvent.Transform ->
        val oldBlock = (mc.player.isBlocking && !mc.player.heldItemMainhand.isEmpty && mc.player.heldItemMainhand.item is ItemSword)
        if(e.hand == EnumHand.MAIN_HAND && (oldBlock || isConsideredAsBlocking)) {
            val i = if(mc.player.primaryHand == EnumHandSide.RIGHT) 1F else -1F
            // func178096b
            GlStateManager.translate(0.15f * i, 0.3f, 0.0f)
            GlStateManager.rotate(5f * i, 0.0f, 0.0f, 0.0f)

            if(i > 0F)
                GlStateManager.translate(0.56f, -0.52f, -0.72f * i)
            else
                GlStateManager.translate(0.56f, -0.52f, 0.5F)

            GlStateManager.translate(0.0f, 0.2f * -0.6f, 0.0f)
            GlStateManager.rotate(45.0f * i, 0.0f, 1.0f, 0.0f)

            GlStateManager.scale(1.625f, 1.625f, 1.625f)
            //func178103d
            GlStateManager.translate(-0.5f, 0.2f, 0.0f)
            GlStateManager.rotate(30.0f * i, 0.0f, 1.0f, 0.0f)
            GlStateManager.rotate(-80.0f, 1.0f, 0.0f, 0.0f)
            GlStateManager.rotate(30.0f * i, 0.0f, 1.0f, 0.0f)
        }
    }

    init {
        isRunning = true
    }

}