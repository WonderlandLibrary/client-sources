package pw.latematt.xiv.ui.clickgui.panel.panels;

import pw.latematt.xiv.XIV;
import pw.latematt.xiv.ui.clickgui.element.elements.ThemeButton;
import pw.latematt.xiv.ui.clickgui.panel.Panel;
import pw.latematt.xiv.ui.clickgui.theme.ClickTheme;

import java.util.ArrayList;

/**
 * @author Matthew
 */
public class ThemePanel extends Panel {
    public ThemePanel(float x, float y) {
        super("Themes", new ArrayList<>(), x, y, 20, 10);

        float elementY = 4;
        for (ClickTheme theme : XIV.getInstance().getGuiClick().getThemes()) {
            getElements().add(new ThemeButton(theme, x + 2, elementY + 2, XIV.getInstance().getGuiClick().getTheme().getElementWidth(), XIV.getInstance().getGuiClick().getTheme().getElementHeight()));
            elementY += XIV.getInstance().getGuiClick().getTheme().getElementHeight() + 1;
        }
    }
}
