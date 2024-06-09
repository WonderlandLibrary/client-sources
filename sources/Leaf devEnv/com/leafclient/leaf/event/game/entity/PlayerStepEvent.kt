package com.leafclient.leaf.event.game.entity

import fr.shyrogan.publisher4k.Cancellable

/**
 * Event invoked when a player is stepping a block, can be cancelled.
 */
class PlayerStepEvent(val xDiff: Double, val yDiff: Double, val zDiff: Double): Cancellable()