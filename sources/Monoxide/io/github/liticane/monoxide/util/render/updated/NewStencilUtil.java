package io.github.liticane.monoxide.util.render.updated;

import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;

import static org.lwjgl.opengl.EXTFramebufferObject.*;
import static org.lwjgl.opengl.EXTPackedDepthStencil.GL_DEPTH_STENCIL_EXT;
import static org.lwjgl.opengl.GL11.*;

/**
 * @author Jinthium (Taken from Older November)
 **/
public class NewStencilUtil {
    public static final Minecraft mc = Minecraft.getMinecraft();

    public static void checkFramebuffer(Framebuffer buffer) {
        if (buffer != null && buffer.depthBuffer > -1) {
            setupFramebuffer(buffer);
            buffer.depthBuffer = -1;
        }
    }

    public static void setupFramebuffer(Framebuffer buffer) {
        glDeleteRenderbuffersEXT(buffer.depthBuffer);

        int bufferID = glGenRenderbuffersEXT();
        glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, bufferID);
        glRenderbufferStorageEXT(GL_RENDERBUFFER_EXT, GL_DEPTH_STENCIL_EXT, mc.displayWidth, mc.displayHeight);
        glFramebufferRenderbufferEXT(GL_FRAMEBUFFER_EXT, GL_STENCIL_ATTACHMENT_EXT, GL_RENDERBUFFER_EXT, bufferID);
        glFramebufferRenderbufferEXT(GL_FRAMEBUFFER_EXT, GL_DEPTH_ATTACHMENT_EXT, GL_RENDERBUFFER_EXT, bufferID);
    }

    public static void initStencil() {
        mc.getFramebuffer().bindFramebuffer(false);
        checkFramebuffer(mc.getFramebuffer());
        glClear(GL_STENCIL_BUFFER_BIT);
        glEnable(GL_STENCIL_TEST);
        glStencilFunc(GL_ALWAYS, 1, 1);
        glStencilOp(GL_REPLACE, GL_REPLACE, GL_REPLACE);
        glColorMask(false, false, false, false);
    }

    public static void readBuffer() {
        glColorMask(true, true, true, true);
        glStencilFunc(GL_EQUAL, 1, 1);
        glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);
    }

    public static void endStencil() {
        glDisable(GL_STENCIL_TEST);
    }

    public static void restartStencil() {
        glEnable(GL_STENCIL_TEST);
    }

    public static void renderStencil(Runnable initRunnable, Runnable endRunnable) {
        initStencil();
        initRunnable.run();
        readBuffer();
        endRunnable.run();
        endStencil();
    }
}
