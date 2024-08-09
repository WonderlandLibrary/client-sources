/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.render;

import com.jhlabs.image.GaussianFilter;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Objects;
import mpp.venusfr.ui.styles.Style;
import mpp.venusfr.utils.client.IMinecraft;
import mpp.venusfr.utils.math.Vector4i;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.shader.ShaderUtil;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector4f;
import net.optifine.util.TextureUtils;
import org.lwjgl.opengl.GL11;

public class DisplayUtils
implements IMinecraft {
    private static final HashMap<Integer, Integer> shadowCache = new HashMap();
    private static Framebuffer whiteCache = new Framebuffer(1, 1, false, true);
    private static Framebuffer contrastCache = new Framebuffer(1, 1, false, true);

    public static void quads(float f, float f2, float f3, float f4, int n, int n2) {
        buffer.begin(n, DefaultVertexFormats.POSITION_TEX_COLOR);
        buffer.pos(f, f2, 0.0).tex(0.0f, 0.0f).color(n2).endVertex();
        buffer.pos(f, f2 + f4, 0.0).tex(0.0f, 1.0f).color(n2).endVertex();
        buffer.pos(f + f3, f2 + f4, 0.0).tex(1.0f, 1.0f).color(n2).endVertex();
        buffer.pos(f + f3, f2, 0.0).tex(1.0f, 0.0f).color(n2).endVertex();
        tessellator.draw();
    }

    public static void scissor(double d, double d2, double d3, double d4) {
        double d5 = mc.getMainWindow().getGuiScaleFactor();
        d2 = (double)mc.getMainWindow().getScaledHeight() - d2;
        GL11.glScissor((int)(d *= d5), (int)((d2 *= d5) - (d4 *= d5)), (int)(d3 *= d5), (int)d4);
    }

    public static void drawShadow(float f, float f2, float f3, float f4, int n, int n2, int n3) {
        Object object;
        Object object2;
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.alphaFunc(516, 0.01f);
        GlStateManager.disableAlphaTest();
        GL11.glShadeModel(7425);
        f -= (float)n;
        f2 -= (float)n;
        f -= 0.25f;
        f2 += 0.25f;
        int n4 = Objects.hash(Float.valueOf(f3 += (float)(n * 2)), Float.valueOf(f4 += (float)(n * 2)), n);
        if (shadowCache.containsKey(n4)) {
            int n5 = shadowCache.get(n4);
            GlStateManager.bindTexture(n5);
        } else {
            if (f3 <= 0.0f) {
                f3 = 1.0f;
            }
            if (f4 <= 0.0f) {
                f4 = 1.0f;
            }
            object2 = new BufferedImage((int)f3, (int)f4, 3);
            object = ((BufferedImage)object2).createGraphics();
            ((Graphics)object).setColor(Color.WHITE);
            ((Graphics)object).fillRect(n, n, (int)(f3 - (float)(n * 2)), (int)(f4 - (float)(n * 2)));
            ((Graphics)object).dispose();
            GaussianFilter gaussianFilter = new GaussianFilter(n);
            BufferedImage bufferedImage = gaussianFilter.filter((BufferedImage)object2, null);
            DynamicTexture dynamicTexture = new DynamicTexture(TextureUtils.toNativeImage(bufferedImage));
            dynamicTexture.setBlurMipmap(true, false);
            int n6 = dynamicTexture.getGlTextureId();
            shadowCache.put(n4, n6);
        }
        object2 = ColorUtils.rgba(n2);
        object = ColorUtils.rgba(n3);
        buffer.begin(7, DefaultVertexFormats.POSITION_COLOR_TEX);
        buffer.pos(f, f2, 0.0).color((float)object2[0], (float)object2[1], (float)object2[2], (float)object2[3]).tex(0.0f, 0.0f).endVertex();
        buffer.pos(f, f2 + (float)((int)f4), 0.0).color((float)object2[0], (float)object2[1], (float)object2[2], (float)object2[3]).tex(0.0f, 1.0f).endVertex();
        buffer.pos(f + (float)((int)f3), f2 + (float)((int)f4), 0.0).color((float)object[0], (float)object[1], (float)object[2], (float)object[3]).tex(1.0f, 1.0f).endVertex();
        buffer.pos(f + (float)((int)f3), f2, 0.0).color((float)object[0], (float)object[1], (float)object[2], (float)object[3]).tex(1.0f, 0.0f).endVertex();
        tessellator.draw();
        GlStateManager.enableAlphaTest();
        GL11.glShadeModel(7424);
        GlStateManager.bindTexture(0);
        GlStateManager.disableBlend();
    }

    public static void drawShadowVertical(float f, float f2, float f3, float f4, int n, int n2, int n3) {
        Object object;
        Object object2;
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.alphaFunc(516, 0.01f);
        GlStateManager.disableAlphaTest();
        GL11.glShadeModel(7425);
        f -= (float)n;
        f2 -= (float)n;
        f -= 0.25f;
        f2 += 0.25f;
        int n4 = Objects.hash(Float.valueOf(f3 += (float)(n * 2)), Float.valueOf(f4 += (float)(n * 2)), n);
        if (shadowCache.containsKey(n4)) {
            int n5 = shadowCache.get(n4);
            GlStateManager.bindTexture(n5);
        } else {
            int n6;
            if (f3 <= 0.0f) {
                f3 = 1.0f;
            }
            if (f4 <= 0.0f) {
                f4 = 1.0f;
            }
            object2 = new BufferedImage((int)f3, (int)f4, 3);
            object = ((BufferedImage)object2).createGraphics();
            ((Graphics)object).setColor(Color.WHITE);
            ((Graphics)object).fillRect(n, n, (int)(f3 - (float)(n * 2)), (int)(f4 - (float)(n * 2)));
            ((Graphics)object).dispose();
            GaussianFilter gaussianFilter = new GaussianFilter(n);
            BufferedImage bufferedImage = gaussianFilter.filter((BufferedImage)object2, null);
            DynamicTexture dynamicTexture = new DynamicTexture(TextureUtils.toNativeImage(bufferedImage));
            dynamicTexture.setBlurMipmap(true, false);
            try {
                n6 = dynamicTexture.getGlTextureId();
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
            shadowCache.put(n4, n6);
        }
        object2 = ColorUtils.rgba(n2);
        object = ColorUtils.rgba(n3);
        buffer.begin(7, DefaultVertexFormats.POSITION_COLOR_TEX);
        buffer.pos(f, f2, 0.0).color((float)object2[0], (float)object2[1], (float)object2[2], (float)object2[3]).tex(0.0f, 0.0f).endVertex();
        buffer.pos(f, f2 + (float)((int)f4), 0.0).color((float)object[0], (float)object[1], (float)object[2], (float)object[3]).tex(0.0f, 1.0f).endVertex();
        buffer.pos(f + (float)((int)f3), f2 + (float)((int)f4), 0.0).color((float)object2[0], (float)object2[1], (float)object2[2], (float)object2[3]).tex(1.0f, 1.0f).endVertex();
        buffer.pos(f + (float)((int)f3), f2, 0.0).color((float)object[0], (float)object[1], (float)object[2], (float)object[3]).tex(1.0f, 0.0f).endVertex();
        tessellator.draw();
        GlStateManager.enableAlphaTest();
        GL11.glShadeModel(7424);
        GlStateManager.bindTexture(0);
        GlStateManager.disableBlend();
    }

    public static void drawShadow(float f, float f2, float f3, float f4, int n, int n2) {
        Object object;
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.alphaFunc(516, 0.01f);
        GlStateManager.disableAlphaTest();
        f -= (float)n;
        f2 -= (float)n;
        f -= 0.25f;
        f2 += 0.25f;
        int n3 = Objects.hash(Float.valueOf(f3 += (float)(n * 2)), Float.valueOf(f4 += (float)(n * 2)), n);
        if (shadowCache.containsKey(n3)) {
            int n4 = shadowCache.get(n3);
            GlStateManager.bindTexture(n4);
        } else {
            int n5;
            if (f3 <= 0.0f) {
                f3 = 1.0f;
            }
            if (f4 <= 0.0f) {
                f4 = 1.0f;
            }
            object = new BufferedImage((int)f3, (int)f4, 3);
            Graphics2D graphics2D = ((BufferedImage)object).createGraphics();
            graphics2D.setColor(Color.WHITE);
            graphics2D.fillRect(n, n, (int)(f3 - (float)(n * 2)), (int)(f4 - (float)(n * 2)));
            graphics2D.dispose();
            GaussianFilter gaussianFilter = new GaussianFilter(n);
            BufferedImage bufferedImage = gaussianFilter.filter((BufferedImage)object, null);
            DynamicTexture dynamicTexture = new DynamicTexture(TextureUtils.toNativeImage(bufferedImage));
            dynamicTexture.setBlurMipmap(true, false);
            try {
                n5 = dynamicTexture.getGlTextureId();
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
            shadowCache.put(n3, n5);
        }
        object = ColorUtils.rgba(n2);
        buffer.begin(7, DefaultVertexFormats.POSITION_COLOR_TEX);
        buffer.pos(f, f2, 0.0).color((float)object[0], (float)object[1], (float)object[2], (float)object[3]).tex(0.0f, 0.0f).endVertex();
        buffer.pos(f, f2 + (float)((int)f4), 0.0).color((float)object[0], (float)object[1], (float)object[2], (float)object[3]).tex(0.0f, 1.0f).endVertex();
        buffer.pos(f + (float)((int)f3), f2 + (float)((int)f4), 0.0).color((float)object[0], (float)object[1], (float)object[2], (float)object[3]).tex(1.0f, 1.0f).endVertex();
        buffer.pos(f + (float)((int)f3), f2, 0.0).color((float)object[0], (float)object[1], (float)object[2], (float)object[3]).tex(1.0f, 0.0f).endVertex();
        tessellator.draw();
        GlStateManager.enableAlphaTest();
        GlStateManager.bindTexture(0);
        GlStateManager.disableBlend();
    }

    public static void drawImage(ResourceLocation resourceLocation, float f, float f2, float f3, float f4, int n) {
        RenderSystem.pushMatrix();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.shadeModel(7425);
        mc.getTextureManager().bindTexture(resourceLocation);
        DisplayUtils.quads(f, f2, f3, f4, 7, n);
        RenderSystem.shadeModel(7424);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.popMatrix();
    }

    public static void drawImage(ResourceLocation resourceLocation, float f, float f2, float f3, float f4, Vector4i vector4i) {
        RenderSystem.pushMatrix();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.shadeModel(7425);
        mc.getTextureManager().bindTexture(resourceLocation);
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        buffer.pos(f, f2, 0.0).tex(0.0f, 0.0f).color(vector4i.x).endVertex();
        buffer.pos(f, f2 + f4, 0.0).tex(0.0f, 1.0f).color(vector4i.y).endVertex();
        buffer.pos(f + f3, f2 + f4, 0.0).tex(1.0f, 1.0f).color(vector4i.z).endVertex();
        buffer.pos(f + f3, f2, 0.0).tex(1.0f, 0.0f).color(vector4i.w).endVertex();
        tessellator.draw();
        RenderSystem.shadeModel(7424);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.popMatrix();
    }

    public static void drawRectWBuilding(double d, double d2, double d3, double d4, int n) {
        d3 += d;
        d4 += d2;
        float f = (float)(n >> 24 & 0xFF) / 255.0f;
        float f2 = (float)(n >> 16 & 0xFF) / 255.0f;
        float f3 = (float)(n >> 8 & 0xFF) / 255.0f;
        float f4 = (float)(n & 0xFF) / 255.0f;
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.pos(d, d4, 0.0).color(f2, f3, f4, f).endVertex();
        bufferBuilder.pos(d3, d4, 0.0).color(f2, f3, f4, f).endVertex();
        bufferBuilder.pos(d3, d2, 0.0).color(f2, f3, f4, f).endVertex();
        bufferBuilder.pos(d, d2, 0.0).color(f2, f3, f4, f).endVertex();
    }

    public static void drawRectBuilding(double d, double d2, double d3, double d4, int n) {
        double d5;
        if (d < d3) {
            d5 = d;
            d = d3;
            d3 = d5;
        }
        if (d2 < d4) {
            d5 = d2;
            d2 = d4;
            d4 = d5;
        }
        float f = (float)(n >> 24 & 0xFF) / 255.0f;
        float f2 = (float)(n >> 16 & 0xFF) / 255.0f;
        float f3 = (float)(n >> 8 & 0xFF) / 255.0f;
        float f4 = (float)(n & 0xFF) / 255.0f;
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.pos(d, d4, 0.0).color(f2, f3, f4, f).endVertex();
        bufferBuilder.pos(d3, d4, 0.0).color(f2, f3, f4, f).endVertex();
        bufferBuilder.pos(d3, d2, 0.0).color(f2, f3, f4, f).endVertex();
        bufferBuilder.pos(d, d2, 0.0).color(f2, f3, f4, f).endVertex();
    }

    public static void drawMCVerticalBuilding(double d, double d2, double d3, double d4, int n, int n2) {
        float f = (float)(n >> 24 & 0xFF) / 255.0f;
        float f2 = (float)(n >> 16 & 0xFF) / 255.0f;
        float f3 = (float)(n >> 8 & 0xFF) / 255.0f;
        float f4 = (float)(n & 0xFF) / 255.0f;
        float f5 = (float)(n2 >> 24 & 0xFF) / 255.0f;
        float f6 = (float)(n2 >> 16 & 0xFF) / 255.0f;
        float f7 = (float)(n2 >> 8 & 0xFF) / 255.0f;
        float f8 = (float)(n2 & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.pos(d, d4, 0.0).color(f2, f3, f4, f).endVertex();
        bufferBuilder.pos(d3, d4, 0.0).color(f2, f3, f4, f).endVertex();
        bufferBuilder.pos(d3, d2, 0.0).color(f6, f7, f8, f5).endVertex();
        bufferBuilder.pos(d, d2, 0.0).color(f6, f7, f8, f5).endVertex();
    }

    public static void drawMCHorizontalBuilding(double d, double d2, double d3, double d4, int n, int n2) {
        float f = (float)(n >> 24 & 0xFF) / 255.0f;
        float f2 = (float)(n >> 16 & 0xFF) / 255.0f;
        float f3 = (float)(n >> 8 & 0xFF) / 255.0f;
        float f4 = (float)(n & 0xFF) / 255.0f;
        float f5 = (float)(n2 >> 24 & 0xFF) / 255.0f;
        float f6 = (float)(n2 >> 16 & 0xFF) / 255.0f;
        float f7 = (float)(n2 >> 8 & 0xFF) / 255.0f;
        float f8 = (float)(n2 & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.pos(d, d4, 0.0).color(f2, f3, f4, f).endVertex();
        bufferBuilder.pos(d3, d4, 0.0).color(f6, f7, f8, f5).endVertex();
        bufferBuilder.pos(d3, d2, 0.0).color(f6, f7, f8, f5).endVertex();
        bufferBuilder.pos(d, d2, 0.0).color(f2, f3, f4, f).endVertex();
    }

    public static void drawRect(double d, double d2, double d3, double d4, int n) {
        double d5;
        if (d < d3) {
            d5 = d;
            d = d3;
            d3 = d5;
        }
        if (d2 < d4) {
            d5 = d2;
            d2 = d4;
            d4 = d5;
        }
        float f = (float)(n >> 24 & 0xFF) / 255.0f;
        float f2 = (float)(n >> 16 & 0xFF) / 255.0f;
        float f3 = (float)(n >> 8 & 0xFF) / 255.0f;
        float f4 = (float)(n & 0xFF) / 255.0f;
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(d, d4, 0.0).color(f2, f3, f4, f).endVertex();
        bufferBuilder.pos(d3, d4, 0.0).color(f2, f3, f4, f).endVertex();
        bufferBuilder.pos(d3, d2, 0.0).color(f2, f3, f4, f).endVertex();
        bufferBuilder.pos(d, d2, 0.0).color(f2, f3, f4, f).endVertex();
        bufferBuilder.finishDrawing();
        WorldVertexBufferUploader.draw(bufferBuilder);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    public static void drawRectW(double d, double d2, double d3, double d4, int n) {
        double d5;
        d3 = d + d3;
        d4 = d2 + d4;
        if (d < d3) {
            d5 = d;
            d = d3;
            d3 = d5;
        }
        if (d2 < d4) {
            d5 = d2;
            d2 = d4;
            d4 = d5;
        }
        float f = (float)(n >> 24 & 0xFF) / 255.0f;
        float f2 = (float)(n >> 16 & 0xFF) / 255.0f;
        float f3 = (float)(n >> 8 & 0xFF) / 255.0f;
        float f4 = (float)(n & 0xFF) / 255.0f;
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(d, d4, 0.0).color(f2, f3, f4, f).endVertex();
        bufferBuilder.pos(d3, d4, 0.0).color(f2, f3, f4, f).endVertex();
        bufferBuilder.pos(d3, d2, 0.0).color(f2, f3, f4, f).endVertex();
        bufferBuilder.pos(d, d2, 0.0).color(f2, f3, f4, f).endVertex();
        bufferBuilder.finishDrawing();
        WorldVertexBufferUploader.draw(bufferBuilder);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    public static void drawRectHorizontalW(double d, double d2, double d3, double d4, int n, int n2) {
        double d5;
        d3 = d + d3;
        d4 = d2 + d4;
        if (d < d3) {
            d5 = d;
            d = d3;
            d3 = d5;
        }
        if (d2 < d4) {
            d5 = d2;
            d2 = d4;
            d4 = d5;
        }
        float[] fArray = ColorUtils.rgba(n);
        float[] fArray2 = ColorUtils.rgba(n2);
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.shadeModel(7425);
        RenderSystem.defaultBlendFunc();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(d, d4, 0.0).color(fArray2[0], fArray2[1], fArray2[2], fArray2[3]).endVertex();
        bufferBuilder.pos(d3, d4, 0.0).color(fArray2[0], fArray2[1], fArray2[2], fArray2[3]).endVertex();
        bufferBuilder.pos(d3, d2, 0.0).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder.pos(d, d2, 0.0).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder.finishDrawing();
        WorldVertexBufferUploader.draw(bufferBuilder);
        RenderSystem.shadeModel(7424);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    public static void drawRectVerticalW(double d, double d2, double d3, double d4, int n, int n2) {
        double d5;
        d3 = d + d3;
        d4 = d2 + d4;
        if (d < d3) {
            d5 = d;
            d = d3;
            d3 = d5;
        }
        if (d2 < d4) {
            d5 = d2;
            d2 = d4;
            d4 = d5;
        }
        float[] fArray = ColorUtils.rgba(n);
        float[] fArray2 = ColorUtils.rgba(n2);
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.shadeModel(7425);
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(d, d4, 0.0).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder.pos(d3, d4, 0.0).color(fArray2[0], fArray2[1], fArray2[2], fArray2[3]).endVertex();
        bufferBuilder.pos(d3, d2, 0.0).color(fArray2[0], fArray2[1], fArray2[2], fArray2[3]).endVertex();
        bufferBuilder.pos(d, d2, 0.0).color(fArray[0], fArray[1], fArray[2], fArray[3]).endVertex();
        bufferBuilder.finishDrawing();
        WorldVertexBufferUploader.draw(bufferBuilder);
        RenderSystem.enableTexture();
        RenderSystem.shadeModel(7424);
        RenderSystem.disableBlend();
    }

    public static void drawRoundedRect(float f, float f2, float f3, float f4, Vector4f vector4f, int n) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        ShaderUtil.rounded.attach();
        ShaderUtil.rounded.setUniform("size", f3 * 2.0f, f4 * 2.0f);
        ShaderUtil.rounded.setUniform("round", vector4f.x * 2.0f, vector4f.y * 2.0f, vector4f.z * 2.0f, vector4f.w * 2.0f);
        ShaderUtil.rounded.setUniform("smoothness", 0.0f, 1.5f);
        ShaderUtil.rounded.setUniform("color1", ColorUtils.rgba(n));
        ShaderUtil.rounded.setUniform("color2", ColorUtils.rgba(n));
        ShaderUtil.rounded.setUniform("color3", ColorUtils.rgba(n));
        ShaderUtil.rounded.setUniform("color4", ColorUtils.rgba(n));
        DisplayUtils.drawQuads(f, f2, f3, f4, 7);
        ShaderUtil.rounded.detach();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawRoundedRect(float f, float f2, float f3, float f4, Vector4f vector4f, Vector4i vector4i) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        ShaderUtil.rounded.attach();
        ShaderUtil.rounded.setUniform("size", f3 * 2.0f, f4 * 2.0f);
        ShaderUtil.rounded.setUniform("round", vector4f.x * 2.0f, vector4f.y * 2.0f, vector4f.z * 2.0f, vector4f.w * 2.0f);
        ShaderUtil.rounded.setUniform("smoothness", 0.0f, 1.5f);
        ShaderUtil.rounded.setUniform("color1", ColorUtils.rgba(vector4i.getX()));
        ShaderUtil.rounded.setUniform("color2", ColorUtils.rgba(vector4i.getY()));
        ShaderUtil.rounded.setUniform("color3", ColorUtils.rgba(vector4i.getZ()));
        ShaderUtil.rounded.setUniform("color4", ColorUtils.rgba(vector4i.getW()));
        DisplayUtils.drawQuads(f, f2, f3, f4, 7);
        ShaderUtil.rounded.detach();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawRoundedRect(float f, float f2, float f3, float f4, float f5, int n, Vector4f vector4f, Vector4i vector4i) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        ShaderUtil.roundedout.attach();
        ShaderUtil.roundedout.setUniform("size", f3 * 2.0f, f4 * 2.0f);
        ShaderUtil.roundedout.setUniform("round", vector4f.x * 2.0f, vector4f.y * 2.0f, vector4f.z * 2.0f, vector4f.w * 2.0f);
        ShaderUtil.roundedout.setUniform("smoothness", 0.0f, 1.5f);
        ShaderUtil.roundedout.setUniform("outlineColor", ColorUtils.rgba(vector4i.getX()));
        ShaderUtil.roundedout.setUniform("outlineColor1", ColorUtils.rgba(vector4i.getY()));
        ShaderUtil.roundedout.setUniform("outlineColor2", ColorUtils.rgba(vector4i.getZ()));
        ShaderUtil.roundedout.setUniform("outlineColor3", ColorUtils.rgba(vector4i.getW()));
        ShaderUtil.roundedout.setUniform("color", ColorUtils.rgba(n));
        ShaderUtil.roundedout.setUniform("outline", f5);
        DisplayUtils.drawQuads(f, f2, f3, f4, 7);
        ShaderUtil.rounded.detach();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawContrast(float f) {
        f = MathHelper.clamp(f, 0.0f, 1.0f);
        GlStateManager.enableBlend();
        GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.glBlendFuncSeparate(770, 771, 1, 0);
        contrastCache = ShaderUtil.createFrameBuffer(contrastCache);
        contrastCache.framebufferClear(true);
        contrastCache.bindFramebuffer(false);
        ShaderUtil.contrast.attach();
        ShaderUtil.contrast.setUniform("texture", 0);
        ShaderUtil.contrast.setUniformf("contrast", f);
        GlStateManager.bindTexture(DisplayUtils.mc.getFramebuffer().framebufferTexture);
        ShaderUtil.drawQuads();
        contrastCache.unbindFramebuffer();
        ShaderUtil.contrast.detach();
        mc.getFramebuffer().bindFramebuffer(false);
        ShaderUtil.contrast.attach();
        ShaderUtil.contrast.setUniform("texture", 0);
        ShaderUtil.contrast.setUniformf("contrast", f);
        GlStateManager.bindTexture(DisplayUtils.contrastCache.framebufferTexture);
        ShaderUtil.drawQuads();
        ShaderUtil.contrast.detach();
        GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.bindTexture(0);
    }

    public static void drawWhite(float f) {
        f = MathHelper.clamp(f, 0.0f, 1.0f);
        GlStateManager.enableBlend();
        GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.glBlendFuncSeparate(770, 771, 1, 0);
        whiteCache = ShaderUtil.createFrameBuffer(whiteCache);
        whiteCache.framebufferClear(true);
        whiteCache.bindFramebuffer(false);
        ShaderUtil.white.attach();
        ShaderUtil.white.setUniform("texture", 0);
        ShaderUtil.white.setUniformf("state", f);
        GlStateManager.bindTexture(DisplayUtils.mc.getFramebuffer().framebufferTexture);
        ShaderUtil.drawQuads();
        whiteCache.unbindFramebuffer();
        ShaderUtil.white.detach();
        mc.getFramebuffer().bindFramebuffer(false);
        ShaderUtil.white.attach();
        ShaderUtil.white.setUniform("texture", 0);
        ShaderUtil.white.setUniformf("state", f);
        GlStateManager.bindTexture(DisplayUtils.whiteCache.framebufferTexture);
        ShaderUtil.drawQuads();
        ShaderUtil.white.detach();
        GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.bindTexture(0);
    }

    public static void drawRoundedRect(float f, float f2, float f3, float f4, float f5, int n) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        ShaderUtil.smooth.attach();
        ShaderUtil.smooth.setUniformf("location", (float)((double)f * mc.getMainWindow().getGuiScaleFactor()), (float)((double)mc.getMainWindow().getHeight() - (double)f4 * mc.getMainWindow().getGuiScaleFactor() - (double)f2 * mc.getMainWindow().getGuiScaleFactor()));
        ShaderUtil.smooth.setUniformf("rectSize", (double)f3 * mc.getMainWindow().getGuiScaleFactor(), (double)f4 * mc.getMainWindow().getGuiScaleFactor());
        ShaderUtil.smooth.setUniformf("radius", (double)f5 * mc.getMainWindow().getGuiScaleFactor());
        ShaderUtil.smooth.setUniform("blur", 0);
        ShaderUtil.smooth.setUniform("color", ColorUtils.rgba(n));
        DisplayUtils.drawQuads(f, f2, f3, f4, 7);
        ShaderUtil.smooth.detach();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawCircle(float f, float f2, float f3, int n) {
        DisplayUtils.drawRoundedRect(f - f3 / 2.0f, f2 - f3 / 2.0f, f3, f3, f3 / 2.0f, n);
    }

    public static void drawShadowCircle(float f, float f2, float f3, int n) {
        DisplayUtils.drawShadow(f - f3 / 2.0f, f2 - f3 / 2.0f, f3, f3, (int)f3, n);
    }

    public static void drawQuads(float f, float f2, float f3, float f4, int n) {
        buffer.begin(n, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(f, f2, 0.0).tex(0.0f, 0.0f).endVertex();
        buffer.pos(f, f2 + f4, 0.0).tex(0.0f, 1.0f).endVertex();
        buffer.pos(f + f3, f2 + f4, 0.0).tex(1.0f, 1.0f).endVertex();
        buffer.pos(f + f3, f2, 0.0).tex(1.0f, 0.0f).endVertex();
        Tessellator.getInstance().draw();
    }

    public static void drawBox(double d, double d2, double d3, double d4, double d5, int n) {
        DisplayUtils.drawRectBuilding(d + d5, d2, d3 - d5, d2 + d5, n);
        DisplayUtils.drawRectBuilding(d, d2, d + d5, d4, n);
        DisplayUtils.drawRectBuilding(d3 - d5, d2, d3, d4, n);
        DisplayUtils.drawRectBuilding(d + d5, d4 - d5, d3 - d5, d4, n);
    }

    public static void drawBoxTest(double d, double d2, double d3, double d4, double d5, Vector4i vector4i) {
        DisplayUtils.drawMCHorizontalBuilding(d + d5, d2, d3 - d5, d2 + d5, vector4i.x, vector4i.z);
        DisplayUtils.drawMCVerticalBuilding(d, d2, d + d5, d4, vector4i.z, vector4i.x);
        DisplayUtils.drawMCVerticalBuilding(d3 - d5, d2, d3, d4, vector4i.x, vector4i.z);
        DisplayUtils.drawMCHorizontalBuilding(d + d5, d4 - d5, d3 - d5, d4, vector4i.z, vector4i.x);
    }

    public static void drawCircle1(float f, float f2, float f3, float f4, float f5, float f6, boolean bl, int n) {
        float f7;
        float f8;
        float f9;
        if (f3 > f4) {
            f9 = f4;
            f4 = f3;
            f3 = f9;
        }
        GlStateManager.enableBlend();
        GL11.glDisable(3553);
        RenderSystem.blendFuncSeparate(770, 771, 1, 0);
        GL11.glEnable(2848);
        GL11.glLineWidth(f6);
        GL11.glBegin(3);
        for (f9 = f4; f9 >= f3; f9 -= 1.0f) {
            ColorUtils.setColor(n);
            f8 = MathHelper.cos((float)((double)f9 * Math.PI / 180.0)) * f5;
            f7 = MathHelper.sin((float)((double)f9 * Math.PI / 180.0)) * f5;
            GL11.glVertex2f(f + f8, f2 + f7);
        }
        GL11.glEnd();
        GL11.glDisable(2848);
        if (bl) {
            GL11.glBegin(6);
            for (f9 = f4; f9 >= f3; f9 -= 1.0f) {
                ColorUtils.setColor1(n);
                f8 = MathHelper.cos((float)((double)f9 * Math.PI / 180.0)) * f5;
                f7 = MathHelper.sin((float)((double)f9 * Math.PI / 180.0)) * f5;
                GL11.glVertex2f(f + f8, f2 + f7);
            }
            GL11.glEnd();
        }
        GL11.glEnable(3553);
        GlStateManager.disableBlend();
    }

    public static void drawCircle1(float f, float f2, float f3, float f4, float f5, float f6, boolean bl, Style style) {
        float f7;
        float f8;
        float f9;
        if (f3 > f4) {
            f9 = f4;
            f4 = f3;
            f3 = f9;
        }
        GlStateManager.enableBlend();
        RenderSystem.disableAlphaTest();
        GL11.glDisable(3553);
        RenderSystem.blendFuncSeparate(770, 771, 1, 0);
        RenderSystem.shadeModel(7425);
        GL11.glEnable(2848);
        GL11.glLineWidth(f6);
        GL11.glBegin(3);
        for (f9 = f4; f9 >= f3; f9 -= 1.0f) {
            ColorUtils.setColor(ColorUtils.getColor((int)(f9 * 1.0f)));
            f8 = MathHelper.cos((float)((double)f9 * Math.PI / 180.0)) * f5;
            f7 = MathHelper.sin((float)((double)f9 * Math.PI / 180.0)) * f5;
            GL11.glVertex2f(f + f8, f2 + f7);
        }
        GL11.glEnd();
        GL11.glDisable(2848);
        if (bl) {
            GL11.glBegin(6);
            for (f9 = f4; f9 >= f3; f9 -= 1.0f) {
                ColorUtils.setColor(ColorUtils.getColor((int)(f9 * 1.0f)));
                f8 = MathHelper.cos((float)((double)f9 * Math.PI / 180.0)) * f5;
                f7 = MathHelper.sin((float)((double)f9 * Math.PI / 180.0)) * f5;
                GL11.glVertex2f(f + f8, f2 + f7);
            }
            GL11.glEnd();
        }
        RenderSystem.enableAlphaTest();
        RenderSystem.shadeModel(7424);
        GL11.glEnable(3553);
        GlStateManager.disableBlend();
    }

    public static void drawGradientRound(float f, float f2, float f3, float f4, float f5, int n, int n2, int n3, int n4) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(770, 771);
        ShaderUtil.roundedout.attach();
        ShaderUtil.setupRoundedRectUniforms(f, f2, f3, f4, f5, ShaderUtil.roundedout);
        ShaderUtil.roundedout.setUniform("color1", ColorUtils.rgba(n));
        ShaderUtil.roundedout.setUniform("color2", ColorUtils.rgba(n2));
        ShaderUtil.roundedout.setUniform("color3", ColorUtils.rgba(n3));
        ShaderUtil.roundedout.setUniform("color4", ColorUtils.rgba(n4));
        ShaderUtil.drawQuads(f - 1.0f, f2 - 1.0f, f3 + 2.0f, f4 + 2.0f);
        ShaderUtil.roundedout.detach();
        RenderSystem.disableBlend();
    }

    public static void drawCircle(float f, float f2, float f3, float f4, float f5, float f6, boolean bl, Style style) {
        float f7;
        float f8;
        float f9;
        if (f3 > f4) {
            float f10 = f4;
            f4 = f3;
            f3 = f10;
        }
        GlStateManager.enableBlend();
        RenderSystem.disableAlphaTest();
        GL11.glDisable(3553);
        RenderSystem.blendFuncSeparate(770, 771, 1, 0);
        RenderSystem.shadeModel(7425);
        GL11.glEnable(2848);
        GL11.glLineWidth(f6);
        GL11.glBegin(3);
        for (f9 = f4; f9 >= f3; f9 -= 1.0f) {
            ColorUtils.setColor(ColorUtils.getColor((int)(f9 * 2.0f)));
            f8 = MathHelper.cos((float)((double)f9 * Math.PI / 180.0)) * f5;
            f7 = MathHelper.sin((float)((double)f9 * Math.PI / 180.0)) * f5;
            GL11.glVertex2f(f + f8, f2 + f7);
        }
        GL11.glEnd();
        GL11.glDisable(2848);
        if (bl) {
            GL11.glBegin(6);
            for (f9 = f4; f9 >= f3; f9 -= 1.0f) {
                ColorUtils.setColor(ColorUtils.getColor((int)(f9 * 2.0f)));
                f8 = MathHelper.cos((float)((double)f9 * Math.PI / 180.0)) * f5;
                f7 = MathHelper.sin((float)((double)f9 * Math.PI / 180.0)) * f5;
                GL11.glVertex2f(f + f8, f2 + f7);
            }
            GL11.glEnd();
        }
        RenderSystem.enableAlphaTest();
        RenderSystem.shadeModel(7424);
        GL11.glEnable(3553);
        GlStateManager.disableBlend();
    }

    public static void drawCircle(float f, float f2, float f3, float f4, float f5, float f6, boolean bl, int n) {
        float f7;
        float f8;
        float f9;
        if (f3 > f4) {
            float f10 = f4;
            f4 = f3;
            f3 = f10;
        }
        GlStateManager.enableBlend();
        GL11.glDisable(3553);
        RenderSystem.blendFuncSeparate(770, 771, 1, 0);
        GL11.glEnable(2848);
        GL11.glLineWidth(f6);
        GL11.glBegin(3);
        for (f9 = f4; f9 >= f3; f9 -= 1.0f) {
            ColorUtils.setColor(n);
            f8 = MathHelper.cos((float)((double)f9 * Math.PI / 180.0)) * f5;
            f7 = MathHelper.sin((float)((double)f9 * Math.PI / 180.0)) * f5;
            GL11.glVertex2f(f + f8, f2 + f7);
        }
        GL11.glEnd();
        GL11.glDisable(2848);
        if (bl) {
            GL11.glBegin(6);
            for (f9 = f4; f9 >= f3; f9 -= 1.0f) {
                ColorUtils.setColor(n);
                f8 = MathHelper.cos((float)((double)f9 * Math.PI / 180.0)) * f5;
                f7 = MathHelper.sin((float)((double)f9 * Math.PI / 180.0)) * f5;
                GL11.glVertex2f(f + f8, f2 + f7);
            }
            GL11.glEnd();
        }
        GL11.glEnable(3553);
        GlStateManager.disableBlend();
    }

    public static Vector3d interpolatePos(float f, float f2, float f3, float f4, float f5, float f6) {
        double d = (double)(f + (f4 - f) * mc.getRenderPartialTicks()) - DisplayUtils.mc.getRenderManager().info.getProjectedView().getX();
        double d2 = (double)(f2 + (f5 - f2) * mc.getRenderPartialTicks()) - DisplayUtils.mc.getRenderManager().info.getProjectedView().getY();
        double d3 = (double)(f3 + (f6 - f3) * mc.getRenderPartialTicks()) - DisplayUtils.mc.getRenderManager().info.getProjectedView().getZ();
        return new Vector3d(d, d2, d3);
    }

    public static void drawImage(ResourceLocation resourceLocation, float f, float f2, int n, int n2, int n3, int n4, int n5, int n6) {
        RenderSystem.pushMatrix();
        RenderSystem.disableLighting();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.shadeModel(7425);
        RenderSystem.disableCull();
        RenderSystem.disableAlphaTest();
        RenderSystem.blendFuncSeparate(770, 1, 0, 1);
        mc.getTextureManager().bindTexture(new ResourceLocation(resourceLocation.getPath()));
        buffer.begin(9, DefaultVertexFormats.POSITION_COLOR_TEX_LIGHTMAP);
        buffer.pos(f, f2 + (float)n2, 0.0).color(n3).tex(0.0f, 0.99f).lightmap(0, 240).endVertex();
        buffer.pos(f + (float)n, f2 + (float)n2, 0.0).color(n4).tex(1.0f, 0.99f).lightmap(0, 240).endVertex();
        buffer.pos(f + (float)n, f2, 0.0).color(n5).tex(1.0f, 0.0f).lightmap(0, 240).endVertex();
        buffer.pos(f, f2, 0.0).color(n6).tex(0.0f, 0.0f).lightmap(0, 240).endVertex();
        tessellator.draw();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        RenderSystem.enableCull();
        RenderSystem.enableAlphaTest();
        RenderSystem.depthMask(true);
        RenderSystem.popMatrix();
    }

    public static void drawImage(ResourceLocation resourceLocation, float f, float f2, float f3, float f4, int n, int n2, int n3, int n4) {
        RenderSystem.pushMatrix();
        RenderSystem.disableLighting();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.shadeModel(7425);
        RenderSystem.disableCull();
        RenderSystem.disableAlphaTest();
        RenderSystem.blendFuncSeparate(770, 1, 0, 1);
        mc.getTextureManager().bindTexture(new ResourceLocation(resourceLocation.getPath()));
        buffer.begin(9, DefaultVertexFormats.POSITION_COLOR_TEX_LIGHTMAP);
        buffer.pos(f, f2 + f4, 0.0).color(n).tex(0.0f, 0.99f).lightmap(0, 240).endVertex();
        buffer.pos(f + f3, f2 + f4, 0.0).color(n2).tex(1.0f, 0.99f).lightmap(0, 240).endVertex();
        buffer.pos(f + f3, f2, 0.0).color(n3).tex(1.0f, 0.0f).lightmap(0, 240).endVertex();
        buffer.pos(f, f2, 0.0).color(n4).tex(0.0f, 0.0f).lightmap(0, 240).endVertex();
        tessellator.draw();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        RenderSystem.enableCull();
        RenderSystem.enableAlphaTest();
        RenderSystem.depthMask(true);
        RenderSystem.popMatrix();
    }
}

