/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.components;

import com.mojang.blaze3d.matrix.MatrixStack;
import mpp.venusfr.utils.math.MathUtil;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.DisplayUtils;
import mpp.venusfr.utils.render.font.Fonts;

public class ButtonComponent {
    float x;
    float y;
    float width;
    float height;
    public String name;
    Runnable action;

    public ButtonComponent(float f, float f2, float f3, float f4, String string, Runnable runnable) {
        this.x = f;
        this.y = f2;
        this.width = f3;
        this.height = f4;
        this.name = string;
        this.action = runnable;
    }

    public void draw(MatrixStack matrixStack, float f, float f2) {
        DisplayUtils.drawRoundedRect(this.x, this.y, this.width, this.height, 3.0f, ColorUtils.rgba(27, 27, 27, 255));
        Fonts.montserrat.drawCenteredText(matrixStack, this.name, this.x + this.width / 2.0f, this.y + this.height / 2.0f - 3.0f, -1, 6.0f);
    }

    public void click(int n, int n2) {
        if (MathUtil.isHovered(n, n2, this.x, this.y, this.width, this.height)) {
            this.action.run();
        }
    }

    public void setX(float f) {
        this.x = f;
    }

    public void setY(float f) {
        this.y = f;
    }

    public void setWidth(float f) {
        this.width = f;
    }

    public void setHeight(float f) {
        this.height = f;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }
}

