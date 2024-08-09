package xyz.mathax.mathaxclient.systems.themes.widgets;

import xyz.mathax.mathaxclient.gui.renderer.GuiRenderer;
import xyz.mathax.mathaxclient.gui.widgets.WQuad;
import xyz.mathax.mathaxclient.utils.render.color.Color;

public class WQuadTheme extends WQuad {
    public WQuadTheme(Color color) {
        super(color);
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        renderer.quad(x, y, width, height, color);
    }
}
