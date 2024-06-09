package com.leafclient.leaf.extension

import net.minecraft.entity.Entity

interface ExtensionEntity {

    var isBot: Boolean

    var isInWeb: Boolean

}

var Entity.isBot: Boolean
    get() = (this as ExtensionEntity).isBot
    set(value) {
        (this as ExtensionEntity).isBot = value
    }

val Entity.isInWeb: Boolean
    get() = (this as ExtensionEntity).isInWeb
