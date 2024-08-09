package xyz.mathax.mathaxclient.systems.themes.widgets;

import xyz.mathax.mathaxclient.gui.widgets.WTopBar;
import xyz.mathax.mathaxclient.utils.render.color.Color;

public class WTopBarTheme extends WTopBar implements WidgetTheme {
    @Override
    protected Color getButtonColor(boolean pressed, boolean hovered) {
        return theme().backgroundColorSetting.get(pressed, hovered);
    }

    @Override
    protected Color getNameColor() {
        return theme().textColorSetting.get();
    }
}
