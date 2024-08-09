package xyz.mathax.mathaxclient.gui.tabs;

import xyz.mathax.mathaxclient.systems.themes.Theme;
import xyz.mathax.mathaxclient.utils.gui.Cell;
import xyz.mathax.mathaxclient.gui.widgets.WWidget;
import xyz.mathax.mathaxclient.gui.widgets.containers.WWindow;

public abstract class WindowTabScreen extends TabScreen {
    protected final WWindow window;

    public WindowTabScreen(Theme theme, Tab tab) {
        super(theme, tab);

        window = super.add(theme.window(tab.name)).center().widget();
    }

    @Override
    public <W extends WWidget> Cell<W> add(W widget) {
        return window.add(widget);
    }

    @Override
    public void clear() {
        window.clear();
    }
}
