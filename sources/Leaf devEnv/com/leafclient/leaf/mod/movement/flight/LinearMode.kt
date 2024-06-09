package com.leafclient.leaf.mod.movement.flight

import com.leafclient.leaf.event.game.entity.PlayerMoveEvent
import com.leafclient.leaf.extension.timer
import com.leafclient.leaf.extension.timerSpeed
import com.leafclient.leaf.mod.modInstance
import com.leafclient.leaf.mod.movement.Flight
import com.leafclient.leaf.utils.baseSpeed
import com.leafclient.leaf.utils.client.setting.Mode
import com.leafclient.leaf.utils.isMoving
import com.leafclient.leaf.utils.strafe
import fr.shyrogan.publisher4k.subscription.Listener
import fr.shyrogan.publisher4k.subscription.Subscribe

class LinearMode: Mode("Linear") {

    private val flight by lazy {
        modInstance<Flight>()
    }

    @Subscribe
    val onPlayerMove = Listener<PlayerMoveEvent> { e ->
        e.y = 0.0
        if(!mc.player.isMoving)
            e.strafe(0.0)
        else
            e.strafe(flight.speed * flight.speed * mc.player.baseSpeed)
    }

}