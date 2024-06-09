package me.finz0.osiris.gui.clickgui.theme.dark;

import me.finz0.osiris.gui.clickgui.base.Component;
import me.finz0.osiris.gui.clickgui.base.ComponentRenderer;
import me.finz0.osiris.gui.clickgui.base.ComponentType;
import me.finz0.osiris.gui.clickgui.elements.Slider;
import me.finz0.osiris.gui.clickgui.theme.Theme;
import me.finz0.osiris.module.modules.gui.ClickGuiModule;
import me.finz0.osiris.util.ColourUtils;
import me.finz0.osiris.util.OsirisTessellator;
import net.minecraft.client.gui.Gui;

public class DarkSlider extends ComponentRenderer {

    public DarkSlider(Theme theme) {

        super(ComponentType.SLIDER, theme);
    }

    @Override
    public void drawComponent(Component component, int mouseX, int mouseY) {

        Slider slider = (Slider) component;
        int width = (int) ((slider.getDimension().getWidth()) * slider.getPercent());

        int mainColor = ClickGuiModule.isLight ? ColourUtils.color(255, 255, 255, 255) : ColourUtils.color(0, 0, 0, 255);
        int mainColorInv = ClickGuiModule.isLight ? ColourUtils.color(0, 0, 0, 255) : ColourUtils.color(255, 255, 255, 255);

        //GLUtils.glColor(ColorUtils.color(1.0f, 1.0f, 1.0f, 1.0f));

        theme.fontRenderer.drawString(slider.getText(), slider.getX() + 4, slider.getY() + 2,
                ColourUtils.color(0.5f, 0.5f, 0.5f, 1.0f));

        theme.fontRenderer.drawString(slider.getValue() + "", slider.getX() + slider.getDimension().width - theme.fontRenderer.getStringWidth(slider.getValue() + "") - 2, slider.getY() + 2,
                mainColorInv);

        OsirisTessellator.drawRect(slider.getX(), slider.getY() + slider.getDimension().height / 2 + 3, slider.getX() + (width) + 3, (slider.getY() + slider.getDimension().height / 2) + 6,
                mainColorInv);

        OsirisTessellator.drawRect(slider.getX(), slider.getY() + slider.getDimension().height / 2 + 3, slider.getX() + (width), (slider.getY() + slider.getDimension().height / 2) + 6,
                ClickGuiModule.getColor());
    }

    @Override
    public void doInteractions(Component component, int mouseX, int mouseY) {

    }
}
