package wtf.diablo.client.util.render.gl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;

/**
 *
 * From cedo, pretty sure open source but its alright. have permission to use anyways
 *
 * @author Creida
 * @since 5/22/2023, 1.0.0
 * @version 1.0.0
 */
public final class FramebufferUtils {
    private static final Minecraft mc = Minecraft.getMinecraft();

    /**
     * Creates a new Framebuffer if the current one is null or the size of the screen has changed
     * @param framebuffer the framebuffer you want to create.
     * @return Framebuffer
     */
    public static Framebuffer createFrameBuffer(
            final Framebuffer framebuffer
    ) {
        return createFrameBuffer(framebuffer, false);
    }

    /**
     * Creates a new Framebuffer if the current one is null or the size of the screen has changed
     * @param framebuffer the framebuffer you want to create.
     * @param depth if you want to create a depth buffer
     * @return Framebuffer
     */
    public static Framebuffer createFrameBuffer(
            final Framebuffer framebuffer,
            final boolean depth
    ) {
        if (needsNewFramebuffer(framebuffer)) {
            if (framebuffer != null) {
                framebuffer.deleteFramebuffer();
            }
            return new Framebuffer(mc.displayWidth, mc.displayHeight, depth);
        }
        return framebuffer;
    }

    /**
     * Checks if the current framebuffer is null or the size of the screen has changed
     * @param framebuffer the framebuffer you want to check
     * @return checking if framebuffer is null and it doesnt equal display width or height.
     */
    public static boolean needsNewFramebuffer(
            final Framebuffer framebuffer
    ) {
        return framebuffer == null || framebuffer.framebufferWidth != mc.displayWidth || framebuffer.framebufferHeight != mc.displayHeight;
    }
}
