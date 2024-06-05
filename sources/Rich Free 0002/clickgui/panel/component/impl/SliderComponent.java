/*
 * Decompiled with CFR 0.150.
 */
package clickgui.panel.component.impl;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;

import clickgui.panel.Panel;
import clickgui.panel.component.Component;
import clickgui.setting.Setting;
import me.rich.Main;
import me.rich.font.Fonts;
import me.rich.helpers.render.AnimationHelper;
import me.rich.helpers.render.ColorHelper;
import me.rich.helpers.render.RenderHelper;

public final class SliderComponent
extends Component {
    private boolean dragging = false;
    private int opacity = 120;
    private float animation = 0.0f;
    private float textHoverAnimate = 0.0f;
    private float currentValueAnimate = 0.0f;

    public SliderComponent(Setting option, Panel panel, int x, int y, int width, int height) {
        super(panel, x, y, width, height);
        this.option = option;
    }

    @Override
    public void onDraw(int mouseX, int mouseY) {
        Panel parent = this.getPanel();
        int x = parent.getX() + this.getX();
        int y = parent.getY() + this.getY() + -1;
        boolean hovered = this.isMouseOver(mouseX, mouseY);
        int height = this.getHeight();
        int width = this.getWidth();
        Setting option = this.option;
        double min = option.getMin();
        double max = option.getMax();
        if (this.dragging) {
            option.setValDouble(this.round((double)(mouseX - x) * (max - min) / (double)width + min, 0.01));
            if (Double.valueOf(option.getValDouble()) > max) {
                option.setValDouble(max);
            } else if (Double.valueOf(option.getValDouble()) < min) {
                option.setValDouble(min);
            }
        }
        double optionValue = this.round(option.getValDouble(), 0.01);
        String optionValueStr = String.valueOf(optionValue);
        int color = Color.WHITE.getRGB();
        double kak = (option.getValDouble() - option.getMin()) / (option.getMax() - option.getMin());
        this.currentValueAnimate = AnimationHelper.animation(this.currentValueAnimate, (float)kak, 1.0E-30f);
        double renderPerc = (double)(width - 2) / (max - min);
        double barWidth = renderPerc * optionValue - renderPerc * min;
        if (hovered) {
            if (this.opacity < 200) {
                this.opacity += 5;
            }
        } else if (this.opacity > 120) {
            this.opacity -= 5;
        }
        this.textHoverAnimate = AnimationHelper.animation(this.textHoverAnimate, hovered ? 2.3f : 2.0f, 1.0E-12f);
        this.animation = AnimationHelper.animation(this.animation, this.dragging ? y + height - 6 : y + height - 5, 1.0E-6f);
        RenderHelper.drawNewRect(x, y, x + width, y + height, parent.dragging ? new Color(25, 25, 25).getRGB() : ColorHelper.getColorWithOpacity(BACKGROUND, 255 - this.opacity).getRGB());
        RenderHelper.drawNewRect(x + 3.5, y + height - 3.5, x + (width - 2.5), y + height - 1.5, new Color(140, 140, 140).getRGB());
        RenderHelper.drawNewRect(x + 4, y + height - 3, x + (width - 3), y + height - 2, new Color(45, 44, 44).getRGB());
        RenderHelper.drawGradientSideways(x + 4, y + height - 3, (float)x + (float)width * this.currentValueAnimate - 2.5f, y + height - 2, Main.getClientColor().getRGB(), Main.getClientColor().getRGB());
       
        Fonts.neverlose500_14.drawStringWithShadow(option.getName().toLowerCase(), (float)x + 3.5f, (float)y + (float)height - 10f, color);
        Fonts.neverlose500_14.drawStringWithShadow(optionValueStr.toLowerCase(), x + width - Fonts.neverlose500_14.getStringWidth(optionValueStr.toLowerCase()) - 3, (float)y + (float)height - 10f, color);
    }

    @Override
    public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (this.isMouseOver(mouseX, mouseY)) {
            this.dragging = true;
        }
    }

    @Override
    public void onMouseRelease(int mouseX, int mouseY, int mouseButton) {
        this.dragging = false;
    }

    private double round(double num, double increment) {
        double v = (double)Math.round(num / increment) * increment;
        BigDecimal bd = new BigDecimal(v);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}

