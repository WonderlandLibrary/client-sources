package com.leafclient.leaf.mod.world.scaffold

import com.leafclient.leaf.event.game.entity.PlayerMoveEvent
import com.leafclient.leaf.mod.world.Scaffold
import com.leafclient.leaf.utils.jumpMotion
import com.leafclient.leaf.utils.client.setting.Mode
import fr.shyrogan.publisher4k.subscription.Listener
import fr.shyrogan.publisher4k.subscription.Subscribe

/**
 * A tower that basically modifies the motion of the player to jump if there's a block below
 */
class PerfectJumpTower(scaffold: Scaffold): Mode("Perfect jump") {

    private var currentlyJumped = -1.0

    @Subscribe
    val onMove = Listener<PlayerMoveEvent> { e ->
        if(mc.gameSettings.keyBindJump.isKeyDown && scaffold.blockQuantity > 0
            && mc.player.onGround) {
            e.y = mc.player.jumpMotion
            currentlyJumped = 0.0
        }
        if(currentlyJumped >= 0.0) {
            if(currentlyJumped >= 1.0) {
                e.y *= 0.0
                currentlyJumped = -1.0
            } else {
                currentlyJumped += e.y.coerceAtLeast(0.0)
            }
        }
    }

}