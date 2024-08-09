package mathax.client.gui.utils;

import mathax.client.gui.GuiTheme;
import mathax.client.gui.WidgetScreen;

public interface IScreenFactory {
    WidgetScreen createScreen(GuiTheme theme);
}
