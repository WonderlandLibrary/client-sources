package mathax.client.gui.themes.mathax.widgets;

import mathax.client.gui.renderer.GuiRenderer;
import mathax.client.gui.widgets.WQuad;
import mathax.client.utils.render.color.Color;

public class WMatHaxQuad extends WQuad {
    public WMatHaxQuad(Color color) {
        super(color);
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        renderer.quadRounded(x, y, width, height, color, theme.roundAmount());
    }
}
