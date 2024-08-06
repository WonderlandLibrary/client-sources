package club.strifeclient.ui.implementations.hud.clickgui.components;

import club.strifeclient.module.Module;
import club.strifeclient.ui.implementations.hud.clickgui.Component;
import club.strifeclient.ui.implementations.hud.clickgui.ExtendableComponent;
import club.strifeclient.ui.implementations.hud.clickgui.Theme;

public class ModuleComponent extends ExtendableComponent<Module> {

    public ModuleComponent(Module object, Theme theme, Component<?> parent, float x, float y, float width, float height) {
        super(object, theme, parent, x, y, width, height);
        if (parent instanceof ExtendableComponent<?>) {
            ExtendableComponent<?> extendableParent = (ExtendableComponent<?>) parent;
            extendableParent.addExtendCallback(extended -> {
                visible = extended;
                start = System.currentTimeMillis();
            });
        }
    }

    private long start;

    @Override
    public boolean isVisible() {
        return super.isVisible();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        float fOfX = Math.min(1, (System.currentTimeMillis() - start) / 500f);
        float expandAnimation = (float) (Math.pow(fOfX - 1, 6));
        if(visible)
            height = origHeight * (1 - expandAnimation);
        theme.drawModule(object, x, y, width, height, origHeight, partialTicks);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isHovered(mouseX, mouseY)) {
            if (button == 0)
                object.setEnabled(!object.isEnabled());
            else if (button == 1) {
                setExtended(!isExtended());
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int button) {
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
    }

    @Override
    public void onGuiClosed() {

    }

    @Override
    public void initGui() {
        height = 0;
        start = System.currentTimeMillis();
    }
}
