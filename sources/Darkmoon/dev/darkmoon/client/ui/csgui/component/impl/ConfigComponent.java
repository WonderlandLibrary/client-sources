package dev.darkmoon.client.ui.csgui.component.impl;

import dev.darkmoon.client.manager.theme.Themes;
import dev.darkmoon.client.ui.csgui.component.Component;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.utility.render.RenderUtility;
import dev.darkmoon.client.utility.render.font.Fonts;
import lombok.Getter;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class ConfigComponent extends Component {
    @Getter
    private final String name;
    private final List<String> buttons = Arrays.asList("Load", "Save", "Delete");

    public ConfigComponent(String name, float width, float height) {
        super(0, 0, width, height);
        this.name = name;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        Color color = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0];
        Color color2 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1];
        Color moduleColor = new Color(17, 15, 15);

        RenderUtility.drawGradientGlow(x + 1, y + height - 1, (int) width - 2, 2, 4, color, color2);
        RenderUtility.drawRoundedGradientRect(x + 0.5f, y + height - 8, width - 1, 9, 5, 1, color.getRGB(), color.getRGB(), color2.getRGB(), color2.getRGB());
        RenderUtility.drawRoundedRect(x, y, width, height, 5, moduleColor.getRGB());

        Fonts.nunitoBold16.drawString(name, x + 5, y + 5, Color.WHITE.getRGB());

        float xOffset = 2;
        float spacing = 3;

        for (String mode : buttons) {
            float enabledWidth = getEnabledWidth(mode);
            float enabledHeight = Fonts.nunitoBold14.getFontHeight() + 4;

            RenderUtility.drawRoundedRect(x + 3 + xOffset, y + 8 + Fonts.nunitoBold14.getFontHeight(), enabledWidth, enabledHeight, 3, new Color(52, 52, 52).getRGB());
            Fonts.nunitoBold14.drawString(mode, x + 5 + xOffset, y + 11 + Fonts.nunitoBold14.getFontHeight(), Color.WHITE.getRGB());
            xOffset += enabledWidth + spacing;
        }
    }

    public boolean mouseBoolClicked(double mouseX, double mouseY, int mouseButton) {
        float xOffset = 2;
        float spacing = 3;

        for (String mode : buttons) {
            float enabledWidth = getEnabledWidth(mode);
            float enabledHeight = Fonts.nunitoBold14.getFontHeight() + 4;

            if (RenderUtility.isHovered(mouseX, mouseY, x + 3 + xOffset, y + 8 + Fonts.nunitoBold14.getFontHeight(), enabledWidth, enabledHeight)) {
                switch (mode)  {
                    case "Load":
                        DarkMoon.getInstance().getConfigManager().loadConfig(name);
                        break;
                    case "Save":
                        DarkMoon.getInstance().getConfigManager().saveConfig(name);
                        break;
                    case "Delete":
                        DarkMoon.getInstance().getConfigManager().deleteConfig(name);
                        return true;
                }
            }

            xOffset += enabledWidth + spacing;
        }
        return false;
    }

    private float getEnabledWidth(String mode) {
        return (Fonts.nunito14.getStringWidth(mode) + 4);
    }
}
