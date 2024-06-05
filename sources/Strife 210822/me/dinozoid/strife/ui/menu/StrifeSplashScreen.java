package me.dinozoid.strife.ui.menu;

import me.dinozoid.strife.shader.Shader;
import me.dinozoid.strife.shader.implementations.MenuShader;
import me.dinozoid.strife.util.MinecraftUtil;
import me.dinozoid.strife.util.render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class StrifeSplashScreen {

    private final int max;
    private int progress;
    private String text;

    public StrifeSplashScreen() {
        max = 10;
    }

    public void drawScreen() {

        ScaledResolution sc = new ScaledResolution(Minecraft.getMinecraft());
        int scaleFactor = sc.getScaleFactor();

        //create the framebuffer
        Framebuffer framebuffer = new Framebuffer(sc.getScaledWidth() * scaleFactor, sc.getScaledHeight() * scaleFactor, true);
        framebuffer.bindFramebuffer(true);

        // Sets up 2D rendering
        GlStateManager.matrixMode(GL11.GL_PROJECTION);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0D, sc.getScaledWidth(), sc.getScaledHeight(), 0.0D, 1000.0D, 3000.0D);
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0.0F, 0.0F, -2000.0F);
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        GlStateManager.disableDepth();
        GlStateManager.enableTexture2D();

        GlStateManager.color(0, 0, 0, 0);

        // This is where your gui is drawn
        drawProgress(sc);
        GlStateManager.resetColor();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        resetTextureState();

        framebuffer.unbindFramebuffer();

        // Render the framebuffer to the screen
        framebuffer.framebufferRender(sc.getScaledWidth() * scaleFactor, sc.getScaledHeight() * scaleFactor);

        // Enable alpha drawing
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);

        Minecraft.getMinecraft().updateDisplay();
    }

    public void drawProgress(ScaledResolution sc) {
        RenderUtil.drawRect(0,0, sc.getScaledWidth(), sc.getScaledHeight(), new Color(18,18,18).getRGB());
        int width = 200;
        int progress = (this.progress / max) * width;
        RenderUtil.drawRect(sc.getScaledWidth() / 2f - 100, sc.getScaledHeight() / 2f - 8, sc.getScaledWidth() / 2f + 100, sc.getScaledHeight() / 2f + 8, 0xff242424);
        RenderUtil.drawRect(sc.getScaledWidth() / 2f - 100 + 2, sc.getScaledHeight() / 2f - 8 + 2, sc.getScaledWidth() / 2f - 100 + progress - 2, sc.getScaledHeight() / 2f + 8 - 2, 0xff910707);
    }

    private void resetTextureState() {
        GlStateManager.textureState[GlStateManager.activeTextureUnit].textureName = -1;
    }

    public int progress() {
        return progress;
    }
    public void progress(int progress, String text) {
        this.progress = progress;
        this.text = text;
        drawScreen();
    }
}
