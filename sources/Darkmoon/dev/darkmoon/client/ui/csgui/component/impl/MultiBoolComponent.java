package dev.darkmoon.client.ui.csgui.component.impl;

import dev.darkmoon.client.manager.theme.Themes;
import dev.darkmoon.client.module.setting.impl.MultiBooleanSetting;
import dev.darkmoon.client.ui.csgui.component.Component;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.utility.render.RenderUtility;
import dev.darkmoon.client.utility.render.font.Fonts;

import java.awt.*;
import java.util.Comparator;
import java.util.stream.Collectors;

public class MultiBoolComponent extends Component {
    public ModuleComponent moduleComponent;
    public MultiBooleanSetting setting;

    public MultiBoolComponent(ModuleComponent moduleComponent, MultiBooleanSetting setting) {
        super(0, 0, 0, 18);
        this.moduleComponent = moduleComponent;
        this.setting = setting;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        super.render(mouseX, mouseY);
        int color = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0].getRGB();
        int color2 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1].getRGB();
        Fonts.nunitoBold14.drawString(setting.getName(), x + 5, y + 3, Color.WHITE.getRGB());

        float availableWidth = width - 10;
        float xOffset = 2;
        float yOffset = 4;
        float spacing = 3;

        for (String mode : setting.values.stream().sorted(Comparator.comparingDouble(this::getEnabledWidth)).collect(Collectors.toList())) {
            float enabledWidth = getEnabledWidth(mode);
            float enabledHeight = Fonts.nunitoBold14.getFontHeight() + 4;

            if (xOffset + enabledWidth > availableWidth) {
                xOffset = 2;
                yOffset += enabledHeight + spacing;
            }

            if (setting.selectedValues.get(setting.values.indexOf(mode))) {
                RenderUtility.drawRoundedGradientRect(x + 3 + xOffset, y + Fonts.nunitoBold14.getFontHeight() + yOffset, enabledWidth, enabledHeight, 3, 1, color, color, color2, color2);
            } else {
                RenderUtility.drawRoundedRect(x + 3 + xOffset, y + Fonts.nunitoBold14.getFontHeight() + yOffset, enabledWidth, enabledHeight, 3, new Color(0, 0, 0).getRGB());
            }

            Fonts.nunitoBold14.drawString(mode, x + 5 + xOffset, y + 3 + Fonts.nunitoBold14.getFontHeight() + yOffset, Color.WHITE.getRGB());
            xOffset += enabledWidth + spacing;
        }

        this.height = 18 + yOffset;
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        float availableWidth = width - 10;
        float xOffset = 2;
        float yOffset = 4;
        float spacing = 3;

        for (String mode : setting.values.stream().sorted(Comparator.comparingDouble(this::getEnabledWidth)).collect(Collectors.toList())) {
            float enabledWidth = getEnabledWidth(mode);
            float enabledHeight = Fonts.nunito14.getFontHeight() + 4;

            if (xOffset + enabledWidth > availableWidth) {
                xOffset = 2;
                yOffset += enabledHeight + spacing;
            }

            if (RenderUtility.isHovered(mouseX, mouseY, x + 3 + xOffset, y + Fonts.nunito14.getFontHeight() + yOffset, enabledWidth, enabledHeight)) {
                int index = setting.values.indexOf(mode);
                setting.selectedValues.set(index, !setting.selectedValues.get(index));
            }
            xOffset += enabledWidth + spacing;
        }
    }

    private float getEnabledWidth(String mode) {
        return (Fonts.nunito14.getStringWidth(mode) + 4);
    }

    @Override
    public boolean isVisible() {
        return setting.getVisible().get();
    }
}