package com.leafclient.leaf.management.utils

interface Toggleable {

    /**
     * Returns whether this toggleable object is running or not
     */
    var isRunning: Boolean

    /**
     * Method executed when the toggleable is enabled
     */
    fun onEnable() {}

    /**
     * Method executed when the toggleable is disabled
     */
    fun onDisable() {}

    /**
     * Modifies the [isRunning] to its opposite
     */
    fun toggle() {
        isRunning = !isRunning
    }

}