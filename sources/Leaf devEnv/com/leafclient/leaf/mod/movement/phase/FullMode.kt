package com.leafclient.leaf.mod.movement.phase

import com.leafclient.leaf.event.game.entity.PlayerMotionEvent
import com.leafclient.leaf.event.game.entity.PlayerUpdateEvent
import com.leafclient.leaf.event.game.world.BlockPushEvent
import com.leafclient.leaf.event.game.world.CollisionBoxEvent
import com.leafclient.leaf.mod.modInstance
import com.leafclient.leaf.mod.movement.Phase
import com.leafclient.leaf.mod.world.Scaffold
import com.leafclient.leaf.utils.client.setting.Mode
import com.leafclient.leaf.utils.isInsideBlock
import com.leafclient.leaf.utils.isMoving
import com.leafclient.leaf.utils.movementYaw
import fr.shyrogan.publisher4k.subscription.Listener
import fr.shyrogan.publisher4k.subscription.Subscribe
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.network.play.client.CPacketPlayer
import kotlin.math.cos
import kotlin.math.sin

/**
 * This is from alerithe, i love u alerithe
 */
class FullMode: Mode("Full") {

    @Subscribe(-5)
    val onPlayerUpdate = Listener<PlayerUpdateEvent.Pre> { e ->
        val dir = mc.player.movementYaw
        val vX = -sin(dir)
        val vZ = cos(dir)
        if(mc.player.isMoving) {
            if(mc.player.collidedHorizontally) {
                val x = vX * 0.006
                val z = vZ * 0.006
                mc.player.phaseTo(mc.player.posX + x, mc.player.posY, mc.player.posZ + z)
            } else if(mc.player.hurtTime == 9 || mc.player.isSneaking) {
                val x = vX * 0.5
                val z = vZ * 0.5
                mc.player.phaseTo(mc.player.posX + x, mc.player.posY, mc.player.posZ + z)
            }
        } else if(mc.player.isSneaking) {
            mc.player.connection.sendPacket(CPacketPlayer.Position(
                mc.player.posX, mc.player.posY - 1.0, mc.player.posZ, true
            ))
        }
    }

    @Subscribe(-5)
    val onPlayerMotion = Listener<PlayerMotionEvent.Pre> { e ->
        mc.player.noClip = true
        if(mc.player.isMoving) {
            e.isSprinting = false
        }
    }

    @Subscribe(-5)
    val onPlayerCollision = Listener<CollisionBoxEvent> { e ->
        if(!mc.player.collidedHorizontally && (e.boundingBox?.minY ?: 0.0) > mc.player.posY) {
            e.boundingBox = null
        }
    }

    private fun EntityPlayerSP.phaseTo(x: Double, y: Double, z: Double) {
        mc.player.connection.sendPacket(CPacketPlayer.Position(x, y, z, true))
        mc.player.connection.sendPacket(CPacketPlayer.Position(x * 11.0, 2.0, z * 11.0, false))
        mc.player.setPosition(x, y, z)
    }

}