/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;

public class RenderSkyboxCube {
    private final ResourceLocation[] locations = new ResourceLocation[6];

    public RenderSkyboxCube(ResourceLocation resourceLocation) {
        for (int i = 0; i < 6; ++i) {
            this.locations[i] = new ResourceLocation(resourceLocation.getNamespace(), resourceLocation.getPath() + "_" + i + ".png");
        }
    }

    public void render(Minecraft minecraft, float f, float f2, float f3) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.matrixMode(5889);
        RenderSystem.pushMatrix();
        RenderSystem.loadIdentity();
        RenderSystem.multMatrix(Matrix4f.perspective(85.0, (float)minecraft.getMainWindow().getFramebufferWidth() / (float)minecraft.getMainWindow().getFramebufferHeight(), 0.05f, 10.0f));
        RenderSystem.matrixMode(5888);
        RenderSystem.pushMatrix();
        RenderSystem.loadIdentity();
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.rotatef(180.0f, 1.0f, 0.0f, 0.0f);
        RenderSystem.enableBlend();
        RenderSystem.disableAlphaTest();
        RenderSystem.disableCull();
        RenderSystem.depthMask(false);
        RenderSystem.defaultBlendFunc();
        int n = 2;
        for (int i = 0; i < 4; ++i) {
            RenderSystem.pushMatrix();
            float f4 = ((float)(i % 2) / 2.0f - 0.5f) / 256.0f;
            float f5 = ((float)(i / 2) / 2.0f - 0.5f) / 256.0f;
            float f6 = 0.0f;
            RenderSystem.translatef(f4, f5, 0.0f);
            RenderSystem.rotatef(f, 1.0f, 0.0f, 0.0f);
            RenderSystem.rotatef(f2, 0.0f, 1.0f, 0.0f);
            for (int j = 0; j < 6; ++j) {
                minecraft.getTextureManager().bindTexture(this.locations[j]);
                bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                int n2 = Math.round(255.0f * f3) / (i + 1);
                if (j == 0) {
                    bufferBuilder.pos(-1.0, -1.0, 1.0).tex(0.0f, 0.0f).color(255, 255, 255, n2).endVertex();
                    bufferBuilder.pos(-1.0, 1.0, 1.0).tex(0.0f, 1.0f).color(255, 255, 255, n2).endVertex();
                    bufferBuilder.pos(1.0, 1.0, 1.0).tex(1.0f, 1.0f).color(255, 255, 255, n2).endVertex();
                    bufferBuilder.pos(1.0, -1.0, 1.0).tex(1.0f, 0.0f).color(255, 255, 255, n2).endVertex();
                }
                if (j == 1) {
                    bufferBuilder.pos(1.0, -1.0, 1.0).tex(0.0f, 0.0f).color(255, 255, 255, n2).endVertex();
                    bufferBuilder.pos(1.0, 1.0, 1.0).tex(0.0f, 1.0f).color(255, 255, 255, n2).endVertex();
                    bufferBuilder.pos(1.0, 1.0, -1.0).tex(1.0f, 1.0f).color(255, 255, 255, n2).endVertex();
                    bufferBuilder.pos(1.0, -1.0, -1.0).tex(1.0f, 0.0f).color(255, 255, 255, n2).endVertex();
                }
                if (j == 2) {
                    bufferBuilder.pos(1.0, -1.0, -1.0).tex(0.0f, 0.0f).color(255, 255, 255, n2).endVertex();
                    bufferBuilder.pos(1.0, 1.0, -1.0).tex(0.0f, 1.0f).color(255, 255, 255, n2).endVertex();
                    bufferBuilder.pos(-1.0, 1.0, -1.0).tex(1.0f, 1.0f).color(255, 255, 255, n2).endVertex();
                    bufferBuilder.pos(-1.0, -1.0, -1.0).tex(1.0f, 0.0f).color(255, 255, 255, n2).endVertex();
                }
                if (j == 3) {
                    bufferBuilder.pos(-1.0, -1.0, -1.0).tex(0.0f, 0.0f).color(255, 255, 255, n2).endVertex();
                    bufferBuilder.pos(-1.0, 1.0, -1.0).tex(0.0f, 1.0f).color(255, 255, 255, n2).endVertex();
                    bufferBuilder.pos(-1.0, 1.0, 1.0).tex(1.0f, 1.0f).color(255, 255, 255, n2).endVertex();
                    bufferBuilder.pos(-1.0, -1.0, 1.0).tex(1.0f, 0.0f).color(255, 255, 255, n2).endVertex();
                }
                if (j == 4) {
                    bufferBuilder.pos(-1.0, -1.0, -1.0).tex(0.0f, 0.0f).color(255, 255, 255, n2).endVertex();
                    bufferBuilder.pos(-1.0, -1.0, 1.0).tex(0.0f, 1.0f).color(255, 255, 255, n2).endVertex();
                    bufferBuilder.pos(1.0, -1.0, 1.0).tex(1.0f, 1.0f).color(255, 255, 255, n2).endVertex();
                    bufferBuilder.pos(1.0, -1.0, -1.0).tex(1.0f, 0.0f).color(255, 255, 255, n2).endVertex();
                }
                if (j == 5) {
                    bufferBuilder.pos(-1.0, 1.0, 1.0).tex(0.0f, 0.0f).color(255, 255, 255, n2).endVertex();
                    bufferBuilder.pos(-1.0, 1.0, -1.0).tex(0.0f, 1.0f).color(255, 255, 255, n2).endVertex();
                    bufferBuilder.pos(1.0, 1.0, -1.0).tex(1.0f, 1.0f).color(255, 255, 255, n2).endVertex();
                    bufferBuilder.pos(1.0, 1.0, 1.0).tex(1.0f, 0.0f).color(255, 255, 255, n2).endVertex();
                }
                tessellator.draw();
            }
            RenderSystem.popMatrix();
            RenderSystem.colorMask(true, true, true, false);
        }
        RenderSystem.colorMask(true, true, true, true);
        RenderSystem.matrixMode(5889);
        RenderSystem.popMatrix();
        RenderSystem.matrixMode(5888);
        RenderSystem.popMatrix();
        RenderSystem.depthMask(true);
        RenderSystem.enableCull();
        RenderSystem.enableDepthTest();
    }

    public CompletableFuture<Void> loadAsync(TextureManager textureManager, Executor executor) {
        CompletableFuture[] completableFutureArray = new CompletableFuture[6];
        for (int i = 0; i < completableFutureArray.length; ++i) {
            completableFutureArray[i] = textureManager.loadAsync(this.locations[i], executor);
        }
        return CompletableFuture.allOf(completableFutureArray);
    }
}

