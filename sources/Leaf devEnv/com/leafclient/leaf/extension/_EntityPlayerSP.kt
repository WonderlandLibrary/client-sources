package com.leafclient.leaf.extension

import com.leafclient.leaf.event.game.entity.PlayerMotionEvent
import net.minecraft.client.entity.EntityPlayerSP
import java.util.function.Consumer

interface ExtensionEntityPlayerSP {

    /**
     * A boolean used to know if the player is actually blocking or not
     */
    var isBlocking: Boolean

    /**
     * The last [PlayerMotionEvent]
     */
    val lastMotionEvent: PlayerMotionEvent.Pre?

    /**
     * The last yaw sent to the server
     */
    val lastReportedYaw: Float

    /**
     * The last pitch sent to the server
     */
    val lastReportedPitch: Float

    /**
     * Returns whether the player was previously on ground
     */
    val prevOnGround: Boolean

}

var EntityPlayerSP.isBlocking: Boolean
    get() = (this as ExtensionEntityPlayerSP).isBlocking
    set(value) {
        (this as ExtensionEntityPlayerSP).isBlocking = value
    }

val EntityPlayerSP.lastReportedYaw: Float
    get() = (this as ExtensionEntityPlayerSP).lastReportedYaw

val EntityPlayerSP.lastReportedPitch: Float
    get() = (this as ExtensionEntityPlayerSP).lastReportedPitch

val EntityPlayerSP.prevOnGround: Boolean
    get() = (this as ExtensionEntityPlayerSP).prevOnGround