/*

    Code was written by MarkGG
    Any illegal distribution of this code will
    have consequences

    Vectus Client @ 2024-2025

 */
package lol.point.returnclient.ui.cgui.dropdown.comp.impl;

import lol.point.returnclient.module.Category;
import lol.point.returnclient.ui.cgui.dropdown.comp.Component;

import java.awt.*;

public class CategoryComponent extends Component {

    private final Category moduleCategory;

    public CategoryComponent(Category moduleCategory) {
        this.moduleCategory = moduleCategory;
    }

    public void drawScreen(int mouseX, int mouseY) {
        RenderUtil.rectangle(getX(), getY(), getWidth(), getHeight(), new Color(37, 37, 37));
        getFont().drawString(moduleCategory.name, getX() + 2, getY() + 4.5f, -1);
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    }

    public void keyTyped(char typedChar, int keyCode) {
    }
}
