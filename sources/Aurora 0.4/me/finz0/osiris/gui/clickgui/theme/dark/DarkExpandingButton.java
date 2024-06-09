package me.finz0.osiris.gui.clickgui.theme.dark;

import me.finz0.osiris.gui.clickgui.base.Component;
import me.finz0.osiris.gui.clickgui.base.ComponentRenderer;
import me.finz0.osiris.gui.clickgui.base.ComponentType;
import me.finz0.osiris.gui.clickgui.elements.ExpandingButton;
import me.finz0.osiris.gui.clickgui.theme.Theme;
import me.finz0.osiris.module.modules.gui.ClickGuiModule;
import me.finz0.osiris.util.ColourUtils;
import me.finz0.osiris.util.GLUtils;
import me.finz0.osiris.util.OsirisTessellator;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class DarkExpandingButton extends ComponentRenderer {

    public DarkExpandingButton(Theme theme) {

        super(ComponentType.EXPANDING_BUTTON, theme);
    }

    @Override
    public void drawComponent(Component component, int mouseX, int mouseY) {

        ExpandingButton button = (ExpandingButton) component;
        String text = button.getText();

        int mainColor = ClickGuiModule.isLight ? ColourUtils.color(255, 255, 255, 255) : ColourUtils.color(0, 0, 0, 255);
        int mainColorInv = ClickGuiModule.isLight ? ColourUtils.color(0, 0, 0, 255) : ColourUtils.color(255, 255, 255, 255);

        if (GLUtils.isHovered(button.getX(), button.getY(), button.getDimension().width, 14, mouseX, mouseY)) {
            OsirisTessellator.drawRect(button.getX(), button.getY() + button.getButtonHeight() - 1, button.getX() + button.getDimension().width, button.getY() + button.getButtonHeight(), ClickGuiModule.getColor());
        }

        if (button.isEnabled()) {
            OsirisTessellator.drawRect(button.getX(), button.getY(), button.getX() + button.getDimension().width - 1, button.getY() + 14,
                    mainColor);
            OsirisTessellator.drawRect(button.getX(), button.getY(), button.getX() + button.getDimension().width - 95, button.getY() + 14,
                    ClickGuiModule.getColor());
            theme.fontRenderer.drawString(text, button.getX() + button.getDimension().width / 2 - theme.fontRenderer.getStringWidth(text) / 2, button.getY() + (button.getButtonHeight() / 2 - theme.fontRenderer.FONT_HEIGHT / 4) - 1,
                    ClickGuiModule.getColor());
        } else {
            OsirisTessellator.drawRect(button.getX(), button.getY(), button.getX() + button.getDimension().width - 1, button.getY() + 14,
                    mainColor);
            theme.fontRenderer.drawString(text, button.getX() + button.getDimension().width / 2 - theme.fontRenderer.getStringWidth(text) / 2, button.getY() + (button.getButtonHeight() / 2 - theme.fontRenderer.FONT_HEIGHT / 4) - 1,
                    mainColorInv);
        }

        if (button.isMaximized()) {
            OsirisTessellator.drawRect(button.getX(), button.getY() + button.getButtonHeight() - 1, button.getX() + button.getDimension().width, button.getY() + button.getButtonHeight(), ClickGuiModule.getColor());
            OsirisTessellator.drawRect(button.getX(), button.getY() + button.getDimension().height - 1, button.getX() + button.getDimension().width, button.getY() + button.getDimension().height, ClickGuiModule.getColor());
        }

        if (!button.isMaximized()) {
            drawExpanded(button.getX() + button.getDimension().width - 15, button.getY() + 3, 13, false, new Color(255, 255, 255, 255).hashCode());
        } else {
            drawExpanded(button.getX() + button.getDimension().width - 15, button.getY() + 3, 13, true, new Color(255, 255, 255, 255).hashCode());
        }

        if (button.isMaximized()) {
            button.renderChildren(mouseX, mouseY);
        }
    }

    @Override
    public void doInteractions(Component component, int mouseX, int mouseY) {

    }
}
