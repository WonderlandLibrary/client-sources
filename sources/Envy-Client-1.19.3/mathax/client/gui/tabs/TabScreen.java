package mathax.client.gui.tabs;

import mathax.client.gui.utils.Cell;
import mathax.client.gui.widgets.WWidget;
import mathax.client.gui.GuiTheme;
import mathax.client.gui.WidgetScreen;

public abstract class TabScreen extends WidgetScreen {
    public final Tab tab;

    public TabScreen(GuiTheme theme, Tab tab) {
        super(theme, tab.name);
        this.tab = tab;
    }

    public <T extends WWidget> Cell<T> addDirect(T widget) {
        return super.add(widget);
    }
}
