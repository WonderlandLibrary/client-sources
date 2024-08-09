package xyz.mathax.mathaxclient.systems.themes.widgets;

import xyz.mathax.mathaxclient.gui.renderer.GuiRenderer;
import xyz.mathax.mathaxclient.gui.widgets.WHorizontalSeparator;
import xyz.mathax.mathaxclient.systems.themes.Theme;

public class WHorizontalSeparatorTheme extends WHorizontalSeparator implements WidgetTheme {
    public WHorizontalSeparatorTheme(String text) {
        super(text);
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        if (text == null) {
            renderWithoutText(renderer);
        } else {
            renderWithText(renderer);
        }
    }

    private void renderWithoutText(GuiRenderer renderer) {
        Theme theme = theme();
        double scale = theme.scale(1);
        double halfWidth = width / 2;

        renderer.quad(x, y + scale, halfWidth, scale, theme.separatorEdgesSetting.get(), theme.separatorCenterSetting.get());
        renderer.quad(x + halfWidth, y + scale, halfWidth, scale, theme.separatorCenterSetting.get(), theme.separatorEdgesSetting.get());
    }

    private void renderWithText(GuiRenderer renderer) {
        Theme theme = theme();
        double scale = theme.scale(2);
        double scale1 = theme.scale(1);

        double textStart = Math.round(width / 2.0 - textWidth / 2.0 - scale);
        double textEnd = scale + textStart + textWidth + scale;

        double offsetY = Math.round(height / 2.0);

        renderer.quad(x, y + offsetY, textStart, scale1, theme.separatorEdgesSetting.get(), theme.separatorCenterSetting.get());
        renderer.text(text, x + textStart + scale, y, theme.separatorTextSetting.get(), false);
        renderer.quad(x + textEnd, y + offsetY, width - textEnd, scale1, theme.separatorCenterSetting.get(), theme.separatorEdgesSetting.get());
    }
}
