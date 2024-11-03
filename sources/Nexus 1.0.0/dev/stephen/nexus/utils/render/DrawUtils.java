package dev.stephen.nexus.utils.render;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.stephen.nexus.utils.Utils;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL40C;

import java.awt.*;

public class DrawUtils implements Utils {

    // Rectangles
    public static void drawRect(MatrixStack matrices, float x, float y, float x2, float y2, Color c) {
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        setupRender();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        buffer.vertex(matrix, x, y2, 0.0F).color(c.getRGB());
        buffer.vertex(matrix, x2, y2, 0.0F).color(c.getRGB());
        buffer.vertex(matrix, x2, y, 0.0F).color(c.getRGB());
        buffer.vertex(matrix, x, y, 0.0F).color(c.getRGB());
        BufferRenderer.drawWithGlobalProgram(buffer.end());
        endRender();
    }

    public static void drawRectWithOutline(MatrixStack matrices, float x, float y, float x2, float y2, Color c, Color c2) {
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        setupRender();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        buffer.vertex(matrix, x, y2, 0.0F).color(c.getRGB());
        buffer.vertex(matrix, x2, y2, 0.0F).color(c.getRGB());
        buffer.vertex(matrix, x2, y, 0.0F).color(c.getRGB());
        buffer.vertex(matrix, x, y, 0.0F).color(c.getRGB());
        BufferRenderer.drawWithGlobalProgram(buffer.end());

        buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION_COLOR);
        buffer.vertex(matrix, x, y2, 0.0F).color(c2.getRGB());
        buffer.vertex(matrix, x2, y2, 0.0F).color(c2.getRGB());
        buffer.vertex(matrix, x2, y, 0.0F).color(c2.getRGB());
        buffer.vertex(matrix, x, y, 0.0F).color(c2.getRGB());
        buffer.vertex(matrix, x, y2, 0.0F).color(c2.getRGB());
        BufferRenderer.drawWithGlobalProgram(buffer.end());
        endRender();
    }

    // Gradient Rectangles
    public static void drawHorizontalGradientRect(MatrixStack matrices, float x1, float y1, float x2, float y2, Color startColor, Color endColor) {
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        setupRender();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        buffer.vertex(matrix, x1, y1, 0.0F).color(startColor.getRGB());
        buffer.vertex(matrix, x1, y2, 0.0F).color(startColor.getRGB());
        buffer.vertex(matrix, x2, y2, 0.0F).color(endColor.getRGB());
        buffer.vertex(matrix, x2, y1, 0.0F).color(endColor.getRGB());
        BufferRenderer.drawWithGlobalProgram(buffer.end());
        endRender();
    }

    public static void drawRoundedHorizontalGradientRect(MatrixStack matrices, float x, float y, float x2, float y2, float radius, Color startColor, Color endColor) {
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        RenderSystem.colorMask(false, false, false, true);
        RenderSystem.clearColor(0.0F, 0.0F, 0.0F, 0.0F);
        RenderSystem.clear(GL40C.GL_COLOR_BUFFER_BIT, false);
        RenderSystem.colorMask(true, true, true, true);

        Color color1 = startColor;
        Color color4 = startColor;
        Color color2 = endColor;
        Color color3 = endColor;

        drawRoundedRect(matrices, x, y, x2, y2, radius, color1);
        setupRender();
        RenderSystem.blendFunc(GL40C.GL_DST_ALPHA, GL40C.GL_ONE_MINUS_DST_ALPHA);
        BufferBuilder bufferBuilder = Tessellator.getInstance().begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(matrix, x, y2, 0.0F).color(color1.getRGB());
        bufferBuilder.vertex(matrix, x2, y2, 0.0F).color(color2.getRGB());
        bufferBuilder.vertex(matrix, x2, y, 0.0F).color(color3.getRGB());
        bufferBuilder.vertex(matrix, x, y, 0.0F).color(color4.getRGB());
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
        endRender();
    }

    public static void drawRoundedGradientRect(MatrixStack matrices, float x, float y, float x2, float y2, float radius, Color color1, Color color2, Color color3, Color color4) {
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        RenderSystem.colorMask(false, false, false, true);
        RenderSystem.clearColor(0.0F, 0.0F, 0.0F, 0.0F);
        RenderSystem.clear(GL40C.GL_COLOR_BUFFER_BIT, false);
        RenderSystem.colorMask(true, true, true, true);

        drawRoundedRect(matrices, x, y, x2, y2, radius, color1);
        setupRender();
        RenderSystem.blendFunc(GL40C.GL_DST_ALPHA, GL40C.GL_ONE_MINUS_DST_ALPHA);
        BufferBuilder bufferBuilder = Tessellator.getInstance().begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(matrix, x, y2, 0.0F).color(color1.getRGB());
        bufferBuilder.vertex(matrix, x2, y2, 0.0F).color(color2.getRGB());
        bufferBuilder.vertex(matrix, x2, y, 0.0F).color(color3.getRGB());
        bufferBuilder.vertex(matrix, x, y, 0.0F).color(color4.getRGB());
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
        endRender();
    }

    // Rounded Rectangle
    public static void drawRoundedRect(MatrixStack matrices, float x, float y, float x2, float y2, float radius, Color color) {
        renderRoundedQuad(matrices, color, x, y, x2, y2, radius, 4);
    }

    public static void renderRoundedQuad(MatrixStack matrices, Color c, double fromX, double fromY, double toX, double toY, double radius, double samples) {
        DrawUtils.setupRender();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        renderRoundedQuadInternal(matrices.peek().getPositionMatrix(), c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha() / 255f, fromX, fromY, toX, toY, radius, samples);
        DrawUtils.endRender();
    }

    public static void renderRoundedQuadInternal(Matrix4f matrix, float cr, float cg, float cb, float ca, double fromX, double fromY, double toX, double toY, double radius, double samples) {
        BufferBuilder bufferBuilder = Tessellator.getInstance().begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
        double[][] map = new double[][]{new double[]{toX - radius, toY - radius, radius}, new double[]{toX - radius, fromY + radius, radius}, new double[]{fromX + radius, fromY + radius, radius}, new double[]{fromX + radius, toY - radius, radius}};
        for (int i = 0; i < 4; i++) {
            double[] current = map[i];
            double rad = current[2];
            for (double r = i * 90d; r < (360 / 4d + i * 90d); r += (90 / samples)) {
                float rad1 = (float) Math.toRadians(r);
                float sin = (float) (Math.sin(rad1) * rad);
                float cos = (float) (Math.cos(rad1) * rad);
                bufferBuilder.vertex(matrix, (float) current[0] + sin, (float) current[1] + cos, 0.0F).color(cr, cg, cb, ca);
            }
            float rad1 = (float) Math.toRadians((360 / 4d + i * 90d));
            float sin = (float) (Math.sin(rad1) * rad);
            float cos = (float) (Math.cos(rad1) * rad);
            bufferBuilder.vertex(matrix, (float) current[0] + sin, (float) current[1] + cos, 0.0F).color(cr, cg, cb, ca);
        }
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
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

    public static void rectPoint4VerticalGradient(BufferBuilder bufferBuilder, Matrix4f matrix, float x, float y, float x1, float y1, Color topColor, Color bottomColor) {
        rectPoint4(bufferBuilder, matrix, x, y, x1, y1, topColor, topColor, bottomColor, bottomColor);
    }

    public static void rectPoint4HorizontalGradient(BufferBuilder bufferBuilder, Matrix4f matrix, float x, float y, float x1, float y1, Color leftColor, Color rightColor) {
        rectPoint4(bufferBuilder, matrix, x, y, x1, y1, leftColor, rightColor, rightColor, leftColor);
    }

    public static void rectPoint4(BufferBuilder bufferBuilder, Matrix4f matrix, float x, float y, float x1, float y1, Color color) {
        rectPoint4(bufferBuilder, matrix, x, y, x1, y1, color, color, color, color);
    }

    public static void rectPoint4(BufferBuilder bufferBuilder, Matrix4f matrix, float x, float y, float x1, float y1, Color topLeftColor, Color topRightColor, Color bottomRightColor, Color bottomLeftColor) {
        bufferBuilder.vertex(matrix, x1, y, 0.0F).color(topRightColor.getRGB());
        bufferBuilder.vertex(matrix, x, y, 0.0F).color(topLeftColor.getRGB());
        bufferBuilder.vertex(matrix, x, y1, 0.0F).color(bottomLeftColor.getRGB());
        bufferBuilder.vertex(matrix, x1, y1, 0.0F).color(bottomRightColor.getRGB());
    }
}
