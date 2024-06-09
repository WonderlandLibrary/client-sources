package com.leafclient.leaf.extension

import net.minecraft.client.multiplayer.PlayerControllerMP

interface ExtensionPlayerController {

    fun syncCurrentItem()

}

fun PlayerControllerMP.syncCurrentItem()
    = (this as ExtensionPlayerController).syncCurrentItem()