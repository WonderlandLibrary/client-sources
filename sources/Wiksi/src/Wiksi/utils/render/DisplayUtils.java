//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package src.Wiksi.utils.render;

import com.jhlabs.image.GaussianFilter;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import src.Wiksi.ui.styles.Style;
import src.Wiksi.utils.client.IMinecraft;
import src.Wiksi.utils.math.Vector4i;
import src.Wiksi.utils.shader.ShaderHandler;
import src.Wiksi.utils.shader.ShaderUtil;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector4f;
import net.optifine.util.TextureUtils;
import org.lwjgl.opengl.GL11;

public class DisplayUtils implements IMinecraft {
    private static final HashMap<Integer, Integer> shadowCache = new HashMap();
    public static ShaderHandler shaderMainMenu = new ShaderHandler("shaderMainMenu");
    private static Framebuffer whiteCache = new Framebuffer(1, 1, false, true);
    private static Framebuffer contrastCache = new Framebuffer(1, 1, false, true);

    public DisplayUtils() {
    }

    public static void quads(float x, float y, float width, float height, int glQuads, int color) {
        buffer.begin(glQuads, DefaultVertexFormats.POSITION_TEX_COLOR);
        buffer.pos((double)x, (double)y, 0.0).tex(0.0F, 0.0F).color(color).endVertex();
        buffer.pos((double)x, (double)(y + height), 0.0).tex(0.0F, 1.0F).color(color).endVertex();
        buffer.pos((double)(x + width), (double)(y + height), 0.0).tex(1.0F, 1.0F).color(color).endVertex();
        buffer.pos((double)(x + width), (double)y, 0.0).tex(1.0F, 0.0F).color(color).endVertex();
        tessellator.draw();
    }

    public static void drawImageAlpha(ResourceLocation resourceLocation, float x, float y, float width, float height, Vector4i color) {
        RenderSystem.pushMatrix();
        RenderSystem.disableWiksi();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.shadeModel(7425);
        RenderSystem.disableCull();
        RenderSystem.disableAlphaTest();
        RenderSystem.blendFuncSeparate(770, 1, 0, 1);
        mc.getTextureManager().bindTexture(resourceLocation);
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        buffer.pos((double)x, (double)y, 0.0).tex(0.0F, 0.99F).lightmap(0, 240).color(color.x).endVertex();
        buffer.pos((double)x, (double)(y + height), 0.0).tex(1.0F, 0.99F).lightmap(0, 240).color(color.y).endVertex();
        buffer.pos((double)(x + width), (double)(y + height), 0.0).tex(1.0F, 0.0F).lightmap(0, 240).color(color.z).endVertex();
        buffer.pos((double)(x + width), (double)y, 0.0).tex(0.0F, 0.0F).lightmap(0, 240).color(color.w).endVertex();
        tessellator.draw();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        RenderSystem.enableCull();
        RenderSystem.enableAlphaTest();
        RenderSystem.depthMask(true);
        RenderSystem.popMatrix();
    }

