package com.leafclient.leaf.mod.movement

import com.leafclient.leaf.event.game.entity.PlayerMoveEvent
import com.leafclient.leaf.event.game.entity.PlayerStepEvent
import com.leafclient.leaf.management.mod.ToggleableMod
import com.leafclient.leaf.management.mod.category.Category
import com.leafclient.leaf.management.setting.setting
import com.leafclient.leaf.utils.jumpMotion
import fr.shyrogan.publisher4k.subscription.Listener
import fr.shyrogan.publisher4k.subscription.Subscribe

/**
 * A motion step at the time but we should introduce packet & more
 * TODO: Rewrite maybe, one day. Not a priority tho
 */
class Step: ToggleableMod("Step", Category.MOVEMENT) {

    private val acceleration by setting("Acceleration", "Accelerates you during step", false)
    private val ignoreJumpBoost by setting("Ignore Jump boost", "Ignores the jump boost potion effect", false)

    private val jumpMotion: Double
        get() = if(ignoreJumpBoost) 0.42 else mc.player.jumpMotion

    private var climbedHeight = 0.0
    private var obstacleHeight = 0.0
    private var xDirection = 0.0
    private var zDirection = 0.0

    @Subscribe(10)
    val onPlayerStep = Listener { ev: PlayerStepEvent ->
        if(ev.yDiff > 0.5) {
            if(obstacleHeight == 0.0) {
                obstacleHeight = ev.yDiff
                climbedHeight = 0.0

                xDirection = ev.xDiff
                zDirection = ev.zDiff
            }
            ev.isCancelled = true
        } else if(ev.yDiff > 0.0) {
            if(obstacleHeight != 0.0) {
                ev.isCancelled = true
            } else {
                obstacleHeight = 0.0
                climbedHeight = 0.0
            }
        }
    }

    @Subscribe(10)
    val onPlayerMove = Listener { ev: PlayerMoveEvent ->
        if(!mc.player.isInWater && !mc.player.isInLava) {
            mc.player.stepHeight = 1.2F
        } else {
            mc.player.stepHeight = 0.5F
        }
        if(obstacleHeight > 0.0) {
            ev.isCancelled = true

            if(climbedHeight == 0.0) {
                jumpMotion.let { motion ->
                    ev.y = motion
                }
            }
            if(climbedHeight >= obstacleHeight)  {
                obstacleHeight = 0.0
                climbedHeight = 0.0

                if(acceleration) {
                    ev.apply {
                        val speed = 0.265
                        x = when {
                            xDirection > 0 -> speed
                            xDirection < 0 -> -speed
                            else -> 0.0
                        }
                        z = when {
                            zDirection > 0 -> speed
                            zDirection < 0 -> -speed
                            else -> 0.0
                        }
                        y = 0.0
                    }
                } else {
                    ev.apply {
                        x = xDirection
                        z = zDirection
                        y = 0.0
                    }
                }
            } else {
                if(ev.y.coerceAtLeast(0.0) == 0.0) {
                    jumpMotion.let { jumpMotion ->
                        val motion = (jumpMotion - 0.08 * 0.9800000190734863) * 0.9800000190734863 // Calculates the motion to ~1E-2 pretty much
                        ev.y = motion
                    }
                }
            }
            climbedHeight += ev.y.coerceAtLeast(0.0)
        }
    }

    override val suffix: String
        get() = if(mc.player.stepHeight > 0.5) "${mc.player.stepHeight}" else ""

    override fun onDisable() {
        if(mc.player != null)
            mc.player.stepHeight = 0.5F
    }

    init {
        isRunning = true
    }

}