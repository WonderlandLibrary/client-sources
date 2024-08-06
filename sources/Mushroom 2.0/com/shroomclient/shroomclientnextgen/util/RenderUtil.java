package com.shroomclient.shroomclientnextgen.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.shroomclient.shroomclientnextgen.ShroomClient;
import com.shroomclient.shroomclientnextgen.modules.impl.client.ClickGUI;
import com.shroomclient.shroomclientnextgen.modules.impl.render.Pointers;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import lombok.Setter;
import me.x150.renderer.font.FontRenderer;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import the_fireplace.ias.gui.AccountListScreen;

public class RenderUtil {

    public static ArrayList growing = new ArrayList();
    public static int fontSize = C.mc.textRenderer.fontHeight;
    public static final FontRenderer Sans_Serif = new FontRenderer(
        new Font[] { new Font(Font.SANS_SERIF, Font.PLAIN, 18) },
        RenderUtil.fontSize
    );
    public static FontRenderer funnyFont = Sans_Serif;
    public static final FontRenderer Serif = new FontRenderer(
        new Font[] { new Font(Font.SERIF, Font.PLAIN, 18) },
        RenderUtil.fontSize
    );
    public static final FontRenderer Monospaced = new FontRenderer(
        new Font[] { new Font(Font.MONOSPACED, Font.PLAIN, 18) },
        RenderUtil.fontSize
    );
    public static final FontRenderer Comic_Sans = new FontRenderer(
        new Font[] { new Font("Comic Sans MS", Font.PLAIN, 18) },
        RenderUtil.fontSize
    );
    // silly font ;3
    public static final FontRenderer Wingdings = new FontRenderer(
        new Font[] { new Font("Wingdings", Font.PLAIN, 18) },
        RenderUtil.fontSize
    );
    public static final FontRenderer Times_New_Roman = new FontRenderer(
        new Font[] { new Font("Times New Roman", Font.PLAIN, 18) },
        RenderUtil.fontSize
    );
    public static Vec3d cameraVector = new Vec3d(0, 0, 0);

    /*
    public static void drawRoundedGlow(float x1, float y1, float width1, float height1, float radius1, float glowWidth, Color color1, int opacity1, boolean left, boolean right, boolean bleft, boolean bright) {
        if (currentContext == null) return;

        Color startColor = new Color(color1.getRed(), color1.getGreen(), color1.getBlue(), opacity1);
        Color endColor = new Color(color1.getRed(), color1.getGreen(), color1.getBlue(), 0);

        int startRed = startColor.getRed();
        int startGreen = startColor.getGreen();
        int startBlue = startColor.getBlue();
        int startOpacity = startColor.getAlpha();

        int redDif = endColor.getRed() - startColor.getRed();
        int greenDif = endColor.getGreen() - startColor.getGreen();
        int blueDif = endColor.getBlue() - startColor.getBlue();
        int opacityDif = endColor.getAlpha() - startColor.getAlpha();

        float redEach = redDif / glowWidth;
        float greenEach = greenDif / glowWidth;
        float blueEach = blueDif / glowWidth;
        float opacityEach = opacityDif / glowWidth;

        MatrixStack matrixStack = currentContext.getMatrices();
        startGL11(matrixStack);

        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();


        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION_COLOR);

        // 0.1 my beloved! (it looks better and isnt TOO fps taxing)
        for (float k = glowWidth; k >= 0; k -= 0.1f) {
            float red = (startRed + (redEach * k)) / 255f;
            float green = (startGreen + (greenEach * k)) / 255f;
            float blue = (startBlue + (blueEach * k)) / 255f;
            float opac = (startOpacity + (opacityEach * k)) / 255f;

            Color fadeColor = new Color(red, green, blue, opac);

            float x = x1 - (k + glowWidth) + glowWidth;
            float y = y1 - (k - glowWidth) - glowWidth;
            float width = width1 + ((k * 2));
            float height = height1 + ((k * 2));
            float radius = radius1 + k;

            float x2 = x + width;
            float y2 = y + height;

            for (int i = 0; i <= 90; i += 3) {
                if (left) {
                    bufferBuilder.vertex(matrix, x, y, 1).color(fadeColor.getRed() / 255f, fadeColor.getGreen() / 255f, fadeColor.getBlue() / 255f, fadeColor.getAlpha() / 255f).next();
                    break;
                }

                bufferBuilder.vertex(matrix, (float) (x + radius + Math.sin(i * 3.141592653589793f / 180.0f) * (radius * -1.0f)), (float) (y + radius + Math.cos(i * 3.141592653589793 / 180.0) * (radius * -1.0)), 0).color(fadeColor.getRed() / 255f, fadeColor.getGreen() / 255f, fadeColor.getBlue() / 255f, fadeColor.getAlpha() / 255f).next();

            }

            for (int i = 90; i <= 180; i += 3) {
                if (bleft) {
                    bufferBuilder.vertex(matrix, x, y2, 1).color(fadeColor.getRed() / 255f, fadeColor.getGreen() / 255f, fadeColor.getBlue() / 255f, fadeColor.getAlpha() / 255f).next();
                    break;
                }

                bufferBuilder.vertex(matrix, (float) (x + radius + Math.sin(i * 3.141592653589793 / 180.0) * (radius * -1.0)), (float) (y2 - radius + Math.cos(i * 3.141592653589793 / 180.0) * (radius * -1.0)), 0).color(fadeColor.getRed() / 255f, fadeColor.getGreen() / 255f, fadeColor.getBlue() / 255f, fadeColor.getAlpha() / 255f).next();

            }

            for (int i = 0; i <= 90; i += 3) {
                if (bright) {
                    bufferBuilder.vertex(matrix, x2, y2, 0).color(fadeColor.getRed() / 255f, fadeColor.getGreen() / 255f, fadeColor.getBlue() / 255f, fadeColor.getAlpha() / 255f).next();
                    break;
                }


                bufferBuilder.vertex(matrix, (float) (x2 - radius + Math.sin(i * 3.141592653589793 / 180.0) * radius), (float) (y2 - radius + Math.cos(i * 3.141592653589793 / 180.0) * radius), 0).color(fadeColor.getRed() / 255f, fadeColor.getGreen() / 255f, fadeColor.getBlue() / 255f, fadeColor.getAlpha() / 255f).next();

            }

            for (int i = 90; i <= 180; i += 3) {
                if (right) {
                    bufferBuilder.vertex(matrix, x2, y, 0).color(fadeColor.getRed() / 255f, fadeColor.getGreen() / 255f, fadeColor.getBlue() / 255f, fadeColor.getAlpha() / 255f).next();
                    break;
                }

                bufferBuilder.vertex(matrix, (float) (x2 - radius + Math.sin(i * 3.141592653589793 / 180.0) * radius), (float) (y + radius + Math.cos(i * 3.141592653589793 / 180.0) * radius), 0).color(fadeColor.getRed() / 255f, fadeColor.getGreen() / 255f, fadeColor.getBlue() / 255f, fadeColor.getAlpha() / 255f).next();
            }


        }

        tessellator.draw();
        endGL11(matrixStack);
    }
     */
    public static double lastCameraY = 0;
    public static double cameraY = 0;

    @Setter
    public static MatrixStack matrix;

    static DrawContext currentContext;

    public static void startGL11(MatrixStack matrixStack) {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        matrixStack.push();
    }

    /*
    public static void drawFade(float x, float y, float w, float h, Color Left, Color Right) {
        if (currentContext == null) return;

        int startRed = Left.getRed();
        int startGreen = Left.getGreen();
        int startBlue = Left.getBlue();
        int startOpacity = Left.getAlpha();

        int redDif = Right.getRed() - Left.getRed();
        int greenDif = Right.getGreen() - Left.getGreen();
        int blueDif = Right.getBlue() - Left.getBlue();
        int opacityDif = Right.getAlpha() - Left.getAlpha();

        float redEach = redDif / w;
        float greenEach = greenDif / w;
        float blueEach = blueDif / w;
        float opacityEach = opacityDif / w;

        MatrixStack matrixStack = currentContext.getMatrices();
        startGL11(matrixStack);
        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        bufferBuilder.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);

        for (float i = 0; i < w; i++) {
            int red = (int) (startRed + (redEach * i));
            int green = (int) (startGreen + (greenEach * i));
            int blue = (int) (startBlue + (blueEach * i));
            int opacity = (int) (startOpacity + (opacityEach * i));

            float tx = x + i;
            float ty = y;
            float tw = Math.min(1, w - i);
            float th = h;
            Color color = new Color(red, green, blue, opacity);

            bufferBuilder.vertex(matrix, tx, ty, 0).color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f).next();
            bufferBuilder.vertex(matrix, tx, ty + th, 0).color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f).next();
            bufferBuilder.vertex(matrix, tx + tw, ty + th, 0).color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f).next();
            bufferBuilder.vertex(matrix, tx + tw, ty, 0).color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f).next();
        }

        tessellator.draw();
        endGL11(matrixStack);
    }
     */

