package mathax.client.gui.themes.meteor.widgets;

import mathax.client.gui.renderer.GuiRenderer;
import mathax.client.gui.themes.meteor.MeteorWidget;
import mathax.client.gui.widgets.WTooltip;

public class WMeteorTooltip extends WTooltip implements MeteorWidget {
    public WMeteorTooltip(String text) {
        super(text);
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        renderer.quad(this, theme().backgroundColor.get());
    }
}
