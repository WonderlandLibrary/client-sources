package com.leafclient.leaf.mod.combat.killaura

import com.leafclient.leaf.event.game.entity.PlayerMotionEvent
import com.leafclient.leaf.mod.combat.KillAura
import com.leafclient.leaf.mod.modInstance
import com.leafclient.leaf.utils.math.angleDifference
import com.leafclient.leaf.utils.math.length
import com.leafclient.leaf.utils.client.setting.Mode
import fr.shyrogan.publisher4k.subscription.Listener
import fr.shyrogan.publisher4k.subscription.Subscribe
import net.minecraft.util.math.Vec2f
import java.util.*

class SwitchMode: Mode("Switch") {

    private val ka by lazy {
        modInstance<KillAura>()
    }

    /**
     * Contains the damaged targets
     */
    private val damagedTargets = mutableMapOf<UUID, Int>()

    @Subscribe(-10)
    val onPreMotion = Listener { e: PlayerMotionEvent.Pre ->
        val availableTargets = ka.availableTargets
        if(availableTargets.isEmpty())
            return@Listener

        ka.rotationTarget = availableTargets
            .firstOrNull { damagedTargets.getOrDefault(it.uniqueID, 0) < ka.switchHits }
        if(ka.rotationTarget == null) {
            damagedTargets.clear()
            ka.rotationTarget = availableTargets
                .first()
        }

        val rotation = ka.smoothRotationToTarget
        e.rotationYaw = rotation.x
        e.rotationPitch = rotation.y
        if(ka.lockView)
            e.isViewLocked = true
    }

    @Subscribe(-10)
    val onPostMotion = Listener { e: PlayerMotionEvent.Post ->
        if(ka.rotationTarget != null) {
            val target = ka.rotationTarget!!
            val currentRotation = Vec2f(e.rotationYaw, e.rotationPitch)
            val calculatedRotation = ka.rotationToTarget
            val diff = currentRotation.angleDifference(calculatedRotation).length

            if(diff == 0F && ka.isAbleToAttack) {
                ka.attack(listOf(target))

                val hitNumber = damagedTargets.getOrDefault(target.uniqueID, 0) + 1
                damagedTargets[target.uniqueID] = hitNumber

                ka.delayer.reset()
            }
        }
    }

}