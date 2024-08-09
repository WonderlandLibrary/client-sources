package mathax.client.gui.screens;

import mathax.client.gui.utils.Cell;
import mathax.client.gui.widgets.WWidget;
import mathax.client.gui.widgets.containers.WContainer;
import mathax.client.gui.GuiTheme;
import mathax.client.gui.WindowScreen;
import mathax.client.systems.modules.render.marker.BaseMarker;

public class MarkerScreen extends WindowScreen {
    private final BaseMarker marker;
    private WContainer settingsContainer;

    public MarkerScreen(GuiTheme theme, BaseMarker marker) {
        super(theme, marker.name.get());

        this.marker = marker;
    }

    @Override
    public void initWidgets() {
        // Settings
        if (marker.settings.groups.size() > 0) {
            settingsContainer = add(theme.verticalList()).expandX().widget();
            settingsContainer.add(theme.settings(marker.settings)).expandX();
        }

        // Custom widget
        WWidget widget = getWidget(theme);

        if (widget != null) {
            add(theme.horizontalSeparator()).expandX();
            Cell<WWidget> cell = add(widget);
            if (widget instanceof WContainer) cell.expandX();
        }
    }

    @Override
    public void tick() {
        marker.settings.tick(settingsContainer, theme);
    }

    public WWidget getWidget(GuiTheme theme) {
        return null;
    }
}
