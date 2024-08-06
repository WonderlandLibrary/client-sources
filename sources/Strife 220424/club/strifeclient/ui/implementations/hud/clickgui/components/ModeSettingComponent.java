package club.strifeclient.ui.implementations.hud.clickgui.components;

import club.strifeclient.setting.implementations.ModeSetting;
import club.strifeclient.ui.implementations.hud.clickgui.Component;
import club.strifeclient.ui.implementations.hud.clickgui.ExtendableComponent;
import club.strifeclient.ui.implementations.hud.clickgui.Theme;

import java.util.Arrays;

public class ModeSettingComponent extends SettingComponent<ModeSetting<?>> {

    private int index;
    private long start;

    public ModeSettingComponent(ModeSetting<?> object, Theme theme, Component<?> parent, float x, float y, float width, float height) {
        super(object, theme, parent, x, y, width, height);
        index = Arrays.asList(object.getValues()).indexOf(object.getValue());
        if(parent instanceof ExtendableComponent<?>) {
            ExtendableComponent<?> extendableParent = (ExtendableComponent<?>) parent;
            extendableParent.addExtendCallback(extended -> {
                visible = extended;
                start = System.currentTimeMillis();
            });
        }
    }

    @Override
    public boolean isVisible() {
        return super.isVisible() && object.isAvailable();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        float fOfX = Math.min(1, (System.currentTimeMillis() - start) / 500f);
        float expandAnimation = (float) (Math.pow(fOfX - 1, 6));
        height = origHeight * (1 - expandAnimation);
        theme.drawModeSetting(object, object.getValue().getName().toUpperCase(), x, y, width, height, origHeight, partialTicks);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if(isHovered(mouseX, mouseY)) {
            if(button != 2)
                index = object.cycle(index, button == 1);
        }
    }

    @Override
    public void initGui() {
        super.initGui();
        start = System.currentTimeMillis();
    }
}
