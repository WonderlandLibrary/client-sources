package com.leafclient.leaf.utils.math

/**
 * [GoalNumber] is an utility class used by few mods but that makes the code cleaner,
 * it allows us to set an [objective] number and operate on [value] until our goal is reached.
 */
class GoalNumber(private val objective: Double, private var value: Double = 0.0) {

    /**
     * Checks whether or [GoalNumber] is reached or not.
     */
    val isReached: Boolean
        get() = value >= objective

    /**
     * Adds [double] to the [value]
     */
    operator fun plus(double: Double) = apply {
        value += double
    }

    /**
     * Adds [double] to value
     */
    operator fun plusAssign(double: Double) {
        value += double
    }

}