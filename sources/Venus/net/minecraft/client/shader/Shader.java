/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.shader;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.util.List;
import java.util.function.IntSupplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderInstance;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.math.vector.Matrix4f;

public class Shader
implements AutoCloseable {
    private final ShaderInstance manager;
    public final Framebuffer framebufferIn;
    public final Framebuffer framebufferOut;
    private final List<IntSupplier> listAuxFramebuffers = Lists.newArrayList();
    private final List<String> listAuxNames = Lists.newArrayList();
    private final List<Integer> listAuxWidths = Lists.newArrayList();
    private final List<Integer> listAuxHeights = Lists.newArrayList();
    private Matrix4f projectionMatrix;

    public Shader(IResourceManager iResourceManager, String string, Framebuffer framebuffer, Framebuffer framebuffer2) throws IOException {
        this.manager = new ShaderInstance(iResourceManager, string);
        this.framebufferIn = framebuffer;
        this.framebufferOut = framebuffer2;
    }

    @Override
    public void close() {
        this.manager.close();
    }

    public void addAuxFramebuffer(String string, IntSupplier intSupplier, int n, int n2) {
        this.listAuxNames.add(this.listAuxNames.size(), string);
        this.listAuxFramebuffers.add(this.listAuxFramebuffers.size(), intSupplier);
        this.listAuxWidths.add(this.listAuxWidths.size(), n);
        this.listAuxHeights.add(this.listAuxHeights.size(), n2);
    }

    public void setProjectionMatrix(Matrix4f matrix4f) {
        this.projectionMatrix = matrix4f;
    }

    public void render(float f) {
        this.framebufferIn.unbindFramebuffer();
        float f2 = this.framebufferOut.framebufferTextureWidth;
        float f3 = this.framebufferOut.framebufferTextureHeight;
        RenderSystem.viewport(0, 0, (int)f2, (int)f3);
        this.manager.func_216537_a("DiffuseSampler", this.framebufferIn::func_242996_f);
        for (int i = 0; i < this.listAuxFramebuffers.size(); ++i) {
            this.manager.func_216537_a(this.listAuxNames.get(i), this.listAuxFramebuffers.get(i));
            this.manager.getShaderUniform("AuxSize" + i).set(this.listAuxWidths.get(i).intValue(), this.listAuxHeights.get(i).intValue());
        }
        this.manager.getShaderUniform("ProjMat").set(this.projectionMatrix);
        this.manager.getShaderUniform("InSize").set(this.framebufferIn.framebufferTextureWidth, this.framebufferIn.framebufferTextureHeight);
        this.manager.getShaderUniform("OutSize").set(f2, f3);
        this.manager.getShaderUniform("Time").set(f);
        Minecraft minecraft = Minecraft.getInstance();
        this.manager.getShaderUniform("ScreenSize").set(minecraft.getMainWindow().getFramebufferWidth(), minecraft.getMainWindow().getFramebufferHeight());
        this.manager.func_216535_f();
        this.framebufferOut.framebufferClear(Minecraft.IS_RUNNING_ON_MAC);
        this.framebufferOut.bindFramebuffer(true);
        RenderSystem.depthFunc(519);
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(0.0, 0.0, 500.0).color(255, 255, 255, 255).endVertex();
        bufferBuilder.pos(f2, 0.0, 500.0).color(255, 255, 255, 255).endVertex();
        bufferBuilder.pos(f2, f3, 500.0).color(255, 255, 255, 255).endVertex();
        bufferBuilder.pos(0.0, f3, 500.0).color(255, 255, 255, 255).endVertex();
        bufferBuilder.finishDrawing();
        WorldVertexBufferUploader.draw(bufferBuilder);
        RenderSystem.depthFunc(515);
        this.manager.func_216544_e();
        this.framebufferOut.unbindFramebuffer();
        this.framebufferIn.unbindFramebufferTexture();
        for (IntSupplier intSupplier : this.listAuxFramebuffers) {
            if (!(intSupplier instanceof Framebuffer)) continue;
            ((Framebuffer)((Object)intSupplier)).unbindFramebufferTexture();
        }
    }

    public ShaderInstance getShaderManager() {
        return this.manager;
    }
}

