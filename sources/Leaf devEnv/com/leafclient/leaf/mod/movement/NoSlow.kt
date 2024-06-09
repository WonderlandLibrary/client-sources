package com.leafclient.leaf.mod.movement

import com.leafclient.leaf.event.game.entity.PlayerMotionEvent
import com.leafclient.leaf.event.game.entity.PlayerSlowEvent
import com.leafclient.leaf.event.game.network.PacketEvent
import com.leafclient.leaf.extension.isBlocking
import com.leafclient.leaf.management.mod.ToggleableMod
import com.leafclient.leaf.management.mod.category.Category
import com.leafclient.leaf.management.setting.contraint.bound
import com.leafclient.leaf.management.setting.contraint.increment
import com.leafclient.leaf.management.setting.setting
import fr.shyrogan.publisher4k.subscription.Listener
import fr.shyrogan.publisher4k.subscription.Subscribe
import net.minecraft.client.Minecraft
import net.minecraft.init.Items
import net.minecraft.item.ItemSword
import net.minecraft.network.play.client.CPacketPlayerDigging
import net.minecraft.network.play.client.CPacketPlayerTryUseItem
import net.minecraft.network.play.server.SPacketPlayerPosLook
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos

class NoSlow: ToggleableMod("NoSlow", Category.MOVEMENT) {

    private var itemFactor by setting("Item factor", 1.0F) {
        bound(0.0F, 1.0F)
        increment(0.1F)
    }
    private var attackFactor by setting("Attack factor", 1.0) {
        bound(0.0, 1.0)
        increment(0.1)
    }
    private var keepRotation by setting("Keep rotation", true)

    @Subscribe(-12)
    val onPreMotion = Listener<PlayerMotionEvent.Pre> { e ->
        stopBlocking()
    }

    @Subscribe(-12)
    val onPostMotion = Listener<PlayerMotionEvent.Post> { e ->
        startBlocking()
    }

    @Subscribe
    val onSlowItem = Listener<PlayerSlowEvent.ActiveHand> { e ->
        if(itemFactor == 1.0F)
            e.isCancelled = true
        else
            e.factor = itemFactor
    }

    @Subscribe
    val onSlowAttack = Listener<PlayerSlowEvent.Attack> { e ->
        if(attackFactor == 1.0)
            e.isCancelled = true
        else {
            e.factor = attackFactor
            e.isSprinting = true
        }
    }

    @Subscribe
    val onPacketReceive = Listener<PacketEvent.Receive> { e ->
        val packet = e.packet
        if(packet is SPacketPlayerPosLook && keepRotation) {
            e.packet = SPacketPlayerPosLook(
                packet.x,
                packet.y,
                packet.z,
                mc.player.rotationYaw,
                mc.player.rotationPitch,
                packet.flags,
                packet.teleportId
            )
        }
    }

    init {
        isRunning = true
    }

    companion object {
        private val mc = Minecraft.getMinecraft()

        /**
         * Stops blocking if required
         */
        fun stopBlocking() {
            if((mc.player.isBlocking && !mc.player.heldItemMainhand.isEmpty && mc.player.heldItemMainhand.item is ItemSword)
                || (mc.player.isHandActive && mc.player.getHeldItem(mc.player.activeHand).item == Items.SHIELD)) {
                mc.player.connection.sendPacket(
                    CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN)
                )
            }
        }

        /**
         * Starts blocking if required
         */
        fun startBlocking() {
            if(mc.player.isHandActive && mc.player.getHeldItem(mc.player.activeHand).item == Items.SHIELD) {
                mc.player.connection.sendPacket(
                    CPacketPlayerTryUseItem(EnumHand.OFF_HAND)
                )
            } else if(mc.player.isBlocking && !mc.player.heldItemMainhand.isEmpty
                    && mc.player.heldItemMainhand.item is ItemSword) {
                mc.player.connection.sendPacket(
                    CPacketPlayerTryUseItem(EnumHand.MAIN_HAND)
                )
                mc.player.connection.sendPacket(
                    CPacketPlayerTryUseItem(EnumHand.OFF_HAND)
                )
            }
        }
    }

}