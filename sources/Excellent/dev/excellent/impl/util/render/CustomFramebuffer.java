package dev.excellent.impl.util.render;

import dev.excellent.impl.util.time.TimerUtil;
import lombok.Getter;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;

import static dev.excellent.api.interfaces.game.IMinecraft.mc;
import static org.lwjgl.opengl.GL11.GL_QUADS;

public class CustomFramebuffer extends Framebuffer {

    private boolean linear;
    @Getter
    private final TimerUtil timerUtils = TimerUtil.create();

    public CustomFramebuffer(boolean useDepthIn) {
        super(1, 1, useDepthIn, Minecraft.IS_RUNNING_ON_MAC);
    }

    public void resizeFramebuffer(Framebuffer framebuffer) {
        Minecraft mc = Minecraft.getInstance();
        if (framebuffer.framebufferWidth != mc.getMainWindow().getWidth() || framebuffer.framebufferHeight != mc.getMainWindow().getFramebufferHeight()) {
            framebuffer.createBuffers(Math.max(mc.getMainWindow().getWidth(), 1), Math.max(mc.getMainWindow().getFramebufferHeight(), 1), Minecraft.IS_RUNNING_ON_MAC);
        }
    }


    public static void drawTexture() {
        final BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        final Tessellator tessellator = Tessellator.getInstance();
        bufferBuilder.begin(GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(0, 0, 0).tex(0, 1).endVertex();
        bufferBuilder.pos(0, Math.max(mc.getMainWindow().getScaledHeight(), 1), 0).tex(0, 0).endVertex();
        bufferBuilder.pos(Math.max(mc.getMainWindow().getScaledWidth(), 1), Math.max(mc.getMainWindow().getScaledHeight(), 1), 0).tex(1, 0).endVertex();
        bufferBuilder.pos(Math.max(mc.getMainWindow().getScaledWidth(), 1), 0, 0).tex(1, 1).endVertex();
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

    public void setup(boolean clear) {
        resizeFramebuffer(this);
        if (clear)
            this.framebufferClear(Minecraft.IS_RUNNING_ON_MAC);
        this.bindFramebuffer(true);
    }

    public void stop() {
        unbindFramebuffer();
        Minecraft.getInstance().getFramebuffer().bindFramebuffer(true);
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
