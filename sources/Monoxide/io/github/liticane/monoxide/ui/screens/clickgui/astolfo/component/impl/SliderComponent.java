package io.github.liticane.monoxide.ui.screens.clickgui.astolfo.component.impl;

import io.github.liticane.monoxide.util.render.font.FontStorage;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.MathHelper;
import org.lwjglx.input.Mouse;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.value.Value;
import io.github.liticane.monoxide.value.impl.NumberValue;
import io.github.liticane.monoxide.util.math.MathUtil;
import io.github.liticane.monoxide.util.render.RenderUtil;
import io.github.liticane.monoxide.util.render.color.ColorUtil;

import java.awt.*;

public class SliderComponent extends ValueComponent {

    private boolean expanded = false;

    public SliderComponent(Value value, float posX, float posY, float baseWidth, float baseHeight) {
        super(value, posX, posY, baseWidth, baseHeight);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        FontRenderer normal = FontStorage.getInstance().findFont("SFUI Medium", 16);
        normal.drawString(value.getName(), getPosX() + 5 + getAddX(), getPosY() + getBaseHeight() / 2 - normal.FONT_HEIGHT / 2, -1);
        normal.drawString(((Number)value.getValue()).floatValue() + "", getPosX() + this.getBaseWidth() - 5 - normal.getStringWidthInt(((Number)value.getValue()).floatValue() + "") + getAddX(), getPosY() + getBaseHeight() / 2 - normal.FONT_HEIGHT / 2, -1);
        if(this.expanded) {
            NumberValue numberValue = (NumberValue) value;
            String min = String.valueOf(numberValue.getMinimum().floatValue());
            String max = String.valueOf(numberValue.getMaximum().floatValue());
            normal.drawString(min, getPosX() + 5 + getAddX(), getPosY() + getBaseHeight() + getBaseHeight() / 2 - normal.FONT_HEIGHT / 2, -1);
            normal.drawString(max, getPosX() + this.getBaseWidth() - 5 - normal.getStringWidthInt(max) + getAddX(), getPosY() + getBaseHeight() + getBaseHeight() / 2 - normal.FONT_HEIGHT / 2, -1);
            float sliderX = getPosX() + 5 + normal.getStringWidthInt(min) + 3 + getAddX();
            float sliderWidth = getBaseWidth() - (5 + normal.getStringWidthInt(min) + 3) - (5 + normal.getStringWidthInt(max) + 3);
            float sliderY = getPosY() + getBaseHeight() + getBaseHeight() / 4 - 1;
            float sliderHeight = getBaseHeight() / 4 * 2;
            RenderUtil.drawRect(sliderX, sliderY, sliderWidth, sliderHeight, new Color(0, 0, 0, 50).getRGB());
            float length = MathHelper
                    .floor_double(((numberValue.getValue()).floatValue() - numberValue.getMinimum().floatValue())
                            / (numberValue.getMaximum().floatValue() - numberValue.getMinimum().floatValue()) * sliderWidth);
            RenderUtil.drawRect(sliderX, sliderY, length, sliderHeight, ColorUtil.getAstolfoColor(getModuleCategory(value.getOwner())));
            if(Mouse.isButtonDown(0) && RenderUtil.isHovered(mouseX, mouseY, this.getPosX() + getAddX(), this.getPosY() + this.getBaseHeight(), this.getBaseWidth(), this.getBaseHeight())) {
                double min1 = numberValue.getMinimum().floatValue();
                double max1 = numberValue.getMaximum().floatValue();
                double newValue = MathUtil.round((mouseX - sliderX) * (max1 - min1) / (sliderWidth - 1.0f) + min1, numberValue.getDecimals());
                numberValue.setValue(newValue);
            }
        }
    }

    @Override
    public float getFinalHeight() {
        return this.expanded ? this.getBaseHeight() * 2 : this.getBaseHeight();
    }

    @Override
    public void mouseClick(int mouseX, int mouseY, int mouseButton) {
        if(RenderUtil.isHovered(mouseX, mouseY, this.getPosX(), this.getPosY(), this.getBaseWidth(), this.getBaseHeight())) {
            expanded = !expanded;
        }
    }

    private ModuleCategory getModuleCategory(Object owner) {
        if (owner instanceof Module) {
            return ((Module) owner).getCategory();
        }
        return ModuleCategory.HUD;
    }
}
