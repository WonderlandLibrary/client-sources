package xyz.mathax.mathaxclient.systems.themes.widgets;

import xyz.mathax.mathaxclient.gui.renderer.GuiRenderer;
import xyz.mathax.mathaxclient.gui.widgets.WVerticalSeparator;
import xyz.mathax.mathaxclient.utils.render.color.Color;

public class WVerticalSeparatorTheme extends WVerticalSeparator implements WidgetTheme {
    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        Color colorEdges = theme.separatorEdgesSetting.get();
        Color colorCenter = theme.separatorCenterSetting.get();

        double s = theme.scale(1);
        double offsetX = Math.round(width / 2.0);

        renderer.quad(x + offsetX, y, s, height / 2, colorEdges, colorEdges, colorCenter, colorCenter);
        renderer.quad(x + offsetX, y + height / 2, s, height / 2, colorCenter, colorCenter, colorEdges, colorEdges);
    }
}
