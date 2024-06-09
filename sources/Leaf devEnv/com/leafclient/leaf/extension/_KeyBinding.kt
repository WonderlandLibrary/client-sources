package com.leafclient.leaf.extension

import net.minecraft.client.settings.KeyBinding

interface ExtensionKeyBinding {

    var isKeyPressed: Boolean

}

var KeyBinding.isKeyPressed: Boolean
    get() = (this as ExtensionKeyBinding).isKeyPressed
    set(value) {
        (this as ExtensionKeyBinding).isKeyPressed = value
    }