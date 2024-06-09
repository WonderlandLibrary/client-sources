package com.leafclient.leaf.management.ui.utils

/**
 * An utility class allowing user interface developers to create smooth looking animation
 * and operate on them easily.
 * They're working using the [System.currentTimeMillis] and are efficient in my opinion.
 */
class Animation(var duration: Long, var transition: Transition) {

    // At this point the animation is not running (when equals to -1)
    private var startTime: Long = -1L

    /**
     * A boolean used to know whether the animation should run forward or not.
     */
    var isForward: Boolean = true
        private set

    /**
     * A private variable to save the progression
     */
    private var savedProgression = -1.0

    /**
     * Checks whether the animation is running or not
     */
    val isRunning: Boolean
        get() = startTime != -1L

    /**
     * Returns the [progression] without any transition applied to it.
     */
    private val progression: Double
        get() = (if(isRunning)
            (System.currentTimeMillis() - startTime) / duration.toDouble()
        else
            savedProgression).coerceIn(0.0..1.0)

    val isOver: Boolean
        get() = if(isRunning) {
            progression == 1.0
        } else {
            savedProgression == 1.0
        }

    /**
     * Returns the current value of this animation
     */
    val value: Double
        get() = transition.invoke(if(isForward)
            progression
        else
            1.0 - progression).coerceIn(0.0..1.0)

    /**
     * Resets the animation to its beginning
     */
    fun reset() = apply {
        if(isRunning) {
            startTime = System.currentTimeMillis()
        } else {
            savedProgression = if(isForward)
                0.0
            else
                1.0
        }
    }

    /**
     * A function that changes the state of [Animation.isForward] to [isForward]
     */
    fun setForward(isForward: Boolean) = apply {
        if(this.isForward == isForward)
            return@apply

        startTime = System.currentTimeMillis() - duration + (progression * duration.toDouble()).toLong()
        this.isForward = isForward
        savedProgression = -1.0
    }

    /**
     * A function that changes the running state to [isRunning]
     */
    fun setRunning(isRunning: Boolean) = apply {
        if(this.isRunning == isRunning)
            return@apply

        if(!isRunning) {
            startTime = -1L
            savedProgression = progression
        } else {
            val factor = if(savedProgression != -1.0)
                savedProgression
            else
                0.0
            startTime = System.currentTimeMillis() + (duration * factor).toLong()
        }
    }
    /**
     * A function that changes the progression
     */
    fun setProgression(progression: Double) = apply {
        if(!isRunning) {
            savedProgression = progression
        } else {
            startTime = System.currentTimeMillis() + (duration * progression).toLong()
        }
    }


}