    public static void endGL11(MatrixStack matrixStack) {
        // LOL THIS SAID PUSH AT ONE POINT
        matrixStack.pop();
        RenderSystem.setShaderColor(1, 1, 1, 1);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static void drawTriangle(
        float x,
        float y,
        float w,
        float h,
        Color color
    ) {
        if (currentContext == null) return;
        MatrixStack matrixStack = currentContext.getMatrices();
        startGL11(matrixStack);

        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        RenderSystem.setShader(GameRenderer::getPositionProgram);

        bufferBuilder.begin(
            VertexFormat.DrawMode.QUADS,
            VertexFormats.POSITION
        );
        RenderSystem.setShaderColor(
            color.getRed() / 255f,
            color.getGreen() / 255f,
            color.getBlue() / 255f,
            color.getAlpha() / 255f
        );

        bufferBuilder.vertex(matrix, x, y, 0).next();
        bufferBuilder.vertex(matrix, x + w, y + (h / 2f), 0).next();
        bufferBuilder.vertex(matrix, x, y + h, 0).next();
        bufferBuilder.vertex(matrix, x + w, y + (h / 2f), 0).next();

        tessellator.draw();

        endGL11(matrixStack);
    }

    public static void drawRect(
        float x,
        float y,
        float w,
        float h,
        Color color
    ) {
        if (currentContext == null) return;
        MatrixStack matrixStack = currentContext.getMatrices();
        startGL11(matrixStack);

        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        RenderSystem.setShader(GameRenderer::getPositionProgram);

        bufferBuilder.begin(
            VertexFormat.DrawMode.QUADS,
            VertexFormats.POSITION
        );
        RenderSystem.setShaderColor(
            color.getRed() / 255f,
            color.getGreen() / 255f,
            color.getBlue() / 255f,
            color.getAlpha() / 255f
        );

        bufferBuilder.vertex(matrix, x, y, 0).next();
        bufferBuilder.vertex(matrix, x, y + h, 0).next();
        bufferBuilder.vertex(matrix, x + w, y + h, 0).next();
        bufferBuilder.vertex(matrix, x + w, y, 0).next();
        tessellator.draw();

        endGL11(matrixStack);
    }

    public static void drawLine(
        float x,
        float y,
        float x2,
        float y2,
        Color color
    ) {
        if (currentContext == null) return;
        MatrixStack matrixStack = currentContext.getMatrices();
        startGL11(matrixStack);

        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        RenderSystem.setShader(GameRenderer::getPositionProgram);

        bufferBuilder.begin(
            VertexFormat.DrawMode.DEBUG_LINES,
            VertexFormats.POSITION
        );
        RenderSystem.setShaderColor(
            color.getRed() / 255f,
            color.getGreen() / 255f,
            color.getBlue() / 255f,
            color.getAlpha() / 255f
        );

        bufferBuilder.vertex(matrix, x, y, 0).next();
        bufferBuilder.vertex(matrix, x2, y2, 0).next();
        //bufferBuilder.vertex(matrix, x2+1, y2+1, 0).next();
        //bufferBuilder.vertex(matrix, x+1, y+1, 0).next();
        tessellator.draw();

        endGL11(matrixStack);
    }

    public static void drawCenteredRoundedGlow(
        float x,
        float y,
        float width,
        float height,
        float radius,
        float glowwidth,
        Color color,
        boolean left,
        boolean right,
        boolean bleft,
        boolean bright
    ) {
        x -= (width / 2);
        y -= (height / 2);
        drawRoundedGlow(
            x,
            y,
            width,
            height,
            radius,
            glowwidth,
            color,
            left,
            right,
            bleft,
            bright
        );
    }

    public static void drawCenteredRoundedGlow(
        float x,
        float y,
        float width,
        float height,
        float radius,
        float glowwidth,
        Color color,
        int opacity,
        boolean left,
        boolean right,
        boolean bleft,
        boolean bright
    ) {
        x -= (width / 2);
        y -= (height / 2);
        drawRoundedGlow(
            x,
            y,
            width,
            height,
            radius,
            glowwidth,
            color,
            opacity,
            left,
            right,
            bleft,
            bright
        );
    }

    public static void drawCenteredRoundedRect(
        float x,
        float y,
        float width,
        float height,
        float radius,
        Color color,
        boolean left,
        boolean right,
        boolean bleft,
        boolean bright
    ) {
        x -= (width / 2);
        y -= (height / 2);
        drawRoundedRect2(
            x,
            y,
            width,
            height,
            radius,
            color,
            left,
            right,
            bleft,
            bright
        );
    }

    // rounded rect by scale, used triangle fan instead of debugline :sob: (litterally only change since batman deleted)
    public static void drawRoundedRect2(
        float x,
        float y,
        float width,
        float height,
        float radius,
        Color color,
        boolean left,
        boolean right,
        boolean bleft,
        boolean bright
    ) {
        if (currentContext == null) return;

        radius = Math.min(radius, width / 2);
        radius = Math.min(radius, height / 2);

        MatrixStack matrixStack = currentContext.getMatrices();
        startGL11(matrixStack);

        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        RenderSystem.setShader(GameRenderer::getPositionProgram);

        RenderSystem.setShaderColor(
            color.getRed() / 255f,
            color.getGreen() / 255f,
            color.getBlue() / 255f,
            color.getAlpha() / 255f
        );

        float x2 = x + width;
        float y2 = y + height;

        bufferBuilder.begin(
            VertexFormat.DrawMode.TRIANGLE_FAN,
            VertexFormats.POSITION
        );
        for (int i = 0; i <= 90; i += 3) {
            if (left) {
                bufferBuilder.vertex(matrix, x, y, 0).next();
                break;
            }

            float xN = getRoundedRectPoint(x, radius, i, 1);
            float yN = getRoundedRectPoint(y, radius, i, 2);

            bufferBuilder.vertex(matrix, xN, yN, 0).next();
        }
        for (int i = 90; i <= 180; i += 3) {
            if (bleft) {
                bufferBuilder.vertex(matrix, x, y2, 0).next();
                break;
            }

            float xN = getRoundedRectPoint(x, radius, i, 3);
            float yN = getRoundedRectPoint(y2, radius, i, 4);

            bufferBuilder.vertex(matrix, xN, yN, 0).next();
        }
        for (int i = 0; i <= 90; i += 3) {
            if (bright) {
                bufferBuilder.vertex(matrix, x2, y2, 0).next();
                break;
            }

            float xN = getRoundedRectPoint(x2, radius, i, 5);
            float yN = getRoundedRectPoint(y2, radius, i, 6);

            bufferBuilder.vertex(matrix, xN, yN, 0).next();
        }
        for (int i = 90; i <= 180; i += 3) {
            if (right) {
                bufferBuilder.vertex(matrix, x2, y, 0).next();
                break;
            }

            float xN = getRoundedRectPoint(x2, radius, i, 7);
            float yN = getRoundedRectPoint(y, radius, i, 8);

            bufferBuilder.vertex(matrix, xN, yN, 0).next();
        }

        tessellator.draw();

        endGL11(matrixStack);
    }

    // p cool, i like
    public static void fractionsHollowRoundedRect(
        float x1,
        float y1,
        float width1,
        float height1,
        float radius1,
        float glowWidth,
        Color color1
    ) {
        if (currentContext == null) return;

        MatrixStack matrixStack = currentContext.getMatrices();
        startGL11(matrixStack);

        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        matrixStack.translate(x1, y1, 0);
        x1 = -width1 / 2;
        y1 = -height1 / 2;

        //matrixStack.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees((System.currentTimeMillis() / 10) % 360));

        RenderSystem.setShader(GameRenderer::getPositionProgram);

        RenderSystem.setShaderColor(
            color1.getRed() / 255f,
            color1.getGreen() / 255f,
            color1.getBlue() / 255f,
            color1.getAlpha() / 255f
        );

        float x2 = x1 + width1;
        float y2 = y1 + height1;

        float radius2 = radius1 + glowWidth;

        float x1outer = x1 - glowWidth;
        float y1outer = y1 - glowWidth;

        float x2outer = x2 + glowWidth;
        float y2outer = y2 + glowWidth;

        float xStart = getRoundedRectPoint(x1, radius1, 0, 1);
        float yStart = getRoundedRectPoint(x1, radius1, 0, 2);
        float xEnd = getRoundedRectPoint(x1, radius1, 0, 1);
        float yEnd;

        float xStart2 = getRoundedRectPoint(x1, radius2, 0, 1);
        float yStart2 = getRoundedRectPoint(x1, radius2, 0, 2);
        float xEnd2 = getRoundedRectPoint(x1outer, radius2, 0, 1);
        float yEnd2;

        float disconnectSize = 10;

        bufferBuilder.begin(
            VertexFormat.DrawMode.QUADS,
            VertexFormats.POSITION
        );
        for (int i = 0; i < 90; i += 3) {
            // optimized maths (joke im so sorry) but its a LOT faster than the old
            xStart = getRoundedRectPoint(x1, radius1, i, 1);
            yStart = getRoundedRectPoint(y1, radius1, i, 2);
            xEnd = getRoundedRectPoint(x1, radius1, i + 3, 1);
            yEnd = getRoundedRectPoint(y1, radius1, i + 3, 2);

            xStart2 = getRoundedRectPoint(x1outer, radius2, i, 1);
            yStart2 = getRoundedRectPoint(y1outer, radius2, i, 2);
            xEnd2 = getRoundedRectPoint(x1outer, radius2, i + 3, 1);
            yEnd2 = getRoundedRectPoint(y1outer, radius2, i + 3, 2);

            bufferBuilder.vertex(matrix, xStart, yStart, 0).next();
            bufferBuilder.vertex(matrix, xStart2, yStart2, 0).next();
            bufferBuilder.vertex(matrix, xEnd2, yEnd2, 0).next();
            bufferBuilder.vertex(matrix, xEnd, yEnd, 0).next();
        }

        yEnd = getRoundedRectPoint(y2, radius1, 90, 4);

        bufferBuilder.vertex(matrix, xStart, yStart, 0).next();
        bufferBuilder.vertex(matrix, xStart2, yStart, 0).next();
        bufferBuilder
            .vertex(matrix, xStart2, yStart + disconnectSize, 0)
            .next();
        bufferBuilder.vertex(matrix, xStart, yStart + disconnectSize, 0).next();

        bufferBuilder.vertex(matrix, xStart, yEnd - disconnectSize, 0).next();
        bufferBuilder.vertex(matrix, xStart2, yEnd - disconnectSize, 0).next();
        bufferBuilder.vertex(matrix, xEnd2, yEnd, 0).next();
        bufferBuilder.vertex(matrix, xEnd, yEnd, 0).next();

        for (int i = 90; i < 180; i += 3) {
            xStart = getRoundedRectPoint(x1, radius1, i, 3);
            yStart = getRoundedRectPoint(y2, radius1, i, 4);
            xEnd = getRoundedRectPoint(x1, radius1, i + 3, 3);
            yEnd = getRoundedRectPoint(y2, radius1, i + 3, 4);

            xStart2 = getRoundedRectPoint(x1outer, radius2, i, 3);
            yStart2 = getRoundedRectPoint(y2outer, radius2, i, 4);
            xEnd2 = getRoundedRectPoint(x1outer, radius2, i + 3, 3);
            yEnd2 = getRoundedRectPoint(y2outer, radius2, i + 3, 4);

            bufferBuilder.vertex(matrix, xStart, yStart, 0).next();
            bufferBuilder.vertex(matrix, xStart2, yStart2, 0).next();
            bufferBuilder.vertex(matrix, xEnd2, yEnd2, 0).next();
            bufferBuilder.vertex(matrix, xEnd, yEnd, 0).next();
        }

        xEnd = getRoundedRectPoint(x2, radius1, 0, 5);
        yEnd = getRoundedRectPoint(y2, radius1, 0, 6);
        xEnd2 = getRoundedRectPoint(x2, radius1, 0, 5);
        yEnd2 = getRoundedRectPoint(y2, radius1, 0, 6);

        bufferBuilder.vertex(matrix, xStart, yStart, 0).next();
        bufferBuilder.vertex(matrix, xStart + disconnectSize, yStart, 0).next();
        bufferBuilder
            .vertex(matrix, xStart + disconnectSize, yStart2, 0)
            .next();
        bufferBuilder.vertex(matrix, xStart, yStart2, 0).next();

        bufferBuilder.vertex(matrix, xEnd, yStart, 0).next();
        bufferBuilder.vertex(matrix, xEnd - disconnectSize, yStart, 0).next();
        bufferBuilder.vertex(matrix, xEnd - disconnectSize, yStart2, 0).next();
        bufferBuilder.vertex(matrix, xEnd, yStart2, 0).next();

        for (int i = 0; i < 90; i += 3) {
            xStart = getRoundedRectPoint(x2, radius1, i, 5);
            yStart = getRoundedRectPoint(y2, radius1, i, 6);
            xEnd = getRoundedRectPoint(x2, radius1, i + 3, 5);
            yEnd = getRoundedRectPoint(y2, radius1, i + 3, 6);

            xStart2 = getRoundedRectPoint(x2outer, radius2, i, 5);
            yStart2 = getRoundedRectPoint(y2outer, radius2, i, 6);
            xEnd2 = getRoundedRectPoint(x2outer, radius2, i + 3, 5);
            yEnd2 = getRoundedRectPoint(y2outer, radius2, i + 3, 6);

            bufferBuilder.vertex(matrix, xStart, yStart, 0).next();
            bufferBuilder.vertex(matrix, xStart2, yStart2, 0).next();
            bufferBuilder.vertex(matrix, xEnd2, yEnd2, 0).next();
            bufferBuilder.vertex(matrix, xEnd, yEnd, 0).next();
        }

        xEnd = getRoundedRectPoint(x2, radius1, 90, 7);
        yEnd = getRoundedRectPoint(y1, radius1, 90, 8);

        bufferBuilder.vertex(matrix, xStart, yStart, 0).next();
        bufferBuilder.vertex(matrix, xEnd2, yStart, 0).next();
        bufferBuilder.vertex(matrix, xEnd2, yStart - disconnectSize, 0).next();
        bufferBuilder.vertex(matrix, xStart, yStart - disconnectSize, 0).next();

        bufferBuilder.vertex(matrix, xEnd, yEnd + disconnectSize, 0).next();
        bufferBuilder.vertex(matrix, xEnd2, yEnd + disconnectSize, 0).next();
        bufferBuilder.vertex(matrix, xEnd2, yEnd, 0).next();
        bufferBuilder.vertex(matrix, xEnd, yEnd, 0).next();

        for (int i = 90; i <= 180; i += 3) {
            xStart = getRoundedRectPoint(x2, radius1, i, 7);
            yStart = getRoundedRectPoint(y1, radius1, i, 8);
            xEnd = getRoundedRectPoint(x2, radius1, i + 3, 7);
            yEnd = getRoundedRectPoint(y1, radius1, i + 3, 8);

            xStart2 = getRoundedRectPoint(x2outer, radius2, i, 7);
            yStart2 = getRoundedRectPoint(y1outer, radius2, i, 8);
            xEnd2 = getRoundedRectPoint(x2outer, radius2, i + 3, 7);
            yEnd2 = getRoundedRectPoint(y1outer, radius2, i + 3, 8);

            bufferBuilder.vertex(matrix, xStart, yStart, 0).next();
            bufferBuilder.vertex(matrix, xStart2, yStart2, 0).next();
            bufferBuilder.vertex(matrix, xEnd2, yEnd2, 0).next();
            bufferBuilder.vertex(matrix, xEnd, yEnd, 0).next();
        }

        xEnd = getRoundedRectPoint(x1, radius1, 0, 1);

        bufferBuilder.vertex(matrix, xStart, yStart, 0).next();
        bufferBuilder.vertex(matrix, xStart - disconnectSize, yStart, 0).next();
        bufferBuilder
            .vertex(matrix, xStart - disconnectSize, yStart2, 0)
            .next();
        bufferBuilder.vertex(matrix, xStart, yStart2, 0).next();

        bufferBuilder.vertex(matrix, xEnd + disconnectSize, yStart, 0).next();
        bufferBuilder.vertex(matrix, xEnd, yStart, 0).next();
        bufferBuilder.vertex(matrix, xEnd, yStart2, 0).next();
        bufferBuilder.vertex(matrix, xEnd + disconnectSize, yStart2, 0).next();

        tessellator.draw();

        endGL11(matrixStack);
    }

    public static void drawOutlineRoundedRect2(
        float x,
        float y,
        float width,
        float height,
        float radius,
        Color color,
        boolean left,
        boolean right,
        boolean bleft,
        boolean bright
    ) {
        if (currentContext == null) return;

        MatrixStack matrixStack = currentContext.getMatrices();
        startGL11(matrixStack);

        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        RenderSystem.setShader(GameRenderer::getPositionProgram);

        float x2 = x + width;
        float y2 = y + height;

        bufferBuilder.begin(
            VertexFormat.DrawMode.DEBUG_LINE_STRIP,
            VertexFormats.POSITION
        );

        RenderSystem.setShaderColor(
            color.getRed() / 255f,
            color.getGreen() / 255f,
            color.getBlue() / 255f,
            color.getAlpha() / 255f
        );
        for (int i = 0; i <= 90; i += 3) {
            if (left) {
                bufferBuilder.vertex(matrix, x, y, 1).next();
                break;
            }

            bufferBuilder
                .vertex(
                    matrix,
                    (float) (x +
                        radius +
                        Math.sin((i * 3.141592653589793f) / 180.0f) *
                            (radius * -1.0f)),
                    (float) (y +
                        radius +
                        Math.cos((i * 3.141592653589793) / 180.0) *
                            (radius * -1.0)),
                    0
                )
                .next();
        }

        for (int i = 90; i <= 180; i += 3) {
            if (bleft) {
                bufferBuilder.vertex(matrix, x, y2, 1).next();
                break;
            }

            bufferBuilder
                .vertex(
                    matrix,
                    (float) (x +
                        radius +
                        Math.sin((i * 3.141592653589793) / 180.0) *
                            (radius * -1.0)),
                    (float) (y2 -
                        radius +
                        Math.cos((i * 3.141592653589793) / 180.0) *
                            (radius * -1.0)),
                    0
                )
                .next();
        }

        for (int i = 0; i <= 90; i += 3) {
            if (bright) {
                bufferBuilder.vertex(matrix, x2, y2, 0).next();
                break;
            }

            bufferBuilder
                .vertex(
                    matrix,
                    (float) (x2 -
                        radius +
                        Math.sin((i * 3.141592653589793) / 180.0) * radius),
                    (float) (y2 -
                        radius +
                        Math.cos((i * 3.141592653589793) / 180.0) * radius),
                    0
                )
                .next();
        }

        for (int i = 90; i <= 180; i += 3) {
            if (right) {
                bufferBuilder.vertex(matrix, x2, y, 0).next();
                break;
            }

            bufferBuilder
                .vertex(
                    matrix,
                    (float) (x2 -
                        radius +
                        Math.sin((i * 3.141592653589793) / 180.0) * radius),
                    (float) (y +
                        radius +
                        Math.cos((i * 3.141592653589793) / 180.0) * radius),
                    0
                )
                .next();
        }

        if (left) bufferBuilder.vertex(matrix, x, y, 0).next();
        else bufferBuilder
            .vertex(
                matrix,
                (float) (x +
                    radius +
                    Math.sin((0 * 3.141592653589793f) / 180.0f) *
                        (radius * -1.0f)),
                (float) (y +
                    radius +
                    Math.cos((0 * 3.141592653589793) / 180.0) *
                        (radius * -1.0)),
                0
            )
            .next();

        tessellator.draw();

        endGL11(matrixStack);
    }

    public static void drawRoundedGlow(
        float x,
        float y,
        float width,
        float height,
        float radius,
        float glowWidth,
        Color color,
        boolean left,
        boolean right,
        boolean bleft,
        boolean bright
    ) {
        drawRoundedGlow(
            x,
            y,
            width,
            height,
            radius,
            glowWidth,
            color,
            200,
            left,
            right,
            bleft,
            bright
        );
    }

    public static void drawRoundedGlow(
        float x1,
        float y1,
        float width1,
        float height1,
        float radius1,
        float glowWidth,
        Color color1,
        int opacity1,
        boolean left,
        boolean right,
        boolean bleft,
        boolean bright
    ) {
        if (currentContext == null) return;

        MatrixStack matrixStack = currentContext.getMatrices();
        startGL11(matrixStack);

        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        bufferBuilder.begin(
            VertexFormat.DrawMode.QUADS,
            VertexFormats.POSITION_COLOR
        );

        float x2 = x1 + width1;
        float y2 = y1 + height1;

        float radius2 = radius1 + glowWidth;

        float x1outer = x1 - glowWidth;
        float y1outer = y1 - glowWidth;

        float x2outer = x2 + glowWidth;
        float y2outer = y2 + glowWidth;

        // this feels suboptimal...
        // LEAST CONFUSING VARIABLE NAMES SO REAL IM QUITTING
        float xStart1 = 0;
        float yStart1 = 0;
        float xEnd1 = 0;
        float yEnd1 = 0;
        float xStart21 = 0;
        float yStart21 = 0;
        float xEnd21 = 0;
        float yEnd21 = 0;

        float xStart2 = 0;
        float yStart2 = 0;
        float xEnd2 = 0;
        float yEnd2 = 0;
        float xStart22 = 0;
        float yStart22 = 0;
        float xEnd22 = 0;
        float yEnd22 = 0;

        float xStart3 = 0;
        float yStart3 = 0;
        float xEnd3 = 0;
        float yEnd3 = 0;
        float xStart23 = 0;
        float yStart23 = 0;
        float xEnd23 = 0;
        float yEnd23 = 0;

        float xStart4 = 0;
        float yStart4 = 0;
        float xEnd4 = 0;
        float yEnd4 = 0;
        float xStart24 = 0;
        float yStart24 = 0;
        float xEnd24 = 0;
        float yEnd24 = 0;

        color1 = new Color(
            color1.getRed(),
            color1.getGreen(),
            color1.getBlue(),
            opacity1
        );
        Color color2 = new Color(
            color1.getRed(),
            color1.getGreen(),
            color1.getBlue(),
            0
        );

        for (int i = 0; i < 90; i += 3) {
            if (left) {
                bufferBuilder.vertex(matrix, x1, y1, 0).next();
                break;
            }

            // optimized maths (joke im so sorry) but its a LOT faster than the old
            xStart1 = getRoundedRectPoint(x1, radius1, i, 1);
            yStart1 = getRoundedRectPoint(y1, radius1, i, 2);
            xEnd1 = getRoundedRectPoint(x1, radius1, i + 3, 1);
            yEnd1 = getRoundedRectPoint(y1, radius1, i + 3, 2);

            xStart21 = getRoundedRectPoint(x1outer, radius2, i, 1);
            yStart21 = getRoundedRectPoint(y1outer, radius2, i, 2);
            xEnd21 = getRoundedRectPoint(x1outer, radius2, i + 3, 1);
            yEnd21 = getRoundedRectPoint(y1outer, radius2, i + 3, 2);

            bufferBuilder
                .vertex(matrix, xStart1, yStart1, 0)
                .color(color1.getRGB())
                .next();
            bufferBuilder
                .vertex(matrix, xStart21, yStart21, 0)
                .color(color2.getRGB())
                .next();
            bufferBuilder
                .vertex(matrix, xEnd21, yEnd21, 0)
                .color(color2.getRGB())
                .next();
            bufferBuilder
                .vertex(matrix, xEnd1, yEnd1, 0)
                .color(color1.getRGB())
                .next();
        }

        for (int i = 90; i < 180; i += 3) {
            xStart2 = getRoundedRectPoint(x1, radius1, i, 3);
            yStart2 = getRoundedRectPoint(y2, radius1, i, 4);
            xEnd2 = getRoundedRectPoint(x1, radius1, i + 3, 3);
            yEnd2 = getRoundedRectPoint(y2, radius1, i + 3, 4);

            xStart22 = getRoundedRectPoint(x1outer, radius2, i, 3);
            yStart22 = getRoundedRectPoint(y2outer, radius2, i, 4);
            xEnd22 = getRoundedRectPoint(x1outer, radius2, i + 3, 3);
            yEnd22 = getRoundedRectPoint(y2outer, radius2, i + 3, 4);

            if (i == 90) {
                bufferBuilder
                    .vertex(matrix, xEnd1, yEnd1, 0)
                    .color(color1.getRGB())
                    .next();
                bufferBuilder
                    .vertex(matrix, xEnd21, yEnd21, 0)
                    .color(color2.getRGB())
                    .next();
                bufferBuilder
                    .vertex(matrix, xStart22, yStart22, 0)
                    .color(color2.getRGB())
                    .next();
                bufferBuilder
                    .vertex(matrix, xStart2, yStart2, 0)
                    .color(color1.getRGB())
                    .next();
            }

            bufferBuilder
                .vertex(matrix, xStart2, yStart2, 0)
                .color(color1.getRGB())
                .next();
            bufferBuilder
                .vertex(matrix, xStart22, yStart22, 0)
                .color(color2.getRGB())
                .next();
            bufferBuilder
                .vertex(matrix, xEnd22, yEnd22, 0)
                .color(color2.getRGB())
                .next();
            bufferBuilder
                .vertex(matrix, xEnd2, yEnd2, 0)
                .color(color1.getRGB())
                .next();
        }

        for (int i = 0; i < 90; i += 3) {
            xStart3 = getRoundedRectPoint(x2, radius1, i, 5);
            yStart3 = getRoundedRectPoint(y2, radius1, i, 6);
            xEnd3 = getRoundedRectPoint(x2, radius1, i + 3, 5);
            yEnd3 = getRoundedRectPoint(y2, radius1, i + 3, 6);

            xStart23 = getRoundedRectPoint(x2outer, radius2, i, 5);
            yStart23 = getRoundedRectPoint(y2outer, radius2, i, 6);
            xEnd23 = getRoundedRectPoint(x2outer, radius2, i + 3, 5);
            yEnd23 = getRoundedRectPoint(y2outer, radius2, i + 3, 6);

            if (i == 0) {
                bufferBuilder
                    .vertex(matrix, xEnd2, yEnd2, 0)
                    .color(color1.getRGB())
                    .next();
                bufferBuilder
                    .vertex(matrix, xEnd22, yEnd22, 0)
                    .color(color2.getRGB())
                    .next();
                bufferBuilder
                    .vertex(matrix, xStart23, yStart23, 0)
                    .color(color2.getRGB())
                    .next();
                bufferBuilder
                    .vertex(matrix, xStart3, yStart3, 0)
                    .color(color1.getRGB())
                    .next();
            }

            bufferBuilder
                .vertex(matrix, xStart3, yStart3, 0)
                .color(color1.getRGB())
                .next();
            bufferBuilder
                .vertex(matrix, xStart23, yStart23, 0)
                .color(color2.getRGB())
                .next();
            bufferBuilder
                .vertex(matrix, xEnd23, yEnd23, 0)
                .color(color2.getRGB())
                .next();
            bufferBuilder
                .vertex(matrix, xEnd3, yEnd3, 0)
                .color(color1.getRGB())
                .next();
        }

        for (int i = 90; i <= 177; i += 3) {
            xStart4 = getRoundedRectPoint(x2, radius1, i, 7);
            yStart4 = getRoundedRectPoint(y1, radius1, i, 8);
            xEnd4 = getRoundedRectPoint(x2, radius1, i + 3, 7);
            yEnd4 = getRoundedRectPoint(y1, radius1, i + 3, 8);

            xStart24 = getRoundedRectPoint(x2outer, radius2, i, 7);
            yStart24 = getRoundedRectPoint(y1outer, radius2, i, 8);
            xEnd24 = getRoundedRectPoint(x2outer, radius2, i + 3, 7);
            yEnd24 = getRoundedRectPoint(y1outer, radius2, i + 3, 8);

            if (i == 90) {
                bufferBuilder
                    .vertex(matrix, xEnd3, yEnd3, 0)
                    .color(color1.getRGB())
                    .next();
                bufferBuilder
                    .vertex(matrix, xEnd23, yEnd23, 0)
                    .color(color2.getRGB())
                    .next();
                bufferBuilder
                    .vertex(matrix, xStart24, yStart24, 0)
                    .color(color2.getRGB())
                    .next();
                bufferBuilder
                    .vertex(matrix, xStart4, yStart4, 0)
                    .color(color1.getRGB())
                    .next();
            }

            bufferBuilder
                .vertex(matrix, xStart4, yStart4, 0)
                .color(color1.getRGB())
                .next();
            bufferBuilder
                .vertex(matrix, xStart24, yStart24, 0)
                .color(color2.getRGB())
                .next();
            bufferBuilder
                .vertex(matrix, xEnd24, yEnd24, 0)
                .color(color2.getRGB())
                .next();
            bufferBuilder
                .vertex(matrix, xEnd4, yEnd4, 0)
                .color(color1.getRGB())
                .next();
        }

        // IMPORTANT
        xStart1 = getRoundedRectPoint(x1, radius1, 0, 1);
        yStart1 = getRoundedRectPoint(y1, radius1, 0, 2);
        xStart21 = getRoundedRectPoint(x1outer, radius2, 0, 1);
        yStart21 = getRoundedRectPoint(y1outer, radius2, 0, 2);

        // draws line from top right to top left
        bufferBuilder
            .vertex(matrix, xEnd4, yEnd4, 0)
            .color(color1.getRGB())
            .next();
        bufferBuilder
            .vertex(matrix, xEnd24, yEnd24, 0)
            .color(color2.getRGB())
            .next();
        bufferBuilder
            .vertex(matrix, xStart21, yStart21, 0)
            .color(color2.getRGB())
            .next();
        bufferBuilder
            .vertex(matrix, xStart1, yStart1, 0)
            .color(color1.getRGB())
            .next();

        tessellator.draw();

        endGL11(matrixStack);
    }

    private record titleScreenBubble(
        int xMulti,
        int yMulti,
        int xOffset,
        int yOffset,
        Color color
    ) {}

    static ArrayList<titleScreenBubble> titleScreenBubbles = new ArrayList<>();

    public static void drawTitleScreen(
        int mouseX,
        int mouseY,
        boolean lmbDown
    ) {
        currentContext
            .getMatrices()
            .scale(ClickGUI.scaleSizeW, ClickGUI.scaleSizeH, 0);

        // background
        RenderUtil.drawRect(0, 0, 960, 496, new Color(23, 23, 23));

        float pulseMaxSize = 10;
        double pulseMS = 500;

        float pulseSize =
            (float) Math.sin(System.currentTimeMillis() / pulseMS) *
                pulseMaxSize +
            pulseMaxSize;

        long systemTimeFloat = System.currentTimeMillis() / 50;

        //titleScreenBubbles.clear();

        while (titleScreenBubbles.size() < 200) {
            double random = Math.random();
            Color color = new Color(
                (int) (random * 50 + 100),
                (int) (random * 100),
                (int) (random * 100)
            );
            titleScreenBubbles.add(
                new titleScreenBubble(
                    (int) (Math.random() * 10),
                    (int) (Math.random() * 10),
                    (int) (Math.random() * 4000),
                    (int) (Math.random() * 4000),
                    color
                )
            );
        }

        for (RenderUtil.titleScreenBubble titleScreenBubble : titleScreenBubbles) {
            RenderUtil.drawRoundedGlow(
                ((titleScreenBubble.xOffset +
                        systemTimeFloat * titleScreenBubble.xMulti) %
                    1500) -
                300,
                ((titleScreenBubble.yOffset +
                        systemTimeFloat * titleScreenBubble.yMulti) %
                    1000) -
                300,
                0,
                0,
                0,
                (float) (200 +
                    (pulseSize * Math.sin(titleScreenBubble.yMulti))),
                titleScreenBubble.color,
                40,
                false,
                false,
                false,
                false
            );
        }

        float iconWidth = 120;
        float iconHeight = 120;

        // epic icon
        RenderUtil.drawRoundedGlow(
            479,
            119,
            2,
            2,
            1,
            iconWidth / 2 + (pulseSize) + 30,
            new Color(218, 36, 69),
            100,
            false,
            false,
            false,
            false
        );
        RenderUtil.drawRoundedGlow(
            479,
            119,
            2,
            2,
            1,
            iconWidth / 2 + (pulseSize) + 10,
            new Color(23, 23, 23),
            false,
            false,
            false,
            false
        );
        RenderUtil.drawTexture(
            "icon.png",
            480 - (iconWidth / 2f),
            120 - (iconHeight / 2f),
            iconWidth,
            iconHeight
        );

        float offsetFromIcon = 40;

        //currentContext.getMatrices().push();
        //currentContext.getMatrices().translate(0,offsetFromIcon,0);

        float mushroomWidth = 250;
        float mushroomHeight = 40;

        float furtherOffset = 10;

        float buttonWidth = 250;
        float buttonHeight = 25;
        float buttonHeightOffset = 15;
        float radius = 4;
        float glowWidth = 5;

        float buttonX = 480 - (buttonWidth / 2f);
        float buttonY =
            (buttonHeight / 2f) + (200) + furtherOffset + offsetFromIcon;

        float textScaleFactor = 2f;

        int buttonsOpac = 150;

        // background
        RenderUtil.drawRoundedRect2(
            buttonX - 20,
            160 + offsetFromIcon,
            buttonWidth + 40,
            buttonHeight * 3 +
            buttonHeightOffset * 3 +
            mushroomHeight +
            furtherOffset +
            10,
            10,
            new Color(23, 23, 23, 100),
            false,
            false,
            false,
            false
        );
        RenderUtil.drawRoundedGlow(
            buttonX - 20,
            160 + offsetFromIcon,
            buttonWidth + 40,
            buttonHeight * 3 +
            buttonHeightOffset * 3 +
            mushroomHeight +
            furtherOffset +
            10,
            10,
            20,
            new Color(23, 23, 23),
            100,
            false,
            false,
            false,
            false
        );

        // mushroom text (temp image)
        RenderUtil.drawRoundedGlow(
            480 - (mushroomWidth / 2f) + 10,
            185 + offsetFromIcon,
            mushroomWidth - 20,
            0,
            0,
            mushroomHeight / 2 + (pulseSize / 4f),
            new Color(218, 36, 69),
            false,
            false,
            false,
            false
        );
        RenderUtil.drawTexture(
            "mushroomtext.png",
            480 - (mushroomWidth / 2f),
            185 - (mushroomHeight / 2f) + offsetFromIcon + 2.5f,
            mushroomWidth,
            mushroomHeight - 5
        );

        //currentContext.getMatrices().push();
        //currentContext.getMatrices().translate(0,furtherOffset,0);

        // singleplayer
        RenderUtil.drawRoundedRect2(
            buttonX,
            buttonY,
            buttonWidth,
            buttonHeight,
            radius,
            new Color(50, 50, 50, 50),
            false,
            false,
            false,
            false
        );
        RenderUtil.drawRoundedGlow(
            buttonX,
            buttonY,
            buttonWidth,
            buttonHeight,
            radius,
            glowWidth,
            new Color(225, 12, 76, 200),
            buttonsOpac,
            false,
            false,
            false,
            false
        );

        if (
            ClickGUIScreen.isHovered(
                mouseX,
                mouseY,
                buttonX * ClickGUI.scaleSizeW,
                buttonY * ClickGUI.scaleSizeH,
                buttonWidth * ClickGUI.scaleSizeW,
                buttonHeight * ClickGUI.scaleSizeH
            )
        ) {
            RenderUtil.drawRoundedGlow(
                buttonX,
                buttonY,
                buttonWidth,
                buttonHeight,
                radius,
                glowWidth,
                new Color(250, 200, 200, 200),
                buttonsOpac,
                false,
                false,
                false,
                false
            );

            if (lmbDown) {
                C.mc.setScreen(new SelectWorldScreen(C.mc.currentScreen));
            }
        }

        currentContext.getMatrices().push();
        currentContext
            .getMatrices()
            .translate(
                480,
                ((buttonY + buttonHeight / 2f) -
                    RenderUtil.getFontHeight("singleplayer") / 2f) -
                2,
                0
            );
        currentContext
            .getMatrices()
            .scale(textScaleFactor, textScaleFactor, textScaleFactor);
        RenderUtil.drawCenteredTextShadow(
            "singleplayer",
            0,
            0,
            new Color(255, 255, 255)
        );
        currentContext.getMatrices().pop();

        // multiplayer
        RenderUtil.drawRoundedRect2(
            buttonX,
            buttonY + (buttonHeight + buttonHeightOffset),
            buttonWidth,
            buttonHeight,
            radius,
            new Color(50, 50, 50, 50),
            false,
            false,
            false,
            false
        );
        RenderUtil.drawRoundedGlow(
            buttonX,
            buttonY + (buttonHeight + buttonHeightOffset),
            buttonWidth,
            buttonHeight,
            radius,
            glowWidth,
            new Color(225, 12, 76, 200),
            buttonsOpac,
            false,
            false,
            false,
            false
        );

        if (
            ClickGUIScreen.isHovered(
                mouseX,
                mouseY,
                buttonX * ClickGUI.scaleSizeW,
                (buttonY + (buttonHeight + buttonHeightOffset)) *
                ClickGUI.scaleSizeH,
                buttonWidth * ClickGUI.scaleSizeW,
                buttonHeight * ClickGUI.scaleSizeH
            )
        ) {
            RenderUtil.drawRoundedGlow(
                buttonX,
                buttonY + (buttonHeight + buttonHeightOffset),
                buttonWidth,
                buttonHeight,
                radius,
                glowWidth,
                new Color(250, 200, 200, 200),
                buttonsOpac,
                false,
                false,
                false,
                false
            );

            if (lmbDown) {
                C.mc.setScreen(new MultiplayerScreen(C.mc.currentScreen));
            }
        }

        currentContext.getMatrices().push();
        currentContext
            .getMatrices()
            .translate(
                480,
                ((buttonY +
                        (buttonHeight + buttonHeightOffset) +
                        buttonHeight / 2f) -
                    RenderUtil.getFontHeight("multiplayer") / 2f) -
                2,
                0
            );
        currentContext
            .getMatrices()
            .scale(textScaleFactor, textScaleFactor, textScaleFactor);
        RenderUtil.drawCenteredTextShadow(
            "multiplayer",
            0,
            0,
            new Color(255, 255, 255)
        );
        currentContext.getMatrices().pop();

        // options
        RenderUtil.drawRoundedRect2(
            buttonX,
            buttonY + (buttonHeight * 2 + buttonHeightOffset * 2),
            buttonWidth / 2 - 10,
            buttonHeight,
            radius,
            new Color(50, 50, 50, 50),
            false,
            false,
            false,
            false
        );
        RenderUtil.drawRoundedGlow(
            buttonX,
            buttonY + (buttonHeight * 2 + buttonHeightOffset * 2),
            buttonWidth / 2 - 10,
            buttonHeight,
            radius,
            glowWidth,
            new Color(225, 12, 76, 200),
            buttonsOpac,
            false,
            false,
            false,
            false
        );

        if (
            ClickGUIScreen.isHovered(
                mouseX,
                mouseY,
                buttonX * ClickGUI.scaleSizeW,
                (buttonY + (buttonHeight * 2 + buttonHeightOffset * 2)) *
                ClickGUI.scaleSizeH,
                (buttonWidth / 2 - 10) * ClickGUI.scaleSizeW,
                buttonHeight * ClickGUI.scaleSizeH
            )
        ) {
            RenderUtil.drawRoundedGlow(
                buttonX,
                buttonY + (buttonHeight * 2 + buttonHeightOffset * 2),
                buttonWidth / 2 - 10,
                buttonHeight,
                radius,
                glowWidth,
                new Color(250, 200, 200, 200),
                buttonsOpac,
                false,
                false,
                false,
                false
            );

            if (lmbDown) {
                C.mc.setScreen(
                    new OptionsScreen(C.mc.currentScreen, C.mc.options)
                );
            }
        }

        float newButtonY =
            buttonY +
            (buttonHeight * 2 + buttonHeightOffset * 2) +
            buttonHeight / 2f;

        currentContext.getMatrices().push();

        currentContext
            .getMatrices()
            .translate(
                buttonX + buttonWidth / 4f,
                (newButtonY - RenderUtil.getFontHeight("options") / 2f) - 2,
                0
            );
        currentContext
            .getMatrices()
            .scale(textScaleFactor, textScaleFactor, textScaleFactor);
        RenderUtil.drawCenteredTextShadow(
            "options ",
            0,
            0,
            new Color(255, 255, 255)
        );
        currentContext.getMatrices().pop();

        // alt manager
        RenderUtil.drawRoundedRect2(
            buttonX + buttonWidth / 2 + 10,
            buttonY + (buttonHeight * 2 + buttonHeightOffset * 2),
            buttonWidth / 2 - 10,
            buttonHeight,
            radius,
            new Color(50, 50, 50, 50),
            false,
            false,
            false,
            false
        );
        RenderUtil.drawRoundedGlow(
            buttonX + buttonWidth / 2 + 10,
            buttonY + (buttonHeight * 2 + buttonHeightOffset * 2),
            buttonWidth / 2 - 10,
            buttonHeight,
            radius,
            glowWidth,
            new Color(225, 12, 76, 200),
            buttonsOpac,
            false,
            false,
            false,
            false
        );

        if (
            ClickGUIScreen.isHovered(
                mouseX,
                mouseY,
                (buttonX + buttonWidth / 2 + 10) * ClickGUI.scaleSizeW,
                (buttonY + (buttonHeight * 2 + buttonHeightOffset * 2)) *
                ClickGUI.scaleSizeH,
                (buttonWidth / 2 - 10) * ClickGUI.scaleSizeW,
                buttonHeight * ClickGUI.scaleSizeH
            )
        ) {
            RenderUtil.drawRoundedGlow(
                buttonX + buttonWidth / 2 + 10,
                buttonY + (buttonHeight * 2 + buttonHeightOffset * 2),
                buttonWidth / 2 - 10,
                buttonHeight,
                radius,
                glowWidth,
                new Color(250, 200, 200, 200),
                buttonsOpac,
                false,
                false,
                false,
                false
            );

            if (lmbDown) {
                C.mc.setScreen(new AccountListScreen(C.mc.currentScreen));
            }
        }

        currentContext.getMatrices().push();
        currentContext
            .getMatrices()
            .translate(
                (buttonX + buttonWidth - buttonWidth / 4d),
                (newButtonY - RenderUtil.getFontHeight("alts") / 2f) - 2,
                0
            );
        currentContext
            .getMatrices()
            .scale(textScaleFactor, textScaleFactor, textScaleFactor);
        RenderUtil.drawCenteredTextShadow(
            " alts",
            0,
            0,
            new Color(255, 255, 255)
        );
        currentContext.getMatrices().pop();

        // un translate the y
        //currentContext.getMatrices().pop();
        //currentContext.getMatrices().pop();

        String authorText = "Made By 112batman, scoliosissy, and swig4";
        String mushroomVersion = "Mushroom " + ShroomClient.version;

        RenderUtil.drawTextShadow(
            authorText,
            (int) (960 - RenderUtil.getFontWidth(authorText) - 5),
            496 - 10,
            new Color(255, 255, 255)
        );
        RenderUtil.drawTextShadow(
            mushroomVersion,
            2,
            496 - 10,
            new Color(255, 255, 255)
        );
    }

    public static float getRoundedRectPoint(
        float point,
        float radius,
        float Int,
        int corner
    ) {
        double cosInt = Math.cos((Int * 3.141592653589793) / 180f);
        double sinInt = Math.sin((Int * 3.141592653589793f) / 180f);

        // now done clockwise!
        switch (corner) {
            // x and y of top left
            case 1:
                return (float) (point + radius + sinInt * (radius * -1.0f));
            case 2:
                return (float) (point + radius + cosInt * (radius * -1.0));
            // x and y of bottom left
            case 3:
                return (float) (point + radius + sinInt * (radius * -1.0));
            case 4:
                return (float) (point - radius + cosInt * (radius * -1.0));
            // x and y of bottom right
            case 5:
                return (float) (point - radius + sinInt * radius);
            case 6:
                return (float) (point - radius + cosInt * radius);
            // x and y of top right
            case 7:
                return (float) (point - radius + sinInt * radius);
            case 8:
                return (float) (point + radius + cosInt * radius);
        }

        System.out.println("INVALID CORNER");
        return 0;
    }

    public static float growEffect(
        String growName,
        int growSize,
        float growTime
    ) {
        if (!growing.contains(growName)) {
            growing.add(growName);
            growing.add(System.currentTimeMillis());
        }

        for (int i = 0; i < growing.size(); i += 2) {
            if (growing.get(i).equals(growName)) {
                long growStartTime = (long) growing.get(i + 1);

                float grownAmount =
                    ((System.currentTimeMillis() - growStartTime) / growTime);

                return Math.min((grownAmount) * growSize, growSize);
            }
        }

        return 0;
    }

    public static void resetGrow(String growName) {
        if (growing.contains(growName)) {
            int index = growing.indexOf(growName);
            growing.remove(index);
            growing.remove(index);
        }
    }

    public static void drawFade(
        float x,
        float y,
        float w,
        float h,
        Color Left,
        Color Right
    ) {
        if (currentContext == null) return;

        MatrixStack matrixStack = currentContext.getMatrices();
        startGL11(matrixStack);
        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        bufferBuilder.begin(
            VertexFormat.DrawMode.QUADS,
            VertexFormats.POSITION_COLOR
        );

        bufferBuilder
            .vertex(matrix, x, y, 0)
            .color(
                Left.getRed(),
                Left.getGreen(),
                Left.getBlue(),
                Left.getAlpha()
            )
            .next();
        bufferBuilder
            .vertex(matrix, x, y + h, 0)
            .color(
                Left.getRed(),
                Left.getGreen(),
                Left.getBlue(),
                Left.getAlpha()
            )
            .next();
        bufferBuilder
            .vertex(matrix, x + w, y + h, 0)
            .color(
                Right.getRed(),
                Right.getGreen(),
                Right.getBlue(),
                Right.getAlpha()
            )
            .next();
        bufferBuilder
            .vertex(matrix, x + w, y, 0)
            .color(
                Right.getRed(),
                Right.getGreen(),
                Right.getBlue(),
                Right.getAlpha()
            )
            .next();

        tessellator.draw();
        endGL11(matrixStack);
    }

    public static void drawRectCulled(
        float x,
        float y,
        float w,
        float h,
        Color color,
        float minY,
        float maxY
    ) {
        if (currentContext == null) return;

        maxY += minY;
        if (y >= maxY) return;
        if (y + h <= minY) return;
        if (y < maxY && y + h > maxY) h = maxY - y;
        if (y < minY && y + h > minY) {
            h -= minY - y;
            y = minY;
        }

        MatrixStack matrixStack = currentContext.getMatrices();
        startGL11(matrixStack);

        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        RenderSystem.setShader(GameRenderer::getPositionProgram);

        bufferBuilder.begin(
            VertexFormat.DrawMode.TRIANGLE_FAN,
            VertexFormats.POSITION
        );
        RenderSystem.setShaderColor(
            color.getRed() / 255f,
            color.getGreen() / 255f,
            color.getBlue() / 255f,
            color.getAlpha() / 255f
        );

        bufferBuilder.vertex(matrix, x, y, 0).next();
        bufferBuilder.vertex(matrix, x, y + h, 0).next();
        bufferBuilder.vertex(matrix, x + w, y + h, 0).next();
        bufferBuilder.vertex(matrix, x + w, y, 0).next();
        tessellator.draw();

        endGL11(matrixStack);
    }

    public static void drawCenteredTextShadow(
        TextRenderer textRenderer,
        String text,
        int x,
        int y,
        Color color
    ) {
        if (ClickGUI.fontType == ClickGUI.font.Minecraft) {
            currentContext.drawCenteredTextWithShadow(
                C.mc.textRenderer,
                String.valueOf(text),
                x,
                y,
                color.getRGB()
            );
        } else {
            funnyFont.drawCenteredString(
                currentContext.getMatrices(),
                String.valueOf(text),
                x + 1,
                y + 1,
                0.05f,
                0.05f,
                0.05f,
                0.9f
            );
            funnyFont.drawCenteredString(
                currentContext.getMatrices(),
                String.valueOf(text),
                x,
                y,
                ((float) color.getRed()) / 255f,
                ((float) color.getGreen()) / 255f,
                ((float) color.getBlue()) / 255f,
                ((float) color.getAlpha()) / 255f
            );
        }
    }

    public static void drawTextShadow(Text text, int x, int y, Color color) {
        if (ClickGUI.fontType != ClickGUI.font.Minecraft) {
            drawString(
                text.getString(),
                x + 1,
                (y - (fontSize / 3f)) + 1,
                0.05f,
                0.05f,
                0.05f,
                0.9f
            );
            drawText(text.getString(), x, y, color);
        } else {
            currentContext.drawTextWithShadow(
                C.mc.textRenderer,
                text,
                x,
                y,
                color.getRGB()
            );
        }
    }

    public static void drawCenteredTextShadow(
        Text text,
        int x,
        int y,
        Color color
    ) {
        if (ClickGUI.fontType != ClickGUI.font.Minecraft) y -= fontSize / 3;

        if (ClickGUI.fontType == ClickGUI.font.Minecraft) {
            currentContext.drawCenteredTextWithShadow(
                C.mc.textRenderer,
                String.valueOf(text),
                x,
                y,
                color.getRGB()
            );
        } else {
            funnyFont.drawCenteredString(
                currentContext.getMatrices(),
                String.valueOf(text),
                x + 1,
                y + 1,
                0.05f,
                0.05f,
                0.05f,
                0.9f
            );
            funnyFont.drawCenteredString(
                currentContext.getMatrices(),
                String.valueOf(text),
                x,
                y,
                ((float) color.getRed()) / 255f,
                ((float) color.getGreen()) / 255f,
                ((float) color.getBlue()) / 255f,
                ((float) color.getAlpha()) / 255f
            );
        }
    }

    public static void drawCenteredTextShadow(
        String text,
        int x,
        int y,
        Color color
    ) {
        if (ClickGUI.fontType != ClickGUI.font.Minecraft) y -= fontSize / 3;

        if (ClickGUI.fontType == ClickGUI.font.Minecraft) {
            currentContext.drawCenteredTextWithShadow(
                C.mc.textRenderer,
                text,
                x,
                y,
                color.getRGB()
            );
        } else {
            funnyFont.drawCenteredString(
                currentContext.getMatrices(),
                text,
                x + 1,
                y + 1,
                0.05f,
                0.05f,
                0.05f,
                0.9f
            );
            funnyFont.drawCenteredString(
                currentContext.getMatrices(),
                text,
                x,
                y,
                ((float) color.getRed()) / 255f,
                ((float) color.getGreen()) / 255f,
                ((float) color.getBlue()) / 255f,
                ((float) color.getAlpha()) / 255f
            );
        }
    }

    public static void drawCenteredText(
        String text,
        int x,
        int y,
        Color color
    ) {
        if (ClickGUI.fontType != ClickGUI.font.Minecraft) y -= fontSize / 3;

        if (ClickGUI.fontType == ClickGUI.font.Minecraft) {
            currentContext.drawCenteredTextWithShadow(
                C.mc.textRenderer,
                text,
                (int) (x - (getFontWidth(text) / 2f)),
                y,
                color.getRGB()
            );
        } else {
            funnyFont.drawCenteredString(
                currentContext.getMatrices(),
                text,
                x + 1,
                y + 1,
                0.05f,
                0.05f,
                0.05f,
                0.9f
            );
        }
    }

    public static void drawTextShadow(String text, int x, int y, Color color) {
        drawText(text, x + 1, y + 1, new Color(0.05f, 0.05f, 0.05f, 0.9f));
        drawText(text, x, y, color);
    }

    public static void drawText(
        String text,
        int x,
        int y,
        Color color,
        float size
    ) {
        if (ClickGUI.fontType != ClickGUI.font.Minecraft) y -= fontSize / 3;

        matrix.push();
        matrix.translate(x, y, 0);
        matrix.scale(size, size, 0);
        drawString(
            text,
            0,
            0,
            ((float) color.getRed()) / 255f,
            ((float) color.getGreen()) / 255f,
            ((float) color.getBlue()) / 255f,
            ((float) color.getAlpha()) / 255f
        );
        matrix.pop();
    }

    public static void drawText(String text, int x, int y, Color color) {
        if (ClickGUI.fontType != ClickGUI.font.Minecraft) y -= fontSize / 3;
        drawString(
            text,
            x,
            y,
            ((float) color.getRed()) / 255f,
            ((float) color.getGreen()) / 255f,
            ((float) color.getBlue()) / 255f,
            ((float) color.getAlpha()) / 255f
        );
    }

    public static float getFontHeight(String text) {
        if (ClickGUI.fontType == ClickGUI.font.Minecraft) {
            return C.mc.textRenderer.fontHeight;
        }
        return funnyFont.getStringHeight(text);
    }

    public static float getFontWidth(String text) {
        if (ClickGUI.fontType == ClickGUI.font.Minecraft) {
            return C.mc.textRenderer.getWidth(text);
        }

        return funnyFont.getStringWidth(text);
    }

    public static void drawTextShadowCulled(
        String text,
        int x,
        int y,
        Color color,
        float minY,
        float maxY
    ) {
        if (ClickGUI.fontType != ClickGUI.font.Minecraft) y -= fontSize / 3;
        maxY += minY;
        if (!(y < minY || y + RenderUtil.fontSize > maxY)) {
            drawString(
                text,
                (int) ((x + ClickGUIScreen.oldX) * ClickGUIScreen.scaleSizeWe) +
                1,
                (int) ((y + ClickGUIScreen.oldY) * ClickGUIScreen.scaleSizeHe) +
                1,
                0.05f,
                0.05f,
                0.05f,
                0.9f
            );
            drawString(
                text,
                (int) ((x + ClickGUIScreen.oldX) * ClickGUIScreen.scaleSizeWe),
                (int) ((y + ClickGUIScreen.oldY) * ClickGUIScreen.scaleSizeHe),
                ((float) color.getRed()) / 255f,
                ((float) color.getGreen()) / 255f,
                ((float) color.getBlue()) / 255f,
                ((float) color.getAlpha()) / 255f
            );
        }
    }

    public static void drawString(String s, float x, float y, Color color) {
        if (ClickGUI.fontType == ClickGUI.font.Minecraft) {
            currentContext.drawText(
                C.mc.textRenderer,
                s,
                (int) x,
                (int) y,
                new Color(
                    color.getRed(),
                    color.getGreen(),
                    color.getBlue(),
                    color.getAlpha()
                ).getRGB(),
                false
            );
        } else {
            funnyFont.drawString(
                currentContext.getMatrices(),
                s,
                x,
                y,
                color.getRed(),
                color.getGreen(),
                color.getBlue(),
                color.getAlpha()
            );
        }
    }

    public static void drawString(
        String s,
        float x,
        float y,
        float r,
        float g,
        float b,
        float a
    ) {
        if (ClickGUI.fontType == ClickGUI.font.Minecraft) {
            currentContext.drawText(
                C.mc.textRenderer,
                s,
                (int) x,
                (int) y,
                new Color(r, g, b, a).getRGB(),
                false
            );
        } else {
            funnyFont.drawString(
                currentContext.getMatrices(),
                s,
                x,
                y,
                r,
                g,
                b,
                a
            );
        }
    }

    public static void drawTexture(
        String imageName,
        float x,
        float y,
        float width,
        float height
    ) {
        currentContext.drawTexture(
            new Identifier("shroomclientnextgen", imageName),
            (int) x,
            (int) y,
            (int) width,
            (int) height,
            0.0f,
            0.0f,
            16,
            128,
            16,
            128
        );
    }

    public static void drawTexture(
        Identifier image,
        int x,
        int y,
        int width,
        int height
    ) {
        currentContext.drawTexture(
            image,
            x,
            y,
            width,
            height,
            0.0f,
            0.0f,
            16,
            128,
            16,
            128
        );
    }

    public static void drawTexture(
        String imageName,
        int x,
        int y,
        int width,
        int height
    ) {
        currentContext.drawTexture(
            new Identifier("shroomclientnextgen", imageName),
            x,
            y,
            width,
            height,
            0.0f,
            0.0f,
            16,
            128,
            16,
            128
        );
    }

    public static void drawTexture(
        String imageName,
        int x,
        int y,
        int width,
        int height,
        int u,
        int v
    ) {
        currentContext.drawTexture(
            new Identifier("shroomclientnextgen", imageName),
            x,
            y,
            u,
            v,
            width,
            height
        );
    }

    public static void drawTexture(
        String imageName,
        int x,
        int y,
        int width,
        int height,
        int u,
        int v,
        int regionWidth,
        int regionHeight,
        int textureWidth,
        int textureHeight
    ) {
        currentContext.drawTexture(
            new Identifier("shroomclientnextgen", imageName),
            x,
            y,
            width,
            height,
            u,
            v,
            regionWidth,
            regionHeight,
            textureWidth,
            textureHeight
        );
    }

    public static void drawCenteredTextShadowCulled(
        String text,
        int x,
        int y,
        Color color,
        float minY,
        float maxY
    ) {
        x -= (int) (RenderUtil.getFontWidth(text) / 2);
        drawTextShadowCulled(text, x, y, color, minY, maxY);
    }

    public static void drawFadeCulled(
        float x,
        float y,
        float w,
        float h,
        Color Left,
        Color Right,
        float minY,
        float maxY
    ) {
        if (currentContext == null) return;

        maxY += minY;

        if (y >= maxY) return;
        if (y + h <= minY) return;
        if (y < maxY && y + h > maxY) h = maxY - y;
        if (y < minY && y + h > minY) {
            h -= minY - y;
            y = minY;
        }

        MatrixStack matrixStack = currentContext.getMatrices();
        startGL11(matrixStack);
        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        bufferBuilder.begin(
            VertexFormat.DrawMode.QUADS,
            VertexFormats.POSITION_COLOR
        );

        bufferBuilder
            .vertex(matrix, x, y, 0)
            .color(
                Left.getRed(),
                Left.getGreen(),
                Left.getBlue(),
                Left.getAlpha()
            )
            .next();
        bufferBuilder
            .vertex(matrix, x, y + h, 0)
            .color(
                Left.getRed(),
                Left.getGreen(),
                Left.getBlue(),
                Left.getAlpha()
            )
            .next();
        bufferBuilder
            .vertex(matrix, x + w, y + h, 0)
            .color(
                Right.getRed(),
                Right.getGreen(),
                Right.getBlue(),
                Right.getAlpha()
            )
            .next();
        bufferBuilder
            .vertex(matrix, x + w, y, 0)
            .color(
                Right.getRed(),
                Right.getGreen(),
                Right.getBlue(),
                Right.getAlpha()
            )
            .next();

        tessellator.draw();
        endGL11(matrixStack);
    }

    public static void drawOutlineRoundedFade(
        float x,
        float y,
        float width,
        float height,
        float radius,
        Color Left,
        Color Right,
        boolean left,
        boolean right,
        boolean bleft,
        boolean bright
    ) {
        if (currentContext == null) return;

        int startRed = Left.getRed();
        int startGreen = Left.getGreen();
        int startBlue = Left.getBlue();
        int startOpacity = Left.getAlpha();

        int redDif = Right.getRed() - Left.getRed();
        int greenDif = Right.getGreen() - Left.getGreen();
        int blueDif = Right.getBlue() - Left.getBlue();
        int opacityDif = Right.getAlpha() - Left.getAlpha();

        float redEach = redDif / width;
        float greenEach = greenDif / width;
        float blueEach = blueDif / width;
        float opacityEach = opacityDif / width;

        MatrixStack matrixStack = currentContext.getMatrices();
        startGL11(matrixStack);

        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        RenderSystem.setShader(GameRenderer::getPositionColorProgram);

        float x2 = x + width;
        float y2 = y + height;

        bufferBuilder.begin(
            VertexFormat.DrawMode.DEBUG_LINE_STRIP,
            VertexFormats.POSITION_COLOR
        );
        for (int i = 0; i <= 90; i += 3) {
            float xcor = x2;
            if (!right) xcor = (float) (x2 -
                radius +
                Math.sin((i * 3.141592653589793) / 180.0) * radius);

            int red = Math.max(
                0,
                Math.min(255, (int) (startRed + (redEach * xcor)))
            );
            int green = Math.max(
                0,
                Math.min(255, (int) (startGreen + (greenEach * xcor)))
            );
            int blue = Math.max(
                0,
                Math.min(255, (int) (startBlue + (blueEach * xcor)))
            );
            int opacity = Math.max(
                0,
                Math.min(255, (int) (startOpacity + (opacityEach * xcor)))
            );

            Color color = new Color(red, green, blue, opacity);

            if (left) {
                bufferBuilder
                    .vertex(matrix, x, y, 0)
                    .color(
                        color.getRed() / 255f,
                        color.getGreen() / 255f,
                        color.getBlue() / 255f,
                        color.getAlpha() / 255f
                    )
                    .next();
                break;
            }

            float xN = getRoundedRectPoint(x, radius, i, 1);
            float yN = getRoundedRectPoint(y, radius, i, 2);

            bufferBuilder
                .vertex(matrix, xN, yN, 0)
                .color(
                    color.getRed() / 255f,
                    color.getGreen() / 255f,
                    color.getBlue() / 255f,
                    color.getAlpha() / 255f
                )
                .next();
        }

        for (int i = 90; i <= 180; i += 3) {
            float xcor = x2;
            if (!right) xcor = (float) (x2 -
                radius +
                Math.sin((i * 3.141592653589793) / 180.0) * radius);

            int red = Math.max(
                0,
                Math.min(255, (int) (startRed + (redEach * xcor)))
            );
            int green = Math.max(
                0,
                Math.min(255, (int) (startGreen + (greenEach * xcor)))
            );
            int blue = Math.max(
                0,
                Math.min(255, (int) (startBlue + (blueEach * xcor)))
            );
            int opacity = Math.max(
                0,
                Math.min(255, (int) (startOpacity + (opacityEach * xcor)))
            );

            Color color = new Color(red, green, blue, opacity);

            if (bleft) {
                bufferBuilder
                    .vertex(matrix, x, y2, 0)
                    .color(
                        color.getRed() / 255f,
                        color.getGreen() / 255f,
                        color.getBlue() / 255f,
                        color.getAlpha() / 255f
                    )
                    .next();
                break;
            }

            float xN = getRoundedRectPoint(x, radius, i, 3);
            float yN = getRoundedRectPoint(y2, radius, i, 4);

            bufferBuilder
                .vertex(matrix, xN, yN, 0)
                .color(
                    color.getRed() / 255f,
                    color.getGreen() / 255f,
                    color.getBlue() / 255f,
                    color.getAlpha() / 255f
                )
                .next();
        }
        for (int i = 0; i <= 90; i += 3) {
            float xcor = x2;
            if (!right) xcor = (float) (x2 -
                radius +
                Math.sin((i * 3.141592653589793) / 180.0) * radius);

            int red = Math.max(
                0,
                Math.min(255, (int) (startRed + (redEach * xcor)))
            );
            int green = Math.max(
                0,
                Math.min(255, (int) (startGreen + (greenEach * xcor)))
            );
            int blue = Math.max(
                0,
                Math.min(255, (int) (startBlue + (blueEach * xcor)))
            );
            int opacity = Math.max(
                0,
                Math.min(255, (int) (startOpacity + (opacityEach * xcor)))
            );

            Color color = new Color(red, green, blue, opacity);

            if (bright) {
                bufferBuilder
                    .vertex(matrix, x2, y2, 0)
                    .color(
                        color.getRed() / 255f,
                        color.getGreen() / 255f,
                        color.getBlue() / 255f,
                        color.getAlpha() / 255f
                    )
                    .next();
                break;
            }

            float xN = getRoundedRectPoint(x2, radius, i, 5);
            float yN = getRoundedRectPoint(y2, radius, i, 6);

            bufferBuilder
                .vertex(matrix, xN, yN, 0)
                .color(
                    color.getRed() / 255f,
                    color.getGreen() / 255f,
                    color.getBlue() / 255f,
                    color.getAlpha() / 255f
                )
                .next();
        }
        for (int i = 90; i <= 180; i += 3) {
            float xcor = x2;
            if (!right) xcor = (float) (x2 -
                radius +
                Math.sin((i * 3.141592653589793) / 180.0) * radius);

            int red = Math.max(
                0,
                Math.min(255, (int) (startRed + (redEach * xcor)))
            );
            int green = Math.max(
                0,
                Math.min(255, (int) (startGreen + (greenEach * xcor)))
            );
            int blue = Math.max(
                0,
                Math.min(255, (int) (startBlue + (blueEach * xcor)))
            );
            int opacity = Math.max(
                0,
                Math.min(255, (int) (startOpacity + (opacityEach * xcor)))
            );

            Color color = new Color(red, green, blue, opacity);

            if (right) {
                bufferBuilder
                    .vertex(matrix, x2, y, 0)
                    .color(
                        color.getRed() / 255f,
                        color.getGreen() / 255f,
                        color.getBlue() / 255f,
                        color.getAlpha() / 255f
                    )
                    .next();
                break;
            }

            float xN = getRoundedRectPoint(x2, radius, i, 7);
            float yN = getRoundedRectPoint(y, radius, i, 8);

            bufferBuilder
                .vertex(matrix, xN, yN, 0)
                .color(
                    color.getRed() / 255f,
                    color.getGreen() / 255f,
                    color.getBlue() / 255f,
                    color.getAlpha() / 255f
                )
                .next();
        }

        float xcor = x2;
        if (!right) xcor = (float) (x2 -
            radius +
            Math.sin((0 * 3.141592653589793) / 180.0) * radius);

        int red = Math.max(
            0,
            Math.min(255, (int) (startRed + (redEach * xcor)))
        );
        int green = Math.max(
            0,
            Math.min(255, (int) (startGreen + (greenEach * xcor)))
        );
        int blue = Math.max(
            0,
            Math.min(255, (int) (startBlue + (blueEach * xcor)))
        );
        int opacity = Math.max(
            0,
            Math.min(255, (int) (startOpacity + (opacityEach * xcor)))
        );

        Color color = new Color(red, green, blue, opacity);

        if (left) {
            bufferBuilder
                .vertex(matrix, x, y, 0)
                .color(
                    color.getRed() / 255f,
                    color.getGreen() / 255f,
                    color.getBlue() / 255f,
                    color.getAlpha() / 255f
                )
                .next();
        } else {
            float xN = getRoundedRectPoint(x, radius, 0, 1);
            float yN = getRoundedRectPoint(y, radius, 0, 2);

            bufferBuilder
                .vertex(matrix, xN, yN, 0)
                .color(
                    color.getRed() / 255f,
                    color.getGreen() / 255f,
                    color.getBlue() / 255f,
                    color.getAlpha() / 255f
                )
                .next();
        }

        tessellator.draw();

        endGL11(matrixStack);
    }

    public static void drawRoundedFade(
        float x,
        float y,
        float width,
        float height,
        float radius,
        Color Left,
        Color Right,
        boolean left,
        boolean right,
        boolean bleft,
        boolean bright
    ) {
        if (currentContext == null) return;

        int startRed = Left.getRed();
        int startGreen = Left.getGreen();
        int startBlue = Left.getBlue();
        int startOpacity = Left.getAlpha();

        int redDif = Right.getRed() - Left.getRed();
        int greenDif = Right.getGreen() - Left.getGreen();
        int blueDif = Right.getBlue() - Left.getBlue();
        int opacityDif = Right.getAlpha() - Left.getAlpha();

        float redEach = redDif / width;
        float greenEach = greenDif / width;
        float blueEach = blueDif / width;
        float opacityEach = opacityDif / width;

        radius = Math.min(width, radius);

        MatrixStack matrixStack = currentContext.getMatrices();
        startGL11(matrixStack);
        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        bufferBuilder.begin(
            VertexFormat.DrawMode.TRIANGLE_FAN,
            VertexFormats.POSITION_COLOR
        );

        float x2 = x + width;
        float y2 = y + height;

        float highestX = 0;

        for (int i = 0; i <= 90; i += 3) {
            float xcor = x;
            if (!left) xcor = (float) (x +
                radius +
                Math.sin((i * 3.141592653589793f) / 180.0f) * (radius * -1.0f));

            highestX = xcor;

            int red = Math.max(
                0,
                Math.min(255, (int) (startRed + (redEach * xcor)))
            );
            int green = Math.max(
                0,
                Math.min(255, (int) (startGreen + (greenEach * xcor)))
            );
            int blue = Math.max(
                0,
                Math.min(255, (int) (startBlue + (blueEach * xcor)))
            );
            int opacity = Math.max(
                0,
                Math.min(255, (int) (startOpacity + (opacityEach * xcor)))
            );

            Color color = new Color(red, green, blue, opacity);

            if (left) {
                bufferBuilder
                    .vertex(matrix, xcor, y, 0)
                    .color(
                        color.getRed() / 255f,
                        color.getGreen() / 255f,
                        color.getBlue() / 255f,
                        color.getAlpha() / 255f
                    )
                    .next();
                break;
            }

            bufferBuilder
                .vertex(
                    matrix,
                    xcor,
                    (float) (y +
                        radius +
                        Math.cos((i * 3.141592653589793) / 180.0) *
                            (radius * -1.0)),
                    0
                )
                .color(
                    color.getRed() / 255f,
                    color.getGreen() / 255f,
                    color.getBlue() / 255f,
                    color.getAlpha() / 255f
                )
                .next();
        }

        for (int i = 90; i <= 180; i += 3) {
            float xcor = x;
            if (!bleft) xcor = (float) (x +
                radius +
                Math.sin((i * 3.141592653589793) / 180.0) * (radius * -1.0));

            if (xcor > highestX) highestX = xcor;

            int red = Math.max(
                0,
                Math.min(255, (int) (startRed + (redEach * xcor)))
            );
            int green = Math.max(
                0,
                Math.min(255, (int) (startGreen + (greenEach * xcor)))
            );
            int blue = Math.max(
                0,
                Math.min(255, (int) (startBlue + (blueEach * xcor)))
            );
            int opacity = Math.max(
                0,
                Math.min(255, (int) (startOpacity + (opacityEach * xcor)))
            );

            Color color = new Color(red, green, blue, opacity);

            if (bleft) {
                bufferBuilder
                    .vertex(matrix, xcor, y2, 0)
                    .color(
                        color.getRed() / 255f,
                        color.getGreen() / 255f,
                        color.getBlue() / 255f,
                        color.getAlpha() / 255f
                    )
                    .next();
                break;
            }

            bufferBuilder
                .vertex(
                    matrix,
                    xcor,
                    (float) (y2 -
                        radius +
                        Math.cos((i * 3.141592653589793) / 180.0) *
                            (radius * -1.0)),
                    0
                )
                .color(
                    color.getRed() / 255f,
                    color.getGreen() / 255f,
                    color.getBlue() / 255f,
                    color.getAlpha() / 255f
                )
                .next();
        }

        for (int i = 0; i <= 90; i += 3) {
            float xcor = x2;
            if (!bright) xcor = (float) (x2 -
                radius +
                Math.sin((i * 3.141592653589793) / 180.0) * radius);

            int red = Math.max(
                0,
                Math.min(255, (int) (startRed + (redEach * xcor)))
            );
            int green = Math.max(
                0,
                Math.min(255, (int) (startGreen + (greenEach * xcor)))
            );
            int blue = Math.max(
                0,
                Math.min(255, (int) (startBlue + (blueEach * xcor)))
            );
            int opacity = Math.max(
                0,
                Math.min(255, (int) (startOpacity + (opacityEach * xcor)))
            );

            Color color = new Color(red, green, blue, opacity);

            if (bright) {
                bufferBuilder
                    .vertex(matrix, xcor, y2, 0)
                    .color(
                        color.getRed() / 255f,
                        color.getGreen() / 255f,
                        color.getBlue() / 255f,
                        color.getAlpha() / 255f
                    )
                    .next();
                break;
            }

            bufferBuilder
                .vertex(
                    matrix,
                    xcor,
                    (float) (y2 -
                        radius +
                        Math.cos((i * 3.141592653589793) / 180.0) * radius),
                    0
                )
                .color(
                    color.getRed() / 255f,
                    color.getGreen() / 255f,
                    color.getBlue() / 255f,
                    color.getAlpha() / 255f
                )
                .next();
        }
        for (int i = 90; i <= 180; i += 3) {
            float xcor = x2;
            if (!right) xcor = (float) (x2 -
                radius +
                Math.sin((i * 3.141592653589793) / 180.0) * radius);

            int red = Math.max(
                0,
                Math.min(255, (int) (startRed + (redEach * xcor)))
            );
            int green = Math.max(
                0,
                Math.min(255, (int) (startGreen + (greenEach * xcor)))
            );
            int blue = Math.max(
                0,
                Math.min(255, (int) (startBlue + (blueEach * xcor)))
            );
            int opacity = Math.max(
                0,
                Math.min(255, (int) (startOpacity + (opacityEach * xcor)))
            );

            Color color = new Color(red, green, blue, opacity);

            if (right) {
                bufferBuilder
                    .vertex(matrix, xcor, y, 0)
                    .color(
                        color.getRed() / 255f,
                        color.getGreen() / 255f,
                        color.getBlue() / 255f,
                        color.getAlpha() / 255f
                    )
                    .next();
                break;
            }

            bufferBuilder
                .vertex(
                    matrix,
                    xcor,
                    (float) (y +
                        radius +
                        Math.cos((i * 3.141592653589793) / 180.0) * radius),
                    0
                )
                .color(
                    color.getRed() / 255f,
                    color.getGreen() / 255f,
                    color.getBlue() / 255f,
                    color.getAlpha() / 255f
                )
                .next();
        }

        for (float i = radius; i < width - radius; i++) {
            int red = Math.max(
                0,
                Math.min(255, (int) (startRed + (redEach * (x + i))))
            );
            int green = Math.max(
                0,
                Math.min(255, (int) (startGreen + (greenEach * (x + i))))
            );
            int blue = Math.max(
                0,
                Math.min(255, (int) (startBlue + (blueEach * (x + i))))
            );
            int opacity = Math.max(
                0,
                Math.min(255, (int) (startOpacity + (opacityEach * (x + i))))
            );

            float tx = x + i;
            Color color = new Color(red, green, blue, opacity);

            bufferBuilder
                .vertex(matrix, tx, y, 0)
                .color(
                    color.getRed() / 255f,
                    color.getGreen() / 255f,
                    color.getBlue() / 255f,
                    color.getAlpha() / 255f
                )
                .next();
            bufferBuilder
                .vertex(matrix, tx, y + height, 0)
                .color(
                    color.getRed() / 255f,
                    color.getGreen() / 255f,
                    color.getBlue() / 255f,
                    color.getAlpha() / 255f
                )
                .next();
            bufferBuilder
                .vertex(matrix, tx + 1, y + height, 0)
                .color(
                    color.getRed() / 255f,
                    color.getGreen() / 255f,
                    color.getBlue() / 255f,
                    color.getAlpha() / 255f
                )
                .next();
            bufferBuilder
                .vertex(matrix, tx + 1, y, 0)
                .color(
                    color.getRed() / 255f,
                    color.getGreen() / 255f,
                    color.getBlue() / 255f,
                    color.getAlpha() / 255f
                )
                .next();
        }

        tessellator.draw();
        endGL11(matrixStack);
    }

    public static void drawDownFade(
        int x,
        int y,
        int w,
        int h,
        Color Top,
        Color Bottom
    ) {
        if (currentContext == null) return;

        MatrixStack matrixStack = currentContext.getMatrices();
        startGL11(matrixStack);

        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        RenderSystem.setShader(GameRenderer::getPositionColorProgram);

        bufferBuilder.begin(
            VertexFormat.DrawMode.QUADS,
            VertexFormats.POSITION_COLOR
        );

        bufferBuilder
            .vertex(matrix, x, y, 0)
            .color(Top.getRed(), Top.getGreen(), Top.getBlue(), Top.getAlpha())
            .next();
        bufferBuilder
            .vertex(matrix, x, y + h, 0)
            .color(
                Bottom.getRed(),
                Bottom.getGreen(),
                Bottom.getBlue(),
                Bottom.getAlpha()
            )
            .next();
        bufferBuilder
            .vertex(matrix, x + w, y + h, 0)
            .color(
                Bottom.getRed(),
                Bottom.getGreen(),
                Bottom.getBlue(),
                Bottom.getAlpha()
            )
            .next();
        bufferBuilder
            .vertex(matrix, x + w, y, 0)
            .color(Top.getRed(), Top.getGreen(), Top.getBlue(), Top.getAlpha())
            .next();

        tessellator.draw();
        endGL11(matrixStack);
    }

    public static void drawFourWayFade(
        float x,
        float y,
        float w,
        float h,
        Color tLeft,
        Color tRight,
        Color bLeft,
        Color bRight
    ) {
        if (currentContext == null) return;

        MatrixStack matrixStack = currentContext.getMatrices();
        startGL11(matrixStack);

        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        RenderSystem.setShader(GameRenderer::getPositionColorProgram);

        bufferBuilder.begin(
            VertexFormat.DrawMode.QUADS,
            VertexFormats.POSITION_COLOR
        );

        bufferBuilder
            .vertex(matrix, x, y, 0)
            .color(
                tLeft.getRed(),
                tLeft.getGreen(),
                tLeft.getBlue(),
                tLeft.getAlpha()
            )
            .next();
        bufferBuilder
            .vertex(matrix, x, y + h, 0)
            .color(
                bLeft.getRed(),
                bLeft.getGreen(),
                bLeft.getBlue(),
                bLeft.getAlpha()
            )
            .next();
        bufferBuilder
            .vertex(matrix, x + w, y + h, 0)
            .color(
                bRight.getRed(),
                bRight.getGreen(),
                bRight.getBlue(),
                bRight.getAlpha()
            )
            .next();
        bufferBuilder
            .vertex(matrix, x + w, y, 0)
            .color(
                tRight.getRed(),
                tRight.getGreen(),
                tRight.getBlue(),
                tRight.getAlpha()
            )
            .next();

        tessellator.draw();
        endGL11(matrixStack);
    }

    public static void drawOutlinedBox(
        double x1,
        double y1,
        double z1,
        double x2,
        double y2,
        double z2,
        float partialTicks,
        MatrixStack matrixStack,
        Color color
    ) {
        // doesnt workie for f5 n shit :(
        Vec3d cameraAngle = getCameraVector(partialTicks);

        float BoxX1 = (float) (x1 - cameraAngle.x);
        float BoxX2 = (float) (BoxX1 + x2);

        float BoxY1 = (float) (y1 - cameraAngle.y);
        float BoxY2 = (float) (BoxY1 + y2);

        float BoxZ1 = (float) (z1 - cameraAngle.z);
        float BoxZ2 = (float) (BoxZ1 + z2);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        matrixStack.push();

        RenderSystem.setShaderColor(
            color.getRed() / 255f,
            color.getGreen() / 255f,
            color.getBlue() / 255f,
            color.getAlpha() / 255f
        );

        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionProgram);

        bufferBuilder.begin(
            VertexFormat.DrawMode.DEBUG_LINE_STRIP,
            VertexFormats.POSITION
        );

        // yayy (i spent like 5 minutes doin a diagram to help me join up points, and they dont join up correctly in TRIANGLE_FAN :(

        // BOTTOM
        bufferBuilder.vertex(matrix, BoxX2, BoxY1, BoxZ1).next();
        bufferBuilder.vertex(matrix, BoxX2, BoxY1, BoxZ2).next();
        bufferBuilder.vertex(matrix, BoxX1, BoxY1, BoxZ2).next();
        bufferBuilder.vertex(matrix, BoxX1, BoxY1, BoxZ1).next();

        // SIDES
        bufferBuilder.vertex(matrix, BoxX1, BoxY2, BoxZ1).next();
        bufferBuilder.vertex(matrix, BoxX2, BoxY2, BoxZ1).next();
        bufferBuilder.vertex(matrix, BoxX2, BoxY1, BoxZ1).next();
        bufferBuilder.vertex(matrix, BoxX1, BoxY1, BoxZ1).next();

        bufferBuilder.vertex(matrix, BoxX1, BoxY1, BoxZ2).next();

        bufferBuilder.vertex(matrix, BoxX2, BoxY1, BoxZ2).next();
        bufferBuilder.vertex(matrix, BoxX2, BoxY2, BoxZ2).next();
        bufferBuilder.vertex(matrix, BoxX1, BoxY2, BoxZ2).next();
        bufferBuilder.vertex(matrix, BoxX1, BoxY1, BoxZ2).next();

        bufferBuilder.vertex(matrix, BoxX2, BoxY1, BoxZ2).next();

        bufferBuilder.vertex(matrix, BoxX2, BoxY1, BoxZ1).next();
        bufferBuilder.vertex(matrix, BoxX2, BoxY2, BoxZ1).next();
        bufferBuilder.vertex(matrix, BoxX2, BoxY2, BoxZ2).next();
        bufferBuilder.vertex(matrix, BoxX2, BoxY1, BoxZ2).next();

        bufferBuilder.vertex(matrix, BoxX2, BoxY1, BoxZ1).next();

        bufferBuilder.vertex(matrix, BoxX1, BoxY1, BoxZ1).next();
        bufferBuilder.vertex(matrix, BoxX1, BoxY2, BoxZ1).next();
        bufferBuilder.vertex(matrix, BoxX2, BoxY2, BoxZ1).next();
        bufferBuilder.vertex(matrix, BoxX2, BoxY1, BoxZ1).next();

        // TOP!!
        bufferBuilder.vertex(matrix, BoxX2, BoxY2, BoxZ1).next();
        bufferBuilder.vertex(matrix, BoxX2, BoxY2, BoxZ2).next();
        bufferBuilder.vertex(matrix, BoxX1, BoxY2, BoxZ2).next();
        bufferBuilder.vertex(matrix, BoxX1, BoxY2, BoxZ1).next();

        tessellator.draw();

        matrixStack.pop();

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    public static void drawBox2(
        double x1,
        double y1,
        double z1,
        double x2,
        double y2,
        double z2,
        float partialTicks,
        MatrixStack matrixStack,
        Color color,
        int opacity
    ) {
        drawBox2(
            x1,
            y1,
            z1,
            x2,
            y2,
            z2,
            partialTicks,
            matrixStack,
            new Color(
                color.getRed(),
                color.getGreen(),
                color.getBlue(),
                opacity
            )
        );
    }

    public static Vec3d getCameraVector(float partialTicks) {
        double playX =
            C.p().lastRenderX +
            (C.p().getX() - C.p().lastRenderX) * partialTicks;
        double playY =
            (C.p().lastRenderY +
                (C.p().getY() - C.p().lastRenderY) * partialTicks) +
            (lastCameraY + (cameraY - lastCameraY) * partialTicks);
        double playZ =
            C.p().lastRenderZ +
            (C.p().getZ() - C.p().lastRenderZ) * partialTicks;

        if (!C.mc.options.getPerspective().isFirstPerson()) {
            playX += cameraVector.x;
            playY += cameraVector.y;
            playZ += cameraVector.z;
        }

        return new Vec3d(playX, playY, playZ);
    }

    public static void drawSimsCrystal(
        double x1,
        double y1,
        double z1,
        double w,
        double h,
        float partialTicks,
        MatrixStack matrixStack,
        Color color
    ) {
        Vec3d cameraAngle = getCameraVector(partialTicks);

        float BoxX1 = (float) (x1 - cameraAngle.x);

        float BoxY1 = (float) (y1 - cameraAngle.y);

        float BoxZ1 = (float) (z1 - cameraAngle.z);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        matrixStack.push();

        RenderSystem.setShaderColor(
            color.getRed() / 255f,
            color.getGreen() / 255f,
            color.getBlue() / 255f,
            color.getAlpha() / 255f
        );

        Matrix4f matrix = matrixStack.peek().getPositionMatrix();

        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);

        bufferBuilder.begin(
            VertexFormat.DrawMode.TRIANGLE_FAN,
            VertexFormats.POSITION_COLOR
        );
        int angles = 4;

        matrixStack.translate(BoxX1, BoxY1, BoxZ1);
        matrixStack.multiply(
            RotationAxis.POSITIVE_Y.rotation((MovementUtil.ticks * 0.05f) % 360)
        );

        BoxX1 = 0;
        BoxY1 = 0;
        BoxZ1 = 0;

        bufferBuilder
            .vertex(matrix, BoxX1, (float) (BoxY1 + h), BoxZ1)
            .color(color.getRGB())
            .next();

        for (int i = 0; i <= angles; ++i) {
            bufferBuilder
                .vertex(
                    matrix,
                    (float) (BoxX1 +
                        Math.cos((i * 3.141592653589793f) / (angles / 2.0f)) *
                            w),
                    (float) (BoxY1 + (h / 2f)),
                    (float) (BoxZ1 +
                        Math.sin((i * 3.141592653589793) / (angles / 2.0)) * w)
                )
                .color(color.getRGB())
                .next();
        }

        // i need this so i can make seethrough, otherwise the seethrrough looked stinky :(
        tessellator.draw();

        bufferBuilder.begin(
            VertexFormat.DrawMode.TRIANGLE_FAN,
            VertexFormats.POSITION_COLOR
        );
        bufferBuilder
            .vertex(matrix, BoxX1, BoxY1, BoxZ1)
            .color(color.getRGB())
            .next();

        for (int i = 0; i <= angles; ++i) {
            bufferBuilder
                .vertex(
                    matrix,
                    (float) (BoxX1 +
                        Math.cos((i * 3.141592653589793f) / (angles / 2.0f)) *
                            w),
                    (float) (BoxY1 + (h / 2f)),
                    (float) (BoxZ1 +
                        Math.sin((i * 3.141592653589793) / (angles / 2.0)) * w)
                )
                .color(color.getRGB())
                .next();
        }

        tessellator.draw();

        matrixStack.pop();

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    public static void draw3dCircle(
        double x1,
        double y1,
        double z1,
        double radius,
        float partialTicks,
        MatrixStack matrixStack,
        Color color
    ) {
        Vec3d cameraAngle = getCameraVector(partialTicks);

        float BoxX1 = (float) (x1 - cameraAngle.x);
        float BoxY1 = (float) (y1 - cameraAngle.y);
        float BoxZ1 = (float) (z1 - cameraAngle.z);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        matrixStack.push();

        Matrix4f matrix = matrixStack.peek().getPositionMatrix();

        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);

        bufferBuilder.begin(
            VertexFormat.DrawMode.TRIANGLE_FAN,
            VertexFormats.POSITION_COLOR
        );
        int angles = 45;

        for (int i = 0; i <= angles; ++i) {
            bufferBuilder
                .vertex(
                    matrix,
                    (float) (BoxX1 +
                        Math.cos((i * 3.141592653589793f) / (angles / 2.0f)) *
                            radius),
                    (BoxY1),
                    (float) (BoxZ1 +
                        Math.sin((i * 3.141592653589793) / (angles / 2.0)) *
                            radius)
                )
                .color(color.getRGB())
                .next();
        }

        tessellator.draw();

        matrixStack.pop();

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    // prob uses 359085890359gb of memory, its by far the slowest thing here
    /*
    public static void drawGlowESP(Vec3d pos, double width, double height, float partialTicks, MatrixStack matrixStack, Color color) {
        Vec3d cameraAngle = getCameraVector(partialTicks);

        float BoxX1 = (float) (pos.x - cameraAngle.x);
        float BoxY1 = (float) (pos.y - cameraAngle.y);
        float BoxZ1 = (float) (pos.z - cameraAngle.z);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        matrixStack.push();

        Matrix4f matrix = matrixStack.peek().getPositionMatrix();

        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);

        bufferBuilder.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
        int angles = 45;

        matrixStack.translate(BoxX1, BoxY1 + 1, BoxZ1);
        BoxX1 = 0;
        BoxY1 = 0;
        BoxZ1 = 0;
        matrixStack.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(RotationUtil.yawTo360(C.p().getYaw())));
        matrixStack.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(90));

        for (int i = 0; i <= angles; ++i) {
            bufferBuilder.vertex(matrix, (float) (BoxX1 + Math.cos(i * 3.141592653589793f / (angles / 2.0f)) * width), (BoxY1), (float) (BoxZ1 + Math.sin(i * 3.141592653589793 / (angles / 2.0)) * height)).color(color.getRGB()).next();
        }

        tessellator.draw();

        matrixStack.pop();

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        RenderSystem.setShaderColor(1, 1, 1, 1);
    }
     */

    public static void drawCoolCircleGoBRRRRRRR(
        Entity ent,
        float partialTicks,
        MatrixStack matrixStack,
        Color color
    ) {
        Vec3d cameraAngle = getCameraVector(partialTicks);

        double playX =
            ent.lastRenderX + (ent.getX() - ent.lastRenderX) * partialTicks;
        double playY =
            ent.lastRenderY + (ent.getY() - ent.lastRenderY) * partialTicks;
        double playZ =
            ent.lastRenderZ + (ent.getZ() - ent.lastRenderZ) * partialTicks;

        float BoxX1 = (float) (playX - cameraAngle.x);
        float BoxY1 = (float) (playY - cameraAngle.y);
        float BoxZ1 = (float) (playZ - cameraAngle.z);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        matrixStack.push();

        RenderSystem.setShaderColor(
            color.getRed() / 255f,
            color.getGreen() / 255f,
            color.getBlue() / 255f,
            color.getAlpha() / 255f
        );

        Matrix4f matrix = matrixStack.peek().getPositionMatrix();

        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);

        bufferBuilder.begin(
            VertexFormat.DrawMode.DEBUG_LINE_STRIP,
            VertexFormats.POSITION_COLOR
        );
        matrixStack.translate(BoxX1, BoxY1 + 1, BoxZ1);

        int angles = 120;

        float sizeNumber = 0.5F;

        float location =
            (float) Math.sin(System.currentTimeMillis() * 0.004) *
            0.6F *
            sizeNumber;

        matrixStack.translate(0, ent.getHeight() * location, 0);
        double time = Math.sin(System.currentTimeMillis() * 0.004);

        for (int i = 0; i <= angles; i++) {
            bufferBuilder
                .vertex(
                    matrix,
                    (float) (Math.cos(
                            (i * 3.141592653589793f) / (angles / 2.0f)
                        ) *
                        ent.getWidth()),
                    (float) (sizeNumber * time),
                    (float) (Math.sin(
                            (i * 3.141592653589793) / (angles / 2.0)
                        ) *
                        ent.getWidth())
                )
                .color(color.getRGB())
                .next();
        }

        tessellator.draw();

        RenderSystem.setShader(GameRenderer::getPositionColorProgram);

        bufferBuilder.begin(
            VertexFormat.DrawMode.QUADS,
            VertexFormats.POSITION_COLOR
        );

        matrixStack.translate(BoxX1, BoxY1 + 1, BoxZ1);

        matrixStack.scale(2, 1, 2);
        matrixStack.translate(0, ent.getHeight() * location, 0);
        matrixStack.translate(0, sizeNumber * (time), 0);

        for (int i = 1; i <= angles; i++) {
            bufferBuilder
                .vertex(
                    matrix,
                    (float) (Math.cos(
                            (i * 3.141592653589793f) / (angles / 2.0f)
                        ) *
                        ent.getWidth()),
                    (float) (sizeNumber * time),
                    (float) (Math.sin(
                            (i * 3.141592653589793) / (angles / 2.0)
                        ) *
                        ent.getWidth())
                )
                .color(setOpac(color, 120).getRGB())
                .next();
            bufferBuilder
                .vertex(
                    matrix,
                    (float) (Math.cos(
                            ((i + 1) * 3.141592653589793f) / (angles / 2.0f)
                        ) *
                        ent.getWidth()),
                    (float) (sizeNumber * time),
                    (float) (Math.sin(
                            ((i + 1) * 3.141592653589793) / (angles / 2.0)
                        ) *
                        ent.getWidth())
                )
                .color(setOpac(color, 120).getRGB())
                .next();
            bufferBuilder
                .vertex(
                    matrix,
                    (float) (Math.cos(
                            ((i + 1) * 3.141592653589793f) / (angles / 2.0f)
                        ) *
                        ent.getWidth()),
                    (float) (-sizeNumber * time),
                    (float) (Math.sin(
                            ((i + 1) * 3.141592653589793) / (angles / 2.0)
                        ) *
                        ent.getWidth())
                )
                .color(setOpac(color, 0).getRGB())
                .next();
            bufferBuilder
                .vertex(
                    matrix,
                    (float) (Math.cos(
                            (i * 3.141592653589793f) / (angles / 2.0f)
                        ) *
                        ent.getWidth()),
                    (float) (-sizeNumber * time),
                    (float) (Math.sin(
                            (i * 3.141592653589793) / (angles / 2.0)
                        ) *
                        ent.getWidth())
                )
                .color(setOpac(color, 0).getRGB())
                .next();
        }

        tessellator.draw();

        matrixStack.pop();

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    public static Color setOpac(Color color, int opac) {
        return new Color(
            color.getRed(),
            color.getGreen(),
            color.getBlue(),
            opac
        );
    }

    public static void draw2dESP(
        Vec3d pos,
        float width,
        float height,
        float lineWidth,
        float partialTicks,
        MatrixStack matrixStack,
        Color color
    ) {
        Vec3d cameraAngle = getCameraVector(partialTicks);

        float BoxX1 = (float) (pos.x - cameraAngle.x);
        float BoxY1 = (float) (pos.y - cameraAngle.y);
        float BoxZ1 = (float) (pos.z - cameraAngle.z);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        matrixStack.push();

        RenderSystem.setShaderColor(
            color.getRed() / 255f,
            color.getGreen() / 255f,
            color.getBlue() / 255f,
            color.getAlpha() / 255f
        );

        Matrix4f matrix = matrixStack.peek().getPositionMatrix();

        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);

        bufferBuilder.begin(
            VertexFormat.DrawMode.QUADS,
            VertexFormats.POSITION_COLOR
        );

        matrixStack.translate(BoxX1, BoxY1 + 1, BoxZ1);

        matrixStack.multiply(
            RotationAxis.NEGATIVE_Y.rotationDegrees(
                RotationUtil.yawTo360(C.p().getYaw())
            )
        );
        matrixStack.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(90));

