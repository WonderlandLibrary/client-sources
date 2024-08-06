package club.strifeclient.ui.implementations.hud.clickgui.components;

import club.strifeclient.setting.Setting;
import club.strifeclient.ui.implementations.hud.clickgui.Component;
import club.strifeclient.ui.implementations.hud.clickgui.Theme;

import java.io.IOException;

public class SettingComponent<T extends Setting<?>> extends Component<T> {
    public SettingComponent(T object, Theme theme, Component<?> parent, float x, float y, float width, float height) {
        super(object, theme, parent, x, y, width, height);
    }
    @Override
    public boolean isVisible() {
        return super.isVisible() && object.isAvailable();
    }
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

    }
    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {

    }
    @Override
    public void mouseReleased(int mouseX, int mouseY, int button) {

    }
    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException {

    }
    @Override
    public void onGuiClosed() {

    }
    @Override
    public void initGui() {

    }
}
