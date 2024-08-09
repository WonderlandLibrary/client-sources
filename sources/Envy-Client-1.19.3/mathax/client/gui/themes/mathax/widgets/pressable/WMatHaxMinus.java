package mathax.client.gui.themes.mathax.widgets.pressable;

import mathax.client.gui.renderer.GuiRenderer;
import mathax.client.gui.themes.mathax.MatHaxWidget;
import mathax.client.gui.widgets.pressable.WMinus;

public class WMatHaxMinus extends WMinus implements MatHaxWidget {
    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        double pad = pad();
        double s = theme.scale(3);

        renderBackground(renderer, this, pressed, mouseOver);
        renderer.quad(x + pad, y + height / 2 - s / 2, width - pad * 2, s, theme().minusColor.get());
    }
}
