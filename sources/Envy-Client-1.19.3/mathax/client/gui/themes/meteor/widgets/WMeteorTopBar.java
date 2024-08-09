package mathax.client.gui.themes.meteor.widgets;

import mathax.client.gui.themes.meteor.MeteorWidget;
import mathax.client.gui.widgets.WTopBar;
import mathax.client.utils.render.color.Color;

public class WMeteorTopBar extends WTopBar implements MeteorWidget {
    @Override
    protected Color getButtonColor(boolean pressed, boolean hovered) {
        return theme().backgroundColor.get(pressed, hovered);
    }

    @Override
    protected Color getNameColor() {
        return theme().textColor.get();
    }
}
