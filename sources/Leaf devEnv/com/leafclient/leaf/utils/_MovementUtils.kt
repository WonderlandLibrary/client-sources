package com.leafclient.leaf.utils

import com.leafclient.leaf.event.game.entity.PlayerMoveEvent
import net.minecraft.client.Minecraft
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.entity.MoverType
import net.minecraft.entity.player.EntityPlayer
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

val EntityPlayerSP.isMoving: Boolean
    get() = movementInput.moveForward != 0.0F || movementInput.moveStrafe != 0.0F

/**
 * Strafes towards the movement direction with a velocity of [velocity]
 */
fun PlayerMoveEvent.strafe(
    velocity: Double = sqrt(x * x + z * z)
) {
    val dir = Minecraft.getMinecraft().player.movementYaw
    x = -sin(dir) * velocity
    z = cos(dir) * velocity
}

fun PlayerMoveEvent.safeWalk(offsetY: Double) {
    val player = Minecraft.getMinecraft().player
    if ((type == MoverType.SELF || type == MoverType.PLAYER) && player.onGround) {
        while (x != 0.0 && player.world
                .getCollisionBoxes(player, player.entityBoundingBox.offset(x, offsetY, 0.0)).isEmpty()) {
            if (x < 0.05 && x >= -0.05) {
                x = 0.0
            } else if (x > 0.0) {
                x -= 0.05
            } else {
                x += 0.05
            }
        }

        while (z != 0.0 && player.world
                .getCollisionBoxes(player, player.entityBoundingBox.offset(0.0, offsetY, z)).isEmpty()
        ) {
            if (z < 0.05 && z >= -0.05) {
                z = 0.0
            } else if (z > 0.0) {
                z -= 0.05
            } else {
                z += 0.05
            }
        }

        while (x != 0.0 && z != 0.0 && player.world
                .getCollisionBoxes(player, player.entityBoundingBox.offset(x, offsetY, z)).isEmpty()
        ) {
            if (x < 0.05 && x >= -0.05) {
                x = 0.0
            } else if (x > 0.0) {
                x -= 0.05
            } else {
                x += 0.05
            }
            if (z < 0.05 && z >= -0.05) {
                z = 0.0
            } else if (z > 0.0) {
                z -= 0.05
            } else {
                z += 0.05
            }
        }
    }
}