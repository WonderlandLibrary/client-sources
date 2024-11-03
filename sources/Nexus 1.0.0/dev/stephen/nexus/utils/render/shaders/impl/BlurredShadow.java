package dev.stephen.nexus.utils.render.shaders.impl;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.stephen.nexus.utils.render.shaders.ShaderUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.awt.image.BufferedImage;
import java.util.Objects;

public class BlurredShadow {
    Texture id;

    public BlurredShadow(BufferedImage bufferedImage) {
        this.id = new Texture("texture/remote/" + RandomStringUtils.randomAlphanumeric(16));
        ShaderUtils.registerBufferedImageTexture(id, bufferedImage);
    }

    public void bind() {
        RenderSystem.setShaderTexture(0, id.getId());
    }
}

final class Rectangle {
    private final float x;
    private final float y;
    private final float x1;
    private final float y1;

    Rectangle(float x, float y, float x1, float y1) {
        this.x = x;
        this.y = y;
        this.x1 = x1;
        this.y1 = y1;
    }

    public boolean contains(double x, double y) {
        return x >= this.x && x <= x1 && y >= this.y && y <= y1;
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    public float x1() {
        return x1;
    }

    public float y1() {
        return y1;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Rectangle) obj;
        return Float.floatToIntBits(this.x) == Float.floatToIntBits(that.x) &&
                Float.floatToIntBits(this.y) == Float.floatToIntBits(that.y) &&
                Float.floatToIntBits(this.x1) == Float.floatToIntBits(that.x1) &&
                Float.floatToIntBits(this.y1) == Float.floatToIntBits(that.y1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, x1, y1);
    }

    @Override
    public String toString() {
        return "Rectangle[" +
                "x=" + x + ", " +
                "y=" + y + ", " +
                "x1=" + x1 + ", " +
                "y1=" + y1 + ']';
    }

}
