package com.leafclient.leaf.extension

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.entity.RenderManager

interface ExtensionRenderManager {

    val isRenderOutline: Boolean

}

val RenderManager.isRenderOutlines: Boolean
    get() = (Minecraft.getMinecraft().renderManager as ExtensionRenderManager)
        .isRenderOutline