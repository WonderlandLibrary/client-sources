package ca.commencal.ware.gui.click.theme.xcc;

import ca.commencal.ware.gui.click.base.ComponentType;
import ca.commencal.ware.gui.click.theme.Theme;
import ca.commencal.ware.utils.system.Wrapper;

public class DarkTheme extends Theme {

    public DarkTheme() {
        super("Dark");
        this.fontRenderer = Wrapper.INSTANCE.fontRenderer();
        addRenderer(ComponentType.FRAME, new DarkFrame(this));
        addRenderer(ComponentType.BUTTON, new XccButton(this));
        addRenderer(ComponentType.SLIDER, new DarkSlider(this));
        addRenderer(ComponentType.CHECK_BUTTON, new DarkCheckButton(this));
        addRenderer(ComponentType.EXPANDING_BUTTON, new DarkExpandingButton(this));
        addRenderer(ComponentType.TEXT, new DarkText(this));
        addRenderer(ComponentType.KEYBIND, new DarkKeybinds(this));
        addRenderer(ComponentType.DROPDOWN, new DarkDropDown(this));
        addRenderer(ComponentType.COMBO_BOX, new DarkComboBox(this));
    }
}
