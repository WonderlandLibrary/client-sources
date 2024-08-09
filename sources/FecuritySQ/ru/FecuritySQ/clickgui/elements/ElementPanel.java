package ru.FecuritySQ.clickgui.elements;
import ru.FecuritySQ.clickgui.UIPanel;
import ru.FecuritySQ.font.Fonts;
import ru.FecuritySQ.module.дисплей.ClickGui;

import java.awt.*;

public class ElementPanel extends Element {

    public UIPanel panel;

    public ElementPanel(UIPanel panel, float width, float height) {
        this.panel = panel;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(int mouseX, int mouseY) {

        Fonts.GREYCLIFF.drawCenteredString(stack, ((String.valueOf(panel.type.name().substring(0, 1)) + panel.type.name().substring(1,panel.type.name().length()).toLowerCase())), getX() + this.getWidth() /2 , getY() + 3, panel.visible ? ClickGui.color.get().getRGB() : Color.GRAY.getRGB());
    }

    @Override
    public void mouseClicked(int x, int y, int button) {
        if(collided(x, y)){
            panel.visible = !panel.visible;
        }
        super.mouseClicked(x, y, button);
    }
}
