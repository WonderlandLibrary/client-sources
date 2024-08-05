package fr.dog.util.render.opengl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;

import static org.lwjgl.opengl.EXTFramebufferObject.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_DEPTH24_STENCIL8;
import static org.lwjgl.opengl.GL30.GL_DEPTH_STENCIL_ATTACHMENT;

@SuppressWarnings("unused")
public final class StencilUtil {

    private static void initStencil() {
        final Framebuffer framebuffer = Minecraft.getMinecraft().getFramebuffer();

        framebuffer.bindFramebuffer(false);
        setupFrameBufferObject(framebuffer);
        glClear(GL_STENCIL_BUFFER_BIT);
        glEnable(GL_STENCIL_TEST);

        glStencilFunc(GL_ALWAYS, GL_ONE, GL_ONE);
        glStencilOp(GL_REPLACE, GL_REPLACE, GL_REPLACE);
        glColorMask(false, false, false, false);
    }

    private static void eraseStencil() {
        glColorMask(true, true, true, true);
        glStencilFunc(GL_EQUAL, 1, GL_ONE);
        glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);
    }

    private static void disposeStencil() {
        glDisable(GL_STENCIL_TEST);
    }

    private static void setupFrameBufferObject(final Framebuffer framebuffer) {
        if (framebuffer == null)
            return;

        if (framebuffer.depthBuffer > -1) {
            setupStencilBuffers(framebuffer);
            framebuffer.depthBuffer = -1;
        }
    }

    private static void setupStencilBuffers(final Framebuffer framebuffer) {
        final int stencilBufferID = generateStencilID();

        glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, stencilBufferID);
        glRenderbufferStorageEXT(GL_RENDERBUFFER_EXT, GL_DEPTH24_STENCIL8, framebuffer.framebufferTextureWidth, framebuffer.framebufferTextureHeight);
        glFramebufferRenderbufferEXT(GL_FRAMEBUFFER_EXT, GL_DEPTH_STENCIL_ATTACHMENT, GL_RENDERBUFFER_EXT, stencilBufferID);
        glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, GL_ZERO);
    }

    private static int generateStencilID() {
        return glGenRenderbuffersEXT();
    }

    public static void renderStencil(final Runnable init, final Runnable end) {
        initStencil();
        init.run();
        eraseStencil();
        end.run();
        disposeStencil();
    }
}