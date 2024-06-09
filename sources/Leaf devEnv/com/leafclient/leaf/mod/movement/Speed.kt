package com.leafclient.leaf.mod.movement

import com.leafclient.leaf.event.game.entity.PlayerUpdateEvent
import com.leafclient.leaf.extension.timer
import com.leafclient.leaf.extension.timerSpeed
import com.leafclient.leaf.management.mod.ToggleableMod
import com.leafclient.leaf.management.mod.category.Category
import com.leafclient.leaf.management.setting.contraint.choice
import com.leafclient.leaf.management.setting.setting
import com.leafclient.leaf.mod.movement.speed.LegitHopMode
import com.leafclient.leaf.mod.movement.speed.BunnyMode
import com.leafclient.leaf.mod.movement.speed.YPortMode
import com.leafclient.leaf.utils.client.setting.Mode
import fr.shyrogan.publisher4k.subscription.Listener
import fr.shyrogan.publisher4k.subscription.Subscribe
import org.lwjgl.input.Keyboard
import kotlin.math.sqrt

class Speed: ToggleableMod("Speed", Category.MOVEMENT, defaultKey = Keyboard.KEY_V) {

    private var mode by setting<Mode>("Mode", BunnyMode()) {
        choice(LegitHopMode(), YPortMode())
    }

    var intensity by setting("Intensity", Intensity.NORMAL) {
        choice(*Intensity.values())
    }

    var velocity = 0.0
    var hDist = 0.0
    var lastTickOnGround = false

    /**
     * Updates the [hDist]
     */
    @Subscribe
    val onPlayerUpdate = Listener<PlayerUpdateEvent.Pre> { e ->
        val diffX = mc.player.posX - mc.player.prevPosX
        val diffZ = mc.player.posZ - mc.player.prevPosZ

        hDist = sqrt(diffX * diffX + diffZ * diffZ)
    }

    override val suffix: String
        get() = mode.label

    /**
     * Resets everything
     */
    override fun onDisable() {
        velocity = 0.0
        hDist = 0.0
        mc.timer.timerSpeed = 1F
    }

    enum class Intensity {
        EXTREME,
        HIGH,
        NORMAL
    }

}