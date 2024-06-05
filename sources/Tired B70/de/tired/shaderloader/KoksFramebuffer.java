package de.tired.shaderloader;

import de.tired.interfaces.IHook;
import lombok.experimental.UtilityClass;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

@UtilityClass
public class KoksFramebuffer implements IHook {

    public void renderTexture() {
        final ScaledResolution resolution = new ScaledResolution(MC);
        renderFR(0, 0, (float) resolution.getScaledWidth(), (float) resolution.getScaledHeight());
    }

    public void renderFR(float x, float y, float width, float height) {
        if (MC.gameSettings.ofFastRender) return;
        glBegin(GL_QUADS);
        {
            glTexCoord2f(0, 1);
            glVertex2f(x, y);
            glTexCoord2f(0, 0);
            glVertex2f(x, height);
            glTexCoord2f(1, 0);
            glVertex2f(width, height);
            glTexCoord2f(1, 1);
            glVertex2f(width, y);
        }
        glEnd();
    }


    public Framebuffer doFrameBuffer(final Framebuffer framebuffer) {
        if (framebuffer == null || framebuffer.framebufferWidth != MC.displayWidth || framebuffer.framebufferHeight != MC.displayHeight) {
            if (framebuffer != null) {
                framebuffer.deleteFramebuffer();
            }
            return new Framebuffer(MC.displayWidth, MC.displayHeight, false);
        }
        return framebuffer;
    }


    //"RenderFrameBufferFullScreen"
    public void renderFRFscreen(final Framebuffer framebuffer) {
        if (MC.gameSettings.ofFastRender) return;
        final ScaledResolution resolution = new ScaledResolution(MC);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, framebuffer.framebufferTexture);
        GL11.glBegin(GL11.GL_QUADS);
        {
            GL11.glTexCoord2d(0, 1);
            GL11.glVertex2d(0, 0);
            GL11.glTexCoord2d(0, 0);
            GL11.glVertex2d(0, resolution.getScaledHeight());
            GL11.glTexCoord2d(1, 0);
            GL11.glVertex2d(resolution.getScaledWidth(), resolution.getScaledHeight());
            GL11.glTexCoord2d(1, 1);
            GL11.glVertex2d(resolution.getScaledWidth(), 0);
        }
        GL11.glEnd();
        ShaderExtension.deleteProgram();
    }
}
