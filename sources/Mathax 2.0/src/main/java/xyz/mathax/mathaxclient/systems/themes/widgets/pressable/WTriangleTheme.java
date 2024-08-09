package xyz.mathax.mathaxclient.systems.themes.widgets.pressable;

import xyz.mathax.mathaxclient.gui.renderer.GuiRenderer;
import xyz.mathax.mathaxclient.systems.themes.widgets.WidgetTheme;
import xyz.mathax.mathaxclient.gui.widgets.pressable.WTriangle;

public class WTriangleTheme extends WTriangle implements WidgetTheme {
    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        renderer.rotatedQuad(GuiRenderer.TRIANGLE, theme().textColorSetting.get(), x, y, width, height, rotation);
    }
}
