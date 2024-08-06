package club.strifeclient.ui.implementations.hud.clickgui.components;

import club.strifeclient.setting.implementations.DoubleSetting;
import club.strifeclient.ui.implementations.hud.clickgui.Component;
import club.strifeclient.ui.implementations.hud.clickgui.ExtendableComponent;
import club.strifeclient.ui.implementations.hud.clickgui.Theme;
import club.strifeclient.util.math.MathUtil;

public class DoubleSettingComponent extends SettingComponent<DoubleSetting> {

    private boolean sliding;
    private long start, slideStart;

    public DoubleSettingComponent(DoubleSetting object, Theme theme, Component<?> parent, float x, float y, float width, float height) {
        super(object, theme, parent, x, y, width, height);
        if(parent instanceof ExtendableComponent<?>) {
            ExtendableComponent<?> extendableParent = (ExtendableComponent<?>) parent;
            extendableParent.addExtendCallback(extended -> {
                visible = extended;
                start = System.currentTimeMillis();
                slideStart = System.currentTimeMillis();
            });
        }
    }

    @Override
    public void onGuiClosed() {
        mouseReleased(0, 0, 0);
    }

    @Override
    public void initGui() {
        super.initGui();
        slideStart = System.currentTimeMillis();
        start = System.currentTimeMillis();
    }

    @Override
    public boolean isVisible() {
        return super.isVisible() && object.isAvailable() && height != 0;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // to calculate %'s you use the same data types
        double valueDelta = object.getMax() - object.getMin();
        double startX = x;
        double mouseDelta = mouseX - startX;
        double widthProgress = (object.getValue() - object.getMin()) / valueDelta * width;
        double slideDelta = 0;
        if (sliding) {
            double value = MathUtil.round(object.getMin() + mouseDelta / width * valueDelta, 2, object.getIncrement());
            if (value != object.getValue()) {
                slideDelta = value - object.getValue();
            }
            object.setValue(value);
        }
        float fOfX = Math.min(1, (System.currentTimeMillis() - start) / 500f);
        float expandAnimation = (float) (Math.pow(fOfX - 1, 6));
        height = origHeight * (1 - expandAnimation);

        fOfX = Math.min(1, (System.currentTimeMillis() - slideStart) / 1000f);
        float widthAnimation = (float) (Math.pow(fOfX - 1, 6));
        widthProgress *= (1 - widthAnimation);
        theme.drawDoubleSetting(object, x, y, width, height, origHeight, widthProgress, partialTicks);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isHovered(mouseX, mouseY) && button == 0)
            sliding = true;
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int button) {
        sliding = false;
    }
}
