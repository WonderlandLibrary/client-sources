package com.leafclient.leaf.mod.movement

import com.leafclient.leaf.event.game.world.BlockPushEvent
import com.leafclient.leaf.management.mod.ToggleableMod
import com.leafclient.leaf.management.mod.category.Category
import com.leafclient.leaf.management.setting.asSetting
import com.leafclient.leaf.management.setting.contraint.bound
import com.leafclient.leaf.management.setting.contraint.choice
import com.leafclient.leaf.management.setting.contraint.increment
import com.leafclient.leaf.management.setting.setting
import com.leafclient.leaf.mod.movement.phase.*
import com.leafclient.leaf.utils.client.setting.Mode
import fr.shyrogan.publisher4k.subscription.Listener
import fr.shyrogan.publisher4k.subscription.Subscribe

class Phase: ToggleableMod("Phase", Category.MOVEMENT) {

    var mode by setting<Mode>("Mode", NormalMode()) {
        choice(
            FullMode()
        )
    }
    var speed by ::mode.asSetting
        .setting("Speed", 10) {
            bound(1, 10)
            increment(1)
        }

    @Subscribe
    val onPush = Listener<BlockPushEvent> { e ->
        e.isCancelled = true
    }

}