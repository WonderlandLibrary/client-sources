/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.clickgui;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.Vec2f;
import org.lwjgl.opengl.GL11;
import ru.govno.client.clickgui.TriangleElement;

public class TriangleGroup {
    private final Random RANDOM = new Random(12312311L);
    private final Tessellator tessellator = Tessellator.getInstance();
    private final BufferBuilder bufferBuilder = this.tessellator.getBuffer();
    private final List<TriangleElement> elements = new ArrayList<TriangleElement>();
    private float x;
    private float y;
    private float w;
    private float h;
    private float size;
    private final float colorSpeed;
    int color;
    int color2;
    int color3;
    int color4;
    long minColorDelay;
    long maxColorDelay;
    float chanceToNullColor;

    public TriangleGroup(float x, float y, float w, float h, float size, int color, int color2, int color3, int color4, float colorSpeed, long minColorDelay, long maxColorDelay, float chanceToNullColor) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.size = size;
        this.color = color;
        this.color2 = color2;
        this.color3 = color3;
        this.color4 = color4;
        this.colorSpeed = colorSpeed;
        this.minColorDelay = minColorDelay;
        this.maxColorDelay = maxColorDelay;
        this.chanceToNullColor = chanceToNullColor;
        this.startElementsList(w, h, size, colorSpeed, minColorDelay, maxColorDelay, chanceToNullColor);
    }

    public static TriangleGroup gen(float x, float y, float w, float h, float size, float colorSpeed, long minColorDelay, long maxColorDelay, float chanceToNullColor) {
        return new TriangleGroup(x, y, w, h, size, 0, 0, 0, 0, colorSpeed, minColorDelay, maxColorDelay, chanceToNullColor);
    }

    public void restartList(float x, float y, float w, float h, float size) {
        if (this.w != w || this.h != h || this.size != size) {
            this.startElementsList(w, h, size, this.colorSpeed, this.minColorDelay, this.maxColorDelay, this.chanceToNullColor);
        }
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.size = size;
    }

    public void updateElementsColors(int color, int color2, int color3, int color4) {
        this.elements.forEach(element -> element.setColors(color, color2, color3, color4));
    }

    public void updateElementsColorSpeed(float colorSpeed) {
        this.elements.forEach(element -> element.setColorSpeed(colorSpeed));
    }

    private void startElementsList(float w, float h, float size, float colorSpeed, long minColorDelay, long maxColorDelay, float chanceToNullColor) {
        int xCount = (int)(w / size);
        int yCount = (int)(h / size * 1.25f);
        if (this.elements.isEmpty() || xCount * yCount != this.elements.size() * 2 || this.size != size || this.colorSpeed != colorSpeed) {
            if (!this.elements.isEmpty()) {
                this.elements.clear();
            }
            for (int yI = 0; yI < yCount; ++yI) {
                float yOffset = (float)yI * size / 1.25f;
                float chanceY = (float)yI / (float)yCount;
                for (int xI = 0; xI < xCount; ++xI) {
                    float xOffset = (float)xI * size;
                    boolean yReverse = (yI + xI) % 2 == 0 == (xI % 2 != 0);
                    float chanceX = (float)xI / (float)xCount;
                    this.elements.add(new TriangleElement(new Vec2f(xOffset, yOffset), size, yReverse, 0, 0, 0, 0, colorSpeed, this.RANDOM, minColorDelay, maxColorDelay, chanceX, chanceY, chanceToNullColor));
                    this.elements.add(new TriangleElement(new Vec2f(xOffset + size / 2.0f, yOffset), size, !yReverse, 0, 0, 0, 0, colorSpeed, this.RANDOM, minColorDelay, maxColorDelay, chanceX, chanceY, chanceToNullColor));
                }
            }
        }
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getSize() {
        return this.size;
    }

    public float getWidth() {
        return this.w;
    }

    public float getHeight() {
        return this.h;
    }

    public void setXY(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setColors(int color, int color2, int color3, int color4) {
        this.color = color;
        this.color2 = color2;
        this.color3 = color3;
        this.color4 = color4;
        this.elements.forEach(element -> element.setColors(color, color2, color3, color4));
    }

    public int getColor() {
        return this.color;
    }

    public int getColor2() {
        return this.color2;
    }

    public int getColor3() {
        return this.color3;
    }

    public int getColor4() {
        return this.color4;
    }

    public void setSize(int size) {
        this.size = size;
        this.restartList(this.getX(), this.getY(), this.getWidth(), this.getHeight(), this.getSize());
    }

    public void drawAllInZone(float x, float y, float w, float h, float alphaPC, boolean bloom) {
        List<TriangleElement> filteredElements = this.elements.stream().filter(element -> {
            boolean isReversed = element.isReversedY();
            float size = element.getSize();
            float xPos = (isReversed ? element.getOffsetVectors().get((int)0).x : element.getOffsetVectors().get((int)2).x) - size;
            float yPos = (isReversed ? element.getOffsetVectors().get((int)2).y : element.getOffsetVectors().get((int)1).y) - size;
            return xPos >= x && yPos >= y && xPos <= x + w + size && yPos <= y + h + size;
        }).collect(Collectors.toList());
        if (!filteredElements.isEmpty()) {
            filteredElements.forEach(TriangleElement::updateElement);
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            GlStateManager.enableBlend();
            GlStateManager.disableTexture2D();
            GlStateManager.disableAlpha();
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            GL11.glLineWidth(1.0E-4f);
            GlStateManager.pushMatrix();
            GlStateManager.translate(this.getX(), this.getY(), 0.0f);
            if (bloom) {
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            }
            this.bufferBuilder.begin(4, DefaultVertexFormats.POSITION_COLOR);
            filteredElements.forEach(element -> element.getOffsetVectors().forEach(vector -> this.bufferBuilder.pos(vector.x, vector.y).color(element.getColor(alphaPC)).endVertex()));
            this.tessellator.draw();
            this.bufferBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
            filteredElements.forEach(element -> element.getOffsetVectors().forEach(vector -> this.bufferBuilder.pos(vector.x, vector.y).color(element.getColor(alphaPC)).endVertex()));
            this.tessellator.draw();
            if (bloom) {
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            }
            GlStateManager.popMatrix();
            GL11.glLineWidth(1.0f);
            GL11.glHint(3154, 4352);
            GL11.glDisable(2848);
            GlStateManager.enableAlpha();
            GlStateManager.enableTexture2D();
            GlStateManager.disableDepth();
            GlStateManager.enableDepth();
        }
    }
}

