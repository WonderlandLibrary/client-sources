package com.leafclient.leaf.mod.world.scaffold

import com.leafclient.leaf.event.game.entity.PlayerMoveEvent
import com.leafclient.leaf.mod.combat.KillAura
import com.leafclient.leaf.mod.modInstance
import com.leafclient.leaf.mod.world.Scaffold
import com.leafclient.leaf.utils.jumpMotion
import com.leafclient.leaf.utils.math.isInsideBlocks
import com.leafclient.leaf.utils.client.setting.Mode
import fr.shyrogan.publisher4k.subscription.Listener
import fr.shyrogan.publisher4k.subscription.Subscribe

/**
 * A tower that basically modifies the motion of the player to jump if there's a block below
 */
class AirTower: Mode("Air tower") {

    private val scaffold by lazy {
        modInstance<Scaffold>()
    }

    @Subscribe
    val onMove = Listener<PlayerMoveEvent> { e ->
        if(mc.gameSettings.keyBindJump.isKeyDown && scaffold.blockQuantity > 0
            && mc.player.entityBoundingBox.offset(0.0, -0.1, 0.0).isInsideBlocks(mc.player, mc.world)) {
            e.y = mc.player.jumpMotion
        }
    }

}