package ac.sapphire.client.module.impl.movement

import ac.sapphire.client.event.annotation.Subscribe
import ac.sapphire.client.event.events.player.PlayerMoveEvent
import ac.sapphire.client.ext.player
import ac.sapphire.client.module.AbstractModule
import ac.sapphire.client.module.ModuleCategory

class FastStopModule : AbstractModule("faststop", "FastStop", "", ModuleCategory.MOVEMENT) {
    @Subscribe
    fun onMove(event: PlayerMoveEvent) {
        if (player.movementInput.moveForward == 0F && player.movementInput.moveStrafe == 0F) {
            event.x = 0.0
            event.z = 0.0
        }
    }
}