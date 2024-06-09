package ca.commencal.ware.gui.click.theme.dark;

import ca.commencal.ware.gui.click.base.Component;
import ca.commencal.ware.gui.click.base.ComponentRenderer;
import ca.commencal.ware.gui.click.base.ComponentType;
import ca.commencal.ware.gui.click.elements.CheckButton;
import ca.commencal.ware.gui.click.theme.Theme;
import ca.commencal.ware.utils.MathUtils;
import ca.commencal.ware.utils.visual.ColorUtils;
import ca.commencal.ware.value.Mode;

public class DarkCheckButton extends ComponentRenderer {

    public DarkCheckButton(Theme theme) {

        super(ComponentType.CHECK_BUTTON, theme);
    }

    @Override
    public void drawComponent(Component component, int mouseX, int mouseY) {

        CheckButton button = (CheckButton) component;
        String text = button.getText();
        
        int colorStringIsEnabled = ColorUtils.color(1.0f, 1.0f, 1.0f, 1.0f);
        int colorStringIsDisabled = ColorUtils.color(0.5f, 0.5f, 0.5f, 1.0f);
        
        if(button.getModeValue() == null) {
        	theme.fontRenderer.drawString("> " + text, button.getX() + 5, MathUtils.getMiddle(button.getY(), button.getY() + button.getDimension().height) - theme.fontRenderer.FONT_HEIGHT / 3 - 1, 
        			button.isEnabled() ? colorStringIsEnabled : colorStringIsDisabled);
        	return;
        }
        
        for(Mode mode : button.getModeValue().getModes()) {
    		if(mode.getName().equals(text)) {
    			theme.fontRenderer.drawString("- " + text, button.getX() + 5, MathUtils.getMiddle(button.getY(), button.getY() + button.getDimension().height) - theme.fontRenderer.FONT_HEIGHT / 3 - 1, 
    					mode.isToggled() ? colorStringIsEnabled : colorStringIsDisabled);
    		}
        }
    }

    @Override
    public void doInteractions(Component component, int mouseX, int mouseY) {

    }
}
