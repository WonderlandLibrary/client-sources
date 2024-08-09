package dev.darkmoon.client.ui.csgui.component.impl;

import dev.darkmoon.client.manager.theme.Themes;
import dev.darkmoon.client.module.setting.impl.ColorSetting;
import dev.darkmoon.client.ui.csgui.component.Component;
import dev.darkmoon.client.ui.csgui.window.ColorPickerWindow;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.ui.csgui.CsGui;
import dev.darkmoon.client.utility.render.RenderUtility;
import dev.darkmoon.client.utility.render.font.Fonts;

import java.awt.*;

public class ColorComponent extends Component {
    public ModuleComponent moduleComponent;
    public ColorSetting setting;

    public ColorComponent(ModuleComponent moduleComponent, ColorSetting setting) {
        super(0, 0, 0, 14);
        this.moduleComponent = moduleComponent;
        this.setting = setting;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        super.render(mouseX, mouseY);
        Fonts.nunitoBold14.drawString(setting.getName(), x + 5, y + 5, Color.WHITE.getRGB());

        RenderUtility.drawRoundedRect(x + width - 16, y + 1.5f, 11, 11, 10, new Color(0, 0, 0).getRGB());
        RenderUtility.drawRoundedRect(x + width - 15, y + 2.5f, 9, 9, 8, new Color(0, 0, 0).getRGB());
        RenderUtility.drawRoundedRect(x + width - 14, y + 3.5f, 7, 7, 6, setting.get());
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (RenderUtility.isHovered(mouseX, mouseY, x + width - 16, y + 1.5f, 11, 11) && (CsGui.colorPicker == null || !CsGui.colorPicker.getColorSetting().equals(setting))) {
            CsGui.colorPicker = new ColorPickerWindow((float) (mouseX + 5), (float) (mouseY + 5), 80, 80, setting);
        }
    }

    @Override
    public boolean isVisible() {
        return setting.getVisible().get();
    }
}
