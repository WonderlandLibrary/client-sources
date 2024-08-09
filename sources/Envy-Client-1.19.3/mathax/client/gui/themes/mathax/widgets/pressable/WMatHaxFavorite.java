package mathax.client.gui.themes.mathax.widgets.pressable;

import mathax.client.gui.themes.mathax.MatHaxWidget;
import mathax.client.gui.widgets.pressable.WFavorite;
import mathax.client.utils.render.color.Color;

public class WMatHaxFavorite extends WFavorite implements MatHaxWidget {
    public WMatHaxFavorite(boolean checked) {
        super(checked);
    }

    @Override
    protected Color getColor() {
        return theme().favoriteColor.get();
    }
}
