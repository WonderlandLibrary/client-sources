package me.finz0.osiris.gui.clickgui.theme.dark;

import me.finz0.osiris.gui.clickgui.base.Component;
import me.finz0.osiris.gui.clickgui.base.ComponentRenderer;
import me.finz0.osiris.gui.clickgui.base.ComponentType;
import me.finz0.osiris.gui.clickgui.elements.Text;
import me.finz0.osiris.gui.clickgui.theme.Theme;

public class DarkText extends ComponentRenderer {

    public DarkText(Theme theme) {
        super(ComponentType.TEXT, theme);
    }

    @Override
    public void drawComponent(Component component, int mouseX, int mouseY) {
        Text text = (Text) component;
        String[] message = text.getMessage();

        int y = text.getY();

        for (String s : message) {
            theme.fontRenderer.drawString(s, text.getX() - 4, y - 4, -1);
            y += 10;
        }
    }

    @Override
    public void doInteractions(Component component, int mouseX, int mouseY) {}
}
