package mathax.client.gui.themes.mathax.widgets;

import mathax.client.gui.renderer.GuiRenderer;
import mathax.client.gui.themes.mathax.MatHaxWidget;
import mathax.client.gui.widgets.WLabel;

public class WMatHaxLabel extends WLabel implements MatHaxWidget {
    public WMatHaxLabel(String text, boolean title) {
        super(text, title);
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        if (!text.isEmpty()) {
            renderer.text(text, x, y, color != null ? color : (title ? theme().titleTextColor.get() : theme().textColor.get()), title);
        }
    }
}
