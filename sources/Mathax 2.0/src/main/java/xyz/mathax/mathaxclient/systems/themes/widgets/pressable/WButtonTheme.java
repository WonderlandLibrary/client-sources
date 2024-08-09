package xyz.mathax.mathaxclient.systems.themes.widgets.pressable;

import xyz.mathax.mathaxclient.gui.renderer.GuiRenderer;
import xyz.mathax.mathaxclient.gui.renderer.packer.GuiTexture;
import xyz.mathax.mathaxclient.systems.themes.Theme;
import xyz.mathax.mathaxclient.systems.themes.widgets.WidgetTheme;
import xyz.mathax.mathaxclient.gui.widgets.pressable.WButton;

public class WButtonTheme extends WButton implements WidgetTheme {
    public WButtonTheme(String text, GuiTexture texture) {
        super(text, texture);
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        Theme theme = theme();
        double pad = pad();

        renderBackground(renderer, this, pressed, mouseOver);

        if (text != null) {
            renderer.text(text, x + width / 2 - textWidth / 2, y + pad, theme.textColorSetting.get(), false);
        } else {
            double textHeight = theme.textHeight();
            renderer.quad(texture, theme.textColorSetting.get(), x + width / 2 - textHeight / 2, y + pad, textHeight, textHeight);
        }
    }
}
