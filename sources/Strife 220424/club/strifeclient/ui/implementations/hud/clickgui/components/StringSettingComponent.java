package club.strifeclient.ui.implementations.hud.clickgui.components;

import club.strifeclient.setting.implementations.StringSetting;
import club.strifeclient.ui.implementations.hud.clickgui.Component;
import club.strifeclient.ui.implementations.hud.clickgui.GuiFocusable;
import club.strifeclient.ui.implementations.hud.clickgui.Theme;

public class StringSettingComponent extends SettingComponent<StringSetting> implements GuiFocusable {
    private boolean focused;
    public StringSettingComponent(StringSetting object, Theme theme, Component<?> parent, float x, float y, float width, float height) {
        super(object, theme, parent, x, y, width, height);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        theme.drawStringSetting(object, x, y, width, height, origHeight, focused, partialTicks);
    }

    @Override
    public boolean isFocused() {
        return focused;
    }

    @Override
    public void setFocused(boolean focused) {
        this.focused = focused;
    }
}
