package mathax.client.gui.utils;

import mathax.client.gui.widgets.WWidget;
import mathax.client.gui.GuiTheme;
import mathax.client.settings.Settings;

public interface SettingsWidgetFactory {
    WWidget create(GuiTheme theme, Settings settings, String filter);
}
