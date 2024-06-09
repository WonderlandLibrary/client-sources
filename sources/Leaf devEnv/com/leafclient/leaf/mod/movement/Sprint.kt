package com.leafclient.leaf.mod.movement

import com.leafclient.leaf.event.game.entity.*
import com.leafclient.leaf.management.mod.ToggleableMod
import com.leafclient.leaf.management.mod.category.Category
import com.leafclient.leaf.management.setting.setting
import com.leafclient.leaf.utils.movementYaw
import fr.shyrogan.publisher4k.subscription.Listener
import fr.shyrogan.publisher4k.subscription.Subscribe
import net.minecraft.init.MobEffects

class Sprint: ToggleableMod("Sprint", Category.MOVEMENT) {

    private var omnidirectional by setting("Omnidirectional", true)
    private var ignoreActiveHand by setting("Ignore active hand", true)

    /**
     * Checks if everything condition are ok
     */
    private val canSprint: Boolean
        get() = ((!omnidirectional && mc.player.movementInput.moveForward >= 0.8f)
                || (omnidirectional && (mc.player.movementInput.moveForward != 0.0F || mc.player.movementInput.moveStrafe != 0.0F)))
                && !mc.player.movementInput.sneak && (ignoreActiveHand || !mc.player.isHandActive)
                && (mc.player.foodStats.foodLevel > 6.0f || mc.player.capabilities.allowFlying)
                && !mc.player.isPotionActive(MobEffects.BLINDNESS)
                && !mc.player.collidedHorizontally // Damn

    @Subscribe
    val onMove = Listener { e: PlayerMoveEvent ->
        if(canSprint)
            mc.player.isSprinting = true
    }

    @Subscribe
    val onLivingUpdateEvent = Listener<PlayerJumpEvent> {
        if(canSprint) {
            mc.player.isSprinting = true
            if (omnidirectional)
                it.rotationYaw = Math.toDegrees(mc.player.movementYaw).toFloat()
        }
    }

    override val suffix: String
        get() = if(omnidirectional) "Omni" else ""

    init {
        isRunning = true
    }

}