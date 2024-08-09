package xyz.mathax.mathaxclient.systems.themes.widgets;

import xyz.mathax.mathaxclient.gui.renderer.GuiRenderer;
import xyz.mathax.mathaxclient.gui.widgets.BaseWidget;
import xyz.mathax.mathaxclient.gui.widgets.WWidget;
import xyz.mathax.mathaxclient.systems.themes.Theme;
import xyz.mathax.mathaxclient.utils.render.color.Color;

public interface WidgetTheme extends BaseWidget {
    default Theme theme() {
        return getTheme();
    }

    default void renderBackground(GuiRenderer renderer, WWidget widget, boolean pressed, boolean mouseOver) {
        Theme theme = theme();
        double scale = theme.scale(2);

        renderer.quad(widget.x + scale, widget.y + scale, widget.width - scale * 2, widget.height - scale * 2, theme.backgroundColorSetting.get(pressed, mouseOver));

        Color outlineColor = theme.outlineColorSetting.get(pressed, mouseOver);
        renderer.quad(widget.x, widget.y, widget.width, scale, outlineColor);
        renderer.quad(widget.x, widget.y + widget.height - scale, widget.width, scale, outlineColor);
        renderer.quad(widget.x, widget.y + scale, scale, widget.height - scale * 2, outlineColor);
        renderer.quad(widget.x + widget.width - scale, widget.y + scale, scale, widget.height - scale * 2, outlineColor);
    }
}
