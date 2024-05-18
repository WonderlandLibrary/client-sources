package me.finz0.osiris.gui.clickgui.theme.dark;

import me.finz0.osiris.gui.clickgui.base.ComponentType;
import me.finz0.osiris.gui.clickgui.theme.Theme;
import net.minecraft.client.Minecraft;

public class DarkTheme extends Theme {

    public DarkTheme() {
        super("DarkTheme");
        this.fontRenderer = Minecraft.getMinecraft().fontRenderer;
        addRenderer(ComponentType.FRAME, new DarkFrame(this));
        addRenderer(ComponentType.BUTTON, new DarkButton(this));
        addRenderer(ComponentType.SLIDER, new DarkSlider(this));
        addRenderer(ComponentType.CHECK_BUTTON, new DarkCheckButton(this));
        addRenderer(ComponentType.EXPANDING_BUTTON, new DarkExpandingButton(this));
        addRenderer(ComponentType.TEXT, new DarkText(this));
        addRenderer(ComponentType.KEYBIND, new DarkKeybinds(this));
        addRenderer(ComponentType.DROPDOWN, new DarkDropDown(this));
        addRenderer(ComponentType.COMBO_BOX, new DarkComboBox(this));
    }
}
