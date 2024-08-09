package wtf.resolute.utiled.render;

import com.jhlabs.image.GaussianFilter;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector4f;
import net.optifine.util.MathUtilsTest;
import net.optifine.util.TextureUtils;
import org.lwjgl.opengl.GL11;
import wtf.resolute.utiled.client.IMinecraft;
import wtf.resolute.utiled.math.MathUtil;
import wtf.resolute.utiled.math.Vector4i;
import wtf.resolute.utiled.shader.ShaderUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.mojang.blaze3d.systems.RenderSystem.depthMask;
import static com.mojang.blaze3d.systems.RenderSystem.disableBlend;
import static net.minecraft.client.renderer.vertex.DefaultVertexFormats.*;
import static org.lwjgl.opengl.GL11.*;

public class DisplayUtils implements IMinecraft {

    public static MatrixStack matrixFrom(MatrixStack matrices, ActiveRenderInfo camera) {

        matrices.rotate(Vector3f.XP.rotationDegrees(0));
        matrices.rotate(Vector3f.YP.rotationDegrees(0));

        return matrices;
    }
    @Getter
    @AllArgsConstructor
    public static class Vec2fColored {
        float x, y;
        int color;
    }
    public static void quads(float x, float y, float width, float height, int glQuads, int color) {
        buffer.begin(glQuads, POSITION_TEX_COLOR);
        {
            buffer.pos(x, y, 0).tex(0, 0).color(color).endVertex();
            buffer.pos(x, y + height, 0).tex(0, 1).color(color).endVertex();
            buffer.pos(x + width, y + height, 0).tex(1, 1).color(color).endVertex();
            buffer.pos(x + width, y, 0).tex(1, 0).color(color).endVertex();
        }
        tessellator.draw();
    }

    public static void scissor(double x, double y, double width, double height) {

        final double scale = mc.getMainWindow().getGuiScaleFactor();

        y = mc.getMainWindow().getScaledHeight() - y;

        x *= scale;
        y *= scale;
        width *= scale;
        height *= scale;

        GL11.glScissor((int) x, (int) (y - height), (int) width, (int) height);
    }


    private static final HashMap<Integer, Integer> shadowCache = new HashMap<Integer, Integer>();

    public static void drawShadowNonFull(float x, float y, float width, float height, int radius, int color, int i) {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.01f);
        GlStateManager.disableAlphaTest();
        GL11.glShadeModel(GL11.GL_SMOOTH);

        x -= radius;
        y -= radius;
        width = width + radius * 2;
        height = height + radius * 2;
        x -= 0.25f;
        y += 0.25f;

        int identifier = Objects.hash(width, height, radius);
        int textureId;

        if (shadowCache.containsKey(identifier)) {
            textureId = shadowCache.get(identifier);
            GlStateManager.bindTexture(textureId);
        } else {
            if (width <= 0) {
                width = 1;
            }

            if (height <= 0) {
                height = 1;
            }

            BufferedImage originalImage = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB_PRE);
            Graphics2D graphics = originalImage.createGraphics();

            graphics.setComposite(AlphaComposite.Clear);
            graphics.fillRect(0, 0, (int) width, (int) height);
            graphics.setComposite(AlphaComposite.Src);
            graphics.setColor(Color.WHITE);

            graphics.fillRect(radius, radius, (int) width - 2 * radius, (int) height - 2 * radius);
            graphics.dispose();

            GaussianFilter filter = new GaussianFilter(radius);
            BufferedImage blurredImage = filter.filter(originalImage, null);

            Graphics2D g2d = blurredImage.createGraphics();
            g2d.setComposite(AlphaComposite.Clear);
            g2d.fillRect(radius, radius, (int) width - 2 * radius, (int) height - 2 * radius);
            g2d.dispose();

