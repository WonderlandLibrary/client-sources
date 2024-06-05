/*
 * This file is part of Clientbase - https://github.com/DietrichPaul/Clientbase
 * by DietrichPaul, FlorianMichael and contributors
 *
 * To the extent possible under law, the person who associated CC0 with
 * Clientbase has waived all copyright and related or neighboring rights
 * to Clientbase.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work.  If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.dietrichpaul.clientbase.util.render.api;

import com.mojang.blaze3d.systems.RenderSystem;
import ladysnake.satin.api.managed.ManagedCoreShader;
import ladysnake.satin.api.managed.ShaderEffectManager;
import ladysnake.satin.api.managed.uniform.Uniform1f;
import ladysnake.satin.api.managed.uniform.Uniform2f;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import org.joml.Matrix4f;

public class Renderer2D {

    private static ManagedCoreShader fillCircleShader;
    private static Uniform2f fillCircleShaderCenter;
    private static Uniform1f fillCircleShaderRadius;

    private static ManagedCoreShader circleShader;
    private static Uniform2f circleShaderCenter;
    private static Uniform1f circleShaderRadius;

    public static ManagedCoreShader msdfShader;
    public static Uniform1f msdfShaderPxRange;

    public static void loadShaders() {
        ShaderEffectManager shaderEffectManager = ShaderEffectManager.getInstance();
        fillCircleShader = shaderEffectManager.manageCoreShader(new Identifier("clientbase", "fill_circle"));
        fillCircleShaderCenter = fillCircleShader.findUniform2f("Center");
        fillCircleShaderRadius = fillCircleShader.findUniform1f("Radius");


        circleShader = shaderEffectManager.manageCoreShader(new Identifier("clientbase", "circle"));
        circleShaderCenter = circleShader.findUniform2f("Center");
        circleShaderRadius = circleShader.findUniform1f("Radius");

        msdfShader = shaderEffectManager.manageCoreShader(new Identifier("clientbase", "msdf"), VertexFormats.POSITION_TEXTURE_COLOR);
        msdfShaderPxRange = msdfShader.findUniform1f("pxRange");
    }

    public static void drawCircle(MatrixStack matrices, float x, float y, float radius, int color) {
        circleShaderCenter.set(x, y);
        circleShaderRadius.set(radius);
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        float lw = RenderSystem.getShaderLineWidth();
        float x1 = x + radius + 1 + lw / 2f; // expand because AA
        float x2 = x - radius - 1 - lw / 2f;
        float y1 = y + radius + 1 + lw / 2f;
        float y2 = y - radius - 1 - lw / 2f;
        float f = (float)(color >> 24 & 255) / 255.0F;
        float g = (float)(color >> 16 & 255) / 255.0F;
        float h = (float)(color >> 8 & 255) / 255.0F;
        float j = (float)(color & 255) / 255.0F;
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        circleShader.getProgram().lineWidth.set(lw); // Is not null
        RenderSystem.setShader(circleShader::getProgram);
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(matrix, (float)x1, (float)y2, 0.0F).color(g, h, j, f).next();
        bufferBuilder.vertex(matrix, (float)x2, (float)y2, 0.0F).color(g, h, j, f).next();
        bufferBuilder.vertex(matrix, (float)x2, (float)y1, 0.0F).color(g, h, j, f).next();
        bufferBuilder.vertex(matrix, (float)x1, (float)y1, 0.0F).color(g, h, j, f).next();
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
        RenderSystem.disableBlend();
    }

    public static void fillCircle(MatrixStack matrices, float x, float y, float radius, int color) {
        fillCircleShaderCenter.set(x, y);
        fillCircleShaderRadius.set(radius);
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        float x1 = x + radius + 1; // expand because AA
        float x2 = x - radius - 1;
        float y1 = y + radius + 1;
        float y2 = y - radius - 1;
        float f = (float)(color >> 24 & 255) / 255.0F;
        float g = (float)(color >> 16 & 255) / 255.0F;
        float h = (float)(color >> 8 & 255) / 255.0F;
        float j = (float)(color & 255) / 255.0F;
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(fillCircleShader::getProgram);
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(matrix, (float)x1, (float)y2, 0.0F).color(g, h, j, f).next();
        bufferBuilder.vertex(matrix, (float)x2, (float)y2, 0.0F).color(g, h, j, f).next();
        bufferBuilder.vertex(matrix, (float)x2, (float)y1, 0.0F).color(g, h, j, f).next();
        bufferBuilder.vertex(matrix, (float)x1, (float)y1, 0.0F).color(g, h, j, f).next();
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
        RenderSystem.disableBlend();
    }

    public static void fill(MatrixStack matrices, float x1, float y1, float x2, float y2, int color) {
        Matrix4f model = matrices.peek().getPositionMatrix();

        float tmp;
        if (x1 < x2) {
            tmp = x1;
            x1 = x2;
            x2 = tmp;
        }
        if (y1 < y2) {
            tmp = y1;
            y1 = y2;
            y2 = tmp;
        }
        float alpha = ColorHelper.Argb.getAlpha(color) / 255F;
        float red = ColorHelper.Argb.getRed(color) / 255F;
        float green = ColorHelper.Argb.getGreen(color) / 255F;
        float blue = ColorHelper.Argb.getBlue(color) / 255F;
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(model, x1, y1, 0).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(model, x1, y2, 0).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(model, x2, y2, 0).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(model, x2, y1, 0).color(red, green, blue, alpha).next();
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
        RenderSystem.disableBlend();
    }

    public static void fill(MatrixStack matrices, float x1, float y1, float x2, float y2, int rd, int ru, int lu, int ld) {
        Matrix4f model = matrices.peek().getPositionMatrix();

        float tmp;
        if (x1 < x2) {
            tmp = x1;
            x1 = x2;
            x2 = tmp;
        }
        if (y1 < y2) {
            tmp = y1;
            y1 = y2;
            y2 = tmp;
        }
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(model, x1, y1, 0).color(rd).next();
        bufferBuilder.vertex(model, x1, y2, 0).color(ru).next();
        bufferBuilder.vertex(model, x2, y2, 0).color(lu).next();
        bufferBuilder.vertex(model, x2, y1, 0).color(ld).next();
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
        RenderSystem.disableBlend();
    }

    public static void drawTexture(MatrixStack matrices, float x, float y, float width, float height) {
        float x0 = x;
        float x1 = x + width;
        float y0 = y;
        float y1 = y + height;
        Matrix4f model = matrices.peek().getPositionMatrix();
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        bufferBuilder.vertex(model, x0, y0, 0).texture(0, 0).next();
        bufferBuilder.vertex(model, x0, y1, 0).texture(0, 1).next();
        bufferBuilder.vertex(model, x1, y1, 0).texture(1, 1).next();
        bufferBuilder.vertex(model, x1, y0, 0).texture(1, 0).next();
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
    }

}