    public static boolean isInRegion(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public static boolean isInRegion(double mouseX, double mouseY, float x, float y, float width, float height) {
        return mouseX >= (double)x && mouseX <= (double)(x + width) && mouseY >= (double)y && mouseY <= (double)(y + height);
    }

    public static boolean isInRegion(double mouseX, double mouseY, int x, int y, int width, int height) {
        return mouseX >= (double)x && mouseX <= (double)(x + width) && mouseY >= (double)y && mouseY <= (double)(y + height);
    }

    public static void drawtargetespimage(MatrixStack stack, ResourceLocation image, double x, double y, double z, double width, double height, int color1, int color2, int color3, int color4) {
        Minecraft minecraft = Minecraft.getInstance();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 1);
        GL11.glShadeModel(7425);
        GL11.glAlphaFunc(516, 0.0F);
        minecraft.getTextureManager().bindTexture(image);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR_TEX_LIGHTMAP);
        bufferBuilder.pos(stack.getLast().getMatrix(), (float)x, (float)(y + height), (float)z).color(color1 >> 16 & 255, color1 >> 8 & 255, color1 & 255, color1 >>> 24).tex(0.0F, 0.99F).lightmap(0, 240).endVertex();
        bufferBuilder.pos(stack.getLast().getMatrix(), (float)(x + width), (float)(y + height), (float)z).color(color2 >> 16 & 255, color2 >> 8 & 255, color2 & 255, color2 >>> 24).tex(1.0F, 0.99F).lightmap(0, 240).endVertex();
        bufferBuilder.pos(stack.getLast().getMatrix(), (float)(x + width), (float)y, (float)z).color(color3 >> 16 & 255, color3 >> 8 & 255, color3 & 255, color3 >>> 24).tex(1.0F, 0.0F).lightmap(0, 240).endVertex();
        bufferBuilder.pos(stack.getLast().getMatrix(), (float)x, (float)y, (float)z).color(color4 >> 16 & 255, color4 >> 8 & 255, color4 & 255, color4 >>> 24).tex(0.0F, 0.0F).lightmap(0, 240).endVertex();
        tessellator.draw();
        RenderSystem.disableBlend();
    }

    public static void drawGradientRoundedRect(float x, float y, float width, float height, float radius, int startColor, int endColor) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glShadeModel(7425);
        GL11.glBegin(6);
        setColor(startColor);
        GL11.glVertex2f(x + radius, y + radius);
        setColor(endColor);
        GL11.glVertex2f(x + radius, y + height - radius);
        setColor(startColor);
        GL11.glVertex2f(x + width - radius, y + height - radius);
        setColor(endColor);
        GL11.glVertex2f(x + width - radius, y + radius);
        GL11.glEnd();
        drawRoundedCorner(x + radius, y + radius, radius, startColor, endColor, true, true);
        drawRoundedCorner(x + width - radius, y + radius, radius, startColor, endColor, false, true);
        drawRoundedCorner(x + width - radius, y + height - radius, radius, startColor, endColor, false, false);
        drawRoundedCorner(x + radius, y + height - radius, radius, startColor, endColor, true, false);
        GL11.glShadeModel(7424);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    private static void setColor(int color) {
        float alpha = (float)(color >> 24 & 255) / 255.0F;
        float red = (float)(color >> 16 & 255) / 255.0F;
        float green = (float)(color >> 8 & 255) / 255.0F;
        float blue = (float)(color & 255) / 255.0F;
        GL11.glColor4f(red, green, blue, alpha);
    }

    private static void drawRoundedCorner(float cx, float cy, float radius, int startColor, int endColor, boolean left, boolean top) {
        GL11.glBegin(6);
        setColor(startColor);
        GL11.glVertex2f(cx, cy);

        for(int i = 0; i <= 90; i += 5) {
            double angle = Math.toRadians((double)i);
            float dx = (float)(Math.cos(angle) * (double)radius);
            float dy = (float)(Math.sin(angle) * (double)radius);
            if (!left) {
                dx = -dx;
            }

            if (!top) {
                dy = -dy;
            }

            setColor(endColor);
            GL11.glVertex2f(cx + dx, cy + dy);
        }

        GL11.glEnd();
    }

    public static void scissor(double x, double y, double width, double height) {
        double scale = mc.getMainWindow().getGuiScaleFactor();
        y = (double)mc.getMainWindow().getScaledHeight() - y;
        x *= scale;
        y *= scale;
        width *= scale;
        height *= scale;
        GL11.glScissor((int)x, (int)(y - height), (int)width, (int)height);
    }

    public static void drawCircle1(float x, float y, float start, float end, float radius, float width, boolean filled, int color) {
        if (start > end) {
            float endOffset = end;
            end = start;
            start = endOffset;
        }

        GlStateManager.enableBlend();
        GL11.glDisable(3553);
        RenderSystem.blendFuncSeparate(770, 771, 1, 0);
        GL11.glEnable(2848);
        GL11.glLineWidth(width);
        GL11.glBegin(3);

        float i;
        float cos;
        float sin;
        for(i = end; i >= start; --i) {
            ColorUtils.setColor(color);
            cos = MathHelper.cos((float)((double)i * Math.PI / 180.0)) * radius;
            sin = MathHelper.sin((float)((double)i * Math.PI / 180.0)) * radius;
            GL11.glVertex2f(x + cos, y + sin);
        }

        GL11.glEnd();
        GL11.glDisable(2848);
        if (filled) {
            GL11.glBegin(6);

            for(i = end; i >= start; --i) {
                ColorUtils.setColor(color);
                cos = MathHelper.cos((float)((double)i * Math.PI / 180.0)) * radius;
                sin = MathHelper.sin((float)((double)i * Math.PI / 180.0)) * radius;
                GL11.glVertex2f(x + cos, y + sin);
            }

            GL11.glEnd();
        }

        GL11.glEnable(3553);
        GlStateManager.disableBlend();
    }

    public static void drawCircle1(float x, float y, float start, float end, float radius, float width, boolean filled, Style s) {
        if (start > end) {
            float endOffset = end;
            end = start;
            start = endOffset;
        }

        GlStateManager.enableBlend();
        RenderSystem.disableAlphaTest();
        GL11.glDisable(3553);
        RenderSystem.blendFuncSeparate(770, 771, 1, 0);
        RenderSystem.shadeModel(7425);
        GL11.glEnable(2848);
        GL11.glLineWidth(width);
        GL11.glBegin(3);

        float i;
        float cos;
        float sin;
        for(i = end; i >= start; --i) {
            ColorUtils.setColor(ColorUtils.getColor((int)(i * 1.0F)));
            cos = MathHelper.cos((float)((double)i * Math.PI / 180.0)) * radius;
            sin = MathHelper.sin((float)((double)i * Math.PI / 180.0)) * radius;
            GL11.glVertex2f(x + cos, y + sin);
        }

        GL11.glEnd();
        GL11.glDisable(2848);
        if (filled) {
            GL11.glBegin(6);

            for(i = end; i >= start; --i) {
                ColorUtils.setColor(ColorUtils.getColor((int)(i * 1.0F)));
                cos = MathHelper.cos((float)((double)i * Math.PI / 180.0)) * radius;
                sin = MathHelper.sin((float)((double)i * Math.PI / 180.0)) * radius;
                GL11.glVertex2f(x + cos, y + sin);
            }

            GL11.glEnd();
        }

        RenderSystem.enableAlphaTest();
        RenderSystem.shadeModel(7424);
        GL11.glEnable(3553);
        GlStateManager.disableBlend();
    }

    public static void drawShadow(float x, float y, float width, float height, int radius, int color, int i) {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.alphaFunc(516, 0.01F);
        GlStateManager.disableAlphaTest();
        GL11.glShadeModel(7425);
        x -= (float)radius;
        y -= (float)radius;
        width += (float)(radius * 2);
        height += (float)(radius * 2);
        x -= 0.25F;
        y += 0.25F;
        int identifier = Objects.hash(new Object[]{width, height, radius});
        int textureId;
        if (shadowCache.containsKey(identifier)) {
            textureId = (Integer)shadowCache.get(identifier);
            GlStateManager.bindTexture(textureId);
        } else {
            if (width <= 0.0F) {
                width = 1.0F;
            }

            if (height <= 0.0F) {
                height = 1.0F;
            }

            BufferedImage originalImage = new BufferedImage((int)width, (int)height, 3);
            Graphics2D graphics = originalImage.createGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(radius, radius, (int)(width - (float)(radius * 2)), (int)(height - (float)(radius * 2)));
            graphics.dispose();
            GaussianFilter filter = new GaussianFilter((float)radius);
            BufferedImage blurredImage = filter.filter(originalImage, (BufferedImage)null);
            DynamicTexture texture = new DynamicTexture(TextureUtils.toNativeImage(blurredImage));
            texture.setBlurMipmap(true, true);
            textureId = texture.getGlTextureId();
            shadowCache.put(identifier, textureId);
        }

        float[] startColorComponents = ColorUtils.rgba(color);
        float[] i1 = ColorUtils.rgba(i);
        buffer.begin(7, DefaultVertexFormats.POSITION_COLOR_TEX);
        buffer.pos((double)x, (double)y, 0.0).color(startColorComponents[0], startColorComponents[1], startColorComponents[2], startColorComponents[3]).tex(0.0F, 0.0F).endVertex();
        buffer.pos((double)x, (double)(y + (float)((int)height)), 0.0).color(startColorComponents[0], startColorComponents[1], startColorComponents[2], startColorComponents[3]).tex(0.0F, 1.0F).endVertex();
        buffer.pos((double)(x + (float)((int)width)), (double)(y + (float)((int)height)), 0.0).color(i1[0], i1[1], i1[2], i1[3]).tex(1.0F, 1.0F).endVertex();
        buffer.pos((double)(x + (float)((int)width)), (double)y, 0.0).color(i1[0], i1[1], i1[2], i1[3]).tex(1.0F, 0.0F).endVertex();
        tessellator.draw();
        GlStateManager.enableAlphaTest();
        GL11.glShadeModel(7424);
        GlStateManager.bindTexture(0);
        GlStateManager.disableBlend();
    }

    public static void drawShadowVertical(float x, float y, float width, float height, int radius, int color, int i) {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.alphaFunc(516, 0.01F);
        GlStateManager.disableAlphaTest();
        GL11.glShadeModel(7425);
        x -= (float)radius;
        y -= (float)radius;
        width += (float)(radius * 2);
        height += (float)(radius * 2);
        x -= 0.25F;
        y += 0.25F;
        int identifier = Objects.hash(new Object[]{width, height, radius});
        int textureId;
        if (shadowCache.containsKey(identifier)) {
            textureId = (Integer)shadowCache.get(identifier);
            GlStateManager.bindTexture(textureId);
        } else {
            if (width <= 0.0F) {
                width = 1.0F;
            }

            if (height <= 0.0F) {
                height = 1.0F;
            }

            BufferedImage originalImage = new BufferedImage((int)width, (int)height, 3);
            Graphics2D graphics = originalImage.createGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(radius, radius, (int)(width - (float)(radius * 2)), (int)(height - (float)(radius * 2)));
            graphics.dispose();
            GaussianFilter filter = new GaussianFilter((float)radius);
            BufferedImage blurredImage = filter.filter(originalImage, (BufferedImage)null);
            DynamicTexture texture = new DynamicTexture(TextureUtils.toNativeImage(blurredImage));
            texture.setBlurMipmap(true, true);

            try {
                textureId = texture.getGlTextureId();
            } catch (Exception var15) {
                Exception e = var15;
                throw new RuntimeException(e);
            }

            shadowCache.put(identifier, textureId);
        }

        float[] startColorComponents = ColorUtils.rgba(color);
        float[] i1 = ColorUtils.rgba(i);
        buffer.begin(7, DefaultVertexFormats.POSITION_COLOR_TEX);
        buffer.pos((double)x, (double)y, 0.0).color(startColorComponents[0], startColorComponents[1], startColorComponents[2], startColorComponents[3]).tex(0.0F, 0.0F).endVertex();
        buffer.pos((double)x, (double)(y + (float)((int)height)), 0.0).color(i1[0], i1[1], i1[2], i1[3]).tex(0.0F, 1.0F).endVertex();
        buffer.pos((double)(x + (float)((int)width)), (double)(y + (float)((int)height)), 0.0).color(startColorComponents[0], startColorComponents[1], startColorComponents[2], startColorComponents[3]).tex(1.0F, 1.0F).endVertex();
        buffer.pos((double)(x + (float)((int)width)), (double)y, 0.0).color(i1[0], i1[1], i1[2], i1[3]).tex(1.0F, 0.0F).endVertex();
        tessellator.draw();
        GlStateManager.enableAlphaTest();
        GL11.glShadeModel(7424);
        GlStateManager.bindTexture(0);
        GlStateManager.disableBlend();
    }

    public static void drawShadow(float x, float y, float width, float height, int radius, int color) {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.alphaFunc(516, 0.01F);
        GlStateManager.disableAlphaTest();
        x -= (float)radius;
        y -= (float)radius;
        width += (float)(radius * 2);
        height += (float)(radius * 2);
        x -= 0.25F;
        y += 0.25F;
        int identifier = Objects.hash(new Object[]{width, height, radius});
        int textureId;
        if (shadowCache.containsKey(identifier)) {
            textureId = (Integer)shadowCache.get(identifier);
            GlStateManager.bindTexture(textureId);
        } else {
            if (width <= 0.0F) {
                width = 1.0F;
            }

            if (height <= 0.0F) {
                height = 1.0F;
            }

            BufferedImage originalImage = new BufferedImage((int)width, (int)height, 3);
            Graphics2D graphics = originalImage.createGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(radius, radius, (int)(width - (float)(radius * 2)), (int)(height - (float)(radius * 2)));
            graphics.dispose();
            GaussianFilter filter = new GaussianFilter((float)radius);
            BufferedImage blurredImage = filter.filter(originalImage, (BufferedImage)null);
            DynamicTexture texture = new DynamicTexture(TextureUtils.toNativeImage(blurredImage));
            texture.setBlurMipmap(true, true);

            try {
                textureId = texture.getGlTextureId();
            } catch (Exception var14) {
                Exception e = var14;
                throw new RuntimeException(e);
            }

            shadowCache.put(identifier, textureId);
        }

        float[] startColorComponents = ColorUtils.rgba(color);
        buffer.begin(7, DefaultVertexFormats.POSITION_COLOR_TEX);
        buffer.pos((double)x, (double)y, 0.0).color(startColorComponents[0], startColorComponents[1], startColorComponents[2], startColorComponents[3]).tex(0.0F, 0.0F).endVertex();
        buffer.pos((double)x, (double)(y + (float)((int)height)), 0.0).color(startColorComponents[0], startColorComponents[1], startColorComponents[2], startColorComponents[3]).tex(0.0F, 1.0F).endVertex();
        buffer.pos((double)(x + (float)((int)width)), (double)(y + (float)((int)height)), 0.0).color(startColorComponents[0], startColorComponents[1], startColorComponents[2], startColorComponents[3]).tex(1.0F, 1.0F).endVertex();
        buffer.pos((double)(x + (float)((int)width)), (double)y, 0.0).color(startColorComponents[0], startColorComponents[1], startColorComponents[2], startColorComponents[3]).tex(1.0F, 0.0F).endVertex();
        tessellator.draw();
        GlStateManager.enableAlphaTest();
        GlStateManager.bindTexture(0);
        GlStateManager.disableBlend();
    }

    public static void drawImage(ResourceLocation resourceLocation, float x, float y, float width, float height, int color) {
        RenderSystem.pushMatrix();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.shadeModel(7425);
        mc.getTextureManager().bindTexture(resourceLocation);
        quads(x, y, width, height, 7, color);
        RenderSystem.shadeModel(7424);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.popMatrix();
    }

    public static void drawImage(ResourceLocation resourceLocation, float x, float y, float width, float height, Vector4i color) {
        RenderSystem.pushMatrix();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.shadeModel(7425);
        mc.getTextureManager().bindTexture(resourceLocation);
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        buffer.pos((double)x, (double)y, 0.0).tex(0.0F, 0.0F).color(color.x).endVertex();
        buffer.pos((double)x, (double)(y + height), 0.0).tex(0.0F, 1.0F).color(color.y).endVertex();
        buffer.pos((double)(x + width), (double)(y + height), 0.0).tex(1.0F, 1.0F).color(color.z).endVertex();
        buffer.pos((double)(x + width), (double)y, 0.0).tex(1.0F, 0.0F).color(color.w).endVertex();
        tessellator.draw();
        RenderSystem.shadeModel(7424);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.popMatrix();
    }

    public static void drawRectWBuilding(double left, double top, double right, double bottom, int color) {
        right += left;
        bottom += top;
        float f3 = (float)(color >> 24 & 255) / 255.0F;
        float f = (float)(color >> 16 & 255) / 255.0F;
        float f1 = (float)(color >> 8 & 255) / 255.0F;
        float f2 = (float)(color & 255) / 255.0F;
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
        bufferbuilder.pos(left, bottom, 0.0).color(f, f1, f2, f3).endVertex();
        bufferbuilder.pos(right, bottom, 0.0).color(f, f1, f2, f3).endVertex();
        bufferbuilder.pos(right, top, 0.0).color(f, f1, f2, f3).endVertex();
        bufferbuilder.pos(left, top, 0.0).color(f, f1, f2, f3).endVertex();
    }

    public static void drawRectBuilding(double left, double top, double right, double bottom, int color) {
        double j;
        if (left < right) {
            j = left;
            left = right;
            right = j;
        }

        if (top < bottom) {
            j = top;
            top = bottom;
            bottom = j;
        }

        float f3 = (float)(color >> 24 & 255) / 255.0F;
        float f = (float)(color >> 16 & 255) / 255.0F;
        float f1 = (float)(color >> 8 & 255) / 255.0F;
        float f2 = (float)(color & 255) / 255.0F;
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
        bufferbuilder.pos(left, bottom, 0.0).color(f, f1, f2, f3).endVertex();
        bufferbuilder.pos(right, bottom, 0.0).color(f, f1, f2, f3).endVertex();
        bufferbuilder.pos(right, top, 0.0).color(f, f1, f2, f3).endVertex();
        bufferbuilder.pos(left, top, 0.0).color(f, f1, f2, f3).endVertex();
    }

    public static void drawMCVerticalBuilding(double x, double y, double width, double height, int start, int end) {
        float f = (float)(start >> 24 & 255) / 255.0F;
        float f1 = (float)(start >> 16 & 255) / 255.0F;
        float f2 = (float)(start >> 8 & 255) / 255.0F;
        float f3 = (float)(start & 255) / 255.0F;
        float f4 = (float)(end >> 24 & 255) / 255.0F;
        float f5 = (float)(end >> 16 & 255) / 255.0F;
        float f6 = (float)(end >> 8 & 255) / 255.0F;
        float f7 = (float)(end & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.pos(x, height, 0.0).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos(width, height, 0.0).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos(width, y, 0.0).color(f5, f6, f7, f4).endVertex();
        bufferbuilder.pos(x, y, 0.0).color(f5, f6, f7, f4).endVertex();
    }

    public static void drawMCHorizontalBuilding(double x, double y, double width, double height, int start, int end) {
        float f = (float)(start >> 24 & 255) / 255.0F;
        float f1 = (float)(start >> 16 & 255) / 255.0F;
        float f2 = (float)(start >> 8 & 255) / 255.0F;
        float f3 = (float)(start & 255) / 255.0F;
        float f4 = (float)(end >> 24 & 255) / 255.0F;
        float f5 = (float)(end >> 16 & 255) / 255.0F;
        float f6 = (float)(end >> 8 & 255) / 255.0F;
        float f7 = (float)(end & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.pos(x, height, 0.0).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos(width, height, 0.0).color(f5, f6, f7, f4).endVertex();
        bufferbuilder.pos(width, y, 0.0).color(f5, f6, f7, f4).endVertex();
        bufferbuilder.pos(x, y, 0.0).color(f1, f2, f3, f).endVertex();
    }

    public static void drawRect(double left, double top, double right, double bottom, int color) {
        double j;
        if (left < right) {
            j = left;
            left = right;
            right = j;
        }

        if (top < bottom) {
            j = top;
            top = bottom;
            bottom = j;
        }

        float f3 = (float)(color >> 24 & 255) / 255.0F;
        float f = (float)(color >> 16 & 255) / 255.0F;
        float f1 = (float)(color >> 8 & 255) / 255.0F;
        float f2 = (float)(color & 255) / 255.0F;
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(left, bottom, 0.0).color(f, f1, f2, f3).endVertex();
        bufferbuilder.pos(right, bottom, 0.0).color(f, f1, f2, f3).endVertex();
        bufferbuilder.pos(right, top, 0.0).color(f, f1, f2, f3).endVertex();
        bufferbuilder.pos(left, top, 0.0).color(f, f1, f2, f3).endVertex();
        bufferbuilder.finishDrawing();
        WorldVertexBufferUploader.draw(bufferbuilder);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    public static void drawRectW(double x, double y, double w, double h, int color) {
        w += x;
        h += y;
        double j;
        if (x < w) {
            j = x;
            x = w;
            w = j;
        }

        if (y < h) {
            j = y;
            y = h;
            h = j;
        }

        float f3 = (float)(color >> 24 & 255) / 255.0F;
        float f = (float)(color >> 16 & 255) / 255.0F;
        float f1 = (float)(color >> 8 & 255) / 255.0F;
        float f2 = (float)(color & 255) / 255.0F;
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(x, h, 0.0).color(f, f1, f2, f3).endVertex();
        bufferbuilder.pos(w, h, 0.0).color(f, f1, f2, f3).endVertex();
        bufferbuilder.pos(w, y, 0.0).color(f, f1, f2, f3).endVertex();
        bufferbuilder.pos(x, y, 0.0).color(f, f1, f2, f3).endVertex();
        bufferbuilder.finishDrawing();
        WorldVertexBufferUploader.draw(bufferbuilder);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    public static void drawRectHorizontalW(double x, double y, double w, double h, int color, int color1) {
        w += x;
        h += y;
        double j;
        if (x < w) {
            j = x;
            x = w;
            w = j;
        }

        if (y < h) {
            j = y;
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
        bufferbuilder.pos(x, h, 0.0).color(colorTwo[0], colorTwo[1], colorTwo[2], colorTwo[3]).endVertex();
        bufferbuilder.pos(w, h, 0.0).color(colorTwo[0], colorTwo[1], colorTwo[2], colorTwo[3]).endVertex();
        bufferbuilder.pos(w, y, 0.0).color(colorOne[0], colorOne[1], colorOne[2], colorOne[3]).endVertex();
        bufferbuilder.pos(x, y, 0.0).color(colorOne[0], colorOne[1], colorOne[2], colorOne[3]).endVertex();
        bufferbuilder.finishDrawing();
        WorldVertexBufferUploader.draw(bufferbuilder);
        RenderSystem.shadeModel(7424);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    public static void drawRectVerticalW(double x, double y, double w, double h, int color, int color1) {
        w += x;
        h += y;
        double j;
        if (x < w) {
            j = x;
            x = w;
            w = j;
        }

        if (y < h) {
            j = y;
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
        bufferbuilder.pos(x, h, 0.0).color(colorOne[0], colorOne[1], colorOne[2], colorOne[3]).endVertex();
        bufferbuilder.pos(w, h, 0.0).color(colorTwo[0], colorTwo[1], colorTwo[2], colorTwo[3]).endVertex();
        bufferbuilder.pos(w, y, 0.0).color(colorTwo[0], colorTwo[1], colorTwo[2], colorTwo[3]).endVertex();
        bufferbuilder.pos(x, y, 0.0).color(colorOne[0], colorOne[1], colorOne[2], colorOne[3]).endVertex();
        bufferbuilder.finishDrawing();
        WorldVertexBufferUploader.draw(bufferbuilder);
        RenderSystem.enableTexture();
        RenderSystem.shadeModel(7424);
        RenderSystem.disableBlend();
    }

    public static void drawRoundedRect(float x, float y, float width, float height, Vector4f vector4f, int color) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        ShaderUtil.rounded.attach();
        ShaderUtil.rounded.setUniform("size", new float[]{width * 2.0F, height * 2.0F});
        ShaderUtil.rounded.setUniform("round", new float[]{vector4f.x * 2.0F, vector4f.y * 2.0F, vector4f.z * 2.0F, vector4f.w * 2.0F});
        ShaderUtil.rounded.setUniform("smoothness", new float[]{0.0F, 1.5F});
        ShaderUtil.rounded.setUniform("color1", ColorUtils.rgba(color));
        ShaderUtil.rounded.setUniform("color2", ColorUtils.rgba(color));
        ShaderUtil.rounded.setUniform("color3", ColorUtils.rgba(color));
        ShaderUtil.rounded.setUniform("color4", ColorUtils.rgba(color));
        drawQuads(x, y, width, height, 7);
        ShaderUtil.rounded.detach();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawRoundedRect1(float x, float y, float width, float height, Vector4f vector4f, int color, int color1) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        ShaderUtil.rounded.attach();
        ShaderUtil.rounded.setUniform("size", new float[]{width * 2.0F, height * 2.0F});
        ShaderUtil.rounded.setUniform("round", new float[]{vector4f.x * 2.0F, vector4f.y * 2.0F, vector4f.z * 2.0F, vector4f.w * 2.0F});
        ShaderUtil.rounded.setUniform("smoothness", new float[]{0.0F, 1.5F});
        ShaderUtil.rounded.setUniform("color1", ColorUtils.rgba(color));
        ShaderUtil.rounded.setUniform("color2", ColorUtils.rgba(color));
        ShaderUtil.rounded.setUniform("color3", ColorUtils.rgba(color));
        ShaderUtil.rounded.setUniform("color4", ColorUtils.rgba(color));
        drawQuads(x, y, width, height, 7);
        ShaderUtil.rounded.detach();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawGradientRound(float x, float y, float width, float height, float radius, int bottomLeft, int topLeft, int bottomRight, int topRight) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(770, 771);
        ShaderUtil.rounded.attach();
        ShaderUtil.rounded.setUniform(String.valueOf(x), new float[]{y, width, height, radius, width * 2.0F, -1.0F});
        ShaderUtil.rounded.setUniform("color1", DisplayUtils.IntColor.rgb(bottomLeft));
        ShaderUtil.rounded.setUniform("color2", DisplayUtils.IntColor.rgb(topLeft));
        ShaderUtil.rounded.setUniform("color3", DisplayUtils.IntColor.rgb(bottomRight));
        ShaderUtil.rounded.setUniform("color4", DisplayUtils.IntColor.rgb(topRight));
        drawQuads(x, y, width, height, 7);
        ShaderUtil.rounded.detach();
        RenderSystem.disableBlend();
    }

    public static void drawRoundedRect(float x, float y, float width, float height, Vector4f vector4f, Vector4i color) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        ShaderUtil.rounded.attach();
        ShaderUtil.rounded.setUniform("size", new float[]{width * 2.0F, height * 2.0F});
        ShaderUtil.rounded.setUniform("round", new float[]{vector4f.x * 2.0F, vector4f.y * 2.0F, vector4f.z * 2.0F, vector4f.w * 2.0F});
        ShaderUtil.rounded.setUniform("smoothness", new float[]{0.0F, 1.5F});
        ShaderUtil.rounded.setUniform("color1", ColorUtils.rgba(color.getX()));
        ShaderUtil.rounded.setUniform("color2", ColorUtils.rgba(color.getY()));
        ShaderUtil.rounded.setUniform("color3", ColorUtils.rgba(color.getZ()));
        ShaderUtil.rounded.setUniform("color4", ColorUtils.rgba(color.getW()));
        drawQuads(x, y, width, height, 7);
        ShaderUtil.rounded.detach();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawRoundedRect(float x, float y, float width, float height, float outline, int color1, Vector4f vector4f, Vector4i color) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        ShaderUtil.roundedout.attach();
        ShaderUtil.roundedout.setUniform("size", new float[]{width * 2.0F, height * 2.0F});
        ShaderUtil.roundedout.setUniform("round", new float[]{vector4f.x * 2.0F, vector4f.y * 2.0F, vector4f.z * 2.0F, vector4f.w * 2.0F});
        ShaderUtil.roundedout.setUniform("smoothness", new float[]{0.0F, 1.5F});
        ShaderUtil.roundedout.setUniform("outlineColor", ColorUtils.rgba(color.getX()));
        ShaderUtil.roundedout.setUniform("outlineColor1", ColorUtils.rgba(color.getY()));
        ShaderUtil.roundedout.setUniform("outlineColor2", ColorUtils.rgba(color.getZ()));
        ShaderUtil.roundedout.setUniform("outlineColor3", ColorUtils.rgba(color.getW()));
        ShaderUtil.roundedout.setUniform("color", ColorUtils.rgba(color1));
        ShaderUtil.roundedout.setUniform("outline", new float[]{outline});
        drawQuads(x, y, width, height, 7);
        ShaderUtil.rounded.detach();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawContrast(float state) {
        state = MathHelper.clamp(state, 0.0F, 1.0F);
        GlStateManager.enableBlend();
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.glBlendFuncSeparate(770, 771, 1, 0);
        contrastCache = ShaderUtil.createFrameBuffer(contrastCache);
        contrastCache.framebufferClear(false);
        contrastCache.bindFramebuffer(true);
        ShaderUtil.contrast.attach();
        ShaderUtil.contrast.setUniform("texture", new int[]{0});
        ShaderUtil.contrast.setUniformf("contrast", new float[]{state});
        GlStateManager.bindTexture(mc.getFramebuffer().framebufferTexture);
        ShaderUtil.drawQuads();
        contrastCache.unbindFramebuffer();
        ShaderUtil.contrast.detach();
        mc.getFramebuffer().bindFramebuffer(true);
        ShaderUtil.contrast.attach();
        ShaderUtil.contrast.setUniform("texture", new int[]{0});
        ShaderUtil.contrast.setUniformf("contrast", new float[]{state});
        GlStateManager.bindTexture(contrastCache.framebufferTexture);
        ShaderUtil.drawQuads();
        ShaderUtil.contrast.detach();
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.bindTexture(0);
    }

    public static void drawWhite(float state) {
        state = MathHelper.clamp(state, 0.0F, 1.0F);
        GlStateManager.enableBlend();
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.glBlendFuncSeparate(770, 771, 1, 0);
        whiteCache = ShaderUtil.createFrameBuffer(whiteCache);
        whiteCache.framebufferClear(false);
        whiteCache.bindFramebuffer(true);
        ShaderUtil.white.attach();
        ShaderUtil.white.setUniform("texture", new int[]{0});
        ShaderUtil.white.setUniformf("state", new float[]{state});
        GlStateManager.bindTexture(mc.getFramebuffer().framebufferTexture);
        ShaderUtil.drawQuads();
        whiteCache.unbindFramebuffer();
        ShaderUtil.white.detach();
        mc.getFramebuffer().bindFramebuffer(true);
        ShaderUtil.white.attach();
        ShaderUtil.white.setUniform("texture", new int[]{0});
        ShaderUtil.white.setUniformf("state", new float[]{state});
        GlStateManager.bindTexture(whiteCache.framebufferTexture);
        ShaderUtil.drawQuads();
        ShaderUtil.white.detach();
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.bindTexture(0);
    }

    public static void drawRoundedRect(float x, float y, float width, float height, float radius, int color) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        ShaderUtil.smooth.attach();
        ShaderUtil.smooth.setUniformf("location", new float[]{(float)((double)x * mc.getMainWindow().getGuiScaleFactor()), (float)((double)mc.getMainWindow().getHeight() - (double)height * mc.getMainWindow().getGuiScaleFactor() - (double)y * mc.getMainWindow().getGuiScaleFactor())});
        ShaderUtil.smooth.setUniformf("rectSize", new double[]{(double)width * mc.getMainWindow().getGuiScaleFactor(), (double)height * mc.getMainWindow().getGuiScaleFactor()});
        ShaderUtil.smooth.setUniformf("radius", new double[]{(double)radius * mc.getMainWindow().getGuiScaleFactor()});
        ShaderUtil.smooth.setUniform("blur", new int[]{0});
        ShaderUtil.smooth.setUniform("color", ColorUtils.rgba(color));
        drawQuads(x, y, width, height, 7);
        ShaderUtil.smooth.detach();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawRoundedRect1(float x, float y, float width, float height, float radius, int color, int gradientAlpha) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        ShaderUtil.smooth.attach();
        ShaderUtil.smooth.setUniformf("location", new float[]{(float)((double)x * mc.getMainWindow().getGuiScaleFactor()), (float)((double)mc.getMainWindow().getHeight() - (double)height * mc.getMainWindow().getGuiScaleFactor() - (double)y * mc.getMainWindow().getGuiScaleFactor())});
        ShaderUtil.smooth.setUniformf("rectSize", new double[]{(double)width * mc.getMainWindow().getGuiScaleFactor(), (double)height * mc.getMainWindow().getGuiScaleFactor()});
        ShaderUtil.smooth.setUniformf("radius", new double[]{(double)radius * mc.getMainWindow().getGuiScaleFactor()});
        ShaderUtil.smooth.setUniform("blur", new int[]{0});
        ShaderUtil.smooth.setUniform("color", ColorUtils.rgba(color));
        drawQuads(x, y, width, height, 7);
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

    public static void drawQuads(float x, float y, float width, float height, int glQuads) {
        buffer.begin(glQuads, DefaultVertexFormats.POSITION_TEX);
        buffer.pos((double)x, (double)y, 0.0).tex(0.0F, 0.0F).endVertex();
        buffer.pos((double)x, (double)(y + height), 0.0).tex(0.0F, 1.0F).endVertex();
        buffer.pos((double)(x + width), (double)(y + height), 0.0).tex(1.0F, 1.0F).endVertex();
        buffer.pos((double)(x + width), (double)y, 0.0).tex(1.0F, 0.0F).endVertex();
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

    public static class IntColor {
        public IntColor() {
        }

        public static float[] rgb(int color) {
            return new float[]{(float)(color >> 16 & 255) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F, (float)(color >> 24 & 255) / 255.0F};
        }

        public static int rgba(int r, int g, int b, int a) {
            return a << 24 | r << 16 | g << 8 | b;
        }

        public static int getRed(int hex) {
            return hex >> 16 & 255;
        }

        public static int getGreen(int hex) {
            return hex >> 8 & 255;
        }

        public static int getBlue(int hex) {
            return hex & 255;
        }

        public static int getAlpha(int hex) {
            return hex >> 24 & 255;
        }
    }
}
