package ca.commencal.ware.gui.click.theme.xcc;

import ca.commencal.ware.gui.click.base.Component;
import ca.commencal.ware.gui.click.base.ComponentRenderer;
import ca.commencal.ware.gui.click.base.ComponentType;
import ca.commencal.ware.gui.click.elements.Dropdown;
import ca.commencal.ware.gui.click.theme.Theme;
import ca.commencal.ware.module.modules.render.ClickGui;


public class DarkDropDown extends ComponentRenderer {

    public DarkDropDown(Theme theme) {

        super(ComponentType.DROPDOWN, theme);
    }

    @Override
    public void drawComponent(Component component, int mouseX, int mouseY) {

        Dropdown dropdown = (Dropdown) component;
        String text = dropdown.getText();                                                                                 //was 2

        theme.fontRenderer.drawString(text, dropdown.getX() + 5, dropdown.getY() + (dropdown.getDropdownHeight() / 1 - theme.fontRenderer.FONT_HEIGHT / 4),
        		ClickGui.color);

        if (dropdown.isMaximized()) {
            dropdown.renderChildren(mouseX, mouseY);
        }
    }

    @Override
    public void doInteractions(Component component, int mouseX, int mouseY) {

    }
}
