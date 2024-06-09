package com.leafclient.leaf.mod.movement.speed

import com.leafclient.leaf.event.game.entity.PlayerMoveEvent
import com.leafclient.leaf.event.game.network.PacketEvent
import com.leafclient.leaf.extension.timer
import com.leafclient.leaf.extension.timerSpeed
import com.leafclient.leaf.mod.modInstance
import com.leafclient.leaf.mod.movement.Speed
import com.leafclient.leaf.utils.baseSpeed
import com.leafclient.leaf.utils.client.setting.Mode
import com.leafclient.leaf.utils.isMoving
import com.leafclient.leaf.utils.jumpMotion
import com.leafclient.leaf.utils.strafe
import fr.shyrogan.publisher4k.subscription.Listener
import fr.shyrogan.publisher4k.subscription.Subscribe
import net.minecraft.network.play.client.CPacketPlayer
import kotlin.math.max
import kotlin.math.sqrt

class YPortMode: Mode("YPort") {

    private val speed by lazy {
        modInstance<Speed>()
    }

    private val isColliding: Boolean
        get() = mc.world.collidesWithAnyBlock(mc.player.entityBoundingBox.expand(0.2, 0.42, 0.2))

    private val jumpTickDelay: Int
        get() = when(speed.intensity) {
            Speed.Intensity.NORMAL -> 3
            Speed.Intensity.EXTREME, Speed.Intensity.HIGH -> 2
        }

    @Subscribe
    val onMove = Listener<PlayerMoveEvent> { e ->
        if (!mc.player.onGround || mc.player.isOnLadder || mc.player.isRiding || mc.player.isInWater || mc.player.isInLava) {
            return@Listener
        }

        if(!mc.player.isMoving) {
            e.x *= 0.0
            e.z *= 0.0
            speed.velocity = 0.0
            speed.hDist = 0.0
            return@Listener
        }

        val playerSpeed = mc.player.baseSpeed
        val hDist = speed.hDist
        val velocity = speed.velocity
        val tick = mc.player.ticksExisted % jumpTickDelay
        val factor = when(speed.intensity) {
            Speed.Intensity.NORMAL -> 1.336
            Speed.Intensity.HIGH -> 1.7675
            Speed.Intensity.EXTREME -> 2.15 - 1.0e-5
        }
        speed.velocity = when(tick) {
            0 -> max(1.35 * playerSpeed - 0.01, velocity * factor)
            1 -> hDist - 0.66 * (hDist - playerSpeed)
            else -> arrayOf(
                (hDist - hDist / 160.0) - 1.0e-5,
                (hDist - playerSpeed) / 33.3 - 1.0e-2,
                ((hDist - playerSpeed) * (1.0 - 0.98)) - 1.0e-2
            ).max() ?: playerSpeed
        }
        e.strafe(speed.velocity)
    }

    @Subscribe
    val onPacketSend = Listener<PacketEvent.Send> { e ->
        if(!mc.player.onGround || mc.player.isOnLadder || mc.player.isRiding || mc.player.isInWater || mc.player.isInLava || !mc.player.isMoving) {
            return@Listener
        }
        if (e.packet is CPacketPlayer.Position) {
            if (!isColliding && mc.player.ticksExisted % jumpTickDelay == 0) {
                e.packet = CPacketPlayer.Position(mc.player.posX, mc.player.posY + mc.player.jumpMotion, mc.player.posZ, false)
            }
        } else if (e.packet is CPacketPlayer.PositionRotation) {
            if (mc.player.ticksExisted % jumpTickDelay == 0 && !isColliding) {
                mc.player.entityBoundingBox.expand(0.0, 0.42, 0.0)
                e.packet = CPacketPlayer.PositionRotation(mc.player.posX, mc.player.posY + mc.player.jumpMotion, mc.player.posZ, mc.player.rotationYaw, mc.player.rotationPitch, false)
            }
        }

    }

}