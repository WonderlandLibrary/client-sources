// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.systems.VertexSorter;
import dev.lvstrng.argon.Argon;
import dev.lvstrng.argon.interfaces.IBufferedBuilder;
import dev.lvstrng.argon.modules.impl.ClickGui;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class RenderUtil {
    public static VertexSorter sorting;

    public static Vec3d getCameraPos() {
        return Argon.mc.getBlockEntityRenderDispatcher().camera.getPos();
    }

    public static double method411() {
        return (Argon.mc.getCurrentFps() > 0) ? (1.0 / Argon.mc.getCurrentFps()) : 1.0;
    }

    public static float method412(final float end, final float start, final float multiple) {
        return (1.0f - MathHelper.clamp((float) (method411() * multiple), 0.0f, 1.0f)) * end + MathHelper.clamp((float) (method411() * multiple), 0.0f, 1.0f) * start;
    }

    public static Vec3d getPlayerLookVecInterpolated(final PlayerEntity player) {
        final float cos = MathHelper.cos(-player.getYaw() * 0.017453292f - 3.1415927f);
        final float sin = MathHelper.sin(-player.getYaw() * 0.017453292f - 3.1415927f);
        final float n = -MathHelper.cos(-player.getPitch() * 0.017453292f);
        return new Vec3d(sin * n, MathHelper.sin(-player.getPitch() * 0.017453292f), cos * n).normalize();
    }

    public static void startDrawing() {
        sorting = RenderSystem.getVertexSorting();
        RenderSystem.setProjectionMatrix(new Matrix4f().setOrtho(0.0f, (float) Argon.mc.getWindow().getFramebufferWidth(), (float) Argon.mc.getWindow().getFramebufferHeight(), 0.0f, 1000.0f, 21000.0f), VertexSorter.BY_Z);
    }

    public static void stopDrawing() {
        RenderSystem.setProjectionMatrix(new Matrix4f().setOrtho(0.0f, (float) (Argon.mc.getWindow().getFramebufferWidth() / Argon.mc.getWindow().getScaleFactor()), (float) (Argon.mc.getWindow().getFramebufferHeight() / Argon.mc.getWindow().getScaleFactor()), 0.0f, 1000.0f, 21000.0f), RenderUtil.sorting);
    }

    public static void method416(final MatrixStack matrices, final Color c, final double x, final double y, final double x2, final double y2, final double corner1, final double corner2, final double corner3, final double corner4, final double samples) {
        final int rgb = c.getRGB();
        final Matrix4f positionMatrix = matrices.peek().getPositionMatrix();
        final float ca = (rgb >> 24 & 0xFF) / 255.0f;
        final float cr = (rgb >> 16 & 0xFF) / 255.0f;
        final float cg = (rgb >> 8 & 0xFF) / 255.0f;
        final float cb = (rgb & 0xFF) / 255.0f;
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        method427(positionMatrix, cr, cg, cb, ca, x, y, x2, y2, corner1, corner2, corner3, corner4, samples);
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
    }

    private static void method417() {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
    }

    private static void method418() {
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
    }

    public static void method419(final MatrixStack matrices, final Color c, final double x, final double y, final double x1, final double y1, final double rad, final double samples) {
        method416(matrices, c, x, y, x1, y1, rad, rad, rad, rad, samples);
    }

    public static void method420(final Matrix4f matrix, final float cr, final float cg, final float cb, final float ca, final double fromX, final double fromY, final double toX, final double toY, final double radC1, final double radC2, final double radC3, final double radC4, final double width, final double samples) {
        final BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        buffer.begin(VertexFormat.DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);
        final double[][] array = {{toX - radC4, toY - radC4, radC4}, {toX - radC2, fromY + radC2, radC2}, {fromX + radC1, fromY + radC1, radC1}, {fromX + radC3, toY - radC3, radC3}};
        int i = 0;
        while (i < 4) {
            final double[] array2 = array[i];
            final double n3 = array2[2];
            double angdeg = i * 90.0;
            while (angdeg < 90.0 + i * 90.0) {
                final float n6 = (float) Math.toRadians(angdeg);
                final double sin = Math.sin(n6);
                final float n7 = (float) (sin * n3);
                final double cos = Math.cos(n6);
                final float n8 = (float) (cos * n3);
                buffer.vertex(matrix, (float) array2[0] + n7, (float) array2[1] + n8, 0.0f).color(cr, cg, cb, ca).next();
                buffer.vertex(matrix, (float) (array2[0] + n7 + sin * width), (float) (array2[1] + n8 + cos * width), 0.0f).color(cr, cg, cb, ca).next();
                angdeg += 90.0 / samples;
            }
            final float n9 = (float) Math.toRadians(90.0 + i * 90.0);
            final double sin2 = Math.sin(n9);
            final float n10 = (float) (sin2 * n3);
            final double cos2 = Math.cos(n9);
            final float n11 = (float) (cos2 * n3);
            buffer.vertex(matrix, (float) array2[0] + n10, (float) array2[1] + n11, 0.0f).color(cr, cg, cb, ca).next();
            buffer.vertex(matrix, (float) (array2[0] + n10 + sin2 * width), (float) (array2[1] + n11 + cos2 * width), 0.0f).color(cr, cg, cb, ca).next();
            ++i;
        }
    }

    public static void method422(final MatrixStack matrices, final Color c, final double originX, final double originY, final double rad, final int segments) {
        final int clamp = MathHelper.clamp(segments, 4, 360);
        final int rgb = c.getRGB();
        final Matrix4f positionMatrix = matrices.peek().getPositionMatrix();
        final int n = 0;
        final float n2 = (rgb >> 24 & 0xFF) / 255.0f;
        final float n3 = (rgb >> 16 & 0xFF) / 255.0f;
        final float n4 = (rgb >> 8 & 0xFF) / 255.0f;
        final float n5 = (rgb & 0xFF) / 255.0f;
        final BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        method417();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        buffer.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
        int i = 0;
        while (i < 360) {
            final double radians = Math.toRadians(i);
            buffer.vertex(positionMatrix, (float) (originX + Math.sin(radians) * rad), (float) (originY + Math.cos(radians) * rad), 0.0f).color(n3, n4, n5, n2).next();
            i += Math.min(360 / clamp, 360 - i);
        }
        BufferRenderer.drawWithGlobalProgram(buffer.end());
        method418();
    }

    public static void method423(final MatrixStack matrixStack, final Color color, final Color color2, final Color color3, final Color color4, final float f, final float f2, final float f3, final float f4, final float f5, final float f6) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        final Tessellator instance = Tessellator.getInstance();
        final BufferBuilder buffer = instance.getBuffer();
        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);
        buffer.vertex(matrixStack.peek().getPositionMatrix(), f - 10.0f, f2 - 10.0f, 0.0f).next();
        buffer.vertex(matrixStack.peek().getPositionMatrix(), f - 10.0f, f2 + f4 + 20.0f, 0.0f).next();
        buffer.vertex(matrixStack.peek().getPositionMatrix(), f + f3 + 20.0f, f2 + f4 + 20.0f, 0.0f).next();
        buffer.vertex(matrixStack.peek().getPositionMatrix(), f + f3 + 20.0f, f2 - 10.0f, 0.0f).next();
        instance.draw();
        RenderSystem.disableBlend();
    }

    public static void method424(final DrawContext poses, final Color c, final double fromX, final double fromY, final double toX, final double toY, final double rad1, final double rad2, final double rad3, final double rad4, final double width, final double samples) {
        final int rgb = c.getRGB();
        final Matrix4f positionMatrix = poses.getMatrices().peek().getPositionMatrix();
        final float ca = (rgb >> 24 & 0xFF) / 255.0f;
        final float cr = (rgb >> 16 & 0xFF) / 255.0f;
        final float cg = (rgb >> 8 & 0xFF) / 255.0f;
        final float cb = (rgb & 0xFF) / 255.0f;
        method417();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        method420(positionMatrix, cr, cg, cb, ca, fromX, fromY, toX, toY, rad1, rad2, rad3, rad4, width, samples);
        method418();
    }

    public static MatrixStack method425(final double x, final double y, final double z) {
        final MatrixStack matrixStack = new MatrixStack();
        final Camera camera = MinecraftClient.getInstance().gameRenderer.getCamera();
        matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera.getPitch()));
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(camera.getYaw() + 180.0f));
        matrixStack.translate(x - camera.getPos().x, y - camera.getPos().y, z - camera.getPos().z);
        return matrixStack;
    }

    public static void method426(final MatrixStack matrices, final float x, final float y, final float width, final float height, final int color) {
        final float n = (color >> 24 & 0xFF) / 255.0f;
        final float n2 = (color >> 16 & 0xFF) / 255.0f;
        final float n3 = (color >> 8 & 0xFF) / 255.0f;
        final float n4 = (color & 0xFF) / 255.0f;
        matrices.push();
        matrices.scale(0.5f, 0.5f, 0.5f);
        matrices.translate(x, y, 0.0);
        final Tessellator instance = Tessellator.getInstance();
        final BufferBuilder buffer = instance.getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        buffer.vertex(0.0, 0.0, 0.0).color(n2, n3, n4, n).next();
        buffer.vertex(0.0, height, 0.0).color(n2, n3, n4, n).next();
        buffer.vertex(width, height, 0.0).color(n2, n3, n4, n).next();
        buffer.vertex(width, 0.0, 0.0).color(n2, n3, n4, n).next();
        instance.draw();
        RenderSystem.disableBlend();
        matrices.pop();
    }

    public static void method427(final Matrix4f matrix, final float cr, final float cg, final float cb, final float ca, final double fromX, final double fromY, final double toX, final double toY, final double corner1, final double corner2, final double corner3, final double corner4, final double samples) {
        final BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        buffer.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
        final double[][] array = {{toX - corner4, toY - corner4, corner4}, {toX - corner2, fromY + corner2, corner2}, {fromX + corner1, fromY + corner1, corner1}, {fromX + corner3, toY - corner3, corner3}};
        int i = 0;
        while (i < 4) {
            final double[] array2 = array[i];
            final double n3 = array2[2];
            double angdeg = i * 90.0;
            while (angdeg < 90.0 + i * 90.0) {
                final float n4 = (float) Math.toRadians(angdeg);
                buffer.vertex(matrix, (float) array2[0] + (float) (Math.sin(n4) * n3), (float) array2[1] + (float) (Math.cos(n4) * n3), 0.0f).color(cr, cg, cb, ca).next();
                angdeg += 90.0 / samples;
            }
            final float n5 = (float) Math.toRadians(90.0 + i * 90.0);
            buffer.vertex(matrix, (float) array2[0] + (float) (Math.sin(n5) * n3), (float) array2[1] + (float) (Math.cos(n5) * n3), 0.0f).color(cr, cg, cb, ca).next();
            ++i;
        }
        BufferRenderer.drawWithGlobalProgram(buffer.end());
    }

    public static void method428(MatrixStack p0, float p1, float p2, float p3, float p4, float p5, float p6, Color p7) {
        RenderSystem.enableBlend();
        RenderSystem.disableDepthTest();
        RenderSystem.setShaderColor((float) p7.getRed() / 255.0f, (float) p7.getGreen() / 255.0f, (float) p7.getBlue() / 255.0f, (float) p7.getAlpha() / 255.0f);
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        Tessellator v11 = Tessellator.getInstance();
        BufferBuilder v12 = v11.getBuffer();
        v12.begin(VertexFormat.DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION);
        v12.vertex(p0.peek().getPositionMatrix(), p1, p2, p3).next();
        v12.vertex(p0.peek().getPositionMatrix(), p1, p2, p3).next();
        v12.vertex(p0.peek().getPositionMatrix(), p1, p2, p3).next();
        v12.vertex(p0.peek().getPositionMatrix(), p1, p2, p6).next();
        v12.vertex(p0.peek().getPositionMatrix(), p1, p5, p3).next();
        v12.vertex(p0.peek().getPositionMatrix(), p1, p5, p6).next();
        v12.vertex(p0.peek().getPositionMatrix(), p1, p5, p6).next();
        v12.vertex(p0.peek().getPositionMatrix(), p1, p2, p6).next();
        v12.vertex(p0.peek().getPositionMatrix(), p4, p5, p6).next();
        v12.vertex(p0.peek().getPositionMatrix(), p4, p2, p6).next();
        v12.vertex(p0.peek().getPositionMatrix(), p4, p2, p6).next();
        v12.vertex(p0.peek().getPositionMatrix(), p4, p2, p3).next();
        v12.vertex(p0.peek().getPositionMatrix(), p4, p5, p6).next();
        v12.vertex(p0.peek().getPositionMatrix(), p4, p5, p3).next();
        v12.vertex(p0.peek().getPositionMatrix(), p4, p5, p3).next();
        v12.vertex(p0.peek().getPositionMatrix(), p4, p2, p3).next();
        v12.vertex(p0.peek().getPositionMatrix(), p1, p5, p3).next();
        v12.vertex(p0.peek().getPositionMatrix(), p1, p2, p3).next();
        v12.vertex(p0.peek().getPositionMatrix(), p1, p2, p3).next();
        v12.vertex(p0.peek().getPositionMatrix(), p4, p2, p3).next();
        v12.vertex(p0.peek().getPositionMatrix(), p1, p2, p6).next();
        v12.vertex(p0.peek().getPositionMatrix(), p4, p2, p6).next();
        v12.vertex(p0.peek().getPositionMatrix(), p4, p2, p6).next();
        v12.vertex(p0.peek().getPositionMatrix(), p1, p5, p3).next();
        v12.vertex(p0.peek().getPositionMatrix(), p1, p5, p3).next();
        v12.vertex(p0.peek().getPositionMatrix(), p1, p5, p6).next();
        v12.vertex(p0.peek().getPositionMatrix(), p4, p5, p3).next();
        v12.vertex(p0.peek().getPositionMatrix(), p4, p5, p6).next();
        v12.vertex(p0.peek().getPositionMatrix(), p4, p5, p6).next();
        v12.vertex(p0.peek().getPositionMatrix(), p4, p5, p6).next();
        v11.draw();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
    }

    public static void method429(final MatrixStack matrices, final Color color, final Vec3d start, final Vec3d end) {
        matrices.push();
        final Matrix4f positionMatrix = matrices.peek().getPositionMatrix();
        if (ClickGui.msaaSetting.getValue()) {
            GL11.glEnable(32925);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
        }
        GL11.glDepthFunc(519);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableBlend();
        method430(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR, GameRenderer::getPositionColorProgram, positionMatrix, start, end.subtract(start), color, RenderUtil::method433);
        GL11.glDepthFunc(515);
        RenderSystem.disableBlend();
        if (ClickGui.msaaSetting.getValue()) {
//            GL11.glDisable(2848);
//            GL11.glDisable(32925);
        }
        matrices.pop();
    }

    private static void method430(VertexFormat.DrawMode drawMode, VertexFormat vertexFormat, Supplier<ShaderProgram> supplier, Matrix4f matrix4f, Vec3d vec3d, Vec3d vec3d2, Color color, IBufferedBuilder class107) {
        float f = (float) color.getRed() / 255.0f;
        float f2 = (float) color.getGreen() / 255.0f;
        float f3 = (float) color.getBlue() / 255.0f;
        float f4 = (float) color.getAlpha() / 255.0f;
        Vec3d vec3d3 = vec3d.add(vec3d2);
        float f5 = (float) vec3d.x;
        float f6 = (float) vec3d.y;
        float f7 = (float) vec3d.z;
        float f8 = (float) vec3d3.x;
        float f9 = (float) vec3d3.y;
        float f10 = (float) vec3d3.z;
        method431(drawMode, vertexFormat, supplier, arg_0 -> method432(class107, f5, f6, f7, f8, f9, f10, f, f2, f3, f4, matrix4f, arg_0));
    }

    private static void method431(final VertexFormat.DrawMode vertexFormat$DrawMode, final VertexFormat vertexFormat, final Supplier<ShaderProgram> shader, final Consumer<BufferBuilder> consumer) {
        final BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        buffer.begin(vertexFormat$DrawMode, vertexFormat);
        consumer.accept(buffer);
        method417();
        RenderSystem.setShader(shader);
        BufferRenderer.drawWithGlobalProgram(buffer.end());
        method418();
    }

    private static void method432(final IBufferedBuilder class107, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8, final float n9, final float n10, final Matrix4f matrix4f, final BufferBuilder bufferBuilder) {
        class107.method373(bufferBuilder, n, n2, n3, n4, n5, n6, n7, n8, n9, n10, matrix4f);
    }

    private static void method433(final BufferBuilder bufferBuilder, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8, final float n9, final float n10, final Matrix4f matrix4f) {
        bufferBuilder.vertex(matrix4f, n, n2, n3).color(n7, n8, n9, n10).next();
        bufferBuilder.vertex(matrix4f, n4, n5, n6).color(n7, n8, n9, n10).next();
    }
}