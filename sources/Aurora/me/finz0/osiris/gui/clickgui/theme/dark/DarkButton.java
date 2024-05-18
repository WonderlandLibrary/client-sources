package me.finz0.osiris.gui.clickgui.theme.dark;

import me.finz0.osiris.gui.clickgui.base.Component;
import me.finz0.osiris.gui.clickgui.base.ComponentRenderer;
import me.finz0.osiris.gui.clickgui.base.ComponentType;
import me.finz0.osiris.gui.clickgui.elements.Button;
import me.finz0.osiris.gui.clickgui.theme.Theme;
import me.finz0.osiris.util.ColourUtils;
import me.finz0.osiris.util.GLUtils;
import me.finz0.osiris.util.OsirisTessellator;

public class DarkButton extends ComponentRenderer {

    public DarkButton(Theme theme) {

        super(ComponentType.BUTTON, theme);
    }

    @Override
    public void drawComponent(Component component, int mouseX, int mouseY) {

        Button button = (Button) component;
        String text = button.getText();
        int color = ColourUtils.color(50, 50, 50, 100);
        int enable = ColourUtils.color(255, 255, 255, 255);

        if (GLUtils.isHovered(button.getX(), button.getY(), button.getDimension().width, button.getDimension().height, mouseX, mouseY)) {
            color = ColourUtils.color(70, 70, 70, 255);
        }

        if (button.isEnabled()) {
            OsirisTessellator.drawRect(button.getX(), button.getY(), button.getX() + button.getDimension().width - 1, button.getY() + button.getDimension().height, enable);
        } else {
            OsirisTessellator.drawRect(button.getX(), button.getY(), button.getX() + button.getDimension().width - 1, button.getY() + button.getDimension().height, color);
        }

        theme.fontRenderer.drawString(text, button.getX() + 5, button.getY() + (button.getDimension().height / 2 - theme.fontRenderer.FONT_HEIGHT / 4), ColourUtils.color(255, 255, 255, 255));
    }

    @Override
    public void doInteractions(Component component, int mouseX, int mouseY) {}
}
