package xyz.mathax.mathaxclient.systems.themes.widgets.pressable;

import xyz.mathax.mathaxclient.gui.renderer.GuiRenderer;
import xyz.mathax.mathaxclient.systems.themes.Theme;
import xyz.mathax.mathaxclient.systems.themes.widgets.WidgetTheme;
import xyz.mathax.mathaxclient.gui.widgets.pressable.WPlus;

public class WPlusTheme extends WPlus implements WidgetTheme {
    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        Theme theme = theme();
        double pad = pad();
        double scale = theme.scale(3);

        renderBackground(renderer, this, pressed, mouseOver);
        renderer.quad(x + pad, y + height / 2 - scale / 2, width - pad * 2, scale, theme.plusColorSetting.get());
        renderer.quad(x + width / 2 - scale / 2, y + pad, scale, height - pad * 2, theme.plusColorSetting.get());
    }
}
