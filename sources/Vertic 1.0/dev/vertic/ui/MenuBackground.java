package dev.vertic.ui;

import dev.vertic.Utils;
import dev.vertic.util.render.DrawUtil;
import dev.vertic.util.render.ShaderUtil;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.util.List;

public class MenuBackground implements Utils {
    private static final ShaderUtil program = new ShaderUtil("vertic/shaders/menu/background.frag");

    private static Framebuffer tempFBO = new Framebuffer(mc.displayWidth, mc.displayHeight, true);

    public static void run(float partialTicks, List<Runnable> runnable) {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        // Prevent rendering
        if (!Display.isVisible() || mc.gameSettings.ofFastRender) {
//            DrawUtil.start();
            DrawUtil.image(new ResourceLocation("vertic/background.png"), 0, 0, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
            DrawUtil.stop();
            return;
        }
        update();

        // program ids
        final int programID = program.getProgramID();

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableAlpha();

        mc.getFramebuffer().bindFramebuffer(true);
        GL20.glUseProgram(programID);
        GL20.glUniform2f(GL20.glGetUniformLocation(programID, "resolution"), mc.displayWidth, mc.displayHeight);
        GL20.glUniform1f(GL20.glGetUniformLocation(programID, "time"), (System.currentTimeMillis() - mc.getStartMillisTime()) / 1000F);
        ShaderUtil.drawQuads();
        GL20.glUseProgram(0);
    }

    public static void update() {
        if (mc.displayWidth != tempFBO.framebufferWidth || mc.displayHeight != tempFBO.framebufferHeight) {
            tempFBO.deleteFramebuffer();
            tempFBO = new Framebuffer(mc.displayWidth, mc.displayHeight, true);
        } else {
            tempFBO.framebufferClear();
        }
    }

}
