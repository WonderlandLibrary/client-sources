/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.shader;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderManager;
import net.minecraft.client.util.JsonException;
import org.lwjgl.util.vector.Matrix4f;

public class Shader {
    private final ShaderManager manager;
    public final Framebuffer framebufferIn;
    public final Framebuffer framebufferOut;
    private final List<Object> listAuxFramebuffers = Lists.newArrayList();
    private final List<String> listAuxNames = Lists.newArrayList();
    private final List<Integer> listAuxWidths = Lists.newArrayList();
    private final List<Integer> listAuxHeights = Lists.newArrayList();
    private Matrix4f projectionMatrix;

    public Shader(IResourceManager resourceManager, String programName, Framebuffer framebufferInIn, Framebuffer framebufferOutIn) throws JsonException, IOException {
        this.manager = new ShaderManager(resourceManager, programName);
        this.framebufferIn = framebufferInIn;
        this.framebufferOut = framebufferOutIn;
    }

    public void deleteShader() {
        this.manager.deleteShader();
    }

    public void addAuxFramebuffer(String auxName, Object auxFramebufferIn, int width, int height) {
        this.listAuxNames.add(this.listAuxNames.size(), auxName);
        this.listAuxFramebuffers.add(this.listAuxFramebuffers.size(), auxFramebufferIn);
        this.listAuxWidths.add(this.listAuxWidths.size(), width);
        this.listAuxHeights.add(this.listAuxHeights.size(), height);
    }

    private void preLoadShader() {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.disableBlend();
        GlStateManager.disableDepth();
        GlStateManager.disableAlpha();
        GlStateManager.disableFog();
        GlStateManager.disableLighting();
        GlStateManager.disableColorMaterial();
        GlStateManager.enableTexture2D();
        GlStateManager.bindTexture(0);
    }

    public void setProjectionMatrix(Matrix4f projectionMatrixIn) {
        this.projectionMatrix = projectionMatrixIn;
    }

    public void loadShader(float p_148042_1_) {
        this.preLoadShader();
        this.framebufferIn.unbindFramebuffer();
        float f = this.framebufferOut.framebufferTextureWidth;
        float f1 = this.framebufferOut.framebufferTextureHeight;
        GlStateManager.viewport(0, 0, (int)f, (int)f1);
        this.manager.addSamplerTexture("DiffuseSampler", this.framebufferIn);
        for (int i = 0; i < this.listAuxFramebuffers.size(); ++i) {
            this.manager.addSamplerTexture(this.listAuxNames.get(i), this.listAuxFramebuffers.get(i));
            this.manager.getShaderUniformOrDefault("AuxSize" + i).set(this.listAuxWidths.get(i).intValue(), this.listAuxHeights.get(i).intValue());
        }
        this.manager.getShaderUniformOrDefault("ProjMat").set(this.projectionMatrix);
        this.manager.getShaderUniformOrDefault("InSize").set(this.framebufferIn.framebufferTextureWidth, this.framebufferIn.framebufferTextureHeight);
        this.manager.getShaderUniformOrDefault("OutSize").set(f, f1);
        this.manager.getShaderUniformOrDefault("Time").set(p_148042_1_);
        Minecraft minecraft = Minecraft.getMinecraft();
        this.manager.getShaderUniformOrDefault("ScreenSize").set(minecraft.displayWidth, minecraft.displayHeight);
        this.manager.useShader();
        this.framebufferOut.framebufferClear();
        this.framebufferOut.bindFramebuffer(false);
        GlStateManager.depthMask(false);
        GlStateManager.colorMask(true, true, true, true);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(0.0, f1, 500.0).color(255, 255, 255, 255).endVertex();
        bufferbuilder.pos(f, f1, 500.0).color(255, 255, 255, 255).endVertex();
        bufferbuilder.pos(f, 0.0, 500.0).color(255, 255, 255, 255).endVertex();
        bufferbuilder.pos(0.0, 0.0, 500.0).color(255, 255, 255, 255).endVertex();
        tessellator.draw();
        GlStateManager.depthMask(true);
        GlStateManager.colorMask(true, true, true, true);
        this.manager.endShader();
        this.framebufferOut.unbindFramebuffer();
        this.framebufferIn.unbindFramebufferTexture();
        for (Object object : this.listAuxFramebuffers) {
            if (!(object instanceof Framebuffer)) continue;
            ((Framebuffer)object).unbindFramebufferTexture();
        }
    }

    public ShaderManager getShaderManager() {
        return this.manager;
    }
}

