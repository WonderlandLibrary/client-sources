package com.leafclient.leaf.mod.movement.flight

import com.leafclient.leaf.event.game.entity.PlayerMotionEvent
import com.leafclient.leaf.event.game.entity.PlayerMoveEvent
import com.leafclient.leaf.event.game.entity.PlayerUpdateEvent
import com.leafclient.leaf.event.game.network.PacketEvent
import com.leafclient.leaf.event.game.world.CollisionBoxEvent
import com.leafclient.leaf.extension.timer
import com.leafclient.leaf.extension.timerSpeed
import com.leafclient.leaf.mod.modInstance
import com.leafclient.leaf.mod.movement.Flight
import com.leafclient.leaf.utils.*
import com.leafclient.leaf.utils.client.setting.Mode
import fr.shyrogan.publisher4k.subscription.Listener
import fr.shyrogan.publisher4k.subscription.Subscribe
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.network.play.client.CPacketConfirmTeleport
import net.minecraft.network.play.client.CPacketPlayer
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * TODO: Improve it
 */
class MovedWronglyMode: Mode("Moved wrongly") {

    private val flight by lazy {
        modInstance<Flight>()
    }

    private val MOVED_WRONGLY_THRESHOLD = 0.0625

    @Subscribe
    val onPlayerMotion = Listener<PlayerMotionEvent.Pre> { e ->
        val dir = mc.player.movementYaw
        mc.timer.timerSpeed = flight.speed.toFloat()

        e.isSprinting = true

        if(mc.gameSettings.keyBindJump.isKeyDown) {
            e.posY += MOVED_WRONGLY_THRESHOLD
            mc.player.connection.sendPacket(CPacketPlayer.Position(mc.player.posX, mc.player.posY + MOVED_WRONGLY_THRESHOLD, mc.player.posZ, mc.player.ticksExisted % 5 == 0))
            mc.player.setPosition(mc.player.posX, mc.player.posY + MOVED_WRONGLY_THRESHOLD, mc.player.posZ)
        } else if(mc.gameSettings.keyBindSneak.isKeyDown) {
            e.posY -= MOVED_WRONGLY_THRESHOLD
            mc.player.connection.sendPacket(CPacketPlayer.Position(mc.player.posX, mc.player.posY - MOVED_WRONGLY_THRESHOLD, mc.player.posZ, mc.player.ticksExisted % 5 == 0))
            mc.player.setPosition(mc.player.posX, mc.player.posY - MOVED_WRONGLY_THRESHOLD, mc.player.posZ)
        } else if(mc.player.isMoving) {
            e.posX -= sin(dir) * MOVED_WRONGLY_THRESHOLD
            e.posZ += cos(dir) * MOVED_WRONGLY_THRESHOLD
            mc.player.connection.sendPacket(CPacketPlayer.Position(mc.player.posX - (sin(dir) * MOVED_WRONGLY_THRESHOLD), mc.player.posY, mc.player.posZ + (cos(dir) * MOVED_WRONGLY_THRESHOLD), mc.player.ticksExisted % 5 == 0))

            //flags a lot more but is fixable
            //mc.player.setPosition(mc.player.posX - (sin(dir) * MOVED_WRONGLY_THRESHOLD), mc.player.posY, mc.player.posZ + (cos(dir) * MOVED_WRONGLY_THRESHOLD))

        }
    }

    @Subscribe
    val onPostMotion = Listener<PlayerMotionEvent.Post> {
        if(mc.player.isMoving || mc.gameSettings.keyBindJump.isKeyDown || mc.gameSettings.keyBindSneak.isKeyDown) {
            mc.player.sendWrongPacket()
        }
    }

    @Subscribe
    val onPlayerMove = Listener<PlayerMoveEvent> { e ->
        // We can noclip with this exploit lol
        mc.player.noClip = true
        e.strafe(0.0)
        e.y = 0.0
    }

    @Subscribe
    val onCollisionCheck = Listener<CollisionBoxEvent> { e ->
        e.boundingBox = null
    }

    private fun EntityPlayerSP.sendWrongPacket()
            =  this.connection.sendPacket(CPacketPlayer.Position(mc.player.posX + 1337.0, 2.0, mc.player.posZ + 1337.0, true))

}