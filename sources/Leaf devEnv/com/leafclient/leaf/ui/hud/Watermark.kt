package com.leafclient.leaf.ui.hud

import com.leafclient.leaf.Leaf
import com.leafclient.leaf.management.ui.Component

class Watermark: Component() {

    /**
     * Returns the client's name
     */
    val clientName: String
        get() = Leaf.clientName

    /**
     * Returns the client's version
     */
    val clientVersion: String
        get() = Leaf.clientVersion

}