package com.leafclient.leaf.mod.movement

import com.leafclient.leaf.extension.timer
import com.leafclient.leaf.extension.timerSpeed
import com.leafclient.leaf.management.mod.ToggleableMod
import com.leafclient.leaf.management.mod.category.Category
import com.leafclient.leaf.management.setting.contraint.bound
import com.leafclient.leaf.management.setting.contraint.choice
import com.leafclient.leaf.management.setting.contraint.increment
import com.leafclient.leaf.management.setting.setting
import com.leafclient.leaf.mod.movement.flight.BopMode
import com.leafclient.leaf.mod.movement.flight.LinearMode
import com.leafclient.leaf.mod.movement.flight.MovedWronglyMode
import com.leafclient.leaf.utils.client.setting.Mode
import org.lwjgl.input.Keyboard

class Flight: ToggleableMod("Flight", Category.MOVEMENT, defaultKey = Keyboard.KEY_G) {

    private val mode by setting<Mode>("Mode", BopMode()) {
        choice(LinearMode(), MovedWronglyMode())
    }

    var speed by setting("Speed", 1.0) {
        bound(0.1, 10.0)
        increment(0.1)
    }

    var startY = 0.0

    override fun onDisable() {
        startY = 0.0
        mc.timer.timerSpeed = 1.0F
    }

}