package dev.luvbeeq.shader;

import dev.excellent.api.interfaces.client.ITheme;
import dev.excellent.client.module.impl.render.FogBlur;
import dev.excellent.impl.util.math.Mathf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.FramebufferConstants;
import net.mojang.blaze3d.platform.GlStateManager;
import net.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;

import static dev.excellent.api.interfaces.game.IMinecraft.mc;
import static dev.excellent.api.interfaces.shader.IShader.DEPTH_SHADER;


public enum DepthRenderer implements ITheme {
    INSTANCE;
    public int depthCopyFbo;
    public int depthCopyColorBuffer;
    public int depthCopyDepthBuffer;
    private int width, height;

    public void doRender() {

        if (width != mc.getMainWindow().getFramebufferWidth() || height != mc.getMainWindow().getFramebufferHeight()) {
            width = mc.getMainWindow().getFramebufferWidth();
            height = mc.getMainWindow().getFramebufferHeight();

            if (depthCopyFbo != 0) {
                deleteDepthCopyFramebuffer();
            }
        }

        if (depthCopyFbo == 0) {
            createDepthCopyFramebuffer();
        }
        render();
    }

    private void render() {
        final Minecraft mc = Minecraft.getInstance();
        final Framebuffer framebuffer = mc.getFramebuffer();

        updateDepthTexture(framebuffer);
        DEPTH_SHADER.setNear(0.05F);
        DEPTH_SHADER.setFar((float) (mc.gameSettings.renderDistanceChunks * 16));

        DEPTH_SHADER.bind();
        blit(framebuffer);
        DEPTH_SHADER.unbind();
        DEPTH_SHADER.setDistance(FogBlur.singleton.get().distance.getValue().floatValue());
        DEPTH_SHADER.clientColor(FogBlur.singleton.get().clientColor.getValue());
        DEPTH_SHADER.saturation(Mathf.clamp01(1F - FogBlur.singleton.get().saturation.getValue().floatValue()));
        DEPTH_SHADER.color1(getTheme().getClientColor(0));
        DEPTH_SHADER.color2(getTheme().getClientColor(0));
        DEPTH_SHADER.color3(getTheme().getClientColor(270));
        DEPTH_SHADER.color4(getTheme().getClientColor(270));
        DEPTH_SHADER.setBlurredBuffer(KawaseBlur.INSTANCE.BLURRED.framebufferTexture);
        DEPTH_SHADER.setMinecraftBuffer(mc.getFramebuffer().framebufferTexture);
        DEPTH_SHADER.setResolution(1.0F / mc.getMainWindow().getWidth(), 1.0F / mc.getMainWindow().getHeight());
//        customFramebuffer.stop();
        framebuffer.bindFramebuffer(true);
        GlStateManager.activeTexture(GL13.GL_TEXTURE0);
        GlStateManager.bindTexture(0);
        GlStateManager.enableBlend();
        GlStateManager.enableTexture();
        GlStateManager.color4f(-1, -1, -1, 1);


    }