        // filled square
        //bufferBuilder.vertex(matrix, -(width/2)/sizeMulti, 0, -(height/2)/sizeMulti).color(color.getRGB()).next();
        //bufferBuilder.vertex(matrix, (width/2)/sizeMulti, 0, -(height/2)/sizeMulti).color(color.getRGB()).next();
        //bufferBuilder.vertex(matrix, (width/2)/sizeMulti, 0, (height/2)/sizeMulti).color(color.getRGB()).next();
        //bufferBuilder.vertex(matrix, -(width/2)/sizeMulti, 0, (height/2)/sizeMulti).color(color.getRGB()).next();
        //bufferBuilder.vertex(matrix, -(width/2)/sizeMulti, 0, -(height/2)/sizeMulti).color(color.getRGB()).next();

        bufferBuilder
            .vertex(matrix, -(width / 2), 0, -(height / 2))
            .color(color.getRGB())
            .next();
        bufferBuilder
            .vertex(matrix, (width / 2), 0, -(height / 2))
            .color(color.getRGB())
            .next();
        bufferBuilder
            .vertex(matrix, (width / 2), 0, -(height / 2) + lineWidth)
            .color(color.getRGB())
            .next();
        bufferBuilder
            .vertex(matrix, -(width / 2), 0, -(height / 2) + lineWidth)
            .color(color.getRGB())
            .next();

