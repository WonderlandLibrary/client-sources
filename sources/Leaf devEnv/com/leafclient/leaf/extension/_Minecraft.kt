package com.leafclient.leaf.extension

import net.minecraft.client.Minecraft
import net.minecraft.util.Session
import net.minecraft.util.Timer

interface ExtensionMinecraft {

    /**
     * Returns the Minecraft's [mutableSession] and allows its modification
     */
    var mutableSession: Session

    /**
     * Returns minecraft's timer
     */
    val timer: Timer

}

var Minecraft.mutableSession: Session
    get() = this.session
    set(value) {
        (this as ExtensionMinecraft).mutableSession = value
    }

val Minecraft.timer: Timer
    get() = (this as ExtensionMinecraft).timer