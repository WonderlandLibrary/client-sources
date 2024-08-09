package xyz.mathax.mathaxclient.systems.themes.widgets.pressable;

import xyz.mathax.mathaxclient.systems.themes.widgets.WidgetTheme;
import xyz.mathax.mathaxclient.gui.widgets.pressable.WFavorite;
import xyz.mathax.mathaxclient.utils.render.color.Color;

public class WFavoriteTheme extends WFavorite implements WidgetTheme {
    public WFavoriteTheme(boolean checked) {
        super(checked);
    }

    @Override
    protected Color getColor() {
        return theme().favoriteColorSetting.get();
    }
}
