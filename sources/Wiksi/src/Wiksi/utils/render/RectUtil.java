package src.Wiksi.utils.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Vector3d;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.ArrayList;
import java.util.List;

import static src.Wiksi.utils.client.IMinecraft.*;


@UtilityClass
@SuppressWarnings("unused")
public class RectUtil implements IRenderAccess {
    public final List<Vec2fColored> VERTEXES_COLORED = new ArrayList<>();
    public final List<Vec2f> VERTEXES = new ArrayList<>();
    int[] LEFT_UP = new int[]{-90, 0};
    int[] RIGHT_UP = new int[]{0, 90};
    int[] RIGHT_DOWN = new int[]{90, 180};
    int[] LEFT_DOWN = new int[]{180, 270};
    @Getter
    @AllArgsConstructor
    public class Vec2fColored {
        float x, y;
        int color;
    }
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
    public Vec2fColored getOfVec3f(Vec2f vec2f, int color) {
        return new Vec2fColored(vec2f.getX(), vec2f.getY(), color);
    }
    public void drawVertexesList(MatrixStack matrix, List<Vec2fColored> vec2c, int begin, boolean texture, boolean bloom) {
        setupRenderRect(texture, bloom);
        buffer.begin(begin, texture ? DefaultVertexFormats.POSITION_TEX_COLOR : DefaultVertexFormats.POSITION_COLOR);
        int counter = 0;
        for (final Vec2fColored vec : vec2c) {
            float[] rgba = ColorUtils.getRGBAf(vec.getColor());
            buffer.pos(matrix.getLast().getMatrix(), vec.getX(), vec.getY(), 0);
            if (texture) buffer.tex(counter == 0 || counter == 3 ? 0 : 1, counter == 0 || counter == 1 ? 0 : 1);
            buffer.color(rgba[0], rgba[1], rgba[2], rgba[3]);
            buffer.endVertex();
            counter++;
        }
        tessellator.draw();
        endRenderRect(bloom);
    }    public void drawRect(MatrixStack matrix, float x, float y, float x2, float y2, int color1, int color2, int color3, int color4, boolean bloom, boolean texture) {
        VERTEXES_COLORED.clear();
        VERTEXES_COLORED.add(new Vec2fColored(x, y, color1));
        VERTEXES_COLORED.add(new Vec2fColored(x2, y, color2));
        VERTEXES_COLORED.add(new Vec2fColored(x2, y2, color3));
        VERTEXES_COLORED.add(new Vec2fColored(x, y2, color4));
        drawVertexesList(matrix, VERTEXES_COLORED, GL11.GL_POLYGON, texture, bloom);
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
    public void drawDuadsSegment(MatrixStack matrix, float x, float y, double radius, double expand, int color, int color2, boolean bloom, int[] side) {
        VERTEXES_COLORED.clear();
        int index = 0;
        for (Vec2f vec2f : generateRadiusCircledVertexes(matrix, x, y, radius, radius + expand, side[0], side[1], 9, true)) {
            VERTEXES_COLORED.add(getOfVec3f(vec2f, index % 2 == 1 ? color2 : color));
            ++index;
        }
        drawVertexesList(matrix, VERTEXES_COLORED, GL12.GL_TRIANGLE_STRIP, false, bloom);
    }
    public void drawShadowSegment(MatrixStack matrix, float x, float y, double radius, int color, boolean sageColor, int[] side, boolean bloom) {
        int color2 = sageColor ? 0 : ColorUtils.replAlpha(color, 0);
        drawDuadsSegment(matrix, x, y, 0, radius, color, color2, bloom, side);
    }

    public void drawShadowSegment(MatrixStack matrix, float x, float y, double radiusRound, double radiusShadow, int color, boolean sageColor, int[] side, boolean bloom) {
        int color2 = sageColor ? 0 : ColorUtils.replAlpha(color, 0);
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

    public void drawShadowSegmentsExtract(MatrixStack matrix, float x, float y, float x2, float y2, double radiusStart, double radiusEnd, int color1, int color2, int color3, int color4, boolean sageColor, boolean bloom) {
        drawShadowSegment(matrix, x, y, radiusStart, radiusEnd, color1, sageColor, LEFT_UP, bloom);
        drawShadowSegment(matrix, x2, y, radiusStart, radiusEnd, color2, sageColor, RIGHT_UP, bloom);
        drawShadowSegment(matrix, x2, y2, radiusStart, radiusEnd, color3, sageColor, RIGHT_DOWN, bloom);
        drawShadowSegment(matrix, x, y2, radiusStart, radiusEnd, color4, sageColor, LEFT_DOWN, bloom);
    }
    public void drawLimitersSegments(MatrixStack matrix, float x, float y, float x2, float y2, float radius, float appendOffsets, int color1, int color2, int color3, int color4, boolean sageColor, boolean retainZero, boolean bloom) {
        int c5 = retainZero ? sageColor ? 0 : ColorUtils.replAlpha(color1, 0) : color1;
        int c6 = retainZero ? sageColor ? 0 : ColorUtils.replAlpha(color2, 0) : color2;
        int c7 = retainZero ? sageColor ? 0 : ColorUtils.replAlpha(color3, 0) : color3;
        int c8 = retainZero ? sageColor ? 0 : ColorUtils.replAlpha(color4, 0) : color4;
        //up
        drawRect(matrix, x + appendOffsets, y - radius, x2 - appendOffsets, y, c5, c6, color2, color1, bloom, false);
        //down
        drawRect(matrix, x + appendOffsets, y2, x2 - appendOffsets, y2 + radius, color4, color3, c7, c8, bloom, false);
        //left
        drawRect(matrix, x - radius, y + appendOffsets, x, y2 - appendOffsets, c5, color1, color4, c8, bloom, false);
        //right
        drawRect(matrix, x2, y + appendOffsets, x2 + radius, y2 - appendOffsets, color2, c6, c7, color3, bloom, false);
    }
    public void drawRoundSegments(MatrixStack matrix, float x, float y, float x2, float y2, double radius, int color1, int color2, int color3, int color4, boolean bloom) {
        drawRoundSegment(matrix, x, y, radius, color1, LEFT_UP, bloom);
        drawRoundSegment(matrix, x2, y, radius, color2, RIGHT_UP, bloom);
        drawRoundSegment(matrix, x2, y2, radius, color3, RIGHT_DOWN, bloom);
        drawRoundSegment(matrix, x, y2, radius, color4, LEFT_DOWN, bloom);
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
    public void drawRect(MatrixStack matrix, float x, float y, float x2, float y2, int color, boolean bloom) {
        drawRect(matrix, x, y, x2, y2, color, color, color, color, bloom, false);
    }

    public void drawRect(MatrixStack matrix, float x, float y, float x2, float y2, int color) {
        drawRect(matrix, x, y, x2, y2, color, false);
    }

    public void setupOrientationMatrix(MatrixStack matrix, double x, double y, double z) {
        float partialTicks = mc.getRenderPartialTicks();
        EntityRendererManager rendererManager = mc.getRenderManager();
        final Vector3d renderPos = rendererManager.info.getProjectedView();
        boolean flag = mc.gameSettings.getPointOfView().func_243192_a() || mc.gameSettings.getPointOfView().func_243194_c().func_243193_b();
        matrix.translate(x - renderPos.x, y - renderPos.y, z - renderPos.z);
    }
}