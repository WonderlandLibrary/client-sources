package mathax.client.gui.themes.mathax.widgets.pressable;

import mathax.client.gui.renderer.GuiRenderer;
import mathax.client.gui.themes.mathax.MatHaxGuiTheme;
import mathax.client.gui.themes.mathax.MatHaxWidget;
import mathax.client.gui.widgets.pressable.WCheckbox;
import mathax.client.utils.Utils;

public class WMatHaxCheckbox extends WCheckbox implements MatHaxWidget {
    private double animProgress;

    public WMatHaxCheckbox(boolean checked) {
        super(checked);
        animProgress = checked ? 1 : 0;
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        MatHaxGuiTheme theme = theme();

        animProgress += (checked ? 1 : -1) * delta * 14;
        animProgress = Utils.clamp(animProgress, 0, 1);

        renderBackground(renderer, this, pressed, mouseOver);

        if (animProgress > 0) {
            double cs = (width - theme.scale(2)) / 1.75 * animProgress;
            renderer.quadRounded(x + (width - cs) / 2, y + (height - cs) / 2, cs, cs, theme.checkboxColor.get(), theme.roundAmount());
        }
    }
}
