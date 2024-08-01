package wtf.diablo.client.util.render.gl;

import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

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


    public static void setup2DRendering(boolean blend) {
        if (blend) {
            enableBlend();
        }
        GlStateManager.disableTexture2D();
    }

    public static void setup2DRendering() {
        setup2DRendering(true);
    }

    public static void end2DRendering() {
        GlStateManager.enableTexture2D();
        disableBlend();
    }

    public static void setupLineSmooth() {
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
    }

    public static void disableLineSmooth() {
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }

}
