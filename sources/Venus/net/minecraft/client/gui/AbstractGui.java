/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.function.BiConsumer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;

public abstract class AbstractGui {
    public static final ResourceLocation BACKGROUND_LOCATION = new ResourceLocation("textures/gui/options_background.png");
    public static final ResourceLocation STATS_ICON_LOCATION = new ResourceLocation("textures/gui/container/stats_icons.png");
    public static final ResourceLocation GUI_ICONS_LOCATION = new ResourceLocation("textures/gui/icons.png");
    private int blitOffset;

    protected void hLine(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
        if (n2 < n) {
            int n5 = n;
            n = n2;
            n2 = n5;
        }
        AbstractGui.fill(matrixStack, n, n3, n2 + 1, n3 + 1, n4);
    }

    public static void drawScaledCustomSizeModalRect(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10) {
        float f11 = 1.0f / f9;
        float f12 = 1.0f / f10;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(f, f2 + f8, 0.0).tex(f3 * f11, (f4 + f6) * f12).endVertex();
        bufferBuilder.pos(f + f7, f2 + f8, 0.0).tex((f3 + f5) * f11, (f4 + f6) * f12).endVertex();
        bufferBuilder.pos(f + f7, f2, 0.0).tex((f3 + f5) * f11, f4 * f12).endVertex();
        bufferBuilder.pos(f, f2, 0.0).tex(f3 * f11, f4 * f12).endVertex();
        tessellator.draw();
    }

    protected void vLine(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
        if (n3 < n2) {
            int n5 = n2;
            n2 = n3;
            n3 = n5;
        }
        AbstractGui.fill(matrixStack, n, n2 + 1, n + 1, n3, n4);
    }

    public static void fill(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5) {
        AbstractGui.fill(matrixStack.getLast().getMatrix(), n, n2, n3, n4, n5);
    }

