package com.leafclient.leaf.mod.movement.phase

import com.leafclient.leaf.event.game.entity.PlayerMotionEvent
import com.leafclient.leaf.event.game.entity.PlayerMoveEvent
import com.leafclient.leaf.event.game.world.CollisionBoxEvent
import com.leafclient.leaf.mod.movement.Phase
import com.leafclient.leaf.utils.client.setting.Mode
import com.leafclient.leaf.utils.isInsideBlock
import com.leafclient.leaf.utils.movementYaw
import fr.shyrogan.publisher4k.subscription.Listener
import fr.shyrogan.publisher4k.subscription.Subscribe
import kotlin.math.cos
import kotlin.math.sin

/**
 * @author auto on 4/15/2020
 */
class NormalMode: Mode("Normal") {

    @Subscribe(-5)
    val onPlayerMotion = Listener<PlayerMotionEvent.Pre> { e ->
        if(mc.player.movementInput.moveForward == 0.0F && mc.player.movementInput.moveStrafe == 0.0F)
            return@Listener

        if(mc.player.isInsideBlock || mc.player.collidedHorizontally) {
            val sign = when((System.currentTimeMillis() % 2).toInt()) {
                0 -> -1
                else -> 1
            }
            e.rotationYaw += 90F * sign
            e.isCancelled = true
        }
    }

    @Subscribe(-5)
    val onPlayerMove = Listener<PlayerMoveEvent> { e ->
        if(mc.player.movementInput.moveForward == 0.0F && mc.player.movementInput.moveStrafe == 0.0F)
            return@Listener

        if(mc.player.isInsideBlock || mc.player.collidedHorizontally) {
            val dir = mc.player.movementYaw
            mc.player.noClip = true
            mc.player.onGround = true
            e.y = 0.0

            e.x = -sin(dir) * 0.4
            e.z = cos(dir) * 0.4
            e.isCancelled = true
        }
    }

    @Subscribe
    val onBoundingBox = Listener<CollisionBoxEvent> { e ->
        if(e.boundingBox?.intersects(mc.player.entityBoundingBox) == true)
            e.boundingBox = null
    }
}