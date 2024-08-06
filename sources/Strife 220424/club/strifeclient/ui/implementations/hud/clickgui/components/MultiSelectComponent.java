package club.strifeclient.ui.implementations.hud.clickgui.components;

import club.strifeclient.setting.implementations.MultiSelectSetting;
import club.strifeclient.ui.implementations.hud.clickgui.Component;
import club.strifeclient.ui.implementations.hud.clickgui.ExtendableComponent;
import club.strifeclient.ui.implementations.hud.clickgui.Theme;

import java.io.IOException;

public class MultiSelectComponent extends ExtendableComponent<MultiSelectSetting<?>> {

    private long start;

    public MultiSelectComponent(MultiSelectSetting<?> object, Theme theme, Component<?> parent, float x, float y, float width, float height) {
        super(object, theme, parent, x, y, width, height);
        if(parent instanceof ExtendableComponent<?>) {
            ExtendableComponent<?> extendableParent = (ExtendableComponent<?>) parent;
            extendableParent.addExtendCallback(extended -> {
                visible = extended;
                start = System.currentTimeMillis();
            });
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        theme.drawMultiSelectSetting(object, x, y, width, height, origWidth, origHeight, isExtended(), partialTicks);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isHovered(mouseX, mouseY) && button == 1)
            setExtended(!isExtended());
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
