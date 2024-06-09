package com.leafclient.leaf.mod.movement.speed

import com.leafclient.leaf.event.game.entity.PlayerMoveEvent
import com.leafclient.leaf.mod.combat.KillAura
import com.leafclient.leaf.mod.modInstance
import com.leafclient.leaf.mod.movement.Speed
import com.leafclient.leaf.utils.client.setting.Mode
import com.leafclient.leaf.utils.jumpMotion
import com.leafclient.leaf.utils.movementYaw
import com.leafclient.leaf.utils.strafe
import fr.shyrogan.publisher4k.subscription.Listener
import fr.shyrogan.publisher4k.subscription.Subscribe
import net.minecraft.entity.MoverType
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

class LegitHopMode: Mode("LegitHop")  {

    private val speed by lazy {
        modInstance<Speed>()
    }

    @Subscribe
    val onMove = Listener<PlayerMoveEvent> { e ->
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

        if(mc.player.onGround) {
            e.y = mc.player.jumpMotion
            val dir = mc.player.movementYaw + Random.nextDouble(-0.01, 0.01)
            e.x -= sin(dir) * 0.2
            e.z += cos(dir) * 0.2
            e.strafe()
            speed.lastTickOnGround = true
        } else {
            speed.lastTickOnGround = false
            e.strafe()
        }
    }

}