        bufferBuilder
            .vertex(matrix, -(width / 2), 0, -(height / 2))
            .color(color.getRGB())
            .next();
        bufferBuilder
            .vertex(matrix, -(width / 2) + lineWidth, 0, -(height / 2))
            .color(color.getRGB())
            .next();
        bufferBuilder
            .vertex(matrix, -(width / 2) + lineWidth, 0, (height / 2))
            .color(color.getRGB())
            .next();
        bufferBuilder
            .vertex(matrix, -(width / 2), 0, (height / 2))
            .color(color.getRGB())
            .next();

        bufferBuilder
            .vertex(matrix, (width / 2), 0, -(height / 2))
            .color(color.getRGB())
            .next();
        bufferBuilder
            .vertex(matrix, (width / 2) - lineWidth, 0, -(height / 2))
            .color(color.getRGB())
            .next();
        bufferBuilder
            .vertex(matrix, (width / 2) - lineWidth, 0, (height / 2))
            .color(color.getRGB())
            .next();
        bufferBuilder
            .vertex(matrix, (width / 2), 0, (height / 2))
            .color(color.getRGB())
            .next();

        bufferBuilder
            .vertex(matrix, -(width / 2), 0, (height / 2))
            .color(color.getRGB())
            .next();
        bufferBuilder
            .vertex(matrix, (width / 2), 0, (height / 2))
            .color(color.getRGB())
            .next();
        bufferBuilder
            .vertex(matrix, (width / 2), 0, (height / 2) + lineWidth)
            .color(color.getRGB())
            .next();
        bufferBuilder
            .vertex(matrix, -(width / 2), 0, (height / 2) + lineWidth)
            .color(color.getRGB())
            .next();

