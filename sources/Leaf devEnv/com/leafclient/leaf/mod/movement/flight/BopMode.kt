package com.leafclient.leaf.mod.movement.flight

import com.leafclient.leaf.event.game.entity.PlayerMotionEvent
import com.leafclient.leaf.event.game.entity.PlayerMoveEvent
import com.leafclient.leaf.mod.modInstance
import com.leafclient.leaf.mod.movement.Flight
import com.leafclient.leaf.utils.baseSpeed
import com.leafclient.leaf.utils.client.setting.Mode
import com.leafclient.leaf.utils.isMoving
import com.leafclient.leaf.utils.jumpMotion
import com.leafclient.leaf.utils.strafe
import fr.shyrogan.publisher4k.subscription.Listener
import fr.shyrogan.publisher4k.subscription.Subscribe
import net.minecraft.client.entity.EntityPlayerSP

class BopMode: Mode("Bop") {

    private val flight by lazy {
        modInstance<Flight>()
    }

    private val FALL_DISTANCE = 3.5
    private val VERTICAL_MOTION = 0.6

    @Subscribe
    val onPlayerMotion = Listener<PlayerMotionEvent.Pre> { e ->
        e.isOnGround = mc.player.belowFallDistance && mc.player.motionY < 0.0
    }

    @Subscribe
    val onPlayerMove = Listener<PlayerMoveEvent> { e ->
        if(flight.startY == 0.0) {
            e.y = mc.player.jumpMotion
            flight.startY = mc.player.posY
            return@Listener
        }

        if(mc.player.belowFallDistance && mc.player.hurtTime > 0)
            e.y = VERTICAL_MOTION

        if(!mc.player.isMoving)
            e.strafe(0.0)
        else
            e.strafe(flight.speed * flight.speed * mc.player.baseSpeed)
    }

    private val EntityPlayerSP.belowFallDistance: Boolean
        get() = mc.player.posY <= flight.startY - FALL_DISTANCE

}