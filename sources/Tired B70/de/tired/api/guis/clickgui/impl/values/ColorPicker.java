package de.tired.api.guis.clickgui.impl.values;

import de.tired.api.guis.clickgui.setting.impl.ColorPickerSetting;
import de.tired.api.util.misc.FileUtil;
import de.tired.api.util.shader.renderapi.AnimationUtil;
import de.tired.interfaces.FHook;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

public class ColorPicker {

    private int x;
    private int y;
    public static int HEIGHT = 15;
    public static int WIDTH = 120;
    public final ColorPickerSetting s;
    public final de.tired.api.util.shader.renderapi.ColorPicker COLORPICKER;
    public boolean extended = false;
    public double animationCoolski;
    public double animation;
    public ColorPicker(ColorPickerSetting s) {
        this.s = s;
        this.COLORPICKER = new de.tired.api.util.shader.renderapi.ColorPicker(de.tired.api.util.shader.renderapi.ColorPicker.Type.QUAD, s);
    }

    public void draw(int x, int y, int mouseX, int mouseY) {
        this.x = x;
        this.y = y;
        animation = AnimationUtil.getAnimationState(animation, WIDTH, Math.max(3.6D, Math.abs((double) animation - WIDTH)) * 2);
        WIDTH = (int) animation;

        float amongus =  extended ? 20 : 0;
        animationCoolski = AnimationUtil.getAnimationState(animationCoolski, amongus, Math.max(3.6D, Math.abs((double) animationCoolski - amongus)) * 4);
        if (this.extended) {
            HEIGHT = (int) (80 + animationCoolski);
        } else {
            HEIGHT = 15;
        }

        if (!this.extended) {
            Gui.drawRect(this.x, this.y, this.x + WIDTH, this.y + 15, new Color(20, 20, 20).getRGB());
        }

        extended = true;

        WIDTH = COLORPICKER.getWidth() + 100;

        Gui.drawRect(x, y, this.x + WIDTH, this.y + 15 + 80 + animationCoolski, new Color(20, 20, 20).getRGB());
        this.COLORPICKER.draw((int) (x + 5), (int) (y + animationCoolski), 120, 90, mouseX, mouseY, s.ColorPickerC);
        //Gui.drawRect(x + WIDTH - 25, y + animationCoolski, x + WIDTH - 2, y + 15 + 30, s.ColorPickerC.getRGB());
     //   FHook.github.drawStringWithShadow2("" + s.ColorPickerC.getRed() + ":" + s.ColorPickerC.getGreen() + ":" + s.ColorPickerC.getBlue(), x + WIDTH - 25, y + animationCoolski, -1);
        FileUtil.FILE_UTIL.saveColor();

        GlStateManager.resetColor();
        FHook.fontRenderer2.drawString(this.s.getName().split("->")[0], (int) ((float) (x + WIDTH) - (FHook.fontRenderer2.getStringWidth(this.s.getName().split("->")[0]) / 2f) - (float) WIDTH / 2), this.extended ? (float) (this.y + 15 - FHook.fontRenderer3.getHeight() / 2 - 15 / 2 + 1) : (float) (this.y + 15 - FHook.fontRenderer3.getHeight() / 2 - 15 / 2 - 1), -1);

    }

    public void handleInput(char typedChar, int keyCode) {
        COLORPICKER.handleInput(typedChar, keyCode);
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        boolean isOver = mouseX > this.x && mouseX < this.x + 90 && mouseY > this.y && mouseY < this.y + 15;
        if (isOver && mouseButton == 1) {
            this.extended = !this.extended;
        }

        COLORPICKER.handleClick(mouseX, mouseY, mouseButton);
    }
}