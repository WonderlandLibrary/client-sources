/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.components;

import com.mojang.blaze3d.matrix.MatrixStack;
import mpp.venusfr.utils.math.MathUtil;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.DisplayUtils;
import mpp.venusfr.utils.render.font.Fonts;

public class FieldComponent {
    float x;
    float y;
    float width;
    float height;
    public String name;
    String input = "";
    boolean inputing;
    boolean hide = false;

    public FieldComponent(float f, float f2, float f3, float f4, String string) {
        this.x = f;
        this.y = f2;
        this.width = f3;
        this.height = f4;
        this.name = string;
    }

    public FieldComponent(float f, float f2, float f3, float f4) {
        this.x = f;
        this.y = f2;
        this.width = f3;
        this.height = f4;
        this.hide = true;
    }

    public void draw(MatrixStack matrixStack, float f, float f2) {
        if (this.hide) {
            DisplayUtils.drawRoundedRect(this.x, this.y, this.width, this.height, 1.0f, MathUtil.isHovered(f, f2, this.x, this.y, this.width, this.height) ? ColorUtils.rgba(31, 31, 31, 255) : ColorUtils.rgba(27, 27, 27, 255));
            Fonts.montserrat.drawCenteredText(matrixStack, this.input + (this.inputing ? (System.currentTimeMillis() % 1000L >= 500L ? "|" : "") : ""), this.x + 2.0f + this.width / 2.0f / 2.0f, this.y + 0.5f, -1, 5.0f);
            return;
        }
        Fonts.montserrat.drawText(matrixStack, this.name, this.x, this.y, -1, 6.0f);
        DisplayUtils.drawRoundedRect(this.x, this.y + 7.0f, this.width, this.height - 7.0f, 3.0f, MathUtil.isHovered(f, f2, this.x, this.y + 7.0f, this.width, this.height - 7.0f) ? ColorUtils.rgba(31, 31, 31, 255) : ColorUtils.rgba(27, 27, 27, 255));
        Fonts.montserrat.drawText(matrixStack, this.input + (this.inputing ? (System.currentTimeMillis() % 1000L >= 500L ? "|" : "") : ""), this.x + 2.0f, this.y + this.height / 2.0f + 1.0f, -1, 5.0f);
    }

    public void click(int n, int n2) {
        if (MathUtil.isHovered(n, n2, this.x, this.y, this.width, this.height)) {
            this.inputing = !this.inputing;
        }
    }

    public void key(int n) {
        if (this.inputing && n == 259 && !this.input.isEmpty()) {
            this.input = this.input.substring(0, this.input.length() - 1);
        }
        if (this.inputing && n == 257) {
            this.inputing = false;
        }
    }

    public String get() {
        return this.input;
    }

    public void set(String string) {
        this.input = string;
    }

    public void charTyped(char c) {
        if (this.inputing) {
            if (Fonts.montserrat.getWidth(this.input + c, 5.0f) > this.width - Fonts.montserrat.getWidth(String.valueOf(c), 5.0f)) {
                return;
            }
            this.input = this.input + c;
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

