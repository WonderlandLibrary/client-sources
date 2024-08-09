package mathax.client.gui.themes.mathax.widgets.pressable;

import mathax.client.gui.renderer.GuiRenderer;
import mathax.client.gui.themes.mathax.MatHaxGuiTheme;
import mathax.client.gui.themes.mathax.MatHaxWidget;
import mathax.client.gui.widgets.pressable.WPlus;

public class WMatHaxPlus extends WPlus implements MatHaxWidget {
    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        MatHaxGuiTheme theme = theme();
        double pad = pad();
        double s = theme.scale(3);

        renderBackground(renderer, this, pressed, mouseOver);
        renderer.quad(x + pad, y + height / 2 - s / 2, width - pad * 2, s, theme.plusColor.get());
        renderer.quad(x + width / 2 - s / 2, y + pad, s, height - pad * 2, theme.plusColor.get());
    }
}
