package com.leafclient.leaf.event.game.entity

import fr.shyrogan.publisher4k.Cancellable

abstract class PlayerSlowEvent: Cancellable() {

    /**
     * Event invoked when we're slowed by item use
     */
    class ActiveHand(var factor: Float): PlayerSlowEvent()

    /**
     * Event invoked when we're slowed by attack
     */
    class Attack @JvmOverloads constructor(var factor: Double, var isSprinting: Boolean = false): PlayerSlowEvent()

}