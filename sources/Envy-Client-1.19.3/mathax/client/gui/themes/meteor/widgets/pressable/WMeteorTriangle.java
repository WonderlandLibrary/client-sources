package mathax.client.gui.themes.meteor.widgets.pressable;

import mathax.client.gui.renderer.GuiRenderer;
import mathax.client.gui.themes.meteor.MeteorWidget;
import mathax.client.gui.widgets.pressable.WTriangle;

public class WMeteorTriangle extends WTriangle implements MeteorWidget {
    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        renderer.rotatedQuad(x, y, width, height, rotation, GuiRenderer.TRIANGLE, theme().backgroundColor.get(pressed, mouseOver));
    }
}