        tessellator.draw();

        matrixStack.pop();

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    public static void drawHealthBar(
        Vec3d pos,
        float width,
        float height,
        float lineWidth,
        float partialTicks,
        MatrixStack matrixStack,
        Color color
    ) {
        Vec3d cameraAngle = getCameraVector(partialTicks);

        float BoxX1 = (float) (pos.x - cameraAngle.x);
        float BoxY1 = (float) (pos.y - cameraAngle.y);
        float BoxZ1 = (float) (pos.z - cameraAngle.z);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        matrixStack.push();

        RenderSystem.setShaderColor(
            color.getRed() / 255f,
            color.getGreen() / 255f,
            color.getBlue() / 255f,
            color.getAlpha() / 255f
        );

        Matrix4f matrix = matrixStack.peek().getPositionMatrix();

        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);

        bufferBuilder.begin(
            VertexFormat.DrawMode.QUADS,
            VertexFormats.POSITION_COLOR
        );

        matrixStack.translate(BoxX1, BoxY1 + 1, BoxZ1);

        matrixStack.multiply(
            RotationAxis.NEGATIVE_Y.rotationDegrees(
                RotationUtil.yawTo360(C.p().getYaw())
            )
        );
        matrixStack.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(90));

        bufferBuilder
            .vertex(matrix, -(width / 2) - 0.1F, 0, -0.9F)
            .color(new Color(23, 23, 23).getRGB())
            .next();
        bufferBuilder
            .vertex(matrix, -(width / 2) + lineWidth - 0.1F, 0, -0.9F)
            .color(new Color(23, 23, 23).getRGB())
            .next();
        bufferBuilder
            .vertex(matrix, -(width / 2) + lineWidth - 0.1F, 0, 0.9F)
            .color(new Color(23, 23, 23).getRGB())
            .next();
        bufferBuilder
            .vertex(matrix, -(width / 2) - 0.1F, 0, 0.9F)
            .color(new Color(23, 23, 23).getRGB())
            .next();

        bufferBuilder
            .vertex(matrix, -(width / 2) - 0.1F, 0, -0.9F)
            .color(color.getRGB())
            .next();
        bufferBuilder
            .vertex(matrix, -(width / 2) + lineWidth - 0.1F, 0, -0.9F)
            .color(color.getRGB())
            .next();
        bufferBuilder
            .vertex(
                matrix,
                -(width / 2) + lineWidth - 0.1F,
                0,
                (-0.9F + height)
            )
            .color(color.getRGB())
            .next();
        bufferBuilder
            .vertex(matrix, -(width / 2) - 0.1F, 0, (-0.9F + height))
            .color(color.getRGB())
            .next();

        tessellator.draw();

        matrixStack.pop();

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    public static void drawTexture(
        Identifier texture,
        int x,
        int y,
        int u,
        int v,
        int width,
        int height
    ) {
        drawTexture(
            texture,
            x,
            y,
            0,
            (float) u,
            (float) v,
            width,
            height,
            width,
            height
        );
    }

    static void drawTexture(
        Identifier texture,
        int x1,
        int x2,
        int y1,
        int y2,
        int z,
        int regionWidth,
        int regionHeight,
        float u,
        float v,
        int textureWidth,
        int textureHeight
    ) {
        drawTexturedQuad(
            texture,
            x1,
            x2,
            y1,
            y2,
            z,
            (u + 0.0F) / (float) textureWidth,
            (u + (float) regionWidth) / (float) textureWidth,
            (v + 0.0F) / (float) textureHeight,
            (v + (float) regionHeight) / (float) textureHeight
        );
    }

    public static void drawTexturedQuad(
        Identifier texture,
        int x1,
        int x2,
        int y1,
        int y2,
        int z,
        float u1,
        float u2,
        float v1,
        float v2,
        float red,
        float green,
        float blue,
        float alpha
    ) {
        if (matrix != null) {
            RenderSystem.setShaderTexture(0, texture);
            RenderSystem.setShader(GameRenderer::getPositionColorTexProgram);
            RenderSystem.enableBlend();
            Matrix4f matrix4f = matrix.peek().getPositionMatrix();
            BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
            bufferBuilder.begin(
                VertexFormat.DrawMode.QUADS,
                VertexFormats.POSITION_COLOR_TEXTURE
            );
            bufferBuilder
                .vertex(matrix4f, (float) x1, (float) y1, (float) z)
                .color(red, green, blue, alpha)
                .texture(u1, v1)
                .next();
            bufferBuilder
                .vertex(matrix4f, (float) x1, (float) y2, (float) z)
                .color(red, green, blue, alpha)
                .texture(u1, v2)
                .next();
            bufferBuilder
                .vertex(matrix4f, (float) x2, (float) y2, (float) z)
                .color(red, green, blue, alpha)
                .texture(u2, v2)
                .next();
            bufferBuilder
                .vertex(matrix4f, (float) x2, (float) y1, (float) z)
                .color(red, green, blue, alpha)
                .texture(u2, v1)
                .next();
            BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
            RenderSystem.disableBlend();
        }
    }

    static void drawTexturedQuad(
        Identifier texture,
        int x1,
        int x2,
        int y1,
        int y2,
        int z,
        float u1,
        float u2,
        float v1,
        float v2
    ) {
        if (matrix != null) {
            RenderSystem.setShaderTexture(0, texture);
            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
            BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
            bufferBuilder.begin(
                VertexFormat.DrawMode.QUADS,
                VertexFormats.POSITION_TEXTURE
            );

            Matrix4f matrix4f = matrix.peek().getPositionMatrix();

            bufferBuilder
                .vertex(matrix4f, (float) x1, (float) y1, (float) z)
                .texture(u1, v1)
                .next();
            bufferBuilder
                .vertex(matrix4f, (float) x1, (float) y2, (float) z)
                .texture(u1, v2)
                .next();
            bufferBuilder
                .vertex(matrix4f, (float) x2, (float) y2, (float) z)
                .texture(u2, v2)
                .next();
            bufferBuilder
                .vertex(matrix4f, (float) x2, (float) y1, (float) z)
                .texture(u2, v1)
                .next();
            BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
        }
    }

    // so many!
    public static void drawRoundedRectImage(
        Identifier texture,
        int x,
        int y,
        int width,
        int height,
        float u,
        float v,
        int regionWidth,
        int regionHeight,
        int textureWidth,
        int textureHeight,
        float radius
    ) {
        drawRoundedRectImage(
            texture,
            x,
            x + width,
            y,
            y + height,
            0,
            regionWidth,
            regionHeight,
            u,
            v,
            textureWidth,
            textureHeight,
            radius
        );
    }

    public static void drawRoundedRectImage(
        Identifier texture,
        int x,
        int y,
        float u,
        float v,
        int width,
        int height,
        int textureWidth,
        int textureHeight,
        float radius
    ) {
        drawRoundedRectImage(
            texture,
            x,
            y,
            width,
            height,
            u,
            v,
            width,
            height,
            textureWidth,
            textureHeight,
            radius
        );
    }

    public static void drawRoundedRectImage(
        Identifier texture,
        int x,
        int y,
        int u,
        int v,
        int width,
        int height,
        float radius,
        Color color
    ) {
        drawRoundedRectImage(
            texture,
            x,
            y,
            0,
            (float) u,
            (float) v,
            width,
            height,
            width,
            height,
            radius,
            color
        );
    }

    public static void drawRoundedRectImage(
        Identifier texture,
        int x,
        int y,
        int u,
        int v,
        int width,
        int height,
        float radius
    ) {
        drawRoundedRectImage(
            texture,
            x,
            y,
            0,
            (float) u,
            (float) v,
            width,
            height,
            width,
            height,
            radius
        );
    }

    public static void drawRoundedRectImage(
        Identifier texture,
        int x,
        int y,
        int z,
        float u,
        float v,
        int width,
        int height,
        int textureWidth,
        int textureHeight,
        float radius
    ) {
        drawRoundedRectImage(
            texture,
            x,
            x + width,
            y,
            y + height,
            z,
            width,
            height,
            u,
            v,
            textureWidth,
            textureHeight,
            radius
        );
    }

    public static void drawRoundedRectImage(
        Identifier texture,
        int x,
        int y,
        int z,
        float u,
        float v,
        int width,
        int height,
        int textureWidth,
        int textureHeight,
        float radius,
        Color color
    ) {
        drawRoundedRectImage(
            texture,
            x,
            x + width,
            y,
            y + height,
            z,
            width,
            height,
            u,
            v,
            textureWidth,
            textureHeight,
            radius,
            color
        );
    }

    public static void drawRoundedRectImage(
        Identifier texture,
        int x1,
        int x2,
        int y1,
        int y2,
        int z,
        int regionWidth,
        int regionHeight,
        float u,
        float v,
        int textureWidth,
        int textureHeight,
        float radius
    ) {
        drawRoundedRectImage(
            texture,
            x1,
            x2,
            y1,
            y2,
            z,
            (u + 0.0F) / (float) textureWidth,
            (u + (float) regionWidth) / (float) textureWidth,
            (v + 0.0F) / (float) textureHeight,
            (v + (float) regionHeight) / (float) textureHeight,
            radius,
            1,
            1,
            1,
            1
        );
    }

    public static void drawRoundedRectImage(
        Identifier texture,
        int x1,
        int x2,
        int y1,
        int y2,
        int z,
        int regionWidth,
        int regionHeight,
        float u,
        float v,
        int textureWidth,
        int textureHeight,
        float radius,
        Color color
    ) {
        drawRoundedRectImage(
            texture,
            x1,
            x2,
            y1,
            y2,
            z,
            (u + 0.0F) / (float) textureWidth,
            (u + (float) regionWidth) / (float) textureWidth,
            (v + 0.0F) / (float) textureHeight,
            (v + (float) regionHeight) / (float) textureHeight,
            radius,
            color.getRed() / 255f,
            color.getGreen() / 255f,
            color.getBlue() / 255f,
            color.getAlpha() / 255f
        );
    }

    public static void drawRoundedRectImage(
        Identifier texture,
        int x1,
        int x2,
        int y1,
        int y2,
        int z,
        int regionWidth,
        int regionHeight,
        float u,
        float v,
        int textureWidth,
        int textureHeight,
        float radius,
        float red,
        float green,
        float blue,
        float alpha
    ) {
        drawRoundedRectImage(
            texture,
            x1,
            x2,
            y1,
            y2,
            z,
            (u + 0.0F) / (float) textureWidth,
            (u + (float) regionWidth) / (float) textureWidth,
            (v + 0.0F) / (float) textureHeight,
            (v + (float) regionHeight) / (float) textureHeight,
            radius,
            red,
            green,
            blue,
            alpha
        );
    }

    public static void drawRoundedRectImage(
        Identifier texture,
        int x1,
        int x2,
        int y1,
        int y2,
        int z,
        float u1,
        float u2,
        float v1,
        float v2,
        float radius,
        float red,
        float green,
        float blue,
        float alpha
    ) {
        if (matrix != null) {
            RenderSystem.setShaderTexture(0, texture);
            RenderSystem.setShader(GameRenderer::getPositionColorTexProgram);
            BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
            bufferBuilder.begin(
                VertexFormat.DrawMode.TRIANGLE_FAN,
                VertexFormats.POSITION_COLOR_TEXTURE
            );

            Matrix4f matrix4f = matrix.peek().getPositionMatrix();

            for (int i = 0; i <= 90; i += 3) {
                float xN = getRoundedRectPoint(x1, radius, i, 1);
                float yN = getRoundedRectPoint(y1, radius, i, 2);

                // (xN-x1) / (x2-x1), (yN-y1) / (y2-y1) // draws full texture without offset.
                // so multiply this by the end offset percent
                // and add the start offset percent

                float xPos = ((xN - x1) / (x2 - x1)) * (u2 - u1) + u1;
                float yPos = ((yN - y1) / (y2 - y1)) * (v2 - v1) + v1;

                bufferBuilder
                    .vertex(matrix4f, xN, yN, 0)
                    .color(red, green, blue, alpha)
                    .texture(xPos, yPos)
                    .next();
            }

            for (int i = 90; i <= 180; i += 3) {
                float xN = getRoundedRectPoint(x1, radius, i, 3);
                float yN = getRoundedRectPoint(y2, radius, i, 4);

                float xPos = ((xN - x1) / (x2 - x1)) * (u2 - u1) + u1;
                float yPos = ((yN - y1) / (y2 - y1)) * (v2 - v1) + v1;

                bufferBuilder
                    .vertex(matrix4f, xN, yN, 0)
                    .color(red, green, blue, alpha)
                    .texture(xPos, yPos)
                    .next();
            }
            for (int i = 0; i <= 90; i += 3) {
                float xN = getRoundedRectPoint(x2, radius, i, 5);
                float yN = getRoundedRectPoint(y2, radius, i, 6);

                float xPos = ((xN - x1) / (x2 - x1)) * (u2 - u1) + u1;
                float yPos = ((yN - y1) / (y2 - y1)) * (v2 - v1) + v1;

                bufferBuilder
                    .vertex(matrix4f, xN, yN, 0)
                    .color(red, green, blue, alpha)
                    .texture(xPos, yPos)
                    .next();
            }
            for (int i = 90; i <= 180; i += 3) {
                float xN = getRoundedRectPoint(x2, radius, i, 7);
                float yN = getRoundedRectPoint(y1, radius, i, 8);

                float xPos = ((xN - x1) / (x2 - x1)) * (u2 - u1) + u1;
                float yPos = ((yN - y1) / (y2 - y1)) * (v2 - v1) + v1;

                bufferBuilder
                    .vertex(matrix4f, xN, yN, 0)
                    .color(red, green, blue, alpha)
                    .texture(xPos, yPos)
                    .next();
            }

            //bufferBuilder.vertex(matrix4f, (float) x1, (float) y1, (float) z).texture(u1, v1).next();
            //bufferBuilder.vertex(matrix4f, (float) x1, (float) y2, (float) z).texture(u1, v2).next();
            //bufferBuilder.vertex(matrix4f, (float) x2, (float) y2, (float) z).texture(u2, v2).next();
            //bufferBuilder.vertex(matrix4f, (float) x2, (float) y1, (float) z).texture(u2, v1).next();

            BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
        }
    }

    public static void drawAnimeGirlESP(
        AbstractClientPlayerEntity player,
        float width,
        float height,
        float partialTicks,
        MatrixStack matrixStack,
        Identifier texture
    ) {
        Vec3d cameraAngle = getCameraVector(partialTicks);

        double playX =
            player.lastRenderX +
            (player.getX() - player.lastRenderX) * partialTicks;
        double playY =
            player.lastRenderY +
            (player.getY() - player.lastRenderY) * partialTicks;
        double playZ =
            player.lastRenderZ +
            (player.getZ() - player.lastRenderZ) * partialTicks;

        float BoxX1 = (float) (playX - cameraAngle.x);
        float BoxY1 = (float) (playY - cameraAngle.y);
        float BoxZ1 = (float) (playZ - cameraAngle.z);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        matrixStack.push();

        matrixStack.translate(BoxX1, BoxY1, BoxZ1);

        matrixStack.multiply(
            RotationAxis.NEGATIVE_Y.rotationDegrees(
                RotationUtil.yawTo360(C.p().getYaw() + 180)
            )
        );
        matrixStack.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees(180));
        matrixStack.scale(0.001f, 0.001f, 0.001f);

        matrix = matrixStack;

        drawTexture(
            texture,
            -(int) (width * 500),
            -(int) (height * 1000),
            0,
            0,
            (int) (width * 1000),
            (int) (height * 1000)
        );

        //tessellator.draw();

        matrixStack.pop();

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    public static void drawHitSubtitle(
        Identifier texture,
        Vec3d pos,
        float width,
        float height,
        long HitTime,
        float partialTicks,
        MatrixStack matrixStack
    ) {
        Vec3d cameraAngle = getCameraVector(partialTicks);

        float BoxX1 = (float) (pos.x - cameraAngle.x);
        float BoxY1 = (float) (pos.y - cameraAngle.y);
        float BoxZ1 = (float) (pos.z - cameraAngle.z);

        float size = (1000f / (System.currentTimeMillis() - HitTime));

        if (size >= 1) {
            BoxY1 -= 0.05f * (size);
            size = 1;
        } else BoxY1 -= 0.05f;

        width *= size;
        height *= size;

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        matrixStack.push();

        matrixStack.translate(BoxX1, BoxY1, BoxZ1);

        matrixStack.multiply(
            RotationAxis.NEGATIVE_Y.rotationDegrees(
                RotationUtil.yawTo360(C.p().getYaw() + 180)
            )
        );
        matrixStack.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees(180));
        matrixStack.scale(0.001f, 0.001f, 0.001f);

        matrix = matrixStack;

        // * 500 to scale picture up to not be 1 pixel.
        drawTexture(
            texture,
            -(int) (width * 500),
            -(int) (height * 1000),
            0,
            0,
            (int) (width * 1000),
            (int) (height * 1000)
        );

        //tessellator.draw();

        matrixStack.pop();

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    public static void drawLine3d(
        Vec3d pos1,
        Vec3d pos2,
        float partialTicks,
        MatrixStack matrixStack,
        Color color
    ) {
        Vec3d cameraAngle = getCameraVector(partialTicks);

        float BoxX1 = (float) (pos1.x - cameraAngle.x);
        float BoxY1 = (float) (pos1.y - cameraAngle.y);
        float BoxZ1 = (float) (pos1.z - cameraAngle.z);

        float BoxX2 = (float) (pos2.x - cameraAngle.x);
        float BoxY2 = (float) (pos2.y - cameraAngle.y);
        float BoxZ2 = (float) (pos2.z - cameraAngle.z);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        matrixStack.push();

        RenderSystem.setShaderColor(
            color.getRed() / 255f,
            color.getGreen() / 255f,
            color.getBlue() / 255f,
            color.getAlpha() / 255f
        );

        Matrix4f matrix = matrixStack.peek().getPositionMatrix();

        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);

        bufferBuilder.begin(
            VertexFormat.DrawMode.DEBUG_LINE_STRIP,
            VertexFormats.POSITION_COLOR
        );
        bufferBuilder
            .vertex(matrix, BoxX1, BoxY1, BoxZ1)
            .color(color.getRGB())
            .next();
        bufferBuilder
            .vertex(matrix, BoxX2, BoxY2, BoxZ2)
            .color(color.getRGB())
            .next();
        //bufferBuilder.vertex(matrix, BoxX2 + 0.1f, BoxY2 + 0.1f, BoxZ2 + 0.1f).color(color.getRGB()).next();
        //bufferBuilder.vertex(matrix, BoxX2, BoxY2, BoxZ2).color(color.getRGB()).next();

        tessellator.draw();

        matrixStack.pop();

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    public static void drawBox2(
        double x1,
        double y1,
        double z1,
        double x2,
        double y2,
        double z2,
        float partialTicks,
        MatrixStack matrixStack,
        Color color
    ) {
        Vec3d cameraAngle = getCameraVector(partialTicks);

        float BoxX1 = (float) (x1 - cameraAngle.x);
        float BoxX2 = (float) (BoxX1 + x2);

        float BoxY1 = (float) (y1 - cameraAngle.y);
        float BoxY2 = (float) (BoxY1 + y2);

        float BoxZ1 = (float) (z1 - cameraAngle.z);
        float BoxZ2 = (float) (BoxZ1 + z2);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        matrixStack.push();

        RenderSystem.setShaderColor(
            color.getRed() / 255f,
            color.getGreen() / 255f,
            color.getBlue() / 255f,
            color.getAlpha() / 255f
        );

        Matrix4f matrix = matrixStack.peek().getPositionMatrix();

        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionProgram);

        bufferBuilder.begin(
            VertexFormat.DrawMode.QUADS,
            VertexFormats.POSITION
        );
        bufferBuilder.vertex(matrix, BoxX1, BoxY1, BoxZ1).next();
        bufferBuilder.vertex(matrix, BoxX2, BoxY1, BoxZ1).next();
        bufferBuilder.vertex(matrix, BoxX2, BoxY1, BoxZ2).next();
        bufferBuilder.vertex(matrix, BoxX1, BoxY1, BoxZ2).next();
        tessellator.draw();
        bufferBuilder.begin(
            VertexFormat.DrawMode.QUADS,
            VertexFormats.POSITION
        );
        bufferBuilder.vertex(matrix, BoxX1, BoxY2, BoxZ2).next();
        bufferBuilder.vertex(matrix, BoxX2, BoxY2, BoxZ2).next();
        bufferBuilder.vertex(matrix, BoxX2, BoxY2, BoxZ1).next();
        bufferBuilder.vertex(matrix, BoxX1, BoxY2, BoxZ1).next();
        tessellator.draw();

        bufferBuilder.begin(
            VertexFormat.DrawMode.QUADS,
            VertexFormats.POSITION
        );
        bufferBuilder.vertex(matrix, BoxX1, BoxY1, BoxZ2).next();
        bufferBuilder.vertex(matrix, BoxX1, BoxY2, BoxZ2).next();
        bufferBuilder.vertex(matrix, BoxX1, BoxY2, BoxZ1).next();
        bufferBuilder.vertex(matrix, BoxX1, BoxY1, BoxZ1).next();
        tessellator.draw();
        bufferBuilder.begin(
            VertexFormat.DrawMode.QUADS,
            VertexFormats.POSITION
        );
        bufferBuilder.vertex(matrix, BoxX2, BoxY1, BoxZ1).next();
        bufferBuilder.vertex(matrix, BoxX2, BoxY2, BoxZ1).next();
        bufferBuilder.vertex(matrix, BoxX2, BoxY2, BoxZ2).next();
        bufferBuilder.vertex(matrix, BoxX2, BoxY1, BoxZ2).next();
        tessellator.draw();

        bufferBuilder.begin(
            VertexFormat.DrawMode.QUADS,
            VertexFormats.POSITION
        );
        bufferBuilder.vertex(matrix, BoxX1, BoxY2, BoxZ1).next();
        bufferBuilder.vertex(matrix, BoxX2, BoxY2, BoxZ1).next();
        bufferBuilder.vertex(matrix, BoxX2, BoxY1, BoxZ1).next();
        bufferBuilder.vertex(matrix, BoxX1, BoxY1, BoxZ1).next();
        tessellator.draw();
        bufferBuilder.begin(
            VertexFormat.DrawMode.QUADS,
            VertexFormats.POSITION
        );
        bufferBuilder.vertex(matrix, BoxX1, BoxY1, BoxZ2).next();
        bufferBuilder.vertex(matrix, BoxX2, BoxY1, BoxZ2).next();
        bufferBuilder.vertex(matrix, BoxX2, BoxY2, BoxZ2).next();
        bufferBuilder.vertex(matrix, BoxX1, BoxY2, BoxZ2).next();
        tessellator.draw();

        matrixStack.pop();

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    public static void drawNameTag(
        float partialTicks,
        MatrixStack matrixStack,
        Entity entity
    ) {
        double playX =
            entity.lastRenderX +
            (entity.getX() - entity.lastRenderX) * partialTicks;
        double playY =
            entity.lastRenderY +
            (entity.getY() - entity.lastRenderY) * partialTicks;
        double playZ =
            entity.lastRenderZ +
            (entity.getZ() - entity.lastRenderZ) * partialTicks;

        playY += 4.21;

        Vec3d cameraAngle = getCameraVector(partialTicks);

        float BoxX1 = (float) (playX - cameraAngle.x);
        float BoxY1 = (float) (playY - cameraAngle.y);
        float BoxZ1 = (float) (playZ - cameraAngle.z);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        matrixStack.push();

        Matrix4f matrix = matrixStack.peek().getPositionMatrix();

        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);

        bufferBuilder.begin(
            VertexFormat.DrawMode.QUADS,
            VertexFormats.POSITION_COLOR
        );

        matrixStack.translate(BoxX1, BoxY1, BoxZ1);
        matrixStack.multiply(
            RotationAxis.NEGATIVE_Y.rotationDegrees(
                RotationUtil.yawTo360(C.p().getYaw())
            )
        );

        float size = Math.max(Math.min(C.p().distanceTo(entity) / 8, 8), 1);

        matrixStack.scale(0.05F * size, 0.05F * size, 1);
        matrixStack.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(180));

        String name = entity.getName().getString();
        int nameWidth = (int) RenderUtil.getFontWidth(name);
        int nameHeight = 10;

        int hardCodedYoffset = 3;
        bufferBuilder
            .vertex(
                matrix,
                (float) -nameWidth / 2,
                -nameHeight / 2f - hardCodedYoffset,
                0
            )
            .color(0, 0, 0, 0.5F)
            .next();
        bufferBuilder
            .vertex(
                matrix,
                (float) -nameWidth / 2,
                nameHeight / 2f - hardCodedYoffset,
                0
            )
            .color(0, 0, 0, 0.5F)
            .next();
        bufferBuilder
            .vertex(
                matrix,
                (float) nameWidth / 2,
                nameHeight / 2f - hardCodedYoffset,
                0
            )
            .color(0, 0, 0, 0.5F)
            .next();
        bufferBuilder
            .vertex(
                matrix,
                (float) nameWidth / 2,
                -nameHeight / 2f - hardCodedYoffset,
                0
            )
            .color(0, 0, 0, 0.5F)
            .next();

        tessellator.draw();

        matrixStack.push();
        matrixStack.translate(0, -nameHeight, 0);

        if (ClickGUI.fontType == ClickGUI.font.Minecraft) {
            Sans_Serif.drawCenteredString(matrixStack, name, 0, 0, 1, 1, 1, 1);
        } else {
            funnyFont.drawCenteredString(matrixStack, name, 0, 0, 1, 1, 1, 1);
        }

        matrixStack.pop();
        if (entity instanceof LivingEntity living) {
            Iterator<ItemStack> armorItems = living.getArmorItems().iterator();
            int n = 0;
            while (armorItems.hasNext()) {
                n++;
                armorItems.next();
            }

            armorItems = living.getArmorItems().iterator();
            while (armorItems.hasNext()) {
                n--;
                ItemStack item = armorItems.next();
                int xPos = -nameWidth / 2 - 2 + (n * 20) + 10;
                int yPos = nameHeight + 2;
                drawItem(item, xPos, yPos);
            }
        }

        matrixStack.pop();

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);

        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    public static void drawNameTag2(
        float partialTicks,
        MatrixStack matrixStack,
        Entity entity
    ) {
        double playX =
            entity.lastRenderX +
            (entity.getX() - entity.lastRenderX) * partialTicks;
        double playY =
            entity.lastRenderY +
            (entity.getY() - entity.lastRenderY) * partialTicks;
        double playZ =
            entity.lastRenderZ +
            (entity.getZ() - entity.lastRenderZ) * partialTicks;

        playY += 4.21;

        Vec3d cameraAngle = getCameraVector(partialTicks);

        float BoxX1 = (float) (playX - cameraAngle.x);
        float BoxY1 = (float) (playY - cameraAngle.y);
        float BoxZ1 = (float) (playZ - cameraAngle.z);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        matrixStack.push();

        Matrix4f matrix = matrixStack.peek().getPositionMatrix();

        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);

        bufferBuilder.begin(
            VertexFormat.DrawMode.QUADS,
            VertexFormats.POSITION_COLOR
        );

        matrixStack.translate(BoxX1, BoxY1, BoxZ1);
        matrixStack.multiply(
            RotationAxis.NEGATIVE_Y.rotationDegrees(
                RotationUtil.yawTo360(C.p().getYaw())
            )
        );

        float size = Math.max(Math.min(C.p().distanceTo(entity) / 8, 8), 1);

        matrixStack.scale(0.05F * size, 0.05F * size, 1);
        matrixStack.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(180));

        String name = entity.getName().getString();
        int nameWidth = (int) RenderUtil.getFontWidth(name);
        int nameHeight = 10;
        bufferBuilder
            .vertex(matrix, (float) -nameWidth / 2, -nameHeight, 0)
            .color(0, 0, 0, 0.5F)
            .next();
        bufferBuilder
            .vertex(matrix, (float) -nameWidth / 2, nameHeight, 0)
            .color(0, 0, 0, 0.5F)
            .next();
        bufferBuilder
            .vertex(matrix, (float) nameWidth / 2, nameHeight, 0)
            .color(0, 0, 0, 0.5F)
            .next();
        bufferBuilder
            .vertex(matrix, (float) nameWidth / 2, -nameHeight, 0)
            .color(0, 0, 0, 0.5F)
            .next();

        tessellator.draw();

        matrixStack.push();
        matrixStack.translate(0, -nameHeight, 0);

        if (ClickGUI.fontType == ClickGUI.font.Minecraft) {
            Sans_Serif.drawCenteredString(matrixStack, name, 0, 0, 1, 1, 1, 1);
        } else {
            funnyFont.drawCenteredString(matrixStack, name, 0, 0, 1, 1, 1, 1);
        }

        matrixStack.pop();
        if (entity instanceof LivingEntity living) {
            Iterator<ItemStack> armorItems = living.getArmorItems().iterator();
            int n = 0;
            while (armorItems.hasNext()) {
                n++;
                armorItems.next();
            }

            armorItems = living.getArmorItems().iterator();
            while (armorItems.hasNext()) {
                n--;
                ItemStack item = armorItems.next();
                int xPos = -nameWidth / 2 - 2 + (n * 20) + 10;
                int yPos = nameHeight + 2;
                RenderUtil.drawItem(item, xPos, yPos);
            }
        }

        matrixStack.pop();

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);

        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    public static void fractionsHollowRoundedRect3d(
        float partialTicks,
        MatrixStack matrixStack,
        Entity entity
    ) {
        double playX =
            entity.lastRenderX +
            (entity.getX() - entity.lastRenderX) * partialTicks;
        double playY =
            entity.lastRenderY +
            (entity.getY() - entity.lastRenderY) * partialTicks;
        double playZ =
            entity.lastRenderZ +
            (entity.getZ() - entity.lastRenderZ) * partialTicks;

        Vec3d cameraAngle = getCameraVector(partialTicks);

        float BoxX1 = (float) (playX - cameraAngle.x);
        float BoxY1 = (float) (playY - cameraAngle.y) + 0.9f;
        float BoxZ1 = (float) (playZ - cameraAngle.z);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        matrixStack.push();

        Matrix4f matrix = matrixStack.peek().getPositionMatrix();

        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        Color color = ThemeUtil.themeColors()[0];
        RenderSystem.setShaderColor(
            color.getRed() / 255f,
            color.getGreen() / 255f,
            color.getBlue() / 255f,
            color.getAlpha() / 255f
        );

        bufferBuilder.begin(
            VertexFormat.DrawMode.QUADS,
            VertexFormats.POSITION
        );

        matrixStack.translate(BoxX1, BoxY1, BoxZ1);
        matrixStack.multiply(
            RotationAxis.NEGATIVE_Y.rotationDegrees(
                RotationUtil.yawTo360(C.p().getYaw())
            )
        );

        float size = Math.max(Math.min(C.p().distanceTo(entity) / 8, 8), 1);

        matrixStack.scale(0.01F * size, 0.01F * size, 1);

        matrixStack.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(180));

        float width1 = 50;
        float height1 = 50;
        float radius1 = 5;
        float glowWidth = 10;

        float x1 = -width1 / 2;
        float y1 = -height1 / 2;

        float x2 = x1 + width1;
        float y2 = y1 + height1;

        float radius2 = radius1 + glowWidth;

        float x1outer = x1 - glowWidth;
        float y1outer = y1 - glowWidth;

        float x2outer = x2 + glowWidth;
        float y2outer = y2 + glowWidth;

        float xStart = getRoundedRectPoint(x1, radius1, 0, 1);
        float yStart = getRoundedRectPoint(x1, radius1, 0, 2);
        float xEnd = getRoundedRectPoint(x1, radius1, 0, 1);
        float yEnd;

        float xStart2 = getRoundedRectPoint(x1, radius2, 0, 1);
        float yStart2 = getRoundedRectPoint(x1, radius2, 0, 2);
        float xEnd2 = getRoundedRectPoint(x1outer, radius2, 0, 1);
        float yEnd2;

        float disconnectSize = 10f;

        double multi =
            (System.currentTimeMillis() / 10d) *
            (((1 + ((PlayerEntity) entity).hurtTime) / 10d) + 1);

        matrixStack.multiply(
            RotationAxis.NEGATIVE_Z.rotationDegrees((float) (multi % 360))
        );

        for (int i = 0; i < 90; i += 3) {
            // optimized maths (joke im so sorry) but its a LOT faster than the old
            xStart = getRoundedRectPoint(x1, radius1, i, 1);
            yStart = getRoundedRectPoint(y1, radius1, i, 2);
            xEnd = getRoundedRectPoint(x1, radius1, i + 3, 1);
            yEnd = getRoundedRectPoint(y1, radius1, i + 3, 2);

            xStart2 = getRoundedRectPoint(x1outer, radius2, i, 1);
            yStart2 = getRoundedRectPoint(y1outer, radius2, i, 2);
            xEnd2 = getRoundedRectPoint(x1outer, radius2, i + 3, 1);
            yEnd2 = getRoundedRectPoint(y1outer, radius2, i + 3, 2);

            bufferBuilder.vertex(matrix, xStart, yStart, 0).next();
            bufferBuilder.vertex(matrix, xStart2, yStart2, 0).next();
            bufferBuilder.vertex(matrix, xEnd2, yEnd2, 0).next();
            bufferBuilder.vertex(matrix, xEnd, yEnd, 0).next();
        }

        yEnd = getRoundedRectPoint(y2, radius1, 90, 4);

        bufferBuilder.vertex(matrix, xStart, yStart, 0).next();
        bufferBuilder.vertex(matrix, xStart2, yStart, 0).next();
        bufferBuilder
            .vertex(matrix, xStart2, yStart + disconnectSize, 0)
            .next();
        bufferBuilder.vertex(matrix, xStart, yStart + disconnectSize, 0).next();

        bufferBuilder.vertex(matrix, xStart, yEnd - disconnectSize, 0).next();
        bufferBuilder.vertex(matrix, xStart2, yEnd - disconnectSize, 0).next();
        bufferBuilder.vertex(matrix, xEnd2, yEnd, 0).next();
        bufferBuilder.vertex(matrix, xEnd, yEnd, 0).next();

        for (int i = 90; i < 180; i += 3) {
            xStart = getRoundedRectPoint(x1, radius1, i, 3);
            yStart = getRoundedRectPoint(y2, radius1, i, 4);
            xEnd = getRoundedRectPoint(x1, radius1, i + 3, 3);
            yEnd = getRoundedRectPoint(y2, radius1, i + 3, 4);

            xStart2 = getRoundedRectPoint(x1outer, radius2, i, 3);
            yStart2 = getRoundedRectPoint(y2outer, radius2, i, 4);
            xEnd2 = getRoundedRectPoint(x1outer, radius2, i + 3, 3);
            yEnd2 = getRoundedRectPoint(y2outer, radius2, i + 3, 4);

            bufferBuilder.vertex(matrix, xStart, yStart, 0).next();
            bufferBuilder.vertex(matrix, xStart2, yStart2, 0).next();
            bufferBuilder.vertex(matrix, xEnd2, yEnd2, 0).next();
            bufferBuilder.vertex(matrix, xEnd, yEnd, 0).next();
        }

        xEnd = getRoundedRectPoint(x2, radius1, 0, 5);
        yEnd = getRoundedRectPoint(y2, radius1, 0, 6);
        xEnd2 = getRoundedRectPoint(x2, radius1, 0, 5);
        yEnd2 = getRoundedRectPoint(y2, radius1, 0, 6);

        bufferBuilder.vertex(matrix, xStart, yStart, 0).next();
        bufferBuilder.vertex(matrix, xStart + disconnectSize, yStart, 0).next();
        bufferBuilder
            .vertex(matrix, xStart + disconnectSize, yStart2, 0)
            .next();
        bufferBuilder.vertex(matrix, xStart, yStart2, 0).next();

        bufferBuilder.vertex(matrix, xEnd, yStart, 0).next();
        bufferBuilder.vertex(matrix, xEnd - disconnectSize, yStart, 0).next();
        bufferBuilder.vertex(matrix, xEnd - disconnectSize, yStart2, 0).next();
        bufferBuilder.vertex(matrix, xEnd, yStart2, 0).next();

        for (int i = 0; i < 90; i += 3) {
            xStart = getRoundedRectPoint(x2, radius1, i, 5);
            yStart = getRoundedRectPoint(y2, radius1, i, 6);
            xEnd = getRoundedRectPoint(x2, radius1, i + 3, 5);
            yEnd = getRoundedRectPoint(y2, radius1, i + 3, 6);

            xStart2 = getRoundedRectPoint(x2outer, radius2, i, 5);
            yStart2 = getRoundedRectPoint(y2outer, radius2, i, 6);
            xEnd2 = getRoundedRectPoint(x2outer, radius2, i + 3, 5);
            yEnd2 = getRoundedRectPoint(y2outer, radius2, i + 3, 6);

            bufferBuilder.vertex(matrix, xStart, yStart, 0).next();
            bufferBuilder.vertex(matrix, xStart2, yStart2, 0).next();
            bufferBuilder.vertex(matrix, xEnd2, yEnd2, 0).next();
            bufferBuilder.vertex(matrix, xEnd, yEnd, 0).next();
        }

        xEnd = getRoundedRectPoint(x2, radius1, 90, 7);
        yEnd = getRoundedRectPoint(y1, radius1, 90, 8);

        bufferBuilder.vertex(matrix, xStart, yStart, 0).next();
        bufferBuilder.vertex(matrix, xEnd2, yStart, 0).next();
        bufferBuilder.vertex(matrix, xEnd2, yStart - disconnectSize, 0).next();
        bufferBuilder.vertex(matrix, xStart, yStart - disconnectSize, 0).next();

        bufferBuilder.vertex(matrix, xEnd, yEnd + disconnectSize, 0).next();
        bufferBuilder.vertex(matrix, xEnd2, yEnd + disconnectSize, 0).next();
        bufferBuilder.vertex(matrix, xEnd2, yEnd, 0).next();
        bufferBuilder.vertex(matrix, xEnd, yEnd, 0).next();

        for (int i = 90; i <= 180; i += 3) {
            xStart = getRoundedRectPoint(x2, radius1, i, 7);
            yStart = getRoundedRectPoint(y1, radius1, i, 8);
            xEnd = getRoundedRectPoint(x2, radius1, i + 3, 7);
            yEnd = getRoundedRectPoint(y1, radius1, i + 3, 8);

            xStart2 = getRoundedRectPoint(x2outer, radius2, i, 7);
            yStart2 = getRoundedRectPoint(y1outer, radius2, i, 8);
            xEnd2 = getRoundedRectPoint(x2outer, radius2, i + 3, 7);
            yEnd2 = getRoundedRectPoint(y1outer, radius2, i + 3, 8);

            bufferBuilder.vertex(matrix, xStart, yStart, 0).next();
            bufferBuilder.vertex(matrix, xStart2, yStart2, 0).next();
            bufferBuilder.vertex(matrix, xEnd2, yEnd2, 0).next();
            bufferBuilder.vertex(matrix, xEnd, yEnd, 0).next();
        }

        xEnd = getRoundedRectPoint(x1, radius1, 0, 1);

        bufferBuilder.vertex(matrix, xStart, yStart, 0).next();
        bufferBuilder.vertex(matrix, xStart - disconnectSize, yStart, 0).next();
        bufferBuilder
            .vertex(matrix, xStart - disconnectSize, yStart2, 0)
            .next();
        bufferBuilder.vertex(matrix, xStart, yStart2, 0).next();

        bufferBuilder.vertex(matrix, xEnd + disconnectSize, yStart, 0).next();
        bufferBuilder.vertex(matrix, xEnd, yStart, 0).next();
        bufferBuilder.vertex(matrix, xEnd, yStart2, 0).next();
        bufferBuilder.vertex(matrix, xEnd + disconnectSize, yStart2, 0).next();

        tessellator.draw();

        matrixStack.pop();

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    public static void drawBox(
        double x1,
        double y1,
        double z1,
        double x2,
        double y2,
        double z2,
        float partialTicks,
        MatrixStack matrixStack,
        Color color
    ) {
        // doesnt workie for f5 n shit :(
        Vec3d cameraAngle = getCameraVector(partialTicks);

        float BoxX1 = (float) (x1 - cameraAngle.x);
        float BoxX2 = (float) (BoxX1 + x2);

        float BoxY1 = (float) (y1 - cameraAngle.y);
        float BoxY2 = (float) (BoxY1 + y2);

        float BoxZ1 = (float) (z1 - cameraAngle.z);
        float BoxZ2 = (float) (BoxZ1 + z2);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        matrixStack.push();

        RenderSystem.setShaderColor(
            color.getRed() / 255f,
            color.getGreen() / 255f,
            color.getBlue() / 255f,
            color.getAlpha() / 255f
        );

        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionProgram);

        bufferBuilder.begin(
            VertexFormat.DrawMode.TRIANGLE_FAN,
            VertexFormats.POSITION
        );

        // yayy (i spent like 5 minutes doin a diagram to help me join up points, and they dont join up correctly in TRIANGLE_FAN :(

        // BOTTOM
        bufferBuilder.vertex(matrix, BoxX2, BoxY1, BoxZ1).next();
        bufferBuilder.vertex(matrix, BoxX2, BoxY1, BoxZ2).next();
        bufferBuilder.vertex(matrix, BoxX1, BoxY1, BoxZ2).next();
        bufferBuilder.vertex(matrix, BoxX1, BoxY1, BoxZ1).next();
        // SIDES
        bufferBuilder.vertex(matrix, BoxX1, BoxY2, BoxZ1).next();
        bufferBuilder.vertex(matrix, BoxX2, BoxY2, BoxZ1).next();
        bufferBuilder.vertex(matrix, BoxX2, BoxY1, BoxZ1).next();
        bufferBuilder.vertex(matrix, BoxX1, BoxY1, BoxZ1).next();

        bufferBuilder.vertex(matrix, BoxX1, BoxY1, BoxZ2).next();

        bufferBuilder.vertex(matrix, BoxX2, BoxY1, BoxZ2).next();
        bufferBuilder.vertex(matrix, BoxX2, BoxY2, BoxZ2).next();
        bufferBuilder.vertex(matrix, BoxX1, BoxY2, BoxZ2).next();
        bufferBuilder.vertex(matrix, BoxX1, BoxY1, BoxZ2).next();

        bufferBuilder.vertex(matrix, BoxX2, BoxY1, BoxZ2).next();

        bufferBuilder.vertex(matrix, BoxX2, BoxY1, BoxZ1).next();
        bufferBuilder.vertex(matrix, BoxX2, BoxY2, BoxZ1).next();
        bufferBuilder.vertex(matrix, BoxX2, BoxY2, BoxZ2).next();
        bufferBuilder.vertex(matrix, BoxX2, BoxY1, BoxZ2).next();

        bufferBuilder.vertex(matrix, BoxX2, BoxY1, BoxZ1).next();

        bufferBuilder.vertex(matrix, BoxX1, BoxY1, BoxZ1).next();
        bufferBuilder.vertex(matrix, BoxX1, BoxY2, BoxZ1).next();
        bufferBuilder.vertex(matrix, BoxX2, BoxY2, BoxZ1).next();
        bufferBuilder.vertex(matrix, BoxX2, BoxY1, BoxZ1).next();

        // TOP!!
        bufferBuilder.vertex(matrix, BoxX2, BoxY2, BoxZ1).next();
        bufferBuilder.vertex(matrix, BoxX2, BoxY2, BoxZ2).next();
        bufferBuilder.vertex(matrix, BoxX1, BoxY2, BoxZ2).next();
        bufferBuilder.vertex(matrix, BoxX1, BoxY2, BoxZ1).next();

        tessellator.draw();

        matrixStack.pop();

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    public static void drawItem(ItemStack item, int x, int y) {
        drawItem(C.p(), C.w(), item, x, y, 0);
    }

    public static void drawItem(ItemStack stack, int x, int y, int seed) {
        drawItem(C.p(), C.w(), stack, x, y, seed);
    }

    public static void drawItem(
        ItemStack stack,
        int x,
        int y,
        int seed,
        int z
    ) {
        drawItem(C.p(), C.w(), stack, x, y, seed, z);
    }

    public static void drawItem(
        LivingEntity entity,
        ItemStack stack,
        int x,
        int y,
        int seed
    ) {
        drawItem(entity, entity.getWorld(), stack, x, y, seed);
    }

    private static void drawItem(
        @Nullable LivingEntity entity,
        @Nullable World world,
        ItemStack stack,
        int x,
        int y,
        int seed
    ) {
        drawItem(entity, world, stack, x, y, seed, 0);
    }

    public static void draw() {
        RenderSystem.disableDepthTest();
        currentContext.getVertexConsumers().draw();
        RenderSystem.enableDepthTest();
    }

    private static void drawItem(
        @Nullable LivingEntity entity,
        @Nullable World world,
        ItemStack stack,
        int x,
        int y,
        int seed,
        int z
    ) {
        if (!stack.isEmpty()) {
            BakedModel bakedModel = C.mc
                .getItemRenderer()
                .getModel(stack, world, entity, seed);
            matrix.push();
            matrix.translate(
                (float) (x + 8),
                (float) (y + 8),
                (float) (150 + (bakedModel.hasDepth() ? z : 0))
            );

            try {
                matrix.multiplyPositionMatrix(
                    (new Matrix4f()).scaling(1.0F, -1.0F, 1.0F)
                );
                matrix.scale(16.0F, 16.0F, 16.0F);
                /*
                if (!bakedModel.isSideLit()) DiffuseLighting.disableGuiDepthLighting();
                 */

                DiffuseLighting.disableGuiDepthLighting();
                C.mc
                    .getItemRenderer()
                    .renderItem(
                        stack,
                        ModelTransformationMode.GUI,
                        false,
                        matrix,
                        currentContext.getVertexConsumers(),
                        15728880,
                        OverlayTexture.DEFAULT_UV,
                        bakedModel
                    );
                draw();
                //if (!bakedModel.isSideLit()) DiffuseLighting.enableGuiDepthLighting();
            } catch (Throwable var12) {
                var12.printStackTrace();
            }

            matrix.pop();
        }
    }

    public static Color[] getColorsFade(
        double y,
        double h,
        double fadeCentre,
        Color[] colors,
        double fadeSpeed
    ) {
        // un-negative the y
        if (y < 0) y *= -1;
        if (h < 0) h *= -1;

        double fadeNumber =
            (System.currentTimeMillis() * fadeSpeed * 0.01 +
                y * 0.01 * colors.length) %
            1;
        double fadeNumber2 =
            (System.currentTimeMillis() * fadeSpeed * 0.01 + y + h * 0.1) % 1;
        int num1 = (int) ((System.currentTimeMillis() * fadeSpeed * 0.01 +
                y * 0.01 * colors.length) %
            colors.length);
        int num2 = (int) ((num1 + 1) % colors.length);

        // get colors
        int colorRedStart = colors[num1].getRed();
        int colorGreenStart = colors[num1].getGreen();
        int colorBlueStart = colors[num1].getBlue();
        int colorOpacityStart = colors[num1].getAlpha();

        int colorRedEnd = colors[num2].getRed();
        int colorGreenEnd = colors[num2].getGreen();
        int colorBlueEnd = colors[num2].getBlue();
        int colorOpacityEnd = colors[num2].getAlpha();

        // get dif from start to end
        int redDif = colorRedEnd - colorRedStart;
        int greenDif = colorGreenEnd - colorGreenStart;
        int blueDif = colorBlueEnd - colorBlueStart;
        int opacityDif = colorOpacityEnd - colorOpacityStart;

        // start color + (difference between start n end · percent down screen)
        int redNum1 = (int) (colorRedStart + (redDif * fadeNumber));
        int redNum2 = (int) (colorRedStart + (redDif * fadeNumber2));

        int greenNum1 = (int) (colorGreenStart + (greenDif * fadeNumber));
        int greenNum2 = (int) (colorGreenStart + (greenDif * fadeNumber2));

        int blueNum1 = (int) (colorBlueStart + (blueDif * fadeNumber));
        int blueNum2 = (int) (colorBlueStart + (blueDif * fadeNumber2));

        int opacityNum1 = (int) (colorOpacityStart + (opacityDif * fadeNumber));
        int opacityNum2 = (int) (colorOpacityStart +
            (opacityDif * fadeNumber2));

        return new Color[] {
            new Color(redNum1, greenNum1, blueNum1, opacityNum1),
            new Color(redNum2, greenNum2, blueNum2, opacityNum2),
        };
    }

    public static void drawHead(
        ClientPlayerEntity e,
        int x,
        int y,
        int w,
        int h
    ) {
        Identifier texture = e.getSkinTextures().texture();
        currentContext.drawTexture(
            texture,
            x,
            y,
            w,
            h,
            8.0f,
            8.0f,
            8,
            8,
            64,
            64
        );
    }

    public static void drawHead(
        Identifier texture,
        int x,
        int y,
        int w,
        int h
    ) {
        currentContext.drawTexture(
            texture,
            x,
            y,
            w,
            h,
            8.0f,
            8.0f,
            8,
            8,
            64,
            64
        );
    }

    public static String getUnhiddenName() {
        String name = C.p().getName().getLiteralString();
        if (name == null) return "";
        return name.charAt(0) + "§r" + name.substring(1);
    }

    public static void setContext(DrawContext c) {
        currentContext = c;
    }

    public static void drawTexture(
        Identifier texture,
        int x,
        int y,
        int width,
        int height,
        float u,
        float v,
        int regionWidth,
        int regionHeight,
        int textureWidth,
        int textureHeight
    ) {
        drawTexture(
            texture,
            x,
            x + width,
            y,
            y + height,
            0,
            regionWidth,
            regionHeight,
            u,
            v,
            textureWidth,
            textureHeight
        );
    }

    public static void drawTexture(
        Identifier texture,
        int x,
        int y,
        int z,
        float u,
        float v,
        int width,
        int height,
        int textureWidth,
        int textureHeight
    ) {
        drawTexture(
            texture,
            x,
            x + width,
            y,
            y + height,
            z,
            width,
            height,
            u,
            v,
            textureWidth,
            textureHeight
        );
    }

    public static void drawArrow(
        float x,
        float y,
        float radius,
        Entity entity,
        Color color
    ) {
        if (currentContext == null) return;

        MatrixStack matrixStack = currentContext.getMatrices();
        startGL11(matrixStack);

        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        RenderSystem.setShader(GameRenderer::getPositionProgram);

        RenderSystem.setShaderColor(
            color.getRed() / 255f,
            color.getGreen() / 255f,
            color.getBlue() / 255f,
            color.getAlpha() / 255f
        );

        bufferBuilder.begin(
            VertexFormat.DrawMode.QUADS,
            VertexFormats.POSITION
        );

        matrixStack.translate(x, y, 0);

        Vec2f neededRot = RotationUtil.getRotation(entity);
        float fov = RotationUtil.getAngleDifference(
            C.p().getYaw(),
            neededRot.y
        );
        float fovPitch = RotationUtil.getAngleDifference(
            C.p().getPitch(),
            neededRot.x
        );

        matrixStack.scale(2, 2, 0);

        if (
            !Pointers.onlyIfOutOfFov ||
            fov < -C.mc.options.getFov().getValue() / 1.5f ||
            fov > C.mc.options.getFov().getValue() / 1.5f ||
            fovPitch > 40 ||
            fovPitch < -40
        ) {
            // not sure what this number is but it works..? (randomly picked numbers until it stopped jumping across the screen)
            fov /= 57.5f;

            matrixStack.multiply(RotationAxis.NEGATIVE_Z.rotation(fov));

            float distanceScale = C.p().distanceTo(entity);
            distanceScale = 1 / distanceScale;
            distanceScale *= 5;

            distanceScale = Math.max(Math.min(1f, distanceScale), 0.1f);

            if (!Pointers.constantScale) matrixStack.scale(
                distanceScale,
                distanceScale,
                0
            );
            else matrixStack.scale(0.2f, 0.2f, 0);

            if (Pointers.constantCircle) radius /= distanceScale;

            // draws the arrow at the peak of the radius
            bufferBuilder.vertex(matrix, 0, -radius, 0).next();
            bufferBuilder.vertex(matrix, -10, -radius + 20, 0).next();
            bufferBuilder.vertex(matrix, 0, -radius + 15, 0).next();
            bufferBuilder.vertex(matrix, 10, -radius + 20, 0).next();
        }

        tessellator.draw();

        endGL11(matrixStack);
    }
}
