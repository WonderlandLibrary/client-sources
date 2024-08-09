package xyz.mathax.mathaxclient.utils.gui;

import xyz.mathax.mathaxclient.systems.themes.Theme;
import xyz.mathax.mathaxclient.gui.WidgetScreen;

public interface IScreenFactory {
    WidgetScreen createScreen(Theme theme);
}
