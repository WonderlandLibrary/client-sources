package dev.excellent.impl.util.render;

import dev.excellent.Excellent;
import dev.excellent.api.interfaces.client.IRenderAccess;
import dev.excellent.impl.util.render.color.ColorUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.mojang.blaze3d.matrix.MatrixStack;
import net.mojang.blaze3d.platform.GlStateManager;
import net.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
@SuppressWarnings("unused")
public class RectUtil implements IRenderAccess {

    public void bindTexture(ResourceLocation location) {
        mc.getTextureManager().bindTexture(location);
    }

    public final List<Vec2fColored> VERTEXES_COLORED = new ArrayList<>();
    public final List<Vec2f> VERTEXES = new ArrayList<>();
    int[] LEFT_UP = new int[]{-90, 0};
    int[] RIGHT_UP = new int[]{0, 90};
    int[] RIGHT_DOWN = new int[]{90, 180};
    int[] LEFT_DOWN = new int[]{180, 270};

    public void setupRenderRect(boolean texture, boolean bloom) {
        if (texture) RenderSystem.enableTexture();
        else RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.disableAlphaTest();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.shadeModel(7425);
//        RenderSystem.depthMask(false);
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, bloom ? GlStateManager.DestFactor.ONE_MINUS_CONSTANT_ALPHA : GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderSystem.disableAlphaTest();
        GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_NICEST);
        GL11.glEnable(GL11.GL_POINT_SMOOTH);
    }

    public void endRenderRect(boolean bloom) {
        RenderSystem.enableAlphaTest();
        if (bloom)
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
//        RenderSystem.depthMask(true);
        RenderSystem.shadeModel(7424);
        RenderSystem.enableCull();
        RenderSystem.enableAlphaTest();
        RenderSystem.enableTexture();
//        RenderSystem.enableDepthTest();
        RenderSystem.clearCurrentColor();
    }

    @Getter
    @AllArgsConstructor
    public class Vec2fColored {
        float x, y;
        int color;
    }

    @Getter
    @AllArgsConstructor
    public class Vec2fMatrix {
        MatrixStack matrix;
        float x, y;
        int color;
    }

    @Getter
    @AllArgsConstructor
    public class Vec3fColored {
        MatrixStack matrix;
        float x, y, z;
        int color;
    }

    public Vec2fColored getOfVec3f(Vec2f vec2f, int color) {
        return new Vec2fColored(vec2f.getX(), vec2f.getY(), color);
    }

    public void glColor(int color) {
        float[] rgbaF = ColorUtil.getRGBAf(color);
        RenderSystem.color4f(rgbaF[0], rgbaF[1], rgbaF[2], rgbaF[3]);
    }

    public void drawVertexesList(MatrixStack matrix, List<Vec2fColored> vec2c, int begin, boolean texture, boolean bloom) {
        setupRenderRect(texture, bloom);
        BUFFER.begin(begin, texture ? DefaultVertexFormats.POSITION_TEX_COLOR : DefaultVertexFormats.POSITION_COLOR);
        int counter = 0;
        for (final Vec2fColored vec : vec2c) {
            float[] rgba = ColorUtil.getRGBAf(vec.getColor());
            BUFFER.pos(matrix.getLast().getMatrix(), vec.getX(), vec.getY(), 0);
            if (texture) BUFFER.tex(counter == 0 || counter == 3 ? 0 : 1, counter == 0 || counter == 1 ? 0 : 1);
            BUFFER.color(rgba[0], rgba[1], rgba[2], rgba[3]);
            BUFFER.endVertex();
            counter++;
        }
        TESSELLATOR.draw();
        endRenderRect(bloom);
    }

    public void drawVertexesList2D(List<Vec2fMatrix> vec2c, int begin, boolean texture, boolean bloom) {
        setupRenderRect(texture, bloom);
        BUFFER.begin(begin, texture ? DefaultVertexFormats.POSITION_TEX_COLOR : DefaultVertexFormats.POSITION_COLOR);
        int counter = 0;
        for (final Vec2fMatrix vec : vec2c) {
            float[] rgba = ColorUtil.getRGBAf(vec.getColor());
            BUFFER.pos(vec.getMatrix().getLast().getMatrix(), vec.getX(), vec.getY(), 0);
            if (texture) BUFFER.tex(counter == 0 || counter == 3 ? 0 : 1, counter == 0 || counter == 1 ? 0 : 1);
            BUFFER.color(rgba[0], rgba[1], rgba[2], rgba[3]);
            BUFFER.endVertex();
            counter++;
        }
        TESSELLATOR.draw();
        endRenderRect(bloom);
    }

    public void drawVertexesList3D(List<Vec3fColored> vec3c, boolean texture, boolean bloom, boolean depth) {
        if (texture) RenderSystem.enableTexture();
        else RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.shadeModel(7425);
        if (depth)
            RenderSystem.depthMask(true);
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, bloom ? GlStateManager.DestFactor.ONE_MINUS_CONSTANT_ALPHA : GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderSystem.disableAlphaTest();
        GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_NICEST);
        GL11.glEnable(GL11.GL_POINT_SMOOTH);

        BUFFER.begin(GlStateManager.GL_QUADS, texture ? DefaultVertexFormats.POSITION_TEX_COLOR : DefaultVertexFormats.POSITION_COLOR);
        for (int i = 0; i < vec3c.size(); i++) {
            Vec3fColored vec = vec3c.get(i);
            float[] rgba = ColorUtil.getRGBAf(vec.getColor());

            BUFFER.pos(vec.getMatrix().getLast().getMatrix(), vec.getX(), vec.getY(), vec.getZ());

            if (texture) BUFFER.tex(i % 4 == 2 || i % 4 == 3 ? 0 : 1, i % 4 == 2 || i % 4 == 1 ? 0 : 1);

            BUFFER.color(rgba[0], rgba[1], rgba[2], rgba[3]);
            BUFFER.endVertex();
        }
        TESSELLATOR.draw();

        RenderSystem.enableAlphaTest();
        if (bloom)
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        if (depth)
            RenderSystem.depthMask(false);
        RenderSystem.shadeModel(7424);
        RenderSystem.enableCull();
        RenderSystem.enableTexture();
        RenderSystem.enableDepthTest();
        RenderSystem.clearCurrentColor();
    }

    public void drawHeadSide(MatrixStack matrixStack, ResourceLocation texture, double x, double y, double z, double width, double height, HeadSide head, boolean depth) {
        matrixStack.push();
        mc.getTextureManager().bindTexture(texture);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.shadeModel(7425);
        RenderSystem.enableAlphaTest();
        if (depth)
            RenderSystem.depthMask(true);
        GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_NICEST);
        GL11.glEnable(GL11.GL_POINT_SMOOTH);

        float[] uv = calculateUV(head.x1, head.y1, head.x2, head.y2, head.size, head.size);

        Matrix4f matrix = matrixStack.getLast().getMatrix();

        BUFFER.begin(GlStateManager.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);

        BUFFER.pos(matrix, (float) x, (float) (y + height), (float) z).tex(uv[0], uv[1]).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        BUFFER.pos(matrix, (float) (x + width), (float) (y + height), (float) z).tex(uv[2], uv[1]).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        BUFFER.pos(matrix, (float) (x + width), (float) y, (float) z).tex(uv[2], uv[3]).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        BUFFER.pos(matrix, (float) x, (float) y, (float) z).tex(uv[0], uv[3]).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();

        TESSELLATOR.draw();

        if (depth)
            RenderSystem.depthMask(false);
        RenderSystem.shadeModel(7424);
        RenderSystem.enableCull();
        RenderSystem.enableDepthTest();
        RenderSystem.clearCurrentColor();

        matrixStack.pop();
    }

    private static float[] calculateUV(int x1, int y1, int x2, int y2, int texWidth, int texHeight) {
        float u1 = (float) x1 / texWidth;
        float v1 = (float) y1 / texHeight;
        float u2 = (float) x2 / texWidth;
        float v2 = (float) y2 / texHeight;

        return new float[]{u1, v1, u2, v2};
    }

    @RequiredArgsConstructor
    public enum HeadSide {
        FRONT(8, 8, 16, 16),
        BACK(24, 8, 32, 16),
        RIGHT(0, 8, 8, 16),
        LEFT(16, 8, 24, 16),
        TOP(8, 0, 16, 8),
        BOTTOM(16, 0, 24, 8),
        ////////////////////////////////
        O_FRONT(40, 8, 48, 16),
        O_BACK(56, 8, 64, 16),
        O_RIGHT(32, 8, 40, 16),
        O_LEFT(48, 8, 56, 16),
        O_TOP(40, 0, 48, 8),
        O_BOTTOM(48, 0, 56, 8),
        ;

        private final int x1, y1, x2, y2;
        private final int size = 64;
    }


    public void drawImage(MatrixStack matrix, ResourceLocation location, float x, float y, float width, float height) {
        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.color4f(1F, 1F, 1F, 1);
        RenderSystem.blendFunc(770, 771);
        bindTexture(location);
        IngameGui.blit(matrix, (int) x, (int) y, (float) 0, (float) 0, (int) width, (int) height, (int) width, (int) height);
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
        RenderSystem.clearCurrentColor();
    }

    public void drawRect(MatrixStack matrix, float x, float y, float x2, float y2, int color1, int color2, int color3, int color4, boolean bloom, boolean texture) {
        VERTEXES_COLORED.clear();
        VERTEXES_COLORED.add(new Vec2fColored(x, y, color1));
        VERTEXES_COLORED.add(new Vec2fColored(x2, y, color2));
        VERTEXES_COLORED.add(new Vec2fColored(x2, y2, color3));
        VERTEXES_COLORED.add(new Vec2fColored(x, y2, color4));
        drawVertexesList(matrix, VERTEXES_COLORED, GL11.GL_POLYGON, texture, bloom);
    }

    public void drawCircle(float x, float y, float start, float end, float radius, float width, boolean filled, int color) {
        float sin;
        float cos;
        float i;
        glColor(-1);
        float endOffset;
        if (start > end) {
            endOffset = end;
            end = start;
            start = endOffset;
        }
        GlStateManager.enableBlend();
        GlStateManager.disableTexture();
        GlStateManager.glBlendFuncSeparate(770, 771, 1, 0);
        glColor(color);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(width);

        BUFFER.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION);
        int numSegments = (int) ((end - start) / 3);

        for (i = 0; i <= numSegments; i++) {
            float angle = (start + i * 3) * (float) Math.PI / 180;
            cos = (float) Math.cos(angle) * radius;
            sin = (float) Math.sin(angle) * radius;
            BUFFER.pos(x + cos, y + sin, 0).endVertex();
        }
        TESSELLATOR.draw();

        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_BLEND);

        BUFFER.begin(filled ? GL11.GL_TRIANGLE_FAN : GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION);
        for (i = 0; i <= numSegments; i++) {
            float angle = (start + i * 3) * (float) Math.PI / 180;
            cos = (float) Math.cos(angle) * radius;
            sin = (float) Math.sin(angle) * radius;
            BUFFER.pos(x + cos, y + sin, 0).endVertex();
        }
        TESSELLATOR.draw();

        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.enableTexture();
        GlStateManager.disableBlend();
    }

    public void drawGradientV(MatrixStack matrix, float x, float y, float x2, float y2, int color, int color2, boolean bloom) {
        drawRect(matrix, x, y, x2, y2, color, color, color2, color2, bloom, false);
    }

    public void drawGradientH(MatrixStack matrix, float x, float y, float x2, float y2, int color, int color2, boolean bloom) {
        drawRect(matrix, x, y, x2, y2, color, color2, color2, color, bloom, false);
    }

    public void drawRect(MatrixStack matrix, float x, float y, float x2, float y2, int color, boolean bloom) {
        drawRect(matrix, x, y, x2, y2, color, color, color, color, bloom, false);
    }

    public void drawRect(MatrixStack matrix, float x, float y, float x2, float y2, int color) {
        drawRect(matrix, x, y, x2, y2, color, false);
    }

    public void drawTexRect(MatrixStack matrix, float x, float y, float x2, float y2, int color1, int color2, int color3, int color4, boolean bloom) {
        drawRect(matrix, x, y, x2, y2, color1, color2, color3, color4, bloom, true);
    }

    public void drawTexRect(MatrixStack matrix, float x, float y, float x2, float y2, int color, boolean bloom) {
        drawRect(matrix, x, y, x2, y2, color, color, color, color, bloom, true);
    }

    public void drawTexRect(MatrixStack matrix, float x, float y, float x2, float y2, int color) {
        drawRect(matrix, x, y, x2, y2, color, color, color, color, false, true);
    }

    public List<Vec2f> generateRadiusCircledVertexes(MatrixStack matrix, float x, float y, double radius1, double radius2, double startRadius, double endRadius, double step, boolean doublepart) {
        VERTEXES.clear();
        double radius = startRadius;
        while (radius <= endRadius) {
            if (radius > endRadius) radius = endRadius;
            float x1 = (float) (Math.sin(Math.toRadians(radius)) * radius1);
            float y1 = (float) (-Math.cos(Math.toRadians(radius)) * radius1);
            VERTEXES.add(new Vec2f(x + x1, y + y1));
            if (doublepart) {
                x1 = (float) (Math.sin(Math.toRadians(radius)) * radius2);
                y1 = (float) (-Math.cos(Math.toRadians(radius)) * radius2);
                VERTEXES.add(new Vec2f(x + x1, y + y1));
            }
            radius += step;
        }
        return VERTEXES;
    }

    public List<Vec2f> generateRadiusCircledVertexes(MatrixStack matrix, float x, float y, double radius, double startRad, double endRad, double step) {
        return generateRadiusCircledVertexes(matrix, x, y, radius, 0, startRad, endRad, step, false);
    }

    public List<Vec2f> generateRadiusCircledVertexesD(MatrixStack matrix, float x, float y, double radius, double c360, double width, double step) {
        return generateRadiusCircledVertexes(matrix, x, y, radius, radius + width, 0, c360, step, true);
    }

    public List<Vec2f> generateRadiusCircledVertexes(MatrixStack matrix, float x, float y, double radius, double c360, double width) {
        return generateRadiusCircledVertexes(matrix, x, y, radius, radius + width, 0, c360, 6, true);
    }

    public void drawDuadsSegment(MatrixStack matrix, float x, float y, double radius, double expand, int color, int color2, boolean bloom, int[] side) {
        VERTEXES_COLORED.clear();
        int index = 0;
        for (Vec2f vec2f : generateRadiusCircledVertexes(matrix, x, y, radius, radius + expand, side[0], side[1], 9, true)) {
            VERTEXES_COLORED.add(getOfVec3f(vec2f, index % 2 == 1 ? color2 : color));
            ++index;
        }
        drawVertexesList(matrix, VERTEXES_COLORED, GL12.GL_TRIANGLE_STRIP, false, bloom);
    }


    public void drawDuadsCircle(MatrixStack matrix, float x, float y, double radius, double c360, float width, int color, boolean bloom) {
        VERTEXES_COLORED.clear();
        int index = 0;
        for (Vec2f vec2f : generateRadiusCircledVertexes(matrix, x, y, radius, 0, 180, 180 + c360, 6, false)) {
            VERTEXES_COLORED.add(getOfVec3f(vec2f, color));
            ++index;
        }
        GL11.glPointSize(width);
        drawVertexesList(matrix, VERTEXES_COLORED, GL11.GL_POINTS, false, bloom);
        GL11.glPointSize(1F);
    }

    public void drawDuadsCircleClientColored(MatrixStack matrix, float x, float y, double radius, double c360, float width, boolean bloom, float alphaPC) {
        VERTEXES_COLORED.clear();
        int index = 0;
        for (Vec2f vec2f : generateRadiusCircledVertexes(matrix, x, y, radius, 0, 180, 180 + c360, 6, false)) {
            VERTEXES_COLORED.add(getOfVec3f(vec2f, Excellent.getInst().getThemeManager().getTheme().getClientColor(index * 6, alphaPC)));
            ++index;
        }
        GL11.glPointSize(width);
        drawVertexesList(matrix, VERTEXES_COLORED, GL11.GL_POINTS, false, bloom);
        GL11.glPointSize(1F);
    }

    public void drawPointsCircle(MatrixStack matrix, float x, float y, double radius, double c360, float width, boolean bloom, int color) {
        c360 = c360 / 360F * 359F;
        VERTEXES_COLORED.clear();
        int index = 0;
        for (Vec2f vec2f : generateRadiusCircledVertexes(matrix, x, y, radius, 0, 0, c360, 6, false)) {
            VERTEXES_COLORED.add(getOfVec3f(vec2f, color));
            ++index;
        }
        GL11.glPointSize(MathHelper.clamp(width, 0.1F, 49.5F));
        drawVertexesList(matrix, VERTEXES_COLORED, GL11.GL_POINTS, false, bloom);
        GL11.glPointSize(1F);
    }

    public void drawPointsCircleClientColored(MatrixStack matrix, float x, float y, double radius, double c360, float width, boolean bloom, float alphaPC) {
        c360 = c360 / 360F * 359F;
        VERTEXES_COLORED.clear();
        int index = 0;
        for (Vec2f vec2f : generateRadiusCircledVertexes(matrix, x, y, radius, 0, 0, c360, 6, false)) {
            VERTEXES_COLORED.add(getOfVec3f(vec2f, Excellent.getInst().getThemeManager().getTheme().getClientColor(index * 6, alphaPC)));
            ++index;
        }
        GL11.glPointSize(MathHelper.clamp(width, 0.1F, 49.5F));
        drawVertexesList(matrix, VERTEXES_COLORED, GL11.GL_POINTS, false, bloom);
        GL11.glPointSize(1F);
    }

    public void drawDuadsCircle(MatrixStack matrix, float x, float y, double radius, double c360, float width, int color) {
        drawDuadsCircle(matrix, x, y, radius, c360, width, color, false);
    }

    public void drawSingleCircle(MatrixStack matrix, float x, float y, float radius, int color, boolean bloom) {
        GL11.glPointSize(radius);
        drawVertexesList(matrix, List.of(new Vec2fColored(x, y, color)), GL11.GL_POINTS, false, bloom);
        GL11.glPointSize(1F);
    }

    public void drawSingleCircle(MatrixStack matrix, float x, float y, float radius, int color) {
        drawSingleCircle(matrix, x, y, radius, color, false);
    }

    public void drawShadowSegment(MatrixStack matrix, float x, float y, double radius, int color, boolean sageColor, int[] side, boolean bloom) {
        int color2 = sageColor ? 0 : ColorUtil.replAlpha(color, 0);
        drawDuadsSegment(matrix, x, y, 0, radius, color, color2, bloom, side);
    }

    public void drawShadowSegment(MatrixStack matrix, float x, float y, double radiusRound, double radiusShadow, int color, boolean sageColor, int[] side, boolean bloom) {
        int color2 = sageColor ? 0 : ColorUtil.replAlpha(color, 0);
        drawDuadsSegment(matrix, x, y, radiusRound, radiusShadow, color, color2, bloom, side);
    }

    public void drawRoundSegment(MatrixStack matrix, float x, float y, double radius, int color, int[] side, boolean bloom) {
        drawDuadsSegment(matrix, x, y, 0, radius, color, color, bloom, side);
    }

    public void drawRoundSegment(MatrixStack matrix, float x, float y, double radius, int color, int[] side) {
        drawDuadsSegment(matrix, x, y, 0, radius, color, color, false, side);
    }

    public void drawShadowSegment(MatrixStack matrix, float x, float y, double radius, int color, boolean sageColor, int[] side) {
        drawShadowSegment(matrix, x, y, radius, color, sageColor, side, false);
    }

    public void drawShadowSegments(MatrixStack matrix, float x, float y, float x2, float y2, double radius, int color1, int color2, int color3, int color4, boolean sageColor, boolean bloom) {
        drawShadowSegment(matrix, x, y, radius, color1, sageColor, LEFT_UP, bloom);
        drawShadowSegment(matrix, x2, y, radius, color2, sageColor, RIGHT_UP, bloom);
        drawShadowSegment(matrix, x2, y2, radius, color3, sageColor, RIGHT_DOWN, bloom);
        drawShadowSegment(matrix, x, y2, radius, color4, sageColor, LEFT_DOWN, bloom);
    }


    public void drawShadowSegmentsExtract(MatrixStack matrix, float x, float y, float x2, float y2, double radiusStart, double radiusEnd, int color1, int color2, int color3, int color4, boolean sageColor, boolean bloom) {
        drawShadowSegment(matrix, x, y, radiusStart, radiusEnd, color1, sageColor, LEFT_UP, bloom);
        drawShadowSegment(matrix, x2, y, radiusStart, radiusEnd, color2, sageColor, RIGHT_UP, bloom);
        drawShadowSegment(matrix, x2, y2, radiusStart, radiusEnd, color3, sageColor, RIGHT_DOWN, bloom);
        drawShadowSegment(matrix, x, y2, radiusStart, radiusEnd, color4, sageColor, LEFT_DOWN, bloom);
    }

    public void drawShadowSegments(MatrixStack matrix, float x, float y, float x2, float y2, double radius, int color1, int color2, int color3, int color4, boolean sageColor) {
        drawShadowSegments(matrix, x, y, x2, y2, radius, color1, color2, color3, color4, sageColor, false);
    }

    public void drawShadowSegmentsExtract(MatrixStack matrix, float x, float y, float x2, float y2, double radiusStart, double radiusEnd, int color1, int color2, int color3, int color4, boolean sageColor) {
        drawShadowSegmentsExtract(matrix, x, y, x2, y2, radiusStart, radiusEnd, color1, color2, color3, color4, sageColor, false);
    }


    public void drawRoundSegments(MatrixStack matrix, float x, float y, float x2, float y2, double radius, int color1, int color2, int color3, int color4, boolean bloom) {
        drawRoundSegment(matrix, x, y, radius, color1, LEFT_UP, bloom);
        drawRoundSegment(matrix, x2, y, radius, color2, RIGHT_UP, bloom);
        drawRoundSegment(matrix, x2, y2, radius, color3, RIGHT_DOWN, bloom);
        drawRoundSegment(matrix, x, y2, radius, color4, LEFT_DOWN, bloom);
    }

    public void drawRoundSegments(MatrixStack matrix, float x, float y, float x2, float y2, double radius, int color1, int color2, int color3, int color4) {
        drawRoundSegments(matrix, x, y, x2, y2, radius, color1, color2, color3, color4, false);
    }

    public void drawRoundSegments(MatrixStack matrix, float x, float y, float x2, float y2, double radius, int color) {
        drawRoundSegments(matrix, x, y, x2, y2, radius, color, color, color, color, false);
    }

    public void drawShadowSegments(MatrixStack matrix, float x, float y, float x2, float y2, double radius, int color1, int color2, int color3, int color4) {
        drawShadowSegments(matrix, x, y, x2, y2, radius, color1, color2, color3, color4, false);
    }

    public void drawShadowSegments(MatrixStack matrix, float x, float y, float x2, float y2, double radius, int color, boolean sageColor) {
        drawShadowSegments(matrix, x, y, x2, y2, radius, color, color, color, color, sageColor);
    }

    public void drawLimitersSegments(MatrixStack matrix, float x, float y, float x2, float y2, float radius, float appendOffsets, int color1, int color2, int color3, int color4, boolean sageColor, boolean retainZero, boolean bloom) {
        int c5 = retainZero ? sageColor ? 0 : ColorUtil.replAlpha(color1, 0) : color1;
        int c6 = retainZero ? sageColor ? 0 : ColorUtil.replAlpha(color2, 0) : color2;
        int c7 = retainZero ? sageColor ? 0 : ColorUtil.replAlpha(color3, 0) : color3;
        int c8 = retainZero ? sageColor ? 0 : ColorUtil.replAlpha(color4, 0) : color4;
        //up
        drawRect(matrix, x + appendOffsets, y - radius, x2 - appendOffsets, y, c5, c6, color2, color1, bloom, false);
        //down
        drawRect(matrix, x + appendOffsets, y2, x2 - appendOffsets, y2 + radius, color4, color3, c7, c8, bloom, false);
        //left
        drawRect(matrix, x - radius, y + appendOffsets, x, y2 - appendOffsets, c5, color1, color4, c8, bloom, false);
        //right
        drawRect(matrix, x2, y + appendOffsets, x2 + radius, y2 - appendOffsets, color2, c6, c7, color3, bloom, false);
    }

    public void drawLimitersSegments(MatrixStack matrix, float x, float y, float x2, float y2, float radius, float appendOffsets, int color1, int color2, int color3, int color4, boolean sageColor, boolean bloom) {
        drawLimitersSegments(matrix, x, y, x2, y2, radius, appendOffsets, color1, color2, color3, color4, false, sageColor, bloom);
    }

    public void drawLimitersSegments(MatrixStack matrix, float x, float y, float x2, float y2, float radius, float appendOffsets, int color1, int color2, int color3, int color4, boolean sageColor) {
        drawLimitersSegments(matrix, x, y, x2, y2, radius, appendOffsets, color1, color2, color3, color4, sageColor, false);
    }

    public void drawLimitersSegments(MatrixStack matrix, float x, float y, float x2, float y2, float radius, float appendOffsets, int color1, int color2, int color3, int color4) {
        drawLimitersSegments(matrix, x, y, x2, y2, radius, appendOffsets, color1, color2, color3, color4, false);
    }

    public void drawLimitersSegments(MatrixStack matrix, float x, float y, float x2, float y2, float radius, float appendOffsets, int color) {
        drawLimitersSegments(matrix, x, y, x2, y2, radius, appendOffsets, color, color, color, color, false);
    }

    private void enableBlendAndSmoothLines(float lineWidth) {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
        GL11.glLineWidth(lineWidth);
    }

    private void disableBlendAndSmoothLines() {
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glLineWidth(1F);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public void drawShadowRect(MatrixStack matrix, float x, float y, float x2, float y2, float radius, int color1, int color2, int color3, int color4, boolean sageColor, boolean bloom, boolean rect, boolean shadow) {
        if (rect) drawRect(matrix, x, y, x2, y2, color1, color2, color3, color4, bloom, false);
        if (!shadow) return;
        drawLimitersSegments(matrix, x, y, x2, y2, radius, 0, color1, color2, color3, color4, sageColor, bloom);
        drawShadowSegments(matrix, x, y, x2, y2, radius, color1, color2, color3, color4, sageColor, bloom);
    }

    public void drawShadowRect(MatrixStack matrix, float x, float y, float x2, float y2, float radius, int color1, int color2, int color3, int color4, boolean sageColor, boolean bloom) {
        drawLimitersSegments(matrix, x, y, x2, y2, radius, 0, color1, color2, color3, color4, sageColor, bloom);
        drawShadowSegments(matrix, x, y, x2, y2, radius, color1, color2, color3, color4, sageColor, bloom);
    }

    public void drawShadowRect(MatrixStack matrix, float x, float y, float x2, float y2, float radius, int color, boolean sageColor, boolean bloom) {
        drawRect(matrix, x, y, x2, y2, color, color, color, color, bloom, false);
        drawLimitersSegments(matrix, x, y, x2, y2, radius, 0, color, color, color, color, sageColor, bloom);
        drawShadowSegments(matrix, x, y, x2, y2, radius, color, color, color, color, sageColor, bloom);
    }

    public void drawShadowRect(MatrixStack matrix, float x, float y, float x2, float y2, float radius, int color1, int color2, int color3, int color4, boolean sageColor) {
        drawShadowRect(matrix, x, y, x2, y2, radius, color1, color2, color3, color4, sageColor, false);
    }

    public void drawShadowRect(MatrixStack matrix, float x, float y, float x2, float y2, float radius, int color) {
        drawShadowRect(matrix, x, y, x2, y2, radius, color, color, color, color, false);
    }

    public void drawShadowRect(MatrixStack matrix, float x, float y, float x2, float y2, float radius, int color, boolean sageColor) {
        drawShadowRect(matrix, x, y, x2, y2, radius, color, color, color, color, sageColor, false);
    }

    public void drawRoundedRectShadowed(MatrixStack matrix, float x, float y, float x2, float y2, float round, float shadowSize, int color1, int color2, int color3, int color4, boolean bloom, boolean sageColor, boolean rect, boolean shadow) {
        float roundMax = Math.max(x2 - x, y2 - y);
        round = Math.max(Math.min(round, roundMax), 0);
        shadowSize = Math.max(shadowSize, 0);

        x += round;
        y += round;
        x2 -= round;
        y2 -= round;
        if (rect) {
            drawRect(matrix, x, y, x2, y2, color1, color2, color3, color4, bloom, false);
            if (round != 0) {
                drawLimitersSegments(matrix, x, y, x2, y2, round, 0, color1, color2, color3, color4, false, false, bloom);
                drawRoundSegments(matrix, x, y, x2, y2, round, color1, color2, color3, color4, bloom);
            }
        }
        if (shadow && shadowSize > 0) {
            drawLimitersSegments(matrix, x - round, y - round, x2 + round, y2 + round, shadowSize, round, color1, color2, color3, color4, sageColor, true, bloom);
            drawShadowSegmentsExtract(matrix, x, y, x2, y2, round, shadowSize, color1, color2, color3, color4, sageColor, bloom);
        }
    }

    public void drawRoundedRect(MatrixStack matrix, float x, float y, float x2, float y2, float radius, int color1, int color2, int color3, int color4, boolean bloom) {
        float roundMax = Math.abs(x2 - x), roundMax2 = Math.abs(y2 - y);
        if (roundMax2 < roundMax) roundMax = roundMax2;
        if (radius > roundMax) radius = roundMax;
        if (radius < 0) radius = 0;
        x += radius;
        y += radius;
        x2 -= radius;
        y2 -= radius;
        drawRect(matrix, x, y, x2, y2, color1, color2, color3, color4, bloom, false);
        if (radius == 0) return;
        drawLimitersSegments(matrix, x, y, x2, y2, radius, 0, color1, color2, color3, color4, true, false, bloom);
        drawRoundSegments(matrix, x, y, x2, y2, radius, color1, color2, color3, color4, bloom);
    }

    public void drawRoundedRect(MatrixStack matrix, float x, float y, float x2, float y2, float radius, int color, boolean bloom) {
        drawRoundedRect(matrix, x, y, x2, y2, radius, color, color, color, color, bloom);
    }

    public void drawRoundedRect(MatrixStack matrix, float x, float y, float x2, float y2, float radius, int color1, int color2, int color3, int color4) {
        drawRoundedRect(matrix, x, y, x2, y2, radius, color1, color2, color3, color4, false);
    }

    public void drawRoundedRect(MatrixStack matrix, float x, float y, float x2, float y2, float radius, int color) {
        drawRoundedRect(matrix, x, y, x2, y2, radius, color, color, color, color, false);
    }

    public void drawTriangle(MatrixStack matrix, float x, float y, float width, float height, int firstColor, int secondColor) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);

        enableBlendAndSmoothLines(1F);
        GL11.glRotatef(180 + 90, 0F, 0F, 1.0F);


        float[] frgba = ColorUtil.getRGBAf(firstColor);
        float[] srgba = ColorUtil.getRGBAf(secondColor);

        // fill
        BUFFER.begin(9, DefaultVertexFormats.POSITION_COLOR);
        BUFFER.pos(x, y - 2, 0).color(frgba[0], frgba[1], frgba[2], frgba[3]).endVertex();
        BUFFER.pos(x + width, y + height, 0).color(frgba[0], frgba[1], frgba[2], frgba[3]).endVertex();
        BUFFER.pos(x + width, y, 0).color(frgba[0], frgba[1], frgba[2], frgba[3]).endVertex();
        BUFFER.pos(x, y - 2, 0).color(frgba[0], frgba[1], frgba[2], frgba[3]).endVertex();
        TESSELLATOR.draw();


        BUFFER.begin(9, DefaultVertexFormats.POSITION_COLOR);
        BUFFER.pos(x + width, y, 0).color(srgba[0], srgba[1], srgba[2], srgba[3]).endVertex();
        BUFFER.pos(x + width, y + height, 0).color(srgba[0], srgba[1], srgba[2], srgba[3]).endVertex();
        BUFFER.pos(x + width * 2, y - 2, 0).color(srgba[0], srgba[1], srgba[2], srgba[3]).endVertex();
        BUFFER.pos(x + width, y, 0).color(srgba[0], srgba[1], srgba[2], srgba[3]).endVertex();
        TESSELLATOR.draw();

        // line
        BUFFER.begin(3, DefaultVertexFormats.POSITION_COLOR);
        BUFFER.pos(x, y - 2, 0).color(frgba[0], frgba[1], frgba[2], frgba[3]).endVertex();
        BUFFER.pos(x + width, y + height, 0).color(frgba[0], frgba[1], frgba[2], frgba[3]).endVertex();
        BUFFER.pos(x + width, y, 0).color(frgba[0], frgba[1], frgba[2], frgba[3]).endVertex();
        BUFFER.pos(x, y - 2, 0).color(frgba[0], frgba[1], frgba[2], frgba[3]).endVertex();
        TESSELLATOR.draw();

        BUFFER.begin(3, DefaultVertexFormats.POSITION_COLOR);
        BUFFER.pos(x + width, y, 0).color(srgba[0], srgba[1], srgba[2], srgba[3]).endVertex();
        BUFFER.pos(x + width, y + height, 0).color(srgba[0], srgba[1], srgba[2], srgba[3]).endVertex();
        BUFFER.pos(x + width * 2, y - 2, 0).color(srgba[0], srgba[1], srgba[2], srgba[3]).endVertex();
        BUFFER.pos(x + width, y, 0).color(srgba[0], srgba[1], srgba[2], srgba[3]).endVertex();
        TESSELLATOR.draw();

        disableBlendAndSmoothLines();
        GL11.glRotatef(-180 - 90, 0F, 0F, 1.0F);
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_BLEND);
    }

    public void drawPolygon(MatrixStack matrix, List<Vec2fColored> vertices, int color, float lineWidth, boolean bloom) {
        BUFFER.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION_COLOR);
        float[] rgba = ColorUtil.getRGBAf(color);
        for (Vec2fColored vertex : vertices) {
            BUFFER.pos(matrix.getLast().getMatrix(), vertex.getX(), vertex.getY(), 0);
            BUFFER.color(rgba[0], rgba[1], rgba[2], rgba[3]);
            BUFFER.endVertex();
        }
        TESSELLATOR.draw();
    }

    public void drawCircle(MatrixStack matrix, float x, float y, float radius, int color, float lineWidth, boolean bloom) {
        BUFFER.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION_COLOR);
        float[] rgba = ColorUtil.getRGBAf(color);
        for (int i = 0; i <= 360; i++) {
            float angle = (float) Math.toRadians(i);
            float xPos = x + radius * (float) Math.cos(angle);
            float yPos = y + radius * (float) Math.sin(angle);
            BUFFER.pos(matrix.getLast().getMatrix(), xPos, yPos, 0);
            BUFFER.color(rgba[0], rgba[1], rgba[2], rgba[3]);
            BUFFER.endVertex();
        }
        TESSELLATOR.draw();
    }

    public void drawLine(MatrixStack matrix, float x1, float y1, float x2, float y2, int color, float lineWidth, boolean bloom) {
        BUFFER.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        float[] rgba = ColorUtil.getRGBAf(color);
        BUFFER.pos(matrix.getLast().getMatrix(), x1, y1, 0);
        BUFFER.color(rgba[0], rgba[1], rgba[2], rgba[3]);
        BUFFER.endVertex();
        BUFFER.pos(matrix.getLast().getMatrix(), x2, y2, 0);
        BUFFER.color(rgba[0], rgba[1], rgba[2], rgba[3]);
        BUFFER.endVertex();
        TESSELLATOR.draw();
    }

    public void setupOrientationMatrix(MatrixStack matrix, float x, float y, float z) {
        setupOrientationMatrix(matrix, (double) x, y, z);
    }

    public void setupOrientationMatrix(MatrixStack matrix, double x, double y, double z) {
        float partialTicks = mc.getRenderPartialTicks();
        EntityRendererManager rendererManager = mc.getRenderManager();
        final Vector3d renderPos = rendererManager.info.getProjectedView();
        boolean flag = mc.gameSettings.getPointOfView().func_243192_a() || mc.gameSettings.getPointOfView().func_243194_c().func_243193_b();
        matrix.translate(x - renderPos.x, y - renderPos.y, z - renderPos.z);
    }

    @Getter
    @AllArgsConstructor
    public class Vec2f {
        public float x, y;
    }
}