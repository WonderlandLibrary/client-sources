package me.finz0.osiris.gui.clickgui.theme.dark;

import me.finz0.osiris.gui.clickgui.base.Component;
import me.finz0.osiris.gui.clickgui.base.ComponentRenderer;
import me.finz0.osiris.gui.clickgui.base.ComponentType;
import me.finz0.osiris.gui.clickgui.elements.CheckButton;
import me.finz0.osiris.gui.clickgui.theme.Theme;
import me.finz0.osiris.module.modules.gui.ClickGuiModule;
import me.finz0.osiris.util.ColourUtils;
import me.finz0.osiris.util.MathUtils;

public class DarkCheckButton extends ComponentRenderer {

    public DarkCheckButton(Theme theme) {

        super(ComponentType.CHECK_BUTTON, theme);
    }

    @Override
    public void drawComponent(Component component, int mouseX, int mouseY) {

        CheckButton button = (CheckButton) component;
        String text = button.getText();

        int mainColor = ClickGuiModule.isLight ? ColourUtils.color(255, 255, 255, 255) : ColourUtils.color(0, 0, 0, 255);
        int mainColorInv = ClickGuiModule.isLight ? ColourUtils.color(0, 0, 0, 255) : ColourUtils.color(255, 255, 255, 255);

        if(button.getModeSetting() == null) {
            theme.fontRenderer.drawString("> " + text, button.getX() + 5, MathUtils.getMiddle(button.getY(), button.getY() + button.getDimension().height) - theme.fontRenderer.FONT_HEIGHT / 3 - 1,
                    button.isEnabled() ? mainColorInv : ColourUtils.color(0.5f, 0.5f, 0.5f, 1.0f));
            return;
        }

        for(String mode : button.getModeSetting().getOptions()) {
            if (mode.equals(text)) {
                theme.fontRenderer.drawString("- " + text, button.getX() + 5, MathUtils.getMiddle(button.getY(), button.getY() + button.getDimension().height) - theme.fontRenderer.FONT_HEIGHT / 3 - 1,
                        (button.getModeSetting().getValString().equals(mode) ? mainColorInv : ColourUtils.color(0.5f, 0.5f, 0.5f, 1.0f)));
            }
        }
    }

    @Override
    public void doInteractions(Component component, int mouseX, int mouseY) {

    }
}
