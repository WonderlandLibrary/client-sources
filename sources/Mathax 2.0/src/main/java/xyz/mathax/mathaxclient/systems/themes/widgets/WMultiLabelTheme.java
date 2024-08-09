package xyz.mathax.mathaxclient.systems.themes.widgets;

import xyz.mathax.mathaxclient.gui.renderer.GuiRenderer;
import xyz.mathax.mathaxclient.gui.widgets.WMultiLabel;
import xyz.mathax.mathaxclient.utils.render.color.Color;

public class WMultiLabelTheme extends WMultiLabel implements WidgetTheme {
    public WMultiLabelTheme(String text, boolean title, double maxWidth) {
        super(text, title, maxWidth);
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        double textHeight = theme.textHeight(title);
        Color defaultColor = theme().textColorSetting.get();
        for (int i = 0; i < lines.size(); i++) {
            renderer.text(lines.get(i), x, y + textHeight * i, color != null ? color : defaultColor, false);
        }
    }
}
