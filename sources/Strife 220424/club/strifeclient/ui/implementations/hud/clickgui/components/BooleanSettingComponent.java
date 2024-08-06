package club.strifeclient.ui.implementations.hud.clickgui.components;

import club.strifeclient.setting.implementations.BooleanSetting;
import club.strifeclient.ui.implementations.hud.clickgui.Component;
import club.strifeclient.ui.implementations.hud.clickgui.ExtendableComponent;
import club.strifeclient.ui.implementations.hud.clickgui.Theme;

public class BooleanSettingComponent extends SettingComponent<BooleanSetting> {
    private long start;

    public BooleanSettingComponent(BooleanSetting object, Theme theme, Component<?> parent, float x, float y, float width, float height) {
        super(object, theme, parent, x, y, width, height);
        if (parent instanceof ExtendableComponent<?>) {
            ExtendableComponent<?> extendableParent = (ExtendableComponent<?>) parent;
            extendableParent.addExtendCallback(extended -> {
                visible = extended;
                start = System.currentTimeMillis();
            });
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        theme.drawBooleanSetting(object, x, y, width, height, origHeight, start, partialTicks);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isHovered(mouseX, mouseY) && button == 0) {
            object.setValue(!object.getValue());
            start = System.currentTimeMillis();
        }
    }

    @Override
    public void initGui() {
        super.initGui();
        start = System.currentTimeMillis();
    }
}
