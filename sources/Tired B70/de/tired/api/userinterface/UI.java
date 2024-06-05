package de.tired.api.userinterface;

import de.tired.api.util.font.CustomFont;
import de.tired.interfaces.IHook;
import de.tired.shaderloader.ShaderRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public abstract class UI implements IHook {

    public int mouseX, mouseY;

    public abstract void renderUI(int mouseX, int mouseY);

    public boolean isOver(int x, int y, int width, int height, int mouseX, int mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

    public int calculateMiddle(String text, CustomFont fontRenderer, int x, int widht) {
        return (int) ((float) (x + widht) - (fontRenderer.getStringWidth(text) / 2f) - (float) widht / 2);
    }

    public boolean mouseWithinCircle(final int mouseX, final int mouseY, final double x, final double y, final double radius) {
        final double dx = mouseX - x;
        final double dy = mouseY - y;
        return Math.sqrt(dx * dx + dy * dy) < radius;
    }


    public static final ResourceLocation optionsBackground = new ResourceLocation("textures/gui/options_background.png");

    public ScaledResolution scaledResolution;

    public UI() {

    }



    public void renderShaderBackground() {
        ShaderRenderer.renderBG();
    }

    public void renderMinecraftBackground() {
        final float width = scaledResolution.getScaledWidth();
        final float height = scaledResolution.getScaledHeight();
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        MC.getTextureManager().bindTexture(optionsBackground);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldrenderer.pos(0.0D, (double) height, 0.0D).tex(0.0D, (double) ((float) height / 32.0F + (float) 0)).color(64, 64, 64, 255).endVertex();
        worldrenderer.pos((double) width, (double) height, 0.0D).tex((double) ((float) width / 32.0F), (double) ((float) height / 32.0F + (float) 0)).color(64, 64, 64, 255).endVertex();
        worldrenderer.pos((double) width, 0.0D, 0.0D).tex((double) ((float) width / 32.0F), (double) 0).color(64, 64, 64, 255).endVertex();
        worldrenderer.pos(0.0D, 0.0D, 0.0D).tex(0.0D, (double) 0).color(64, 64, 64, 255).endVertex();
        tessellator.draw();
    }

    public void update() {
        scaledResolution = new ScaledResolution(MC);
    }


}