    private static void fill(Matrix4f matrix4f, int n, int n2, int n3, int n4, int n5) {
        int n6;
        if (n < n3) {
            n6 = n;
            n = n3;
            n3 = n6;
        }
        if (n2 < n4) {
            n6 = n2;
            n2 = n4;
            n4 = n6;
        }
        float f = (float)(n5 >> 24 & 0xFF) / 255.0f;
        float f2 = (float)(n5 >> 16 & 0xFF) / 255.0f;
        float f3 = (float)(n5 >> 8 & 0xFF) / 255.0f;
        float f4 = (float)(n5 & 0xFF) / 255.0f;
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(matrix4f, n, n4, 0.0f).color(f2, f3, f4, f).endVertex();
        bufferBuilder.pos(matrix4f, n3, n4, 0.0f).color(f2, f3, f4, f).endVertex();
        bufferBuilder.pos(matrix4f, n3, n2, 0.0f).color(f2, f3, f4, f).endVertex();
        bufferBuilder.pos(matrix4f, n, n2, 0.0f).color(f2, f3, f4, f).endVertex();
        bufferBuilder.finishDrawing();
        WorldVertexBufferUploader.draw(bufferBuilder);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    protected void fillGradient(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5, int n6) {
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.disableAlphaTest();
        RenderSystem.defaultBlendFunc();
        RenderSystem.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        AbstractGui.fillGradient(matrixStack.getLast().getMatrix(), bufferBuilder, n, n2, n3, n4, this.blitOffset, n5, n6);
        tessellator.draw();
        RenderSystem.shadeModel(7424);
        RenderSystem.disableBlend();
        RenderSystem.enableAlphaTest();
        RenderSystem.enableTexture();
    }

    protected static void fillGradient(Matrix4f matrix4f, BufferBuilder bufferBuilder, int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        float f = (float)(n6 >> 24 & 0xFF) / 255.0f;
        float f2 = (float)(n6 >> 16 & 0xFF) / 255.0f;
        float f3 = (float)(n6 >> 8 & 0xFF) / 255.0f;
        float f4 = (float)(n6 & 0xFF) / 255.0f;
        float f5 = (float)(n7 >> 24 & 0xFF) / 255.0f;
        float f6 = (float)(n7 >> 16 & 0xFF) / 255.0f;
        float f7 = (float)(n7 >> 8 & 0xFF) / 255.0f;
        float f8 = (float)(n7 & 0xFF) / 255.0f;
        bufferBuilder.pos(matrix4f, n3, n2, n5).color(f2, f3, f4, f).endVertex();
        bufferBuilder.pos(matrix4f, n, n2, n5).color(f2, f3, f4, f).endVertex();
        bufferBuilder.pos(matrix4f, n, n4, n5).color(f6, f7, f8, f5).endVertex();
        bufferBuilder.pos(matrix4f, n3, n4, n5).color(f6, f7, f8, f5).endVertex();
    }

    public static void drawCenteredString(MatrixStack matrixStack, FontRenderer fontRenderer, String string, int n, int n2, int n3) {
        fontRenderer.drawStringWithShadow(matrixStack, string, n - fontRenderer.getStringWidth(string) / 2, n2, n3);
    }

    public static void drawCenteredString(MatrixStack matrixStack, FontRenderer fontRenderer, ITextComponent iTextComponent, int n, int n2, int n3) {
        IReorderingProcessor iReorderingProcessor = iTextComponent.func_241878_f();
        fontRenderer.func_238407_a_(matrixStack, iReorderingProcessor, n - fontRenderer.func_243245_a(iReorderingProcessor) / 2, n2, n3);
    }

    public static void drawString(MatrixStack matrixStack, FontRenderer fontRenderer, String string, int n, int n2, int n3) {
        fontRenderer.drawStringWithShadow(matrixStack, string, n, n2, n3);
    }

    public static void drawString(MatrixStack matrixStack, FontRenderer fontRenderer, ITextComponent iTextComponent, int n, int n2, int n3) {
        fontRenderer.func_243246_a(matrixStack, iTextComponent, n, n2, n3);
    }

    public void blitBlackOutline(int n, int n2, BiConsumer<Integer, Integer> biConsumer) {
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        biConsumer.accept(n + 1, n2);
        biConsumer.accept(n - 1, n2);
        biConsumer.accept(n, n2 + 1);
        biConsumer.accept(n, n2 - 1);
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        biConsumer.accept(n, n2);
    }

    public static void blit(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5, TextureAtlasSprite textureAtlasSprite) {
        AbstractGui.innerBlit(matrixStack.getLast().getMatrix(), n, n + n4, n2, n2 + n5, n3, textureAtlasSprite.getMinU(), textureAtlasSprite.getMaxU(), textureAtlasSprite.getMinV(), textureAtlasSprite.getMaxV());
    }

    public void blit(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5, int n6) {
        AbstractGui.blit(matrixStack, n, n2, this.blitOffset, n3, n4, n5, n6, 256, 256);
    }

    public static void blit(MatrixStack matrixStack, int n, int n2, int n3, float f, float f2, int n4, int n5, int n6, int n7) {
        AbstractGui.innerBlit(matrixStack, n, n + n4, n2, n2 + n5, n3, n4, n5, f, f2, n7, n6);
    }

    public static void blit(MatrixStack matrixStack, int n, int n2, int n3, int n4, float f, float f2, int n5, int n6, int n7, int n8) {
        AbstractGui.innerBlit(matrixStack, n, n + n3, n2, n2 + n4, 0, n5, n6, f, f2, n7, n8);
    }

    public static void blit(MatrixStack matrixStack, int n, int n2, float f, float f2, int n3, int n4, int n5, int n6) {
        AbstractGui.blit(matrixStack, n, n2, n3, n4, f, f2, n3, n4, n5, n6);
    }

    private static void innerBlit(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5, int n6, int n7, float f, float f2, int n8, int n9) {
        AbstractGui.innerBlit(matrixStack.getLast().getMatrix(), n, n2, n3, n4, n5, (f + 0.0f) / (float)n8, (f + (float)n6) / (float)n8, (f2 + 0.0f) / (float)n9, (f2 + (float)n7) / (float)n9);
    }

    private static void innerBlit(Matrix4f matrix4f, int n, int n2, int n3, int n4, int n5, float f, float f2, float f3, float f4) {
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(matrix4f, n, n4, n5).tex(f, f4).endVertex();
        bufferBuilder.pos(matrix4f, n2, n4, n5).tex(f2, f4).endVertex();
        bufferBuilder.pos(matrix4f, n2, n3, n5).tex(f2, f3).endVertex();
        bufferBuilder.pos(matrix4f, n, n3, n5).tex(f, f3).endVertex();
        bufferBuilder.finishDrawing();
        RenderSystem.enableAlphaTest();
        WorldVertexBufferUploader.draw(bufferBuilder);
    }

    public int getBlitOffset() {
        return this.blitOffset;
    }

    public void setBlitOffset(int n) {
        this.blitOffset = n;
    }
}

