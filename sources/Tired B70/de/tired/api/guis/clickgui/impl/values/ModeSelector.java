package de.tired.api.guis.clickgui.impl.values;

import de.tired.api.extension.processors.extensions.generally.RenderProcessor;
import de.tired.api.guis.clickgui.impl.clickable.Clickable;
import de.tired.api.guis.clickgui.setting.ModeSetting;
import de.tired.api.util.font.FontManager;
import de.tired.api.util.render.Scissoring;
import de.tired.api.util.shader.renderapi.AnimationUtil;
import de.tired.interfaces.FHook;
import de.tired.module.impl.list.visual.ClickGUI;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

public class ModeSelector {

    public ModeSetting modeSetting;

    public int WIDTH = 120, HEIGHT = 21;

    public double animation;
    public ModeSetting set;
    private int x;
    private int y;

    public int yOffset;
    private int modeIndex;
    public boolean extended = false;
    public String[] settings;

    public ModeSelector(ModeSetting setting, String[] settings) {
        this.modeSetting = setting;
        this.set = setting;
        this.modeIndex = 0;
        this.settings = settings;
    }

    public void drawSlider(int x, int y, int mouseX, int mouseY) {
        this.x = x;
        this.y = y;
        String name = set.getName();
        name = name.substring(0, 1).toUpperCase() + name.substring(1);

        yOffset = HEIGHT;

        double heighto = extended ? this.HEIGHT : 0;
        animation = AnimationUtil.getAnimationState(animation, extended ? this.HEIGHT : 0, Math.max(12.6D, Math.abs((double) this.animation - heighto) * 8));

        for (String s : settings) {
            GlStateManager.resetColor();
            Gui.drawRect(this.x, this.y + yOffset - 8, this.x + 144, this.y + yOffset + animation - 4, new Color(30, 33, 39).getRGB());
            FontManager.light.drawString(s.split("->")[0], (float) ((this.x + this.WIDTH) - ( FontManager.light.getStringWidth(s.split("->")[0]) / 2) - this.WIDTH / 2), extended ? (float) ((this.y + animation + yOffset) - (FHook.fontRenderer.getHeight() / 2) - (this.HEIGHT / 2)) - 1 : (float) ((this.y + animation + yOffset) - (FHook.fontRenderer.getHeight() / 2) - (this.HEIGHT / 2)) + 1, s.split("->")[0].equalsIgnoreCase(this.set.getValue()) ? ClickGUI.getInstance().colorPickerSetting.ColorPickerC.getRGB() : -1);
            if (extended) {
                yOffset += animation - 4;
            }
        }

        RenderProcessor.drawRoundedRectangle(x, y + 4, 144, 15, extended ? 0 : 2, new Color(30, 33, 39).getRGB());
        GlStateManager.disableBlend();
        GlStateManager.resetColor();

        FontManager.entypo.drawString("i", x + 130, y + 5, new Color(95, 99, 104).getRGB());


        WIDTH = (int) 144;

        FontManager.light.drawString2(name + ": " + set.getValue(), (x + 2), (y + 7), -1);
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {

        if (isMouseOnButton(mouseX, mouseY) && mouseButton == 1) {
            this.extended = !this.extended;
        }
        if (isMouseOnButton(mouseX, mouseY)) {
            int yOffset = HEIGHT;
            if (extended) {
                for (String s : settings) {
                    boolean isOverSetting = (mouseX > this.x) && (mouseX < this.x + this.WIDTH) && (mouseY > this.y + yOffset - 6) && (mouseY < this.y + yOffset + 1 + animation - 4);
                    if (isOverSetting) {
                        modeSetting.setValue(s);
                    }
                    yOffset+= animation - 4;
                }
            }

        }
    }

    public boolean isMouseOnButton(int x, int y) {
        return x > this.x && x < this.x + WIDTH && y > this.y && y < this.y + yOffset;
    }
}