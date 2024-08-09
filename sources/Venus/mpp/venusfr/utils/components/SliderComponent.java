/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.components;

import com.mojang.blaze3d.matrix.MatrixStack;
import mpp.venusfr.utils.components.FieldComponent;
import mpp.venusfr.utils.math.MathUtil;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.DisplayUtils;
import mpp.venusfr.utils.render.font.Fonts;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.math.MathHelper;

public class SliderComponent {
    float x;
    float y;
    float width;
    float height;
    public String name;
    public int min;
    public int max;
    public int current;
    public Enchantment enchantment;
    boolean drag;
    float widthSlider = 0.0f;
    public FieldComponent fieldComponent = new FieldComponent(0.0f, 0.0f, 0.0f, 0.0f);

    public SliderComponent(float f, float f2, float f3, float f4, int n, int n2, String string) {
        this.x = f;
        this.y = f2;
        this.width = f3;
        this.height = f4;
        this.name = string;
        this.min = n;
        this.max = n2;
        this.current = n - 1;
        this.fieldComponent.set(String.valueOf(this.current));
    }

    public SliderComponent(float f, float f2, float f3, float f4, int n, int n2, Enchantment enchantment, String string) {
        this.x = f;
        this.y = f2;
        this.width = f3;
        this.height = f4;
        this.name = string;
        this.min = n;
        this.max = n2;
        this.current = n - 1;
        this.enchantment = enchantment;
        this.fieldComponent.set(String.valueOf(this.current));
    }

    public void draw(MatrixStack matrixStack, float f, float f2) {
        int n = this.fieldComponent.get().isEmpty() ? 0 : Integer.parseInt(this.fieldComponent.get());
        Fonts.montserrat.drawText(matrixStack, this.name, this.x, this.y, -1, 6.0f);
        this.fieldComponent.setX(this.x + Fonts.montserrat.getWidth(this.name, 6.0f) + 5.0f);
        this.fieldComponent.setY(this.y);
        this.fieldComponent.setWidth(10.0f);
        this.fieldComponent.setHeight(6.0f);
        this.fieldComponent.draw(matrixStack, f, f2);
        float f3 = this.width * (float)(n - (this.min - 1)) / (float)(this.max - (this.min - 1));
        this.widthSlider = MathHelper.clamp(MathUtil.fast(this.widthSlider, f3, 15.0f), 0.0f, this.width);
        DisplayUtils.drawRoundedRect(this.x, this.y + 7.0f, this.width, this.height - 7.0f, 1.0f, ColorUtils.rgba(27, 27, 27, 255));
        DisplayUtils.drawRoundedRect(this.x, this.y + 7.0f, this.widthSlider, this.height - 7.0f, 1.0f, ColorUtils.getColor(0));
        if (this.drag) {
            n = (int)MathHelper.clamp(MathUtil.round((f - this.x) / this.width * (float)(this.max - (this.min - 1)) + (float)(this.min - 1), 1.0), (double)(this.min - 1), (double)this.max);
            this.fieldComponent.set(String.valueOf(n));
        }
    }

    public void click(int n, int n2) {
        if (MathUtil.isHovered(n, n2, this.x, this.y + 7.0f - 2.0f, this.width, this.height - 7.0f + 4.0f)) {
            this.drag = true;
        }
        this.fieldComponent.click(n, n2);
    }

    public void unpress() {
        this.drag = false;
    }

    public void key(int n) {
        this.fieldComponent.key(n);
    }

    public void charTyped(char c) {
        if (Character.isDigit(c)) {
            this.fieldComponent.charTyped(c);
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

