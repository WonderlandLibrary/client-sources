package io.github.liticane.monoxide.util.render.shader.util;

import io.github.liticane.monoxide.util.interfaces.Methods;
import net.minecraft.client.shader.Framebuffer;

public class FramebufferHelper implements Methods {

    public static Framebuffer doFrameBuffer(final Framebuffer framebuffer) {
        if (framebuffer == null || framebuffer.framebufferWidth != mc.displayWidth || framebuffer.framebufferHeight != mc.displayHeight) {
            if (framebuffer != null) {
                framebuffer.deleteFramebuffer();
            }
            return new Framebuffer(mc.displayWidth, mc.displayHeight, true);
        }
        return framebuffer;
    }


}