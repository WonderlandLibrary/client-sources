package com.alan.clients.util.gl;

import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public final class GLUtils {

    /**
     * Enables blending with the default blend function.
     */
    public static void enableBlend()
    {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    /**
     * Disables blending.
     */
    public static void disableBlend()
    {
        GlStateManager.disableBlend();
    }

    /**
     * Binds a texture to the OpenGL context.
     * @param texture The texture to bind.
     */
    public static void bindTexture(
            final int texture
    )
    {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
    }

    /**
     * Binds a texture to the OpenGL context.
     * @param texture The texture to bind.
     */
    public static void bindTexture(
            final int texture,
            final int textureUnit
    ) {
        GL13.glActiveTexture(textureUnit);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
    }
}