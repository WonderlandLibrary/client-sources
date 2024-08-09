package dev.darkmoon.client.ui.csgui.component.impl;

import dev.darkmoon.client.manager.theme.Theme;
import dev.darkmoon.client.manager.theme.Themes;
import dev.darkmoon.client.ui.csgui.component.Component;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.utility.render.RenderUtility;
import dev.darkmoon.client.utility.render.font.Fonts;
import lombok.Getter;

import java.awt.*;

public class ThemeComponent extends Component {
    @Getter
    private final Theme theme;

    public ThemeComponent(Theme theme, float width, float height) {
        super(0, 0, width, height);
        this.theme = theme;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        if (DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().equals(theme)) {
            RenderUtility.drawRoundedRect(x - 1, y - 1, width + 2, height + 2, 8, Color.WHITE.getRGB());
        }
        Color color = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0];
        Color color2 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1];
        RenderUtility.drawRoundedRect(x, y, width, height, 7, new Color(17, 15, 15).getRGB());

        if (theme.getType().equals(Theme.ThemeType.STYLE)) {
            RenderUtility.drawRoundedGradientRect(x, y, width, height - 16, 7, 0, 7, 0, 1, theme.getColors()[0].getRGB(), theme.getColors()[0].getRGB(), theme.getColors()[1].getRGB(), theme.getColors()[1].getRGB());
            Fonts.nunitoBold14.drawGradientCenteredString(theme.getName(), x + width / 2f, y + 19.8f, theme.getColors()[0], theme.getColors()[1]);
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (RenderUtility.isHovered(mouseX, mouseY, x, y, width, height)) {
            if (theme.getType().equals(Theme.ThemeType.STYLE)) {
                DarkMoon.getInstance().getThemeManager().setCurrentStyleTheme(theme);
            }
        }
    }
}