            DynamicTexture texture = new DynamicTexture(TextureUtils.toNativeImage(blurredImage));
            texture.setBlurMipmap(true, true);
            textureId = texture.getGlTextureId();
            shadowCache.put(identifier, textureId);
        }

        float[] startColorComponents = ColorUtils.rgba(color);
        float[] i1 = ColorUtils.rgba(i);
        buffer.begin(GL11.GL_QUADS, POSITION_COLOR_TEX);

        buffer.pos(x, y, 0.0f).color(startColorComponents[0], startColorComponents[1], startColorComponents[2], startColorComponents[3]).tex(0.0f, 0.0f).endVertex();
        buffer.pos(x, y + (float) ((int) height), 0.0f).color(startColorComponents[0], startColorComponents[1], startColorComponents[2], startColorComponents[3]).tex(0.0f, 1.0f).endVertex();
        buffer.pos(x + (float) ((int) width), y + (float) ((int) height), 0.0f).color(i1[0], i1[1], i1[2], i1[3]).tex(1.0f, 1.0f).endVertex();
        buffer.pos(x + (float) ((int) width), y, 0.0f).color(i1[0], i1[1], i1[2], i1[3]).tex(1.0f, 0.0f).endVertex();

        tessellator.draw();
        GlStateManager.enableAlphaTest();
        GL11.glShadeModel(GL11.GL_FLAT);
        GlStateManager.bindTexture(0);
        GlStateManager.disableBlend();
    }


    public static void drawShadow(float x, float y, float width, float height, int radius, int color, int i) {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.01f);
        GlStateManager.disableAlphaTest();
        GL11.glShadeModel(7425);

        x -= radius;
        y -= radius;
        width = width + radius * 2;
        height = height + radius * 2;
        x -= 0.25f;
        y += 0.25f;

        int identifier = Objects.hash(width, height, radius);
        int textureId;

        if (shadowCache.containsKey(identifier)) {
            textureId = shadowCache.get(identifier);
            GlStateManager.bindTexture(textureId);
        } else {
            if (width <= 0) {
                width = 1;
            }

            if (height <= 0) {
                height = 1;
            }

            BufferedImage originalImage = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB_PRE);
            Graphics2D graphics = originalImage.createGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(radius, radius, (int) (width - radius * 2), (int) (height - radius * 2));
            graphics.dispose();

            GaussianFilter filter = new GaussianFilter(radius);
            BufferedImage blurredImage = filter.filter(originalImage, null);
            DynamicTexture texture = new DynamicTexture(TextureUtils.toNativeImage(blurredImage));
            texture.setBlurMipmap(true, true);
            textureId = texture.getGlTextureId();
            shadowCache.put(identifier, textureId);
        }

        float[] startColorComponents = ColorUtils.rgba(color);
        float[] i1 = ColorUtils.rgba(i);
        buffer.begin(GL11.GL_QUADS, POSITION_COLOR_TEX);
        buffer.pos(x, y, 0.0f)
                .color(startColorComponents[0], startColorComponents[1], startColorComponents[2],
                        startColorComponents[3])
                .tex(0.0f, 0.0f)
                .endVertex();

        buffer.pos(x, y + (float) ((int) height), 0.0f)
                .color(startColorComponents[0], startColorComponents[1], startColorComponents[2],
                        startColorComponents[3])
                .tex(0.0f, 1.0f)
                .endVertex();

        buffer.pos(x + (float) ((int) width), y + (float) ((int) height), 0.0f)
                .color(i1[0], i1[1], i1[2],
                        i1[3])
                .tex(1.0f, 1.0f)
                .endVertex();

        buffer.pos(x + (float) ((int) width), y, 0.0f)
                .color(i1[0], i1[1], i1[2],
                        i1[3])
                .tex(1.0f, 0.0f)
                .endVertex();

        tessellator.draw();
        GlStateManager.enableAlphaTest();
        GL11.glShadeModel(7424);
        GlStateManager.bindTexture(0);
        GlStateManager.disableBlend();
    }

    public static void drawFace(float d,
                                float y,
                                float u,
                                float v,
                                float uWidth,
                                float vHeight,
                                float width,
                                float height,
                                float tileWidth,
                                float tileHeight,
                                AbstractClientPlayerEntity target) {
        try {
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            ResourceLocation skin = target.getLocationSkin();
            mc.getTextureManager().bindTexture(skin);
            float hurtPercent = getHurtPercent(target);
            GL11.glColor4f(1, 1 - hurtPercent, 1 - hurtPercent, 1);
            AbstractGui.drawScaledCustomSizeModalRect(d, y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight);
            GL11.glColor4f(1, 1, 1, 1);
            GL11.glPopMatrix();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static float getHurtPercent(LivingEntity hurt) {
        return getRenderHurtTime(hurt) / (float) 10;
    }
    public static float getRenderHurtTime(LivingEntity hurt) {
        return (float) hurt.hurtTime - (hurt.hurtTime != 0 ? mc.timer.renderPartialTicks : 0.0f);
    }

    public static void disposeWorldRenderer(MatrixStack stack) {
        glEnable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
        glEnable(GL_ALPHA_TEST);
        stack.translate(mc.getRenderManager().renderPosX(), mc.getRenderManager().renderPosY(), mc.getRenderManager().renderPosZ());
        stack.pop();
    }

    public static void setupWorldRenderer(MatrixStack stack) {
        stack.push();
        glDisable(GL_LIGHTING);
        glEnable(GL_BLEND);
        GlStateManager.enableAlphaTest();
        glDisable(GL_ALPHA_TEST);
        stack.translate(-mc.getRenderManager().renderPosX(), -mc.getRenderManager().renderPosY(), -mc.getRenderManager().renderPosZ());
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDisable(GL_TEXTURE_2D);
    }

    public static void drawRoundOutline(float x, float y, float width, float height, float radius, float outlineThickness, int color, org.joml.Vector4i outlineColor) {
        GlStateManager.color4f(1, 1, 1, 1);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        ShaderedUtils.ROUND_SHADER_OUTLINE.attach();

        MainWindow sr = mc.getMainWindow();
        ShaderedUtils.setupRoundedRectUniforms(x, y, width, height, radius, ShaderedUtils.ROUND_SHADER_OUTLINE);

        float[] clr = DisplayUtils.IntColor.rgb(color);
        ShaderedUtils.ROUND_SHADER_OUTLINE.setUniform("outlineThickness", outlineThickness * 2.0f);
        ShaderedUtils.ROUND_SHADER_OUTLINE.setUniform("color", clr[0], clr[1], clr[2], clr[3]);
        for (int i = 0; i < 4;i++) {
            float[] col = DisplayUtils.IntColor.rgb(outlineColor.get(i));
            ShaderedUtils.ROUND_SHADER_OUTLINE.setUniform("outlineColor" + (i + 1), col[0], col[1], col[2],col[3]);
        }

        ShaderedUtils.ROUND_SHADER_OUTLINE.drawQuads(x - (2 + outlineThickness), y - (2 + outlineThickness), width + (2 + outlineThickness * 3), height + (3 + outlineThickness * 2));
        ShaderedUtils.ROUND_SHADER_OUTLINE.detach();
        GlStateManager.disableBlend();
    }


    public static class IntColor {

        public static float[] rgb(final int color) {
            return new float[]{
                    (color >> 16 & 0xFF) / 255f,
                    (color >> 8 & 0xFF) / 255f,
                    (color & 0xFF) / 255f,
                    (color >> 24 & 0xFF) / 255f
            };
        }

        public static int rgba(final int r,
                               final int g,
                               final int b,
                               final int a) {
            return a << 24 | r << 16 | g << 8 | b;
        }

        public static int getRed(final int hex) {
            return hex >> 16 & 255;
        }

        public static int getGreen(final int hex) {
            return hex >> 8 & 255;
        }

        public static int getBlue(final int hex) {
            return hex & 255;
        }

        public static int getAlpha(final int hex) {
            return hex >> 24 & 255;
        }
    }
    public static void drawImage(ResourceLocation image, double x, double y, double width, double height, int color1, int color2, int color3, int color4) {
        Minecraft minecraft = Minecraft.getInstance();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0);
        minecraft.getTextureManager().bindTexture(image);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR_TEX_LIGHTMAP);
        bufferBuilder.pos((float) x, (float) (y + height), 0).color((color1 >> 16) & 0xFF, (color1 >> 8) & 0xFF, color1 & 0xFF, color1 >>> 24).tex(0, 1 - 0.01f).lightmap(0, 240).endVertex();
        bufferBuilder.pos((float) (x + width), (float) (y + height), 0).color((color2 >> 16) & 0xFF, (color2 >> 8) & 0xFF, color2 & 0xFF, color2 >>> 24).tex(1, 1 - 0.01f).lightmap(0, 240).endVertex();
        bufferBuilder.pos((float) (x + width), (float) y, 0).color((color3 >> 16) & 0xFF, (color3 >> 8) & 0xFF, color3 & 0xFF, color3 >>> 24).tex(1, 0).lightmap(0, 240).endVertex();
        bufferBuilder.pos(x, (float) y, 0).color((color4 >> 16) & 0xFF, (color4 >> 8) & 0xFF, color4 & 0xFF, color4 >>> 24).tex(0, 0).lightmap(0, 240).endVertex();

        tessellator.draw();
        disableBlend();
    }
    public static void drawBloom(BloomMode mode,double x, double y, double width, double height, int color1, int color2, int color3, int color4) {
        DisplayUtils.drawImage(mode.namespace,
                x, y, width, height,
                color1, color2,
                color3, color4);
    }
    public static void drawShadowVertical(float x, float y, float width, float height, int radius, int color, int i) {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.01f);
        GlStateManager.disableAlphaTest();
        GL11.glShadeModel(7425);

        x -= radius;
        y -= radius;
        width = width + radius * 2;
        height = height + radius * 2;
        x -= 0.25f;
        y += 0.25f;

        int identifier = Objects.hash(width, height, radius);
        int textureId;

        if (shadowCache.containsKey(identifier)) {
            textureId = shadowCache.get(identifier);
            GlStateManager.bindTexture(textureId);
        } else {
            if (width <= 0) {
                width = 1;
            }

            if (height <= 0) {
                height = 1;
            }

            BufferedImage originalImage = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB_PRE);
            Graphics2D graphics = originalImage.createGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(radius, radius, (int) (width - radius * 2), (int) (height - radius * 2));
            graphics.dispose();

            GaussianFilter filter = new GaussianFilter(radius);
            BufferedImage blurredImage = filter.filter(originalImage, null);
            DynamicTexture texture = new DynamicTexture(TextureUtils.toNativeImage(blurredImage));
            texture.setBlurMipmap(true, true);
            try {
                textureId = texture.getGlTextureId();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            shadowCache.put(identifier, textureId);
        }

        float[] startColorComponents = ColorUtils.rgba(color);
        float[] i1 = ColorUtils.rgba(i);
        buffer.begin(GL11.GL_QUADS, POSITION_COLOR_TEX);
        buffer.pos(x, y, 0.0f)
                .color(startColorComponents[0], startColorComponents[1], startColorComponents[2],
                        startColorComponents[3])
                .tex(0.0f, 0.0f)
                .endVertex();

        buffer.pos(x, y + (float) ((int) height), 0.0f)
                .color(i1[0], i1[1], i1[2],
                        i1[3])
                .tex(0.0f, 1.0f)
                .endVertex();

        buffer.pos(x + (float) ((int) width), y + (float) ((int) height), 0.0f)
                .color(startColorComponents[0], startColorComponents[1], startColorComponents[2],
                        startColorComponents[3])
                .tex(1.0f, 1.0f)
                .endVertex();

        buffer.pos(x + (float) ((int) width), y, 0.0f)
                .color(i1[0], i1[1], i1[2],
                        i1[3])
                .tex(1.0f, 0.0f)
                .endVertex();

        tessellator.draw();
        GlStateManager.enableAlphaTest();
        GL11.glShadeModel(7424);
        GlStateManager.bindTexture(0);
        GlStateManager.disableBlend();
    }

    public static void drawShadow(float x, float y, float width, float height, int radius, int color) {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.01f);
        GlStateManager.disableAlphaTest();

        x -= radius;
        y -= radius;
        width = width + radius * 2;
        height = height + radius * 2;
        x -= 0.25f;
        y += 0.25f;

        int identifier = Objects.hash(width, height, radius);
        int textureId;

        if (shadowCache.containsKey(identifier)) {
            textureId = shadowCache.get(identifier);
            GlStateManager.bindTexture(textureId);
        } else {
            if (width <= 0) {
                width = 1;
            }

            if (height <= 0) {
                height = 1;
            }

            BufferedImage originalImage = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB_PRE);
            Graphics2D graphics = originalImage.createGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(radius, radius, (int) (width - radius * 2), (int) (height - radius * 2));
            graphics.dispose();

            GaussianFilter filter = new GaussianFilter(radius);
            BufferedImage blurredImage = filter.filter(originalImage, null);
            DynamicTexture texture = new DynamicTexture(TextureUtils.toNativeImage(blurredImage));
            texture.setBlurMipmap(true, true);
            try {
                textureId = texture.getGlTextureId();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            shadowCache.put(identifier, textureId);
        }

        float[] startColorComponents = ColorUtils.rgba(color);

        buffer.begin(GL11.GL_QUADS, POSITION_COLOR_TEX);
        buffer.pos(x, y, 0.0f)
                .color(startColorComponents[0], startColorComponents[1], startColorComponents[2],
                        startColorComponents[3])
                .tex(0.0f, 0.0f)
                .endVertex();

        buffer.pos(x, y + (float) ((int) height), 0.0f)
                .color(startColorComponents[0], startColorComponents[1], startColorComponents[2],
                        startColorComponents[3])
                .tex(0.0f, 1.0f)
                .endVertex();

        buffer.pos(x + (float) ((int) width), y + (float) ((int) height), 0.0f)
                .color(startColorComponents[0], startColorComponents[1], startColorComponents[2],
                        startColorComponents[3])
                .tex(1.0f, 1.0f)
                .endVertex();

        buffer.pos(x + (float) ((int) width), y, 0.0f)
                .color(startColorComponents[0], startColorComponents[1], startColorComponents[2],
                        startColorComponents[3])
                .tex(1.0f, 0.0f)
                .endVertex();

        tessellator.draw();
        GlStateManager.enableAlphaTest();
        GlStateManager.bindTexture(0);
        GlStateManager.disableBlend();
    }


    public static void drawImage(ResourceLocation resourceLocation, float x, float y, float width, float height,
                                 int color) {
        RenderSystem.pushMatrix();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.shadeModel(7425);
        mc.getTextureManager().bindTexture(resourceLocation);
        quads(x, y, width, height, 7, color);
        RenderSystem.shadeModel(7424);
        RenderSystem.color4f(1, 1, 1, 1);
        RenderSystem.popMatrix();

    }

    public static void drawImage(ResourceLocation resourceLocation, float x, float y, float width, float height,
                                 Vector4i color) {
        RenderSystem.pushMatrix();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.shadeModel(7425);
        mc.getTextureManager().bindTexture(resourceLocation);
        buffer.begin(7, POSITION_TEX_COLOR);
        {
            buffer.pos(x, y, 0).tex(0, 0).color(color.x).endVertex();
            buffer.pos(x, y + height, 0).tex(0, 1).color(color.y).endVertex();
            buffer.pos(x + width, y + height, 0).tex(1, 1).color(color.z).endVertex();
            buffer.pos(x + width, y, 0).tex(1, 0).color(color.w).endVertex();
        }
        tessellator.draw();
        RenderSystem.shadeModel(7424);
        RenderSystem.color4f(1, 1, 1, 1);
        RenderSystem.popMatrix();

    }

    public static void drawRectWBuilding(
            double left,
            double top,
            double right,
            double bottom,
            int color) {
        right += left;
        bottom += top;

        float f3 = (float) (color >> 24 & 255) / 255.0F;
        float f = (float) (color >> 16 & 255) / 255.0F;
        float f1 = (float) (color >> 8 & 255) / 255.0F;
        float f2 = (float) (color & 255) / 255.0F;
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
        bufferbuilder.pos(left, bottom, 0.0F).color(f, f1, f2, f3).endVertex();
        bufferbuilder.pos(right, bottom, 0.0F).color(f, f1, f2, f3).endVertex();
        bufferbuilder.pos(right, top, 0.0F).color(f, f1, f2, f3).endVertex();
        bufferbuilder.pos(left, top, 0.0F).color(f, f1, f2, f3).endVertex();
    }

    public static void drawRectBuilding(
            double left,
            double top,
            double right,
            double bottom,
            int color) {
        if (left < right) {
            double i = left;
            left = right;
            right = i;
        }

        if (top < bottom) {
            double j = top;
            top = bottom;
            bottom = j;
        }

        float f3 = (float) (color >> 24 & 255) / 255.0F;
        float f = (float) (color >> 16 & 255) / 255.0F;
        float f1 = (float) (color >> 8 & 255) / 255.0F;
        float f2 = (float) (color & 255) / 255.0F;
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
        bufferbuilder.pos(left, bottom, 0.0F).color(f, f1, f2, f3).endVertex();
        bufferbuilder.pos(right, bottom, 0.0F).color(f, f1, f2, f3).endVertex();
        bufferbuilder.pos(right, top, 0.0F).color(f, f1, f2, f3).endVertex();
        bufferbuilder.pos(left, top, 0.0F).color(f, f1, f2, f3).endVertex();
    }

    public static void drawMCVerticalBuilding(double x,
                                              double y,
                                              double width,
                                              double height,
                                              int start,
                                              int end) {

        float f = (float) (start >> 24 & 255) / 255.0F;
        float f1 = (float) (start >> 16 & 255) / 255.0F;
        float f2 = (float) (start >> 8 & 255) / 255.0F;
        float f3 = (float) (start & 255) / 255.0F;
        float f4 = (float) (end >> 24 & 255) / 255.0F;
        float f5 = (float) (end >> 16 & 255) / 255.0F;
        float f6 = (float) (end >> 8 & 255) / 255.0F;
        float f7 = (float) (end & 255) / 255.0F;


        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();

        bufferbuilder.pos(x, height, 0f).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos(width, height, 0f).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos(width, y, 0f).color(f5, f6, f7, f4).endVertex();
        bufferbuilder.pos(x, y, 0f).color(f5, f6, f7, f4).endVertex();
    }

    public static void drawMCHorizontalBuilding(double x,
                                                double y,
                                                double width,
                                                double height,
                                                int start,
                                                int end) {


        float f = (float) (start >> 24 & 255) / 255.0F;
        float f1 = (float) (start >> 16 & 255) / 255.0F;
        float f2 = (float) (start >> 8 & 255) / 255.0F;
        float f3 = (float) (start & 255) / 255.0F;
        float f4 = (float) (end >> 24 & 255) / 255.0F;
        float f5 = (float) (end >> 16 & 255) / 255.0F;
        float f6 = (float) (end >> 8 & 255) / 255.0F;
        float f7 = (float) (end & 255) / 255.0F;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.pos(x, height, 0f).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos(width, height, 0f).color(f5, f6, f7, f4).endVertex();
        bufferbuilder.pos(width, y, 0f).color(f5, f6, f7, f4).endVertex();
        bufferbuilder.pos(x, y, 0f).color(f1, f2, f3, f).endVertex();
    }
    public static void drawRect(
            double left,
            double top,
            double right,
            double bottom,
            int color) {
        if (left < right) {
            double i = left;
            left = right;
            right = i;
        }

        if (top < bottom) {
            double j = top;
            top = bottom;
            bottom = j;
        }

        float f3 = (float) (color >> 24 & 255) / 255.0F;
        float f = (float) (color >> 16 & 255) / 255.0F;
        float f1 = (float) (color >> 8 & 255) / 255.0F;
        float f2 = (float) (color & 255) / 255.0F;
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(left, bottom, 0.0F).color(f, f1, f2, f3).endVertex();
        bufferbuilder.pos(right, bottom, 0.0F).color(f, f1, f2, f3).endVertex();
        bufferbuilder.pos(right, top, 0.0F).color(f, f1, f2, f3).endVertex();
        bufferbuilder.pos(left, top, 0.0F).color(f, f1, f2, f3).endVertex();
        bufferbuilder.finishDrawing();
        WorldVertexBufferUploader.draw(bufferbuilder);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    public static void drawRectW(
            double x,
            double y,
            double w,
            double h,
            int color) {

        w = x + w;
        h = y + h;

        if (x < w) {
            double i = x;
            x = w;
            w = i;
        }

        if (y < h) {
            double j = y;
            y = h;
            h = j;
        }

        float f3 = (float) (color >> 24 & 255) / 255.0F;
        float f = (float) (color >> 16 & 255) / 255.0F;
        float f1 = (float) (color >> 8 & 255) / 255.0F;
        float f2 = (float) (color & 255) / 255.0F;
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(x, h, 0.0F).color(f, f1, f2, f3).endVertex();
        bufferbuilder.pos(w, h, 0.0F).color(f, f1, f2, f3).endVertex();
        bufferbuilder.pos(w, y, 0.0F).color(f, f1, f2, f3).endVertex();
        bufferbuilder.pos(x, y, 0.0F).color(f, f1, f2, f3).endVertex();
        bufferbuilder.finishDrawing();
        WorldVertexBufferUploader.draw(bufferbuilder);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    public static void drawRectHorizontalW(
            double x,
            double y,
            double w,
            double h,
            int color,
            int color1) {

        w = x + w;
        h = y + h;

        if (x < w) {
            double i = x;
            x = w;
            w = i;
        }

        if (y < h) {
            double j = y;
            y = h;
            h = j;
        }

        float[] colorOne = ColorUtils.rgba(color);
        float[] colorTwo = ColorUtils.rgba(color1);
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.shadeModel(7425);
        RenderSystem.defaultBlendFunc();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(x, h, 0.0F).color(colorTwo[0], colorTwo[1], colorTwo[2], colorTwo[3]).endVertex();
        bufferbuilder.pos(w, h, 0.0F).color(colorTwo[0], colorTwo[1], colorTwo[2], colorTwo[3]).endVertex();
        bufferbuilder.pos(w, y, 0.0F).color(colorOne[0], colorOne[1], colorOne[2], colorOne[3]).endVertex();
        bufferbuilder.pos(x, y, 0.0F).color(colorOne[0], colorOne[1], colorOne[2], colorOne[3]).endVertex();
        bufferbuilder.finishDrawing();
        WorldVertexBufferUploader.draw(bufferbuilder);
        RenderSystem.shadeModel(7424);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    public static void drawRectVerticalW(
            double x,
            double y,
            double w,
            double h,
            int color,
            int color1) {

        w = x + w;
        h = y + h;

        if (x < w) {
            double i = x;
            x = w;
            w = i;
        }

        if (y < h) {
            double j = y;
            y = h;
            h = j;
        }

        float[] colorOne = ColorUtils.rgba(color);
        float[] colorTwo = ColorUtils.rgba(color1);
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.shadeModel(7425);
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(x, h, 0.0F).color(colorOne[0], colorOne[1], colorOne[2], colorOne[3]).endVertex();
        bufferbuilder.pos(w, h, 0.0F).color(colorTwo[0], colorTwo[1], colorTwo[2], colorTwo[3]).endVertex();
        bufferbuilder.pos(w, y, 0.0F).color(colorTwo[0], colorTwo[1], colorTwo[2], colorTwo[3]).endVertex();
        bufferbuilder.pos(x, y, 0.0F).color(colorOne[0], colorOne[1], colorOne[2], colorOne[3]).endVertex();
        bufferbuilder.finishDrawing();
        WorldVertexBufferUploader.draw(bufferbuilder);
        RenderSystem.enableTexture();
        RenderSystem.shadeModel(7424);
        RenderSystem.disableBlend();
    }

    public static float[] rgb(final int color) {
        return new float[]{
                (color >> 16 & 0xFF) / 255f,
                (color >> 8 & 0xFF) / 255f,
                (color & 0xFF) / 255f,
                (color >> 24 & 0xFF) / 255f
        };
    }

    public static void quadsBegin(float x, float y, float width, float height, int glQuads) {
        buffer.begin(glQuads, POSITION_TEX);
        {
            buffer.pos(x, y, 0).tex(0, 0).endVertex();
            buffer.pos(x, y + height, 0).tex(0, 1).endVertex();
            buffer.pos(x + width, y + height, 0).tex(1, 1).endVertex();
            buffer.pos(x + width, y, 0).tex(1, 0).endVertex();
        }
        tessellator.draw();
    }
    public static void drawGradientRound(float x, float y, float width, float height, float radius, int bottomLeft, int topLeft, int bottomRight, int topRight) {
        RenderSystem.color4f(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        wtf.resolute.utiled.render.ShaderUtil.GRADIENT_ROUND_SHADER.attach();
        wtf.resolute.utiled.render.ShaderUtil.setupRoundedRectUniforms(x, y, width, height, radius, wtf.resolute.utiled.render.ShaderUtil.GRADIENT_ROUND_SHADER);

        wtf.resolute.utiled.render.ShaderUtil.GRADIENT_ROUND_SHADER.setUniform("color1", rgb(bottomLeft));
        wtf.resolute.utiled.render.ShaderUtil.GRADIENT_ROUND_SHADER.setUniform("color2", rgb(topLeft));
        wtf.resolute.utiled.render.ShaderUtil.GRADIENT_ROUND_SHADER.setUniform("color3", rgb(bottomRight));
        wtf.resolute.utiled.render.ShaderUtil.GRADIENT_ROUND_SHADER.setUniform("color4", rgb(topRight));

        wtf.resolute.utiled.render.ShaderUtil.GRADIENT_ROUND_SHADER.drawQuads(x -1,y -1,width + 2,height + 2);
        wtf.resolute.utiled.render.ShaderUtil.GRADIENT_ROUND_SHADER.detach();
        RenderSystem.disableBlend();
    }
    public static void drawRoundedRect(float x,
                                       float y,
                                       float width,
                                       float height,
                                       Vector4f vector4f,
                                       int color) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();

        wtf.resolute.utiled.shader.ShaderUtil.rounded.attach();

        wtf.resolute.utiled.shader.ShaderUtil.rounded.setUniform("size", width * 2, height * 2);
        wtf.resolute.utiled.shader.ShaderUtil.rounded.setUniform("round", vector4f.x * 2, vector4f.y * 2, vector4f.z * 2, vector4f.w * 2);

        wtf.resolute.utiled.shader.ShaderUtil.rounded.setUniform("smoothness", 0.f, 1.5f);
        wtf.resolute.utiled.shader.ShaderUtil.rounded.setUniform("color1",
                ColorUtils.rgba(color));
        wtf.resolute.utiled.shader.ShaderUtil.rounded.setUniform("color2",
                ColorUtils.rgba(color));
        wtf.resolute.utiled.shader.ShaderUtil.rounded.setUniform("color3",
                ColorUtils.rgba(color));
        wtf.resolute.utiled.shader.ShaderUtil.rounded.setUniform("color4",
                ColorUtils.rgba(color));
        drawQuads(x, y, width, height, 7);

        wtf.resolute.utiled.shader.ShaderUtil.rounded.detach();
        GlStateManager.disableBlend();

        GlStateManager.popMatrix();
    }

    public static void drawRoundedRect(float x,
                                       float y,
                                       float width,
                                       float height,
                                       Vector4f vector4f,
                                       Vector4i color) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        wtf.resolute.utiled.shader.ShaderUtil.rounded.attach();

        wtf.resolute.utiled.shader.ShaderUtil.rounded.setUniform("size", width * 2, height * 2);
        wtf.resolute.utiled.shader.ShaderUtil.rounded.setUniform("round", vector4f.x * 2, vector4f.y * 2, vector4f.z * 2, vector4f.w * 2);

        wtf.resolute.utiled.shader.ShaderUtil.rounded.setUniform("smoothness", 0.f, 1.5f);
        wtf.resolute.utiled.shader.ShaderUtil.rounded.setUniform("color1",
                ColorUtils.rgba(color.getX()));
        wtf.resolute.utiled.shader.ShaderUtil.rounded.setUniform("color2",
                ColorUtils.rgba(color.getY()));
        wtf.resolute.utiled.shader.ShaderUtil.rounded.setUniform("color3",
                ColorUtils.rgba(color.getZ()));
        wtf.resolute.utiled.shader.ShaderUtil.rounded.setUniform("color4",
                ColorUtils.rgba(color.getW()));
        drawQuads(x, y, width, height, 7);

        wtf.resolute.utiled.shader.ShaderUtil.rounded.detach();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }


    public static void drawRoundedRect(float x,
                                       float y,
                                       float width,
                                       float height,
                                       float outline,
                                       int color1,
                                       Vector4f vector4f,
                                       Vector4i color) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        wtf.resolute.utiled.shader.ShaderUtil.roundedout.attach();

        wtf.resolute.utiled.shader.ShaderUtil.roundedout.setUniform("size", width * 2, height * 2);
        wtf.resolute.utiled.shader.ShaderUtil.roundedout.setUniform("round", vector4f.x * 2, vector4f.y * 2, vector4f.z * 2, vector4f.w * 2);

        wtf.resolute.utiled.shader.ShaderUtil.roundedout.setUniform("smoothness", 0.f, 1.5f);
        wtf.resolute.utiled.shader.ShaderUtil.roundedout.setUniform("outlineColor",
                ColorUtils.rgba(color.getX()));
        wtf.resolute.utiled.shader.ShaderUtil.roundedout.setUniform("outlineColor1",
                ColorUtils.rgba(color.getY()));
        wtf.resolute.utiled.shader.ShaderUtil.roundedout.setUniform("outlineColor2",
                ColorUtils.rgba(color.getZ()));
        wtf.resolute.utiled.shader.ShaderUtil.roundedout.setUniform("outlineColor3",
                ColorUtils.rgba(color.getW()));
        wtf.resolute.utiled.shader.ShaderUtil.roundedout.setUniform("color", ColorUtils.rgba(color1));
        wtf.resolute.utiled.shader.ShaderUtil.roundedout.setUniform("outline",
                outline);
        drawQuads(x, y, width, height, 7);

        wtf.resolute.utiled.shader.ShaderUtil.rounded.detach();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    private static Framebuffer whiteCache = new Framebuffer(1, 1, false, true);
    private static Framebuffer contrastCache = new Framebuffer(1, 1, false, true);

    public static void drawContrast(float state) {
        state = MathHelper.clamp(state, 0, 1);
        GlStateManager.enableBlend();
        GlStateManager.color4f(1, 1, 1, 1);
        GlStateManager.glBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);

        contrastCache = wtf.resolute.utiled.shader.ShaderUtil.createFrameBuffer(contrastCache);

        contrastCache.framebufferClear(false);
        contrastCache.bindFramebuffer(true);

        // prepare image
        wtf.resolute.utiled.shader.ShaderUtil.contrast.attach();
        wtf.resolute.utiled.shader.ShaderUtil.contrast.setUniform("texture", 0);
        wtf.resolute.utiled.shader.ShaderUtil.contrast.setUniformf("contrast", state);
        GlStateManager.bindTexture(mc.getFramebuffer().framebufferTexture);

        wtf.resolute.utiled.shader.ShaderUtil.drawQuads();
        contrastCache.unbindFramebuffer();
        wtf.resolute.utiled.shader.ShaderUtil.contrast.detach();
        mc.getFramebuffer().bindFramebuffer(true);

        // draw image
        wtf.resolute.utiled.shader.ShaderUtil.contrast.attach();
        wtf.resolute.utiled.shader.ShaderUtil.contrast.setUniform("texture", 0);
        wtf.resolute.utiled.shader.ShaderUtil.contrast.setUniformf("contrast", state);
        GlStateManager.bindTexture(contrastCache.framebufferTexture);
        wtf.resolute.utiled.shader.ShaderUtil.drawQuads();
        wtf.resolute.utiled.shader.ShaderUtil.contrast.detach();

        GlStateManager.color4f(1, 1, 1, 1);
        GlStateManager.bindTexture(0);
    }

    public static void drawWhite(float state) {
        state = MathHelper.clamp(state, 0, 1);
        GlStateManager.enableBlend();
        GlStateManager.color4f(1, 1, 1, 1);
        GlStateManager.glBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);

        whiteCache = wtf.resolute.utiled.shader.ShaderUtil.createFrameBuffer(whiteCache);

        whiteCache.framebufferClear(false);
        whiteCache.bindFramebuffer(true);

        // prepare image
        wtf.resolute.utiled.shader.ShaderUtil.white.attach();
        wtf.resolute.utiled.shader.ShaderUtil.white.setUniform("texture", 0);
        wtf.resolute.utiled.shader.ShaderUtil.white.setUniformf("state", state);
        GlStateManager.bindTexture(mc.getFramebuffer().framebufferTexture);

        wtf.resolute.utiled.shader.ShaderUtil.drawQuads();
        whiteCache.unbindFramebuffer();
        wtf.resolute.utiled.shader.ShaderUtil.white.detach();
        mc.getFramebuffer().bindFramebuffer(true);

        // draw image
        wtf.resolute.utiled.shader.ShaderUtil.white.attach();
        wtf.resolute.utiled.shader.ShaderUtil.white.setUniform("texture", 0);
        wtf.resolute.utiled.shader.ShaderUtil.white.setUniformf("state", state);
        GlStateManager.bindTexture(whiteCache.framebufferTexture);
        wtf.resolute.utiled.shader.ShaderUtil.drawQuads();
        wtf.resolute.utiled.shader.ShaderUtil.white.detach();

        GlStateManager.color4f(1, 1, 1, 1);
        GlStateManager.bindTexture(0);
    }


    public static void drawRoundedRect(float x,
                                       float y,
                                       float width,
                                       float height,
                                       float radius,
                                       int color) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        wtf.resolute.utiled.shader.ShaderUtil.smooth.attach();

        wtf.resolute.utiled.shader.ShaderUtil.smooth.setUniformf("location", (float) (x * mc.getMainWindow().getGuiScaleFactor()),
                (float) ((mc.getMainWindow().getHeight() - (height * mc.getMainWindow().getGuiScaleFactor()))
                        - (y * mc.getMainWindow().getGuiScaleFactor())));
        wtf.resolute.utiled.shader.ShaderUtil.smooth.setUniformf("rectSize", width * mc.getMainWindow().getGuiScaleFactor(),
                height * mc.getMainWindow().getGuiScaleFactor());
        wtf.resolute.utiled.shader.ShaderUtil.smooth.setUniformf("radius", radius * mc.getMainWindow().getGuiScaleFactor());
        wtf.resolute.utiled.shader.ShaderUtil.smooth.setUniform("blur", 0);
        wtf.resolute.utiled.shader.ShaderUtil.smooth.setUniform("color",
                ColorUtils.rgba(color));
        drawQuads(x, y, width, height, 7);

        ShaderUtil.smooth.detach();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    public static void drawCircle(float x, float y, float radius, int color) {
        drawRoundedRect(x - radius / 2f, y - radius / 2f, radius, radius,
                radius / 2f, color);
    }

    public static void drawShadowCircle(float x, float y, float radius, int color) {
        drawShadow(x - radius / 2f, y - radius / 2f, radius, radius,
                (int) radius, color);
    }

    public static void drawQuads(float x, float y, float width, float height, int glQuads) {
        buffer.begin(glQuads, POSITION_TEX);
        {
            buffer.pos(x, y, 0).tex(0, 0).endVertex();
            buffer.pos(x, y + height, 0).tex(0, 1).endVertex();
            buffer.pos(x + width, y + height, 0).tex(1, 1).endVertex();
            buffer.pos(x + width, y, 0).tex(1, 0).endVertex();
        }
        Tessellator.getInstance().draw();
    }
    public static void drawBox(double x, double y, double width, double height, double size, int color) {
        drawRectBuilding(x + size, y, width - size, y + size, color);
        drawRectBuilding(x, y, x + size, height, color);

        drawRectBuilding(width - size, y, width, height, color);
        drawRectBuilding(x + size, height - size, width - size, height, color);
    }

    public static void drawBoxTest(double x, double y, double width, double height, double size, Vector4i colors) {
        drawMCHorizontalBuilding(x + size, y, width - size, y + size, colors.x, colors.z);
        drawMCVerticalBuilding(x, y, x + size, height, colors.z, colors.x);

        drawMCVerticalBuilding(width - size, y, width, height, colors.x, colors.z);
        drawMCHorizontalBuilding(x + size, height - size, width - size, height, colors.z, colors.x);
    }

    public static void enable(final int glTarget) {
        GL11.glEnable(glTarget);
    }

    public static void disable(final int glTarget) {
        GL11.glDisable(glTarget);
    }

    public static int reAlphaInt(final int color,
                                 final int alpha) {
        return (MathHelper.clamp(alpha, 0, 255) << 24) | (color & 16777215);
    }
    public static void start() {
        GlStateManager.pushMatrix();
        enable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        disable(GL11.GL_TEXTURE_2D);
        disable(GL11.GL_CULL_FACE);
        depthMask(false);
    }
    public static final void color(double red, double green, double blue, double alpha) {
        GL11.glColor4d(red, green, blue, alpha);
    }
    public static final void color(Color color) {
        if (color == null)
            color = Color.white;
        color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F);
    }
    public static void stop() {
        depthMask(true);
        enable(GL11.GL_CULL_FACE);
        enable(GL11.GL_TEXTURE_2D);
        disable(GL11.GL_BLEND);
        color(Color.white);
        GlStateManager.popMatrix();
    }

}