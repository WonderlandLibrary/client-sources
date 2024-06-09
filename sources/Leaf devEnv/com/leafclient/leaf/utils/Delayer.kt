package com.leafclient.leaf.utils

/**
 * [Delayer] is an utility class allowing us to measure time between certain actions,
 * they're mostly known as timers but that names conflicts with Minecraft's one.
 */
class Delayer {

    private var startTime = System.currentTimeMillis()

    /**
     * Returns the difference between now and [startTime]
     */
    val currentTime: Long
        get() = (System.currentTimeMillis() - startTime)

    /**
     * Resets the [Delayer]
     */
    fun reset() = apply {
        startTime = System.currentTimeMillis()
    }

    /**
     * Resets the [Delayer]
     */
    fun hasReached(duration: Long) = currentTime >= duration

}