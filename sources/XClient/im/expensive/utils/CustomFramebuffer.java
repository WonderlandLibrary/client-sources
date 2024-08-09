package im.expensive.utils;

import com.mojang.blaze3d.platform.GlStateManager;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.shader.ShaderUtil;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;

public class CustomFramebuffer extends Framebuffer {

    private boolean linear;

    public CustomFramebuffer(boolean useDepthIn) {
        super(1, 1, useDepthIn, Minecraft.IS_RUNNING_ON_MAC);
    }

    private static void resizeFramebuffer(Framebuffer framebuffer) {
        Minecraft mc = Minecraft.getInstance();
        if (framebuffer.framebufferWidth != mc.getMainWindow().getWidth() || framebuffer.framebufferHeight != mc.getMainWindow().getHeight()) {
            framebuffer.createBuffers(Math.max(mc.getMainWindow().getWidth(), 1), Math.max(mc.getMainWindow().getHeight(), 1), Minecraft.IS_RUNNING_ON_MAC);
        }
    }


    public static void drawTexture() {
        Minecraft mc = Minecraft.getInstance();
        MainWindow sr = mc.getMainWindow();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        float width = (float) sr.getScaledWidth();
        float height = (float) sr.getScaledHeight();


        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(0, 0, 0).tex(0, 1).endVertex();
        bufferBuilder.pos(0, height, 0).tex(0, 0).endVertex();
        bufferBuilder.pos(width, height, 0).tex(1, 0).endVertex();
        bufferBuilder.pos(width, 0, 0).tex(1, 1).endVertex();
        tessellator.draw();
    }

    public static void drawTexture(int color) {
        Minecraft mc = Minecraft.getInstance();
        MainWindow sr = mc.getMainWindow();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        float width = (float) sr.getScaledWidth();
        float height = (float) sr.getScaledHeight();


        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR_TEX);
        bufferBuilder.pos(0, 0, 0).color(color).tex(0, 1).endVertex();
        bufferBuilder.pos(0, height, 0).color(color).tex(0, 0).endVertex();
        bufferBuilder.pos(width, height, 0).color(color).tex(1, 0).endVertex();
        bufferBuilder.pos(width, 0, 0).color(color).tex(1, 1).endVertex();
        tessellator.draw();
    }

    public CustomFramebuffer setLinear() {
        this.linear = true;
        return this;
    }

    public void setFramebufferFilter(int framebufferFilterIn) {
        super.setFramebufferFilter(this.linear ? 9729 : framebufferFilterIn);
    }

    public void setup() {
        resizeFramebuffer(this);
        this.framebufferClear(Minecraft.IS_RUNNING_ON_MAC);
        this.bindFramebuffer(false);
    }
    

    public void draw() {
        this.bindFramebufferTexture();
        drawTexture();
    }
    public void draw(int color) {
        this.bindFramebufferTexture();
        drawTexture(color);
    }

    public void draw(Framebuffer bFramebuffer) {
        bFramebuffer.bindFramebufferTexture();
        drawTexture();
    }

}
