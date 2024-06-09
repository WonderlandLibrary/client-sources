package com.leafclient.leaf.mod.combat

import com.leafclient.leaf.event.game.network.PacketEvent
import com.leafclient.leaf.extension.prevOnGround
import com.leafclient.leaf.management.mod.ToggleableMod
import com.leafclient.leaf.management.mod.category.Category
import com.leafclient.leaf.management.setting.contraint.bound
import com.leafclient.leaf.management.setting.contraint.increment
import com.leafclient.leaf.management.setting.setting
import com.leafclient.leaf.utils.Delayer
import com.leafclient.leaf.utils.math.isInsideBlocks
import fr.shyrogan.publisher4k.subscription.Listener
import fr.shyrogan.publisher4k.subscription.Subscribe
import net.minecraft.entity.EntityLivingBase
import net.minecraft.init.MobEffects
import net.minecraft.network.play.client.CPacketPlayer
import net.minecraft.network.play.client.CPacketUseEntity

class Criticals: ToggleableMod("Criticals", Category.FIGHT) {

    private var hurtTime by setting("Hurt time", true)
    private var delay by setting("Delay", 250L) {
        bound(0L, 1000L)
        increment(10L)
    }

    //Stole that value from Nocheatplus source
    private val FIGHT_CRITICAL_FALLDISTANCE = 0.0625101

    private val delayer = Delayer()
    private var shouldCancel = false
    private var ticksOnGround = 0

    @Subscribe
    val onPacketSend = Listener<PacketEvent.Send> { e ->
        val packet = e.packet
        if(packet is CPacketUseEntity && packet.action == CPacketUseEntity.Action.ATTACK) {
            val entity = packet.getEntityFromWorld(mc.world) ?: return@Listener
            if(isAbleToCrit &&
                ((entity !is EntityLivingBase && entity.hurtResistantTime == 0) || (entity is EntityLivingBase && entity.hurtTime < 5))) {
                println("crit")
                mc.player.connection.sendPacket(CPacketPlayer.Position(
                    mc.player.posX, mc.player.posY + FIGHT_CRITICAL_FALLDISTANCE, mc.player.posZ, false
                ))
                mc.player.connection.sendPacket(CPacketPlayer.Position(
                    mc.player.posX, mc.player.posY, mc.player.posZ, false
                ))
                shouldCancel = true
                println(delayer.currentTime)
                delayer.reset()
            }
        } else if(packet is CPacketPlayer) {
            if(packet.isOnGround)
                ticksOnGround += 2
            else
                ticksOnGround--
            ticksOnGround = ticksOnGround.coerceAtLeast(0)

            if(shouldCancel) {
                e.isCancelled = true
                shouldCancel = false
            }
        }
    }

    private val isAbleToCrit: Boolean
        get() = !mc.player.entityBoundingBox.offset(0.0, FIGHT_CRITICAL_FALLDISTANCE, 0.0).isInsideBlocks(mc.player, mc.world)
                && !mc.player.isPotionActive(MobEffects.BLINDNESS)
                && !mc.player.isOnLadder
                && mc.player.ridingEntity == null
                && !mc.player.isInWater
                && !mc.player.isInLava
                && mc.player.onGround
                && mc.player.motionY.coerceAtLeast(0.0) == 0.0
                && delayer.hasReached(delay)
                && ticksOnGround > 2

}