package dev.stephen.nexus.utils.render.shaders;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.stephen.nexus.utils.Utils;
import dev.stephen.nexus.utils.render.DrawUtils;
import dev.stephen.nexus.utils.render.shaders.impl.BlurredShadow;
import dev.stephen.nexus.utils.render.shaders.impl.GaussianFilter;
import dev.stephen.nexus.utils.render.shaders.impl.Texture;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;

// thunderhack dev gave me this
public class ShaderUtils implements Utils {
    public static HashMap<Integer, BlurredShadow> shadowCache = new HashMap<>();
    public static HashMap<Integer, BlurredShadow> shadowCache1 = new HashMap<>();

    public static void drawGlow(MatrixStack matrices, float x, float y, float width, float height, int blurRadius, Color color) {
        width = width + blurRadius * 2;
        height = height + blurRadius * 2;
        x = x - blurRadius;
        y = y - blurRadius;

        int identifier = (int) (width * height + width * blurRadius);
        if (shadowCache.containsKey(identifier)) {
            shadowCache.get(identifier).bind();
        } else {
            BufferedImage original = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB);
            Graphics g = original.getGraphics();
            g.setColor(new Color(-1));
            g.fillRect(blurRadius, blurRadius, (int) (width - blurRadius * 2), (int) (height - blurRadius * 2));
            g.dispose();
            GaussianFilter op = new GaussianFilter(blurRadius);
            BufferedImage blurred = op.filter(original, null);
            shadowCache.put(identifier, new BlurredShadow(blurred));
            return;
        }

        setupRender();
        RenderSystem.setShaderColor(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
        ShaderUtils.renderTexture(matrices, x, y, width, height, 0, 0, width, height, width, height);
        endRender();
    }

    public static void drawGradientBlur(MatrixStack matrices, float x, float y, float width, float height, int blurRadius, Color color1, Color color2, Color color3, Color color4) {
        width = width + blurRadius * 2;
        height = height + blurRadius * 2;
        x = x - blurRadius;
        y = y - blurRadius;

        int identifier = (int) (width * height + width * blurRadius);
        if (shadowCache1.containsKey(identifier)) {
            shadowCache1.get(identifier).bind();
        } else {
            BufferedImage original = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB);
            Graphics g = original.getGraphics();
            g.setColor(new Color(-1));
            g.fillRect(blurRadius, blurRadius, (int) (width - blurRadius * 2), (int) (height - blurRadius * 2));
            g.dispose();
            BufferedImage blurred = new GaussianFilter(blurRadius).filter(original, null);

            BufferedImage black = new BufferedImage((int) width + blurRadius * 2, (int) height + blurRadius * 2, BufferedImage.TYPE_INT_ARGB);
            Graphics g2 = black.getGraphics();
            g2.setColor(new Color(0x000000));
            g2.fillRect(0, 0, (int) width + blurRadius * 2, (int) height + blurRadius * 2);
            g2.dispose();

            BufferedImage combined = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB);
            Graphics g1 = combined.getGraphics();
            g1.drawImage(black, -blurRadius, -blurRadius, null);
            g1.drawImage(blurred, 0, 0, null);
            g1.dispose();

            shadowCache1.put(identifier, new BlurredShadow(combined));
            return;
        }

        setupRender();
        RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE);
        ShaderUtils.renderGradientTexture(matrices, x, y, width, height, 0, 0, width, height, width, height, color1, color2, color3, color4);
        endRender();
    }


    public static void renderTexture(MatrixStack matrices, double x0, double y0, double width, double height, float u, float v, double regionWidth, double regionHeight, double textureWidth, double textureHeight) {
        double x1 = x0 + width;
        double y1 = y0 + height;
        double z = 0;
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        buffer.vertex(matrix, (float) x0, (float) y1, (float) z).texture((u) / (float) textureWidth, (v + (float) regionHeight) / (float) textureHeight);
        buffer.vertex(matrix, (float) x1, (float) y1, (float) z).texture((u + (float) regionWidth) / (float) textureWidth, (v + (float) regionHeight) / (float) textureHeight);
        buffer.vertex(matrix, (float) x1, (float) y0, (float) z).texture((u + (float) regionWidth) / (float) textureWidth, (v) / (float) textureHeight);
        buffer.vertex(matrix, (float) x0, (float) y0, (float) z).texture((u) / (float) textureWidth, (v + 0.0F) / (float) textureHeight);
        BufferRenderer.drawWithGlobalProgram(buffer.end());
    }

    public static void registerBufferedImageTexture(Texture i, BufferedImage bi) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "png", baos);
            byte[] bytes = baos.toByteArray();
            registerTexture(i, bytes);
        } catch (Exception ignored) {
        }
    }

    private static void registerTexture(Texture i, byte[] content) {
        try {
            ByteBuffer data = BufferUtils.createByteBuffer(content.length).put(content);
            data.flip();
            NativeImageBackedTexture tex = new NativeImageBackedTexture(NativeImage.read(data));
            mc.execute(() -> mc.getTextureManager().registerTexture(i.getId(), tex));
        } catch (Exception ignored) {
        }
    }

    public static void renderGradientTexture(MatrixStack matrices, double x0, double y0, double width, double height, float u, float v, double regionWidth, double regionHeight, double textureWidth, double textureHeight, Color c1, Color c2, Color c3, Color c4) {
        RenderSystem.setShader(GameRenderer::getPositionTexColorProgram);
        BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
        renderGradientTextureInternal(buffer, matrices, x0, y0, width, height, u, v, regionWidth, regionHeight, textureWidth, textureHeight, c1, c2, c3, c4);
        BufferRenderer.drawWithGlobalProgram(buffer.end());
    }

    public static void renderGradientTextureInternal(BufferBuilder buff, MatrixStack matrices, double x0, double y0, double width, double height, float u, float v, double regionWidth, double regionHeight, double textureWidth, double textureHeight, Color c1, Color c2, Color c3, Color c4) {
        double x1 = x0 + width;
        double y1 = y0 + height;
        double z = 0;
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        buff.vertex(matrix, (float) x0, (float) y1, (float) z).texture((u) / (float) textureWidth, (v + (float) regionHeight) / (float) textureHeight).color(c1.getRGB());
        buff.vertex(matrix, (float) x1, (float) y1, (float) z).texture((u + (float) regionWidth) / (float) textureWidth, (v + (float) regionHeight) / (float) textureHeight).color(c2.getRGB());
        buff.vertex(matrix, (float) x1, (float) y0, (float) z).texture((u + (float) regionWidth) / (float) textureWidth, (v) / (float) textureHeight).color(c3.getRGB());
        buff.vertex(matrix, (float) x0, (float) y0, (float) z).texture((u) / (float) textureWidth, (v + 0.0F) / (float) textureHeight).color(c4.getRGB());
    }

    public static void setupRender() {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
    }

    public static void endRender() {
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
    }
}