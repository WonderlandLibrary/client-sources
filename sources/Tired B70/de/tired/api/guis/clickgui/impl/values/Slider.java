package de.tired.api.guis.clickgui.impl.values;

import de.tired.api.extension.Extension;
import de.tired.api.extension.processors.extensions.generally.RenderProcessor;
import de.tired.api.guis.clickgui.setting.NumberSetting;
import de.tired.api.util.font.FontManager;
import de.tired.api.util.misc.FileUtil;
import de.tired.api.util.font.CustomFont;
import de.tired.api.util.shader.renderapi.AnimationUtil;
import de.tired.interfaces.FHook;
import de.tired.module.impl.list.visual.ClickGUI;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

public class Slider {

    public NumberSetting setting;

    private double renderWidth;

    public int WIDTH = 220, HEIGHT = 35;
    public float animationX;
    public double animation;
    public final double NORMAL_WIDTH = 120, NORMAL_HEIGHT = 15;
    private boolean dragging;
    private int x, y;

    public Slider(NumberSetting setting) {
        this.setting = setting;
    }

    public void drawSlider(int x, int y, int mouseX, int mouseY, boolean justSlider, boolean... onlyText) {
        this.x = x;
        this.y = y;
        animation = AnimationUtil.getAnimationState(animation, WIDTH, 1222);
        WIDTH = (int) animation;
        double value = (setting.getValue() - setting.getMin()) / (setting.getMax() - setting.getMin());

        if (this.dragging) {
            double valueX = (float) (mouseX) - (this.x);

            value = valueX / (float) (this.WIDTH);

            if (value < 0.0F) {
                value = 0F;
            }

            if (value > 1.0F) {
                value = 1.0F;
            }

        }

        double _value = setting.getMin() + (setting.getMax() - setting.getMin()) * value;

        double disValue = Math.round(_value / setting.getInc()) * setting.getInc();
        final double percent = this.setting.getValue() * 100 / this.setting.getMax();
        final double calcWidth = (WIDTH - 20) * percent / 100;
        this.animationX = (float) AnimationUtil.getAnimationState((double) this.animationX, calcWidth + 10, Math.max(2.6D, Math.abs((double) this.animationX - calcWidth - 10) * 2));
        setting.setValue(disValue);
     //   Gui.drawRect(this.x, this.y, this.x + this.WIDTH, this.y + this.HEIGHT, Flat1.isState() ? Integer.MIN_VALUE : new Color(20, 20, 20).getRGB());
        Gui.drawRect(x + 10, y + 12, x + WIDTH - 10, y + NORMAL_HEIGHT, new Color(30, 33, 39).getRGB());

        String name = setting.getName();
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        final String toString = String.valueOf(Math.round(disValue * 10.0) / 10.0).replace(".0", "");

        FontManager.light.drawString2(" " + name, calculateMiddle(name, FontManager.light, x, (int) WIDTH), (int) (this.y + (FontManager.light.FONT_HEIGHT / 2f) - 5), -1);

        if (isOver(x, y, WIDTH, HEIGHT, mouseX, mouseY)) {
            FontManager.light.drawString(toString, x + 11, y + 5, -1);
        }
        GlStateManager.disableBlend();

        RenderProcessor.drawGradientSideways(x + 10, y + 12, this.x + animationX, y + NORMAL_HEIGHT, ClickGUI.getInstance().colorPickerSetting.ColorPickerC.darker().getRGB(), ClickGUI.getInstance().colorPickerSetting.ColorPickerC.getRGB());

        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.renderObjectCircle(this.x + animationX, y + NORMAL_HEIGHT - 2, 4, ClickGUI.getInstance().colorPickerSetting.ColorPickerC.brighter().getRGB());

        WIDTH = 222;

    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isOver(x + 10, y, WIDTH - 10, HEIGHT, mouseX, mouseY)) {
            this.dragging = true;
            setting.dragged = true;
        }
    }

    public void mouseReleased(int mouseX, int mouseY) {
        dragging = false;
        setting.dragged = false;
        FileUtil.FILE_UTIL.saveSettings();
    }

    public boolean isOver(int x, int y, int width, int height, int mouseX, int mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

    public int calculateMiddle(String text, CustomFont fontRenderer, int x, int widht) {
        return (int) ((float) (x + widht) - (fontRenderer.getStringWidth(text) / 2f) - (float) widht / 2);
    }


}