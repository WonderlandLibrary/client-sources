/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.debug;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.optifine.Config;
import net.optifine.shaders.Shaders;

public class ChunkBorderDebugRenderer
implements DebugRenderer.IDebugRenderer {
    private final Minecraft minecraft;

    public ChunkBorderDebugRenderer(Minecraft minecraft) {
        this.minecraft = minecraft;
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, double d, double d2, double d3) {
        if (!Shaders.isShadowPass) {
            int n;
            if (Config.isShaders()) {
                Shaders.beginLeash();
            }
            RenderSystem.enableDepthTest();
            RenderSystem.shadeModel(7425);
            RenderSystem.enableAlphaTest();
            RenderSystem.defaultAlphaFunc();
            Entity entity2 = this.minecraft.gameRenderer.getActiveRenderInfo().getRenderViewEntity();
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferBuilder = tessellator.getBuffer();
            double d4 = 0.0 - d2;
            double d5 = 256.0 - d2;
            RenderSystem.disableTexture();
            RenderSystem.disableBlend();
            double d6 = (double)(entity2.chunkCoordX << 4) - d;
            double d7 = (double)(entity2.chunkCoordZ << 4) - d3;
            RenderSystem.lineWidth(1.0f);
            bufferBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
            for (n = -16; n <= 32; n += 16) {
                for (int i = -16; i <= 32; i += 16) {
                    bufferBuilder.pos(d6 + (double)n, d4, d7 + (double)i).color(1.0f, 0.0f, 0.0f, 0.0f).endVertex();
                    bufferBuilder.pos(d6 + (double)n, d4, d7 + (double)i).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                    bufferBuilder.pos(d6 + (double)n, d5, d7 + (double)i).color(1.0f, 0.0f, 0.0f, 0.5f).endVertex();
                    bufferBuilder.pos(d6 + (double)n, d5, d7 + (double)i).color(1.0f, 0.0f, 0.0f, 0.0f).endVertex();
                }
            }
            for (n = 2; n < 16; n += 2) {
                bufferBuilder.pos(d6 + (double)n, d4, d7).color(1.0f, 1.0f, 0.0f, 0.0f).endVertex();
                bufferBuilder.pos(d6 + (double)n, d4, d7).color(1.0f, 1.0f, 0.0f, 1.0f).endVertex();
                bufferBuilder.pos(d6 + (double)n, d5, d7).color(1.0f, 1.0f, 0.0f, 1.0f).endVertex();
                bufferBuilder.pos(d6 + (double)n, d5, d7).color(1.0f, 1.0f, 0.0f, 0.0f).endVertex();
                bufferBuilder.pos(d6 + (double)n, d4, d7 + 16.0).color(1.0f, 1.0f, 0.0f, 0.0f).endVertex();
                bufferBuilder.pos(d6 + (double)n, d4, d7 + 16.0).color(1.0f, 1.0f, 0.0f, 1.0f).endVertex();
                bufferBuilder.pos(d6 + (double)n, d5, d7 + 16.0).color(1.0f, 1.0f, 0.0f, 1.0f).endVertex();
                bufferBuilder.pos(d6 + (double)n, d5, d7 + 16.0).color(1.0f, 1.0f, 0.0f, 0.0f).endVertex();
            }
            for (n = 2; n < 16; n += 2) {
                bufferBuilder.pos(d6, d4, d7 + (double)n).color(1.0f, 1.0f, 0.0f, 0.0f).endVertex();
                bufferBuilder.pos(d6, d4, d7 + (double)n).color(1.0f, 1.0f, 0.0f, 1.0f).endVertex();
                bufferBuilder.pos(d6, d5, d7 + (double)n).color(1.0f, 1.0f, 0.0f, 1.0f).endVertex();
                bufferBuilder.pos(d6, d5, d7 + (double)n).color(1.0f, 1.0f, 0.0f, 0.0f).endVertex();
                bufferBuilder.pos(d6 + 16.0, d4, d7 + (double)n).color(1.0f, 1.0f, 0.0f, 0.0f).endVertex();
                bufferBuilder.pos(d6 + 16.0, d4, d7 + (double)n).color(1.0f, 1.0f, 0.0f, 1.0f).endVertex();
                bufferBuilder.pos(d6 + 16.0, d5, d7 + (double)n).color(1.0f, 1.0f, 0.0f, 1.0f).endVertex();
                bufferBuilder.pos(d6 + 16.0, d5, d7 + (double)n).color(1.0f, 1.0f, 0.0f, 0.0f).endVertex();
            }
            for (n = 0; n <= 256; n += 2) {
                double d8 = (double)n - d2;
                bufferBuilder.pos(d6, d8, d7).color(1.0f, 1.0f, 0.0f, 0.0f).endVertex();
                bufferBuilder.pos(d6, d8, d7).color(1.0f, 1.0f, 0.0f, 1.0f).endVertex();
                bufferBuilder.pos(d6, d8, d7 + 16.0).color(1.0f, 1.0f, 0.0f, 1.0f).endVertex();
                bufferBuilder.pos(d6 + 16.0, d8, d7 + 16.0).color(1.0f, 1.0f, 0.0f, 1.0f).endVertex();
                bufferBuilder.pos(d6 + 16.0, d8, d7).color(1.0f, 1.0f, 0.0f, 1.0f).endVertex();
                bufferBuilder.pos(d6, d8, d7).color(1.0f, 1.0f, 0.0f, 1.0f).endVertex();
                bufferBuilder.pos(d6, d8, d7).color(1.0f, 1.0f, 0.0f, 0.0f).endVertex();
            }
            tessellator.draw();
            RenderSystem.lineWidth(2.0f);
            bufferBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
            for (n = 0; n <= 16; n += 16) {
                for (int i = 0; i <= 16; i += 16) {
                    bufferBuilder.pos(d6 + (double)n, d4, d7 + (double)i).color(0.25f, 0.25f, 1.0f, 0.0f).endVertex();
                    bufferBuilder.pos(d6 + (double)n, d4, d7 + (double)i).color(0.25f, 0.25f, 1.0f, 1.0f).endVertex();
                    bufferBuilder.pos(d6 + (double)n, d5, d7 + (double)i).color(0.25f, 0.25f, 1.0f, 1.0f).endVertex();
                    bufferBuilder.pos(d6 + (double)n, d5, d7 + (double)i).color(0.25f, 0.25f, 1.0f, 0.0f).endVertex();
                }
            }
            for (n = 0; n <= 256; n += 16) {
                double d9 = (double)n - d2;
                bufferBuilder.pos(d6, d9, d7).color(0.25f, 0.25f, 1.0f, 0.0f).endVertex();
                bufferBuilder.pos(d6, d9, d7).color(0.25f, 0.25f, 1.0f, 1.0f).endVertex();
                bufferBuilder.pos(d6, d9, d7 + 16.0).color(0.25f, 0.25f, 1.0f, 1.0f).endVertex();
                bufferBuilder.pos(d6 + 16.0, d9, d7 + 16.0).color(0.25f, 0.25f, 1.0f, 1.0f).endVertex();
                bufferBuilder.pos(d6 + 16.0, d9, d7).color(0.25f, 0.25f, 1.0f, 1.0f).endVertex();
                bufferBuilder.pos(d6, d9, d7).color(0.25f, 0.25f, 1.0f, 1.0f).endVertex();
                bufferBuilder.pos(d6, d9, d7).color(0.25f, 0.25f, 1.0f, 0.0f).endVertex();
            }
            tessellator.draw();
            RenderSystem.lineWidth(1.0f);
            RenderSystem.enableBlend();
            RenderSystem.enableTexture();
            RenderSystem.shadeModel(7424);
            if (Config.isShaders()) {
                Shaders.endLeash();
            }
        }
    }
}

