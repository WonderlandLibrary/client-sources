package ca.commencal.ware.gui.click.theme.dark;

import ca.commencal.ware.gui.click.base.Component;
import ca.commencal.ware.gui.click.base.ComponentRenderer;
import ca.commencal.ware.gui.click.base.ComponentType;
import ca.commencal.ware.gui.click.elements.ExpandingButton;
import ca.commencal.ware.gui.click.theme.Theme;
import ca.commencal.ware.module.modules.render.ClickGui;
import ca.commencal.ware.utils.visual.ColorUtils;
import ca.commencal.ware.utils.visual.GLUtils;
import ca.commencal.ware.utils.visual.RenderUtils;

import java.awt.*;

public class DarkExpandingButton extends ComponentRenderer {

    public DarkExpandingButton(Theme theme) {

        super(ComponentType.EXPANDING_BUTTON, theme);
    }

    @Override
    public void drawComponent(Component component, int mouseX, int mouseY) {

        ExpandingButton button = (ExpandingButton) component;
        String text = button.getText();
        Color colorButtonIsDisabled = new Color(0, 0, 0, 150);
        Color colorButtonIsEnabled = new Color(0, 0, 0, 150);
        
        int colorStringIsEnabled = ClickGui.color;
        int colorStringIsDisabled = ColorUtils.color(1.0f, 1.0f, 1.0f, 1.0f);
        
        if (GLUtils.isHovered(button.getX(), button.getY(), button.getDimension().width, 14, mouseX, mouseY)) {
            RenderUtils.drawRect(button.getX(), button.getY() + button.getButtonHeight() - 1, button.getX() + button.getDimension().width, button.getY() + button.getButtonHeight(), ClickGui.color);
        }
        
        if (button.isEnabled()) {
            RenderUtils.drawRect(button.getX(), button.getY(), button.getX() + button.getDimension().width - 1, button.getY() + 14, 
            		colorButtonIsEnabled);
            theme.fontRenderer.drawString(text, button.getX() + 5, button.getY() + (button.getButtonHeight() / 2 - theme.fontRenderer.FONT_HEIGHT / 4) - 1, 
            		colorStringIsEnabled);
        } 
        else 
        {
            RenderUtils.drawRect(button.getX(), button.getY(), button.getX() + button.getDimension().width - 1, button.getY() + 14, 
            		colorButtonIsDisabled);
            theme.fontRenderer.drawString(text, button.getX() + 5, button.getY() + (button.getButtonHeight() / 2 - theme.fontRenderer.FONT_HEIGHT / 4) - 1, 
            		colorStringIsDisabled);
        }

        if (button.isMaximized()) {
            RenderUtils.drawRect(button.getX(), button.getY() + button.getButtonHeight() - 1, button.getX() + button.getDimension().width, button.getY() + button.getButtonHeight(), ClickGui.color);
            RenderUtils.drawRect(button.getX(), button.getY() + button.getDimension().height - 1, button.getX() + button.getDimension().width, button.getY() + button.getDimension().height, ClickGui.color);
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