    public void updateDepthTexture(final Framebuffer framebuffer) {
        GlStateManager.bindFramebuffer(GL30.GL_READ_FRAMEBUFFER, framebuffer.framebufferObject);
        GlStateManager.bindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, depthCopyFbo);
        GL30.glBlitFramebuffer(0, 0,
                framebuffer.framebufferWidth,
                framebuffer.framebufferHeight,
                0, 0,
                framebuffer.framebufferWidth,
                framebuffer.framebufferHeight,
                GL30.GL_DEPTH_BUFFER_BIT,
                GL30.GL_NEAREST);
    }

    private void createDepthCopyFramebuffer() {
        final Framebuffer framebuffer = Minecraft.getInstance().getFramebuffer();
        depthCopyFbo = GlStateManager.genFramebuffers();
        depthCopyColorBuffer = createTexture(framebuffer.framebufferWidth, framebuffer.framebufferHeight, GL11.GL_RGBA8, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE);
        depthCopyDepthBuffer = createTexture(framebuffer.framebufferWidth, framebuffer.framebufferHeight, GL30.GL_DEPTH_COMPONENT, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT);
        GlStateManager.bindFramebuffer(FramebufferConstants.GL_FRAMEBUFFER, depthCopyFbo);
        GlStateManager.framebufferTexture2D(FramebufferConstants.GL_FRAMEBUFFER, FramebufferConstants.GL_COLOR_ATTACHMENT0, GL11.GL_TEXTURE_2D, depthCopyColorBuffer, 0);
        GlStateManager.framebufferTexture2D(FramebufferConstants.GL_FRAMEBUFFER, FramebufferConstants.GL_DEPTH_ATTACHMENT, GL11.GL_TEXTURE_2D, depthCopyDepthBuffer, 0);
        GlStateManager.bindFramebuffer(FramebufferConstants.GL_FRAMEBUFFER, 0);
        DEPTH_SHADER.setDepthBuffer(depthCopyDepthBuffer);

    }

    public void deleteDepthCopyFramebuffer() {
        DEPTH_SHADER.setDepthBuffer(0);
        GlStateManager.deleteFramebuffers(depthCopyFbo);
        depthCopyFbo = 0;
        TextureUtil.releaseTextureId(depthCopyColorBuffer);
        depthCopyColorBuffer = 0;
        TextureUtil.releaseTextureId(depthCopyDepthBuffer);
        depthCopyDepthBuffer = 0;
    }

    private int createTexture(final int width, final int height, final int internalFormat, final int format, final int type) {
        final int texture = TextureUtil.generateTextureId();
        GlStateManager.bindTexture(texture);
        GlStateManager.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GlStateManager.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        GlStateManager.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GlStateManager.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GlStateManager.texParameter(GL11.GL_TEXTURE_2D, GL14.GL_DEPTH_TEXTURE_MODE, GL11.GL_LUMINANCE);
        GlStateManager.texParameter(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_COMPARE_MODE, GL14.GL_NONE);
        GlStateManager.texParameter(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_COMPARE_FUNC, GL11.GL_LEQUAL);
        GlStateManager.texImage2D(GL11.GL_TEXTURE_2D, 0, internalFormat, width, height, 0, format, type, null);
        GlStateManager.bindTexture(0);
        return texture;
    }

    private void blit(final Framebuffer framebuffer) {
        final int width = framebuffer.framebufferWidth;
        final int height = framebuffer.framebufferHeight;

        RenderSystem.depthMask(false);
        RenderSystem.disableDepthTest();

        setupMatrices(width, height);

        framebuffer.bindFramebuffer(false);

        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(0, height, 0).tex(0, 0).endVertex();
        buffer.pos(width, height, 0).tex(1, 0).endVertex();
        buffer.pos(width, 0, 0).tex(1, 1).endVertex();
        buffer.pos(0, 0, 0).tex(0, 1).endVertex();
        tessellator.draw();

        restoreMatrices();

        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
    }

    private void setupMatrices(final int width, final int height) {
        RenderSystem.matrixMode(GL11.GL_PROJECTION);
        RenderSystem.pushMatrix();
        RenderSystem.loadIdentity();
        RenderSystem.ortho(0, width, height, 0, 1000, 3000);
        RenderSystem.matrixMode(GL11.GL_MODELVIEW);
        RenderSystem.pushMatrix();
        RenderSystem.loadIdentity();
        RenderSystem.translated(0, 0, -2000);
        RenderSystem.viewport(0, 0, width, height);
    }

    private void restoreMatrices() {
        RenderSystem.matrixMode(GL11.GL_PROJECTION);
        RenderSystem.popMatrix();
        RenderSystem.matrixMode(GL11.GL_MODELVIEW);
        RenderSystem.popMatrix();
    }
}
