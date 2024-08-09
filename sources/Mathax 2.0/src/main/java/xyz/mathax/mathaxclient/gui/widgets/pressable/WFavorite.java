package xyz.mathax.mathaxclient.gui.widgets.pressable;

import xyz.mathax.mathaxclient.gui.renderer.GuiRenderer;
import xyz.mathax.mathaxclient.utils.render.color.Color;

public abstract class WFavorite extends WPressable {
    public boolean checked;

    public WFavorite(boolean checked) {
        this.checked = checked;
    }

    @Override
    protected void onCalculateSize() {
        double pad = pad();
        double textHeight = theme.textHeight();
        width = pad + textHeight + pad;
        height = pad + textHeight + pad;
    }

    @Override
    protected void onPressed(int button) {
        checked = !checked;
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        double pad = pad();
        double textHeight = theme.textHeight();

        renderer.quad(checked ? GuiRenderer.FAVORITE_YES : GuiRenderer.FAVORITE_NO, getColor(), x + pad, y + pad, textHeight, textHeight);
    }

    protected abstract Color getColor();
}
