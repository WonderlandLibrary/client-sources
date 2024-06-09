package com.leafclient.leaf.utils.render.shader.framebuffer

import net.minecraft.client.shader.Framebuffer

class QuickFramebuffer(width: Int, height: Int, depth: Boolean):
        Framebuffer(width, height, depth) {

    /**
     * Resizes this framebuffer if the size has changed since the last render
     */
    fun resizeIfRequired(width: Int, height: Int) {
        if(this.framebufferWidth == width && this.framebufferHeight == height)
            // Why would you update this if it's already correct ? :')
            return

        deleteFramebuffer()
        createBindFramebuffer(width, height)
    }

}