package club.strifeclient.ui.implementations.imgui.implementations;

import club.strifeclient.ui.implementations.imgui.theme.IImGuiTheme;

public interface IImGuiInterface {
    void render();
    IImGuiTheme getTheme();
    void setTheme(final IImGuiTheme theme);
}
