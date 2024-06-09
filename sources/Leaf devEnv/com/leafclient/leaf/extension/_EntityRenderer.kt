package com.leafclient.leaf.extension

import net.minecraft.client.renderer.EntityRenderer
import net.minecraft.client.shader.ShaderGroup
import net.minecraft.util.ResourceLocation

interface ExtensionEntityRenderer {

    fun setupCamera(partialTicks: Float, pass: Int)

}

fun EntityRenderer.setupCamera(partialTicks: Float, pass: Int) {
    (this as ExtensionEntityRenderer).setupCamera(partialTicks, pass)
}