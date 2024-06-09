package com.leafclient.leaf.mod.movement.speed

import com.leafclient.leaf.event.game.entity.PlayerMoveEvent
import com.leafclient.leaf.mod.modInstance
import com.leafclient.leaf.mod.movement.Speed
import com.leafclient.leaf.utils.baseSpeed
import com.leafclient.leaf.utils.jumpMotion
import com.leafclient.leaf.utils.client.setting.Mode
import com.leafclient.leaf.utils.strafe
import fr.shyrogan.publisher4k.subscription.Listener
import fr.shyrogan.publisher4k.subscription.Subscribe
import net.minecraft.entity.MoverType
import java.util.*

class BunnyMode: Mode("BunnyHop") {

    private val speed by lazy {
        modInstance<Speed>()
    }

    @Subscribe
    val onPlayerMove = Listener<PlayerMoveEvent> { e ->
        var velocity = speed.velocity
        val hDist = speed.hDist
        val playerSpeed = mc.player.baseSpeed
        if(e.type != MoverType.SELF || mc.player.isOnLadder || mc.player.isRiding ||
            mc.player.isInWater || mc.player.isInLava)
            return@Listener

        if(mc.player.movementInput.moveForward == 0.0F && mc.player.movementInput.moveStrafe == 0.0F) {
            e.apply {
                x = 0.0
                z = 0.0
            }
            speed.velocity = 0.0
            speed.hDist = 0.0
            return@Listener
        }

        val velocities = LinkedList<Double>()
        if(mc.player.onGround) {
            e.y = mc.player.jumpMotion
            velocities.add(1.35 * playerSpeed - 0.01)
            velocities.add(
                velocity * when (speed.intensity) {
                    Speed.Intensity.NORMAL -> 1.535
                    Speed.Intensity.HIGH -> 1.7675
                    Speed.Intensity.EXTREME -> 2.15 - 1.0e-5
                }
            )
            speed.lastTickOnGround = true
        } else {
            velocities.add(hDist - 0.66 * (hDist - playerSpeed))
            if(!speed.lastTickOnGround) {
                velocities.add((hDist - hDist / 160.0) - 1.0e-5)
                velocities.add((hDist - playerSpeed) / 33.3 - 1.0e-2)
                velocities.add(((hDist - playerSpeed) * (1.0 - 0.98)) - 1.0e-2)
            }

            speed.lastTickOnGround = false
        }

        velocity = velocities
            .sortedWith(compareByDescending { it })
            .first()
            .coerceAtLeast(playerSpeed)

        speed.velocity = velocity

        e.strafe(velocity)
    }

}