package com.leafclient.leaf.extension

import net.minecraft.util.Timer

interface ExtensionTimer {

    var timerSpeed: Float

}

var Timer.timerSpeed: Float
    get() = (this as ExtensionTimer).timerSpeed
    set(value) {
        (this as ExtensionTimer).timerSpeed = value
    }